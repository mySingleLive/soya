package org.soya.antlr;

import java.util.Vector;
import java.util.logging.Logger;

import antlr.CharStreamException;
import antlr.TokenStreamException;
import org.soya.runtime.SoyaException;
import org.soya.antlr.parser.SoyaLexer;
import org.soya.antlr.parser.SoyaParser;

import antlr.Token;
import antlr.TokenStream;

import static org.soya.antlr.parser.SoyaParserTokenTypes.*;

/**
 * @author: Jun Gong
 */
public class SoyaTokenSource implements TokenStream {
	private static final Logger LOG = Logger.getLogger(SoyaTokenSource.class.getName());
    public static final int MAX_INDENTS = 100;
    
    private boolean allowHandleIndentation = false;
    
    /** The stack of indent */
    protected int[] indentStack = new int[MAX_INDENTS];
    
    /** The top of stack */
    protected int top = -1;
    
    protected boolean yaml = false;
    
    /** The queue of tokens */
    protected Vector<Token> tokens = new Vector<Token>();
    
    private int beginIndentationIndex = 0;
    
    protected int indentationListTokenType = STAR;
    
    protected TokenStream tokenStream;
    
    protected SoyaLexer lexer;
    
    protected SoyaParser parser;
    
	public void setAllowHandleIndentation(boolean value) {
		allowHandleIndentation = value;
	}

	public boolean isAllowHandleIndentation() { 
		return allowHandleIndentation;
	}
	
	public SoyaTokenSource(TokenStream tokenStream) {
		this.tokenStream = tokenStream;
		push(1);
	}

	public TokenStream getTokenStream() {
		return tokenStream;
	}

	public void setTokenStream(TokenStream tokenStream) {
		this.tokenStream = tokenStream;
	}
	
	public SoyaLexer getLexer() {
		return lexer;
	}

	public void setLexer(SoyaLexer lexer) {
		this.lexer = lexer;
	}

	public SoyaParser getParser() {
		return parser;
	}

	public void setParser(SoyaParser parser) {
		this.parser = parser;
	}
	
    public void beginHandleIndentation (Token tok) {
//    	if (!allowHandleIndentation) {
    		LOG.info("[hand indentation token: " + tok + "]");
    		setAllowHandleIndentation(true);
    		addIndentation(tok.getColumn(), tok);
    	//}
    }

	protected void push(int tok) {
		if (top >= MAX_INDENTS) {
            throw new IllegalStateException("stack overflow");
        }
		//LOG.info("push(" + tok + ")");
		indentStack[++top] = tok;
	}
	
	protected int pop() {
		if (top < 0) {
            throw new IllegalStateException("stack underflow");
        }
        int t = indentStack[top];
        top--;
        return t;
	}
	
	protected int peek() {
		return indentStack[top];
	}
	
	protected int findPreviousIndent(int col, Token t) throws SoyaException {
        for (int j = top - 1; j >= 1; j--) {
            if (indentStack[j] == col) {
                return j;
            }
        }
        if (col == -1 || col == -2) {
            return 0;
        }
        throw new SoyaException("unindent does not match any outer indentation column: " + col);
    }

	@Override
	public Token nextToken() throws TokenStreamException {
		if (tokens.size() > 0) {
            Token t = tokens.firstElement();
            tokens.removeElementAt(0);
            //System.out.println(t.toString());
            if (t.getType() == MULTI_COMMENT) {
                return nextToken();
            }
            return t;
        }
        try {
            addTokens();
        } catch (TokenStreamException e) {
            throw e;
        } catch (CharStreamException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SoyaException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return nextToken();
	}

    private void handleList(SoyaToken token) throws TokenStreamException {
        SoyaToken starToken = token;
        token = (SoyaToken)tokenStream.nextToken();
        while(token.getType() == NLS) {
            token = (SoyaToken)tokenStream.nextToken();
        }
        this.addIndentation(starToken.getColumn(), starToken);
        tokens.addElement(starToken);
    }
	
	private void handleYAML(SoyaToken token) throws TokenStreamException {
		SoyaToken tokID = token;
		token = (SoyaToken)tokenStream.nextToken();
		if (token.getType() == COLON) {
			SoyaToken tokCOLON = token;
			token = (SoyaToken)tokenStream.nextToken();
			if (token.getType() == NLS) {
				if (!allowHandleIndentation) {
					SoyaToken tokNLS = token;
					token = (SoyaToken)tokenStream.nextToken();
                    while (token.getType() == NLS) {
                        token = (SoyaToken)tokenStream.nextToken();
                    }
					this.setAllowHandleIndentation(true);
					yaml = true;
					//top = -1;
					push(1);
					//this.addIndentation(tokID.getColumn(), tokID);
					push(tokID.getColumn());
					beginIndentationIndex = top;
					tokens.addElement(tokID);
					tokens.addElement(tokCOLON);
					tokens.addElement(tokNLS);
                    if (token.getType() == indentationListTokenType) {
                        SoyaToken starToken = token;
                        token = (SoyaToken)tokenStream.nextToken();
                        while(token.getType() == NLS) {
                            token = (SoyaToken)tokenStream.nextToken();
                        }
                        this.addIndentation(starToken.getColumn(), starToken);
                        tokens.addElement(starToken);
                        this.addIndentation(token.getColumn(), token);
                    }
                    else {
                        this.addIndentation(token.getColumn(), token);
                    }
                    tokens.addElement(token);
				}
			}
			else {
				tokens.addElement(tokID);
				tokens.addElement(tokCOLON);
				tokens.addElement(token);
			}
		}
		else {
			tokens.addElement(tokID);
			tokens.addElement(token);
		}
	}

    private boolean isKeyType(int tokType) {
        return tokType == ID ||
               tokType == STRING ||
               tokType == STR_SQ_START ||
               tokType == STR_TQ_START;
    }
	
	protected void addTokens() throws TokenStreamException, SoyaException, CharStreamException {
		Token token = tokenStream.nextToken();
		int tokType = token.getType();

		if (tokType == EOF) {
			//LOG.info("match EOF!!");
			Token prev = parser.LT(-1);
			handleEOF((SoyaToken)token, (SoyaToken)prev);
			if (this.isAllowHandleIndentation()) {
				addDedentation(-1, token);
			}
			tokens.addElement(token);
		}
		else if (!allowHandleIndentation && isKeyType(tokType)) {
			handleYAML((SoyaToken)token);
		}
		else if (tokType == NLS) {
			Token nlsTok = token;
			tokens.addElement(nlsTok);

			if (allowHandleIndentation) {
				Token prev = token;
				token = tokenStream.nextToken();
                while (token.getType() == NLS) {
                    token = tokenStream.nextToken();
                }
				
				int col = token.getColumn();
				
				if (token.getType() == EOF) {
					//LOG.info("match NLS EOF!!");
					handleEOF((SoyaToken)token, (SoyaToken)prev);
					col = -1;
				}
				
				int lastIndent  = peek();

				if (col > lastIndent) {
					addIndentation(col, token);
				}
				else if (col < lastIndent) {
					//LOG.info("--------- add dedentation " + col);
					addDedentation(col, token);
					addNLS((SoyaToken) prev);
				}

                if (token.getType() == indentationListTokenType) {
                    //System.out.println("--------------------- NL " + token + "  **** -------");
                    SoyaToken starToken = (SoyaToken)token;
                    tokens.addElement(starToken);
                    token = tokenStream.nextToken();
                    while(token.getType() == NLS) {
                        token = tokenStream.nextToken();
                    }
                    this.setAllowHandleIndentation(true);
                    this.addIndentation(token.getColumn(), token);
                }


                if (!allowHandleIndentation && isKeyType(token.getType())) {
					handleYAML((SoyaToken)token);
				}
				else {
					if (prev.getType() == LIST_PREFIX) {
						tokens.addElement(prev);
						lastIndent = peek();
						col = token.getColumn();
						if (col > lastIndent) {
							addIndentation(col, token);
						}
						else if (col < lastIndent) {
							addDedentation(col, token);
							addNLS((SoyaToken) prev);
						}
					}
					tokens.addElement(token);
				}
			}
            else {
                token = tokenStream.nextToken();
                while (token.getType() == NLS) {
                    token = tokenStream.nextToken();
                }
                tokType = token.getType();

                if (tokType == indentationListTokenType) {
                    //System.out.println("--------------------- " + token + "  **** -------");
                    SoyaToken starToken = (SoyaToken)token;
                    if (!allowHandleIndentation) {
                        push(1);
                        //this.addIndentation(tokID.getColumn(), tokID);
                        push(starToken.getColumn());
                        beginIndentationIndex = top;
                        setAllowHandleIndentation(true);
                    }
                    tokens.addElement(starToken);
                    token = tokenStream.nextToken();

                    while(token.getType() == NLS) {
                        token = tokenStream.nextToken();
                    }
                    this.addIndentation(token.getColumn(), token);
                    tokens.addElement(token);
                }
                else if (isKeyType(tokType)) {
                    handleYAML((SoyaToken) token);
                }
                else  {
                    tokens.addElement(token);
                }
            }
		}
		else {
			tokens.addElement(token);
		}
	}
	
	private void addNLS(SoyaToken token) {
		SoyaToken nls = new SoyaToken(NLS);
		nls.setText("<NEWLINES>");
		nls.setColumn(token.getColumn());
		nls.setLine(token.getLine());
		nls.setEndColumn(token.getEndColumn());
		nls.setEndLine(token.getEndLine());
		tokens.addElement(nls);
	}
	
    private void handleEOF(SoyaToken eof, SoyaToken prev) {
        if (prev != null) {
        	eof.setText("<EOF>");
            //eof.setColumn(prev.getEndColumn());
            //eof.setEndColumn(prev.getEndColumn());
            //eof.setLine(prev.getEndLine());
            //eof.setEndLine(prev.getEndLine());
        }
    }

	
	protected void addIndentation(int col, Token tok) {
		if (!allowHandleIndentation) {
			return;
		}
		push(col);
		SoyaToken indent = new SoyaToken(INDENT);
		indent.setText("<INDENT " + col + ">");
		indent.setColumn(tok.getColumn());
		indent.setLine(tok.getLine());
		indent.setEndColumn(tok.getColumn());
		indent.setEndLine(tok.getLine());
		tokens.addElement(indent);
		//LOG.info("[add indentation]: " + tok + ", top: " + top);
	}
	
	protected void addDedentation(int col, Token tok) throws SoyaException {
		int prevIndex = findPreviousIndent(col, tok);
        for (int i = top - 1; i >= prevIndex; i--) {
        	SoyaToken dedent = new SoyaToken(DEDENT);
        	dedent.setText("<DEDENT " + indentStack[i] + ">");
         	dedent.setColumn(tok.getColumn());
        	dedent.setLine(tok.getLine());
        	dedent.setEndColumn(tok.getColumn());
        	dedent.setEndLine(tok.getLine());
        	tokens.addElement(dedent);
        	//LOG.info("[add dedentation]: " + tok + ", top: " + top);
            if (i <= beginIndentationIndex) {
            	this.setAllowHandleIndentation(false);
            	yaml = false;
            	//LOG.info("---------- set allow handle Indentation false");
            	break;
            }
        }
        top = prevIndex;
	}
}
