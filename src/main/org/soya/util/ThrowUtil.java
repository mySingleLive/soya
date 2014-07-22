package org.soya.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Jun Gong
 */
public class ThrowUtil {

    private static String SOYA_PACKAGES[] = {
        "org.soya.",
        "soya.",
        "java.",
        "javax.",
        "sun."
    };

    public static Throwable sanitize(Throwable t) {
        StackTraceElement[] trace = t.getStackTrace();
        List<StackTraceElement> newTrace = new ArrayList<StackTraceElement>();
        for (StackTraceElement stackTraceElement : trace) {
            if (isApplicationClass(stackTraceElement.getClassName())) {
                newTrace.add(stackTraceElement);
            }
        }

        StackTraceElement[] clean = new StackTraceElement[newTrace.size()];
        newTrace.toArray(clean);
        t.setStackTrace(clean);
        return t;
    }

    public static boolean isApplicationClass(String className) {
        for (String soyaPackage : SOYA_PACKAGES) {
            if (className.startsWith(soyaPackage)) {
                return false;
            }
        }
        return true;
    }

    public static Throwable deepSanitize(Throwable t) {
        Throwable current = t;
        while (current.getCause() != null) {
            current = sanitize(current.getCause());
        }
        return sanitize(t);
    }
}
