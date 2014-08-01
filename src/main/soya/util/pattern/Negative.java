package soya.util.pattern;

import org.soya.runtime.InvokeUtil;
import org.soya.runtime.SoyaShell;

/**
 * Created by Gonjun on 7/31/14.
 */
public class Negative {
    public static boolean isCase(Object o) throws Throwable {
        if (!InvokeUtil.isNumber(o)) {
            return false;
        }
        return SoyaShell.getSharedShell().lessThen(o, 0);
    }
}
