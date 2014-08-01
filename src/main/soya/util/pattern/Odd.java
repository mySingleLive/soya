package soya.util.pattern;

import org.soya.runtime.InvokeUtil;
import soya.lang.Int;

/**
 * Created by Gonjun on 7/31/14.
 */
public class Odd {

    public static boolean isCase(Object o) {
        if (!InvokeUtil.isInt(o)) {
            return false;
        }
        if (o instanceof Integer) {
            return InvokeUtil.intValue((Integer) o) % 2 != 0;
        }
        if (o instanceof Int) {
            return InvokeUtil.intValue((Int) o) % 2 != 0;
        }
        return false;
    }
}
