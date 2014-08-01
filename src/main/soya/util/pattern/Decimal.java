package soya.util.pattern;

import org.soya.runtime.InvokeUtil;

/**
 * Created by Gonjun on 7/31/14.
 */
public class Decimal {
    public static boolean isCase(Object o) {
        if (InvokeUtil.isNumber(o)) {
            String s = o.toString();
            int len = s.length();
            for (int i = 0; i < len; i++) {
                char ch = s.charAt(i);
                if (ch == '.') {
                    return true;
                }
            }
        }
        return false;
    }
}
