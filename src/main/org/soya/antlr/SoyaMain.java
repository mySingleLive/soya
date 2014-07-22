package org.soya.antlr;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.collections.AST;
import org.soya.antlr.parser.SoyaLexer;
import org.soya.antlr.parser.SoyaParser;
import org.soya.ast.SoyaCST;

/**
 * @author: Jun Gong
 */
public class SoyaMain {

	public static void main(String[] args) {
		try {
			Reader reader = null;
			if (args.length == 0) {
				String src = "\t* 12\n\t* 24";
				reader = new StringReader(src);
			}
			else {
				reader = new FileReader(new File(args[0]));
			}
			SoyaLexer lexer = new SoyaLexer(reader);
			SoyaTokenSource tokenSource = new SoyaTokenSource(lexer.plumb());
			tokenSource.setLexer(lexer);
			SoyaParser parser = new SoyaParser(tokenSource);
			lexer.setTokenSource(tokenSource);
            parser.setLexer(lexer);
            parser.getASTFactory().setASTNodeClass(SoyaCST.class);
            tokenSource.setParser(parser);

            parser.compilationUnit();
			AST results = parser.getAST();

			//DumpASTVisitor vistor = new DumpASTVisitor();
			//vistor.visit(results);
			System.out.println("parse: " + results.toStringTree());
			reader.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
            e.printStackTrace();
		}
		catch (TokenStreamException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (RecognitionException e) {
			e.printStackTrace();
		}
	}

}
