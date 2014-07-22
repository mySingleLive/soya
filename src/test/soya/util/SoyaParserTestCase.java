package soya.util;

import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;

import org.soya.antlr.SoyaTokenSource;
import org.soya.antlr.parser.SoyaLexer;
import org.soya.antlr.parser.SoyaParser;

/**
 * @author: Jun Gong
 */
public class SoyaParserTestCase extends SoyaBaseTestCase {


    protected void parse(Reader reader) {
		try {
			SoyaLexer lexer = new SoyaLexer(reader);
			SoyaTokenSource tokenSource = new SoyaTokenSource(lexer.plumb());
			tokenSource.setLexer(lexer);
			SoyaParser parser = new SoyaParser(tokenSource);
			tokenSource.setParser(parser);

			parser.compilationUnit();
		}
		catch (Exception ex) {
			StringWriter out = new StringWriter();
            out.write(ex.getMessage());
            out.write("\n");
            ex.printStackTrace(new PrintWriter(out));
            fail(out.toString());
        }
	}

}
