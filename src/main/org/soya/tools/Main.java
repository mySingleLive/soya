package org.soya.tools;

import org.apache.log4j.PropertyConfigurator;
import org.soya.util.ThrowUtil;

import java.io.FileNotFoundException;


/**
 * @author: Jun Gong
 */
public class Main {

    public static void doMain(String[] args) throws Throwable {
        try {
/*
            System.setProperty("log4j.rootLogger", "INFO, stdout");
            System.setProperty("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
            System.setProperty("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");

            System.setProperty("log4j.logger.httpclient.wire.header", "");
            System.setProperty("log4j.logger.httpclient.wire", "");
            System.setProperty("log4j.logger.org.apache.commons.httpclient", "");
*/


            PropertyConfigurator.configure(System.getProperties());
            ArgumentProcessor processor = new ArgumentProcessor(args);
            Configuration configuration = processor.getConfiguration();
            if (configuration.isVersion()) {
                System.out.println(Properties.getVersionString());
            }
            else if (configuration.isHelp()) {
                System.out.println(Properties.getHelpString());
            }
            else  {
                Interpreter interpreter = new Interpreter(configuration);
                interpreter.interpret(configuration.getSourceFile());
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
        catch (UnreconizedOptionException e) {
            e.printStackTrace();
            System.exit(0);
        }
        catch (ErrorListException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void main(String[] args) throws Throwable {
        try {
            doMain(args);
        } catch (Throwable t) {
            throw ThrowUtil.deepSanitize(t);
//            throw t;
        }
    }

}
