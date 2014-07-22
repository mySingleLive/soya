// $ANTLR 2.7.7 (20060906): "soya.g" -> "SoyaParser.java"$

package org.soya.antlr.parser;
import java.util.*;
import java.io.InputStream;
import java.io.Reader;
import antlr.InputBuffer;
import org.soya.antlr.SoyaTokenSource;
import org.soya.ast.SoyaCST;
import antlr.LexerSharedInputState;
import antlr.TokenStreamRecognitionException;

import org.soya.antlr.SoyaToken;

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.collections.AST;
import java.util.Hashtable;
import antlr.ASTFactory;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;

/**
 * Parser of Soya
 * @author: James Gong
 */
public class SoyaParser extends antlr.LLkParser       implements SoyaParserTokenTypes
 {

    protected SoyaLexer lexer;

    public SoyaLexer getLexer() { return lexer; }

    public void setLexer(SoyaLexer lexer) {
        this.lexer = lexer;
    }


    public AST addEndLineColumn(AST ast, Object last) {
        if (ast instanceof SoyaCST && last instanceof Token) {
            SoyaCST past = (SoyaCST)ast;
            Token t = (Token) last;
            past.setEndLine(t.getLine());
            past.setEndColumn(t.getColumn());
        }
        else if (ast instanceof SoyaCST && last instanceof AST) {
            SoyaCST past = (SoyaCST)ast;
            AST t = (AST) last;
            past.setEndLine(t.getLine());
            past.setEndColumn(t.getColumn());
        }
        return ast;
    }

    public AST node(int type, String txt, Token first) {
        AST ast = astFactory.create(type,txt);
        if (ast != null && first != null) {
            ast.initialize(first);
            ast.initialize(type, txt);
        }
        return ast;
    }

    public AST node(int type, String txt, AST first) {
        AST ast = astFactory.create(type,txt);
        if (ast != null && first != null) {
            ast.initialize(first);
            ast.initialize(type, txt);
        }
        return ast;
    }


    public AST node(int type, String txt, Token first, Token last) {
        AST ast = node(type, txt, first);
        addEndLineColumn(ast, last);
        return ast;
    }

    public AST node(int type, String txt, AST first, AST last) {
        AST ast = node(type, txt, first);
        addEndLineColumn(ast, last);
        return ast;
    }

    public AST node(int type, String txt, AST first, Token last) {
        AST ast = node(type, txt, first);
        addEndLineColumn(ast, last);
        return ast;
    }


protected SoyaParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public SoyaParser(TokenBuffer tokenBuf) {
  this(tokenBuf,2);
}

protected SoyaParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public SoyaParser(TokenStream lexer) {
  this(lexer,2);
}

public SoyaParser(ParserSharedInputState state) {
  super(state,2);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

	public final void compilationUnit() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST compilationUnit_AST = null;
		AST pkg_AST = null;
		AST sts_AST = null;
		
		Token first = LT(1);
		
		
		nls();
		{
		switch ( LA(1)) {
		case UNIX_HEADER:
		{
			match(UNIX_HEADER);
			nls();
			break;
		}
		case INDENT:
		case INTEGER:
		case ID:
		case URL:
		case STR_TQ_START:
		case STR_SQ_START:
		case REGEX_START:
		case FILE_PATH:
		case FILE_PATH_START:
		case LPAREN:
		case LBRACK:
		case LCURLY:
		case EMAIL:
		case TIME:
		case PERCENTAGE:
		case NLS:
		case FLOAT:
		case PAIR:
		case DATE:
		case PAIR_LCURLY:
		case WELL_LCURLY:
		case PLUS:
		case MINUS:
		case NOT:
		case DB_DOT:
		case TILDE:
		case RFC822:
		case K_NULL:
		case K_NEW:
		case K_TRUE:
		case K_FALSE:
		case IN:
		case K_IF:
		case K_FOR:
		case WHILE:
		case DO:
		case DEF:
		case K_CLASS:
		case K_TRY:
		case K_IMPORT:
		case K_PATTERN:
		case K_PACKAGE:
		case K_RETURN:
		case K_BREAK:
		case K_MATCH:
		case K_ASSERT:
		case K_THROW:
		case THIS:
		case SUPER:
		case IS:
		case REFRENCE_NAME:
		case IF:
		case FOR:
		case NEW:
		case MOR:
		case AT:
		case LITERAL_public:
		case LITERAL_private:
		case LITERAL_protected:
		case LITERAL_static:
		case LITERAL_abstract:
		case LITERAL_final:
		case STAR:
		case DB_DOT_LT:
		case GT_DB_DOT:
		case GT_DB_DOT_LT:
		case LNOT:
		case STRING:
		case K_FLASE:
		case ELSE:
		case SIGN_LPAREN:
		case SIGN_LCURLY:
		case REGEX:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		{
		switch ( LA(1)) {
		case K_PACKAGE:
		{
			packageStatement();
			pkg_AST = (AST)returnAST;
			astFactory.addASTChild(currentAST, returnAST);
			sep();
			break;
		}
		case INDENT:
		case INTEGER:
		case ID:
		case URL:
		case STR_TQ_START:
		case STR_SQ_START:
		case REGEX_START:
		case FILE_PATH:
		case FILE_PATH_START:
		case LPAREN:
		case LBRACK:
		case LCURLY:
		case EMAIL:
		case TIME:
		case PERCENTAGE:
		case NLS:
		case FLOAT:
		case PAIR:
		case DATE:
		case PAIR_LCURLY:
		case WELL_LCURLY:
		case PLUS:
		case MINUS:
		case NOT:
		case DB_DOT:
		case TILDE:
		case RFC822:
		case K_NULL:
		case K_NEW:
		case K_TRUE:
		case K_FALSE:
		case IN:
		case K_IF:
		case K_FOR:
		case WHILE:
		case DO:
		case DEF:
		case K_CLASS:
		case K_TRY:
		case K_IMPORT:
		case K_PATTERN:
		case K_RETURN:
		case K_BREAK:
		case K_MATCH:
		case K_ASSERT:
		case K_THROW:
		case THIS:
		case SUPER:
		case IS:
		case REFRENCE_NAME:
		case IF:
		case FOR:
		case NEW:
		case MOR:
		case AT:
		case LITERAL_public:
		case LITERAL_private:
		case LITERAL_protected:
		case LITERAL_static:
		case LITERAL_abstract:
		case LITERAL_final:
		case STAR:
		case DB_DOT_LT:
		case GT_DB_DOT:
		case GT_DB_DOT_LT:
		case LNOT:
		case STRING:
		case K_FLASE:
		case ELSE:
		case SIGN_LPAREN:
		case SIGN_LCURLY:
		case REGEX:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		statements();
		sts_AST = (AST)returnAST;
		{
		switch ( LA(1)) {
		case NLS:
		case SEMI:
		{
			sep();
			break;
		}
		case EOF:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(Token.EOF_TYPE);
		if ( inputState.guessing==0 ) {
			compilationUnit_AST = (AST)currentAST.root;
			
			compilationUnit_AST = (AST)astFactory.make( (new ASTArray(3)).add(node(COMPILATION_UNIT,"COMPILATION_UNIT",first)).add(pkg_AST).add(sts_AST));
			
			currentAST.root = compilationUnit_AST;
			currentAST.child = compilationUnit_AST!=null &&compilationUnit_AST.getFirstChild()!=null ?
				compilationUnit_AST.getFirstChild() : compilationUnit_AST;
			currentAST.advanceChildToEnd();
		}
		compilationUnit_AST = (AST)currentAST.root;
		returnAST = compilationUnit_AST;
	}
	
	public final void nls() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST nls_AST = null;
		
		{
		if ((LA(1)==NLS) && (_tokenSet_0.member(LA(2)))) {
			match(NLS);
		}
		else if ((_tokenSet_0.member(LA(1))) && (_tokenSet_1.member(LA(2)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		returnAST = nls_AST;
	}
	
	public final void packageStatement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST packageStatement_AST = null;
		AST name_AST = null;
		
		Token first = LT(1);
		
		
		match(K_PACKAGE);
		idChain();
		name_AST = (AST)returnAST;
		astFactory.addASTChild(currentAST, returnAST);
		if ( inputState.guessing==0 ) {
			packageStatement_AST = (AST)currentAST.root;
			
			packageStatement_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(PACKAGE,"PACKAGE",first)).add(name_AST));
			
			currentAST.root = packageStatement_AST;
			currentAST.child = packageStatement_AST!=null &&packageStatement_AST.getFirstChild()!=null ?
				packageStatement_AST.getFirstChild() : packageStatement_AST;
			currentAST.advanceChildToEnd();
		}
		packageStatement_AST = (AST)currentAST.root;
		returnAST = packageStatement_AST;
	}
	
	public final void sep() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST sep_AST = null;
		
		switch ( LA(1)) {
		case SEMI:
		{
			match(SEMI);
			{
			_loop376:
			do {
				if ((LA(1)==NLS) && (_tokenSet_2.member(LA(2)))) {
					match(NLS);
				}
				else {
					break _loop376;
				}
				
			} while (true);
			}
			break;
		}
		case NLS:
		{
			match(NLS);
			nls();
			{
			_loop380:
			do {
				if ((LA(1)==SEMI) && (_tokenSet_2.member(LA(2)))) {
					match(SEMI);
					{
					_loop379:
					do {
						if ((LA(1)==NLS) && (_tokenSet_2.member(LA(2)))) {
							match(NLS);
						}
						else {
							break _loop379;
						}
						
					} while (true);
					}
				}
				else {
					break _loop380;
				}
				
			} while (true);
			}
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = sep_AST;
	}
	
	public final void statements() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST statements_AST = null;
		
		Token first = LT(1);
		
		
		statement();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop8:
		do {
			if ((LA(1)==NLS||LA(1)==SEMI) && (_tokenSet_2.member(LA(2)))) {
				sep();
				{
				if ((_tokenSet_3.member(LA(1))) && (_tokenSet_4.member(LA(2)))) {
					statement();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else if ((_tokenSet_5.member(LA(1))) && (_tokenSet_6.member(LA(2)))) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				
				}
			}
			else {
				break _loop8;
			}
			
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			statements_AST = (AST)currentAST.root;
			statements_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(STATEMENTS,"STATEMENTS",first)).add(statements_AST));
			currentAST.root = statements_AST;
			currentAST.child = statements_AST!=null &&statements_AST.getFirstChild()!=null ?
				statements_AST.getFirstChild() : statements_AST;
			currentAST.advanceChildToEnd();
		}
		statements_AST = (AST)currentAST.root;
		returnAST = statements_AST;
	}
	
	public final void statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST statement_AST = null;
		
		switch ( LA(1)) {
		case K_FOR:
		{
			forStatement();
			astFactory.addASTChild(currentAST, returnAST);
			statement_AST = (AST)currentAST.root;
			break;
		}
		case K_BREAK:
		{
			breakStatement();
			astFactory.addASTChild(currentAST, returnAST);
			statement_AST = (AST)currentAST.root;
			break;
		}
		case K_THROW:
		{
			throwStatement();
			astFactory.addASTChild(currentAST, returnAST);
			statement_AST = (AST)currentAST.root;
			break;
		}
		case K_RETURN:
		{
			returnStatement();
			astFactory.addASTChild(currentAST, returnAST);
			statement_AST = (AST)currentAST.root;
			break;
		}
		case K_TRY:
		{
			tryStatement();
			astFactory.addASTChild(currentAST, returnAST);
			statement_AST = (AST)currentAST.root;
			break;
		}
		case STAR:
		{
			starList();
			astFactory.addASTChild(currentAST, returnAST);
			statement_AST = (AST)currentAST.root;
			break;
		}
		case K_IMPORT:
		{
			importStatement();
			astFactory.addASTChild(currentAST, returnAST);
			statement_AST = (AST)currentAST.root;
			break;
		}
		default:
			boolean synPredMatched11 = false;
			if (((_tokenSet_7.member(LA(1))) && (_tokenSet_8.member(LA(2))))) {
				int _m11 = mark();
				synPredMatched11 = true;
				inputState.guessing++;
				try {
					{
					methodStart();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched11 = false;
				}
				rewind(_m11);
inputState.guessing--;
			}
			if ( synPredMatched11 ) {
				{
				methodDeclarationStatement();
				astFactory.addASTChild(currentAST, returnAST);
				}
				statement_AST = (AST)currentAST.root;
			}
			else {
				boolean synPredMatched14 = false;
				if (((_tokenSet_9.member(LA(1))) && (_tokenSet_10.member(LA(2))))) {
					int _m14 = mark();
					synPredMatched14 = true;
					inputState.guessing++;
					try {
						{
						annotationList();
						nls();
						match(K_CLASS);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched14 = false;
					}
					rewind(_m14);
inputState.guessing--;
				}
				if ( synPredMatched14 ) {
					{
					classDeclarationStatement();
					astFactory.addASTChild(currentAST, returnAST);
					}
					statement_AST = (AST)currentAST.root;
				}
				else if ((_tokenSet_7.member(LA(1))) && (_tokenSet_8.member(LA(2)))) {
					methodDeclarationStatement();
					astFactory.addASTChild(currentAST, returnAST);
					statement_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==SUPER) && (LA(2)==LPAREN)) {
					constructorCallStatement();
					astFactory.addASTChild(currentAST, returnAST);
					statement_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==K_ASSERT) && (_tokenSet_11.member(LA(2)))) {
					assertStatement();
					astFactory.addASTChild(currentAST, returnAST);
					statement_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==LCURLY) && (_tokenSet_12.member(LA(2)))) {
					hashMapConstructExpression();
					astFactory.addASTChild(currentAST, returnAST);
					statement_AST = (AST)currentAST.root;
				}
				else if ((_tokenSet_13.member(LA(1))) && (LA(2)==COLON||LA(2)==EQ_GT)) {
					yamlHashStatement();
					astFactory.addASTChild(currentAST, returnAST);
					statement_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==LCURLY||LA(1)==K_MATCH||LA(1)==MOR) && (_tokenSet_14.member(LA(2)))) {
					matchStatement();
					astFactory.addASTChild(currentAST, returnAST);
					statement_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==MOR) && (_tokenSet_15.member(LA(2)))) {
					matchBlock();
					astFactory.addASTChild(currentAST, returnAST);
					statement_AST = (AST)currentAST.root;
				}
				else if ((_tokenSet_9.member(LA(1))) && (_tokenSet_10.member(LA(2)))) {
					classDeclarationStatement();
					astFactory.addASTChild(currentAST, returnAST);
					statement_AST = (AST)currentAST.root;
				}
				else if ((_tokenSet_16.member(LA(1))) && (_tokenSet_17.member(LA(2)))) {
					expressionStatement();
					astFactory.addASTChild(currentAST, returnAST);
					statement_AST = (AST)currentAST.root;
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}}
			returnAST = statement_AST;
		}
		
	public final void methodStart() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST methodStart_AST = null;
		
		{
		_loop91:
		do {
			if ((LA(1)==AT)) {
				annotation();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop91;
			}
			
		} while (true);
		}
		nls();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop93:
		do {
			if (((LA(1) >= LITERAL_public && LA(1) <= LITERAL_final))) {
				modifier();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop93;
			}
			
		} while (true);
		}
		nls();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp10_AST = null;
		tmp10_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp10_AST);
		match(DEF);
		methodStart_AST = (AST)currentAST.root;
		returnAST = methodStart_AST;
	}
	
	public final void methodDeclarationStatement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST methodDeclarationStatement_AST = null;
		AST anns_AST = null;
		AST mds_AST = null;
		Token  mthName = null;
		AST mthName_AST = null;
		AST paramList1_AST = null;
		AST paramList2_AST = null;
		AST body_AST = null;
		
		AST ty = null;
		AST paramList = null;
		Token first = null;
		
		
		annotationList();
		anns_AST = (AST)returnAST;
		nls();
		if ( inputState.guessing==0 ) {
			
			first = LT(1);
			
		}
		modifiers();
		mds_AST = (AST)returnAST;
		nls();
		match(DEF);
		nls();
		mthName = LT(1);
		mthName_AST = astFactory.create(mthName);
		match(ID);
		nls();
		{
		switch ( LA(1)) {
		case LPAREN:
		{
			match(LPAREN);
			nls();
			{
			switch ( LA(1)) {
			case INTEGER:
			case ID:
			case URL:
			case FILE_PATH:
			case EMAIL:
			case TIME:
			case PERCENTAGE:
			case FLOAT:
			case PAIR:
			case DATE:
			case K_NULL:
			case K_TRUE:
			case K_FALSE:
			case REFRENCE_NAME:
			case STRING:
			case REGEX:
			{
				parameterDefinitionList();
				paramList1_AST = (AST)returnAST;
				nls();
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				
				paramList = paramList1_AST;
				
			}
			break;
		}
		case INTEGER:
		case ID:
		case URL:
		case FILE_PATH:
		case EMAIL:
		case TIME:
		case PERCENTAGE:
		case FLOAT:
		case PAIR:
		case DATE:
		case K_NULL:
		case K_TRUE:
		case K_FALSE:
		case REFRENCE_NAME:
		case STRING:
		case REGEX:
		{
			parameterDefinitionList();
			paramList2_AST = (AST)returnAST;
			nls();
			if ( inputState.guessing==0 ) {
				
				paramList = paramList2_AST;
				
			}
			break;
		}
		case LCURLY:
		case RARROW:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		methodBlockStatement();
		body_AST = (AST)returnAST;
		if ( inputState.guessing==0 ) {
			methodDeclarationStatement_AST = (AST)currentAST.root;
			
			methodDeclarationStatement_AST = (AST)astFactory.make( (new ASTArray(7)).add(node(METHOD_DEF,"METHOD_DEF",first,LT(1))).add(anns_AST).add(mds_AST).add(ty).add(mthName_AST).add(paramList).add(body_AST));
			
			currentAST.root = methodDeclarationStatement_AST;
			currentAST.child = methodDeclarationStatement_AST!=null &&methodDeclarationStatement_AST.getFirstChild()!=null ?
				methodDeclarationStatement_AST.getFirstChild() : methodDeclarationStatement_AST;
			currentAST.advanceChildToEnd();
		}
		returnAST = methodDeclarationStatement_AST;
	}
	
	public final void annotationList() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST annotationList_AST = null;
		
		Token first = LT(1);
		
		
		{
		switch ( LA(1)) {
		case AT:
		{
			annotation();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop102:
			do {
				if ((LA(1)==NLS||LA(1)==AT) && (LA(2)==ID||LA(2)==AT)) {
					nls();
					annotation();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop102;
				}
				
			} while (true);
			}
			break;
		}
		case ID:
		case NLS:
		case DEF:
		case K_CLASS:
		case K_PATTERN:
		case LITERAL_public:
		case LITERAL_private:
		case LITERAL_protected:
		case LITERAL_static:
		case LITERAL_abstract:
		case LITERAL_final:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		if ( inputState.guessing==0 ) {
			annotationList_AST = (AST)currentAST.root;
			
			annotationList_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(ANNOTATION_LIST,"ANNOTATION_LIST",first,LT(1))).add(annotationList_AST));
			
			currentAST.root = annotationList_AST;
			currentAST.child = annotationList_AST!=null &&annotationList_AST.getFirstChild()!=null ?
				annotationList_AST.getFirstChild() : annotationList_AST;
			currentAST.advanceChildToEnd();
		}
		annotationList_AST = (AST)currentAST.root;
		returnAST = annotationList_AST;
	}
	
	public final void classDeclarationStatement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST classDeclarationStatement_AST = null;
		AST anns_AST = null;
		Token  ptn = null;
		AST ptn_AST = null;
		Token  clsName = null;
		AST clsName_AST = null;
		AST extType_AST = null;
		AST mblk_AST = null;
		AST clss_AST = null;
		
		AST body = null;
		Token first = null;
		AST prefix = null;
		
		
		annotationList();
		anns_AST = (AST)returnAST;
		nls();
		if ( inputState.guessing==0 ) {
			
			first = LT(1);
			
		}
		{
		switch ( LA(1)) {
		case K_PATTERN:
		{
			ptn = LT(1);
			ptn_AST = astFactory.create(ptn);
			match(K_PATTERN);
			if ( inputState.guessing==0 ) {
				
				prefix = ptn_AST;
				
			}
			break;
		}
		case K_CLASS:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(K_CLASS);
		nls();
		clsName = LT(1);
		clsName_AST = astFactory.create(clsName);
		match(ID);
		{
		boolean synPredMatched60 = false;
		if (((LA(1)==NLS||LA(1)==K_EXTENDS) && (LA(2)==ID||LA(2)==NLS||LA(2)==K_EXTENDS))) {
			int _m60 = mark();
			synPredMatched60 = true;
			inputState.guessing++;
			try {
				{
				nls();
				match(K_EXTENDS);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched60 = false;
			}
			rewind(_m60);
inputState.guessing--;
		}
		if ( synPredMatched60 ) {
			nls();
			AST tmp15_AST = null;
			tmp15_AST = astFactory.create(LT(1));
			match(K_EXTENDS);
			nls();
			type();
			extType_AST = (AST)returnAST;
			if ( inputState.guessing==0 ) {
				
								extType_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(EXTENDS_TYPE,"EXTENDS_TYPE",first,LT(1))).add(extType_AST));
				
			}
		}
		else if ((_tokenSet_18.member(LA(1))) && (_tokenSet_19.member(LA(2)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		{
		boolean synPredMatched63 = false;
		if (((LA(1)==NLS||LA(1)==MOR) && (_tokenSet_20.member(LA(2))))) {
			int _m63 = mark();
			synPredMatched63 = true;
			inputState.guessing++;
			try {
				{
				nls();
				match(MOR);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched63 = false;
			}
			rewind(_m63);
inputState.guessing--;
		}
		if ( synPredMatched63 ) {
			nls();
			matchBlock();
			mblk_AST = (AST)returnAST;
		}
		else if ((_tokenSet_21.member(LA(1))) && (_tokenSet_19.member(LA(2)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		{
		boolean synPredMatched67 = false;
		if (((LA(1)==NLS) && (_tokenSet_22.member(LA(2))))) {
			int _m67 = mark();
			synPredMatched67 = true;
			inputState.guessing++;
			try {
				{
				match(NLS);
				{
				match(_tokenSet_23);
				}
				}
			}
			catch (RecognitionException pe) {
				synPredMatched67 = false;
			}
			rewind(_m67);
inputState.guessing--;
		}
		if ( synPredMatched67 ) {
			{
			match(NLS);
			classStatements();
			clss_AST = (AST)returnAST;
			}
		}
		else if ((_tokenSet_21.member(LA(1))) && (_tokenSet_19.member(LA(2)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		if ( inputState.guessing==0 ) {
			classDeclarationStatement_AST = (AST)currentAST.root;
			
			classDeclarationStatement_AST = (AST)astFactory.make( (new ASTArray(7)).add(node(CLASS,"CLASS",first,LT(1))).add(anns_AST).add(prefix).add(clsName_AST).add(extType_AST).add(mblk_AST).add(clss_AST));
			
			currentAST.root = classDeclarationStatement_AST;
			currentAST.child = classDeclarationStatement_AST!=null &&classDeclarationStatement_AST.getFirstChild()!=null ?
				classDeclarationStatement_AST.getFirstChild() : classDeclarationStatement_AST;
			currentAST.advanceChildToEnd();
		}
		returnAST = classDeclarationStatement_AST;
	}
	
	public final void constructorCallStatement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST constructorCallStatement_AST = null;
		AST al_AST = null;
		
		Token first = LT(1);
		
		
		match(SUPER);
		match(LPAREN);
		nls();
		{
		switch ( LA(1)) {
		case INTEGER:
		case ID:
		case URL:
		case STR_TQ_START:
		case STR_SQ_START:
		case REGEX_START:
		case FILE_PATH:
		case FILE_PATH_START:
		case LPAREN:
		case LBRACK:
		case LCURLY:
		case EMAIL:
		case TIME:
		case PERCENTAGE:
		case FLOAT:
		case PAIR:
		case DATE:
		case PAIR_LCURLY:
		case WELL_LCURLY:
		case PLUS:
		case MINUS:
		case NOT:
		case DB_DOT:
		case TILDE:
		case RFC822:
		case K_NULL:
		case K_NEW:
		case K_TRUE:
		case K_FALSE:
		case IN:
		case K_IF:
		case WHILE:
		case DO:
		case DEF:
		case K_CLASS:
		case K_MATCH:
		case K_ASSERT:
		case THIS:
		case SUPER:
		case IS:
		case REFRENCE_NAME:
		case IF:
		case FOR:
		case NEW:
		case DB_DOT_LT:
		case GT_DB_DOT:
		case GT_DB_DOT_LT:
		case LNOT:
		case STRING:
		case K_FLASE:
		case ELSE:
		case SIGN_LPAREN:
		case SIGN_LCURLY:
		case REGEX:
		{
			argList();
			al_AST = (AST)returnAST;
			break;
		}
		case RPAREN:
		case NLS:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		nls();
		match(RPAREN);
		if ( inputState.guessing==0 ) {
			constructorCallStatement_AST = (AST)currentAST.root;
			
			constructorCallStatement_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(SUPER_COTR_CALL,"SUPER_COTR_CALL",first,LT(1))).add(al_AST));
			
			currentAST.root = constructorCallStatement_AST;
			currentAST.child = constructorCallStatement_AST!=null &&constructorCallStatement_AST.getFirstChild()!=null ?
				constructorCallStatement_AST.getFirstChild() : constructorCallStatement_AST;
			currentAST.advanceChildToEnd();
		}
		returnAST = constructorCallStatement_AST;
	}
	
	public final void forStatement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST forStatement_AST = null;
		AST ps1_AST = null;
		AST ps2_AST = null;
		AST ps3_AST = null;
		AST s1_AST = null;
		AST s2_AST = null;
		AST s3_AST = null;
		AST body_AST = null;
		
		AST sts1 = null;
		AST sts2 = null;
		AST sts3 = null;
		Token first = LT(1);
		
		
		match(K_FOR);
		{
		if ((LA(1)==LPAREN||LA(1)==LCURLY||LA(1)==NLS) && (_tokenSet_24.member(LA(2)))) {
			{
			switch ( LA(1)) {
			case LPAREN:
			{
				match(LPAREN);
				nls();
				{
				if ((_tokenSet_3.member(LA(1))) && (_tokenSet_25.member(LA(2)))) {
					statement();
					ps1_AST = (AST)returnAST;
				}
				else if ((LA(1)==RPAREN||LA(1)==NLS||LA(1)==SEMI) && (_tokenSet_26.member(LA(2)))) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				
				}
				nls();
				{
				if ((LA(1)==RPAREN||LA(1)==SEMI) && (_tokenSet_26.member(LA(2)))) {
					{
					switch ( LA(1)) {
					case SEMI:
					{
						match(SEMI);
						nls();
						{
						if ((_tokenSet_3.member(LA(1))) && (_tokenSet_25.member(LA(2)))) {
							statement();
							ps2_AST = (AST)returnAST;
						}
						else if ((LA(1)==RPAREN||LA(1)==NLS||LA(1)==SEMI) && (_tokenSet_26.member(LA(2)))) {
						}
						else {
							throw new NoViableAltException(LT(1), getFilename());
						}
						
						}
						nls();
						{
						switch ( LA(1)) {
						case SEMI:
						{
							match(SEMI);
							nls();
							{
							if ((_tokenSet_3.member(LA(1))) && (_tokenSet_25.member(LA(2)))) {
								statement();
								ps3_AST = (AST)returnAST;
							}
							else if ((LA(1)==RPAREN||LA(1)==NLS) && (LA(2)==RPAREN||LA(2)==LCURLY||LA(2)==NLS)) {
							}
							else {
								throw new NoViableAltException(LT(1), getFilename());
							}
							
							}
							nls();
							break;
						}
						case RPAREN:
						{
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
						break;
					}
					case RPAREN:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
				}
				else if ((LA(1)==RPAREN) && (LA(2)==LCURLY||LA(2)==NLS)) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				
				}
				match(RPAREN);
				if ( inputState.guessing==0 ) {
					
					sts1 = ps1_AST;
					sts2 = ps2_AST;
					sts3 = ps3_AST;
					
				}
				break;
			}
			case LCURLY:
			case NLS:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		else if ((_tokenSet_27.member(LA(1))) && (_tokenSet_25.member(LA(2)))) {
			{
			if ((_tokenSet_27.member(LA(1))) && (_tokenSet_25.member(LA(2)))) {
				{
				if ((_tokenSet_3.member(LA(1))) && (_tokenSet_25.member(LA(2)))) {
					statement();
					s1_AST = (AST)returnAST;
				}
				else if ((LA(1)==LCURLY||LA(1)==NLS||LA(1)==SEMI) && (_tokenSet_28.member(LA(2)))) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				
				}
				nls();
				{
				if ((LA(1)==LCURLY||LA(1)==NLS||LA(1)==SEMI) && (_tokenSet_28.member(LA(2)))) {
					{
					switch ( LA(1)) {
					case SEMI:
					{
						match(SEMI);
						nls();
						{
						if ((_tokenSet_3.member(LA(1))) && (_tokenSet_25.member(LA(2)))) {
							statement();
							s2_AST = (AST)returnAST;
						}
						else if ((LA(1)==LCURLY||LA(1)==NLS||LA(1)==SEMI) && (_tokenSet_28.member(LA(2)))) {
						}
						else {
							throw new NoViableAltException(LT(1), getFilename());
						}
						
						}
						nls();
						{
						switch ( LA(1)) {
						case SEMI:
						{
							match(SEMI);
							nls();
							{
							if ((_tokenSet_3.member(LA(1))) && (_tokenSet_25.member(LA(2)))) {
								statement();
								s3_AST = (AST)returnAST;
							}
							else if ((LA(1)==LCURLY||LA(1)==NLS) && (_tokenSet_29.member(LA(2)))) {
							}
							else {
								throw new NoViableAltException(LT(1), getFilename());
							}
							
							}
							nls();
							break;
						}
						case LCURLY:
						case NLS:
						{
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
						break;
					}
					case LCURLY:
					case NLS:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
				}
				else if ((LA(1)==LCURLY||LA(1)==NLS) && (_tokenSet_29.member(LA(2)))) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				
				}
				if ( inputState.guessing==0 ) {
					
					sts1 = s1_AST;
					sts2 = s2_AST;
					sts3 = s3_AST;
					
				}
			}
			else if ((LA(1)==LCURLY||LA(1)==NLS) && (_tokenSet_29.member(LA(2)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		nls();
		openBlockStatement();
		body_AST = (AST)returnAST;
		if ( inputState.guessing==0 ) {
			forStatement_AST = (AST)currentAST.root;
			
			forStatement_AST = (AST)astFactory.make( (new ASTArray(5)).add(node(FOR,"FOR",first,LT(1))).add(sts1).add(sts2).add(sts3).add(body_AST));
			
			currentAST.root = forStatement_AST;
			currentAST.child = forStatement_AST!=null &&forStatement_AST.getFirstChild()!=null ?
				forStatement_AST.getFirstChild() : forStatement_AST;
			currentAST.advanceChildToEnd();
		}
		forStatement_AST = (AST)currentAST.root;
		returnAST = forStatement_AST;
	}
	
	public final void breakStatement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST breakStatement_AST = null;
		
		AST tmp27_AST = null;
		tmp27_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp27_AST);
		match(K_BREAK);
		{
		if ((_tokenSet_15.member(LA(1))) && (_tokenSet_17.member(LA(2)))) {
			expression();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((_tokenSet_21.member(LA(1))) && (_tokenSet_19.member(LA(2)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		breakStatement_AST = (AST)currentAST.root;
		returnAST = breakStatement_AST;
	}
	
	public final void assertStatement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST assertStatement_AST = null;
		AST expr1_AST = null;
		AST expr2_AST = null;
		
		Token first = LT(1);
		
		
		match(K_ASSERT);
		assignmentValueExpression();
		expr1_AST = (AST)returnAST;
		{
		if ((LA(1)==COLON||LA(1)==COMMAR) && (_tokenSet_15.member(LA(2)))) {
			{
			switch ( LA(1)) {
			case COMMAR:
			{
				match(COMMAR);
				break;
			}
			case COLON:
			{
				match(COLON);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			expression();
			expr2_AST = (AST)returnAST;
		}
		else if ((_tokenSet_21.member(LA(1))) && (_tokenSet_19.member(LA(2)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		if ( inputState.guessing==0 ) {
			assertStatement_AST = (AST)currentAST.root;
			
				        assertStatement_AST = (AST)astFactory.make( (new ASTArray(3)).add(node(ASSERT,"ASSERT",first,LT(1))).add(expr1_AST).add(expr2_AST));
				
			currentAST.root = assertStatement_AST;
			currentAST.child = assertStatement_AST!=null &&assertStatement_AST.getFirstChild()!=null ?
				assertStatement_AST.getFirstChild() : assertStatement_AST;
			currentAST.advanceChildToEnd();
		}
		returnAST = assertStatement_AST;
	}
	
	public final void throwStatement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST throwStatement_AST = null;
		AST expr_AST = null;
		
		Token first = LT(1);
		
		
		match(K_THROW);
		nls();
		expression();
		expr_AST = (AST)returnAST;
		astFactory.addASTChild(currentAST, returnAST);
		if ( inputState.guessing==0 ) {
			throwStatement_AST = (AST)currentAST.root;
			
			throwStatement_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(THROW,"throw",first,LT(1))).add(expr_AST));
			
			currentAST.root = throwStatement_AST;
			currentAST.child = throwStatement_AST!=null &&throwStatement_AST.getFirstChild()!=null ?
				throwStatement_AST.getFirstChild() : throwStatement_AST;
			currentAST.advanceChildToEnd();
		}
		throwStatement_AST = (AST)currentAST.root;
		returnAST = throwStatement_AST;
	}
	
	public final void returnStatement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST returnStatement_AST = null;
		
		AST tmp32_AST = null;
		tmp32_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp32_AST);
		match(K_RETURN);
		{
		if ((_tokenSet_15.member(LA(1))) && (_tokenSet_17.member(LA(2)))) {
			expression();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((_tokenSet_21.member(LA(1))) && (_tokenSet_19.member(LA(2)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		returnStatement_AST = (AST)currentAST.root;
		returnAST = returnStatement_AST;
	}
	
	public final void tryStatement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST tryStatement_AST = null;
		AST body_AST = null;
		AST catchList_AST = null;
		
		Token first = LT(1);
		
		
		AST tmp33_AST = null;
		tmp33_AST = astFactory.create(LT(1));
		match(K_TRY);
		nls();
		openBlockStatement();
		body_AST = (AST)returnAST;
		nls();
		catchListStatement();
		catchList_AST = (AST)returnAST;
		if ( inputState.guessing==0 ) {
			tryStatement_AST = (AST)currentAST.root;
			
			tryStatement_AST = (AST)astFactory.make( (new ASTArray(3)).add(node(TRY,"TRY",first,LT(1))).add(body_AST).add(catchList_AST));
			
			currentAST.root = tryStatement_AST;
			currentAST.child = tryStatement_AST!=null &&tryStatement_AST.getFirstChild()!=null ?
				tryStatement_AST.getFirstChild() : tryStatement_AST;
			currentAST.advanceChildToEnd();
		}
		returnAST = tryStatement_AST;
	}
	
	public final void hashMapConstructExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST hashMapConstructExpression_AST = null;
		AST lst_AST = null;
		
		Token first = LT(1);
		
		
		match(LCURLY);
		nls();
		{
		switch ( LA(1)) {
		case INTEGER:
		case ID:
		case URL:
		case EMAIL:
		case PERCENTAGE:
		case FLOAT:
		case NOT:
		case RFC822:
		case K_NULL:
		case K_TRUE:
		case IN:
		case WHILE:
		case DO:
		case DEF:
		case K_CLASS:
		case K_MATCH:
		case K_ASSERT:
		case THIS:
		case SUPER:
		case IS:
		case IF:
		case FOR:
		case NEW:
		case STRING:
		case K_FLASE:
		case ELSE:
		{
			hashMapEntryList();
			lst_AST = (AST)returnAST;
			nls();
			break;
		}
		case RCURLY:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(RCURLY);
		if ( inputState.guessing==0 ) {
			hashMapConstructExpression_AST = (AST)currentAST.root;
			
					    if (lst_AST != null) {
					       hashMapConstructExpression_AST = lst_AST;
					    }
					    else {
					       hashMapConstructExpression_AST = (AST)astFactory.make( (new ASTArray(1)).add(node(HASH,"HASH",first,LT(1))));
					    }
					
			currentAST.root = hashMapConstructExpression_AST;
			currentAST.child = hashMapConstructExpression_AST!=null &&hashMapConstructExpression_AST.getFirstChild()!=null ?
				hashMapConstructExpression_AST.getFirstChild() : hashMapConstructExpression_AST;
			currentAST.advanceChildToEnd();
		}
		returnAST = hashMapConstructExpression_AST;
	}
	
	public final void yamlHashStatement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST yamlHashStatement_AST = null;
		AST h0_AST = null;
		AST h1_AST = null;
		
		Token first = LT(1);
		
		
		hashMapEntry();
		h0_AST = (AST)returnAST;
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop34:
		do {
			boolean synPredMatched31 = false;
			if (((LA(1)==NLS||LA(1)==COMMAR) && (_tokenSet_30.member(LA(2))))) {
				int _m31 = mark();
				synPredMatched31 = true;
				inputState.guessing++;
				try {
					{
					{
					switch ( LA(1)) {
					case NLS:
					{
						match(NLS);
						break;
					}
					case COMMAR:
					{
						match(COMMAR);
						nls();
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					match(ID);
					{
					switch ( LA(1)) {
					case COLON:
					{
						match(COLON);
						break;
					}
					case EQ_GT:
					{
						match(EQ_GT);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					}
				}
				catch (RecognitionException pe) {
					synPredMatched31 = false;
				}
				rewind(_m31);
inputState.guessing--;
			}
			if ( synPredMatched31 ) {
				{
				{
				switch ( LA(1)) {
				case NLS:
				{
					match(NLS);
					break;
				}
				case COMMAR:
				{
					match(COMMAR);
					nls();
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				hashMapEntry();
				h1_AST = (AST)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
				if ( inputState.guessing==0 ) {
					
										//System.out.println("YAML match entry: " + #h1.toStringTree());
									
				}
				}
			}
			else {
				break _loop34;
			}
			
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			yamlHashStatement_AST = (AST)currentAST.root;
			
						yamlHashStatement_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(HASH,"HASH",first,LT(1))).add(yamlHashStatement_AST));
					
			currentAST.root = yamlHashStatement_AST;
			currentAST.child = yamlHashStatement_AST!=null &&yamlHashStatement_AST.getFirstChild()!=null ?
				yamlHashStatement_AST.getFirstChild() : yamlHashStatement_AST;
			currentAST.advanceChildToEnd();
		}
		yamlHashStatement_AST = (AST)currentAST.root;
		returnAST = yamlHashStatement_AST;
	}
	
	public final void starList() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST starList_AST = null;
		
		Token first = LT(1);
		
		
		starListItem();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop340:
		do {
			boolean synPredMatched339 = false;
			if (((LA(1)==NLS) && (LA(2)==STAR))) {
				int _m339 = mark();
				synPredMatched339 = true;
				inputState.guessing++;
				try {
					{
					match(NLS);
					match(STAR);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched339 = false;
				}
				rewind(_m339);
inputState.guessing--;
			}
			if ( synPredMatched339 ) {
				match(NLS);
				starListItem();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop340;
			}
			
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			starList_AST = (AST)currentAST.root;
			
						starList_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(LIST,"LIST",first,LT(1))).add(starList_AST));
					
			currentAST.root = starList_AST;
			currentAST.child = starList_AST!=null &&starList_AST.getFirstChild()!=null ?
				starList_AST.getFirstChild() : starList_AST;
			currentAST.advanceChildToEnd();
		}
		starList_AST = (AST)currentAST.root;
		returnAST = starList_AST;
	}
	
	public final void matchStatement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST matchStatement_AST = null;
		AST cd1_AST = null;
		AST cd2_AST = null;
		AST block_AST = null;
		
		{
		switch ( LA(1)) {
		case K_MATCH:
		{
			AST tmp39_AST = null;
			tmp39_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp39_AST);
			match(K_MATCH);
			{
			if ((LA(1)==LPAREN) && (_tokenSet_15.member(LA(2)))) {
				match(LPAREN);
				expression();
				cd1_AST = (AST)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
				match(RPAREN);
			}
			else if ((_tokenSet_15.member(LA(1))) && (_tokenSet_31.member(LA(2)))) {
				expression();
				cd2_AST = (AST)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			nls();
			break;
		}
		case LCURLY:
		case MOR:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		{
		switch ( LA(1)) {
		case LCURLY:
		{
			{
			match(LCURLY);
			nls();
			matchBlock();
			block_AST = (AST)returnAST;
			astFactory.addASTChild(currentAST, returnAST);
			nls();
			match(RCURLY);
			}
			break;
		}
		case MOR:
		{
			{
			matchBlock();
			astFactory.addASTChild(currentAST, returnAST);
			}
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		matchStatement_AST = (AST)currentAST.root;
		returnAST = matchStatement_AST;
	}
	
	public final void matchBlock() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST matchBlock_AST = null;
		AST head_AST = null;
		AST rest_AST = null;
		
		AST elseItem = null;
		Token first = LT(1);
		
		
		matchItem();
		head_AST = (AST)returnAST;
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop48:
		do {
			boolean synPredMatched46 = false;
			if (((LA(1)==NLS) && (LA(2)==MOR))) {
				int _m46 = mark();
				synPredMatched46 = true;
				inputState.guessing++;
				try {
					{
					match(NLS);
					match(MOR);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched46 = false;
				}
				rewind(_m46);
inputState.guessing--;
			}
			if ( synPredMatched46 ) {
				{
				match(NLS);
				matchItem();
				rest_AST = (AST)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
				}
			}
			else {
				break _loop48;
			}
			
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			matchBlock_AST = (AST)currentAST.root;
			
			matchBlock_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(MATCH_BLOCK,"MATCH_BLOCK",first,LT(1))).add(matchBlock_AST));
				  	
			currentAST.root = matchBlock_AST;
			currentAST.child = matchBlock_AST!=null &&matchBlock_AST.getFirstChild()!=null ?
				matchBlock_AST.getFirstChild() : matchBlock_AST;
			currentAST.advanceChildToEnd();
		}
		matchBlock_AST = (AST)currentAST.root;
		returnAST = matchBlock_AST;
	}
	
	public final void importStatement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST importStatement_AST = null;
		AST name_AST = null;
		
		Token first = LT(1);
		
		
		match(K_IMPORT);
		idChainStar();
		name_AST = (AST)returnAST;
		astFactory.addASTChild(currentAST, returnAST);
		if ( inputState.guessing==0 ) {
			importStatement_AST = (AST)currentAST.root;
			
			importStatement_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(IMPORT,"IMPORT",first)).add(name_AST));
			
			currentAST.root = importStatement_AST;
			currentAST.child = importStatement_AST!=null &&importStatement_AST.getFirstChild()!=null ?
				importStatement_AST.getFirstChild() : importStatement_AST;
			currentAST.advanceChildToEnd();
		}
		importStatement_AST = (AST)currentAST.root;
		returnAST = importStatement_AST;
	}
	
	public final void expressionStatement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expressionStatement_AST = null;
		AST exp_AST = null;
		
		Token first = LT(1);
		
		
		switch ( LA(1)) {
		case INDENT:
		{
			match(INDENT);
			hashMapWithoutBoundary();
			astFactory.addASTChild(currentAST, returnAST);
			expressionStatement_AST = (AST)currentAST.root;
			break;
		}
		case INTEGER:
		case ID:
		case URL:
		case STR_TQ_START:
		case STR_SQ_START:
		case REGEX_START:
		case FILE_PATH:
		case FILE_PATH_START:
		case LPAREN:
		case LBRACK:
		case LCURLY:
		case EMAIL:
		case TIME:
		case PERCENTAGE:
		case FLOAT:
		case PAIR:
		case DATE:
		case PAIR_LCURLY:
		case WELL_LCURLY:
		case PLUS:
		case MINUS:
		case NOT:
		case DB_DOT:
		case TILDE:
		case RFC822:
		case K_NULL:
		case K_NEW:
		case K_TRUE:
		case K_FALSE:
		case IN:
		case K_IF:
		case WHILE:
		case DO:
		case DEF:
		case K_CLASS:
		case K_MATCH:
		case K_ASSERT:
		case THIS:
		case SUPER:
		case IS:
		case REFRENCE_NAME:
		case IF:
		case FOR:
		case NEW:
		case DB_DOT_LT:
		case GT_DB_DOT:
		case GT_DB_DOT_LT:
		case LNOT:
		case STRING:
		case K_FLASE:
		case ELSE:
		case SIGN_LPAREN:
		case SIGN_LCURLY:
		case REGEX:
		{
			expression();
			exp_AST = (AST)returnAST;
			astFactory.addASTChild(currentAST, returnAST);
			expressionStatement_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = expressionStatement_AST;
	}
	
	public final void idChain() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST idChain_AST = null;
		Token  id1 = null;
		AST id1_AST = null;
		Token  dot = null;
		AST dot_AST = null;
		Token  id2 = null;
		AST id2_AST = null;
		
		Token first = LT(1);
		
		
		id1 = LT(1);
		id1_AST = astFactory.create(id1);
		match(ID);
		{
		_loop355:
		do {
			if ((LA(1)==DOT)) {
				dot = LT(1);
				dot_AST = astFactory.create(dot);
				match(DOT);
				nls();
				id2 = LT(1);
				id2_AST = astFactory.create(id2);
				match(ID);
				if ( inputState.guessing==0 ) {
					id1_AST = (AST)astFactory.make( (new ASTArray(3)).add(node(DOT,".",first,LT(1))).add(id1_AST).add(id2_AST));
				}
			}
			else {
				break _loop355;
			}
			
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			idChain_AST = (AST)currentAST.root;
			idChain_AST = id1_AST;
			currentAST.root = idChain_AST;
			currentAST.child = idChain_AST!=null &&idChain_AST.getFirstChild()!=null ?
				idChain_AST.getFirstChild() : idChain_AST;
			currentAST.advanceChildToEnd();
		}
		returnAST = idChain_AST;
	}
	
	public final void idChainStar() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST idChainStar_AST = null;
		Token  id1 = null;
		AST id1_AST = null;
		Token  dot = null;
		AST dot_AST = null;
		Token  id2 = null;
		AST id2_AST = null;
		Token  dot2 = null;
		AST dot2_AST = null;
		Token  s = null;
		AST s_AST = null;
		Token  dot3 = null;
		AST dot3_AST = null;
		Token  as = null;
		AST as_AST = null;
		
		Token first = LT(1);
		
		
		id1 = LT(1);
		id1_AST = astFactory.create(id1);
		match(ID);
		{
		_loop358:
		do {
			if ((LA(1)==DOT) && (LA(2)==ID||LA(2)==NLS)) {
				dot = LT(1);
				dot_AST = astFactory.create(dot);
				match(DOT);
				nls();
				id2 = LT(1);
				id2_AST = astFactory.create(id2);
				match(ID);
				if ( inputState.guessing==0 ) {
					id1_AST = (AST)astFactory.make( (new ASTArray(3)).add(node(DOT,".",first,LT(1))).add(id1_AST).add(id2_AST));
				}
			}
			else {
				break _loop358;
			}
			
		} while (true);
		}
		{
		if ((LA(1)==DOT) && (LA(2)==NLS||LA(2)==STAR)) {
			dot2 = LT(1);
			dot2_AST = astFactory.create(dot2);
			match(DOT);
			nls();
			s = LT(1);
			s_AST = astFactory.create(s);
			match(STAR);
			if ( inputState.guessing==0 ) {
				id1_AST = (AST)astFactory.make( (new ASTArray(3)).add(node(DOT,".",first,LT(1))).add(id1_AST).add(s_AST));
			}
		}
		else if ((LA(1)==DOT) && (LA(2)==NLS||LA(2)==K_AS)) {
			dot3 = LT(1);
			dot3_AST = astFactory.create(dot3);
			match(DOT);
			nls();
			as = LT(1);
			as_AST = astFactory.create(as);
			match(K_AS);
			if ( inputState.guessing==0 ) {
				id1_AST = (AST)astFactory.make( (new ASTArray(3)).add(node(DOT,".",first,LT(1))).add(id1_AST).add(as_AST));
			}
		}
		else if ((_tokenSet_21.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		if ( inputState.guessing==0 ) {
			idChainStar_AST = (AST)currentAST.root;
			idChainStar_AST = id1_AST;
			currentAST.root = idChainStar_AST;
			currentAST.child = idChainStar_AST!=null &&idChainStar_AST.getFirstChild()!=null ?
				idChainStar_AST.getFirstChild() : idChainStar_AST;
			currentAST.advanceChildToEnd();
		}
		returnAST = idChainStar_AST;
	}
	
	public final void expression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_AST = null;
		
		assignmentExpression();
		astFactory.addASTChild(currentAST, returnAST);
		expression_AST = (AST)currentAST.root;
		returnAST = expression_AST;
	}
	
	public final void assignmentValueExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST assignmentValueExpression_AST = null;
		
		switch ( LA(1)) {
		case K_FOR:
		{
			forStatement();
			astFactory.addASTChild(currentAST, returnAST);
			assignmentValueExpression_AST = (AST)currentAST.root;
			break;
		}
		case STAR:
		{
			starList();
			astFactory.addASTChild(currentAST, returnAST);
			assignmentValueExpression_AST = (AST)currentAST.root;
			break;
		}
		default:
			boolean synPredMatched181 = false;
			if (((LA(1)==LCURLY||LA(1)==K_MATCH||LA(1)==MOR) && (_tokenSet_14.member(LA(2))))) {
				int _m181 = mark();
				synPredMatched181 = true;
				inputState.guessing++;
				try {
					{
					match(K_MATCH);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched181 = false;
				}
				rewind(_m181);
inputState.guessing--;
			}
			if ( synPredMatched181 ) {
				matchStatement();
				astFactory.addASTChild(currentAST, returnAST);
				assignmentValueExpression_AST = (AST)currentAST.root;
			}
			else if ((_tokenSet_16.member(LA(1))) && (_tokenSet_17.member(LA(2)))) {
				expressionStatement();
				astFactory.addASTChild(currentAST, returnAST);
				assignmentValueExpression_AST = (AST)currentAST.root;
			}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = assignmentValueExpression_AST;
	}
	
	public final void hashMapEntry() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST hashMapEntry_AST = null;
		AST key_AST = null;
		AST t_AST = null;
		AST value_AST = null;
		
		Token first = LT(1);
		
		
		hashMapKey();
		key_AST = (AST)returnAST;
		hashMapSeperator();
		t_AST = (AST)returnAST;
		nls();
		hashMapValue();
		value_AST = (AST)returnAST;
		if ( inputState.guessing==0 ) {
			hashMapEntry_AST = (AST)currentAST.root;
			
						hashMapEntry_AST = (AST)astFactory.make( (new ASTArray(4)).add(node(HASH_ENTRY,"HASH_ENTRY",first,LT(1))).add(t_AST).add(key_AST).add(value_AST));
					
			currentAST.root = hashMapEntry_AST;
			currentAST.child = hashMapEntry_AST!=null &&hashMapEntry_AST.getFirstChild()!=null ?
				hashMapEntry_AST.getFirstChild() : hashMapEntry_AST;
			currentAST.advanceChildToEnd();
		}
		returnAST = hashMapEntry_AST;
	}
	
	public final void matchItem() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST matchItem_AST = null;
		AST exp_AST = null;
		AST blk1_AST = null;
		AST blk2_AST = null;
		
		Token first = LT(1);
		AST blk = null;
		
		
		match(MOR);
		expression();
		exp_AST = (AST)returnAST;
		{
		boolean synPredMatched52 = false;
		if (((LA(1)==RARROW) && (LA(2)==LCURLY||LA(2)==NLS))) {
			int _m52 = mark();
			synPredMatched52 = true;
			inputState.guessing++;
			try {
				{
				match(RARROW);
				nls();
				match(LCURLY);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched52 = false;
			}
			rewind(_m52);
inputState.guessing--;
		}
		if ( synPredMatched52 ) {
			{
			match(RARROW);
			nls();
			curlyBlockStatement();
			blk1_AST = (AST)returnAST;
			if ( inputState.guessing==0 ) {
				blk = blk1_AST;
			}
			}
		}
		else if ((LA(1)==RARROW) && (_tokenSet_3.member(LA(2)))) {
			closureBlockStatement();
			blk2_AST = (AST)returnAST;
			if ( inputState.guessing==0 ) {
				blk = blk2_AST;
			}
		}
		else if ((_tokenSet_21.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		if ( inputState.guessing==0 ) {
			matchItem_AST = (AST)currentAST.root;
			
			matchItem_AST = (AST)astFactory.make( (new ASTArray(3)).add(node(MATCH_ITEM,"MATCH_ITEM",first,LT(1))).add(exp_AST).add(blk));
				
			currentAST.root = matchItem_AST;
			currentAST.child = matchItem_AST!=null &&matchItem_AST.getFirstChild()!=null ?
				matchItem_AST.getFirstChild() : matchItem_AST;
			currentAST.advanceChildToEnd();
		}
		returnAST = matchItem_AST;
	}
	
	public final void curlyBlockStatement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST curlyBlockStatement_AST = null;
		AST sts_AST = null;
		
		Token first = LT(1);
		
		
		match(LCURLY);
		nls();
		{
		switch ( LA(1)) {
		case INDENT:
		case INTEGER:
		case ID:
		case URL:
		case STR_TQ_START:
		case STR_SQ_START:
		case REGEX_START:
		case FILE_PATH:
		case FILE_PATH_START:
		case LPAREN:
		case LBRACK:
		case LCURLY:
		case EMAIL:
		case TIME:
		case PERCENTAGE:
		case NLS:
		case FLOAT:
		case PAIR:
		case DATE:
		case PAIR_LCURLY:
		case WELL_LCURLY:
		case PLUS:
		case MINUS:
		case NOT:
		case DB_DOT:
		case TILDE:
		case RFC822:
		case K_NULL:
		case K_NEW:
		case K_TRUE:
		case K_FALSE:
		case IN:
		case K_IF:
		case K_FOR:
		case WHILE:
		case DO:
		case DEF:
		case K_CLASS:
		case K_TRY:
		case K_IMPORT:
		case K_PATTERN:
		case K_RETURN:
		case K_BREAK:
		case K_MATCH:
		case K_ASSERT:
		case K_THROW:
		case THIS:
		case SUPER:
		case IS:
		case REFRENCE_NAME:
		case IF:
		case FOR:
		case NEW:
		case MOR:
		case AT:
		case LITERAL_public:
		case LITERAL_private:
		case LITERAL_protected:
		case LITERAL_static:
		case LITERAL_abstract:
		case LITERAL_final:
		case STAR:
		case DB_DOT_LT:
		case GT_DB_DOT:
		case GT_DB_DOT_LT:
		case LNOT:
		case STRING:
		case K_FLASE:
		case ELSE:
		case SIGN_LPAREN:
		case SIGN_LCURLY:
		case REGEX:
		{
			statements();
			sts_AST = (AST)returnAST;
			astFactory.addASTChild(currentAST, returnAST);
			nls();
			break;
		}
		case RCURLY:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(RCURLY);
		if ( inputState.guessing==0 ) {
			curlyBlockStatement_AST = (AST)currentAST.root;
			
			curlyBlockStatement_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(BLOCK,"BLOCK",first,LT(1))).add(sts_AST));
			
			currentAST.root = curlyBlockStatement_AST;
			currentAST.child = curlyBlockStatement_AST!=null &&curlyBlockStatement_AST.getFirstChild()!=null ?
				curlyBlockStatement_AST.getFirstChild() : curlyBlockStatement_AST;
			currentAST.advanceChildToEnd();
		}
		curlyBlockStatement_AST = (AST)currentAST.root;
		returnAST = curlyBlockStatement_AST;
	}
	
	public final void closureBlockStatement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST closureBlockStatement_AST = null;
		AST sts_AST = null;
		AST st_AST = null;
		
		AST blockStat = null;
		Token first = LT(1);
		
		
		match(RARROW);
		nls();
		{
		if ((LA(1)==INDENT) && (LA(2)==NLS)) {
			match(INDENT);
			match(NLS);
			statements();
			sts_AST = (AST)returnAST;
			astFactory.addASTChild(currentAST, returnAST);
			match(DEDENT);
			if ( inputState.guessing==0 ) {
				
				blockStat = sts_AST;
				
			}
		}
		else if ((_tokenSet_3.member(LA(1))) && (_tokenSet_17.member(LA(2)))) {
			statement();
			st_AST = (AST)returnAST;
			if ( inputState.guessing==0 ) {
				
				blockStat = st_AST;
				
			}
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		if ( inputState.guessing==0 ) {
			closureBlockStatement_AST = (AST)currentAST.root;
			
			closureBlockStatement_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(BLOCK,"BLOCK",first,LT(1))).add(blockStat));
			
			currentAST.root = closureBlockStatement_AST;
			currentAST.child = closureBlockStatement_AST!=null &&closureBlockStatement_AST.getFirstChild()!=null ?
				closureBlockStatement_AST.getFirstChild() : closureBlockStatement_AST;
			currentAST.advanceChildToEnd();
		}
		closureBlockStatement_AST = (AST)currentAST.root;
		returnAST = closureBlockStatement_AST;
	}
	
	public final void argList() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST argList_AST = null;
		
		Token first = LT(1);
		
		
		argument();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop348:
		do {
			if ((LA(1)==COMMAR)) {
				match(COMMAR);
				nls();
				argument();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop348;
			}
			
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			argList_AST = (AST)currentAST.root;
			
			argList_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(ARG_LIST,"ARG_LIST",first,LT(1))).add(argList_AST));
			
			currentAST.root = argList_AST;
			currentAST.child = argList_AST!=null &&argList_AST.getFirstChild()!=null ?
				argList_AST.getFirstChild() : argList_AST;
			currentAST.advanceChildToEnd();
		}
		argList_AST = (AST)currentAST.root;
		returnAST = argList_AST;
	}
	
	public final void type() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST type_AST = null;
		
		typeName();
		astFactory.addASTChild(currentAST, returnAST);
		type_AST = (AST)currentAST.root;
		returnAST = type_AST;
	}
	
	public final void classStatements() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST classStatements_AST = null;
		
		Token first = LT(1);
		
		
		classStatement();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop76:
		do {
			boolean synPredMatched74 = false;
			if (((LA(1)==NLS) && (_tokenSet_22.member(LA(2))))) {
				int _m74 = mark();
				synPredMatched74 = true;
				inputState.guessing++;
				try {
					{
					match(NLS);
					{
					match(_tokenSet_23);
					}
					}
				}
				catch (RecognitionException pe) {
					synPredMatched74 = false;
				}
				rewind(_m74);
inputState.guessing--;
			}
			if ( synPredMatched74 ) {
				{
				match(NLS);
				classStatement();
				astFactory.addASTChild(currentAST, returnAST);
				}
			}
			else {
				break _loop76;
			}
			
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			classStatements_AST = (AST)currentAST.root;
			
			classStatements_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(CLASS_STATEMENTS,"CLASS_STATEMENTS",first,LT(1))).add(classStatements_AST));
			
			currentAST.root = classStatements_AST;
			currentAST.child = classStatements_AST!=null &&classStatements_AST.getFirstChild()!=null ?
				classStatements_AST.getFirstChild() : classStatements_AST;
			currentAST.advanceChildToEnd();
		}
		classStatements_AST = (AST)currentAST.root;
		returnAST = classStatements_AST;
	}
	
	public final void fieldDeclarationStatement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST fieldDeclarationStatement_AST = null;
		AST anns_AST = null;
		AST mds_AST = null;
		Token  id = null;
		AST id_AST = null;
		AST right_AST = null;
		
		Token first = null;
		
		
		annotationList();
		anns_AST = (AST)returnAST;
		nls();
		if ( inputState.guessing==0 ) {
			
			first = LT(1);
			
		}
		modifiers();
		mds_AST = (AST)returnAST;
		nls();
		id = LT(1);
		id_AST = astFactory.create(id);
		match(ID);
		match(INIT_ASSIGN);
		nls();
		assignmentRight();
		right_AST = (AST)returnAST;
		if ( inputState.guessing==0 ) {
			fieldDeclarationStatement_AST = (AST)currentAST.root;
			
			fieldDeclarationStatement_AST = (AST)astFactory.make( (new ASTArray(5)).add(node(FIELD,"FIELD",first,LT(1))).add(anns_AST).add(mds_AST).add(id_AST).add(right_AST));
			
			currentAST.root = fieldDeclarationStatement_AST;
			currentAST.child = fieldDeclarationStatement_AST!=null &&fieldDeclarationStatement_AST.getFirstChild()!=null ?
				fieldDeclarationStatement_AST.getFirstChild() : fieldDeclarationStatement_AST;
			currentAST.advanceChildToEnd();
		}
		returnAST = fieldDeclarationStatement_AST;
	}
	
	public final void modifiers() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST modifiers_AST = null;
		
		Token first = LT(1);
		
		
		{
		switch ( LA(1)) {
		case LITERAL_public:
		case LITERAL_private:
		case LITERAL_protected:
		case LITERAL_static:
		case LITERAL_abstract:
		case LITERAL_final:
		{
			modifier();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop117:
			do {
				if (((LA(1) >= LITERAL_public && LA(1) <= LITERAL_final))) {
					modifier();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop117;
				}
				
			} while (true);
			}
			break;
		}
		case ID:
		case NLS:
		case DEF:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		if ( inputState.guessing==0 ) {
			modifiers_AST = (AST)currentAST.root;
			
			modifiers_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(MODIFIERS,"MODIFIERS",first,LT(1))).add(modifiers_AST));
			
			currentAST.root = modifiers_AST;
			currentAST.child = modifiers_AST!=null &&modifiers_AST.getFirstChild()!=null ?
				modifiers_AST.getFirstChild() : modifiers_AST;
			currentAST.advanceChildToEnd();
		}
		modifiers_AST = (AST)currentAST.root;
		returnAST = modifiers_AST;
	}
	
	public final void assignmentRight() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST assignmentRight_AST = null;
		
		if ((_tokenSet_13.member(LA(1))) && (LA(2)==COLON||LA(2)==EQ_GT)) {
			yamlHashStatement();
			astFactory.addASTChild(currentAST, returnAST);
			assignmentRight_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_11.member(LA(1))) && (_tokenSet_17.member(LA(2)))) {
			assignmentValueExpression();
			astFactory.addASTChild(currentAST, returnAST);
			assignmentRight_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		returnAST = assignmentRight_AST;
	}
	
	public final void classStatement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST classStatement_AST = null;
		
		boolean synPredMatched79 = false;
		if (((_tokenSet_32.member(LA(1))) && (_tokenSet_33.member(LA(2))))) {
			int _m79 = mark();
			synPredMatched79 = true;
			inputState.guessing++;
			try {
				{
				fieldStart();
				}
			}
			catch (RecognitionException pe) {
				synPredMatched79 = false;
			}
			rewind(_m79);
inputState.guessing--;
		}
		if ( synPredMatched79 ) {
			fieldDeclarationStatement();
			astFactory.addASTChild(currentAST, returnAST);
			classStatement_AST = (AST)currentAST.root;
		}
		else {
			boolean synPredMatched81 = false;
			if (((_tokenSet_7.member(LA(1))) && (_tokenSet_8.member(LA(2))))) {
				int _m81 = mark();
				synPredMatched81 = true;
				inputState.guessing++;
				try {
					{
					methodStart();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched81 = false;
				}
				rewind(_m81);
inputState.guessing--;
			}
			if ( synPredMatched81 ) {
				methodDeclarationStatement();
				astFactory.addASTChild(currentAST, returnAST);
				classStatement_AST = (AST)currentAST.root;
			}
			else {
				boolean synPredMatched83 = false;
				if (((LA(1)==K_INIT))) {
					int _m83 = mark();
					synPredMatched83 = true;
					inputState.guessing++;
					try {
						{
						constructStart();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched83 = false;
					}
					rewind(_m83);
inputState.guessing--;
				}
				if ( synPredMatched83 ) {
					constructDeclationStatement();
					astFactory.addASTChild(currentAST, returnAST);
					classStatement_AST = (AST)currentAST.root;
				}
				else if ((_tokenSet_13.member(LA(1))) && (LA(2)==COLON||LA(2)==EQ_GT)) {
					hashMapEntry();
					astFactory.addASTChild(currentAST, returnAST);
					classStatement_AST = (AST)currentAST.root;
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}}
				returnAST = classStatement_AST;
			}
			
	public final void fieldStart() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST fieldStart_AST = null;
		
		{
		_loop86:
		do {
			if ((LA(1)==AT)) {
				annotation();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop86;
			}
			
		} while (true);
		}
		nls();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop88:
		do {
			if (((LA(1) >= LITERAL_public && LA(1) <= LITERAL_final))) {
				modifier();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop88;
			}
			
		} while (true);
		}
		nls();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp58_AST = null;
		tmp58_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp58_AST);
		match(ID);
		AST tmp59_AST = null;
		tmp59_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp59_AST);
		match(INIT_ASSIGN);
		fieldStart_AST = (AST)currentAST.root;
		returnAST = fieldStart_AST;
	}
	
	public final void constructStart() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST constructStart_AST = null;
		
		{
		_loop96:
		do {
			if ((LA(1)==AT)) {
				annotation();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop96;
			}
			
		} while (true);
		}
		nls();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop98:
		do {
			if (((LA(1) >= LITERAL_public && LA(1) <= LITERAL_final))) {
				modifier();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop98;
			}
			
		} while (true);
		}
		nls();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp60_AST = null;
		tmp60_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp60_AST);
		match(K_INIT);
		constructStart_AST = (AST)currentAST.root;
		returnAST = constructStart_AST;
	}
	
	public final void constructDeclationStatement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST constructDeclationStatement_AST = null;
		AST paramList1_AST = null;
		AST paramList2_AST = null;
		AST body_AST = null;
		
		AST paramList = null;
		Token first = LT(1);
		
		
		match(K_INIT);
		{
		switch ( LA(1)) {
		case LPAREN:
		{
			match(LPAREN);
			nls();
			{
			switch ( LA(1)) {
			case INTEGER:
			case ID:
			case URL:
			case FILE_PATH:
			case EMAIL:
			case TIME:
			case PERCENTAGE:
			case FLOAT:
			case PAIR:
			case DATE:
			case K_NULL:
			case K_TRUE:
			case K_FALSE:
			case REFRENCE_NAME:
			case STRING:
			case REGEX:
			{
				parameterDefinitionList();
				paramList1_AST = (AST)returnAST;
				nls();
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				
				paramList = paramList1_AST;
				
			}
			break;
		}
		case INTEGER:
		case ID:
		case URL:
		case FILE_PATH:
		case EMAIL:
		case TIME:
		case PERCENTAGE:
		case FLOAT:
		case PAIR:
		case DATE:
		case K_NULL:
		case K_TRUE:
		case K_FALSE:
		case REFRENCE_NAME:
		case STRING:
		case REGEX:
		{
			parameterDefinitionList();
			paramList2_AST = (AST)returnAST;
			nls();
			if ( inputState.guessing==0 ) {
				
				paramList = paramList2_AST;
				
			}
			break;
		}
		case LCURLY:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		openBlockStatement();
		body_AST = (AST)returnAST;
		if ( inputState.guessing==0 ) {
			constructDeclationStatement_AST = (AST)currentAST.root;
			
			constructDeclationStatement_AST = (AST)astFactory.make( (new ASTArray(3)).add(node(CONSTRUCTOR_DEF,"CONSTRUCTOR_DEF",first,LT(1))).add(paramList).add(body_AST));
			
			currentAST.root = constructDeclationStatement_AST;
			currentAST.child = constructDeclationStatement_AST!=null &&constructDeclationStatement_AST.getFirstChild()!=null ?
				constructDeclationStatement_AST.getFirstChild() : constructDeclationStatement_AST;
			currentAST.advanceChildToEnd();
		}
		returnAST = constructDeclationStatement_AST;
	}
	
	public final void annotation() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST annotation_AST = null;
		Token  id = null;
		AST id_AST = null;
		AST args_AST = null;
		
		Token first = LT(1);
		
		
		match(AT);
		id = LT(1);
		id_AST = astFactory.create(id);
		match(ID);
		{
		switch ( LA(1)) {
		case LPAREN:
		{
			match(LPAREN);
			{
			switch ( LA(1)) {
			case INTEGER:
			case ID:
			case URL:
			case STR_TQ_START:
			case STR_SQ_START:
			case REGEX_START:
			case FILE_PATH:
			case FILE_PATH_START:
			case LPAREN:
			case LBRACK:
			case LCURLY:
			case EMAIL:
			case TIME:
			case PERCENTAGE:
			case FLOAT:
			case PAIR:
			case DATE:
			case PAIR_LCURLY:
			case WELL_LCURLY:
			case PLUS:
			case MINUS:
			case NOT:
			case DB_DOT:
			case TILDE:
			case RFC822:
			case K_NULL:
			case K_NEW:
			case K_TRUE:
			case K_FALSE:
			case IN:
			case K_IF:
			case WHILE:
			case DO:
			case DEF:
			case K_CLASS:
			case K_MATCH:
			case K_ASSERT:
			case THIS:
			case SUPER:
			case IS:
			case REFRENCE_NAME:
			case IF:
			case FOR:
			case NEW:
			case DB_DOT_LT:
			case GT_DB_DOT:
			case GT_DB_DOT_LT:
			case LNOT:
			case STRING:
			case K_FLASE:
			case ELSE:
			case SIGN_LPAREN:
			case SIGN_LCURLY:
			case REGEX:
			{
				argList();
				args_AST = (AST)returnAST;
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(RPAREN);
			break;
		}
		case ID:
		case NLS:
		case DEF:
		case K_CLASS:
		case K_PATTERN:
		case K_INIT:
		case AT:
		case LITERAL_public:
		case LITERAL_private:
		case LITERAL_protected:
		case LITERAL_static:
		case LITERAL_abstract:
		case LITERAL_final:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		if ( inputState.guessing==0 ) {
			annotation_AST = (AST)currentAST.root;
			
			annotation_AST = (AST)astFactory.make( (new ASTArray(3)).add(node(ANNOTATION,"ANNOTATION",first,LT(1))).add(id_AST).add(args_AST));
			
			currentAST.root = annotation_AST;
			currentAST.child = annotation_AST!=null &&annotation_AST.getFirstChild()!=null ?
				annotation_AST.getFirstChild() : annotation_AST;
			currentAST.advanceChildToEnd();
		}
		annotation_AST = (AST)currentAST.root;
		returnAST = annotation_AST;
	}
	
	public final void modifier() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST modifier_AST = null;
		
		switch ( LA(1)) {
		case LITERAL_public:
		{
			AST tmp67_AST = null;
			tmp67_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp67_AST);
			match(LITERAL_public);
			modifier_AST = (AST)currentAST.root;
			break;
		}
		case LITERAL_private:
		{
			AST tmp68_AST = null;
			tmp68_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp68_AST);
			match(LITERAL_private);
			modifier_AST = (AST)currentAST.root;
			break;
		}
		case LITERAL_protected:
		{
			AST tmp69_AST = null;
			tmp69_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp69_AST);
			match(LITERAL_protected);
			modifier_AST = (AST)currentAST.root;
			break;
		}
		case LITERAL_static:
		{
			AST tmp70_AST = null;
			tmp70_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp70_AST);
			match(LITERAL_static);
			modifier_AST = (AST)currentAST.root;
			break;
		}
		case LITERAL_abstract:
		{
			AST tmp71_AST = null;
			tmp71_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp71_AST);
			match(LITERAL_abstract);
			modifier_AST = (AST)currentAST.root;
			break;
		}
		case LITERAL_final:
		{
			AST tmp72_AST = null;
			tmp72_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp72_AST);
			match(LITERAL_final);
			modifier_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = modifier_AST;
	}
	
	public final void definitionHeader() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST definitionHeader_AST = null;
		AST t_AST = null;
		
		AST ty = null;
		Token first = LT(1);
		
		
		{
		switch ( LA(1)) {
		case DEF:
		{
			match(DEF);
			break;
		}
		case ID:
		{
			type();
			t_AST = (AST)returnAST;
			if ( inputState.guessing==0 ) {
				definitionHeader_AST = (AST)currentAST.root;
				
				definitionHeader_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(TYPE,"TYPE",first,LT(1))).add(t_AST));
				
				currentAST.root = definitionHeader_AST;
				currentAST.child = definitionHeader_AST!=null &&definitionHeader_AST.getFirstChild()!=null ?
					definitionHeader_AST.getFirstChild() : definitionHeader_AST;
				currentAST.advanceChildToEnd();
			}
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		returnAST = definitionHeader_AST;
	}
	
	public final void parameterDefinitionList() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST parameterDefinitionList_AST = null;
		
		Token first = LT(1);
		
		
		parameterDefinition();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop125:
		do {
			if ((LA(1)==COMMAR)) {
				match(COMMAR);
				nls();
				parameterDefinition();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop125;
			}
			
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			parameterDefinitionList_AST = (AST)currentAST.root;
			
			parameterDefinitionList_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(PARAM_LIST,"PARAM_LIST",first,LT(1))).add(parameterDefinitionList_AST));
			
			currentAST.root = parameterDefinitionList_AST;
			currentAST.child = parameterDefinitionList_AST!=null &&parameterDefinitionList_AST.getFirstChild()!=null ?
				parameterDefinitionList_AST.getFirstChild() : parameterDefinitionList_AST;
			currentAST.advanceChildToEnd();
		}
		parameterDefinitionList_AST = (AST)currentAST.root;
		returnAST = parameterDefinitionList_AST;
	}
	
	public final void openBlockStatement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST openBlockStatement_AST = null;
		
		curlyBlockStatement();
		astFactory.addASTChild(currentAST, returnAST);
		openBlockStatement_AST = (AST)currentAST.root;
		returnAST = openBlockStatement_AST;
	}
	
	public final void methodBlockStatement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST methodBlockStatement_AST = null;
		
		switch ( LA(1)) {
		case LCURLY:
		{
			curlyBlockStatement();
			astFactory.addASTChild(currentAST, returnAST);
			methodBlockStatement_AST = (AST)currentAST.root;
			break;
		}
		case RARROW:
		{
			closureBlockStatement();
			astFactory.addASTChild(currentAST, returnAST);
			methodBlockStatement_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = methodBlockStatement_AST;
	}
	
	public final void typeName() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST typeName_AST = null;
		Token  id1 = null;
		AST id1_AST = null;
		Token  d = null;
		AST d_AST = null;
		Token  id2 = null;
		AST id2_AST = null;
		
		Token first = LT(1);
		
		
		id1 = LT(1);
		id1_AST = astFactory.create(id1);
		match(ID);
		{
		_loop122:
		do {
			if ((LA(1)==DOT) && (LA(2)==ID||LA(2)==NLS)) {
				d = LT(1);
				d_AST = astFactory.create(d);
				match(DOT);
				nls();
				id2 = LT(1);
				id2_AST = astFactory.create(id2);
				match(ID);
				if ( inputState.guessing==0 ) {
					id1_AST = (AST)astFactory.make( (new ASTArray(3)).add(node(DOT,".",first)).add(id1_AST).add(id2_AST));
				}
			}
			else {
				break _loop122;
			}
			
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			typeName_AST = (AST)currentAST.root;
			
			typeName_AST = id1_AST;
			
			currentAST.root = typeName_AST;
			currentAST.child = typeName_AST!=null &&typeName_AST.getFirstChild()!=null ?
				typeName_AST.getFirstChild() : typeName_AST;
			currentAST.advanceChildToEnd();
		}
		typeName_AST = (AST)currentAST.root;
		returnAST = typeName_AST;
	}
	
	public final void parameterDefinition() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST parameterDefinition_AST = null;
		Token  id = null;
		AST id_AST = null;
		AST lit_AST = null;
		
		AST param = null;
		Token first = LT(1);
		
		
		{
		switch ( LA(1)) {
		case ID:
		{
			id = LT(1);
			id_AST = astFactory.create(id);
			astFactory.addASTChild(currentAST, id_AST);
			match(ID);
			if ( inputState.guessing==0 ) {
				
				param = id_AST;
				
			}
			break;
		}
		case INTEGER:
		case URL:
		case FILE_PATH:
		case EMAIL:
		case TIME:
		case PERCENTAGE:
		case FLOAT:
		case PAIR:
		case DATE:
		case K_NULL:
		case K_TRUE:
		case K_FALSE:
		case REFRENCE_NAME:
		case STRING:
		case REGEX:
		{
			literal();
			lit_AST = (AST)returnAST;
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				
				param = lit_AST;
				
			}
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		if ( inputState.guessing==0 ) {
			parameterDefinition_AST = (AST)currentAST.root;
			
			parameterDefinition_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(PARAM,"PARAM",first,LT(1))).add(param));
			
			currentAST.root = parameterDefinition_AST;
			currentAST.child = parameterDefinition_AST!=null &&parameterDefinition_AST.getFirstChild()!=null ?
				parameterDefinition_AST.getFirstChild() : parameterDefinition_AST;
			currentAST.advanceChildToEnd();
		}
		parameterDefinition_AST = (AST)currentAST.root;
		returnAST = parameterDefinition_AST;
	}
	
/** Basic literals */
	public final void literal() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST literal_AST = null;
		
		switch ( LA(1)) {
		case STRING:
		{
			stringLiteral();
			astFactory.addASTChild(currentAST, returnAST);
			literal_AST = (AST)currentAST.root;
			break;
		}
		case REFRENCE_NAME:
		{
			referenceLiteral();
			astFactory.addASTChild(currentAST, returnAST);
			literal_AST = (AST)currentAST.root;
			break;
		}
		case REGEX:
		{
			regexLiteral();
			astFactory.addASTChild(currentAST, returnAST);
			literal_AST = (AST)currentAST.root;
			break;
		}
		case DATE:
		{
			dateLiteral();
			astFactory.addASTChild(currentAST, returnAST);
			literal_AST = (AST)currentAST.root;
			break;
		}
		case TIME:
		{
			timeLiteral();
			astFactory.addASTChild(currentAST, returnAST);
			literal_AST = (AST)currentAST.root;
			break;
		}
		case PAIR:
		{
			pairLiteral();
			astFactory.addASTChild(currentAST, returnAST);
			literal_AST = (AST)currentAST.root;
			break;
		}
		case EMAIL:
		{
			emailLiteral();
			astFactory.addASTChild(currentAST, returnAST);
			literal_AST = (AST)currentAST.root;
			break;
		}
		case INTEGER:
		case PERCENTAGE:
		case FLOAT:
		{
			numberLiteral();
			astFactory.addASTChild(currentAST, returnAST);
			literal_AST = (AST)currentAST.root;
			break;
		}
		case URL:
		{
			urlLiteral();
			astFactory.addASTChild(currentAST, returnAST);
			literal_AST = (AST)currentAST.root;
			break;
		}
		case FILE_PATH:
		{
			filePathLiteral();
			astFactory.addASTChild(currentAST, returnAST);
			literal_AST = (AST)currentAST.root;
			break;
		}
		case K_NULL:
		{
			AST tmp75_AST = null;
			tmp75_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp75_AST);
			match(K_NULL);
			literal_AST = (AST)currentAST.root;
			break;
		}
		case K_TRUE:
		{
			AST tmp76_AST = null;
			tmp76_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp76_AST);
			match(K_TRUE);
			literal_AST = (AST)currentAST.root;
			break;
		}
		case K_FALSE:
		{
			AST tmp77_AST = null;
			tmp77_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp77_AST);
			match(K_FALSE);
			literal_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = literal_AST;
	}
	
	public final void ifStatement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST ifStatement_AST = null;
		AST condition1_AST = null;
		AST body_AST = null;
		AST elifbody_AST = null;
		AST ebody_AST = null;
		
		AST elseBody = null;
		AST condition = null;
		Token first = LT(1);
		
		
		match(K_IF);
		nls();
		{
		match(LPAREN);
		expression();
		condition1_AST = (AST)returnAST;
		if ( inputState.guessing==0 ) {
			
			condition = condition1_AST;
			
		}
		match(RPAREN);
		}
		nls();
		openBlockStatement();
		body_AST = (AST)returnAST;
		{
		boolean synPredMatched134 = false;
		if (((LA(1)==NLS||LA(1)==K_ELSE) && (_tokenSet_34.member(LA(2))))) {
			int _m134 = mark();
			synPredMatched134 = true;
			inputState.guessing++;
			try {
				{
				nls();
				match(K_ELSE);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched134 = false;
			}
			rewind(_m134);
inputState.guessing--;
		}
		if ( synPredMatched134 ) {
			nls();
			match(K_ELSE);
			nls();
			{
			switch ( LA(1)) {
			case K_IF:
			{
				ifStatement();
				elifbody_AST = (AST)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
				if ( inputState.guessing==0 ) {
					
					elseBody = elifbody_AST;
					
				}
				break;
			}
			case LCURLY:
			{
				openBlockStatement();
				ebody_AST = (AST)returnAST;
				if ( inputState.guessing==0 ) {
					
					elseBody = ebody_AST;
					
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		else if ((_tokenSet_35.member(LA(1))) && (_tokenSet_19.member(LA(2)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		if ( inputState.guessing==0 ) {
			ifStatement_AST = (AST)currentAST.root;
			
			ifStatement_AST = (AST)astFactory.make( (new ASTArray(4)).add(node(IF,"IF",first,LT(1))).add(condition).add(body_AST).add(elseBody));
			
			currentAST.root = ifStatement_AST;
			currentAST.child = ifStatement_AST!=null &&ifStatement_AST.getFirstChild()!=null ?
				ifStatement_AST.getFirstChild() : ifStatement_AST;
			currentAST.advanceChildToEnd();
		}
		ifStatement_AST = (AST)currentAST.root;
		returnAST = ifStatement_AST;
	}
	
	public final void catchListStatement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST catchListStatement_AST = null;
		
		Token first = LT(1);
		
		
		catchStatement();
		astFactory.addASTChild(currentAST, returnAST);
		if ( inputState.guessing==0 ) {
			catchListStatement_AST = (AST)currentAST.root;
			
			catchListStatement_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(CATCH_LIST,"CATCH_LIST",first,LT(1))).add(catchListStatement_AST));
			
			currentAST.root = catchListStatement_AST;
			currentAST.child = catchListStatement_AST!=null &&catchListStatement_AST.getFirstChild()!=null ?
				catchListStatement_AST.getFirstChild() : catchListStatement_AST;
			currentAST.advanceChildToEnd();
		}
		catchListStatement_AST = (AST)currentAST.root;
		returnAST = catchListStatement_AST;
	}
	
	public final void catchStatement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST catchStatement_AST = null;
		AST t_AST = null;
		Token  param = null;
		AST param_AST = null;
		AST body_AST = null;
		
		Token first = LT(1);
		
		
		AST tmp82_AST = null;
		tmp82_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp82_AST);
		match(K_CATCH);
		match(LPAREN);
		nls();
		{
		if ((LA(1)==ID) && (LA(2)==ID||LA(2)==NLS||LA(2)==DOT)) {
			type();
			t_AST = (AST)returnAST;
			astFactory.addASTChild(currentAST, returnAST);
			nls();
		}
		else if ((LA(1)==ID) && (LA(2)==RPAREN||LA(2)==NLS)) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		param = LT(1);
		param_AST = astFactory.create(param);
		match(ID);
		nls();
		match(RPAREN);
		nls();
		openBlockStatement();
		body_AST = (AST)returnAST;
		astFactory.addASTChild(currentAST, returnAST);
		if ( inputState.guessing==0 ) {
			catchStatement_AST = (AST)currentAST.root;
			
			catchStatement_AST = (AST)astFactory.make( (new ASTArray(3)).add(node(CATCH,"CATCH",first,LT(1))).add(param_AST).add(body_AST));
			
			currentAST.root = catchStatement_AST;
			currentAST.child = catchStatement_AST!=null &&catchStatement_AST.getFirstChild()!=null ?
				catchStatement_AST.getFirstChild() : catchStatement_AST;
			currentAST.advanceChildToEnd();
		}
		catchStatement_AST = (AST)currentAST.root;
		returnAST = catchStatement_AST;
	}
	
	public final void hashMapWithoutBoundary() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST hashMapWithoutBoundary_AST = null;
		
		hashMapEntryList();
		astFactory.addASTChild(currentAST, returnAST);
		hashMapWithoutBoundary_AST = (AST)currentAST.root;
		returnAST = hashMapWithoutBoundary_AST;
	}
	
	public final void commandArgument() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST commandArgument_AST = null;
		
		boolean synPredMatched167 = false;
		if (((_tokenSet_13.member(LA(1))) && (LA(2)==COLON||LA(2)==EQ_GT))) {
			int _m167 = mark();
			synPredMatched167 = true;
			inputState.guessing++;
			try {
				{
				hashMapKey();
				match(COLCON);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched167 = false;
			}
			rewind(_m167);
inputState.guessing--;
		}
		if ( synPredMatched167 ) {
			hashMapEntry();
			astFactory.addASTChild(currentAST, returnAST);
			commandArgument_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_15.member(LA(1))) && (_tokenSet_36.member(LA(2)))) {
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			commandArgument_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		returnAST = commandArgument_AST;
	}
	
	public final void hashMapKey() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST hashMapKey_AST = null;
		
		switch ( LA(1)) {
		case ID:
		{
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			hashMapKey_AST = (AST)currentAST.root;
			break;
		}
		case STRING:
		{
			stringLiteral();
			astFactory.addASTChild(currentAST, returnAST);
			hashMapKey_AST = (AST)currentAST.root;
			break;
		}
		case EMAIL:
		{
			emailLiteral();
			astFactory.addASTChild(currentAST, returnAST);
			hashMapKey_AST = (AST)currentAST.root;
			break;
		}
		case INTEGER:
		case PERCENTAGE:
		case FLOAT:
		{
			numberLiteral();
			astFactory.addASTChild(currentAST, returnAST);
			hashMapKey_AST = (AST)currentAST.root;
			break;
		}
		case URL:
		{
			urlLiteral();
			astFactory.addASTChild(currentAST, returnAST);
			hashMapKey_AST = (AST)currentAST.root;
			break;
		}
		case NOT:
		case RFC822:
		case K_NULL:
		case K_TRUE:
		case IN:
		case WHILE:
		case DO:
		case DEF:
		case K_CLASS:
		case K_MATCH:
		case K_ASSERT:
		case THIS:
		case SUPER:
		case IS:
		case IF:
		case FOR:
		case NEW:
		case K_FLASE:
		case ELSE:
		{
			keywordAsMethodName();
			astFactory.addASTChild(currentAST, returnAST);
			hashMapKey_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = hashMapKey_AST;
	}
	
	public final void commandArgumentList() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST commandArgumentList_AST = null;
		
		Token first = LT(1);
		
		
		commandArgument();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop170:
		do {
			if ((LA(1)==COMMAR)) {
				match(COMMAR);
				nls();
				commandArgument();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop170;
			}
			
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			commandArgumentList_AST = (AST)currentAST.root;
			
			commandArgumentList_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(ARG_LIST,"ARG_LIST",first,LT(1))).add(commandArgumentList_AST));
			
			currentAST.root = commandArgumentList_AST;
			currentAST.child = commandArgumentList_AST!=null &&commandArgumentList_AST.getFirstChild()!=null ?
				commandArgumentList_AST.getFirstChild() : commandArgumentList_AST;
			currentAST.advanceChildToEnd();
		}
		commandArgumentList_AST = (AST)currentAST.root;
		returnAST = commandArgumentList_AST;
	}
	
	public final void assignmentExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST assignmentExpression_AST = null;
		AST left_AST = null;
		AST right_AST = null;
		
		boolean hasLeft = false;
		boolean hasIndentation = false;
		
		
		assignmentLeft();
		left_AST = (AST)returnAST;
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((_tokenSet_37.member(LA(1))) && (_tokenSet_38.member(LA(2)))) {
			{
			switch ( LA(1)) {
			case ASSIGN:
			{
				AST tmp86_AST = null;
				tmp86_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp86_AST);
				match(ASSIGN);
				break;
			}
			case PLUS_ASSIGN:
			{
				AST tmp87_AST = null;
				tmp87_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp87_AST);
				match(PLUS_ASSIGN);
				break;
			}
			case MINUS_ASSIGN:
			{
				AST tmp88_AST = null;
				tmp88_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp88_AST);
				match(MINUS_ASSIGN);
				break;
			}
			case STAR_ASSIGN:
			{
				AST tmp89_AST = null;
				tmp89_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp89_AST);
				match(STAR_ASSIGN);
				break;
			}
			case DIV_ASSIGN:
			{
				AST tmp90_AST = null;
				tmp90_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp90_AST);
				match(DIV_ASSIGN);
				break;
			}
			case INIT_ASSIGN:
			{
				AST tmp91_AST = null;
				tmp91_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp91_AST);
				match(INIT_ASSIGN);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			nls();
			{
			assignmentRight();
			astFactory.addASTChild(currentAST, returnAST);
			}
		}
		else if ((_tokenSet_39.member(LA(1))) && (_tokenSet_19.member(LA(2)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		assignmentExpression_AST = (AST)currentAST.root;
		returnAST = assignmentExpression_AST;
	}
	
	public final void contextExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST contextExpression_AST = null;
		
		matchOrExpression();
		astFactory.addASTChild(currentAST, returnAST);
		contextExpression_AST = (AST)currentAST.root;
		returnAST = contextExpression_AST;
	}
	
	public final void matchOrExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST matchOrExpression_AST = null;
		
		conditionalExpression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop188:
		do {
			if ((LA(1)==MOR) && (_tokenSet_40.member(LA(2)))) {
				AST tmp92_AST = null;
				tmp92_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp92_AST);
				match(MOR);
				nls();
				conditionalExpression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop188;
			}
			
		} while (true);
		}
		matchOrExpression_AST = (AST)currentAST.root;
		returnAST = matchOrExpression_AST;
	}
	
	public final void assignmentLeft() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST assignmentLeft_AST = null;
		
		matchOrExpression();
		astFactory.addASTChild(currentAST, returnAST);
		assignmentLeft_AST = (AST)currentAST.root;
		returnAST = assignmentLeft_AST;
	}
	
	public final void conditionalExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST conditionalExpression_AST = null;
		
		matchAndExpression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==QUESTION_COLON) && (_tokenSet_40.member(LA(2)))) {
			AST tmp93_AST = null;
			tmp93_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp93_AST);
			match(QUESTION_COLON);
			nls();
			assignmentExpression();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==QUESTION) && (_tokenSet_40.member(LA(2)))) {
			AST tmp94_AST = null;
			tmp94_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp94_AST);
			match(QUESTION);
			nls();
			assignmentExpression();
			astFactory.addASTChild(currentAST, returnAST);
			nls();
			match(COLON);
			nls();
			conditionalExpression();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((_tokenSet_39.member(LA(1))) && (_tokenSet_19.member(LA(2)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		conditionalExpression_AST = (AST)currentAST.root;
		returnAST = conditionalExpression_AST;
	}
	
	public final void matchAndExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST matchAndExpression_AST = null;
		AST left_AST = null;
		
		Token first = LT(1);
		
		
		logicalOrExpression();
		left_AST = (AST)returnAST;
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop193:
		do {
			if ((LA(1)==MAND) && (_tokenSet_40.member(LA(2)))) {
				AST tmp96_AST = null;
				tmp96_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp96_AST);
				match(MAND);
				nls();
				logicalOrExpression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop193;
			}
			
		} while (true);
		}
		matchAndExpression_AST = (AST)currentAST.root;
		returnAST = matchAndExpression_AST;
	}
	
	public final void logicalOrExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST logicalOrExpression_AST = null;
		
		logicalAndExpression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop197:
		do {
			if ((LA(1)==KOR||LA(1)==LOR) && (_tokenSet_40.member(LA(2)))) {
				{
				switch ( LA(1)) {
				case LOR:
				{
					AST tmp97_AST = null;
					tmp97_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp97_AST);
					match(LOR);
					break;
				}
				case KOR:
				{
					AST tmp98_AST = null;
					tmp98_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp98_AST);
					match(KOR);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				nls();
				logicalAndExpression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop197;
			}
			
		} while (true);
		}
		logicalOrExpression_AST = (AST)currentAST.root;
		returnAST = logicalOrExpression_AST;
	}
	
	public final void logicalAndExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST logicalAndExpression_AST = null;
		
		bitwiseAndExpression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop201:
		do {
			if ((LA(1)==XOR||LA(1)==KAND||LA(1)==LAND) && (_tokenSet_40.member(LA(2)))) {
				{
				switch ( LA(1)) {
				case LAND:
				{
					AST tmp99_AST = null;
					tmp99_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp99_AST);
					match(LAND);
					break;
				}
				case KAND:
				{
					AST tmp100_AST = null;
					tmp100_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp100_AST);
					match(KAND);
					break;
				}
				case XOR:
				{
					AST tmp101_AST = null;
					tmp101_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp101_AST);
					match(XOR);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				nls();
				bitwiseAndExpression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop201;
			}
			
		} while (true);
		}
		logicalAndExpression_AST = (AST)currentAST.root;
		returnAST = logicalAndExpression_AST;
	}
	
	public final void bitwiseAndExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST bitwiseAndExpression_AST = null;
		
		bitwiseOrExpression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop204:
		do {
			if ((LA(1)==BOR) && (_tokenSet_40.member(LA(2)))) {
				AST tmp102_AST = null;
				tmp102_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp102_AST);
				match(BOR);
				nls();
				bitwiseOrExpression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop204;
			}
			
		} while (true);
		}
		bitwiseAndExpression_AST = (AST)currentAST.root;
		returnAST = bitwiseAndExpression_AST;
	}
	
	public final void bitwiseOrExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST bitwiseOrExpression_AST = null;
		
		equalityExpression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop207:
		do {
			if ((LA(1)==BAND) && (_tokenSet_40.member(LA(2)))) {
				AST tmp103_AST = null;
				tmp103_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp103_AST);
				match(BAND);
				nls();
				equalityExpression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop207;
			}
			
		} while (true);
		}
		bitwiseOrExpression_AST = (AST)currentAST.root;
		returnAST = bitwiseOrExpression_AST;
	}
	
	public final void equalityExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST equalityExpression_AST = null;
		
		relationalExpression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop211:
		do {
			if ((_tokenSet_41.member(LA(1))) && (_tokenSet_40.member(LA(2)))) {
				{
				switch ( LA(1)) {
				case NOT_EQUAL:
				{
					AST tmp104_AST = null;
					tmp104_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp104_AST);
					match(NOT_EQUAL);
					break;
				}
				case EQUAL:
				{
					AST tmp105_AST = null;
					tmp105_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp105_AST);
					match(EQUAL);
					break;
				}
				case IS:
				{
					AST tmp106_AST = null;
					tmp106_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp106_AST);
					match(IS);
					break;
				}
				case REGEX_MATCH:
				{
					AST tmp107_AST = null;
					tmp107_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp107_AST);
					match(REGEX_MATCH);
					break;
				}
				case REGEX_NOT_MATCH:
				{
					AST tmp108_AST = null;
					tmp108_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp108_AST);
					match(REGEX_NOT_MATCH);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				nls();
				relationalExpression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop211;
			}
			
		} while (true);
		}
		equalityExpression_AST = (AST)currentAST.root;
		returnAST = equalityExpression_AST;
	}
	
	public final void relationalExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST relationalExpression_AST = null;
		
		notExpression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((_tokenSet_42.member(LA(1))) && (_tokenSet_40.member(LA(2)))) {
			{
			{
			switch ( LA(1)) {
			case LT:
			{
				AST tmp109_AST = null;
				tmp109_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp109_AST);
				match(LT);
				break;
			}
			case GT:
			{
				AST tmp110_AST = null;
				tmp110_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp110_AST);
				match(GT);
				break;
			}
			case LE:
			{
				AST tmp111_AST = null;
				tmp111_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp111_AST);
				match(LE);
				break;
			}
			case GE:
			{
				AST tmp112_AST = null;
				tmp112_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp112_AST);
				match(GE);
				break;
			}
			case IN:
			{
				AST tmp113_AST = null;
				tmp113_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp113_AST);
				match(IN);
				break;
			}
			case INSTANCEOF:
			{
				AST tmp114_AST = null;
				tmp114_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp114_AST);
				match(INSTANCEOF);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			nls();
			notExpression();
			astFactory.addASTChild(currentAST, returnAST);
			}
		}
		else if ((_tokenSet_39.member(LA(1))) && (_tokenSet_19.member(LA(2)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		relationalExpression_AST = (AST)currentAST.root;
		returnAST = relationalExpression_AST;
	}
	
	public final void notExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST notExpression_AST = null;
		
		if ((LA(1)==NOT) && (_tokenSet_40.member(LA(2)))) {
			AST tmp115_AST = null;
			tmp115_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp115_AST);
			match(NOT);
			nls();
			stepExpression();
			astFactory.addASTChild(currentAST, returnAST);
			notExpression_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_15.member(LA(1))) && (_tokenSet_43.member(LA(2)))) {
			stepExpression();
			astFactory.addASTChild(currentAST, returnAST);
			notExpression_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		returnAST = notExpression_AST;
	}
	
	public final void stepExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST stepExpression_AST = null;
		
		rangeExpression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		switch ( LA(1)) {
		case K_STEP:
		{
			AST tmp116_AST = null;
			tmp116_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp116_AST);
			match(K_STEP);
			nls();
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case EOF:
		case DEDENT:
		case ID:
		case STR_PART_MIDDLE:
		case STR_PART_END:
		case REGEX_END:
		case REGEX_MIDDLE:
		case FILE_PATH_MIDDLE:
		case FILE_PATH_END:
		case LPAREN:
		case RPAREN:
		case LBRACK:
		case RBRACK:
		case LCURLY:
		case RCURLY:
		case NLS:
		case RCURLY_X_PAIR:
		case RCURLY_X_PAIR_LCURLY:
		case RCURLY_X_LCURLY:
		case ASSIGN:
		case PLUS:
		case MINUS:
		case EQUAL:
		case NOT_EQUAL:
		case MOD:
		case COLON:
		case COMMAR:
		case SEMI:
		case DOT:
		case DB_DOT:
		case QUESTION:
		case GT:
		case LT:
		case GE:
		case LE:
		case SR:
		case SL:
		case INC:
		case DEC:
		case DB_STAR:
		case STAR_DOT:
		case OPTIONAL_DOT:
		case PLUS_ASSIGN:
		case MINUS_ASSIGN:
		case STAR_ASSIGN:
		case DIV_ASSIGN:
		case DIV:
		case REGEX_MATCH:
		case REGEX_NOT_MATCH:
		case IN:
		case XOR:
		case INSTANCEOF:
		case K_AS:
		case K_MATCH:
		case IS:
		case KAND:
		case KOR:
		case MOR:
		case RARROW:
		case INIT_ASSIGN:
		case STAR:
		case QUESTION_COLON:
		case MAND:
		case LOR:
		case LAND:
		case BOR:
		case BAND:
		case DB_DOT_LT:
		case GT_DB_DOT:
		case GT_DB_DOT_LT:
		case BSL:
		case BSR:
		case POW:
		case THREE_DOT:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		stepExpression_AST = (AST)currentAST.root;
		returnAST = stepExpression_AST;
	}
	
	public final void rangeExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST rangeExpression_AST = null;
		
		postifxRangeExpression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((_tokenSet_44.member(LA(1))) && (_tokenSet_45.member(LA(2)))) {
			{
			switch ( LA(1)) {
			case DB_DOT:
			{
				AST tmp117_AST = null;
				tmp117_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp117_AST);
				match(DB_DOT);
				break;
			}
			case DB_DOT_LT:
			{
				AST tmp118_AST = null;
				tmp118_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp118_AST);
				match(DB_DOT_LT);
				break;
			}
			case GT_DB_DOT:
			{
				AST tmp119_AST = null;
				tmp119_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp119_AST);
				match(GT_DB_DOT);
				break;
			}
			case GT_DB_DOT_LT:
			{
				AST tmp120_AST = null;
				tmp120_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp120_AST);
				match(GT_DB_DOT_LT);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			nls();
			{
			if ((_tokenSet_46.member(LA(1))) && (_tokenSet_43.member(LA(2)))) {
				shiftExpression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else if ((_tokenSet_35.member(LA(1))) && (_tokenSet_19.member(LA(2)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
		}
		else if ((_tokenSet_35.member(LA(1))) && (_tokenSet_19.member(LA(2)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		rangeExpression_AST = (AST)currentAST.root;
		returnAST = rangeExpression_AST;
	}
	
	public final void postifxRangeExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST postifxRangeExpression_AST = null;
		
		switch ( LA(1)) {
		case DB_DOT:
		case DB_DOT_LT:
		case GT_DB_DOT:
		case GT_DB_DOT_LT:
		{
			{
			switch ( LA(1)) {
			case DB_DOT:
			{
				AST tmp121_AST = null;
				tmp121_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp121_AST);
				match(DB_DOT);
				if ( inputState.guessing==0 ) {
					tmp121_AST.setType(U_DB_DOT);
				}
				break;
			}
			case DB_DOT_LT:
			{
				AST tmp122_AST = null;
				tmp122_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp122_AST);
				match(DB_DOT_LT);
				if ( inputState.guessing==0 ) {
					tmp122_AST.setType(U_DB_DOT_LT);
				}
				break;
			}
			case GT_DB_DOT:
			{
				AST tmp123_AST = null;
				tmp123_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp123_AST);
				match(GT_DB_DOT);
				if ( inputState.guessing==0 ) {
					tmp123_AST.setType(U_GT_DB_DOT);
				}
				break;
			}
			case GT_DB_DOT_LT:
			{
				AST tmp124_AST = null;
				tmp124_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp124_AST);
				match(GT_DB_DOT_LT);
				if ( inputState.guessing==0 ) {
					tmp124_AST.setType(U_GT_DB_DOT_LT);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			shiftExpression();
			astFactory.addASTChild(currentAST, returnAST);
			postifxRangeExpression_AST = (AST)currentAST.root;
			break;
		}
		case INTEGER:
		case ID:
		case URL:
		case STR_TQ_START:
		case STR_SQ_START:
		case REGEX_START:
		case FILE_PATH:
		case FILE_PATH_START:
		case LPAREN:
		case LBRACK:
		case LCURLY:
		case EMAIL:
		case TIME:
		case PERCENTAGE:
		case FLOAT:
		case PAIR:
		case DATE:
		case PAIR_LCURLY:
		case WELL_LCURLY:
		case PLUS:
		case MINUS:
		case NOT:
		case TILDE:
		case RFC822:
		case K_NULL:
		case K_NEW:
		case K_TRUE:
		case K_FALSE:
		case IN:
		case K_IF:
		case WHILE:
		case DO:
		case DEF:
		case K_CLASS:
		case K_MATCH:
		case K_ASSERT:
		case THIS:
		case SUPER:
		case IS:
		case REFRENCE_NAME:
		case IF:
		case FOR:
		case NEW:
		case LNOT:
		case STRING:
		case K_FLASE:
		case ELSE:
		case SIGN_LPAREN:
		case SIGN_LCURLY:
		case REGEX:
		{
			shiftExpression();
			astFactory.addASTChild(currentAST, returnAST);
			postifxRangeExpression_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = postifxRangeExpression_AST;
	}
	
	public final void shiftExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST shiftExpression_AST = null;
		
		additiveExpression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop228:
		do {
			if ((_tokenSet_47.member(LA(1))) && (_tokenSet_48.member(LA(2)))) {
				{
				switch ( LA(1)) {
				case SL:
				{
					AST tmp125_AST = null;
					tmp125_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp125_AST);
					match(SL);
					break;
				}
				case SR:
				{
					AST tmp126_AST = null;
					tmp126_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp126_AST);
					match(SR);
					break;
				}
				case BSL:
				{
					AST tmp127_AST = null;
					tmp127_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp127_AST);
					match(BSL);
					break;
				}
				case BSR:
				{
					AST tmp128_AST = null;
					tmp128_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp128_AST);
					match(BSR);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				nls();
				additiveExpression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop228;
			}
			
		} while (true);
		}
		shiftExpression_AST = (AST)currentAST.root;
		returnAST = shiftExpression_AST;
	}
	
	public final void additiveExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST additiveExpression_AST = null;
		
		multiplicativeExpression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop232:
		do {
			if ((LA(1)==PLUS||LA(1)==MINUS) && (_tokenSet_48.member(LA(2)))) {
				{
				switch ( LA(1)) {
				case PLUS:
				{
					AST tmp129_AST = null;
					tmp129_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp129_AST);
					match(PLUS);
					break;
				}
				case MINUS:
				{
					AST tmp130_AST = null;
					tmp130_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp130_AST);
					match(MINUS);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				nls();
				multiplicativeExpression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop232;
			}
			
		} while (true);
		}
		additiveExpression_AST = (AST)currentAST.root;
		returnAST = additiveExpression_AST;
	}
	
	public final void multiplicativeExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST multiplicativeExpression_AST = null;
		
		powerExpression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop236:
		do {
			if ((LA(1)==MOD||LA(1)==DIV||LA(1)==STAR) && (_tokenSet_48.member(LA(2)))) {
				{
				switch ( LA(1)) {
				case STAR:
				{
					AST tmp131_AST = null;
					tmp131_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp131_AST);
					match(STAR);
					break;
				}
				case DIV:
				{
					AST tmp132_AST = null;
					tmp132_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp132_AST);
					match(DIV);
					break;
				}
				case MOD:
				{
					AST tmp133_AST = null;
					tmp133_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp133_AST);
					match(MOD);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				nls();
				powerExpression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop236;
			}
			
		} while (true);
		}
		multiplicativeExpression_AST = (AST)currentAST.root;
		returnAST = multiplicativeExpression_AST;
	}
	
	public final void powerExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST powerExpression_AST = null;
		
		matchOpExpression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop240:
		do {
			if ((LA(1)==DB_STAR||LA(1)==POW) && (_tokenSet_48.member(LA(2)))) {
				{
				switch ( LA(1)) {
				case DB_STAR:
				{
					AST tmp134_AST = null;
					tmp134_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp134_AST);
					match(DB_STAR);
					break;
				}
				case POW:
				{
					AST tmp135_AST = null;
					tmp135_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp135_AST);
					match(POW);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				nls();
				matchOpExpression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop240;
			}
			
		} while (true);
		}
		powerExpression_AST = (AST)currentAST.root;
		returnAST = powerExpression_AST;
	}
	
	public final void matchOpExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST matchOpExpression_AST = null;
		AST expr_AST = null;
		AST block_AST = null;
		
		aliasExpression();
		expr_AST = (AST)returnAST;
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==K_MATCH) && (LA(2)==LCURLY||LA(2)==NLS||LA(2)==MOR)) {
			AST tmp136_AST = null;
			tmp136_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp136_AST);
			match(K_MATCH);
			nls();
			{
			switch ( LA(1)) {
			case LCURLY:
			{
				{
				match(LCURLY);
				nls();
				matchBlock();
				block_AST = (AST)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
				nls();
				match(RCURLY);
				}
				break;
			}
			case MOR:
			{
				{
				matchBlock();
				astFactory.addASTChild(currentAST, returnAST);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		else if ((_tokenSet_35.member(LA(1))) && (_tokenSet_19.member(LA(2)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		matchOpExpression_AST = (AST)currentAST.root;
		returnAST = matchOpExpression_AST;
	}
	
	public final void aliasExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST aliasExpression_AST = null;
		
		unaryExpression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==K_AS) && (LA(2)==ID||LA(2)==NLS)) {
			AST tmp139_AST = null;
			tmp139_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp139_AST);
			match(K_AS);
			nls();
			AST tmp140_AST = null;
			tmp140_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp140_AST);
			match(ID);
		}
		else if ((_tokenSet_35.member(LA(1))) && (_tokenSet_19.member(LA(2)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		aliasExpression_AST = (AST)currentAST.root;
		returnAST = aliasExpression_AST;
	}
	
	public final void unaryExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST unaryExpression_AST = null;
		
		switch ( LA(1)) {
		case PLUS:
		{
			AST tmp141_AST = null;
			tmp141_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp141_AST);
			match(PLUS);
			if ( inputState.guessing==0 ) {
				tmp141_AST.setType(U_PLUS);
			}
			nls();
			unaryExpression();
			astFactory.addASTChild(currentAST, returnAST);
			unaryExpression_AST = (AST)currentAST.root;
			break;
		}
		case MINUS:
		{
			AST tmp142_AST = null;
			tmp142_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp142_AST);
			match(MINUS);
			if ( inputState.guessing==0 ) {
				tmp142_AST.setType(U_MINUS);
			}
			nls();
			unaryExpression();
			astFactory.addASTChild(currentAST, returnAST);
			unaryExpression_AST = (AST)currentAST.root;
			break;
		}
		case TILDE:
		{
			AST tmp143_AST = null;
			tmp143_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp143_AST);
			match(TILDE);
			nls();
			unaryExpression();
			astFactory.addASTChild(currentAST, returnAST);
			unaryExpression_AST = (AST)currentAST.root;
			break;
		}
		case LNOT:
		{
			AST tmp144_AST = null;
			tmp144_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp144_AST);
			match(LNOT);
			nls();
			unaryExpression();
			astFactory.addASTChild(currentAST, returnAST);
			unaryExpression_AST = (AST)currentAST.root;
			break;
		}
		case INTEGER:
		case ID:
		case URL:
		case STR_TQ_START:
		case STR_SQ_START:
		case REGEX_START:
		case FILE_PATH:
		case FILE_PATH_START:
		case LPAREN:
		case LBRACK:
		case LCURLY:
		case EMAIL:
		case TIME:
		case PERCENTAGE:
		case FLOAT:
		case PAIR:
		case DATE:
		case PAIR_LCURLY:
		case WELL_LCURLY:
		case NOT:
		case RFC822:
		case K_NULL:
		case K_NEW:
		case K_TRUE:
		case K_FALSE:
		case IN:
		case K_IF:
		case WHILE:
		case DO:
		case DEF:
		case K_CLASS:
		case K_MATCH:
		case K_ASSERT:
		case THIS:
		case SUPER:
		case IS:
		case REFRENCE_NAME:
		case IF:
		case FOR:
		case NEW:
		case STRING:
		case K_FLASE:
		case ELSE:
		case SIGN_LPAREN:
		case SIGN_LCURLY:
		case REGEX:
		{
			postfixExpression();
			astFactory.addASTChild(currentAST, returnAST);
			unaryExpression_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = unaryExpression_AST;
	}
	
	public final void postfixExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST postfixExpression_AST = null;
		AST pathExp_AST = null;
		Token  inc = null;
		AST inc_AST = null;
		Token  dec = null;
		AST dec_AST = null;
		
		pathExpression();
		pathExp_AST = (AST)returnAST;
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==INC) && (_tokenSet_35.member(LA(2)))) {
			inc = LT(1);
			inc_AST = astFactory.create(inc);
			astFactory.makeASTRoot(currentAST, inc_AST);
			match(INC);
			if ( inputState.guessing==0 ) {
				inc_AST.setType(POST_INC);
			}
		}
		else if ((LA(1)==DEC) && (_tokenSet_35.member(LA(2)))) {
			dec = LT(1);
			dec_AST = astFactory.create(dec);
			astFactory.makeASTRoot(currentAST, dec_AST);
			match(DEC);
			if ( inputState.guessing==0 ) {
				dec_AST.setType(POST_DEC);
			}
		}
		else if ((_tokenSet_35.member(LA(1))) && (_tokenSet_19.member(LA(2)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		postfixExpression_AST = (AST)currentAST.root;
		returnAST = postfixExpression_AST;
	}
	
	public final void pathExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST pathExpression_AST = null;
		AST left_AST = null;
		AST right1_AST = null;
		AST right2_AST = null;
		AST right3_AST = null;
		AST right4_AST = null;
		
		AST prefix = null;
		
		
		primaryExpression();
		left_AST = (AST)returnAST;
		if ( inputState.guessing==0 ) {
			prefix = left_AST;
		}
		{
		_loop263:
		do {
			boolean synPredMatched256 = false;
			if (((_tokenSet_49.member(LA(1))) && (_tokenSet_50.member(LA(2))))) {
				int _m256 = mark();
				synPredMatched256 = true;
				inputState.guessing++;
				try {
					{
					pathSymbol();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched256 = false;
				}
				rewind(_m256);
inputState.guessing--;
			}
			if ( synPredMatched256 ) {
				pathElement(prefix);
				right1_AST = (AST)returnAST;
				if ( inputState.guessing==0 ) {
					prefix = right1_AST;
				}
			}
			else {
				boolean synPredMatched258 = false;
				if (((_tokenSet_49.member(LA(1))) && (_tokenSet_50.member(LA(2))))) {
					int _m258 = mark();
					synPredMatched258 = true;
					inputState.guessing++;
					try {
						{
						match(LPAREN);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched258 = false;
					}
					rewind(_m258);
inputState.guessing--;
				}
				if ( synPredMatched258 ) {
					pathElement(prefix);
					right2_AST = (AST)returnAST;
					if ( inputState.guessing==0 ) {
						prefix = right2_AST;
					}
				}
				else {
					boolean synPredMatched260 = false;
					if (((_tokenSet_49.member(LA(1))) && (_tokenSet_50.member(LA(2))))) {
						int _m260 = mark();
						synPredMatched260 = true;
						inputState.guessing++;
						try {
							{
							match(LBRACK);
							}
						}
						catch (RecognitionException pe) {
							synPredMatched260 = false;
						}
						rewind(_m260);
inputState.guessing--;
					}
					if ( synPredMatched260 ) {
						pathElement(prefix);
						right3_AST = (AST)returnAST;
						if ( inputState.guessing==0 ) {
							prefix = right3_AST;
						}
					}
					else {
						boolean synPredMatched262 = false;
						if (((_tokenSet_49.member(LA(1))) && (_tokenSet_50.member(LA(2))))) {
							int _m262 = mark();
							synPredMatched262 = true;
							inputState.guessing++;
							try {
								{
								match(LCURLY);
								}
							}
							catch (RecognitionException pe) {
								synPredMatched262 = false;
							}
							rewind(_m262);
inputState.guessing--;
						}
						if ( synPredMatched262 ) {
							pathElement(prefix);
							right4_AST = (AST)returnAST;
							if ( inputState.guessing==0 ) {
								prefix = right4_AST;
							}
						}
						else {
							break _loop263;
						}
						}}}
					} while (true);
					}
					if ( inputState.guessing==0 ) {
						pathExpression_AST = (AST)currentAST.root;
						
									pathExpression_AST = prefix;
								
						currentAST.root = pathExpression_AST;
						currentAST.child = pathExpression_AST!=null &&pathExpression_AST.getFirstChild()!=null ?
							pathExpression_AST.getFirstChild() : pathExpression_AST;
						currentAST.advanceChildToEnd();
					}
					pathExpression_AST = (AST)currentAST.root;
					returnAST = pathExpression_AST;
				}
				
	public final void primaryExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST primaryExpression_AST = null;
		Token  lp = null;
		AST lp_AST = null;
		AST expr_AST = null;
		
		switch ( LA(1)) {
		case LCURLY:
		{
			hashMapConstructExpression();
			astFactory.addASTChild(currentAST, returnAST);
			primaryExpression_AST = (AST)currentAST.root;
			break;
		}
		case SIGN_LPAREN:
		case SIGN_LCURLY:
		{
			closureExpression();
			astFactory.addASTChild(currentAST, returnAST);
			primaryExpression_AST = (AST)currentAST.root;
			break;
		}
		case STR_TQ_START:
		case STR_SQ_START:
		{
			stringConstructorExpression();
			astFactory.addASTChild(currentAST, returnAST);
			primaryExpression_AST = (AST)currentAST.root;
			break;
		}
		case REGEX_START:
		{
			regexContructorExpression();
			astFactory.addASTChild(currentAST, returnAST);
			primaryExpression_AST = (AST)currentAST.root;
			break;
		}
		case FILE_PATH_START:
		{
			fileConstructorExpression();
			astFactory.addASTChild(currentAST, returnAST);
			primaryExpression_AST = (AST)currentAST.root;
			break;
		}
		case PAIR_LCURLY:
		case WELL_LCURLY:
		{
			pairExpression();
			astFactory.addASTChild(currentAST, returnAST);
			primaryExpression_AST = (AST)currentAST.root;
			break;
		}
		case K_NEW:
		{
			newExpression();
			astFactory.addASTChild(currentAST, returnAST);
			primaryExpression_AST = (AST)currentAST.root;
			break;
		}
		case LBRACK:
		{
			listExpression();
			astFactory.addASTChild(currentAST, returnAST);
			primaryExpression_AST = (AST)currentAST.root;
			break;
		}
		case LPAREN:
		{
			lp = LT(1);
			lp_AST = astFactory.create(lp);
			match(LPAREN);
			expression();
			expr_AST = (AST)returnAST;
			astFactory.addASTChild(currentAST, returnAST);
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				((SoyaCST) expr_AST).setFirst(lp_AST);
			}
			primaryExpression_AST = (AST)currentAST.root;
			break;
		}
		case K_IF:
		{
			ifStatement();
			astFactory.addASTChild(currentAST, returnAST);
			primaryExpression_AST = (AST)currentAST.root;
			break;
		}
		default:
			boolean synPredMatched282 = false;
			if (((_tokenSet_13.member(LA(1))) && (LA(2)==COLON||LA(2)==EQ_GT))) {
				int _m282 = mark();
				synPredMatched282 = true;
				inputState.guessing++;
				try {
					{
					hashMapEntryStart();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched282 = false;
				}
				rewind(_m282);
inputState.guessing--;
			}
			if ( synPredMatched282 ) {
				hashMapWithoutBoundary();
				astFactory.addASTChild(currentAST, returnAST);
				primaryExpression_AST = (AST)currentAST.root;
			}
			else if ((_tokenSet_51.member(LA(1))) && (_tokenSet_52.member(LA(2)))) {
				literal();
				astFactory.addASTChild(currentAST, returnAST);
				primaryExpression_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==ID) && (_tokenSet_35.member(LA(2)))) {
				identifier();
				astFactory.addASTChild(currentAST, returnAST);
				primaryExpression_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==THIS) && (_tokenSet_35.member(LA(2)))) {
				AST tmp146_AST = null;
				tmp146_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp146_AST);
				match(THIS);
				primaryExpression_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==SUPER) && (_tokenSet_35.member(LA(2)))) {
				AST tmp147_AST = null;
				tmp147_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp147_AST);
				match(SUPER);
				primaryExpression_AST = (AST)currentAST.root;
			}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = primaryExpression_AST;
	}
	
	public final void pathSymbol() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST pathSymbol_AST = null;
		
		switch ( LA(1)) {
		case NLS:
		case DOT:
		{
			nls();
			AST tmp148_AST = null;
			tmp148_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp148_AST);
			match(DOT);
			pathSymbol_AST = (AST)currentAST.root;
			break;
		}
		case STAR_DOT:
		{
			AST tmp149_AST = null;
			tmp149_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp149_AST);
			match(STAR_DOT);
			pathSymbol_AST = (AST)currentAST.root;
			break;
		}
		case OPTIONAL_DOT:
		{
			AST tmp150_AST = null;
			tmp150_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp150_AST);
			match(OPTIONAL_DOT);
			pathSymbol_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = pathSymbol_AST;
	}
	
	public final void pathElement(
		AST prefix
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST pathElement_AST = null;
		AST sym_AST = null;
		AST el_AST = null;
		AST mcp_AST = null;
		AST cblk_AST = null;
		AST idx_AST = null;
		
		AST symbol = null;
		Token first = LT(1);
		
		
		switch ( LA(1)) {
		case NLS:
		case DOT:
		case STAR_DOT:
		case OPTIONAL_DOT:
		{
			{
			pathSymbol();
			sym_AST = (AST)returnAST;
			astFactory.addASTChild(currentAST, returnAST);
			nls();
			namePart();
			el_AST = (AST)returnAST;
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				pathElement_AST = (AST)currentAST.root;
				
								symbol = sym_AST;
								pathElement_AST = (AST)astFactory.make( (new ASTArray(3)).add(node(symbol.getType(),symbol.getText(),prefix,LT(1))).add(prefix).add(el_AST));
							
				currentAST.root = pathElement_AST;
				currentAST.child = pathElement_AST!=null &&pathElement_AST.getFirstChild()!=null ?
					pathElement_AST.getFirstChild() : pathElement_AST;
				currentAST.advanceChildToEnd();
			}
			}
			pathElement_AST = (AST)currentAST.root;
			break;
		}
		case LPAREN:
		{
			{
			methodCallWithParen(prefix);
			mcp_AST = (AST)returnAST;
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				pathElement_AST = (AST)currentAST.root;
				
								pathElement_AST = mcp_AST;
							
				currentAST.root = pathElement_AST;
				currentAST.child = pathElement_AST!=null &&pathElement_AST.getFirstChild()!=null ?
					pathElement_AST.getFirstChild() : pathElement_AST;
				currentAST.advanceChildToEnd();
			}
			}
			pathElement_AST = (AST)currentAST.root;
			break;
		}
		case LCURLY:
		{
			callBlockExpression(prefix);
			cblk_AST = (AST)returnAST;
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				pathElement_AST = (AST)currentAST.root;
				
				//System.out.println("Call Closure");
							pathElement_AST = cblk_AST;
						
				currentAST.root = pathElement_AST;
				currentAST.child = pathElement_AST!=null &&pathElement_AST.getFirstChild()!=null ?
					pathElement_AST.getFirstChild() : pathElement_AST;
				currentAST.advanceChildToEnd();
			}
			pathElement_AST = (AST)currentAST.root;
			break;
		}
		case LBRACK:
		{
			{
			indexPropertyArgs(prefix);
			idx_AST = (AST)returnAST;
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				pathElement_AST = (AST)currentAST.root;
				
					pathElement_AST = idx_AST;
				
				currentAST.root = pathElement_AST;
				currentAST.child = pathElement_AST!=null &&pathElement_AST.getFirstChild()!=null ?
					pathElement_AST.getFirstChild() : pathElement_AST;
				currentAST.advanceChildToEnd();
			}
			}
			pathElement_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = pathElement_AST;
	}
	
	public final void namePart() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST namePart_AST = null;
		
		switch ( LA(1)) {
		case PLUS:
		case MINUS:
		case COLON:
		case COMMAR:
		case SEMI:
		case DOT:
		case QUESTION:
		case TILDE:
		case GT:
		case LT:
		case GE:
		case LE:
		case SR:
		case SL:
		case DIV:
		case STAR:
		{
			operatorAsMethodName();
			astFactory.addASTChild(currentAST, returnAST);
			namePart_AST = (AST)currentAST.root;
			break;
		}
		case NOT:
		case RFC822:
		case K_NULL:
		case K_TRUE:
		case IN:
		case WHILE:
		case DO:
		case DEF:
		case K_CLASS:
		case K_MATCH:
		case K_ASSERT:
		case THIS:
		case SUPER:
		case IS:
		case IF:
		case FOR:
		case NEW:
		case K_FLASE:
		case ELSE:
		{
			keywordAsMethodName();
			astFactory.addASTChild(currentAST, returnAST);
			namePart_AST = (AST)currentAST.root;
			break;
		}
		case REFRENCE_NAME:
		{
			AST tmp151_AST = null;
			tmp151_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp151_AST);
			match(REFRENCE_NAME);
			namePart_AST = (AST)currentAST.root;
			break;
		}
		case ID:
		{
			AST tmp152_AST = null;
			tmp152_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp152_AST);
			match(ID);
			namePart_AST = (AST)currentAST.root;
			break;
		}
		case STRING:
		{
			AST tmp153_AST = null;
			tmp153_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp153_AST);
			match(STRING);
			namePart_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = namePart_AST;
	}
	
	public final void methodCallWithParen(
		AST callee
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST methodCallWithParen_AST = null;
		AST al_AST = null;
		Token  rp = null;
		AST rp_AST = null;
		
		match(LPAREN);
		nls();
		{
		switch ( LA(1)) {
		case INTEGER:
		case ID:
		case URL:
		case STR_TQ_START:
		case STR_SQ_START:
		case REGEX_START:
		case FILE_PATH:
		case FILE_PATH_START:
		case LPAREN:
		case LBRACK:
		case LCURLY:
		case EMAIL:
		case TIME:
		case PERCENTAGE:
		case FLOAT:
		case PAIR:
		case DATE:
		case PAIR_LCURLY:
		case WELL_LCURLY:
		case PLUS:
		case MINUS:
		case NOT:
		case DB_DOT:
		case TILDE:
		case RFC822:
		case K_NULL:
		case K_NEW:
		case K_TRUE:
		case K_FALSE:
		case IN:
		case K_IF:
		case WHILE:
		case DO:
		case DEF:
		case K_CLASS:
		case K_MATCH:
		case K_ASSERT:
		case THIS:
		case SUPER:
		case IS:
		case REFRENCE_NAME:
		case IF:
		case FOR:
		case NEW:
		case DB_DOT_LT:
		case GT_DB_DOT:
		case GT_DB_DOT_LT:
		case LNOT:
		case STRING:
		case K_FLASE:
		case ELSE:
		case SIGN_LPAREN:
		case SIGN_LCURLY:
		case REGEX:
		{
			argList();
			al_AST = (AST)returnAST;
			break;
		}
		case RPAREN:
		case NLS:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		nls();
		rp = LT(1);
		rp_AST = astFactory.create(rp);
		match(RPAREN);
		if ( inputState.guessing==0 ) {
			methodCallWithParen_AST = (AST)currentAST.root;
			
					    if (callee != null && callee.getFirstChild() != null) {
			methodCallWithParen_AST = (AST)astFactory.make( (new ASTArray(3)).add(node(METHOD_CALL,"METHOD_CALL",callee.getFirstChild(),LT(1))).add(callee).add(al_AST));
						}
						else {
			methodCallWithParen_AST = (AST)astFactory.make( (new ASTArray(3)).add(node(METHOD_CALL,"METHOD_CALL",callee,LT(1))).add(callee).add(al_AST));
						}
					
			currentAST.root = methodCallWithParen_AST;
			currentAST.child = methodCallWithParen_AST!=null &&methodCallWithParen_AST.getFirstChild()!=null ?
				methodCallWithParen_AST.getFirstChild() : methodCallWithParen_AST;
			currentAST.advanceChildToEnd();
		}
		methodCallWithParen_AST = (AST)currentAST.root;
		returnAST = methodCallWithParen_AST;
	}
	
	public final void callBlockExpression(
		AST callee
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST callBlockExpression_AST = null;
		AST blk_AST = null;
		
		Token first = LT(1);
		
		
		curlyBlockStatement();
		blk_AST = (AST)returnAST;
		astFactory.addASTChild(currentAST, returnAST);
		if ( inputState.guessing==0 ) {
			callBlockExpression_AST = (AST)currentAST.root;
			
			if (callee != null && callee.getType() == METHOD_CALL) {
			AST closure = (AST)astFactory.make( (new ASTArray(2)).add(node(CLOSURE,"CLOSURE",first,LT(1))).add(blk_AST));
			callBlockExpression_AST = (AST)astFactory.make( (new ASTArray(3)).add(node(METHOD_CALL,"METHOD_CALL",callee.getFirstChild(),LT(1))).add(callee.getFirstChild()).add(closure));
			}
			else {
			AST closure = (AST)astFactory.make( (new ASTArray(2)).add(node(CLOSURE,"CLOSURE",first,LT(1))).add(blk_AST));
			callBlockExpression_AST = (AST)astFactory.make( (new ASTArray(3)).add(node(METHOD_CALL,"METHOD_CALL",callee,LT(1))).add(callee).add(closure));
			}
			
			currentAST.root = callBlockExpression_AST;
			currentAST.child = callBlockExpression_AST!=null &&callBlockExpression_AST.getFirstChild()!=null ?
				callBlockExpression_AST.getFirstChild() : callBlockExpression_AST;
			currentAST.advanceChildToEnd();
		}
		callBlockExpression_AST = (AST)currentAST.root;
		returnAST = callBlockExpression_AST;
	}
	
	public final void indexPropertyArgs(
		AST indexee
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST indexPropertyArgs_AST = null;
		AST al_AST = null;
		
		match(LBRACK);
		argList();
		al_AST = (AST)returnAST;
		astFactory.addASTChild(currentAST, returnAST);
		match(RBRACK);
		if ( inputState.guessing==0 ) {
			indexPropertyArgs_AST = (AST)currentAST.root;
			
				        indexPropertyArgs_AST = (AST)astFactory.make( (new ASTArray(3)).add(node(INDEX_OP,"INDEX_OP",indexee,LT(1))).add(indexee).add(al_AST));
			
			currentAST.root = indexPropertyArgs_AST;
			currentAST.child = indexPropertyArgs_AST!=null &&indexPropertyArgs_AST.getFirstChild()!=null ?
				indexPropertyArgs_AST.getFirstChild() : indexPropertyArgs_AST;
			currentAST.advanceChildToEnd();
		}
		indexPropertyArgs_AST = (AST)currentAST.root;
		returnAST = indexPropertyArgs_AST;
	}
	
	public final void operatorAsMethodName() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST operatorAsMethodName_AST = null;
		
		switch ( LA(1)) {
		case PLUS:
		{
			AST tmp157_AST = null;
			tmp157_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp157_AST);
			match(PLUS);
			operatorAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case MINUS:
		{
			AST tmp158_AST = null;
			tmp158_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp158_AST);
			match(MINUS);
			operatorAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case STAR:
		{
			AST tmp159_AST = null;
			tmp159_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp159_AST);
			match(STAR);
			operatorAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case DIV:
		{
			AST tmp160_AST = null;
			tmp160_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp160_AST);
			match(DIV);
			operatorAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case COMMAR:
		{
			AST tmp161_AST = null;
			tmp161_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp161_AST);
			match(COMMAR);
			operatorAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case COLON:
		{
			AST tmp162_AST = null;
			tmp162_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp162_AST);
			match(COLON);
			operatorAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case DOT:
		{
			AST tmp163_AST = null;
			tmp163_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp163_AST);
			match(DOT);
			operatorAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case SEMI:
		{
			AST tmp164_AST = null;
			tmp164_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp164_AST);
			match(SEMI);
			operatorAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case QUESTION:
		{
			AST tmp165_AST = null;
			tmp165_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp165_AST);
			match(QUESTION);
			operatorAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case TILDE:
		{
			AST tmp166_AST = null;
			tmp166_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp166_AST);
			match(TILDE);
			operatorAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case GT:
		{
			AST tmp167_AST = null;
			tmp167_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp167_AST);
			match(GT);
			operatorAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case LT:
		{
			AST tmp168_AST = null;
			tmp168_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp168_AST);
			match(LT);
			operatorAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case GE:
		{
			AST tmp169_AST = null;
			tmp169_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp169_AST);
			match(GE);
			operatorAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case LE:
		{
			AST tmp170_AST = null;
			tmp170_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp170_AST);
			match(LE);
			operatorAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case SR:
		{
			AST tmp171_AST = null;
			tmp171_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp171_AST);
			match(SR);
			operatorAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case SL:
		{
			AST tmp172_AST = null;
			tmp172_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp172_AST);
			match(SL);
			operatorAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = operatorAsMethodName_AST;
	}
	
	public final void keywordAsMethodName() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST keywordAsMethodName_AST = null;
		
		switch ( LA(1)) {
		case K_TRUE:
		{
			AST tmp173_AST = null;
			tmp173_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp173_AST);
			match(K_TRUE);
			keywordAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case K_FLASE:
		{
			AST tmp174_AST = null;
			tmp174_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp174_AST);
			match(K_FLASE);
			keywordAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case K_NULL:
		{
			AST tmp175_AST = null;
			tmp175_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp175_AST);
			match(K_NULL);
			keywordAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case IN:
		{
			AST tmp176_AST = null;
			tmp176_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp176_AST);
			match(IN);
			keywordAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case IF:
		{
			AST tmp177_AST = null;
			tmp177_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp177_AST);
			match(IF);
			keywordAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case ELSE:
		{
			AST tmp178_AST = null;
			tmp178_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp178_AST);
			match(ELSE);
			keywordAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case FOR:
		{
			AST tmp179_AST = null;
			tmp179_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp179_AST);
			match(FOR);
			keywordAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case WHILE:
		{
			AST tmp180_AST = null;
			tmp180_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp180_AST);
			match(WHILE);
			keywordAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case DO:
		{
			AST tmp181_AST = null;
			tmp181_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp181_AST);
			match(DO);
			keywordAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case DEF:
		{
			AST tmp182_AST = null;
			tmp182_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp182_AST);
			match(DEF);
			keywordAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case NEW:
		{
			AST tmp183_AST = null;
			tmp183_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp183_AST);
			match(NEW);
			keywordAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case K_CLASS:
		{
			AST tmp184_AST = null;
			tmp184_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp184_AST);
			match(K_CLASS);
			keywordAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case THIS:
		{
			AST tmp185_AST = null;
			tmp185_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp185_AST);
			match(THIS);
			keywordAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case SUPER:
		{
			AST tmp186_AST = null;
			tmp186_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp186_AST);
			match(SUPER);
			keywordAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case IS:
		{
			AST tmp187_AST = null;
			tmp187_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp187_AST);
			match(IS);
			keywordAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case NOT:
		{
			AST tmp188_AST = null;
			tmp188_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp188_AST);
			match(NOT);
			keywordAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case K_MATCH:
		{
			AST tmp189_AST = null;
			tmp189_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp189_AST);
			match(K_MATCH);
			keywordAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case K_ASSERT:
		{
			AST tmp190_AST = null;
			tmp190_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp190_AST);
			match(K_ASSERT);
			keywordAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		case RFC822:
		{
			AST tmp191_AST = null;
			tmp191_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp191_AST);
			match(RFC822);
			keywordAsMethodName_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = keywordAsMethodName_AST;
	}
	
	public final void hashMapEntryStart() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST hashMapEntryStart_AST = null;
		
		hashMapKey();
		hashMapSeperator();
		nls();
		hashMapValue();
		returnAST = hashMapEntryStart_AST;
	}
	
	public final void closureExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST closureExpression_AST = null;
		AST paramList_AST = null;
		AST blk_AST = null;
		AST sts_AST = null;
		
		Token first = LT(1);
		
		
		switch ( LA(1)) {
		case SIGN_LPAREN:
		{
			match(SIGN_LPAREN);
			nls();
			{
			switch ( LA(1)) {
			case INTEGER:
			case ID:
			case URL:
			case FILE_PATH:
			case EMAIL:
			case TIME:
			case PERCENTAGE:
			case FLOAT:
			case PAIR:
			case DATE:
			case K_NULL:
			case K_TRUE:
			case K_FALSE:
			case REFRENCE_NAME:
			case STRING:
			case REGEX:
			{
				parameterDefinitionList();
				paramList_AST = (AST)returnAST;
				nls();
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(RPAREN);
			nls();
			openBlockStatement();
			blk_AST = (AST)returnAST;
			if ( inputState.guessing==0 ) {
				closureExpression_AST = (AST)currentAST.root;
				
				closureExpression_AST = (AST)astFactory.make( (new ASTArray(3)).add(node(CLOSURE,"CLOSURE",first,LT(1))).add(paramList_AST).add(blk_AST));
				
				currentAST.root = closureExpression_AST;
				currentAST.child = closureExpression_AST!=null &&closureExpression_AST.getFirstChild()!=null ?
					closureExpression_AST.getFirstChild() : closureExpression_AST;
				currentAST.advanceChildToEnd();
			}
			closureExpression_AST = (AST)currentAST.root;
			break;
		}
		case SIGN_LCURLY:
		{
			AST tmp194_AST = null;
			tmp194_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp194_AST);
			match(SIGN_LCURLY);
			nls();
			statements();
			sts_AST = (AST)returnAST;
			astFactory.addASTChild(currentAST, returnAST);
			nls();
			match(RCURLY);
			if ( inputState.guessing==0 ) {
				closureExpression_AST = (AST)currentAST.root;
				
				AST block = (AST)astFactory.make( (new ASTArray(2)).add(node(BLOCK,"BLOCK",first)).add(sts_AST));
				closureExpression_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(CLOSURE,"CLOSURE",first,LT(1))).add(block));
				
				currentAST.root = closureExpression_AST;
				currentAST.child = closureExpression_AST!=null &&closureExpression_AST.getFirstChild()!=null ?
					closureExpression_AST.getFirstChild() : closureExpression_AST;
				currentAST.advanceChildToEnd();
			}
			closureExpression_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = closureExpression_AST;
	}
	
	public final void stringConstructorExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST stringConstructorExpression_AST = null;
		
		Token first = LT(1);
		
		
		{
		switch ( LA(1)) {
		case STR_SQ_START:
		{
			AST tmp196_AST = null;
			tmp196_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp196_AST);
			match(STR_SQ_START);
			break;
		}
		case STR_TQ_START:
		{
			AST tmp197_AST = null;
			tmp197_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp197_AST);
			match(STR_TQ_START);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		expression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop290:
		do {
			if ((LA(1)==STR_PART_MIDDLE)) {
				AST tmp198_AST = null;
				tmp198_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp198_AST);
				match(STR_PART_MIDDLE);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop290;
			}
			
		} while (true);
		}
		AST tmp199_AST = null;
		tmp199_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp199_AST);
		match(STR_PART_END);
		if ( inputState.guessing==0 ) {
			stringConstructorExpression_AST = (AST)currentAST.root;
			
				        stringConstructorExpression_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(STRING_CONSTRUCTOR,"STRING_CONSTRUCTOR",first,LT(1))).add(stringConstructorExpression_AST));
				
			currentAST.root = stringConstructorExpression_AST;
			currentAST.child = stringConstructorExpression_AST!=null &&stringConstructorExpression_AST.getFirstChild()!=null ?
				stringConstructorExpression_AST.getFirstChild() : stringConstructorExpression_AST;
			currentAST.advanceChildToEnd();
		}
		stringConstructorExpression_AST = (AST)currentAST.root;
		returnAST = stringConstructorExpression_AST;
	}
	
	public final void regexContructorExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST regexContructorExpression_AST = null;
		
		Token first = LT(1);
		
		
		AST tmp200_AST = null;
		tmp200_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp200_AST);
		match(REGEX_START);
		expression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop293:
		do {
			if ((LA(1)==REGEX_MIDDLE)) {
				AST tmp201_AST = null;
				tmp201_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp201_AST);
				match(REGEX_MIDDLE);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop293;
			}
			
		} while (true);
		}
		AST tmp202_AST = null;
		tmp202_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp202_AST);
		match(REGEX_END);
		if ( inputState.guessing==0 ) {
			regexContructorExpression_AST = (AST)currentAST.root;
			
			regexContructorExpression_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(REGEX_CONSTRUCTIOR,"REGEX_CONSTRUCTIOR",first,LT(1))).add(regexContructorExpression_AST));
			
			currentAST.root = regexContructorExpression_AST;
			currentAST.child = regexContructorExpression_AST!=null &&regexContructorExpression_AST.getFirstChild()!=null ?
				regexContructorExpression_AST.getFirstChild() : regexContructorExpression_AST;
			currentAST.advanceChildToEnd();
		}
		regexContructorExpression_AST = (AST)currentAST.root;
		returnAST = regexContructorExpression_AST;
	}
	
	public final void fileConstructorExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST fileConstructorExpression_AST = null;
		
		Token first = LT(1);
		
		
		AST tmp203_AST = null;
		tmp203_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp203_AST);
		match(FILE_PATH_START);
		expression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop296:
		do {
			if ((LA(1)==FILE_PATH_MIDDLE)) {
				AST tmp204_AST = null;
				tmp204_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp204_AST);
				match(FILE_PATH_MIDDLE);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop296;
			}
			
		} while (true);
		}
		{
		switch ( LA(1)) {
		case FILE_PATH_END:
		{
			AST tmp205_AST = null;
			tmp205_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp205_AST);
			match(FILE_PATH_END);
			break;
		}
		case RCURLY:
		{
			match(RCURLY);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		if ( inputState.guessing==0 ) {
			fileConstructorExpression_AST = (AST)currentAST.root;
			
				        fileConstructorExpression_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(FILE_CONSTRUCTOR,"FILE_CONSTRUCTOR",first,LT(1))).add(fileConstructorExpression_AST));
				
			currentAST.root = fileConstructorExpression_AST;
			currentAST.child = fileConstructorExpression_AST!=null &&fileConstructorExpression_AST.getFirstChild()!=null ?
				fileConstructorExpression_AST.getFirstChild() : fileConstructorExpression_AST;
			currentAST.advanceChildToEnd();
		}
		fileConstructorExpression_AST = (AST)currentAST.root;
		returnAST = fileConstructorExpression_AST;
	}
	
	public final void identifier() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST identifier_AST = null;
		
		AST tmp207_AST = null;
		tmp207_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp207_AST);
		match(ID);
		identifier_AST = (AST)currentAST.root;
		returnAST = identifier_AST;
	}
	
	public final void pairExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST pairExpression_AST = null;
		AST expr_AST = null;
		AST expr2_AST = null;
		
		Token first = LT(1);
		
		
		{
		switch ( LA(1)) {
		case PAIR_LCURLY:
		{
			AST tmp208_AST = null;
			tmp208_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp208_AST);
			match(PAIR_LCURLY);
			break;
		}
		case WELL_LCURLY:
		{
			match(WELL_LCURLY);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		expression();
		expr_AST = (AST)returnAST;
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop302:
		do {
			if ((LA(1)==RCURLY_X_PAIR_LCURLY||LA(1)==RCURLY_X_LCURLY)) {
				{
				switch ( LA(1)) {
				case RCURLY_X_PAIR_LCURLY:
				{
					AST tmp210_AST = null;
					tmp210_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp210_AST);
					match(RCURLY_X_PAIR_LCURLY);
					break;
				}
				case RCURLY_X_LCURLY:
				{
					match(RCURLY_X_LCURLY);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				expression();
				expr2_AST = (AST)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop302;
			}
			
		} while (true);
		}
		{
		switch ( LA(1)) {
		case RCURLY:
		{
			match(RCURLY);
			break;
		}
		case RCURLY_X_PAIR:
		{
			AST tmp213_AST = null;
			tmp213_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp213_AST);
			match(RCURLY_X_PAIR);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		if ( inputState.guessing==0 ) {
			pairExpression_AST = (AST)currentAST.root;
			
						pairExpression_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(PAIR_CONSTRUCTOR,"PAIR_CONSTRUCTOR",first,LT(1))).add(pairExpression_AST));
					
			currentAST.root = pairExpression_AST;
			currentAST.child = pairExpression_AST!=null &&pairExpression_AST.getFirstChild()!=null ?
				pairExpression_AST.getFirstChild() : pairExpression_AST;
			currentAST.advanceChildToEnd();
		}
		pairExpression_AST = (AST)currentAST.root;
		returnAST = pairExpression_AST;
	}
	
	public final void newExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST newExpression_AST = null;
		AST t_AST = null;
		AST as_AST = null;
		
		AST args = null;
		Token first = LT(1);
		
		
		match(K_NEW);
		nls();
		type();
		t_AST = (AST)returnAST;
		{
		if ((LA(1)==LPAREN) && (_tokenSet_53.member(LA(2)))) {
			methodCallWithParen(null);
			as_AST = (AST)returnAST;
			if ( inputState.guessing==0 ) {
				args = as_AST.getFirstChild();
			}
		}
		else if ((_tokenSet_35.member(LA(1))) && (_tokenSet_19.member(LA(2)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		if ( inputState.guessing==0 ) {
			newExpression_AST = (AST)currentAST.root;
			
			newExpression_AST = (AST)astFactory.make( (new ASTArray(3)).add(node(NEW,"NEW",first,LT(1))).add(t_AST).add(args));
				
			currentAST.root = newExpression_AST;
			currentAST.child = newExpression_AST!=null &&newExpression_AST.getFirstChild()!=null ?
				newExpression_AST.getFirstChild() : newExpression_AST;
			currentAST.advanceChildToEnd();
		}
		newExpression_AST = (AST)currentAST.root;
		returnAST = newExpression_AST;
	}
	
	public final void listExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST listExpression_AST = null;
		
		Token first = LT(1);
		
		
		match(LBRACK);
		nls();
		{
		switch ( LA(1)) {
		case INTEGER:
		case ID:
		case URL:
		case STR_TQ_START:
		case STR_SQ_START:
		case REGEX_START:
		case FILE_PATH:
		case FILE_PATH_START:
		case LPAREN:
		case LBRACK:
		case LCURLY:
		case EMAIL:
		case TIME:
		case PERCENTAGE:
		case FLOAT:
		case PAIR:
		case DATE:
		case PAIR_LCURLY:
		case WELL_LCURLY:
		case PLUS:
		case MINUS:
		case NOT:
		case DB_DOT:
		case TILDE:
		case RFC822:
		case K_NULL:
		case K_NEW:
		case K_TRUE:
		case K_FALSE:
		case IN:
		case K_IF:
		case WHILE:
		case DO:
		case DEF:
		case K_CLASS:
		case K_MATCH:
		case K_ASSERT:
		case THIS:
		case SUPER:
		case IS:
		case REFRENCE_NAME:
		case IF:
		case FOR:
		case NEW:
		case DB_DOT_LT:
		case GT_DB_DOT:
		case GT_DB_DOT_LT:
		case LNOT:
		case STRING:
		case K_FLASE:
		case ELSE:
		case SIGN_LPAREN:
		case SIGN_LCURLY:
		case REGEX:
		{
			argument();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop308:
			do {
				if ((LA(1)==NLS||LA(1)==COMMAR) && (_tokenSet_15.member(LA(2)))) {
					{
					switch ( LA(1)) {
					case COMMAR:
					{
						match(COMMAR);
						break;
					}
					case NLS:
					{
						match(NLS);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					argument();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop308;
				}
				
			} while (true);
			}
			break;
		}
		case RBRACK:
		case NLS:
		case THREE_DOT:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		{
		switch ( LA(1)) {
		case THREE_DOT:
		{
			AST tmp218_AST = null;
			tmp218_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp218_AST);
			match(THREE_DOT);
			argument();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case RBRACK:
		case NLS:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		nls();
		match(RBRACK);
		if ( inputState.guessing==0 ) {
			listExpression_AST = (AST)currentAST.root;
			
			listExpression_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(LIST,"LIST",first,LT(1))).add(listExpression_AST));
				
			currentAST.root = listExpression_AST;
			currentAST.child = listExpression_AST!=null &&listExpression_AST.getFirstChild()!=null ?
				listExpression_AST.getFirstChild() : listExpression_AST;
			currentAST.advanceChildToEnd();
		}
		listExpression_AST = (AST)currentAST.root;
		returnAST = listExpression_AST;
	}
	
	public final void argument() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST argument_AST = null;
		AST en_AST = null;
		AST expr_AST = null;
		Token  n = null;
		AST n_AST = null;
		
		Token first = LT(1);
		AST expr = null;
		
		
		boolean synPredMatched351 = false;
		if (((_tokenSet_13.member(LA(1))) && (LA(2)==COLON||LA(2)==EQ_GT))) {
			int _m351 = mark();
			synPredMatched351 = true;
			inputState.guessing++;
			try {
				{
				hashMapEntryStart();
				}
			}
			catch (RecognitionException pe) {
				synPredMatched351 = false;
			}
			rewind(_m351);
inputState.guessing--;
		}
		if ( synPredMatched351 ) {
			hashMapEntry();
			en_AST = (AST)returnAST;
			if ( inputState.guessing==0 ) {
				argument_AST = (AST)currentAST.root;
				
				argument_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(NAMED_ARG,"NAMED_ARG",first,LT(1))).add(en_AST));
				
				currentAST.root = argument_AST;
				currentAST.child = argument_AST!=null &&argument_AST.getFirstChild()!=null ?
					argument_AST.getFirstChild() : argument_AST;
				currentAST.advanceChildToEnd();
			}
		}
		else if ((_tokenSet_15.member(LA(1))) && (_tokenSet_43.member(LA(2)))) {
			expression();
			expr_AST = (AST)returnAST;
			{
			if ((LA(1)==ID) && (_tokenSet_35.member(LA(2)))) {
				n = LT(1);
				n_AST = astFactory.create(n);
				match(ID);
				if ( inputState.guessing==0 ) {
					argument_AST = (AST)currentAST.root;
					
					argument_AST = (AST)astFactory.make( (new ASTArray(3)).add(node(MATCH_VAR_DEF,"MATCH_VAR_DEF",first,LT(1))).add(expr_AST).add(n_AST));
						
					currentAST.root = argument_AST;
					currentAST.child = argument_AST!=null &&argument_AST.getFirstChild()!=null ?
						argument_AST.getFirstChild() : argument_AST;
					currentAST.advanceChildToEnd();
				}
			}
			else if ((_tokenSet_35.member(LA(1))) && (_tokenSet_19.member(LA(2)))) {
				if ( inputState.guessing==0 ) {
					argument_AST = (AST)currentAST.root;
					
						    argument_AST = expr_AST;
						
					currentAST.root = argument_AST;
					currentAST.child = argument_AST!=null &&argument_AST.getFirstChild()!=null ?
						argument_AST.getFirstChild() : argument_AST;
					currentAST.advanceChildToEnd();
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		returnAST = argument_AST;
	}
	
	public final void hashMapEntryList() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST hashMapEntryList_AST = null;
		
		Token first = LT(1);
		
		
		hashMapEntry();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop325:
		do {
			boolean synPredMatched322 = false;
			if (((LA(1)==NLS||LA(1)==COMMAR) && (_tokenSet_30.member(LA(2))))) {
				int _m322 = mark();
				synPredMatched322 = true;
				inputState.guessing++;
				try {
					{
					switch ( LA(1)) {
					case COMMAR:
					{
						match(COMMAR);
						nls();
						break;
					}
					case NLS:
					{
						match(NLS);
						hashMapEntryStart();
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
				}
				catch (RecognitionException pe) {
					synPredMatched322 = false;
				}
				rewind(_m322);
inputState.guessing--;
			}
			if ( synPredMatched322 ) {
				{
				{
				switch ( LA(1)) {
				case COMMAR:
				{
					match(COMMAR);
					nls();
					break;
				}
				case NLS:
				{
					match(NLS);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				hashMapEntry();
				astFactory.addASTChild(currentAST, returnAST);
				}
			}
			else {
				break _loop325;
			}
			
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			hashMapEntryList_AST = (AST)currentAST.root;
			
						hashMapEntryList_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(HASH,"HASH",first,LT(1))).add(hashMapEntryList_AST));
					
			currentAST.root = hashMapEntryList_AST;
			currentAST.child = hashMapEntryList_AST!=null &&hashMapEntryList_AST.getFirstChild()!=null ?
				hashMapEntryList_AST.getFirstChild() : hashMapEntryList_AST;
			currentAST.advanceChildToEnd();
		}
		hashMapEntryList_AST = (AST)currentAST.root;
		returnAST = hashMapEntryList_AST;
	}
	
	public final void hashMapWithIndentation() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST hashMapWithIndentation_AST = null;
		
		Token first = null;
		
		
		match(INDENT);
		if ( inputState.guessing==0 ) {
			
				        first = LT(1);
				
		}
		hashMapEntry();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop318:
		do {
			if ((LA(1)==NLS||LA(1)==COMMAR) && (_tokenSet_13.member(LA(2)))) {
				{
				switch ( LA(1)) {
				case COMMAR:
				{
					match(COMMAR);
					break;
				}
				case NLS:
				{
					match(NLS);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				hashMapEntry();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop318;
			}
			
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			hashMapWithIndentation_AST = (AST)currentAST.root;
			
						hashMapWithIndentation_AST = (AST)astFactory.make( (new ASTArray(2)).add(node(HASH,"HASH",first,LT(1))).add(hashMapWithIndentation_AST));
					
			currentAST.root = hashMapWithIndentation_AST;
			currentAST.child = hashMapWithIndentation_AST!=null &&hashMapWithIndentation_AST.getFirstChild()!=null ?
				hashMapWithIndentation_AST.getFirstChild() : hashMapWithIndentation_AST;
			currentAST.advanceChildToEnd();
		}
		nls();
		match(DEDENT);
		hashMapWithIndentation_AST = (AST)currentAST.root;
		returnAST = hashMapWithIndentation_AST;
	}
	
	public final void hashMapSeperator() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST hashMapSeperator_AST = null;
		
		switch ( LA(1)) {
		case COLON:
		{
			AST tmp226_AST = null;
			tmp226_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp226_AST);
			match(COLON);
			hashMapSeperator_AST = (AST)currentAST.root;
			break;
		}
		case EQ_GT:
		{
			AST tmp227_AST = null;
			tmp227_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp227_AST);
			match(EQ_GT);
			hashMapSeperator_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = hashMapSeperator_AST;
	}
	
	public final void hashMapValue() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST hashMapValue_AST = null;
		
		boolean synPredMatched331 = false;
		if (((LA(1)==INDENT) && (_tokenSet_13.member(LA(2))))) {
			int _m331 = mark();
			synPredMatched331 = true;
			inputState.guessing++;
			try {
				{
				match(INDENT);
				hashMapEntryStart();
				}
			}
			catch (RecognitionException pe) {
				synPredMatched331 = false;
			}
			rewind(_m331);
inputState.guessing--;
		}
		if ( synPredMatched331 ) {
			hashMapWithIndentation();
			astFactory.addASTChild(currentAST, returnAST);
			hashMapValue_AST = (AST)currentAST.root;
		}
		else {
			boolean synPredMatched333 = false;
			if (((LA(1)==INDENT) && (LA(2)==STAR))) {
				int _m333 = mark();
				synPredMatched333 = true;
				inputState.guessing++;
				try {
					{
					match(INDENT);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched333 = false;
				}
				rewind(_m333);
inputState.guessing--;
			}
			if ( synPredMatched333 ) {
				starListWithIndentation();
				astFactory.addASTChild(currentAST, returnAST);
				hashMapValue_AST = (AST)currentAST.root;
			}
			else if ((_tokenSet_15.member(LA(1)))) {
				argument();
				astFactory.addASTChild(currentAST, returnAST);
				hashMapValue_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			returnAST = hashMapValue_AST;
		}
		
	public final void starListWithIndentation() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST starListWithIndentation_AST = null;
		
		match(INDENT);
		starList();
		astFactory.addASTChild(currentAST, returnAST);
		nls();
		match(DEDENT);
		starListWithIndentation_AST = (AST)currentAST.root;
		returnAST = starListWithIndentation_AST;
	}
	
	public final void stringLiteral() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST stringLiteral_AST = null;
		
		AST tmp230_AST = null;
		tmp230_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp230_AST);
		match(STRING);
		if ( inputState.guessing==0 ) {
			stringLiteral_AST = (AST)currentAST.root;
			addEndLineColumn(stringLiteral_AST, LT(1));
		}
		stringLiteral_AST = (AST)currentAST.root;
		returnAST = stringLiteral_AST;
	}
	
	public final void emailLiteral() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST emailLiteral_AST = null;
		
		AST tmp231_AST = null;
		tmp231_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp231_AST);
		match(EMAIL);
		if ( inputState.guessing==0 ) {
			emailLiteral_AST = (AST)currentAST.root;
			addEndLineColumn(emailLiteral_AST, LT(1));
		}
		emailLiteral_AST = (AST)currentAST.root;
		returnAST = emailLiteral_AST;
	}
	
	public final void numberLiteral() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST numberLiteral_AST = null;
		
		switch ( LA(1)) {
		case INTEGER:
		{
			AST tmp232_AST = null;
			tmp232_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp232_AST);
			match(INTEGER);
			numberLiteral_AST = (AST)currentAST.root;
			break;
		}
		case FLOAT:
		{
			AST tmp233_AST = null;
			tmp233_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp233_AST);
			match(FLOAT);
			numberLiteral_AST = (AST)currentAST.root;
			break;
		}
		case PERCENTAGE:
		{
			AST tmp234_AST = null;
			tmp234_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp234_AST);
			match(PERCENTAGE);
			numberLiteral_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = numberLiteral_AST;
	}
	
	public final void urlLiteral() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST urlLiteral_AST = null;
		
		AST tmp235_AST = null;
		tmp235_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp235_AST);
		match(URL);
		if ( inputState.guessing==0 ) {
			urlLiteral_AST = (AST)currentAST.root;
			addEndLineColumn(urlLiteral_AST, LT(1));
		}
		urlLiteral_AST = (AST)currentAST.root;
		returnAST = urlLiteral_AST;
	}
	
	public final void starListItem() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST starListItem_AST = null;
		
		match(STAR);
		nls();
		match(INDENT);
		{
		if ((_tokenSet_13.member(LA(1))) && (LA(2)==COLON||LA(2)==EQ_GT)) {
			yamlHashStatement();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==STAR)) {
			starList();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((_tokenSet_15.member(LA(1))) && (_tokenSet_54.member(LA(2)))) {
			expression();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		nls();
		match(DEDENT);
		starListItem_AST = (AST)currentAST.root;
		returnAST = starListItem_AST;
	}
	
	public final void referenceLiteral() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST referenceLiteral_AST = null;
		
		AST tmp239_AST = null;
		tmp239_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp239_AST);
		match(REFRENCE_NAME);
		referenceLiteral_AST = (AST)currentAST.root;
		returnAST = referenceLiteral_AST;
	}
	
	public final void regexLiteral() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST regexLiteral_AST = null;
		
		AST tmp240_AST = null;
		tmp240_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp240_AST);
		match(REGEX);
		regexLiteral_AST = (AST)currentAST.root;
		returnAST = regexLiteral_AST;
	}
	
	public final void dateLiteral() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST dateLiteral_AST = null;
		
		AST tmp241_AST = null;
		tmp241_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp241_AST);
		match(DATE);
		{
		switch ( LA(1)) {
		case TIME:
		{
			AST tmp242_AST = null;
			tmp242_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp242_AST);
			match(TIME);
			break;
		}
		case EOF:
		case DEDENT:
		case ID:
		case STR_TQ_START:
		case STR_SQ_START:
		case STR_PART_MIDDLE:
		case STR_PART_END:
		case REGEX_END:
		case REGEX_MIDDLE:
		case FILE_PATH_MIDDLE:
		case FILE_PATH_END:
		case LPAREN:
		case RPAREN:
		case LBRACK:
		case RBRACK:
		case LCURLY:
		case RCURLY:
		case NLS:
		case RCURLY_X_PAIR:
		case RCURLY_X_PAIR_LCURLY:
		case RCURLY_X_LCURLY:
		case ASSIGN:
		case PLUS:
		case MINUS:
		case EQUAL:
		case NOT_EQUAL:
		case MOD:
		case COLON:
		case COMMAR:
		case SEMI:
		case DOT:
		case DB_DOT:
		case QUESTION:
		case GT:
		case LT:
		case GE:
		case LE:
		case SR:
		case SL:
		case INC:
		case DEC:
		case DB_STAR:
		case STAR_DOT:
		case OPTIONAL_DOT:
		case PLUS_ASSIGN:
		case MINUS_ASSIGN:
		case STAR_ASSIGN:
		case DIV_ASSIGN:
		case RFC822:
		case DIV:
		case REGEX_MATCH:
		case REGEX_NOT_MATCH:
		case IN:
		case XOR:
		case INSTANCEOF:
		case K_STEP:
		case K_AS:
		case K_MATCH:
		case IS:
		case KAND:
		case KOR:
		case MOR:
		case RARROW:
		case INIT_ASSIGN:
		case STAR:
		case QUESTION_COLON:
		case MAND:
		case LOR:
		case LAND:
		case BOR:
		case BAND:
		case DB_DOT_LT:
		case GT_DB_DOT:
		case GT_DB_DOT_LT:
		case BSL:
		case BSR:
		case POW:
		case STRING:
		case THREE_DOT:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		{
		switch ( LA(1)) {
		case RFC822:
		{
			AST tmp243_AST = null;
			tmp243_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp243_AST);
			match(RFC822);
			break;
		}
		case STRING:
		{
			stringLiteral();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case STR_TQ_START:
		case STR_SQ_START:
		{
			stringConstructorExpression();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		default:
			if ((LA(1)==ID) && (_tokenSet_35.member(LA(2)))) {
				AST tmp244_AST = null;
				tmp244_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp244_AST);
				match(ID);
			}
			else if ((_tokenSet_35.member(LA(1))) && (_tokenSet_19.member(LA(2)))) {
			}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		if ( inputState.guessing==0 ) {
			dateLiteral_AST = (AST)currentAST.root;
			addEndLineColumn(dateLiteral_AST, LT(1));
		}
		dateLiteral_AST = (AST)currentAST.root;
		returnAST = dateLiteral_AST;
	}
	
	public final void timeLiteral() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST timeLiteral_AST = null;
		
		AST tmp245_AST = null;
		tmp245_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp245_AST);
		match(TIME);
		if ( inputState.guessing==0 ) {
			timeLiteral_AST = (AST)currentAST.root;
			addEndLineColumn(timeLiteral_AST, LT(1));
		}
		timeLiteral_AST = (AST)currentAST.root;
		returnAST = timeLiteral_AST;
	}
	
	public final void pairLiteral() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST pairLiteral_AST = null;
		
		AST tmp246_AST = null;
		tmp246_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp246_AST);
		match(PAIR);
		if ( inputState.guessing==0 ) {
			pairLiteral_AST = (AST)currentAST.root;
			addEndLineColumn(pairLiteral_AST, LT(1));
		}
		pairLiteral_AST = (AST)currentAST.root;
		returnAST = pairLiteral_AST;
	}
	
	public final void filePathLiteral() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST filePathLiteral_AST = null;
		
		AST tmp247_AST = null;
		tmp247_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp247_AST);
		match(FILE_PATH);
		if ( inputState.guessing==0 ) {
			filePathLiteral_AST = (AST)currentAST.root;
			addEndLineColumn(filePathLiteral_AST, LT(1));
		}
		filePathLiteral_AST = (AST)currentAST.root;
		returnAST = filePathLiteral_AST;
	}
	
	public final void markupLiteral() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST markupLiteral_AST = null;
		
		AST tmp248_AST = null;
		tmp248_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp248_AST);
		match(MARKUP);
		markupLiteral_AST = (AST)currentAST.root;
		returnAST = markupLiteral_AST;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"INDENT",
		"DEDENT",
		"INTEGER",
		"an identifier",
		"ID_LPAREN",
		"URL",
		"STRING_PART",
		"STR_TQ_START",
		"STR_SQ_START",
		"STR_PART_MIDDLE",
		"STR_PART_END",
		"REGEX_NUM_PART",
		"REGEX_START",
		"REGEX_END",
		"REGEX_MIDDLE",
		"REGEX_CONSTRUCTIOR",
		"FILE_PATH",
		"FILE_PATH_START",
		"FILE_PATH_MIDDLE",
		"FILE_PATH_END",
		"FILE_CONSTRUCTOR",
		"'('",
		"')'",
		"'['",
		"']'",
		"STRING_CONSTRUCTOR",
		"'{'",
		"right curly",
		"EMAIL",
		"TIME",
		"LIST_PREFIX",
		"PERCENTAGE",
		"NLS",
		"FLOAT",
		"PAIR",
		"a date",
		"PAIR_LCURLY",
		"'#{'",
		"RCURLY_X_PAIR",
		"RCURLY_X_PAIR_LCURLY",
		"RCURLY_X_LCURLY",
		"single line comment",
		"'='",
		"'+'",
		"'-'",
		"'=='",
		"\"not\"",
		"'!='",
		"MOD",
		"':'",
		"','",
		"';'",
		"'.'",
		"'..'",
		"'?'",
		"'~'",
		"'>'",
		"less then operator",
		"'>='",
		"'<='",
		"'>>'",
		"'<<'",
		"'++'",
		"'--'",
		"'**'",
		"'::'",
		"'*.'",
		"'?.'",
		"XML_START",
		"XML_END",
		"'+='",
		"'-='",
		"'*='",
		"DIV_ASSIGN",
		"MOD_ASSIGN",
		"'**='",
		"RFC822",
		"U_DB_DOT",
		"U_DB_DOT_LT",
		"U_GT_DB_DOT",
		"U_GT_DB_DOT_LT",
		"DIV",
		"'=~'",
		"'!~'",
		"\"null\"",
		"\"new\"",
		"\"true\"",
		"\"false\"",
		"\"in\"",
		"\"xor\"",
		"\"instanceof\"",
		"\"step\"",
		"\"if\"",
		"\"else\"",
		"\"for\"",
		"\"while\"",
		"\"do\"",
		"\"def\"",
		"\"class\"",
		"\"extends\"",
		"\"try\"",
		"\"catch\"",
		"\"import\"",
		"\"environment\"",
		"\"pattern\"",
		"\"package\"",
		"\"as\"",
		"\"return\"",
		"\"break\"",
		"\"match\"",
		"\"assert\"",
		"\"throw\"",
		"\"this\"",
		"\"super\"",
		"\"is\"",
		"\"and\"",
		"\"or\"",
		"\"init\"",
		"DATE_SLASH_LCURLY",
		"DATE_BSLASH_LCURLY",
		"DATE_SUB_LCURLY",
		"DATE_DOT_LCURLY",
		"DATE_SLASH_DATE_SLASH_LCURLY",
		"REFRENCE_NAME",
		"DATE_BSLASH_DATE_BSLASH_LCURLY",
		"DATE_SUB_DATE_SUB_LCURLY",
		"DATE_DOT_DATE_DOT_LCURLY",
		"RCURLY_SLASH_LCURLY",
		"RCURLY_SLASH_DATE",
		"RCURLY_SLASH_DATE_SLASH",
		"RCURLY_SLASH_DATE_SLASH_LCURLY",
		"RCURLY_BSLASH_LCURLY",
		"RCURLY_BSLASH_DATE",
		"RCURLY_BSLASH_DATE_BSLASH",
		"RCURLY_BSLASH_DATE_BSLASH_LCURLY",
		"RCURLY_SUB_LCURLY",
		"RCURLY_SUB_DATE",
		"RCURLY_SUB_DATE_SUB",
		"RCURLY_SUB_DATE_SUB_LCURLY",
		"RCURLY_DOT_LCURLY",
		"RCURLY_DOT_DATE",
		"RCURLY_DOT_DATE_DOT",
		"RCURLY_DOT_DATE_DOT_LCURLY",
		"STATEMENTS",
		"HASH",
		"HASH_ENTRY",
		"DATE_TIME",
		"U_MINUS",
		"U_PLUS",
		"PAIR_CONSTRUCTOR",
		"LIST",
		"ARG_LIST",
		"METHOD_CALL",
		"INDEX_OP",
		"MATCH_ATTR",
		"MATCH_VAR_DEF",
		"BLOCK",
		"CALL_BLOCK",
		"POSTFIX",
		"POST_INC",
		"POST_DEC",
		"ASSERT",
		"IF",
		"FOR",
		"PARAM",
		"PARAM_LIST",
		"METHOD_DEF",
		"CONSTRUCTOR_DEF",
		"CLOSURE",
		"ALIAS",
		"THROW",
		"NEW",
		"MATCH",
		"MATCH_ITEM",
		"MATCH_ELSE_ITEM",
		"MATCH_BLOCK",
		"EXTENDS_TYPE",
		"TRY",
		"CATCH",
		"FIELD",
		"CATCH_LIST",
		"NAMED_ARG",
		"ANNOTATION_FIELD",
		"TYPE",
		"CLASS_STATEMENTS",
		"CLASS_BLOCK",
		"CLASS",
		"MODIFIERS",
		"IMPORT",
		"ANNOTATION",
		"ANNOTATION_LIST",
		"COMPILATION_UNIT",
		"START_TAG",
		"TAG_ATTR",
		"TAG_ATTR_LIST",
		"END_TAG",
		"TAG_CONTENT",
		"TAG",
		"TAG_TEXT",
		"MARKUP",
		"PACKAGE",
		"SUPER_COTR_CALL",
		"UNIX_HEADER",
		"'=>'",
		"'|'",
		"'->'",
		"':='",
		"'@'",
		"\"public\"",
		"\"private\"",
		"\"protected\"",
		"\"static\"",
		"\"abstract\"",
		"\"final\"",
		"COLCON",
		"'*'",
		"'?:'",
		"'&'",
		"'||'",
		"'&&'",
		"'.|.'",
		"'.&.'",
		"'..<'",
		"'>..'",
		"'>..<'",
		"'.<<.'",
		"'.>>.'",
		"'^'",
		"LNOT",
		"STRING",
		"K_FLASE",
		"ELSE",
		"'#('",
		"'#{'",
		"'...'",
		"REGEX",
		"'::['",
		"'&&='",
		"'||='",
		"LETTER",
		"an exponent",
		"HEX_DIGIT",
		"DIGIT",
		"DIGIT_UNDERLINE",
		"DIGITS",
		"DIGITS_UNDERLINE",
		"DECIMAL_DIGITS",
		"DOT_FLOAT_DIGITS",
		"URL_CHAR",
		"a date character",
		"NARKUP_ELEMENT",
		"MARKUP_ATTRS",
		"MARKUP_WS",
		"MARKUP_CONTENT_CHAR",
		"MARKUP_NAME",
		"FILE_START_CHAR",
		"FILE_CHAR",
		"FILE_PATH_PART",
		"NL",
		"a string character",
		"an escape sequence",
		"a multiple lines comment",
		"REGEGX_ATTRUBTE",
		"REGEGX_START",
		"REGEX_PART",
		"REGEX_START_CHAR",
		"REGEX_CHAR",
		"a double qoute string character",
		"a regular expression character",
		"whitespace"
	};
	
	protected void buildTokenTypeASTClassMap() {
		tokenTypeToASTClassMap=null;
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = new long[8];
		data[0]=-35202106164494L;
		data[1]=-8935150456798102305L;
		data[2]=141562122076160L;
		data[3]=281474909552640L;
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = new long[8];
		data[0]=-35202106164494L;
		data[1]=-8935150456798102305L;
		data[2]=141562122076160L;
		data[3]=281474909569024L;
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = new long[8];
		data[0]=758157229734042354L;
		data[1]=-9187460350188912640L;
		data[2]=141562122076160L;
		data[3]=210127180857344L;
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = new long[8];
		data[0]=722128430567594704L;
		data[1]=-9187460350188912640L;
		data[2]=141562122076160L;
		data[3]=210127180857344L;
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = new long[8];
		data[0]=-18080386954224910L;
		data[1]=-9079303586615051041L;
		data[2]=141562122076160L;
		data[3]=281474909437952L;
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = { 36028867885924386L, 0L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	private static final long[] mk_tokenSet_6() {
		long[] data = new long[8];
		data[0]=-35202106164494L;
		data[1]=-9079301379001860897L;
		data[2]=141562122076160L;
		data[3]=281474909536256L;
		return data;
	}
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	private static final long[] mk_tokenSet_7() {
		long[] data = new long[8];
		data[0]=68719476736L;
		data[1]=137438953472L;
		data[3]=66584576L;
		return data;
	}
	public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
	private static final long[] mk_tokenSet_8() {
		long[] data = new long[8];
		data[0]=68719476864L;
		data[1]=137438953472L;
		data[3]=66060288L;
		return data;
	}
	public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
	private static final long[] mk_tokenSet_9() {
		long[] data = new long[8];
		data[0]=68719476736L;
		data[1]=17867063951360L;
		data[3]=524288L;
		return data;
	}
	public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
	private static final long[] mk_tokenSet_10() {
		long[] data = { 68719476864L, 17867063951360L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
	private static final long[] mk_tokenSet_11() {
		long[] data = new long[8];
		data[0]=722128361848117968L;
		data[1]=-9190157452211847168L;
		data[2]=141562122076160L;
		data[3]=210127114272768L;
		return data;
	}
	public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
	private static final long[] mk_tokenSet_12() {
		long[] data = new long[8];
		data[0]=1126146867462848L;
		data[1]=33214563000320000L;
		data[2]=141562122076160L;
		data[3]=15393162788864L;
		return data;
	}
	public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
	private static final long[] mk_tokenSet_13() {
		long[] data = new long[8];
		data[0]=1126076000502464L;
		data[1]=33214563000320000L;
		data[2]=141562122076160L;
		data[3]=15393162788864L;
		return data;
	}
	public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
	private static final long[] mk_tokenSet_14() {
		long[] data = new long[8];
		data[0]=722128430567594688L;
		data[1]=-9190157469391716352L;
		data[2]=141562122076160L;
		data[3]=210126980055040L;
		return data;
	}
	public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
	private static final long[] mk_tokenSet_15() {
		long[] data = new long[8];
		data[0]=722128361848117952L;
		data[1]=-9190157469391716352L;
		data[2]=141562122076160L;
		data[3]=210126979989504L;
		return data;
	}
	public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
	private static final long[] mk_tokenSet_16() {
		long[] data = new long[8];
		data[0]=722128361848117968L;
		data[1]=-9190157469391716352L;
		data[2]=141562122076160L;
		data[3]=210126979989504L;
		return data;
	}
	public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
	private static final long[] mk_tokenSet_17() {
		long[] data = new long[8];
		data[0]=-65988444742926L;
		data[1]=-9079303586615051041L;
		data[2]=141562122076160L;
		data[3]=281474909437952L;
		return data;
	}
	public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
	private static final long[] mk_tokenSet_18() {
		long[] data = new long[8];
		data[0]=212091466959290402L;
		data[1]=2097171L;
		data[3]=1082466041856L;
		return data;
	}
	public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
	private static final long[] mk_tokenSet_19() {
		long[] data = new long[8];
		data[0]=-35202106164494L;
		data[1]=-8935186190926005025L;
		data[2]=141562122076160L;
		data[3]=281474909536256L;
		return data;
	}
	public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
	private static final long[] mk_tokenSet_20() {
		long[] data = new long[8];
		data[0]=722128361848117952L;
		data[1]=-9190157469391716352L;
		data[2]=141562122076160L;
		data[3]=210126980055040L;
		return data;
	}
	public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
	private static final long[] mk_tokenSet_21() {
		long[] data = new long[8];
		data[0]=212091466959290402L;
		data[1]=2097171L;
		data[3]=1082465976320L;
		return data;
	}
	public static final BitSet _tokenSet_21 = new BitSet(mk_tokenSet_21());
	private static final long[] mk_tokenSet_22() {
		long[] data = new long[8];
		data[0]=1126144719979200L;
		data[1]=177329751076175872L;
		data[2]=141562122076160L;
		data[3]=15393229373440L;
		return data;
	}
	public static final BitSet _tokenSet_22 = new BitSet(mk_tokenSet_22());
	private static final long[] mk_tokenSet_23() {
		long[] data = new long[10];
		data[0]=-16L;
		data[1]=-274877906945L;
		for (int i = 2; i<=3; i++) { data[i]=-1L; }
		data[4]=262143L;
		return data;
	}
	public static final BitSet _tokenSet_23 = new BitSet(mk_tokenSet_23());
	private static final long[] mk_tokenSet_24() {
		long[] data = new long[8];
		data[0]=758157229801151184L;
		data[1]=-9187460350188912640L;
		data[2]=141562122076160L;
		data[3]=210127180857344L;
		return data;
	}
	public static final BitSet _tokenSet_24 = new BitSet(mk_tokenSet_24());
	private static final long[] mk_tokenSet_25() {
		long[] data = new long[8];
		data[0]=-18080386954224944L;
		data[1]=-9079303586615051041L;
		data[2]=141562122076160L;
		data[3]=281474909437952L;
		return data;
	}
	public static final BitSet _tokenSet_25 = new BitSet(mk_tokenSet_25());
	private static final long[] mk_tokenSet_26() {
		long[] data = new long[8];
		data[0]=758157227653667536L;
		data[1]=-9187460350188912640L;
		data[2]=141562122076160L;
		data[3]=210127180857344L;
		return data;
	}
	public static final BitSet _tokenSet_26 = new BitSet(mk_tokenSet_26());
	private static final long[] mk_tokenSet_27() {
		long[] data = new long[8];
		data[0]=758157227586558672L;
		data[1]=-9187460350188912640L;
		data[2]=141562122076160L;
		data[3]=210127180857344L;
		return data;
	}
	public static final BitSet _tokenSet_27 = new BitSet(mk_tokenSet_27());
	private static final long[] mk_tokenSet_28() {
		long[] data = new long[8];
		data[0]=758157229734042320L;
		data[1]=-9187460350188912640L;
		data[2]=141562122076160L;
		data[3]=210127180857344L;
		return data;
	}
	public static final BitSet _tokenSet_28 = new BitSet(mk_tokenSet_28());
	private static final long[] mk_tokenSet_29() {
		long[] data = new long[8];
		data[0]=722128432715078352L;
		data[1]=-9187460350188912640L;
		data[2]=141562122076160L;
		data[3]=210127180857344L;
		return data;
	}
	public static final BitSet _tokenSet_29 = new BitSet(mk_tokenSet_29());
	private static final long[] mk_tokenSet_30() {
		long[] data = new long[8];
		data[0]=1126144719979200L;
		data[1]=33214563000320000L;
		data[2]=141562122076160L;
		data[3]=15393162788864L;
		return data;
	}
	public static final BitSet _tokenSet_30 = new BitSet(mk_tokenSet_30());
	private static final long[] mk_tokenSet_31() {
		long[] data = new long[8];
		data[0]=-54109183973188912L;
		data[1]=-9079303586615051041L;
		data[2]=141562122076160L;
		data[3]=281474909437952L;
		return data;
	}
	public static final BitSet _tokenSet_31 = new BitSet(mk_tokenSet_31());
	private static final long[] mk_tokenSet_32() {
		long[] data = new long[8];
		data[0]=68719476864L;
		data[3]=66584576L;
		return data;
	}
	public static final BitSet _tokenSet_32 = new BitSet(mk_tokenSet_32());
	private static final long[] mk_tokenSet_33() {
		long[] data = new long[8];
		data[0]=68719476864L;
		data[3]=66322432L;
		return data;
	}
	public static final BitSet _tokenSet_33 = new BitSet(mk_tokenSet_33());
	private static final long[] mk_tokenSet_34() {
		long[] data = { 69793218560L, 12884901888L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_34 = new BitSet(mk_tokenSet_34());
	private static final long[] mk_tokenSet_35() {
		long[] data = new long[8];
		data[0]=-577626162171846494L;
		data[1]=126734112305200351L;
		data[3]=71468122046464L;
		return data;
	}
	public static final BitSet _tokenSet_35 = new BitSet(mk_tokenSet_35());
	private static final long[] mk_tokenSet_36() {
		long[] data = new long[8];
		data[0]=-36094785463706926L;
		data[1]=-9079303586615051041L;
		data[2]=141562122076160L;
		data[3]=281474909437952L;
		return data;
	}
	public static final BitSet _tokenSet_36 = new BitSet(mk_tokenSet_36());
	private static final long[] mk_tokenSet_37() {
		long[] data = new long[8];
		data[0]=70368744177664L;
		data[1]=15360L;
		data[3]=262144L;
		return data;
	}
	public static final BitSet _tokenSet_37 = new BitSet(mk_tokenSet_37());
	private static final long[] mk_tokenSet_38() {
		long[] data = new long[8];
		data[0]=722128430567594704L;
		data[1]=-9190157452211847168L;
		data[2]=141562122076160L;
		data[3]=210127114272768L;
		return data;
	}
	public static final BitSet _tokenSet_38 = new BitSet(mk_tokenSet_38());
	private static final long[] mk_tokenSet_39() {
		long[] data = new long[8];
		data[0]=-577626162171846494L;
		data[1]=126734110157716703L;
		data[3]=71468122046464L;
		return data;
	}
	public static final BitSet _tokenSet_39 = new BitSet(mk_tokenSet_39());
	private static final long[] mk_tokenSet_40() {
		long[] data = new long[8];
		data[0]=722128430567594688L;
		data[1]=-9190157469391716352L;
		data[2]=141562122076160L;
		data[3]=210126979989504L;
		return data;
	}
	public static final BitSet _tokenSet_40 = new BitSet(mk_tokenSet_40());
	private static final long[] mk_tokenSet_41() {
		long[] data = { 2814749767106560L, 18014398522064896L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_41 = new BitSet(mk_tokenSet_41());
	private static final long[] mk_tokenSet_42() {
		long[] data = { -1152921504606846976L, 1342177280L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_42 = new BitSet(mk_tokenSet_42());
	private static final long[] mk_tokenSet_43() {
		long[] data = new long[8];
		data[0]=-35202106164494L;
		data[1]=-9079303586615051041L;
		data[2]=141562122076160L;
		data[3]=281474909569024L;
		return data;
	}
	public static final BitSet _tokenSet_43 = new BitSet(mk_tokenSet_43());
	private static final long[] mk_tokenSet_44() {
		long[] data = new long[8];
		data[0]=144115188075855872L;
		data[3]=120259084288L;
		return data;
	}
	public static final BitSet _tokenSet_44 = new BitSet(mk_tokenSet_44());
	private static final long[] mk_tokenSet_45() {
		long[] data = new long[8];
		data[0]=-35202106164510L;
		data[1]=-9082000705817854753L;
		data[2]=141562122076160L;
		data[3]=281474842951680L;
		return data;
	}
	public static final BitSet _tokenSet_45 = new BitSet(mk_tokenSet_45());
	private static final long[] mk_tokenSet_46() {
		long[] data = new long[8];
		data[0]=578013173772262080L;
		data[1]=-9190157469391716352L;
		data[2]=141562122076160L;
		data[3]=210006720905216L;
		return data;
	}
	public static final BitSet _tokenSet_46 = new BitSet(mk_tokenSet_46());
	private static final long[] mk_tokenSet_47() {
		long[] data = new long[8];
		data[1]=3L;
		data[3]=412316860416L;
		return data;
	}
	public static final BitSet _tokenSet_47 = new BitSet(mk_tokenSet_47());
	private static final long[] mk_tokenSet_48() {
		long[] data = new long[8];
		data[0]=578013242491738816L;
		data[1]=-9190157469391716352L;
		data[2]=141562122076160L;
		data[3]=210006720905216L;
		return data;
	}
	public static final BitSet _tokenSet_48 = new BitSet(mk_tokenSet_48());
	private static final long[] mk_tokenSet_49() {
		long[] data = { 72057663998918656L, 192L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_49 = new BitSet(mk_tokenSet_49());
	private static final long[] mk_tokenSet_50() {
		long[] data = new long[8];
		data[0]=-7454706851833136L;
		data[1]=-9187460350186815485L;
		data[2]=141562122076160L;
		data[3]=210127180857344L;
		return data;
	}
	public static final BitSet _tokenSet_50 = new BitSet(mk_tokenSet_50());
	private static final long[] mk_tokenSet_51() {
		long[] data = new long[8];
		data[0]=1009318363712L;
		data[1]=-9223372036636672000L;
		data[3]=142936511610880L;
		return data;
	}
	public static final BitSet _tokenSet_51 = new BitSet(mk_tokenSet_51());
	private static final long[] mk_tokenSet_52() {
		long[] data = new long[8];
		data[0]=-577626153581905758L;
		data[1]=126734112305265887L;
		data[3]=73667145302016L;
		return data;
	}
	public static final BitSet _tokenSet_52 = new BitSet(mk_tokenSet_52());
	private static final long[] mk_tokenSet_53() {
		long[] data = new long[8];
		data[0]=722128430634703552L;
		data[1]=-9190157469391716352L;
		data[2]=141562122076160L;
		data[3]=210126979989504L;
		return data;
	}
	public static final BitSet _tokenSet_53 = new BitSet(mk_tokenSet_53());
	private static final long[] mk_tokenSet_54() {
		long[] data = new long[8];
		data[0]=-54109183973188880L;
		data[1]=-9079303586615051041L;
		data[2]=141562122076160L;
		data[3]=281474909437952L;
		return data;
	}
	public static final BitSet _tokenSet_54 = new BitSet(mk_tokenSet_54());
	
	}
