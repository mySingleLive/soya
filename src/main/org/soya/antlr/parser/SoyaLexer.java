// $ANTLR 2.7.7 (20060906): "soya.g" -> "SoyaLexer.java"$

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

import java.io.InputStream;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.TokenStreamRecognitionException;
import antlr.CharStreamException;
import antlr.CharStreamIOException;
import antlr.ANTLRException;
import java.io.Reader;
import java.util.Hashtable;
import antlr.CharScanner;
import antlr.InputBuffer;
import antlr.ByteBuffer;
import antlr.CharBuffer;
import antlr.Token;
import antlr.CommonToken;
import antlr.RecognitionException;
import antlr.NoViableAltForCharException;
import antlr.MismatchedCharException;
import antlr.TokenStream;
import antlr.ANTLRHashString;
import antlr.LexerSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.SemanticException;

/**
 * Lexer of Soya
 * @author: James Gong
 */
public class SoyaLexer extends antlr.CharScanner implements SoyaParserTokenTypes, TokenStream
 {

    protected SoyaTokenSource tokenSource;

    // regex pattern attrubtes

    private final int REGEXP_ATTR_I = 0x1;
    private final int REGEXP_ATTR_G = 0x1 << 1;
    private final int REGEXP_ATTR_M = 0x1 << 2;

    private int regexPatternAttrubtes = 0;

	private boolean wellCharCURLYStart = false;

	private int whiteSpaceStartPos = -1;

	private int newlines = 0;

	protected int currentCurlyLevel = 0;

	private List curlyStack = new ArrayList();

	protected int stringPartType = 0;

	private boolean allowFilePath = true;

    public void setTokenSource (SoyaTokenSource tokenSource) { this.tokenSource = tokenSource; }

    public SoyaTokenSource getTokenSource() { return tokenSource; }

	public void setWellCharCURLYStart (boolean value) { wellCharCURLYStart = value; }

	public boolean isWellCharCURLYStart() { return wellCharCURLYStart; }

	public void setWhiteSpaceStartPos (int value) { whiteSpaceStartPos = value; }

	public int getWhiteSpaceStartPos() { return whiteSpaceStartPos; }

	public void setNewlines (int value) { newlines = value; }

	public int getNewlines() { return newlines; }

	public void setAllowFilePath (boolean value) { allowFilePath = value; }

	public boolean isAllowFilePath() { return allowFilePath; }

	public void setAllowHandleIndentation (boolean value) { tokenSource.setAllowHandleIndentation(value); }

	public boolean isAllowHandleIndentation() { return tokenSource.isAllowHandleIndentation(); }

    public void beginHandleIndentation (Token tok) {
        tokenSource.beginHandleIndentation(tok);
    }

    public void pushCurlyLevel() {
         curlyStack.add(new Integer(stringPartType));
         stringPartType = 0;
    }

    public void popCurlyLevel() {
         if (curlyStack.size() > 0) {
             Integer i = (Integer) curlyStack.remove(curlyStack.size() - 1);
             stringPartType = i.intValue();
         }
    }

	protected Token makeToken(int t) {
        SoyaToken tok = new SoyaToken(t);
        tok.setColumn(inputState.getTokenStartColumn());
        tok.setLine(inputState.getTokenStartLine());
        tok.setEndColumn(inputState.getColumn());
        tok.setEndLine(inputState.getLine());
        return tok;
    }

    public TokenStream plumb() {
        return new TokenStream() {
            public Token nextToken() throws TokenStreamException {
				Token token = SoyaLexer.this.nextToken();
                int tokType = token.getType();
                switch (tokType) {
                    case NLS:
                        break;
                }
				return token;
			}
		};
	}

public SoyaLexer(InputStream in) {
	this(new ByteBuffer(in));
}
public SoyaLexer(Reader in) {
	this(new CharBuffer(in));
}
public SoyaLexer(InputBuffer ib) {
	this(new LexerSharedInputState(ib));
}
public SoyaLexer(LexerSharedInputState state) {
	super(state);
	caseSensitiveLiterals = true;
	setCaseSensitive(true);
	literals = new Hashtable();
	literals.put(new ANTLRHashString("xor", this), new Integer(93));
	literals.put(new ANTLRHashString("abstract", this), new Integer(216));
	literals.put(new ANTLRHashString("static", this), new Integer(215));
	literals.put(new ANTLRHashString("import", this), new Integer(106));
	literals.put(new ANTLRHashString("break", this), new Integer(112));
	literals.put(new ANTLRHashString("init", this), new Integer(121));
	literals.put(new ANTLRHashString("catch", this), new Integer(105));
	literals.put(new ANTLRHashString("for", this), new Integer(98));
	literals.put(new ANTLRHashString("else", this), new Integer(97));
	literals.put(new ANTLRHashString("is", this), new Integer(118));
	literals.put(new ANTLRHashString("and", this), new Integer(119));
	literals.put(new ANTLRHashString("as", this), new Integer(110));
	literals.put(new ANTLRHashString("private", this), new Integer(213));
	literals.put(new ANTLRHashString("environment", this), new Integer(107));
	literals.put(new ANTLRHashString("throw", this), new Integer(115));
	literals.put(new ANTLRHashString("try", this), new Integer(104));
	literals.put(new ANTLRHashString("not", this), new Integer(50));
	literals.put(new ANTLRHashString("in", this), new Integer(92));
	literals.put(new ANTLRHashString("pattern", this), new Integer(108));
	literals.put(new ANTLRHashString("this", this), new Integer(116));
	literals.put(new ANTLRHashString("null", this), new Integer(88));
	literals.put(new ANTLRHashString("public", this), new Integer(212));
	literals.put(new ANTLRHashString("extends", this), new Integer(103));
	literals.put(new ANTLRHashString("false", this), new Integer(91));
	literals.put(new ANTLRHashString("final", this), new Integer(217));
	literals.put(new ANTLRHashString("step", this), new Integer(95));
	literals.put(new ANTLRHashString("true", this), new Integer(90));
	literals.put(new ANTLRHashString("do", this), new Integer(100));
	literals.put(new ANTLRHashString("protected", this), new Integer(214));
	literals.put(new ANTLRHashString("or", this), new Integer(120));
	literals.put(new ANTLRHashString("if", this), new Integer(96));
	literals.put(new ANTLRHashString("return", this), new Integer(111));
	literals.put(new ANTLRHashString("match", this), new Integer(113));
	literals.put(new ANTLRHashString("instanceof", this), new Integer(94));
	literals.put(new ANTLRHashString("new", this), new Integer(89));
	literals.put(new ANTLRHashString("assert", this), new Integer(114));
	literals.put(new ANTLRHashString("def", this), new Integer(101));
	literals.put(new ANTLRHashString("class", this), new Integer(102));
	literals.put(new ANTLRHashString("while", this), new Integer(99));
	literals.put(new ANTLRHashString("package", this), new Integer(109));
	literals.put(new ANTLRHashString("super", this), new Integer(117));
}

public Token nextToken() throws TokenStreamException {
	Token theRetToken=null;
tryAgain:
	for (;;) {
		Token _token = null;
		int _ttype = Token.INVALID_TYPE;
		resetText();
		try {   // for char stream error handling
			try {   // for lexical error handling
				switch ( LA(1)) {
				case '(':
				{
					mLPAREN(true);
					theRetToken=_returnToken;
					break;
				}
				case ')':
				{
					mRPAREN(true);
					theRetToken=_returnToken;
					break;
				}
				case '[':
				{
					mLBRACK(true);
					theRetToken=_returnToken;
					break;
				}
				case ']':
				{
					mRBRACK(true);
					theRetToken=_returnToken;
					break;
				}
				case '{':
				{
					mLCURLY(true);
					theRetToken=_returnToken;
					break;
				}
				case ',':
				{
					mCOMMAR(true);
					theRetToken=_returnToken;
					break;
				}
				case '@':
				{
					mAT(true);
					theRetToken=_returnToken;
					break;
				}
				case ';':
				{
					mSEMI(true);
					theRetToken=_returnToken;
					break;
				}
				case '~':
				{
					mTILDE(true);
					theRetToken=_returnToken;
					break;
				}
				case '^':
				{
					mPOW(true);
					theRetToken=_returnToken;
					break;
				}
				case '0':  case '1':  case '2':  case '3':
				case '4':  case '5':  case '6':  case '7':
				case '8':  case '9':
				{
					mINTEGER(true);
					theRetToken=_returnToken;
					break;
				}
				case '}':
				{
					mRCURLY(true);
					theRetToken=_returnToken;
					break;
				}
				case '%':
				{
					mMOD(true);
					theRetToken=_returnToken;
					break;
				}
				case '\n':  case '\r':
				{
					mNLS(true);
					theRetToken=_returnToken;
					break;
				}
				case '"':  case '\'':
				{
					mSTRING(true);
					theRetToken=_returnToken;
					break;
				}
				case '\t':  case '\u000c':  case ' ':
				{
					mWS(true);
					theRetToken=_returnToken;
					break;
				}
				default:
					if ((LA(1)=='>') && (LA(2)=='.') && (LA(3)=='.') && (LA(4)=='<')) {
						mGT_DB_DOT_LT(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='/') && (LA(2)=='*') && ((LA(3) >= '\u0000' && LA(3) <= '\ufffe')) && ((LA(4) >= '\u0000' && LA(4) <= '\ufffe'))) {
						mMULTI_COMMENT(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)==':') && (LA(2)==':') && (LA(3)=='[')) {
						mDB_COLON_LBRACK(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='.') && (LA(2)=='.') && (LA(3)=='.')) {
						mTHREE_DOT(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='.') && (LA(2)=='.') && (LA(3)=='<')) {
						mDB_DOT_LT(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='>') && (LA(2)=='.') && (LA(3)=='.') && (true)) {
						mGT_DB_DOT(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='*') && (LA(2)=='*') && (LA(3)=='=')) {
						mDB_STAR_ASSIGN(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='&') && (LA(2)=='&') && (LA(3)=='=')) {
						mAND_ASSIGN(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='|') && (LA(2)=='|') && (LA(3)=='=')) {
						mOR_ASSIGN(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='A'||LA(1)=='B'||LA(1)=='C'||LA(1)=='E'||LA(1)=='G'||LA(1)=='H'||LA(1)=='I'||LA(1)=='J'||LA(1)=='M'||LA(1)=='N'||LA(1)=='P'||LA(1)=='S'||LA(1)=='U'||LA(1)=='V') && (LA(2)=='A'||LA(2)=='C'||LA(2)=='E'||LA(2)=='G'||LA(2)=='I'||LA(2)=='L'||LA(2)=='M'||LA(2)=='N'||LA(2)=='R'||LA(2)=='S'||LA(2)=='T') && (LA(3)=='C'||LA(3)=='T') && (true)) {
						mRFC822(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='-') && (LA(2)=='>')) {
						mRARROW(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='=') && (LA(2)=='>')) {
						mEQ_GT(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)==':') && (LA(2)=='=')) {
						mINIT_ASSIGN(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='#') && (LA(2)=='(')) {
						mSIGN_LPAREN(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='#') && (LA(2)=='{') && (true) && (true)) {
						mSIGN_LCURLY(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='?') && (LA(2)==':')) {
						mQUESTION_COLON(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='>') && (LA(2)=='=')) {
						mGE(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='<') && (LA(2)=='=')) {
						mLE(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='>') && (LA(2)=='>')) {
						mSR(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='<') && (LA(2)=='<')) {
						mSL(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='|') && (LA(2)=='|') && (true)) {
						mLOR(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='&') && (LA(2)=='&') && (true)) {
						mLAND(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='.') && (LA(2)=='&')) {
						mBAND(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='.') && (LA(2)=='|')) {
						mBOR(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='.') && (LA(2)=='>')) {
						mBSR(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='.') && (LA(2)=='<')) {
						mBSL(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='#') && (LA(2)=='{') && (true) && (true)) {
						mWELL_LCURLY(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='+') && (LA(2)=='+')) {
						mINC(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='-') && (LA(2)=='-')) {
						mDEC(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='*') && (LA(2)=='*') && (true)) {
						mDB_STAR(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)==':') && (LA(2)==':') && (true)) {
						mDB_COLON(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='.') && (LA(2)=='.') && (true)) {
						mDB_DOT(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='*') && (LA(2)=='.')) {
						mSTAR_DOT(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='?') && (LA(2)=='.')) {
						mOPTIONAL_DOT(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='=') && (LA(2)=='=')) {
						mEQUAL(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='!') && (LA(2)=='=')) {
						mNOT_EQUAL(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='=') && (LA(2)=='~')) {
						mREGEX_MATCH(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='!') && (LA(2)=='~')) {
						mREGEX_NOT_MATCH(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='+') && (LA(2)=='=')) {
						mPLUS_ASSIGN(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='-') && (LA(2)=='=')) {
						mMINUS_ASSIGN(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='*') && (LA(2)=='=')) {
						mSTAR_ASSIGN(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='#') && ((LA(2) >= '0' && LA(2) <= '9'))) {
						mDATE(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='#') && (LA(2)=='!')) {
						mUNIX_HEADER(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='#') && (_tokenSet_0.member(LA(2)))) {
						mREFRENCE_NAME(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='/') && (LA(2)=='/') && (true) && (true)) {
						mSINGLE_LINE_COMMENT(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='=') && (true)) {
						mASSIGN(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='+') && (true)) {
						mPLUS(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='-') && (true)) {
						mMINUS(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='*') && (true)) {
						mSTAR(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)==':') && (true)) {
						mCOLON(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='.') && (true)) {
						mDOT(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='?') && (true)) {
						mQUESTION(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='>') && (true)) {
						mGT(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='|') && (true)) {
						mMOR(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='&') && (true)) {
						mMAND(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='!') && (true)) {
						mLNOT(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='<') && (true)) {
						mLT(true);
						theRetToken=_returnToken;
					}
					else if ((_tokenSet_1.member(LA(1))) && (true) && (true) && (true)) {
						mID(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='/') && (true) && (true) && (true)) {
						mREGEX(true);
						theRetToken=_returnToken;
					}
				else {
					if (LA(1)==EOF_CHAR) {uponEOF(); _returnToken = makeToken(Token.EOF_TYPE);}
				else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
				}
				}
				if ( _returnToken==null ) continue tryAgain; // found SKIP token
				_ttype = _returnToken.getType();
				_returnToken.setType(_ttype);
				return _returnToken;
			}
			catch (RecognitionException e) {
				throw new TokenStreamRecognitionException(e);
			}
		}
		catch (CharStreamException cse) {
			if ( cse instanceof CharStreamIOException ) {
				throw new TokenStreamIOException(((CharStreamIOException)cse).io);
			}
			else {
				throw new TokenStreamException(cse.getMessage());
			}
		}
	}
}

	public final void mLPAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LPAREN;
		int _saveIndex;
		
		match('(');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mRPAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = RPAREN;
		int _saveIndex;
		
		match(')');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mLBRACK(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LBRACK;
		int _saveIndex;
		
		match('[');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mRBRACK(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = RBRACK;
		int _saveIndex;
		
		match(']');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mLCURLY(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LCURLY;
		int _saveIndex;
		
		match('{');
		if ( inputState.guessing==0 ) {
			pushCurlyLevel(); currentCurlyLevel++;
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mRARROW(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = RARROW;
		int _saveIndex;
		
		match("->");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mEQ_GT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = EQ_GT;
		int _saveIndex;
		
		match("=>");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mASSIGN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ASSIGN;
		int _saveIndex;
		
		match('=');
		if ( inputState.guessing==0 ) {
			allowFilePath=true;
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mINIT_ASSIGN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = INIT_ASSIGN;
		int _saveIndex;
		
		match(":=");
		if ( inputState.guessing==0 ) {
			allowFilePath=true;
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mPLUS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = PLUS;
		int _saveIndex;
		
		match('+');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mMINUS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = MINUS;
		int _saveIndex;
		
		match('-');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mSTAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = STAR;
		int _saveIndex;
		
		match('*');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mCOMMAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = COMMAR;
		int _saveIndex;
		
		match(',');
		if ( inputState.guessing==0 ) {
			allowFilePath=true;
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mCOLON(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = COLON;
		int _saveIndex;
		
		match(':');
		if ( inputState.guessing==0 ) {
			allowFilePath=true;
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mDOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DOT;
		int _saveIndex;
		
		match('.');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mAT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = AT;
		int _saveIndex;
		
		match('@');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mSIGN_LPAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SIGN_LPAREN;
		int _saveIndex;
		
		match("#(");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mSIGN_LCURLY(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SIGN_LCURLY;
		int _saveIndex;
		
		match("#{");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mSEMI(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SEMI;
		int _saveIndex;
		
		match(';');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mQUESTION(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = QUESTION;
		int _saveIndex;
		
		match('?');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mQUESTION_COLON(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = QUESTION_COLON;
		int _saveIndex;
		
		match("?:");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mTILDE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = TILDE;
		int _saveIndex;
		
		match('~');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mGT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = GT;
		int _saveIndex;
		
		match('>');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mGE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = GE;
		int _saveIndex;
		
		match(">=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mLE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LE;
		int _saveIndex;
		
		match("<=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mSR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SR;
		int _saveIndex;
		
		match(">>");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mSL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SL;
		int _saveIndex;
		
		match("<<");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mLOR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LOR;
		int _saveIndex;
		
		match("||");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mLAND(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LAND;
		int _saveIndex;
		
		match("&&");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mMOR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = MOR;
		int _saveIndex;
		
		match('|');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mMAND(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = MAND;
		int _saveIndex;
		
		match('&');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mBAND(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = BAND;
		int _saveIndex;
		
		match(".&.");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mBOR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = BOR;
		int _saveIndex;
		
		match(".|.");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mBSR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = BSR;
		int _saveIndex;
		
		match(".>>.");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mBSL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = BSL;
		int _saveIndex;
		
		match(".<<.");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mWELL_LCURLY(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = WELL_LCURLY;
		int _saveIndex;
		
		match("#{");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mINC(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = INC;
		int _saveIndex;
		
		match("++");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mDEC(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DEC;
		int _saveIndex;
		
		match("--");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mDB_STAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DB_STAR;
		int _saveIndex;
		
		match("**");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mPOW(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = POW;
		int _saveIndex;
		
		match('^');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mDB_COLON(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DB_COLON;
		int _saveIndex;
		
		match("::");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mDB_COLON_LBRACK(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DB_COLON_LBRACK;
		int _saveIndex;
		
		match("::[");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mDB_DOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DB_DOT;
		int _saveIndex;
		
		match("..");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mTHREE_DOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = THREE_DOT;
		int _saveIndex;
		
		match("...");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mDB_DOT_LT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DB_DOT_LT;
		int _saveIndex;
		
		match("..<");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mGT_DB_DOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = GT_DB_DOT;
		int _saveIndex;
		
		match(">..");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mGT_DB_DOT_LT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = GT_DB_DOT_LT;
		int _saveIndex;
		
		match(">..<");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mSTAR_DOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = STAR_DOT;
		int _saveIndex;
		
		match("*.");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mOPTIONAL_DOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = OPTIONAL_DOT;
		int _saveIndex;
		
		match("?.");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mEQUAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = EQUAL;
		int _saveIndex;
		
		match("==");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mNOT_EQUAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = NOT_EQUAL;
		int _saveIndex;
		
		match("!=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mREGEX_MATCH(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = REGEX_MATCH;
		int _saveIndex;
		
		match("=~");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mREGEX_NOT_MATCH(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = REGEX_NOT_MATCH;
		int _saveIndex;
		
		match("!~");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mPLUS_ASSIGN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = PLUS_ASSIGN;
		int _saveIndex;
		
		match("+=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mMINUS_ASSIGN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = MINUS_ASSIGN;
		int _saveIndex;
		
		match("-=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mSTAR_ASSIGN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = STAR_ASSIGN;
		int _saveIndex;
		
		match("*=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mDB_STAR_ASSIGN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DB_STAR_ASSIGN;
		int _saveIndex;
		
		match("**=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mAND_ASSIGN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = AND_ASSIGN;
		int _saveIndex;
		
		match("&&=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mOR_ASSIGN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = OR_ASSIGN;
		int _saveIndex;
		
		match("||=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mLETTER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LETTER;
		int _saveIndex;
		
		{
		switch ( LA(1)) {
		case 'a':  case 'b':  case 'c':  case 'd':
		case 'e':  case 'f':  case 'g':  case 'h':
		case 'i':  case 'j':  case 'k':  case 'l':
		case 'm':  case 'n':  case 'o':  case 'p':
		case 'q':  case 'r':  case 's':  case 't':
		case 'u':  case 'v':  case 'w':  case 'x':
		case 'y':  case 'z':
		{
			matchRange('a','z');
			break;
		}
		case 'A':  case 'B':  case 'C':  case 'D':
		case 'E':  case 'F':  case 'G':  case 'H':
		case 'I':  case 'J':  case 'K':  case 'L':
		case 'M':  case 'N':  case 'O':  case 'P':
		case 'Q':  case 'R':  case 'S':  case 'T':
		case 'U':  case 'V':  case 'W':  case 'X':
		case 'Y':  case 'Z':
		{
			matchRange('A','Z');
			break;
		}
		case '\u00c0':  case '\u00c1':  case '\u00c2':  case '\u00c3':
		case '\u00c4':  case '\u00c5':  case '\u00c6':  case '\u00c7':
		case '\u00c8':  case '\u00c9':  case '\u00ca':  case '\u00cb':
		case '\u00cc':  case '\u00cd':  case '\u00ce':  case '\u00cf':
		case '\u00d0':  case '\u00d1':  case '\u00d2':  case '\u00d3':
		case '\u00d4':  case '\u00d5':  case '\u00d6':
		{
			matchRange('\u00C0','\u00D6');
			break;
		}
		case '\u00d8':  case '\u00d9':  case '\u00da':  case '\u00db':
		case '\u00dc':  case '\u00dd':  case '\u00de':  case '\u00df':
		case '\u00e0':  case '\u00e1':  case '\u00e2':  case '\u00e3':
		case '\u00e4':  case '\u00e5':  case '\u00e6':  case '\u00e7':
		case '\u00e8':  case '\u00e9':  case '\u00ea':  case '\u00eb':
		case '\u00ec':  case '\u00ed':  case '\u00ee':  case '\u00ef':
		case '\u00f0':  case '\u00f1':  case '\u00f2':  case '\u00f3':
		case '\u00f4':  case '\u00f5':  case '\u00f6':
		{
			matchRange('\u00D8','\u00F6');
			break;
		}
		case '\u00f8':  case '\u00f9':  case '\u00fa':  case '\u00fb':
		case '\u00fc':  case '\u00fd':  case '\u00fe':  case '\u00ff':
		{
			matchRange('\u00F8','\u00FF');
			break;
		}
		case '_':
		{
			match('_');
			break;
		}
		default:
			if (((LA(1) >= '\u0100' && LA(1) <= '\ufffe'))) {
				matchRange('\u0100','\uFFFE');
			}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mEXPONENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = EXPONENT;
		int _saveIndex;
		
		{
		switch ( LA(1)) {
		case 'e':
		{
			match('e');
			break;
		}
		case 'E':
		{
			match('E');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		{
		switch ( LA(1)) {
		case '+':
		{
			match('+');
			break;
		}
		case '-':
		{
			match('-');
			break;
		}
		case '0':  case '1':  case '2':  case '3':
		case '4':  case '5':  case '6':  case '7':
		case '8':  case '9':  case '_':
		{
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		{
		_loop448:
		do {
			if (((LA(1) >= '0' && LA(1) <= '9')) && (LA(2)=='0'||LA(2)=='1'||LA(2)=='2'||LA(2)=='3'||LA(2)=='4'||LA(2)=='5'||LA(2)=='6'||LA(2)=='7'||LA(2)=='8'||LA(2)=='9'||LA(2)=='_')) {
				matchRange('0','9');
			}
			else if ((LA(1)=='_')) {
				match('_');
			}
			else {
				break _loop448;
			}
			
		} while (true);
		}
		{
		matchRange('0','9');
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mHEX_DIGIT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = HEX_DIGIT;
		int _saveIndex;
		
		{
		switch ( LA(1)) {
		case 'a':  case 'b':  case 'c':  case 'd':
		case 'e':  case 'f':
		{
			matchRange('a','f');
			break;
		}
		case '0':  case '1':  case '2':  case '3':
		case '4':  case '5':  case '6':  case '7':
		case '8':  case '9':
		{
			matchRange('0','9');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mDIGIT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DIGIT;
		int _saveIndex;
		
		{
		matchRange('0','9');
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mDIGIT_UNDERLINE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DIGIT_UNDERLINE;
		int _saveIndex;
		
		{
		switch ( LA(1)) {
		case '0':  case '1':  case '2':  case '3':
		case '4':  case '5':  case '6':  case '7':
		case '8':  case '9':
		{
			mDIGIT(false);
			break;
		}
		case '_':
		{
			match('_');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mDIGITS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DIGITS;
		int _saveIndex;
		
		{
		_loop458:
		do {
			if (((LA(1) >= '0' && LA(1) <= '9'))) {
				mDIGIT(false);
			}
			else {
				break _loop458;
			}
			
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mDIGITS_UNDERLINE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DIGITS_UNDERLINE;
		int _saveIndex;
		
		{
		_loop461:
		do {
			if ((LA(1)=='0'||LA(1)=='1'||LA(1)=='2'||LA(1)=='3'||LA(1)=='4'||LA(1)=='5'||LA(1)=='6'||LA(1)=='7'||LA(1)=='8'||LA(1)=='9'||LA(1)=='_') && (LA(2)=='0'||LA(2)=='1'||LA(2)=='2'||LA(2)=='3'||LA(2)=='4'||LA(2)=='5'||LA(2)=='6'||LA(2)=='7'||LA(2)=='8'||LA(2)=='9'||LA(2)=='_')) {
				mDIGIT_UNDERLINE(false);
			}
			else {
				break _loop461;
			}
			
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mDECIMAL_DIGITS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DECIMAL_DIGITS;
		int _saveIndex;
		
		{
		matchRange('1','9');
		}
		mDIGITS(false);
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mDOT_FLOAT_DIGITS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DOT_FLOAT_DIGITS;
		int _saveIndex;
		
		match('.');
		{
		matchRange('0','9');
		}
		{
		if ((LA(1)=='0'||LA(1)=='1'||LA(1)=='2'||LA(1)=='3'||LA(1)=='4'||LA(1)=='5'||LA(1)=='6'||LA(1)=='7'||LA(1)=='8'||LA(1)=='9'||LA(1)=='_')) {
			mDIGITS_UNDERLINE(false);
			mDIGIT(false);
		}
		else {
		}
		
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mURL_CHAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = URL_CHAR;
		int _saveIndex;
		
		{
		switch ( LA(1)) {
		case '0':  case '1':  case '2':  case '3':
		case '4':  case '5':  case '6':  case '7':
		case '8':  case '9':
		{
			mDIGIT(false);
			break;
		}
		case '=':
		{
			match('=');
			break;
		}
		case '~':
		{
			match('~');
			break;
		}
		case '!':
		{
			match('!');
			break;
		}
		case '@':
		{
			match('@');
			break;
		}
		case '#':
		{
			match('#');
			break;
		}
		case '$':
		{
			match('$');
			break;
		}
		case '%':
		{
			match('%');
			break;
		}
		case '^':
		{
			match('^');
			break;
		}
		case '&':
		{
			match('&');
			break;
		}
		case '*':
		{
			match('*');
			break;
		}
		case '-':
		{
			match('-');
			break;
		}
		case '+':
		{
			match('+');
			break;
		}
		case '\\':
		{
			match('\\');
			break;
		}
		case '|':
		{
			match('|');
			break;
		}
		case ',':
		{
			match(',');
			break;
		}
		case '.':
		{
			match('.');
			break;
		}
		case ':':
		{
			match(':');
			break;
		}
		case '<':
		{
			match('<');
			break;
		}
		case '>':
		{
			match('>');
			break;
		}
		case '/':
		{
			match('/');
			break;
		}
		case '?':
		{
			match('?');
			break;
		}
		default:
			if ((_tokenSet_1.member(LA(1)))) {
				mLETTER(false);
			}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mDATE_CHAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DATE_CHAR;
		int _saveIndex;
		
		{
		switch ( LA(1)) {
		case 'a':  case 'b':  case 'c':  case 'd':
		case 'e':  case 'f':  case 'g':  case 'h':
		case 'i':  case 'j':  case 'k':  case 'l':
		case 'm':  case 'n':  case 'o':  case 'p':
		case 'q':  case 'r':  case 's':  case 't':
		case 'u':  case 'v':  case 'w':  case 'x':
		case 'y':  case 'z':
		{
			matchRange('a','z');
			break;
		}
		case 'A':  case 'B':  case 'C':  case 'D':
		case 'E':  case 'F':  case 'G':  case 'H':
		case 'I':  case 'J':  case 'K':  case 'L':
		case 'M':  case 'N':  case 'O':  case 'P':
		case 'Q':  case 'R':  case 'S':  case 'T':
		case 'U':  case 'V':  case 'W':  case 'X':
		case 'Y':  case 'Z':
		{
			matchRange('A','Z');
			break;
		}
		case '0':  case '1':  case '2':  case '3':
		case '4':  case '5':  case '6':  case '7':
		case '8':  case '9':
		{
			mDIGIT(false);
			break;
		}
		case '_':
		{
			match('_');
			break;
		}
		case '+':
		{
			match('+');
			break;
		}
		case '-':
		{
			match('-');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mLNOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LNOT;
		int _saveIndex;
		
		match('!');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mLT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LT;
		int _saveIndex;
		
		boolean xmlBegin = false;
		boolean closed = false;
		
		
		match('<');
		if ( inputState.guessing==0 ) {
			
			/*
			int i = 1;
			char c = LA(i);
			
			if (c == '/') {
			_ttype = XML_END;
			}
			else {
			while (c == ' ' || c == '\t') {
			c = LA(i++);
			}
			
			// Markup Name
			if (Character.isLetterOrDigit(c)) {
			do {
			i++;
			c = LA(i);
			} while (Character.isLetterOrDigit(c));
			while (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
			i++;
			c = LA(i);
			}
			if (c == '>') {   // <MarkupName>
			xmlBegin = true;
			}
			else if (c == '/' || c == '!' || c == '?') {  // <MarkupName/>
			i++;
			c = LA(i);
			if (c == '>') {
			xmlBegin = true;
			}
			else {
			xmlBegin = false;
			}
			}
			else if (Character.isLetterOrDigit(c)) {   // Attributes
			do {
			do {
			i++;
			c = LA(i);
			} while (Character.isLetterOrDigit(c));  // Attribute Name
			while (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
			i++;
			c = LA(i);
			}
			if (c == '=') {
			i++;
			c = LA(i);
			while (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
			i++;
			c = LA(i);
			}
			if (c == '"') {
			i++;
			c = LA(i);
			while (c != '"' && c != EOF) {
			i++;
			c = LA(i);
			}
			i++;
			c = LA(i);
			while (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
			i++;
			c = LA(i);
			}
			}
			else {
			xmlBegin = false;
			break;
			}
			}
			} while (Character.isLetterOrDigit(c));
			if (c == '>') {
			xmlBegin = true;
			}
			else if (c == '/' || c == '!' || c == '?') {  // <MarkupName [attr ...]/>
			i++;
			c = LA(i);
			if (c == '>') {
			xmlBegin = true;
			}
			else {
			xmlBegin = false;
			}
			}
			else {
			xmlBegin = false;
			}
			}
			else {
			xmlBegin = false;
			}
			}
			else if (c == '>' || c == '/' || c == '!' || c == '?') {
			xmlBegin = true;
			}
			else {
			xmlBegin = false;
			}
			}
			*/
			
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mNARKUP_ELEMENT(boolean _createToken,
		boolean hasLT
	) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = NARKUP_ELEMENT;
		int _saveIndex;
		
		boolean closed = false;
		
		
		{
		boolean synPredMatched476 = false;
		if (((LA(1)=='<'))) {
			int _m476 = mark();
			synPredMatched476 = true;
			inputState.guessing++;
			try {
				{
				if (!(!hasLT))
				  throw new SemanticException("!hasLT");
				}
			}
			catch (RecognitionException pe) {
				synPredMatched476 = false;
			}
			rewind(_m476);
inputState.guessing--;
		}
		if ( synPredMatched476 ) {
			match('<');
			mMARKUP_WS(false);
		}
		else if ((_tokenSet_2.member(LA(1)))) {
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		
		}
		mMARKUP_NAME(false);
		mMARKUP_WS(false);
		mMARKUP_ATTRS(false);
		{
		switch ( LA(1)) {
		case '/':
		{
			match('/');
			if ( inputState.guessing==0 ) {
				closed = true;
			}
			break;
		}
		case '>':
		{
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		match('>');
		{
		boolean synPredMatched480 = false;
		if (((_tokenSet_3.member(LA(1))) && ((LA(2) >= '\u0000' && LA(2) <= '\uffff')) && ((LA(3) >= '\u0000' && LA(3) <= '\uffff')) && ((LA(4) >= '\u0000' && LA(4) <= '\uffff')))) {
			int _m480 = mark();
			synPredMatched480 = true;
			inputState.guessing++;
			try {
				{
				if (!(!closed))
				  throw new SemanticException("!closed");
				}
			}
			catch (RecognitionException pe) {
				synPredMatched480 = false;
			}
			rewind(_m480);
inputState.guessing--;
		}
		if ( synPredMatched480 ) {
			{
			mMARKUP_WS(false);
			{
			boolean synPredMatched484 = false;
			if (((_tokenSet_4.member(LA(1))) && (_tokenSet_5.member(LA(2))) && ((LA(3) >= '\u0000' && LA(3) <= '\uffff')) && ((LA(4) >= '\u0000' && LA(4) <= '\uffff')))) {
				int _m484 = mark();
				synPredMatched484 = true;
				inputState.guessing++;
				try {
					{
					match('<');
					matchNot('/');
					}
				}
				catch (RecognitionException pe) {
					synPredMatched484 = false;
				}
				rewind(_m484);
inputState.guessing--;
			}
			if ( synPredMatched484 ) {
				mNARKUP_ELEMENT(false,false);
				{
				_loop488:
				do {
					boolean synPredMatched487 = false;
					if (((_tokenSet_6.member(LA(1))) && (_tokenSet_7.member(LA(2))) && ((LA(3) >= '\u0000' && LA(3) <= '\uffff')) && ((LA(4) >= '\u0000' && LA(4) <= '\uffff')))) {
						int _m487 = mark();
						synPredMatched487 = true;
						inputState.guessing++;
						try {
							{
							mMARKUP_WS(false);
							match('<');
							matchNot('/');
							}
						}
						catch (RecognitionException pe) {
							synPredMatched487 = false;
						}
						rewind(_m487);
inputState.guessing--;
					}
					if ( synPredMatched487 ) {
						mMARKUP_WS(false);
						mNARKUP_ELEMENT(false,false);
					}
					else {
						break _loop488;
					}
					
				} while (true);
				}
			}
			else {
				boolean synPredMatched490 = false;
				if (((_tokenSet_8.member(LA(1))) && (_tokenSet_3.member(LA(2))) && (_tokenSet_3.member(LA(3))) && (_tokenSet_3.member(LA(4))))) {
					int _m490 = mark();
					synPredMatched490 = true;
					inputState.guessing++;
					try {
						{
						matchNot('<');
						}
					}
					catch (RecognitionException pe) {
						synPredMatched490 = false;
					}
					rewind(_m490);
inputState.guessing--;
				}
				if ( synPredMatched490 ) {
					{
					int _cnt492=0;
					_loop492:
					do {
						if ((_tokenSet_8.member(LA(1))) && (_tokenSet_3.member(LA(2))) && (_tokenSet_3.member(LA(3))) && (_tokenSet_3.member(LA(4)))) {
							mMARKUP_CONTENT_CHAR(false);
						}
						else {
							if ( _cnt492>=1 ) { break _loop492; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
						}
						
						_cnt492++;
					} while (true);
					}
				}
				else if ((LA(1)=='\t'||LA(1)=='\n'||LA(1)=='\r'||LA(1)==' '||LA(1)=='<') && (LA(2)=='\t'||LA(2)=='\n'||LA(2)=='\r'||LA(2)==' '||LA(2)=='/'||LA(2)=='<') && (_tokenSet_9.member(LA(3))) && (_tokenSet_7.member(LA(4)))) {
				}
				else {
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
				}
				}
				}
				mMARKUP_WS(false);
				match('<');
				match('/');
				mMARKUP_NAME(false);
				match('>');
				}
			}
			else if ((_tokenSet_6.member(LA(1))) && (_tokenSet_7.member(LA(2))) && ((LA(3) >= '\u0000' && LA(3) <= '\uffff')) && ((LA(4) >= '\u0000' && LA(4) <= '\uffff'))) {
			}
			else {
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			
			}
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		}
		
	protected final void mMARKUP_WS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = MARKUP_WS;
		int _saveIndex;
		
		{
		_loop502:
		do {
			if ((LA(1)==' ') && ((LA(2) >= '\u0000' && LA(2) <= '\uffff')) && ((LA(3) >= '\u0000' && LA(3) <= '\uffff')) && ((LA(4) >= '\u0000' && LA(4) <= '\uffff'))) {
				match(' ');
			}
			else if ((LA(1)=='\t') && ((LA(2) >= '\u0000' && LA(2) <= '\uffff')) && ((LA(3) >= '\u0000' && LA(3) <= '\uffff')) && ((LA(4) >= '\u0000' && LA(4) <= '\uffff'))) {
				match('\t');
			}
			else if ((LA(1)=='\n') && ((LA(2) >= '\u0000' && LA(2) <= '\uffff')) && ((LA(3) >= '\u0000' && LA(3) <= '\uffff')) && ((LA(4) >= '\u0000' && LA(4) <= '\uffff'))) {
				match('\n');
			}
			else if ((LA(1)=='\r') && ((LA(2) >= '\u0000' && LA(2) <= '\uffff')) && ((LA(3) >= '\u0000' && LA(3) <= '\uffff')) && ((LA(4) >= '\u0000' && LA(4) <= '\uffff'))) {
				match('\r');
			}
			else if ((LA(1)=='\r') && (LA(2)=='\n') && ((LA(3) >= '\u0000' && LA(3) <= '\uffff')) && ((LA(4) >= '\u0000' && LA(4) <= '\uffff'))) {
				match("\r\n");
			}
			else {
				break _loop502;
			}
			
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mMARKUP_NAME(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = MARKUP_NAME;
		int _saveIndex;
		
		if ((_tokenSet_10.member(LA(1)))) {
			{
			int _cnt507=0;
			_loop507:
			do {
				if ((_tokenSet_1.member(LA(1))) && (_tokenSet_11.member(LA(2))) && ((LA(3) >= '\u0000' && LA(3) <= '\uffff')) && ((LA(4) >= '\u0000' && LA(4) <= '\uffff'))) {
					mLETTER(false);
				}
				else if (((LA(1) >= '0' && LA(1) <= '9')) && (_tokenSet_11.member(LA(2))) && ((LA(3) >= '\u0000' && LA(3) <= '\uffff')) && ((LA(4) >= '\u0000' && LA(4) <= '\uffff'))) {
					mDIGIT(false);
				}
				else {
					if ( _cnt507>=1 ) { break _loop507; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
				}
				
				_cnt507++;
			} while (true);
			}
		}
		else if ((LA(1)=='{')) {
			match('{');
			{
			int _cnt509=0;
			_loop509:
			do {
				if ((_tokenSet_1.member(LA(1)))) {
					mLETTER(false);
				}
				else if (((LA(1) >= '0' && LA(1) <= '9'))) {
					mDIGIT(false);
				}
				else {
					if ( _cnt509>=1 ) { break _loop509; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
				}
				
				_cnt509++;
			} while (true);
			}
			match('}');
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mMARKUP_ATTRS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = MARKUP_ATTRS;
		int _saveIndex;
		
		{
		_loop499:
		do {
			if ((_tokenSet_2.member(LA(1)))) {
				{
				int _cnt496=0;
				_loop496:
				do {
					if ((_tokenSet_2.member(LA(1)))) {
						mMARKUP_NAME(false);
					}
					else {
						if ( _cnt496>=1 ) { break _loop496; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
					}
					
					_cnt496++;
				} while (true);
				}
				mMARKUP_WS(false);
				match('=');
				mMARKUP_WS(false);
				match('"');
				{
				_loop498:
				do {
					if ((_tokenSet_12.member(LA(1)))) {
						mDQ_STRING_CHAR(false);
					}
					else {
						break _loop498;
					}
					
				} while (true);
				}
				match('"');
				mMARKUP_WS(false);
			}
			else {
				break _loop499;
			}
			
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mMARKUP_CONTENT_CHAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = MARKUP_CONTENT_CHAR;
		int _saveIndex;
		
		{
		match(_tokenSet_8);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mDQ_STRING_CHAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DQ_STRING_CHAR;
		int _saveIndex;
		
		switch ( LA(1)) {
		case '\\':
		{
			mESC(false);
			break;
		}
		case '{':
		{
			match('{');
			break;
		}
		case '\n':
		{
			match('\n');
			break;
		}
		case '\r':
		{
			match('\r');
			break;
		}
		case '\'':
		{
			{
			match('\'');
			{
			if ((_tokenSet_13.member(LA(1)))) {
				matchNot('\'');
			}
			else if ((LA(1)=='\'')) {
				match('\'');
				matchNot('\'');
			}
			else {
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			
			}
			}
			break;
		}
		default:
			if ((_tokenSet_14.member(LA(1))) && ((LA(2) >= '\u0000' && LA(2) <= '\ufffe')) && ((LA(3) >= '\u0000' && LA(3) <= '\uffff')) && ((LA(4) >= '\u0000' && LA(4) <= '\uffff'))) {
				mSTRING_CHAR(false);
			}
			else if ((LA(1)=='$') && ((LA(2) >= '\u0000' && LA(2) <= '\ufffe')) && ((LA(3) >= '\u0000' && LA(3) <= '\uffff')) && ((LA(4) >= '\u0000' && LA(4) <= '\uffff'))) {
				match('$');
			}
			else if ((LA(1)=='}') && ((LA(2) >= '\u0000' && LA(2) <= '\ufffe')) && ((LA(3) >= '\u0000' && LA(3) <= '\uffff')) && ((LA(4) >= '\u0000' && LA(4) <= '\uffff'))) {
				match('}');
			}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mID(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ID;
		int _saveIndex;
		boolean isEmail = false, isHasSepcialChar = false, isColon = false;
		
		{
		if ((_tokenSet_1.member(LA(1))) && (true) && (true) && (true)) {
			mLETTER(false);
		}
		else if ((LA(1)=='_') && (true) && (true) && (true)) {
			match('_');
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		
		}
		{
		_loop513:
		do {
			if ((_tokenSet_1.member(LA(1))) && (true) && (true) && (true)) {
				mLETTER(false);
			}
			else if ((LA(1)=='_') && (true) && (true) && (true)) {
				match('_');
			}
			else if (((LA(1) >= '0' && LA(1) <= '9'))) {
				mDIGIT(false);
			}
			else {
				break _loop513;
			}
			
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			
				  		char c = LA(1);
				  		if (c == '+' || c == '-' || c == '.') {
				  			int i = 3;
				  			c = LA(2);
				  			isHasSepcialChar = true;
				  			while (c != ' ' && c != '\n' && c != '\r' && c != '\uffff') {
				  				if (c == '@') {
				  					isEmail = true;
				  					break;
				  				}
				  				c = LA(i++);
				  			}
				  		}
				  		else if (c == '@') {
				  			isEmail = true;
				  		}
				  	
		}
		{
		if (((LA(1)=='+'||LA(1)=='-'||LA(1)=='.'||LA(1)=='@'))&&(isEmail)) {
			{
			switch ( LA(1)) {
			case '+':  case '-':  case '.':
			{
				{
				switch ( LA(1)) {
				case '+':
				{
					match('+');
					break;
				}
				case '-':
				{
					match('-');
					break;
				}
				case '.':
				{
					match('.');
					break;
				}
				default:
				{
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
				}
				}
				}
				{
				_loop518:
				do {
					if ((_tokenSet_15.member(LA(1)))) {
						mDATE_CHAR(false);
					}
					else {
						break _loop518;
					}
					
				} while (true);
				}
				break;
			}
			case '@':
			{
				break;
			}
			default:
			{
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			}
			}
			match('@');
			{
			{
			int _cnt521=0;
			_loop521:
			do {
				if ((_tokenSet_15.member(LA(1)))) {
					mDATE_CHAR(false);
				}
				else {
					if ( _cnt521>=1 ) { break _loop521; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
				}
				
				_cnt521++;
			} while (true);
			}
			match('.');
			{
			int _cnt523=0;
			_loop523:
			do {
				if ((_tokenSet_15.member(LA(1)))) {
					mDATE_CHAR(false);
				}
				else {
					if ( _cnt523>=1 ) { break _loop523; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
				}
				
				_cnt523++;
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				
					  					_ttype = EMAIL;
					  				
			}
			}
		}
		else {
			boolean synPredMatched525 = false;
			if (( true )) {
				int _m525 = mark();
				synPredMatched525 = true;
				inputState.guessing++;
				try {
					{
					match("://");
					}
				}
				catch (RecognitionException pe) {
					synPredMatched525 = false;
				}
				rewind(_m525);
inputState.guessing--;
			}
			if ( synPredMatched525 ) {
				{
				if ((LA(1)==':')) {
					match("://");
					{
					int _cnt528=0;
					_loop528:
					do {
						if ((_tokenSet_16.member(LA(1)))) {
							mURL_CHAR(false);
						}
						else {
							if ( _cnt528>=1 ) { break _loop528; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
						}
						
						_cnt528++;
					} while (true);
					}
					if ( inputState.guessing==0 ) {
						
								  			_ttype = URL;
								  		
					}
				}
				else {
				}
				
				}
			}
			else {
			}
			}
			}
			if ( inputState.guessing==0 ) {
				
							allowFilePath = false;
					  	
			}
			_ttype = testLiteralsTable(_ttype);
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		}
		
	public final void mINTEGER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = INTEGER;
		int _saveIndex;
		
				boolean isPercentage = false;
				boolean isDecimal = true;
			
		
		{
		{
		switch ( LA(1)) {
		case '0':
		{
			{
			match('0');
			if ( inputState.guessing==0 ) {
				isDecimal = true;
			}
			{
			switch ( LA(1)) {
			case 'B':  case 'b':
			{
				{
				switch ( LA(1)) {
				case 'b':
				{
					match('b');
					break;
				}
				case 'B':
				{
					match('B');
					break;
				}
				default:
				{
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
				}
				}
				}
				{
				switch ( LA(1)) {
				case '0':
				{
					match('0');
					break;
				}
				case '1':
				{
					match('1');
					break;
				}
				default:
				{
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
				}
				}
				}
				{
				if ((LA(1)=='0'||LA(1)=='1'||LA(1)=='_')) {
					{
					_loop542:
					do {
						if ((LA(1)=='0') && (LA(2)=='0'||LA(2)=='1'||LA(2)=='_')) {
							match('0');
						}
						else if ((LA(1)=='1') && (LA(2)=='0'||LA(2)=='1'||LA(2)=='_')) {
							match('1');
						}
						else if ((LA(1)=='_')) {
							match('_');
						}
						else {
							break _loop542;
						}
						
					} while (true);
					}
					{
					switch ( LA(1)) {
					case '0':
					{
						match('0');
						break;
					}
					case '1':
					{
						match('1');
						break;
					}
					default:
					{
						throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
					}
					}
					}
				}
				else {
				}
				
				}
				if ( inputState.guessing==0 ) {
					isDecimal = false;
				}
				break;
			}
			case '0':  case '1':  case '2':  case '3':
			case '4':  case '5':  case '6':  case '7':
			{
				{
				matchRange('0','7');
				}
				{
				if ((LA(1)=='0'||LA(1)=='1'||LA(1)=='2'||LA(1)=='3'||LA(1)=='4'||LA(1)=='5'||LA(1)=='6'||LA(1)=='7'||LA(1)=='_')) {
					{
					_loop547:
					do {
						if (((LA(1) >= '0' && LA(1) <= '7')) && (LA(2)=='0'||LA(2)=='1'||LA(2)=='2'||LA(2)=='3'||LA(2)=='4'||LA(2)=='5'||LA(2)=='6'||LA(2)=='7'||LA(2)=='_')) {
							matchRange('0','7');
						}
						else if ((LA(1)=='_')) {
							match('_');
						}
						else {
							break _loop547;
						}
						
					} while (true);
					}
					{
					matchRange('0','7');
					}
				}
				else {
				}
				
				}
				if ( inputState.guessing==0 ) {
					isDecimal = false;
				}
				break;
			}
			default:
				if ((LA(1)=='X'||LA(1)=='x') && (LA(2)=='0'||LA(2)=='1'||LA(2)=='2'||LA(2)=='3'||LA(2)=='4'||LA(2)=='5'||LA(2)=='6'||LA(2)=='7'||LA(2)=='8'||LA(2)=='9'||LA(2)=='a'||LA(2)=='b'||LA(2)=='c'||LA(2)=='d'||LA(2)=='e'||LA(2)=='f') && (true) && (true)) {
					{
					switch ( LA(1)) {
					case 'x':
					{
						match('x');
						break;
					}
					case 'X':
					{
						match('X');
						break;
					}
					default:
					{
						throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
					}
					}
					}
					mHEX_DIGIT(false);
					{
					if ((LA(1)=='0'||LA(1)=='1'||LA(1)=='2'||LA(1)=='3'||LA(1)=='4'||LA(1)=='5'||LA(1)=='6'||LA(1)=='7'||LA(1)=='8'||LA(1)=='9'||LA(1)=='_'||LA(1)=='a'||LA(1)=='b'||LA(1)=='c'||LA(1)=='d'||LA(1)=='e'||LA(1)=='f')) {
						{
						_loop537:
						do {
							if ((LA(1)=='0'||LA(1)=='1'||LA(1)=='2'||LA(1)=='3'||LA(1)=='4'||LA(1)=='5'||LA(1)=='6'||LA(1)=='7'||LA(1)=='8'||LA(1)=='9'||LA(1)=='a'||LA(1)=='b'||LA(1)=='c'||LA(1)=='d'||LA(1)=='e'||LA(1)=='f') && (LA(2)=='0'||LA(2)=='1'||LA(2)=='2'||LA(2)=='3'||LA(2)=='4'||LA(2)=='5'||LA(2)=='6'||LA(2)=='7'||LA(2)=='8'||LA(2)=='9'||LA(2)=='_'||LA(2)=='a'||LA(2)=='b'||LA(2)=='c'||LA(2)=='d'||LA(2)=='e'||LA(2)=='f')) {
								mHEX_DIGIT(false);
							}
							else if ((LA(1)=='_')) {
								match('_');
							}
							else {
								break _loop537;
							}
							
						} while (true);
						}
						mHEX_DIGIT(false);
					}
					else {
					}
					
					}
					if ( inputState.guessing==0 ) {
						isDecimal = false;
					}
				}
				else {
				}
			}
			}
			}
			break;
		}
		case '1':  case '2':  case '3':  case '4':
		case '5':  case '6':  case '7':  case '8':
		case '9':
		{
			{
			matchRange('1','9');
			}
			{
			if ((LA(1)=='0'||LA(1)=='1'||LA(1)=='2'||LA(1)=='3'||LA(1)=='4'||LA(1)=='5'||LA(1)=='6'||LA(1)=='7'||LA(1)=='8'||LA(1)=='9'||LA(1)=='_')) {
				mDIGITS_UNDERLINE(false);
				mDIGIT(false);
			}
			else {
			}
			
			}
			if ( inputState.guessing==0 ) {
				isDecimal = true;
			}
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		{
		boolean synPredMatched553 = false;
		if ((((LA(1)==':'))&&(isDecimal))) {
			int _m553 = mark();
			synPredMatched553 = true;
			inputState.guessing++;
			try {
				{
				match(':');
				mDIGIT(false);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched553 = false;
			}
			rewind(_m553);
inputState.guessing--;
		}
		if ( synPredMatched553 ) {
			{
			match(':');
			{
			mDIGIT(false);
			{
			if ((LA(1)=='0'||LA(1)=='1'||LA(1)=='2'||LA(1)=='3'||LA(1)=='4'||LA(1)=='5'||LA(1)=='6'||LA(1)=='7'||LA(1)=='8'||LA(1)=='9'||LA(1)=='_')) {
				mDIGITS_UNDERLINE(false);
				mDIGIT(false);
			}
			else {
			}
			
			}
			}
			{
			if ((LA(1)==':')) {
				match(':');
				mDIGIT(false);
				{
				if ((LA(1)=='0'||LA(1)=='1'||LA(1)=='2'||LA(1)=='3'||LA(1)=='4'||LA(1)=='5'||LA(1)=='6'||LA(1)=='7'||LA(1)=='8'||LA(1)=='9'||LA(1)=='_')) {
					mDIGITS_UNDERLINE(false);
					mDIGIT(false);
				}
				else {
				}
				
				}
			}
			else {
			}
			
			}
			if ( inputState.guessing==0 ) {
				
										_ttype = TIME;
									
			}
			}
		}
		else {
		}
		
		}
		}
		{
		if ((LA(1)=='-'||LA(1)=='/'||LA(1)=='\\')) {
			{
			if (!(isDecimal))
			  throw new SemanticException("isDecimal");
			{
			switch ( LA(1)) {
			case '/':
			{
				{
				match('/');
				mDIGITS(false);
				match('/');
				mDIGITS(false);
				}
				break;
			}
			case '-':
			{
				{
				match('-');
				mDIGITS(false);
				match('-');
				mDIGITS(false);
				}
				break;
			}
			case '\\':
			{
				{
				match('\\');
				mDIGITS(false);
				match('\\');
				mDIGITS(false);
				}
				break;
			}
			default:
			{
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			}
			}
			}
			if ( inputState.guessing==0 ) {
				
								_ttype = DATE;
							
			}
		}
		else {
			{
			boolean synPredMatched568 = false;
			if ((((LA(1)=='.'))&&(isDecimal))) {
				int _m568 = mark();
				synPredMatched568 = true;
				inputState.guessing++;
				try {
					{
					match('.');
					{
					matchRange('0','9');
					}
					}
				}
				catch (RecognitionException pe) {
					synPredMatched568 = false;
				}
				rewind(_m568);
inputState.guessing--;
			}
			if ( synPredMatched568 ) {
				mDOT_FLOAT_DIGITS(false);
				if ( inputState.guessing==0 ) {
					
										if (_ttype != TIME)
											_ttype = FLOAT;
									
				}
				{
				boolean synPredMatched572 = false;
				if (((LA(1)=='.'))) {
					int _m572 = mark();
					synPredMatched572 = true;
					inputState.guessing++;
					try {
						{
						match('.');
						{
						matchRange('0','9');
						}
						}
					}
					catch (RecognitionException pe) {
						synPredMatched572 = false;
					}
					rewind(_m572);
inputState.guessing--;
				}
				if ( synPredMatched572 ) {
					match('.');
					mDECIMAL_DIGITS(false);
					if ( inputState.guessing==0 ) {
						
												_ttype = DATE;
											
					}
				}
				else {
				}
				
				}
			}
			else {
			}
			
			}
		}
		
		}
		{
		if ((LA(1)=='X'||LA(1)=='x')) {
			{
			{
			switch ( LA(1)) {
			case 'x':
			{
				match('x');
				break;
			}
			case 'X':
			{
				match('X');
				break;
			}
			default:
			{
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			}
			}
			{
			{
			{
			switch ( LA(1)) {
			case '0':
			{
				match('0');
				break;
			}
			case '1':  case '2':  case '3':  case '4':
			case '5':  case '6':  case '7':  case '8':
			case '9':
			{
				mDECIMAL_DIGITS(false);
				break;
			}
			default:
			{
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				
										_ttype = PAIR;
								
			}
			}
			{
			_loop583:
			do {
				if ((LA(1)=='X'||LA(1)=='x') && ((LA(2) >= '0' && LA(2) <= '9'))) {
					{
					switch ( LA(1)) {
					case 'x':
					{
						match('x');
						break;
					}
					case 'X':
					{
						match('X');
						break;
					}
					default:
					{
						throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
					}
					}
					}
					{
					switch ( LA(1)) {
					case '0':
					{
						match('0');
						break;
					}
					case '1':  case '2':  case '3':  case '4':
					case '5':  case '6':  case '7':  case '8':
					case '9':
					{
						mDECIMAL_DIGITS(false);
						break;
					}
					default:
					{
						throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
					}
					}
					}
				}
				else {
					break _loop583;
				}
				
			} while (true);
			}
			{
			if ((LA(1)=='X'||LA(1)=='x')) {
				{
				switch ( LA(1)) {
				case 'x':
				{
					_saveIndex=text.length();
					match('x');
					text.setLength(_saveIndex);
					break;
				}
				case 'X':
				{
					_saveIndex=text.length();
					match('X');
					text.setLength(_saveIndex);
					break;
				}
				default:
				{
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
				}
				}
				}
				_saveIndex=text.length();
				match('{');
				text.setLength(_saveIndex);
				if ( inputState.guessing==0 ) {
					
										_ttype = PAIR_LCURLY;
									
				}
			}
			else {
			}
			
			}
			}
			}
		}
		else {
			if ( inputState.guessing==0 ) {
				
								if (_ttype != TIME && LA(1) == '%') {
									char c = LA(2);
									int i = 3;
									while (c == ' ') {
										c = LA(i++);
									}
									isPercentage = (c == '\n' || c == '\r' || c == ';' || c == '\uffff');
								}
							
			}
			{
			if (((LA(1)=='%'))&&(isPercentage)) {
				match('%');
				if ( inputState.guessing==0 ) {
					_ttype = PERCENTAGE;
				}
			}
			else {
			}
			
			}
		}
		
		}
		if ( inputState.guessing==0 ) {
			
						allowFilePath = false;
				  	
		}
		_ttype = testLiteralsTable(_ttype);
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mDATE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DATE;
		int _saveIndex;
		
		String pairText;
		
		
		_saveIndex=text.length();
		match('#');
		text.setLength(_saveIndex);
		{
		switch ( LA(1)) {
		case '0':
		{
			match('0');
			break;
		}
		case '1':  case '2':  case '3':  case '4':
		case '5':  case '6':  case '7':  case '8':
		case '9':
		{
			mDECIMAL_DIGITS(false);
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		{
		switch ( LA(1)) {
		case '/':
		{
			{
			match('/');
			mDIGITS(false);
			match('/');
			mDIGITS(false);
			}
			break;
		}
		case '-':
		{
			{
			match('-');
			mDIGITS(false);
			match('-');
			mDIGITS(false);
			}
			break;
		}
		case '\\':
		{
			{
			match('\\');
			mDIGITS(false);
			match('\\');
			mDIGITS(false);
			}
			break;
		}
		case '.':
		{
			{
			match('.');
			mDIGITS(false);
			match('.');
			mDIGITS(false);
			}
			break;
		}
		case 'X':  case 'x':
		{
			{
			{
			switch ( LA(1)) {
			case 'x':
			{
				match('x');
				break;
			}
			case 'X':
			{
				match('X');
				break;
			}
			default:
			{
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			}
			}
			{
			switch ( LA(1)) {
			case '0':  case '1':  case '2':  case '3':
			case '4':  case '5':  case '6':  case '7':
			case '8':  case '9':
			{
				{
				{
				switch ( LA(1)) {
				case '0':
				{
					match('0');
					break;
				}
				case '1':  case '2':  case '3':  case '4':
				case '5':  case '6':  case '7':  case '8':
				case '9':
				{
					mDECIMAL_DIGITS(false);
					break;
				}
				default:
				{
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
				}
				}
				}
				if ( inputState.guessing==0 ) {
					
											_ttype = PAIR;
										
				}
				}
				{
				_loop601:
				do {
					if ((LA(1)=='X'||LA(1)=='x') && ((LA(2) >= '0' && LA(2) <= '9'))) {
						{
						switch ( LA(1)) {
						case 'x':
						{
							match('x');
							break;
						}
						case 'X':
						{
							match('X');
							break;
						}
						default:
						{
							throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
						}
						}
						}
						{
						switch ( LA(1)) {
						case '0':
						{
							match('0');
							break;
						}
						case '1':  case '2':  case '3':  case '4':
						case '5':  case '6':  case '7':  case '8':
						case '9':
						{
							mDECIMAL_DIGITS(false);
							break;
						}
						default:
						{
							throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
						}
						}
						}
					}
					else {
						break _loop601;
					}
					
				} while (true);
				}
				{
				if ((LA(1)=='X'||LA(1)=='x')) {
					{
					switch ( LA(1)) {
					case 'x':
					{
						_saveIndex=text.length();
						match('x');
						text.setLength(_saveIndex);
						break;
					}
					case 'X':
					{
						_saveIndex=text.length();
						match('X');
						text.setLength(_saveIndex);
						break;
					}
					default:
					{
						throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
					}
					}
					}
					_saveIndex=text.length();
					match('{');
					text.setLength(_saveIndex);
					if ( inputState.guessing==0 ) {
						
												_ttype = PAIR_LCURLY;
											
					}
				}
				else {
				}
				
				}
				break;
			}
			case '{':
			{
				{
				_saveIndex=text.length();
				match('{');
				text.setLength(_saveIndex);
				if ( inputState.guessing==0 ) {
					
											pairText = new String(text.getBuffer(),_begin,text.length()-_begin);
											pairText = pairText.substring(0, pairText.length() - 1);
											text.setLength(_begin); text.append(pairText);
											_ttype = PAIR_LCURLY;
										
				}
				}
				break;
			}
			default:
			{
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			}
			}
			}
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		if ( inputState.guessing==0 ) {
			
					allowFilePath = false;
				
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mRFC822(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = RFC822;
		int _saveIndex;
		
		switch ( LA(1)) {
		case 'G':
		{
			match("GMT");
			break;
		}
		case 'U':
		{
			match("UTC");
			break;
		}
		case 'V':
		{
			match("VST");
			break;
		}
		case 'J':
		{
			match("JST");
			break;
		}
		case 'S':
		{
			match("SST");
			break;
		}
		case 'H':
		{
			match("HST");
			break;
		}
		default:
			if ((LA(1)=='E') && (LA(2)=='C')) {
				match("ECT");
			}
			else if ((LA(1)=='E') && (LA(2)=='E')) {
				match("EET");
			}
			else if ((LA(1)=='A') && (LA(2)=='R')) {
				match("ART");
			}
			else if ((LA(1)=='E') && (LA(2)=='A')) {
				match("EAT");
			}
			else if ((LA(1)=='M') && (LA(2)=='E')) {
				match("MET");
			}
			else if ((LA(1)=='N') && (LA(2)=='E')) {
				match("NET");
			}
			else if ((LA(1)=='P') && (LA(2)=='L')) {
				match("PLT");
			}
			else if ((LA(1)=='I') && (LA(2)=='S')) {
				match("IST");
			}
			else if ((LA(1)=='B') && (LA(2)=='S')) {
				match("BST");
			}
			else if ((LA(1)=='C') && (LA(2)=='T')) {
				match("CTT");
			}
			else if ((LA(1)=='A') && (LA(2)=='C')) {
				match("ACT");
			}
			else if ((LA(1)=='A') && (LA(2)=='E')) {
				match("AET");
			}
			else if ((LA(1)=='N') && (LA(2)=='S')) {
				match("NST");
			}
			else if ((LA(1)=='M') && (LA(2)=='I')) {
				match("MIT");
			}
			else if ((LA(1)=='A') && (LA(2)=='S')) {
				match("AST");
			}
			else if ((LA(1)=='P') && (LA(2)=='S')) {
				match("PST");
			}
			else if ((LA(1)=='P') && (LA(2)=='N')) {
				match("PNT");
			}
			else if ((LA(1)=='M') && (LA(2)=='S')) {
				match("MST");
			}
			else if ((LA(1)=='C') && (LA(2)=='S')) {
				match("CST");
			}
			else if ((LA(1)=='E') && (LA(2)=='S')) {
				match("EST");
			}
			else if ((LA(1)=='I') && (LA(2)=='E')) {
				match("IET");
			}
			else if ((LA(1)=='P') && (LA(2)=='R')) {
				match("PRT");
			}
			else if ((LA(1)=='C') && (LA(2)=='N')) {
				match("CNT");
			}
			else if ((LA(1)=='A') && (LA(2)=='G')) {
				match("AGT");
			}
			else if ((LA(1)=='B') && (LA(2)=='E')) {
				match("BET");
			}
			else if ((LA(1)=='C') && (LA(2)=='A')) {
				match("CAT");
			}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mUNIX_HEADER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = UNIX_HEADER;
		int _saveIndex;
		
		match('#');
		match('!');
		{
		_loop609:
		do {
			if ((_tokenSet_17.member(LA(1)))) {
				{
				match(_tokenSet_17);
				}
			}
			else {
				break _loop609;
			}
			
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			_ttype = Token.SKIP;
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mREFRENCE_NAME(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = REFRENCE_NAME;
		int _saveIndex;
		
		match('#');
		{
		if ((LA(1)=='_') && (true) && (true) && (true)) {
			match('_');
		}
		else if ((LA(1)=='$')) {
			match('$');
		}
		else if ((_tokenSet_1.member(LA(1))) && (true) && (true) && (true)) {
			mLETTER(false);
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		
		}
		{
		_loop613:
		do {
			switch ( LA(1)) {
			case '$':
			{
				match('$');
				break;
			}
			case '0':  case '1':  case '2':  case '3':
			case '4':  case '5':  case '6':  case '7':
			case '8':  case '9':
			{
				mDIGIT(false);
				break;
			}
			default:
				if ((LA(1)=='_') && (true) && (true) && (true)) {
					match('_');
				}
				else if ((_tokenSet_1.member(LA(1))) && (true) && (true) && (true)) {
					mLETTER(false);
				}
			else {
				break _loop613;
			}
			}
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			
				        String refText = new String(text.getBuffer(),_begin,text.length()-_begin);
				        refText = refText.substring(1, refText.length());
				        text.setLength(_begin); text.append(refText);
				
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mRCURLY(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = RCURLY;
		int _saveIndex;
		
		int tt = RCURLY;
		
		
		_saveIndex=text.length();
		match('}');
		text.setLength(_saveIndex);
		if ( inputState.guessing==0 ) {
			
				        popCurlyLevel();
				
		}
		{
		boolean synPredMatched617 = false;
		if (((LA(1)=='X'||LA(1)=='x') && (LA(2)=='0'||LA(2)=='1'||LA(2)=='2'||LA(2)=='3'||LA(2)=='4'||LA(2)=='5'||LA(2)=='6'||LA(2)=='7'||LA(2)=='8'||LA(2)=='9'||LA(2)=='{') && (true) && (true))) {
			int _m617 = mark();
			synPredMatched617 = true;
			inputState.guessing++;
			try {
				{
				switch ( LA(1)) {
				case 'x':
				{
					match('x');
					break;
				}
				case 'X':
				{
					match('X');
					break;
				}
				default:
				{
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
				}
				}
				}
			}
			catch (RecognitionException pe) {
				synPredMatched617 = false;
			}
			rewind(_m617);
inputState.guessing--;
		}
		if ( synPredMatched617 ) {
			{
			switch ( LA(1)) {
			case 'x':
			{
				_saveIndex=text.length();
				match('x');
				text.setLength(_saveIndex);
				break;
			}
			case 'X':
			{
				_saveIndex=text.length();
				match('X');
				text.setLength(_saveIndex);
				break;
			}
			default:
			{
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			}
			}
			{
			switch ( LA(1)) {
			case '0':  case '1':  case '2':  case '3':
			case '4':  case '5':  case '6':  case '7':
			case '8':  case '9':
			{
				{
				{
				switch ( LA(1)) {
				case '0':
				{
					match('0');
					break;
				}
				case '1':  case '2':  case '3':  case '4':
				case '5':  case '6':  case '7':  case '8':
				case '9':
				{
					mDECIMAL_DIGITS(false);
					break;
				}
				default:
				{
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
				}
				}
				}
				if ( inputState.guessing==0 ) {
					
										tt = RCURLY_X_PAIR;
									
				}
				}
				{
				_loop625:
				do {
					if ((LA(1)=='X'||LA(1)=='x') && ((LA(2) >= '0' && LA(2) <= '9'))) {
						{
						switch ( LA(1)) {
						case 'x':
						{
							match('x');
							break;
						}
						case 'X':
						{
							match('X');
							break;
						}
						default:
						{
							throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
						}
						}
						}
						{
						switch ( LA(1)) {
						case '0':
						{
							match('0');
							break;
						}
						case '1':  case '2':  case '3':  case '4':
						case '5':  case '6':  case '7':  case '8':
						case '9':
						{
							mDECIMAL_DIGITS(false);
							break;
						}
						default:
						{
							throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
						}
						}
						}
					}
					else {
						break _loop625;
					}
					
				} while (true);
				}
				{
				if ((LA(1)=='X'||LA(1)=='x')) {
					{
					switch ( LA(1)) {
					case 'x':
					{
						_saveIndex=text.length();
						match('x');
						text.setLength(_saveIndex);
						break;
					}
					case 'X':
					{
						_saveIndex=text.length();
						match('X');
						text.setLength(_saveIndex);
						break;
					}
					default:
					{
						throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
					}
					}
					}
					_saveIndex=text.length();
					match('{');
					text.setLength(_saveIndex);
					if ( inputState.guessing==0 ) {
						
											    tt = RCURLY_X_PAIR_LCURLY;
										
					}
				}
				else {
				}
				
				}
				break;
			}
			case '{':
			{
				{
				_saveIndex=text.length();
				match('{');
				text.setLength(_saveIndex);
				if ( inputState.guessing==0 ) {
					
										tt = RCURLY_X_LCURLY;
									
				}
				}
				break;
			}
			default:
			{
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			}
			}
		}
		else {
			boolean synPredMatched630 = false;
			if (((_tokenSet_18.member(LA(1))) && (true) && (true) && (true))) {
				int _m630 = mark();
				synPredMatched630 = true;
				inputState.guessing++;
				try {
					{
					if (!(stringPartType == STR_SQ_START))
					  throw new SemanticException("stringPartType == STR_SQ_START");
					}
				}
				catch (RecognitionException pe) {
					synPredMatched630 = false;
				}
				rewind(_m630);
inputState.guessing--;
			}
			if ( synPredMatched630 ) {
				tt=mSTRING_PART(false,false, false);
			}
			else {
				boolean synPredMatched632 = false;
				if (((_tokenSet_18.member(LA(1))) && (true) && (true) && (true))) {
					int _m632 = mark();
					synPredMatched632 = true;
					inputState.guessing++;
					try {
						{
						if (!(stringPartType == STR_TQ_START))
						  throw new SemanticException("stringPartType == STR_TQ_START");
						}
					}
					catch (RecognitionException pe) {
						synPredMatched632 = false;
					}
					rewind(_m632);
inputState.guessing--;
				}
				if ( synPredMatched632 ) {
					tt=mSTRING_PART(false,false, true);
				}
				else {
					boolean synPredMatched634 = false;
					if (((_tokenSet_17.member(LA(1))) && (true) && (true) && (true))) {
						int _m634 = mark();
						synPredMatched634 = true;
						inputState.guessing++;
						try {
							{
							if (!(stringPartType == REGEX_START))
							  throw new SemanticException("stringPartType == REGEX_START");
							}
						}
						catch (RecognitionException pe) {
							synPredMatched634 = false;
						}
						rewind(_m634);
inputState.guessing--;
					}
					if ( synPredMatched634 ) {
						tt=mREGEX_PART(false,false, false);
						{
						if (((_tokenSet_17.member(LA(1))))&&(tt == REGEX_NUM_PART)) {
							{
							int _cnt637=0;
							_loop637:
							do {
								if (((_tokenSet_17.member(LA(1))))&&(tt == REGEX_NUM_PART)) {
									tt=mREGEX_PART(false,false, true);
								}
								else {
									if ( _cnt637>=1 ) { break _loop637; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
								}
								
								_cnt637++;
							} while (true);
							}
						}
						else {
						}
						
						}
					}
					else {
						boolean synPredMatched639 = false;
						if (( true )) {
							int _m639 = mark();
							synPredMatched639 = true;
							inputState.guessing++;
							try {
								{
								if (!(stringPartType == FILE_PATH_START))
								  throw new SemanticException("stringPartType == FILE_PATH_START");
								}
							}
							catch (RecognitionException pe) {
								synPredMatched639 = false;
							}
							rewind(_m639);
inputState.guessing--;
						}
						if ( synPredMatched639 ) {
							{
							boolean synPredMatched642 = false;
							if (((_tokenSet_19.member(LA(1))))) {
								int _m642 = mark();
								synPredMatched642 = true;
								inputState.guessing++;
								try {
									{
									mFILE_CHAR(false);
									}
								}
								catch (RecognitionException pe) {
									synPredMatched642 = false;
								}
								rewind(_m642);
inputState.guessing--;
							}
							if ( synPredMatched642 ) {
								tt=mFILE_PATH_PART(false,false);
							}
							else {
								if ( inputState.guessing==0 ) {
									tt = FILE_PATH_END;
								}
							}
							
							}
						}
						else {
							boolean synPredMatched644 = false;
							if (( true )) {
								int _m644 = mark();
								synPredMatched644 = true;
								inputState.guessing++;
								try {
									{
									if (!(stringPartType == 0))
									  throw new SemanticException("stringPartType == 0");
									}
								}
								catch (RecognitionException pe) {
									synPredMatched644 = false;
								}
								rewind(_m644);
inputState.guessing--;
							}
							if ( synPredMatched644 ) {
								if ( inputState.guessing==0 ) {
									
									//System.out.println("Current Curly Level: " + currentCurlyLevel);
									text.setLength(_begin); text.append("}");
											    tt = RCURLY;
											    currentCurlyLevel--;
											
								}
							}
							else {
								throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
							}
							}}}}}
							}
							if ( inputState.guessing==0 ) {
								_ttype = tt;
							}
							if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
								_token = makeToken(_ttype);
								_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
							}
							_returnToken = _token;
						}
						
	protected final int  mSTRING_PART(boolean _createToken,
		boolean start, boolean tripleQuote
	) throws RecognitionException, CharStreamException, TokenStreamException {
		int tt = STR_PART_END;
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = STRING_PART;
		int _saveIndex;
		
		{
		_loop759:
		do {
			boolean synPredMatched755 = false;
			if ((((LA(1)=='"') && (_tokenSet_18.member(LA(2))) && (true) && (true))&&(tripleQuote))) {
				int _m755 = mark();
				synPredMatched755 = true;
				inputState.guessing++;
				try {
					{
					match('"');
					{
					if ((_tokenSet_20.member(LA(1)))) {
						matchNot('"');
					}
					else if ((LA(1)=='"')) {
						match('"');
						matchNot('"');
					}
					else {
						throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
					}
					
					}
					}
				}
				catch (RecognitionException pe) {
					synPredMatched755 = false;
				}
				rewind(_m755);
inputState.guessing--;
			}
			if ( synPredMatched755 ) {
				match('"');
			}
			else if ((_tokenSet_14.member(LA(1)))) {
				mSTRING_CHAR(false);
			}
			else if ((LA(1)=='\\')) {
				mESC(false);
			}
			else {
				boolean synPredMatched757 = false;
				if ((((LA(1)=='\n'||LA(1)=='\r'))&&(tripleQuote))) {
					int _m757 = mark();
					synPredMatched757 = true;
					inputState.guessing++;
					try {
						{
						switch ( LA(1)) {
						case '\n':
						{
							match('\n');
							break;
						}
						case '\r':
						{
							match('\r');
							break;
						}
						default:
						{
							throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
						}
						}
						}
					}
					catch (RecognitionException pe) {
						synPredMatched757 = false;
					}
					rewind(_m757);
inputState.guessing--;
				}
				if ( synPredMatched757 ) {
					{
					switch ( LA(1)) {
					case '\n':
					{
						match('\n');
						break;
					}
					case '\r':
					{
						match('\r');
						break;
					}
					default:
					{
						throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
					}
					}
					}
				}
				else {
					break _loop759;
				}
				}
			} while (true);
			}
			{
			switch ( LA(1)) {
			case '"':
			{
				{
				if (((LA(1)=='"') && (LA(2)=='"'))&&(tripleQuote)) {
					_saveIndex=text.length();
					match("\"\"\"");
					text.setLength(_saveIndex);
				}
				else if (((LA(1)=='"') && (true))&&(!tripleQuote)) {
					_saveIndex=text.length();
					match("\"");
					text.setLength(_saveIndex);
				}
				else {
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
				}
				
				}
				if ( inputState.guessing==0 ) {
					
					tt = start ? STRING : STR_PART_END;
					stringPartType = 0;
					
				}
				break;
			}
			case '{':
			{
				_saveIndex=text.length();
				match('{');
				text.setLength(_saveIndex);
				if ( inputState.guessing==0 ) {
					
					if (start) {
					tt = tripleQuote ? STR_TQ_START : STR_SQ_START;
					stringPartType = tt;
					}
					else {
					tt = STR_PART_MIDDLE;
					}
					pushCurlyLevel();
					
				}
				break;
			}
			default:
			{
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				_ttype = tt;
			}
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
			return tt;
		}
		
	protected final int  mREGEX_PART(boolean _createToken,
		boolean start, boolean number
	) throws RecognitionException, CharStreamException, TokenStreamException {
		int tt = REGEX_END;
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = REGEX_PART;
		int _saveIndex;
		
		boolean testCount = false;
		boolean multiCurly = false;
		
		
		{
		if (((_tokenSet_21.member(LA(1))) && (_tokenSet_17.member(LA(2))) && (true) && (true))&&(start)) {
			mREGEX_START_CHAR(false);
		}
		else if ((_tokenSet_17.member(LA(1))) && (true) && (true) && (true)) {
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		
		}
		{
		_loop722:
		do {
			if ((_tokenSet_22.member(LA(1)))) {
				mREGEX_CHAR(false);
			}
			else if ((LA(1)=='}')) {
				match('}');
			}
			else {
				break _loop722;
			}
			
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			
			char c = LA(1);
			if (c == '{') {
			int i = 2;
			c = LA(i);
			if (c == '{') {
			testCount = true;
			}
			if (!testCount && Character.isDigit(c)) {
			do {
			i++;
			c = LA(i);
			} while (Character.isDigit(c));
			if (c == '}') {
			testCount = true;
			}
			}
			}
			
		}
		{
		switch ( LA(1)) {
		case '/':
		{
			match('/');
			if ( inputState.guessing==0 ) {
				
				if (start) {
				tt = REGEX;
				}
				else if (stringPartType == REGEX_START) {
				tt = REGEX_END;
				}
				else if (number) {
				tt = REGEX;
				}
				stringPartType = 0;
				
			}
			{
			_loop727:
			do {
				if ((LA(1)=='U'||LA(1)=='g'||LA(1)=='i'||LA(1)=='m'||LA(1)=='s') && (true) && (true) && (true)) {
					mREGEGX_ATTRUBTE(false);
				}
				else {
					break _loop727;
				}
				
			} while (true);
			}
			break;
		}
		case '{':
		{
			{
			if (((LA(1)=='{') && (true) && (true) && (true))&&(testCount)) {
				match('{');
				if ( inputState.guessing==0 ) {
					tt = REGEX_NUM_PART;
				}
			}
			else if ((LA(1)=='{') && (true) && (true) && (true)) {
				_saveIndex=text.length();
				match('{');
				text.setLength(_saveIndex);
				if ( inputState.guessing==0 ) {
					
					tt = start ? REGEX_START : REGEX_MIDDLE;
					if (start) {
					stringPartType = REGEX_START;
					}
					pushCurlyLevel();
					
				}
			}
			else {
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			
			}
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		if ( inputState.guessing==0 ) {
			_ttype = tt;
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
		return tt;
	}
	
	protected final void mFILE_CHAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = FILE_CHAR;
		int _saveIndex;
		
		{
		if ((_tokenSet_23.member(LA(1)))) {
			mFILE_START_CHAR(false);
		}
		else if ((LA(1)=='=')) {
			match('=');
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final int  mFILE_PATH_PART(boolean _createToken,
		boolean start
	) throws RecognitionException, CharStreamException, TokenStreamException {
		int tt = FILE_PATH;
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = FILE_PATH_PART;
		int _saveIndex;
		
		{
		if (((_tokenSet_23.member(LA(1))) && (_tokenSet_19.member(LA(2))) && (true) && (true))&&(start)) {
			mFILE_START_CHAR(false);
		}
		else if ((_tokenSet_19.member(LA(1))) && (true) && (true) && (true)) {
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		
		}
		{
		_loop663:
		do {
			if ((_tokenSet_24.member(LA(1))) && (_tokenSet_19.member(LA(2))) && (true) && (true)) {
				mFILE_CHAR(false);
			}
			else {
				break _loop663;
			}
			
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			
			if (start) {
			tt = FILE_PATH;
			}
			else if (stringPartType == FILE_PATH_START) {
			tt = FILE_PATH_END;
			}
			stringPartType = 0;
			
		}
		{
		boolean synPredMatched666 = false;
		if (((LA(1)=='{'))) {
			int _m666 = mark();
			synPredMatched666 = true;
			inputState.guessing++;
			try {
				{
				match('{');
				}
			}
			catch (RecognitionException pe) {
				synPredMatched666 = false;
			}
			rewind(_m666);
inputState.guessing--;
		}
		if ( synPredMatched666 ) {
			_saveIndex=text.length();
			match('{');
			text.setLength(_saveIndex);
			if ( inputState.guessing==0 ) {
				
				tt = start ? FILE_PATH_START : FILE_PATH_MIDDLE;
				if (start) {
				stringPartType = FILE_PATH_START;
				}
				pushCurlyLevel();
				
			}
		}
		else {
			boolean synPredMatched668 = false;
			if (((_tokenSet_24.member(LA(1))))) {
				int _m668 = mark();
				synPredMatched668 = true;
				inputState.guessing++;
				try {
					{
					mFILE_CHAR(false);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched668 = false;
				}
				rewind(_m668);
inputState.guessing--;
			}
			if ( synPredMatched668 ) {
				{
				int _cnt670=0;
				_loop670:
				do {
					if ((_tokenSet_24.member(LA(1)))) {
						mFILE_CHAR(false);
					}
					else {
						if ( _cnt670>=1 ) { break _loop670; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
					}
					
					_cnt670++;
				} while (true);
				}
				if ( inputState.guessing==0 ) {
					
					if (start) {
					tt = FILE_PATH;
					}
					else if (stringPartType == FILE_PATH_START) {
					tt = FILE_PATH_END;
					}
					stringPartType = 0;
					
				}
			}
			else {
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				_ttype = tt;
			}
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
			return tt;
		}
		
	protected final void mFILE_START_CHAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = FILE_START_CHAR;
		int _saveIndex;
		
		{
		switch ( LA(1)) {
		case '0':  case '1':  case '2':  case '3':
		case '4':  case '5':  case '6':  case '7':
		case '8':  case '9':
		{
			mDIGIT(false);
			break;
		}
		case '~':
		{
			match('~');
			break;
		}
		case '!':
		{
			match('!');
			break;
		}
		case '@':
		{
			match('@');
			break;
		}
		case '#':
		{
			match('#');
			break;
		}
		case '$':
		{
			match('$');
			break;
		}
		case '%':
		{
			match('%');
			break;
		}
		case '^':
		{
			match('^');
			break;
		}
		case '&':
		{
			match('&');
			break;
		}
		case '-':
		{
			match('-');
			break;
		}
		case '+':
		{
			match('+');
			break;
		}
		case ',':
		{
			match(',');
			break;
		}
		case '.':
		{
			match('.');
			break;
		}
		case ':':
		{
			match(':');
			break;
		}
		case '/':
		{
			match('/');
			break;
		}
		case '\\':
		{
			match('\\');
			break;
		}
		default:
			if ((_tokenSet_1.member(LA(1)))) {
				mLETTER(false);
			}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mMOD(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = MOD;
		int _saveIndex;
		
		String fileText;
		int tt = MOD;
		
		
		boolean synPredMatched651 = false;
		if (((LA(1)=='%') && (LA(2)=='=') && (true) && (true))) {
			int _m651 = mark();
			synPredMatched651 = true;
			inputState.guessing++;
			try {
				{
				match('%');
				match('=');
				}
			}
			catch (RecognitionException pe) {
				synPredMatched651 = false;
			}
			rewind(_m651);
inputState.guessing--;
		}
		if ( synPredMatched651 ) {
			match('%');
			match('=');
			if ( inputState.guessing==0 ) {
				_ttype = MOD_ASSIGN;
			}
		}
		else {
			boolean synPredMatched655 = false;
			if (((LA(1)=='%') && (_tokenSet_19.member(LA(2))) && (true) && (true))) {
				int _m655 = mark();
				synPredMatched655 = true;
				inputState.guessing++;
				try {
					{
					mFILE_START_CHAR(false);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched655 = false;
				}
				rewind(_m655);
inputState.guessing--;
			}
			if ( synPredMatched655 ) {
				_saveIndex=text.length();
				match('%');
				text.setLength(_saveIndex);
				tt=mFILE_PATH_PART(false,true);
				if ( inputState.guessing==0 ) {
					_ttype = tt;
				}
			}
			else {
				boolean synPredMatched657 = false;
				if (((LA(1)=='%') && (true))) {
					int _m657 = mark();
					synPredMatched657 = true;
					inputState.guessing++;
					try {
						{
						match('%');
						}
					}
					catch (RecognitionException pe) {
						synPredMatched657 = false;
					}
					rewind(_m657);
inputState.guessing--;
				}
				if ( synPredMatched657 ) {
					match('%');
					if ( inputState.guessing==0 ) {
						_ttype = MOD;
					}
				}
				else {
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
				}
				}}
				_ttype = testLiteralsTable(_ttype);
				if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
					_token = makeToken(_ttype);
					_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
				}
				_returnToken = _token;
			}
			
	protected final void mNL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = NL;
		int _saveIndex;
		
		{
		if ((LA(1)=='\r') && (LA(2)=='\n') && (true) && (true)) {
			match("\r\n");
		}
		else if ((LA(1)=='\r') && (true) && (true) && (true)) {
			match('\r');
		}
		else if ((LA(1)=='\n')) {
			match('\n');
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		
		}
		if ( inputState.guessing==0 ) {
			
						newlines++;
						newline();
			
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mNLS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = NLS;
		int _saveIndex;
		
		mNL(false);
		{
		_loop675:
		do {
			switch ( LA(1)) {
			case ' ':
			{
				match(' ');
				break;
			}
			case '\n':  case '\r':
			{
				mNL(false);
				break;
			}
			default:
			{
				break _loop675;
			}
			}
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			
						if (whiteSpaceStartPos == 0) {
							_ttype = Token.SKIP;
						}
						else {
							text.setLength(_begin); text.append("<NEWLINES>");
						}
						allowFilePath = true;
					
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mSTRING_CHAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = STRING_CHAR;
		int _saveIndex;
		
		{
		match(_tokenSet_14);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mESC(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ESC;
		int _saveIndex;
		
		_saveIndex=text.length();
		match('\\');
		text.setLength(_saveIndex);
		{
		switch ( LA(1)) {
		case 'n':
		{
			match('n');
			if ( inputState.guessing==0 ) {
				text.setLength(_begin); text.append("\n");
			}
			break;
		}
		case 'r':
		{
			match('r');
			if ( inputState.guessing==0 ) {
				text.setLength(_begin); text.append("\r");
			}
			break;
		}
		case 't':
		{
			match('t');
			if ( inputState.guessing==0 ) {
				text.setLength(_begin); text.append("\t");
			}
			break;
		}
		case 'b':
		{
			match('b');
			if ( inputState.guessing==0 ) {
				text.setLength(_begin); text.append("\b");
			}
			break;
		}
		case 'f':
		{
			match('f');
			if ( inputState.guessing==0 ) {
				text.setLength(_begin); text.append("\f");
			}
			break;
		}
		case '"':
		{
			match('"');
			break;
		}
		case '\'':
		{
			match('\'');
			break;
		}
		case '\\':
		{
			match('\\');
			break;
		}
		case '$':
		{
			match('$');
			break;
		}
		case '{':
		{
			match('{');
			break;
		}
		case 'u':
		{
			{
			int _cnt681=0;
			_loop681:
			do {
				if ((LA(1)=='u')) {
					match('u');
				}
				else {
					if ( _cnt681>=1 ) { break _loop681; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
				}
				
				_cnt681++;
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				text.setLength(_begin); text.append("");
			}
			mHEX_DIGIT(false);
			mHEX_DIGIT(false);
			mHEX_DIGIT(false);
			mHEX_DIGIT(false);
			if ( inputState.guessing==0 ) {
				char ch = (char)Integer.parseInt(new String(text.getBuffer(),_begin,text.length()-_begin), 16); text.setLength(_begin); text.append(ch);
			}
			break;
		}
		case '0':  case '1':  case '2':  case '3':
		{
			{
			matchRange('0','3');
			}
			{
			if (((LA(1) >= '0' && LA(1) <= '7')) && ((LA(2) >= '\u0000' && LA(2) <= '\ufffe')) && (true) && (true)) {
				{
				matchRange('0','7');
				}
				{
				if (((LA(1) >= '0' && LA(1) <= '7')) && ((LA(2) >= '\u0000' && LA(2) <= '\ufffe')) && (true) && (true)) {
					{
					matchRange('0','7');
					}
				}
				else if (((LA(1) >= '\u0000' && LA(1) <= '\ufffe')) && (true) && (true) && (true)) {
				}
				else {
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
				}
				
				}
			}
			else if (((LA(1) >= '\u0000' && LA(1) <= '\ufffe')) && (true) && (true) && (true)) {
			}
			else {
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			
			}
			if ( inputState.guessing==0 ) {
				char ch = (char)Integer.parseInt(new String(text.getBuffer(),_begin,text.length()-_begin), 8); text.setLength(_begin); text.append(ch);
			}
			break;
		}
		case '4':  case '5':  case '6':  case '7':
		{
			{
			matchRange('4','7');
			}
			{
			if (((LA(1) >= '0' && LA(1) <= '7')) && ((LA(2) >= '\u0000' && LA(2) <= '\ufffe')) && (true) && (true)) {
				{
				matchRange('0','7');
				}
			}
			else if (((LA(1) >= '\u0000' && LA(1) <= '\ufffe')) && (true) && (true) && (true)) {
			}
			else {
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			
			}
			if ( inputState.guessing==0 ) {
				char ch = (char)Integer.parseInt(new String(text.getBuffer(),_begin,text.length()-_begin), 8); text.setLength(_begin); text.append(ch);
			}
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mSINGLE_LINE_COMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SINGLE_LINE_COMMENT;
		int _saveIndex;
		
		match("//");
		{
		_loop695:
		do {
			if ((_tokenSet_17.member(LA(1)))) {
				{
				match(_tokenSet_17);
				}
			}
			else {
				break _loop695;
			}
			
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			_ttype = Token.SKIP;
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mMULTI_COMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = MULTI_COMMENT;
		int _saveIndex;
		
		match("/*");
		{
		_loop704:
		do {
			boolean synPredMatched702 = false;
			if (((LA(1)=='*') && ((LA(2) >= '\u0000' && LA(2) <= '\ufffe')) && ((LA(3) >= '\u0000' && LA(3) <= '\ufffe')))) {
				int _m702 = mark();
				synPredMatched702 = true;
				inputState.guessing++;
				try {
					{
					match('*');
					{
					match(_tokenSet_25);
					}
					}
				}
				catch (RecognitionException pe) {
					synPredMatched702 = false;
				}
				rewind(_m702);
inputState.guessing--;
			}
			if ( synPredMatched702 ) {
				match('*');
			}
			else if ((LA(1)=='\n'||LA(1)=='\r')) {
				mNL(false);
			}
			else if ((_tokenSet_26.member(LA(1)))) {
				{
				match(_tokenSet_26);
				}
			}
			else {
				break _loop704;
			}
			
		} while (true);
		}
		match("*/");
		if ( inputState.guessing==0 ) {
			_ttype = Token.SKIP;
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mREGEX(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = REGEX;
		int _saveIndex;
		
		int tt = REGEX;
		regexPatternAttrubtes = 0;
		
		
		boolean synPredMatched707 = false;
		if (((LA(1)=='/') && (_tokenSet_17.member(LA(2))) && (true) && (true))) {
			int _m707 = mark();
			synPredMatched707 = true;
			inputState.guessing++;
			try {
				{
				match('/');
				mREGEGX_START(false);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched707 = false;
			}
			rewind(_m707);
inputState.guessing--;
		}
		if ( synPredMatched707 ) {
			{
			_saveIndex=text.length();
			match('/');
			text.setLength(_saveIndex);
			tt=mREGEX_PART(false,true, false);
			{
			if (((_tokenSet_17.member(LA(1))))&&(tt == REGEX_NUM_PART)) {
				{
				int _cnt711=0;
				_loop711:
				do {
					if (((_tokenSet_17.member(LA(1))))&&(tt == REGEX_NUM_PART)) {
						tt=mREGEX_PART(false,false, true);
					}
					else {
						if ( _cnt711>=1 ) { break _loop711; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
					}
					
					_cnt711++;
				} while (true);
				}
			}
			else {
			}
			
			}
			if ( inputState.guessing==0 ) {
				_ttype = tt;
			}
			}
		}
		else if ((LA(1)=='/') && (LA(2)=='=') && (true) && (true)) {
			match("/=");
			if ( inputState.guessing==0 ) {
				_ttype = DIV_ASSIGN;
			}
		}
		else if ((LA(1)=='/') && (true)) {
			match('/');
			if ( inputState.guessing==0 ) {
				_ttype = DIV;
			}
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mREGEGX_START(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = REGEGX_START;
		int _saveIndex;
		
		{
		if ((LA(1)=='\\') && (LA(2)=='/') && (_tokenSet_27.member(LA(3))) && (true)) {
			match('\\');
			match('/');
		}
		else if ((_tokenSet_28.member(LA(1))) && (_tokenSet_27.member(LA(2))) && (true) && (true)) {
			{
			match(_tokenSet_28);
			}
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		
		}
		{
		_loop718:
		do {
			if ((LA(1)=='\\') && (LA(2)=='/') && (_tokenSet_27.member(LA(3))) && (true)) {
				match('\\');
				match('/');
			}
			else if ((_tokenSet_29.member(LA(1))) && (_tokenSet_27.member(LA(2))) && (true) && (true)) {
				{
				match(_tokenSet_29);
				}
			}
			else {
				break _loop718;
			}
			
		} while (true);
		}
		match('/');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mREGEGX_ATTRUBTE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = REGEGX_ATTRUBTE;
		int _saveIndex;
		
		switch ( LA(1)) {
		case 'i':
		{
			match('i');
			break;
		}
		case 'g':
		{
			match('g');
			break;
		}
		case 'm':
		{
			match('m');
			break;
		}
		case 's':
		{
			match('s');
			break;
		}
		case 'U':
		{
			match('U');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mREGEX_START_CHAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = REGEX_START_CHAR;
		int _saveIndex;
		
		boolean bNumber = false;
		
		
		if ((LA(1)=='\\') && (LA(2)=='/') && (_tokenSet_17.member(LA(3))) && (true)) {
			match('\\');
			match('/');
			if ( inputState.guessing==0 ) {
				text.setLength(_begin); text.append('/');
			}
		}
		else if (((LA(1)=='\\') && (_tokenSet_17.member(LA(2))) && (true) && (true))&&(  LA(2) != '/' && LA(2) != '\n' && LA(2) != '\r'  )) {
			match('\\');
		}
		else if ((_tokenSet_30.member(LA(1)))) {
			{
			match(_tokenSet_30);
			}
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mREGEX_CHAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = REGEX_CHAR;
		int _saveIndex;
		
		if ((_tokenSet_21.member(LA(1)))) {
			mREGEX_START_CHAR(false);
		}
		else if ((LA(1)=='*')) {
			match('*');
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mSTRING(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = STRING;
		int _saveIndex;
		int tt = 0;
		
		boolean synPredMatched739 = false;
		if (((LA(1)=='\'') && (LA(2)=='\'') && (LA(3)=='\'') && ((LA(4) >= '\u0000' && LA(4) <= '\ufffe')))) {
			int _m739 = mark();
			synPredMatched739 = true;
			inputState.guessing++;
			try {
				{
				match("'''");
				}
			}
			catch (RecognitionException pe) {
				synPredMatched739 = false;
			}
			rewind(_m739);
inputState.guessing--;
		}
		if ( synPredMatched739 ) {
			{
			_saveIndex=text.length();
			match("'''");
			text.setLength(_saveIndex);
			{
			_loop745:
			do {
				switch ( LA(1)) {
				case '\\':
				{
					mESC(false);
					break;
				}
				case '"':
				{
					match('"');
					break;
				}
				case '{':
				{
					match('{');
					break;
				}
				case '\n':
				{
					match('\n');
					break;
				}
				case '\r':
				{
					match('\r');
					break;
				}
				default:
					if ((_tokenSet_14.member(LA(1))) && ((LA(2) >= '\u0000' && LA(2) <= '\ufffe')) && ((LA(3) >= '\u0000' && LA(3) <= '\ufffe')) && ((LA(4) >= '\u0000' && LA(4) <= '\ufffe'))) {
						mSTRING_CHAR(false);
					}
					else if ((LA(1)=='}') && ((LA(2) >= '\u0000' && LA(2) <= '\ufffe')) && ((LA(3) >= '\u0000' && LA(3) <= '\ufffe')) && ((LA(4) >= '\u0000' && LA(4) <= '\ufffe'))) {
						match('}');
					}
					else {
						boolean synPredMatched744 = false;
						if (((LA(1)=='\'') && ((LA(2) >= '\u0000' && LA(2) <= '\ufffe')) && ((LA(3) >= '\u0000' && LA(3) <= '\ufffe')) && ((LA(4) >= '\u0000' && LA(4) <= '\ufffe')))) {
							int _m744 = mark();
							synPredMatched744 = true;
							inputState.guessing++;
							try {
								{
								match('\'');
								{
								if ((_tokenSet_13.member(LA(1)))) {
									matchNot('\'');
								}
								else if ((LA(1)=='\'')) {
									match('\'');
									matchNot('\'');
								}
								else {
									throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
								}
								
								}
								}
							}
							catch (RecognitionException pe) {
								synPredMatched744 = false;
							}
							rewind(_m744);
inputState.guessing--;
						}
						if ( synPredMatched744 ) {
							match('\'');
						}
					else {
						break _loop745;
					}
					}}
				} while (true);
				}
				_saveIndex=text.length();
				match("'''");
				text.setLength(_saveIndex);
				}
			}
			else {
				boolean synPredMatched747 = false;
				if (((LA(1)=='"') && (LA(2)=='"') && (LA(3)=='"') && (_tokenSet_18.member(LA(4))))) {
					int _m747 = mark();
					synPredMatched747 = true;
					inputState.guessing++;
					try {
						{
						match("\"\"\"");
						}
					}
					catch (RecognitionException pe) {
						synPredMatched747 = false;
					}
					rewind(_m747);
inputState.guessing--;
				}
				if ( synPredMatched747 ) {
					{
					_saveIndex=text.length();
					match("\"\"\"");
					text.setLength(_saveIndex);
					tt=mSTRING_PART(false,true, true);
					if ( inputState.guessing==0 ) {
						_ttype = tt;
					}
					}
				}
				else if ((LA(1)=='\'') && ((LA(2) >= '\u0000' && LA(2) <= '\ufffe')) && (true) && (true)) {
					_saveIndex=text.length();
					match('\'');
					text.setLength(_saveIndex);
					{
					_loop750:
					do {
						switch ( LA(1)) {
						case '\\':
						{
							mESC(false);
							break;
						}
						case '"':
						{
							match('"');
							break;
						}
						case '{':
						{
							match('{');
							break;
						}
						case '\n':
						{
							_saveIndex=text.length();
							match('\n');
							text.setLength(_saveIndex);
							break;
						}
						case '\r':
						{
							_saveIndex=text.length();
							match('\r');
							text.setLength(_saveIndex);
							break;
						}
						default:
							if ((_tokenSet_14.member(LA(1))) && ((LA(2) >= '\u0000' && LA(2) <= '\ufffe')) && (true) && (true)) {
								mSTRING_CHAR(false);
							}
							else if ((LA(1)=='}') && ((LA(2) >= '\u0000' && LA(2) <= '\ufffe')) && (true) && (true)) {
								match('}');
							}
						else {
							break _loop750;
						}
						}
					} while (true);
					}
					_saveIndex=text.length();
					match('\'');
					text.setLength(_saveIndex);
				}
				else if ((LA(1)=='"') && (_tokenSet_18.member(LA(2))) && (true) && (true)) {
					_saveIndex=text.length();
					match('"');
					text.setLength(_saveIndex);
					tt=mSTRING_PART(false,true, false);
					if ( inputState.guessing==0 ) {
						_ttype = tt;
					}
				}
				else {
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
				}
				}
				if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
					_token = makeToken(_ttype);
					_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
				}
				_returnToken = _token;
			}
			
	protected final void mREGEXP_CHAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = REGEXP_CHAR;
		int _saveIndex;
		
		{
		if ((LA(1)=='\\') && (LA(2)=='/')) {
			match('\\');
			match('/');
			if ( inputState.guessing==0 ) {
				text.setLength(_begin); text.append('/');
			}
		}
		else if ((LA(1)=='\\') && (LA(2)=='\n'||LA(2)=='\r')) {
			_saveIndex=text.length();
			match('\\');
			text.setLength(_saveIndex);
			_saveIndex=text.length();
			mNL(false);
			text.setLength(_saveIndex);
		}
		else if ((_tokenSet_31.member(LA(1)))) {
			{
			match(_tokenSet_31);
			}
		}
		else if (((LA(1)=='\\') && (true))&&( LA(2)!='/' && LA(2)!='\n' && LA(2)!='\r' )) {
			match('\\');
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		
		}
		{
		_loop766:
		do {
			if ((LA(1)=='*')) {
				match('*');
			}
			else {
				break _loop766;
			}
			
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mWS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = WS;
		int _saveIndex;
		
		int spaces = 0;
		int lines = 0;
		
		
		boolean synPredMatched769 = false;
		if (((LA(1)=='\t'||LA(1)=='\u000c'||LA(1)==' ') && (true) && (true) && (true))) {
			int _m769 = mark();
			synPredMatched769 = true;
			inputState.guessing++;
			try {
				{
				if (!(whiteSpaceStartPos < 0))
				  throw new SemanticException("whiteSpaceStartPos < 0");
				}
			}
			catch (RecognitionException pe) {
				synPredMatched769 = false;
			}
			rewind(_m769);
inputState.guessing--;
		}
		if ( synPredMatched769 ) {
			{
			int _cnt771=0;
			_loop771:
			do {
				switch ( LA(1)) {
				case ' ':
				{
					match(' ');
					break;
				}
				case '\t':
				{
					match('\t');
					break;
				}
				case '\u000c':
				{
					match('\u000C');
					break;
				}
				default:
				{
					if ( _cnt771>=1 ) { break _loop771; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
				}
				}
				_cnt771++;
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				_ttype = Token.SKIP;
			}
		}
		else {
			boolean synPredMatched773 = false;
			if (((LA(1)=='\t'||LA(1)==' ') && (true) && (true) && (true))) {
				int _m773 = mark();
				synPredMatched773 = true;
				inputState.guessing++;
				try {
					{
					if (!(whiteSpaceStartPos == 0))
					  throw new SemanticException("whiteSpaceStartPos == 0");
					}
				}
				catch (RecognitionException pe) {
					synPredMatched773 = false;
				}
				rewind(_m773);
inputState.guessing--;
			}
			if ( synPredMatched773 ) {
				{
				int _cnt775=0;
				_loop775:
				do {
					switch ( LA(1)) {
					case ' ':
					{
						match(' ');
						if ( inputState.guessing==0 ) {
							spaces++;
						}
						break;
					}
					case '\t':
					{
						match('\t');
						if ( inputState.guessing==0 ) {
							spaces += 8; spaces -= (spaces % 8);
						}
						break;
					}
					default:
					{
						if ( _cnt775>=1 ) { break _loop775; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
					}
					}
					_cnt775++;
				} while (true);
				}
				{
				_loop778:
				do {
					if ((LA(1)=='\n'||LA(1)=='\r')) {
						{
						switch ( LA(1)) {
						case '\r':
						{
							match('\r');
							break;
						}
						case '\n':
						{
							break;
						}
						default:
						{
							throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
						}
						}
						}
						match('\n');
						if ( inputState.guessing==0 ) {
							lines++;
						}
					}
					else {
						break _loop778;
					}
					
				} while (true);
				}
				if ( inputState.guessing==0 ) {
					
								if (LA(1) != -1 || lines == 0) {
									char[] indentation = new char[spaces];
					for (int i = 0; i < spaces; i++) {
						indentation[i] = ' ';
					}
					String str = new String(indentation);
					text.setLength(_begin); text.append(str);
								}
							
				}
			}
			else {
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			}
			if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
				_token = makeToken(_ttype);
				_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
			}
			_returnToken = _token;
		}
		
		
		private static final long[] mk_tokenSet_0() {
			long[] data = new long[2560];
			data[0]=68719476736L;
			data[1]=576460745995190270L;
			data[3]=-36028797027352577L;
			for (int i = 4; i<=1022; i++) { data[i]=-1L; }
			data[1023]=9223372036854775807L;
			return data;
		}
		public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
		private static final long[] mk_tokenSet_1() {
			long[] data = new long[2560];
			data[1]=576460745995190270L;
			data[3]=-36028797027352577L;
			for (int i = 4; i<=1022; i++) { data[i]=-1L; }
			data[1023]=9223372036854775807L;
			return data;
		}
		public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
		private static final long[] mk_tokenSet_2() {
			long[] data = new long[2560];
			data[0]=287948901175001088L;
			data[1]=1152921498298613758L;
			data[3]=-36028797027352577L;
			for (int i = 4; i<=1022; i++) { data[i]=-1L; }
			data[1023]=9223372036854775807L;
			return data;
		}
		public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
		private static final long[] mk_tokenSet_3() {
			long[] data = new long[2560];
			data[0]=-4611686018427387905L;
			for (int i = 1; i<=1023; i++) { data[i]=-1L; }
			return data;
		}
		public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
		private static final long[] mk_tokenSet_4() {
			long[] data = new long[2560];
			data[0]=1440870405781848064L;
			data[1]=1152921498298613758L;
			data[3]=-36028797027352577L;
			for (int i = 4; i<=1022; i++) { data[i]=-1L; }
			data[1023]=9223372036854775807L;
			return data;
		}
		public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
		private static final long[] mk_tokenSet_5() {
			long[] data = new long[2560];
			data[0]=4899775661385721344L;
			data[1]=1152921498298613758L;
			data[3]=-36028797027352577L;
			for (int i = 4; i<=1022; i++) { data[i]=-1L; }
			data[1023]=9223372036854775807L;
			return data;
		}
		public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
		private static final long[] mk_tokenSet_6() {
			long[] data = new long[2560];
			data[0]=1440870410076825088L;
			data[1]=1152921498298613758L;
			data[3]=-36028797027352577L;
			for (int i = 4; i<=1022; i++) { data[i]=-1L; }
			data[1023]=9223372036854775807L;
			return data;
		}
		public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
		private static final long[] mk_tokenSet_7() {
			long[] data = new long[2560];
			data[0]=6052697165992568320L;
			data[1]=1152921498298613758L;
			data[3]=-36028797027352577L;
			for (int i = 4; i<=1022; i++) { data[i]=-1L; }
			data[1023]=9223372036854775807L;
			return data;
		}
		public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
		private static final long[] mk_tokenSet_8() {
			long[] data = new long[2048];
			data[0]=-5764607523034234881L;
			for (int i = 1; i<=1023; i++) { data[i]=-1L; }
			return data;
		}
		public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
		private static final long[] mk_tokenSet_9() {
			long[] data = new long[2560];
			data[0]=1441011147565180416L;
			data[1]=1152921498298613758L;
			data[3]=-36028797027352577L;
			for (int i = 4; i<=1022; i++) { data[i]=-1L; }
			data[1023]=9223372036854775807L;
			return data;
		}
		public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
		private static final long[] mk_tokenSet_10() {
			long[] data = new long[2560];
			data[0]=287948901175001088L;
			data[1]=576460745995190270L;
			data[3]=-36028797027352577L;
			for (int i = 4; i<=1022; i++) { data[i]=-1L; }
			data[1023]=9223372036854775807L;
			return data;
		}
		public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
		private static final long[] mk_tokenSet_11() {
			long[] data = new long[2560];
			data[0]=7205618670599415296L;
			data[1]=1152921498298613758L;
			data[3]=-36028797027352577L;
			for (int i = 4; i<=1022; i++) { data[i]=-1L; }
			data[1023]=9223372036854775807L;
			return data;
		}
		public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
		private static final long[] mk_tokenSet_12() {
			long[] data = new long[2048];
			data[0]=-17179869185L;
			for (int i = 1; i<=1022; i++) { data[i]=-1L; }
			data[1023]=9223372036854775807L;
			return data;
		}
		public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
		private static final long[] mk_tokenSet_13() {
			long[] data = new long[2048];
			data[0]=-549755813889L;
			for (int i = 1; i<=1023; i++) { data[i]=-1L; }
			return data;
		}
		public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
		private static final long[] mk_tokenSet_14() {
			long[] data = new long[2048];
			data[0]=-566935692289L;
			data[1]=-576460752571858945L;
			for (int i = 2; i<=1022; i++) { data[i]=-1L; }
			data[1023]=9223372036854775807L;
			return data;
		}
		public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
		private static final long[] mk_tokenSet_15() {
			long[] data = new long[1025];
			data[0]=287992881640112128L;
			data[1]=576460745995190270L;
			return data;
		}
		public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
		private static final long[] mk_tokenSet_16() {
			long[] data = new long[2560];
			data[0]=-576464626363924480L;
			data[1]=6341068270371602431L;
			data[3]=-36028797027352577L;
			for (int i = 4; i<=1022; i++) { data[i]=-1L; }
			data[1023]=9223372036854775807L;
			return data;
		}
		public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
		private static final long[] mk_tokenSet_17() {
			long[] data = new long[2048];
			data[0]=-9217L;
			for (int i = 1; i<=1022; i++) { data[i]=-1L; }
			data[1023]=9223372036854775807L;
			return data;
		}
		public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
		private static final long[] mk_tokenSet_18() {
			long[] data = new long[2048];
			data[0]=-549755813889L;
			for (int i = 1; i<=1022; i++) { data[i]=-1L; }
			data[1023]=9223372036854775807L;
			return data;
		}
		public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
		private static final long[] mk_tokenSet_19() {
			long[] data = new long[2560];
			data[0]=2882295489410105344L;
			data[1]=5764607518068178943L;
			data[3]=-36028797027352577L;
			for (int i = 4; i<=1022; i++) { data[i]=-1L; }
			data[1023]=9223372036854775807L;
			return data;
		}
		public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
		private static final long[] mk_tokenSet_20() {
			long[] data = new long[2048];
			data[0]=-17179869185L;
			for (int i = 1; i<=1023; i++) { data[i]=-1L; }
			return data;
		}
		public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
		private static final long[] mk_tokenSet_21() {
			long[] data = new long[2048];
			data[0]=-145135534875649L;
			data[1]=-2882303761517117441L;
			for (int i = 2; i<=1022; i++) { data[i]=-1L; }
			data[1023]=9223372036854775807L;
			return data;
		}
		public static final BitSet _tokenSet_21 = new BitSet(mk_tokenSet_21());
		private static final long[] mk_tokenSet_22() {
			long[] data = new long[2048];
			data[0]=-140737488364545L;
			data[1]=-2882303761517117441L;
			for (int i = 2; i<=1022; i++) { data[i]=-1L; }
			data[1023]=9223372036854775807L;
			return data;
		}
		public static final BitSet _tokenSet_22 = new BitSet(mk_tokenSet_22());
		private static final long[] mk_tokenSet_23() {
			long[] data = new long[2560];
			data[0]=576452480196411392L;
			data[1]=5188146765764755455L;
			data[3]=-36028797027352577L;
			for (int i = 4; i<=1022; i++) { data[i]=-1L; }
			data[1023]=9223372036854775807L;
			return data;
		}
		public static final BitSet _tokenSet_23 = new BitSet(mk_tokenSet_23());
		private static final long[] mk_tokenSet_24() {
			long[] data = new long[2560];
			data[0]=2882295489410105344L;
			data[1]=5188146765764755455L;
			data[3]=-36028797027352577L;
			for (int i = 4; i<=1022; i++) { data[i]=-1L; }
			data[1023]=9223372036854775807L;
			return data;
		}
		public static final BitSet _tokenSet_24 = new BitSet(mk_tokenSet_24());
		private static final long[] mk_tokenSet_25() {
			long[] data = new long[2048];
			data[0]=-140737488355329L;
			for (int i = 1; i<=1023; i++) { data[i]=-1L; }
			return data;
		}
		public static final BitSet _tokenSet_25 = new BitSet(mk_tokenSet_25());
		private static final long[] mk_tokenSet_26() {
			long[] data = new long[2048];
			data[0]=-4398046520321L;
			for (int i = 1; i<=1022; i++) { data[i]=-1L; }
			data[1023]=9223372036854775807L;
			return data;
		}
		public static final BitSet _tokenSet_26 = new BitSet(mk_tokenSet_26());
		private static final long[] mk_tokenSet_27() {
			long[] data = new long[2048];
			data[0]=-9217L;
			for (int i = 1; i<=1023; i++) { data[i]=-1L; }
			return data;
		}
		public static final BitSet _tokenSet_27 = new BitSet(mk_tokenSet_27());
		private static final long[] mk_tokenSet_28() {
			long[] data = new long[2048];
			data[0]=-145135534875649L;
			for (int i = 1; i<=1023; i++) { data[i]=-1L; }
			return data;
		}
		public static final BitSet _tokenSet_28 = new BitSet(mk_tokenSet_28());
		private static final long[] mk_tokenSet_29() {
			long[] data = new long[2048];
			data[0]=-140737488364545L;
			for (int i = 1; i<=1023; i++) { data[i]=-1L; }
			return data;
		}
		public static final BitSet _tokenSet_29 = new BitSet(mk_tokenSet_29());
		private static final long[] mk_tokenSet_30() {
			long[] data = new long[2048];
			data[0]=-145135534875649L;
			data[1]=-2882303761785552897L;
			for (int i = 2; i<=1022; i++) { data[i]=-1L; }
			data[1023]=9223372036854775807L;
			return data;
		}
		public static final BitSet _tokenSet_30 = new BitSet(mk_tokenSet_30());
		private static final long[] mk_tokenSet_31() {
			long[] data = new long[2048];
			data[0]=-145204254352385L;
			data[1]=-268435457L;
			for (int i = 2; i<=1022; i++) { data[i]=-1L; }
			data[1023]=9223372036854775807L;
			return data;
		}
		public static final BitSet _tokenSet_31 = new BitSet(mk_tokenSet_31());
		
		}
