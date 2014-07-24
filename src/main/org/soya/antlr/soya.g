header {
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
}

/**
 * Parser of Soya
 * @author: James Gong
 */
class SoyaParser extends Parser;

options {
    k = 2;
    buildAST = true;
    defaultErrorHandler = false;
}

tokens {
INDENT; DEDENT; INTEGER;
ID; ID_LPAREN; URL; STRING_PART; STR_TQ_START; STR_SQ_START; STR_PART_MIDDLE; STR_PART_END;
REGEX_NUM_PART; REGEX_START; REGEX_END; REGEX_MIDDLE; REGEX_CONSTRUCTIOR;
FILE_PATH; FILE_PATH_START; FILE_PATH_MIDDLE; FILE_PATH_END; FILE_CONSTRUCTOR;
LPAREN; RPAREN; LBRACK; RBRACK; STRING_CONSTRUCTOR;  LCURLY; RCURLY; EMAIL; TIME; LIST_PREFIX;
PERCENTAGE; NLS; FLOAT; PAIR; DATE; PAIR_LCURLY; WELL_LCURLY; RCURLY_X_PAIR; RCURLY_X_PAIR_LCURLY;
RCURLY_X_LCURLY; SINGLE_LINE_COMMENT; ASSIGN; PLUS; MINUS; EQUAL; NOT; NOT_EQUAL; MOD; COLON; COMMAR; SEMI;
DOT; DB_DOT; QUESTION; TILDE; GT; LT; GE; LE; SR; SL; INC; DEC; DB_STAR; DB_COLON; STAR_DOT; OPTIONAL_DOT;
XML_START; XML_END; PLUS_ASSIGN; MINUS_ASSIGN; STAR_ASSIGN; DIV_ASSIGN; MOD_ASSIGN; DB_STAR_ASSIGN; RFC822;
U_DB_DOT; U_DB_DOT_LT; U_GT_DB_DOT;U_GT_DB_DOT_LT; DIV; DIV_ASSIGN; REGEX_MATCH; REGEX_NOT_MATCH;

K_NULL = "null"; K_NEW = "new"; K_TRUE = "true"; K_FALSE = "false"; IN = "in"; XOR = "xor";
INSTANCEOF = "instanceof"; K_STEP = "step"; K_IF = "if"; K_ELSE = "else"; K_FOR = "for";
WHILE = "while"; DO = "do"; DEF = "def"; K_CLASS = "class"; K_EXTENDS = "extends";
K_TRY = "try"; K_CATCH = "catch"; K_IMPORT = "import"; K_ENVIRONMENT = "environment";
K_PATTERN = "pattern"; K_PACKAGE = "package"; K_AS = "as"; K_RETURN = "return"; K_BREAK = "break";
K_MATCH = "match"; K_ASSERT = "assert"; K_THROW = "throw"; THIS = "this"; SUPER = "super";
IS = "is"; NOT = "not"; KAND = "and"; KOR = "or"; K_INIT = "init";

DATE_SLASH_LCURLY; DATE_BSLASH_LCURLY; DATE_SUB_LCURLY;
DATE_DOT_LCURLY; DATE_SLASH_DATE_SLASH_LCURLY; REFRENCE_NAME;
DATE_BSLASH_DATE_BSLASH_LCURLY; DATE_SUB_DATE_SUB_LCURLY; DATE_DOT_DATE_DOT_LCURLY;
RCURLY_SLASH_LCURLY; RCURLY_SLASH_DATE; RCURLY_SLASH_DATE_SLASH; RCURLY_SLASH_DATE_SLASH_LCURLY;
RCURLY_BSLASH_LCURLY; RCURLY_BSLASH_DATE; RCURLY_BSLASH_DATE_BSLASH; RCURLY_BSLASH_DATE_BSLASH_LCURLY;
RCURLY_SUB_LCURLY; RCURLY_SUB_DATE; RCURLY_SUB_DATE_SUB; RCURLY_SUB_DATE_SUB_LCURLY;
RCURLY_DOT_LCURLY; RCURLY_DOT_DATE; RCURLY_DOT_DATE_DOT; RCURLY_DOT_DATE_DOT_LCURLY;
STATEMENTS; HASH; HASH_ENTRY; DATE_TIME; U_MINUS; U_PLUS; PAIR_CONSTRUCTOR; LIST; ARG_LIST;
METHOD_CALL; INDEX_OP; MATCH_ATTR; MATCH_VAR_DEF; BLOCK; CALL_BLOCK; POSTFIX; POST_INC; POST_DEC;
ASSERT; IF; FOR; PARAM; PARAM_LIST; METHOD_DEF; CONSTRUCTOR_DEF; CLOSURE; ALIAS; THROW;
NEW; MATCH; MATCH_ITEM; MATCH_ELSE_ITEM; MATCH_BLOCK; EXTENDS_TYPE; TRY; CATCH; FIELD;
CATCH_LIST; NAMED_ARG; ANNOTATION_FIELD; TYPE; CLASS_STATEMENTS; CLASS_BLOCK; CLASS; MODIFIERS;
IMPORT; ANNOTATION; ANNOTATION_LIST; COMPILATION_UNIT; START_TAG; TAG_ATTR; TAG_ATTR_LIST; END_TAG;
TAG_CONTENT; TAG; TAG_TEXT; MARKUP; PACKAGE; SUPER_COTR_CALL;
}

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

}


compilationUnit
{
    Token first = LT(1);
}
	:	nls! (UNIX_HEADER! nls!)?
	    (pkg:packageStatement sep!)?
        sts:statements! (sep!)?
		EOF!
        {
            #compilationUnit = #(node(COMPILATION_UNIT, "COMPILATION_UNIT", first), pkg, sts);
        }
	;

statements
{
   Token first = LT(1);
}
	:   statement
		(
			sep! (statement)?
    	)*
		{#statements = #(node(STATEMENTS, "STATEMENTS", first), #statements);}
	;

statement
    :   (methodStart) => (
            methodDeclarationStatement
        )
    |   (annotationList nls K_CLASS) => (
            classDeclarationStatement
        )
	|	methodDeclarationStatement
	|   constructorCallStatement
	//|   ifStatement
    |   forStatement
    |   breakStatement
    |   assertStatement
    |   throwStatement
    |   returnStatement
    |   tryStatement
    |   hashMapConstructExpression
    |   yamlHashStatement
    |   starListGroup
    |   matchStatement
    |   matchBlock
    |   importStatement
    |   classDeclarationStatement
    |   expressionStatement
	;


packageStatement
{
    Token first = LT(1);
}
    :   K_PACKAGE! name:idChain
        {
             #packageStatement = #(node(PACKAGE, "PACKAGE", first), #name);
        }
    ;


importStatement
{
    Token first = LT(1);
}
    :  K_IMPORT! name:idChainStar
       {
           #importStatement = #(node(IMPORT, "IMPORT", first), #name);
       }
    ;

returnStatement
	:   K_RETURN^ (expression)?
	;

breakStatement
	:   K_BREAK^ (expression)?
	;

assertStatement!
{
    Token first = LT(1);
}
	:   K_ASSERT! expr1:assignmentValueExpression
        (   options {greedy=true;} :
            (   COMMAR!
            |   COLON!
            )
            expr2:expression
        )?
        {
	        #assertStatement = #(node(ASSERT, "ASSERT", first, LT(1)), #expr1, #expr2);
	    }
	;


throwStatement
{
    Token first = LT(1);
}
    :   K_THROW! nls! expr:expression
        {
            #throwStatement = #(node(THROW, "throw", first, LT(1)), #expr);
        }
    ;


yamlHashStatement
{
    Token first = LT(1);
}
	: 	h0:hashMapEntry
		(
			options { greedy = true; } :
			((NLS | COMMAR nls) ID (COLON | EQ_GT)) =>
			(
				(NLS! | COMMAR! nls!)
	        	h1:hashMapEntry
				{
					//System.out.println("YAML match entry: " + #h1.toStringTree());
				}
			)
		)*
		{
			#yamlHashStatement = #(node(HASH, "HASH", first, LT(1)), #yamlHashStatement);
		}
	;

matchStatement
	:
    	(
            K_MATCH^
            (
                LPAREN!
                cd1:expression
                RPAREN!
            |
                cd2:expression
            )
            nls!
        )?
        (
            (LCURLY) =>
            (
                LCURLY! nls!
                block:matchBlock
                nls!
                RCURLY!
            )
        |
            (
                matchBlock
            )
        )
	;

matchBlock
{
    AST elseItem = null;
    Token first = LT(1);
}
	:
	    head:matchItem
	  	(
	  	    (NLS MOR) =>
	  	    (
	  	        NLS!
	  		    rest:matchItem
	  		)
	  	)*
	  	{
            #matchBlock = #(node(MATCH_BLOCK, "MATCH_BLOCK", first, LT(1)), #matchBlock);
	  	}
	;

matchItem!
{
    Token first = LT(1);
    AST blk = null;
}
	:   MOR! exp:expression
	    (
	        (RARROW nls LCURLY) =>
	        (
                RARROW! nls! blk1:curlyBlockStatement { blk = #blk1; }
            )
        |
            blk2:closureBlockStatement { blk = #blk2; }
	    )?
	    {
            #matchItem = #(node(MATCH_ITEM, "MATCH_ITEM", first, LT(1)), #exp, blk);
	    }
	;


constructorCallStatement!
{
   Token first = LT(1);
}
    :   SUPER! LPAREN! nls! (al:argList!)? nls! RPAREN!
        {
            #constructorCallStatement = #(node(SUPER_COTR_CALL, "SUPER_COTR_CALL", first, LT(1)), al);
        }
    ;

classDeclarationStatement!
{
    AST body = null;
    Token first = null;
    AST prefix = null;
}
    :   anns:annotationList nls!
        {
            first = LT(1);
        }
        (
           ptn:K_PATTERN
           {
               prefix = #ptn;
           }
        )?
        K_CLASS! nls! clsName:ID
        (
            (nls K_EXTENDS) =>
            nls! K_EXTENDS nls! extType:type
            {
				#extType = #(node(EXTENDS_TYPE, "EXTENDS_TYPE", first, LT(1)), #extType);
            }
        )?
        (
            (nls MOR) =>
            nls!
            mblk:matchBlock
        )?
        (
            (NLS ~(K_CLASS)) =>
            (
                NLS!
                clss:classStatements
            )
        )?
        {
            #classDeclarationStatement = #(node(CLASS, "CLASS", first, LT(1)), anns, prefix, clsName, extType, #mblk, #clss);
        }
    ;

fieldDeclarationStatement!
{
    Token first = null;
}
    :   anns:annotationList nls!
        {
            first = LT(1);
        }
        mds:modifiers nls!
        id:ID INIT_ASSIGN! nls!
        right:assignmentRight
        {
            #fieldDeclarationStatement = #(node(FIELD, "FIELD", first, LT(1)), anns, mds, id, right);
        }
    ;


classStatements
{
    Token first = LT(1);
}
    :   classStatement
        (
            (NLS ~(K_CLASS)) =>
            (
                NLS! classStatement
            )
        )*
        {
            #classStatements = #(node(CLASS_STATEMENTS, "CLASS_STATEMENTS", first, LT(1)), #classStatements);
        }
    ;

classStatement
    :   (fieldStart) => fieldDeclarationStatement
    |   (methodStart) => methodDeclarationStatement
    |   (constructStart) => constructDeclationStatement
    |   hashMapEntry
    ;

fieldStart
    :   (annotation)* nls (modifier)* nls ID INIT_ASSIGN
    ;


methodStart
    :   (annotation)* nls (modifier)* nls DEF
    ;

constructStart
    :   (annotation)* nls (modifier)* nls K_INIT
    ;

annotationList
{
    Token first = LT(1);
}
    :   (
            annotation
            (
               nls! annotation
            )*
        )?
        {
           #annotationList = #(node(ANNOTATION_LIST, "ANNOTATION_LIST", first, LT(1)), #annotationList);
        }
    ;


annotation
{
    Token first = LT(1);
}
    :   AT! id:ID! (LPAREN! (args:argList!)? RPAREN!)?
        {
            #annotation = #(node(ANNOTATION, "ANNOTATION", first, LT(1)), id, args);
        }
    ;


definitionHeader!
{
    AST ty = null;
    Token first = LT(1);
}
    :   (   DEF!
        |   t:type!
            {
                #definitionHeader = #(node(TYPE, "TYPE", first, LT(1)), t);
            }
        )
    ;
    
    
constructDeclationStatement!
{
    AST paramList = null;
    Token first = LT(1);
}
    :   K_INIT!
        (
            LPAREN! nls!
            (
                paramList1:parameterDefinitionList! nls!
            )?
            RPAREN!
            {
                paramList = #paramList1;
            }
        |
            paramList2:parameterDefinitionList! nls!
            {
                paramList = #paramList2;
            }
        )?
        body:openBlockStatement!
        {
            #constructDeclationStatement = #(node(CONSTRUCTOR_DEF, "CONSTRUCTOR_DEF", first, LT(1)), paramList, body);
        }
    ;
        

methodDeclarationStatement!
{
    AST ty = null;
    AST paramList = null;
    Token first = null;
}
    :
        anns:annotationList nls!
        {
            first = LT(1);
        }
        mds:modifiers nls!
        DEF! nls!
        mthName:ID! nls!
        (
            LPAREN! nls!
            (
                paramList1:parameterDefinitionList! nls!
            )?
            RPAREN!
            {
                paramList = #paramList1;
            }
        |
            paramList2:parameterDefinitionList! nls!
            {
                paramList = #paramList2;
            }
        )?
        body:methodBlockStatement!
        {
            #methodDeclarationStatement = #(node(METHOD_DEF, "METHOD_DEF", first, LT(1)),
                 anns, mds, ty, mthName, paramList, body);
        }
    ;


modifiers
{
    Token first = LT(1);
}
    :   (
            modifier
            (
                 options { greedy=true; }:
                 modifier
            )*
        )?
        {
            #modifiers = #(node(MODIFIERS, "MODIFIERS", first, LT(1)), #modifiers);
        }
    ;


modifier
    :   "public"
    |   "private"
    |   "protected"
    |   "static"
    |   "abstract"
    |   "final"
    ;


type
    :  typeName
    ;

typeName
{
    Token first = LT(1);
}
    :  id1:ID!
       (
           options{ greedy=true; }:
           d:DOT! nls! id2:ID!
           { #id1 = #(node(DOT, ".", first), #id1, #id2); }
       )*
       {
           #typeName = #id1;
       }
    ;

parameterDefinitionList
{
    Token first = LT(1);
}
    :   parameterDefinition
        (
            COMMAR! nls!
            parameterDefinition
        )*
        {
            #parameterDefinitionList = #(node(PARAM_LIST, "PARAM_LIST", first, LT(1)), #parameterDefinitionList);
        }
    ;

parameterDefinition
{
    AST param = null;
    Token first = LT(1);
}
    :
        (   id:ID
            {
                param = #id;
            }
        |   lit:literal
            {
                param = #lit;
            }
        )
        {
            #parameterDefinition = #(node(PARAM, "PARAM", first, LT(1)), param);
        }
    ;

// if-else statement
ifStatement
{
    AST elseBody = null;
    AST condition = null;
    Token first = LT(1);
}
    :   K_IF! nls!
        (   (LPAREN) =>
            LPAREN!
            condition1:expression!
            {
                condition = #condition1;
            }
            RPAREN!
/*
        |
            condition2:expression!
            {
                condition = #condition2;
            }
            */
        )
        nls! body:openBlockStatement!
        (
            (nls K_ELSE) =>
            nls! K_ELSE! nls!
            (
                elifbody:ifStatement
                {
                    elseBody = #elifbody;
                }
            |   ebody:openBlockStatement!
                {
                    elseBody = #ebody;
                }
            )
        )?
        {
            #ifStatement = #(node(IF, "IF", first, LT(1)), condition, body, elseBody);
        }
    ;


forStatement
{
    AST sts1 = null;
    AST sts2 = null;
    AST sts3 = null;
    Token first = LT(1);
}
    :   K_FOR!
        (
            (
                LPAREN! nls!
                (ps1:statement!)? nls!
                (
                    (
                         SEMI! nls! (ps2:statement!)? nls!
                         (SEMI! nls! (ps3:statement!)? nls!)?
                    )?
                )?
                RPAREN!
                {
                    sts1 = #ps1;
                    sts2 = #ps2;
                    sts3 = #ps3;
                }
            )?
        |
            (
                (s1:statement!)? nls!
                (
                    (
                         SEMI! nls! (s2:statement!)? nls!
                         (SEMI! nls! (s3:statement!)? nls!)?
                    )?
                )?
                {
                    sts1 = #s1;
                    sts2 = #s2;
                    sts3 = #s3;
                }
            )?
        ) nls!
        body:openBlockStatement!
        {
            #forStatement = #(node(FOR, "FOR", first, LT(1)), sts1, sts2, sts3, body);
        }
    ;


tryStatement!
{
    Token first = LT(1);
}
    :   K_TRY nls!
        body:openBlockStatement nls!
        catchList:catchListStatement
        {
            #tryStatement = #(node(TRY, "TRY", first, LT(1)), body, catchList);
        }
    ;

catchListStatement
{
    Token first = LT(1);
}
    :   catchStatement
/*
        (
            nls! catchStatement
        )*
*/
        {
            #catchListStatement = #(node(CATCH_LIST, "CATCH_LIST", first, LT(1)), #catchListStatement);
        }
    ;

catchStatement
{
    Token first = LT(1);
}
    :   K_CATCH
        LPAREN! nls!
        (t:type nls!)? param:ID! nls!
        RPAREN! nls!
        body:openBlockStatement
        {
            #catchStatement = #(node(CATCH, "CATCH", first, LT(1)), param, body);
        }
    ;

methodBlockStatement
    :   curlyBlockStatement
    |   closureBlockStatement
    ;

openBlockStatement
    :   //closureBlockStatement
    //|   curlyBlockStatement
          curlyBlockStatement
    ;


closureBlockStatement
{
    AST blockStat = null;
    Token first = LT(1);
}
    :   RARROW! nls!
        (
            INDENT! NLS!
            sts:statements
            DEDENT!
            {
                blockStat = #sts;
            }
        |   st:statement!
            {
                blockStat = #st;
            }
        )
        {
            #closureBlockStatement = #(node(BLOCK, "BLOCK", first, LT(1)), blockStat);
        }
    ;

curlyBlockStatement
{
    Token first = LT(1);
}
    :   LCURLY! nls!
        (
            sts:statements nls!
        )?
        RCURLY!
        {
            #curlyBlockStatement = #(node(BLOCK, "BLOCK", first, LT(1)), #sts);
        }
    ;

expressionStatement
{
    Token first = LT(1);
}
	:	(INDENT ID COLON) => INDENT! hashMapWithoutBoundary
	|	exp:expression
	;

commandArgument
	:	(hashMapKey COLCON) => hashMapEntry
	|	expression
    ;

commandArgumentList
{
    Token first = LT(1);
}
    :   commandArgument
        ( options {greedy=true;}
        :   COMMAR! nls!
            commandArgument
        )*
        {
            #commandArgumentList = #(node(ARG_LIST, "ARG_LIST", first, LT(1)), #commandArgumentList);
        }
    ;

expression
	:	assignmentExpression
	;


contextExpression
	:	matchOrExpression
	;


assignmentExpression
{
    boolean hasLeft = false;
    boolean hasIndentation = false;
}
	:	left:assignmentLeft
		(
			(
                ASSIGN^
			|	PLUS_ASSIGN^
			|	MINUS_ASSIGN^
			|	STAR_ASSIGN^
			|	DIV_ASSIGN^
			|   INIT_ASSIGN^
			)
            nls!
			right:(
                assignmentRight
            )
		)?
	;

 assignmentLeft
	:	matchOrExpression
	;


assignmentRight
    :   yamlHashStatement
    |	assignmentValueExpression
	;

assignmentValueExpression
    :   (K_MATCH) => matchStatement
    |   (K_FOR) => forStatement
    |   (STAR nls ~STAR) => starListGroup
    |	expressionStatement
    ;

matchOrExpression
    :   conditionalExpression
        (
            MOR^ nls!
            conditionalExpression
        )*
    ;

conditionalExpression
    :   matchAndExpression
        (
            QUESTION_COLON^ nls! assignmentExpression
        |   QUESTION^ nls!
            assignmentExpression nls!
            COLON! nls! conditionalExpression
        )?
    ;

matchAndExpression
{
   Token first = LT(1);
}
    :   left:logicalOrExpression
        (
            MAND^ nls!
            logicalOrExpression
        )*
    ;


logicalOrExpression
    :   logicalAndExpression
        (
             (LOR^ | KOR^) nls!
             logicalAndExpression
        )*
    ;


logicalAndExpression
    :   bitwiseAndExpression
        (
             (   LAND^
             |   KAND^
             |   XOR^
             ) nls!
             bitwiseAndExpression
        )*
    ;

bitwiseAndExpression
    :   bitwiseOrExpression
        (
             BOR^ nls!
             bitwiseOrExpression
        )*
    ;


bitwiseOrExpression
    :   equalityExpression
        (
             BAND^ nls!
             equalityExpression
        )*
    ;


equalityExpression
    :   relationalExpression
        (
             (NOT_EQUAL^ | EQUAL^ | IS^ | REGEX_MATCH^ | REGEX_NOT_MATCH^) nls!
             relationalExpression
        )*
    ;


relationalExpression
	:	notExpression
		(   options {greedy=true;} : (
                (   LT^
                |   GT^
                |   LE^
                |   GE^
                |   IN^
                |   INSTANCEOF^
                ) nls!
                notExpression
            )
        )?
	;

notExpression
	:   NOT^ nls! stepExpression
    |   stepExpression
	;


stepExpression
    :   rangeExpression
        (
            K_STEP^ nls!
        	expression
        )?
    ;

rangeExpression
	:	postifxRangeExpression
		(
			(	DB_DOT^
			|	DB_DOT_LT^
			|	GT_DB_DOT^
			|	GT_DB_DOT_LT^
			) nls!
			(shiftExpression)?
		)?
	;

postifxRangeExpression
	:   (	DB_DOT^ {#DB_DOT.setType(U_DB_DOT);}
        |	DB_DOT_LT^ {#DB_DOT_LT.setType(U_DB_DOT_LT);}
        |	GT_DB_DOT^  {#GT_DB_DOT.setType(U_GT_DB_DOT);}
        |	GT_DB_DOT_LT^ {#GT_DB_DOT_LT.setType(U_GT_DB_DOT_LT);}
        ) shiftExpression
    |   shiftExpression
	;


shiftExpression
	:	additiveExpression
		(
			(SL^ | SR^ | BSL^ | BSR^) nls!
			additiveExpression
		)*
	;

additiveExpression
	:	multiplicativeExpression
		(
            (PLUS^ | MINUS^) nls!
            multiplicativeExpression
		)*
	;

multiplicativeExpression
	:	powerExpression
		(
			(STAR^ | DIV^ | MOD^) nls!
			powerExpression
		)*
	;

powerExpression
	:	matchOpExpression
	    (
            (DB_STAR^ | POW^) nls!
            matchOpExpression
        )*
	;

matchOpExpression
	:   expr:aliasExpression
	    (
	        K_MATCH^ nls!
	        (
	            (LCURLY) =>
	            (
                    LCURLY! nls!
                    block:matchBlock
                    nls!
                    RCURLY!
                )
            |
                (
                    matchBlock
                )
	        )
	    )?
	;

aliasExpression
	:   unaryExpression (
	       K_AS^ nls! ID
	    )?
	;

unaryExpression
	:	PLUS^ {#PLUS.setType(U_PLUS);} nls! unaryExpression
	|	MINUS^ {#MINUS.setType(U_MINUS);} nls! unaryExpression
    |   TILDE^ nls! unaryExpression
    //|   NOT^ nls! unaryExpression
    |   LNOT^ nls! unaryExpression
	|	postfixExpression
	;

postfixExpression
	:   pathExp:pathExpression
	    (
	        options {greedy=true;} :
            inc:INC^ {#inc.setType(POST_INC);}
        |   dec:DEC^ {#dec.setType(POST_DEC);}
	    )?
	;


// path expression
pathExpression
	{
        AST prefix = null;
    }
	:	left:primaryExpression!
		{prefix = #left;}
		(   options { greedy=true; }
		: 	(pathSymbol) =>
			right1:pathElement[prefix]!
			{prefix = #right1;}
		|	(LPAREN) =>
			right2:pathElement[prefix]!
			{prefix = #right2;}
		|	(LBRACK) =>
			right3:pathElement[prefix]!
			{prefix = #right3;}
		|	(LCURLY) =>
			right4:pathElement[prefix]!
			{prefix = #right4;}
		)*
		{
			#pathExpression = prefix;
		}
	;


pathElement[AST prefix]
    {
        AST symbol = null;
        Token first = LT(1);
    }
	:	(pathSymbol) => (
			sym:pathSymbol
			nls!
			el:namePart
			{
				symbol = #sym;
				#pathElement = #(node(symbol.getType(), symbol.getText(), prefix, LT(1)), prefix, el);
			}
		)
    |	(LPAREN) => (
			mcp:methodCallWithParen[prefix]
            {
				#pathElement = #mcp;
			}
		)
    |   cblk:callBlockExpression[prefix]
        {
            //System.out.println("Call Closure");
			#pathElement = #cblk;
		}
		
    |	(LBRACK) => (
            idx:indexPropertyArgs[prefix]
            {
            	#pathElement = #idx;
            }
        )
	;

callBlockExpression[AST callee]
{
    Token first = LT(1);
}
    :   blk:curlyBlockStatement
        {
            if (callee != null && callee.getType() == METHOD_CALL) {
                AST closure = #(node(CLOSURE, "CLOSURE", first, LT(1)), blk);
                #callBlockExpression = #(node(METHOD_CALL, "METHOD_CALL", callee.getFirstChild(), LT(1)), callee.getFirstChild(), closure);
            }
            else {
                AST closure = #(node(CLOSURE, "CLOSURE", first, LT(1)), blk);
                #callBlockExpression = #(node(METHOD_CALL, "METHOD_CALL", callee, LT(1)), callee, closure);
            }
        }
    ;

indexPropertyArgs[AST indexee]
    :	LBRACK!
        al:argList
        RBRACK!
        {
	        #indexPropertyArgs = #(node(INDEX_OP, "INDEX_OP", indexee, LT(1)), indexee, al);
        }
    ;


pathSymbol
	:	nls! DOT
	|	STAR_DOT
	|	OPTIONAL_DOT
	//|	DB_COLON
	;

namePart
	:	operatorAsMethodName
    |   keywordAsMethodName
    |   REFRENCE_NAME
    |   ID
	|	STRING
	;

operatorAsMethodName
    :   PLUS
    |   MINUS
    |   STAR
    |   DIV
    |   COMMAR
    |   COLON
    |   DOT
    |   SEMI
    |   QUESTION
    |   TILDE
    |   GT
    |   LT
    |   GE
    |   LE
    |   SR
    |   SL
    ;

keywordAsMethodName
    :   K_TRUE
    |   K_FLASE
    |   K_NULL
    |   IN
    |   IF
    |   ELSE
    |   FOR
    |   WHILE
    |   DO
    |   DEF
    |   NEW
    |   K_CLASS
    |   THIS
    |   SUPER
    |   IS
    |   NOT
    |   K_MATCH
    |   K_ASSERT
    |   RFC822
    ;

primaryExpression
	:	(hashMapEntryStart) => hashMapWithoutBoundary
	|   hashMapConstructExpression
	|   literal
	|   closureExpression
	|   stringConstructorExpression
	|   regexContructorExpression
	|   fileConstructorExpression
	|	identifier
	|	pairExpression
	|	newExpression
	|	THIS
	|	SUPER
  	|   listExpression
	|   lp:LPAREN! expr:expression RPAREN! {((SoyaCST) #expr).setFirst(#lp);}
	|   ifStatement
	;


closureExpression
{
    Token first = LT(1);
}
    :   SIGN_LPAREN! nls!
        (
            paramList:parameterDefinitionList! nls!
        )?
        RPAREN! nls!
        blk:openBlockStatement!
        {
            #closureExpression = #(node(CLOSURE, "CLOSURE", first, LT(1)), paramList, blk);
        }
    |
        SIGN_LCURLY nls!
        sts:statements nls!
        RCURLY!
        {
            AST block = #(node(BLOCK, "BLOCK", first), sts);
            #closureExpression = #(node(CLOSURE, "CLOSURE", first, LT(1)), block);
        }
    ;


newExpression
{
    AST args = null;
    Token first = LT(1);
}
	:   K_NEW! nls! t:type!
	    (
	        as:methodCallWithParen[null]!
	        { args = #as.getFirstChild(); }
	    )?
	    {
            #newExpression = #(node(NEW, "NEW", first, LT(1)), #t, args);
	    }
	;

stringConstructorExpression
{
    Token first = LT(1);
}
	:
	    (
	        STR_SQ_START
    	|   STR_TQ_START
    	)
	    expression
	    (
	        STR_PART_MIDDLE
	        expression
	    )*
	    STR_PART_END
        {
   	        #stringConstructorExpression = #(node(STRING_CONSTRUCTOR, "STRING_CONSTRUCTOR", first, LT(1)), stringConstructorExpression);
   	    }
	;

regexContructorExpression
{
    Token first = LT(1);
}
	:   REGEX_START
        expression
 	    (
	        REGEX_MIDDLE
	        expression
	    )*
        REGEX_END
        {
            #regexContructorExpression = #(node(REGEX_CONSTRUCTIOR, "REGEX_CONSTRUCTIOR", first, LT(1)), regexContructorExpression);
        }
	;

fileConstructorExpression
{
    Token first = LT(1);
}
	:   FILE_PATH_START
	    expression
	    (
	        FILE_PATH_MIDDLE
	        expression
	    )*
	    ( FILE_PATH_END | RCURLY! )
	    {
	        #fileConstructorExpression = #(node(FILE_CONSTRUCTOR, "FILE_CONSTRUCTOR", first, LT(1)), fileConstructorExpression);
	    }
	;


pairExpression
{
    Token first = LT(1);
}
	:	(PAIR_LCURLY | WELL_LCURLY!)
		expr:expression
		(
			(RCURLY_X_PAIR_LCURLY | RCURLY_X_LCURLY!)
			expr2:expression
		)*
		(RCURLY! | RCURLY_X_PAIR)
		{
			#pairExpression = #(node(PAIR_CONSTRUCTOR, "PAIR_CONSTRUCTOR", first, LT(1)), #pairExpression);
		}
	;


listExpression
{
    Token first = LT(1);
}
	:   LBRACK! nls!
	    (
	        argument
	        (
     			options { greedy = true; } :
     	        (COMMAR! | NLS!)
     	        argument
    	    )*
	    )?
	    (
	        THREE_DOT argument
	    )?
	    nls! RBRACK!
	    {
            #listExpression = #(node(LIST, "LIST", first, LT(1)), #listExpression);
	    }
	;


hashMapConstructExpression!
{
    Token first = LT(1);
}
	:	LCURLY! nls!
		(
		    (hashMapKey COLON) =>
			lst:hashMapEntryList nls!
		)?
		RCURLY!
		{
		    if (#lst != null) {
		       #hashMapConstructExpression = #lst;
		    }
		    else {
		       #hashMapConstructExpression = #(node(HASH, "HASH", first, LT(1)));
		    }
		}
	;

hashMapWithoutBoundary
	:	hashMapEntryList
	;

hashMapWithIndentation
{
    Token first = null;
}
	:	INDENT!
	    {
	        first = LT(1);
	    }
		hashMapEntry
		(
			options { greedy = true; } :
			(COMMAR! | NLS!) hashMapEntry
		)*
		{
			#hashMapWithIndentation = #(node(HASH, "HASH", first, LT(1)), #hashMapWithIndentation);
		}
		nls! DEDENT!
	;


hashMapEntryList
{
    Token first = LT(1);
}
	:	hashMapEntry
		(
			options { greedy = true; } :
			(COMMAR nls | NLS hashMapEntryStart) =>
			(
                (COMMAR! nls! | NLS!) hashMapEntry
			)
		)*
		{
			#hashMapEntryList = #(node(HASH, "HASH", first, LT(1)), #hashMapEntryList);
		}
	;

hashMapEntryStart!
    :   hashMapKey hashMapSeperator nls hashMapValue
    ;


hashMapEntry!
{
    Token first = LT(1);
}
	:	key:hashMapKey
	    t:hashMapSeperator nls!
        value:hashMapValue
		{
			#hashMapEntry = #(node(HASH_ENTRY, "HASH_ENTRY", first, LT(1)), #t, #key, #value);
		}
	;

hashMapSeperator
	:   COLON
	|   EQ_GT
	;


hashMapValue
	:	(INDENT hashMapEntryStart) => hashMapWithIndentation
	|   (INDENT) => starListWithIndentation
	|	argument
	;

hashMapKey
	:   identifier
    //|   stringConstructorExpression
	|	stringLiteral
	|	emailLiteral
	|	numberLiteral
	|	urlLiteral
	|   keywordAsMethodName
	;


starListWithIndentation
    :   INDENT! starListGroup nls! DEDENT!
    ;


starListGroup
{
    Token first = LT(1);
    AST ret = null;
    boolean multi = false;
}
    :   (starListHeadItem) =>
        (
            s1:starList
            {
                ret = #s1;
            }
            (   options { greedy = true; } :
                (nls starListHeadItem) =>
                nls! starList
                {
                    System.out.println("is Multi!!");
                    multi = true;
                }
            )*
            {
                if (multi) {
                    System.out.println("DO is Multi!!");
                    #starListGroup = #(node(LIST, "LIST", first, LT(1)), #starListGroup);
                }
                else {
                    System.out.println("NOT Multi!!");
                    #starListGroup = ret;
                }
            }
        )
    ;


starList
{
    Token first = LT(1);
}
	:	starListHeadItem
		(
		    (NLS ~(INDENT | DEDENT)) =>
            NLS! starListItem
		)*
		nls! DEDENT!
		{
			#starList = #(node(LIST, "LIST", first, LT(1)), #starList);
		}
	;

starListHeadItem
    :   STAR! nls! INDENT! (starListGroup | yamlHashStatement | expression)
    ;

starListItem
	:	(starListGroup | yamlHashStatement | expression)
	;

identifier
	:	ID
	;

methodCallWithParen[AST callee]
	:	LPAREN! nls!
		(al:argList!)? nls!
		rp:RPAREN!
		{
		    if (callee != null && callee.getFirstChild() != null) {
                #methodCallWithParen = #(node(METHOD_CALL, "METHOD_CALL", callee.getFirstChild(), LT(1)), callee, #al);
			}
			else {
                #methodCallWithParen = #(node(METHOD_CALL, "METHOD_CALL", callee, LT(1)), callee, #al);
			}
		}
	;


argList
{
    Token first = LT(1);
}
	:	argument
		(
            COMMAR! nls!
            argument
		)*
		{
            #argList = #(node(ARG_LIST, "ARG_LIST", first, LT(1)), #argList);
        }
	;

argument!
{
    Token first = LT(1);
    AST expr = null;
}
	:	(hashMapEntryStart) => en:hashMapEntry
        {
            #argument = #(node(NAMED_ARG, "NAMED_ARG", first, LT(1)), #en);
        }
	|	expr:expression
	    (
	         n:ID
	         {
                #argument = #(node(MATCH_VAR_DEF, "MATCH_VAR_DEF", first, LT(1)), expr, n);
	         }
        |
        	 {
        	    #argument = #expr;
        	 }
	    )
	;

idChain!
{
    Token first = LT(1);
}
    :   id1:ID!
        (   options { greedy = true; } :
            dot:DOT! nls! id2:ID!
            { #id1 = #(node(DOT, ".", first, LT(1)), id1, id2); }
        )*
        { #idChain = #id1; }
    ;


idChainStar!
{
    Token first = LT(1);
}
    :   id1:ID!
        (   options { greedy = true; } :
            dot:DOT! nls! id2:ID!
            { #id1 = #(node(DOT, ".", first, LT(1)), id1, id2); }
        )*
        (
            dot2:DOT! nls! s:STAR!
            { #id1 = #(node(DOT, ".", first, LT(1)), id1, s); }
        |   dot3:DOT! nls! as:K_AS!
            { #id1 = #(node(DOT, ".", first, LT(1)), id1, as); }
        )?
        { #idChainStar = #id1; }
    ;

/** Basic literals */
literal
	:	stringLiteral
//  |   markupLiteral
    |   referenceLiteral
    |   regexLiteral
	|	dateLiteral
	|	timeLiteral
	|	pairLiteral
	|	emailLiteral
	|	numberLiteral
	|	urlLiteral
	|	filePathLiteral
	|   K_NULL
	|	K_TRUE
	|	K_FALSE
	;

markupLiteral
	:   MARKUP
	;


referenceLiteral
	:   REFRENCE_NAME
	;


regexLiteral
    :   REGEX
    ;


stringLiteral
	:	STRING {addEndLineColumn(#stringLiteral, LT(1));}
	;


dateLiteral
	:	DATE^
	    (TIME)?
	    (RFC822 | ID | stringLiteral | stringConstructorExpression)?
	    {addEndLineColumn(#dateLiteral, LT(1));}
	;


timeLiteral
	:	TIME {addEndLineColumn(#timeLiteral, LT(1));}
	;

pairLiteral
	:	PAIR {addEndLineColumn(#pairLiteral, LT(1));}
	;

emailLiteral
	:	EMAIL {addEndLineColumn(#emailLiteral, LT(1));}
	;

urlLiteral
	:	URL {addEndLineColumn(#urlLiteral, LT(1));}
	;

filePathLiteral
	:	FILE_PATH  {addEndLineColumn(#filePathLiteral, LT(1));}
	;

numberLiteral
	:	INTEGER
	|	FLOAT
	|	PERCENTAGE
	;


sep!
    :   SEMI!
        (options { greedy=true; }: NLS!)*
    |   NLS!
        nls!
        (
            options { greedy=true; }:
            SEMI!
            (options { greedy=true; }: NLS!)*
        )*
    ;

nls!
    :
        (options { greedy=true; }: NLS!)?
    ;


/**
 * Lexer of Soya
 * @author: James Gong
 */
class SoyaLexer extends Lexer;

options {
    testLiterals=false;
    k=4;
    charVocabulary='\u0000'..'\uFFFF';
    codeGenBitsetTestThreshold=20;
}

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

}

LPAREN             options {paraphrase="'('";}         :   '(';
RPAREN             options {paraphrase="')'";}         :   ')';
LBRACK             options {paraphrase="'['";}         :   '[';
RBRACK             options {paraphrase="']'";}         :   ']';
LCURLY             options {paraphrase="'{'";}         :   '{'   {pushCurlyLevel(); currentCurlyLevel++;};
//RCURLY             options {paraphrase="'}'";}         :   '}';
RARROW             options {paraphrase="'->'";}        :   "->";
EQ_GT              options {paraphrase="'=>'";}        :   "=>";
ASSIGN             options {paraphrase="'='";}         :   '='   {allowFilePath=true;};
INIT_ASSIGN        options {paraphrase="':='";}        :   ":="  {allowFilePath=true;};
PLUS               options {paraphrase="'+'";}         :   '+';
MINUS              options {paraphrase="'-'";}  	   :   '-';
STAR               options {paraphrase="'*'";}  	   :   '*';
//DIV                options {paraphrase="'/'";}  	   :   '/';
COMMAR             options {paraphrase="','";}  	   :   ','   {allowFilePath=true;};
COLON              options {paraphrase="':'";}  	   :   ':'   {allowFilePath=true;};
DOT                options {paraphrase="'.'";}  	   :   '.';
AT                 options {paraphrase="'@'";}         :   '@';
SIGN_LPAREN        options {paraphrase="'#('";}        :   "#(";
SIGN_LCURLY        options {paraphrase="'#{'";}        :   "#{";
SEMI               options {paraphrase="';'";}  	   :   ';';
QUESTION           options {paraphrase="'?'";}         :   '?';
QUESTION_COLON     options {paraphrase="'?:'";}        :   "?:";
TILDE              options {paraphrase="'~'";}         :   '~';
GT                 options {paraphrase="'>'";}  	   :   '>';
GE                 options {paraphrase="'>='";}  	   :   ">=";
LE                 options {paraphrase="'<='";}  	   :   "<=";
SR                 options {paraphrase="'>>'";}  	   :   ">>";
SL                 options {paraphrase="'<<'";}  	   :   "<<";
LOR                options {paraphrase="'||'";}        :   "||";
LAND               options {paraphrase="'&&'";}        :   "&&";
MOR                options {paraphrase="'|'";}         :   '|';
MAND               options {paraphrase="'&'";}         :   '&';
BAND               options {paraphrase="'.&.'";}       :   ".&.";
BOR                options {paraphrase="'.|.'";}       :   ".|.";
BSR                options {paraphrase="'.>>.'";}      :   ".>>.";
BSL                options {paraphrase="'.<<.'";}      :   ".<<.";
WELL_LCURLY        options {paraphrase="'#{'";}  	   :   "#{";
INC		           options {paraphrase="'++'";}  	   :   "++";
DEC		           options {paraphrase="'--'";}  	   :   "--";
DB_STAR            options {paraphrase="'**'";}  	   :   "**";
POW                options {paraphrase="'^'";}  	   :   '^';
DB_COLON           options {paraphrase="'::'";}  	   :   "::";
DB_COLON_LBRACK    options {paraphrase="'::['";}  	   :   "::[";
DB_DOT             options {paraphrase="'..'";}  	   :   "..";
THREE_DOT          options {paraphrase="'...'";}  	   :   "...";
DB_DOT_LT          options {paraphrase="'..<'";}  	   :   "..<";
GT_DB_DOT          options {paraphrase="'>..'";}  	   :   ">..";
GT_DB_DOT_LT       options {paraphrase="'>..<'";}  	   :   ">..<";
STAR_DOT           options {paraphrase="'*.'";}  	   :   "*.";
OPTIONAL_DOT       options {paraphrase="'?.'";}        :   "?.";
EQUAL              options {paraphrase="'=='";}        :   "==";
NOT_EQUAL          options {paraphrase="'!='";}        :   "!=";
REGEX_MATCH         options {paraphrase="'=~'";}       :   "=~";
REGEX_NOT_MATCH     options {paraphrase="'!~'";}       :   "!~";
PLUS_ASSIGN        options {paraphrase="'+='";}  	   :   "+=";
MINUS_ASSIGN       options {paraphrase="'-='";}  	   :   "-=";
STAR_ASSIGN        options {paraphrase="'*='";}  	   :   "*=";
//DIV_ASSIGN         options {paraphrase="'/='";}  	   :   "/=";
DB_STAR_ASSIGN     options {paraphrase="'**='";}  	   :   "**=";
AND_ASSIGN         options {paraphrase="'&&='";}  	   :   "&&=";
OR_ASSIGN          options {paraphrase="'||='";}  	   :   "||=";


protected
LETTER
	:	('a'..'z'|'A'..'Z'|'\u00C0'..'\u00D6'|'\u00D8'..'\u00F6'|'\u00F8'..'\u00FF'|'\u0100'..'\uFFFE'|'_')
	;

protected
EXPONENT
options {
    paraphrase="an exponent";
}
    :   ('e'|'E') ('+'|'-')? ('0'..'9'|'_')* ('0'..'9')
    ;

protected
HEX_DIGIT
	:	('a'..'f'|'0'..'9')
	;

protected
DIGIT
	:	('0'..'9')
	;

protected
DIGIT_UNDERLINE
	: (DIGIT|'_')
	;

protected
DIGITS
	: (DIGIT)*
	;

protected
DIGITS_UNDERLINE
	: (DIGIT_UNDERLINE)*
	;

protected
DECIMAL_DIGITS
	: ('1'..'9') DIGITS
	;

protected
DOT_FLOAT_DIGITS
	: '.' ('0'..'9') (DIGITS_UNDERLINE DIGIT)?
	;

protected
URL_CHAR
	: (LETTER|DIGIT|'='|'~'|'!'|'@'|'#'|'$'|'%'|'^'|'&'|'*'|'-'|'+'|'\\'|'|'|','|'.'|':'|'<'|'>'|'/'|'?')
	;

protected
DATE_CHAR
options {
    paraphrase="a date character";
}
	:	('a'..'z'|'A'..'Z'|DIGIT|'_'|'+'|'-')
	;

LNOT
    : '!'
    ;

LT
options {
    paraphrase="less then operator";
}
{
    boolean xmlBegin = false;
    boolean closed = false;
}
    :   '<'
        {
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
        /*
        (
            ({xmlBegin}?) =>
            (
                NARKUP_ELEMENT[true]
                {
                    _ttype = MARKUP;
                }
            )
        |
        )
        */
    ;

protected
NARKUP_ELEMENT[boolean hasLT]
{
    boolean closed = false;
}
    :   (
             ({!hasLT}?) =>
             '<' MARKUP_WS
        |
        )
        MARKUP_NAME
        MARKUP_WS
        MARKUP_ATTRS
        (
            '/'
            { closed = true; }
        )?
        '>'
        (
            ({!closed}?) =>
            (
                MARKUP_WS
                (
                    ('<' ~'/') =>
                    NARKUP_ELEMENT[false]
                    (
                        (MARKUP_WS '<' ~'/') =>
                        MARKUP_WS
                        NARKUP_ELEMENT[false]
                    )*
                |
                    (~'<') =>
                    (MARKUP_CONTENT_CHAR)+
                )?
                MARKUP_WS
                '<' '/' MARKUP_NAME '>'
            )
        |
        )
    ;

protected
MARKUP_ATTRS
    :   (
            (MARKUP_NAME)+
            MARKUP_WS '='
            MARKUP_WS '"' (DQ_STRING_CHAR)* '"'
            MARKUP_WS
        )*
    ;

protected
MARKUP_WS
    :   (' ' | '\t' | '\n' | '\r' | "\r\n")*
    ;


protected
MARKUP_CONTENT_CHAR
    :   ~('<' | '>')
    ;

protected
MARKUP_NAME
    :   (LETTER | DIGIT)+
    |   '{' (LETTER | DIGIT)+ '}'
    ;


ID
options {
    paraphrase="an identifier";
    testLiterals=true;
}
	{boolean isEmail = false, isHasSepcialChar = false, isColon = false;}
	:	(LETTER | '_') (LETTER | '_' | DIGIT)*
	  	{
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
	  	(
			{isEmail}?
			(('+' | '-' | '.') (DATE_CHAR)*)? '@'
			 (
			 	(DATE_CHAR)+ '.' (DATE_CHAR)+
	  				{
	  					_ttype = EMAIL;
	  				}
	  		)
	  	|   ("://") => (
	  			"://" (URL_CHAR)+
	  			{
		  			_ttype = URL;
		  		}
		  	)?
	  	/*|	'('
	  		{
	  			_ttype = ID_LPAREN;
	  		}*/
	  	)?
	  	{
			allowFilePath = false;
	  	}
	;


INTEGER
options { testLiterals=true; }
	{
		boolean isPercentage = false;
		boolean isDecimal = true;
	}
	:	(
			(
				('0'	{ isDecimal = true; }
					(
						('x'|'X') HEX_DIGIT ((HEX_DIGIT | '_')* HEX_DIGIT)?
						{ isDecimal = false; }
					|   ('b'|'B') ('0'|'1') (('0'|'1'|'_')* ('0'|'1'))?
						{ isDecimal = false; }
					|   ('0'..'7') (('0'..'7'|'_')* ('0'..'7'))?
						{ isDecimal = false; }
					)?
	  			)
			|	('1'..'9') (DIGITS_UNDERLINE DIGIT)?
				{ isDecimal = true; }
			)
			(
				(':' DIGIT) =>
				{isDecimal}?
				(
					':' (DIGIT (DIGITS_UNDERLINE DIGIT)?)
					(':' DIGIT (DIGITS_UNDERLINE DIGIT)?)?
					{
						_ttype = TIME;
					}
				)
			)?
		)
		(
			(
				{isDecimal}?
				(
					('/' DIGITS '/' DIGITS)
				|	('-' DIGITS '-' DIGITS)
				|   ('\\' DIGITS '\\' DIGITS)
				)
			)
			{
				_ttype = DATE;
			}
		|	(
				('.' ('0'..'9')) =>
				{isDecimal}?
				DOT_FLOAT_DIGITS
				{
					if (_ttype != TIME)
						_ttype = FLOAT;
				}
				(
				    ('.' ('0'..'9')) =>
					'.' DECIMAL_DIGITS
					{
						_ttype = DATE;
					}
			    |
				)
			)?
		)
		(
			{
				if (_ttype != TIME && LA(1) == '%') {
					char c = LA(2);
					int i = 3;
					while (c == ' ') {
						c = LA(i++);
					}
					isPercentage = (c == '\n' || c == '\r' || c == ';' || c == '\uffff');
				}
			}
			(
				{isPercentage}? '%' { _ttype = PERCENTAGE; }
			)?
		|   
            (
       			('x' | 'X')
   			    ( 
       				(('0' | DECIMAL_DIGITS)
				    {
   						_ttype = PAIR;
				    }
   				)
   				(('x' | 'X') ('0' | DECIMAL_DIGITS))*
   				(('x'! | 'X'!) '{'!
   				{
   					_ttype = PAIR_LCURLY;
   				}
   			)?
		)
	)
		    
		)
		{
			allowFilePath = false;
	  	}
	;

DATE
options {
    paraphrase="a date";
}
{
    String pairText;
}
	:	'#'!  ('0' | DECIMAL_DIGITS)	(

		('/' DIGITS '/' DIGITS)
	|   ('-' DIGITS '-' DIGITS)
	|   ('\\' DIGITS '\\' DIGITS)
	|   ('.' DIGITS '.' DIGITS)
	|   (
			('x' | 'X')
			(
				(('0' | DECIMAL_DIGITS)
					{
						_ttype = PAIR;
					}
				)
				(('x' | 'X') ('0' | DECIMAL_DIGITS))*
				(('x'! | 'X'!) '{'!
					{
						_ttype = PAIR_LCURLY;
					}
				)?
			|
				('{'!
					{
						pairText = $getText;
						pairText = pairText.substring(0, pairText.length() - 1);
						$setText(pairText);
						_ttype = PAIR_LCURLY;
					}
				)
			)
		)
	)
	{
		allowFilePath = false;
  	}
	;

RFC822
    :   "GMT"
    |   "UTC"
    |   "ECT"
    |   "EET"
    |   "ART"
    |   "EAT"
    |   "MET"
    |   "NET"
    |   "PLT"
    |   "IST"
    |   "BST"
    |   "VST"
    |   "CTT"
    |   "JST"
    |   "ACT"
    |   "AET"
    |   "SST"
    |   "NST"
    |   "MIT"
    |   "HST"
    |   "AST"
    |   "PST"
    |   "PNT"
    |   "MST"
    |   "CST"
    |   "EST"
    |   "IET"
    |   "PRT"
    |   "CNT"
    |   "AGT"
    |   "BET"
    |   "CAT"
    ;

UNIX_HEADER
	:	'#' '!' ( options { greedy = true; }:  ~('\n'|'\r'|'\uffff') )*
        { $setType(Token.SKIP); }
	;

REFRENCE_NAME
	:   '#' ('_' | '$' | LETTER) ('_' | '$' | LETTER | DIGIT)*
	    {
	        String refText = $getText;
	        refText = refText.substring(1, refText.length());
	        $setText(refText);
	    }
	;

RCURLY
options {
	paraphrase="right curly";
}
{
    int tt = RCURLY;
}
	:	'}'!
	    {
	        popCurlyLevel();
	    }
	    (
	        ('x' | 'X') =>
	        ('x'! | 'X'!)
		    (
    			(
    				('0' | DECIMAL_DIGITS)
    				{
    					tt = RCURLY_X_PAIR;
    				}
    			)
    			(('x' | 'X') ('0' | DECIMAL_DIGITS))*
    			(
    				('x'! | 'X'!) '{'!
    				{
   					    tt = RCURLY_X_PAIR_LCURLY;
				    }
			    )?
    		|
    			(
    				'{'!
    				{
    					tt = RCURLY_X_LCURLY;
    				}
    			)
    		)
    	|
    	    ({stringPartType == STR_SQ_START}?) =>
    	    tt=STRING_PART[false, false]
    	|   ({stringPartType == STR_TQ_START}?) =>
    	    tt=STRING_PART[false, true]
    	|   ({stringPartType == REGEX_START}?) =>
    	    tt=REGEX_PART[false, false]
            (
                {tt == REGEX_NUM_PART}?
                (
                    {tt == REGEX_NUM_PART}?
                    tt=REGEX_PART[false, true]
                )+
            |
            )
        |   ({stringPartType == FILE_PATH_START}?) =>
            (
                (FILE_CHAR) =>
                tt=FILE_PATH_PART[false]
            |
                { tt = FILE_PATH_END; }
            )
  	    |
  	        ({stringPartType == 0}?) =>
      		{
                //System.out.println("Current Curly Level: " + currentCurlyLevel);
                $setText("}");
     		    tt = RCURLY;
      		    currentCurlyLevel--;
       		}
		)
        { $setType(tt); }
	;


protected
FILE_START_CHAR
	: 	(LETTER|DIGIT|'~'|'!'|'@'|'#'|'$'|'%'|'^'|'&'|'-'|'+'|','|'.'|':'|'/'|'\\')
	;

protected
FILE_CHAR
    :   (FILE_START_CHAR|'=')
	;


MOD
options { testLiterals=true; }
{
    String fileText;
    int tt = MOD;
}
	:
        ('%' '=') => '%' '='
        {$setType(MOD_ASSIGN);}
    |	('%' FILE_START_CHAR) => '%'! (FILE_START_CHAR) => tt=FILE_PATH_PART[true]
        {$setType(tt);}
    |   ('%') => '%' {$setType(MOD);}
	;


protected
FILE_PATH_PART[boolean start]
returns [int tt = FILE_PATH]
	:   (
	        {start}? FILE_START_CHAR
	    )?
        (FILE_CHAR) =>
        (
            options { greedy = true; }:
            FILE_CHAR
        )*
        {
           if (start) {
               tt = FILE_PATH;
           }
           else if (stringPartType == FILE_PATH_START) {
               tt = FILE_PATH_END;
           }
           stringPartType = 0;
        }
        (
            ('{') =>
            '{'!
            {
                tt = start ? FILE_PATH_START : FILE_PATH_MIDDLE;
                if (start) {
                    stringPartType = FILE_PATH_START;
                }
                pushCurlyLevel();
            }
        |   (FILE_CHAR) =>
            (
                options { greedy = true; }:
                FILE_CHAR
            )+
            {
               if (start) {
                   tt = FILE_PATH;
               }
               else if (stringPartType == FILE_PATH_START) {
                   tt = FILE_PATH_END;
               }
               stringPartType = 0;
            }
        )
        {$setType(tt);}
	;


protected
NL
	:	(options {generateAmbigWarnings=false;}
        	:"\r\n"|'\r'|'\n')
        {
			newlines++;
			newline();
        }
	;

NLS
	: 	NL (' ' | NL)*
		{
			if (whiteSpaceStartPos == 0) {
				_ttype = Token.SKIP;
			}
			else {
				$setText("<NEWLINES>");
			}
			allowFilePath = true;
		}
	;

protected
STRING_CHAR
options {
    paraphrase="a string character";
}
    :   ~('"'|'\''|'\\'|'{'|'\n'|'\r'|'\uffff')
    ;

protected
ESC
options {
    paraphrase="an escape sequence";
}
    :   '\\'!
        (   'n'     {$setText("\n");}
        |   'r'     {$setText("\r");}
        |   't'     {$setText("\t");}
        |   'b'     {$setText("\b");}
        |   'f'     {$setText("\f");}
        |   '"'
        |   '\''
        |   '\\'
        |   '$'
        |   '{'
        |   ('u')+ {$setText("");}
            HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
            {char ch = (char)Integer.parseInt($getText, 16); $setText(ch);}
        |   ('0'..'3')
            (
                options { warnWhenFollowAmbig = false; }
            :   ('0'..'7')
                (
                    options { warnWhenFollowAmbig = false; }
                :   ('0'..'7')
                )?
            )?
            {char ch = (char)Integer.parseInt($getText, 8); $setText(ch);}
        |   ('4'..'7')
            (
                options { warnWhenFollowAmbig = false; }
            :   ('0'..'7')
            )?
            {char ch = (char)Integer.parseInt($getText, 8); $setText(ch);}
        )
    ;

SINGLE_LINE_COMMENT
options {
    paraphrase="single line comment";
}
    :   ('/' '/') => "//" ( options { greedy = true; }:  ~('\uffff'|'\n'|'\r'))*
        { $setType(Token.SKIP); }
    ;


MULTI_COMMENT
options {
    paraphrase="a multiple lines comment";
}
    :   ("/*") => "/*"
        (
            options { generateAmbigWarnings=false; greedy=true; }
        :   ('*' ~('/')) => '*'
        |   NL | ~('*'|'\n'|'\r'|'\uffff')
        )*
        "*/"
        { $setType(Token.SKIP); }
    ;


REGEX
{
    int tt = REGEX;
    regexPatternAttrubtes = 0;
}
    :   ('/' REGEGX_START) =>
        (
            '/'! tt=REGEX_PART[true, false]
            (
                {tt == REGEX_NUM_PART}?
                (
                    {tt == REGEX_NUM_PART}?
                    tt=REGEX_PART[false, true]
                )+
            |
            )
            { $setType(tt); }
        )
    |   "/=" {$setType(DIV_ASSIGN);}
    |   '/' {$setType(DIV);}
    ;

protected
REGEGX_ATTRUBTE
    :   'i' | 'g' | 'm' | 's' | 'U'
    ;


protected
REGEGX_START
    :   (~( '*' | '/' | '\n' | '\r') | '\\' '/') (~( '/' | '\n' | '\r') | '\\' '/')* '/'
    ;


protected
REGEX_PART[boolean start, boolean number]
returns [int tt = REGEX_END]
{
    boolean testCount = false;
    boolean multiCurly = false;
}
    :  (
          {start}? REGEX_START_CHAR
       )?
       (
           options { greedy = true; }:
           REGEX_CHAR
       |   '}'
       )*
       {
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
       (
           ('/') =>
           '/'
           {
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
           (
               REGEGX_ATTRUBTE
           )*
       |
           ('{') =>
           (
                {testCount}? '{' { tt = REGEX_NUM_PART; }
           |    '{'!
                {
                    tt = start ? REGEX_START : REGEX_MIDDLE;
                    if (start) {
                        stringPartType = REGEX_START;
                    }
                    pushCurlyLevel();
                }
           )
       )
       { $setType(tt); }
    ;


protected
REGEX_START_CHAR
{
   boolean bNumber = false;
}
    :  ~( '*' | '{' | '}' | '/' | '\\' | '\n' | '\r' | '\uffff' )
    |  {  LA(2) != '/' && LA(2) != '\n' && LA(2) != '\r'  }? '\\'
    |  '\\' '/'    { $setText('/'); }
    ;

protected
REGEX_CHAR
    :  REGEX_START_CHAR | '*'
    ;


protected
DQ_STRING_CHAR
options {
    paraphrase="a double qoute string character";
}
    :   STRING_CHAR | ESC | '$' | '{' | '}' | '\n' | '\r' | ('\'' (~'\'' | '\'' ~'\''))
    ;


STRING
{int tt = 0;}
	:
		("'''") =>
		("'''"! (STRING_CHAR | ESC | '"' | '{' | '}' | '\n' | '\r'
		| ('\'' (~'\'' | '\'' ~'\'')) => '\''
		)*  "'''"!)
	|	("\"\"\"") =>
	    ("\"\"\""! tt=STRING_PART[true, true] { $setType(tt); })
	|	'\''! (STRING_CHAR | ESC | '"' | '{' | '}' | '\n'! | '\r'!)* '\''!
	|	'"'! tt=STRING_PART[true, false] { $setType(tt); }
	;


protected
STRING_PART[boolean start, boolean tripleQuote]
returns [int tt = STR_PART_END]
	:   (
	        options {  greedy = true;  }:
	        STRING_CHAR | ESC
        |   ('"' (~'"' | '"' ~'"')) => {tripleQuote}? '"'
        |   ('\n' | '\r') => {tripleQuote}? ('\n' | '\r')
	    )*
	    (
	        (   {!tripleQuote}? "\""!
            |   {tripleQuote}? "\"\"\""!
            )
            {
                tt = start ? STRING : STR_PART_END;
                stringPartType = 0;
            }
        |
            '{'!
            {
                if (start) {
                    tt = tripleQuote ? STR_TQ_START : STR_SQ_START;
                    stringPartType = tt;
                }
                else {
                    tt = STR_PART_MIDDLE;
                }
                pushCurlyLevel();
            }
	    )
	    {$setType(tt);}
	;


protected
REGEXP_CHAR
options {
    paraphrase="a regular expression character";
}
    :
        (
            ~('*'|'/'|'$'|'\\'|'\n'|'\r'|'\uffff')
        |   { LA(2)!='/' && LA(2)!='\n' && LA(2)!='\r' }? '\\'
        |   '\\' '/'  { $setText('/'); }
        |!  '\\' NL
        )
        ('*')*
    ;

WS
options {
    paraphrase="whitespace";
}
{
    int spaces = 0;
    int lines = 0;
}
	:	({whiteSpaceStartPos < 0}?) =>
		(options { greedy=true; }: ' '|'\t'|'\u000C')+
		{ _ttype = Token.SKIP; }
	|	({whiteSpaceStartPos == 0}?) =>
		(
			' '			{ spaces++; }
		|	'\t'		{ spaces += 8; spaces -= (spaces % 8); }
		)+
		(
			 ('\r')? '\n' { lines++; }
		)*
		{
			if (LA(1) != -1 || lines == 0) {
				char[] indentation = new char[spaces];
                for (int i = 0; i < spaces; i++) {
                	indentation[i] = ' ';
                }
                String str = new String(indentation);
                $setText(str);
			}
		}
	;




