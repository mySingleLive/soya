package org.soya.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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

    public static String getBuildDate() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("MM.yyyy");
        return format.format(date);
    }

    public static String getCopyright() {
        return "Copyright (c) 2013-" +  new SimpleDateFormat("yyyy").format(new Date()) + " Jun Gong";
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
