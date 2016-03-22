package org.soya.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: Jun Gong
 */
public class Properties {

    public static String getVersion() {
        return "1.0.2-alpha";
    }

    public static String getCompilerName() {
        return "Soya";
    }

    public static String getCommandName() {
        return "soya";
    }

    private static String getBuildYear() {
        return "2016";
    }

    private static String getBuildMonth() {
        return "03";
    }

    private static String getBuildDay() {
        return "22";
    }

    private static String getBuildDate() {
        return getBuildYear() + "." + getBuildMonth() + "." + getBuildDay();
    }

    public static String getCopyright() {
        return "Copyright (c) 2013-" + getBuildYear() + ", Jun Gong";
    }

    public static String getVersionString() {
        return getCompilerName() + " version \"" + getVersion() + "\" [build " + getBuildDate() + "]\n" +
                getCopyright();
    }

    public static String getHelpString() {
        return "Usage: " + getCommandName() + " [option] ... [source file] arguments\n" +
               "   -v -version     display current version.\n" +
               "   -cp             class search path of directories and zip/jar files.\n" +
               "   -classpath      class search path of directories and zip/jar files.\n" +
               "   -h -help        display help message.";
    }
}
