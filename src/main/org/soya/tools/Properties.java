package org.soya.tools;

/**
 * @author: Jun Gong
 */
public class Properties {

    public static String getVersion() {
        return "1.0.1-alpha";
    }

    public static String getCompilerName() {
        return "soya";
    }

    public static String getCommandName() {
        return "soya";
    }

    public static String getBuildDate() {
        return "2014";
    }

    public static String getVersionString() {
        return getCompilerName() + " " + getVersion() + " (" + getBuildDate() + ")";
    }

    public static String getHelpString() {
        return "Usage: " + getCommandName() + " [option] ... [source file] arguments\n" +
               "   -v -version     display current version.\n" +
               "   -cp             class search path of directories and zip/jar files.\n" +
               "   -classpath      class search path of directories and zip/jar files.\n" +
               "   -h -help        display help message.";
    }
}
