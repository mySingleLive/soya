package soya.util.pattern;

import soya.lang.EvalObject;
import org.soya.runtime.InvokeUtil;

/**
 * @author: Jun Gong
 */
public class Empty {

    private Empty() {
    }

    public static boolean isCase(Object o) {
        if (InvokeUtil.isNull(o)) {
            return true;
        }
        o = InvokeUtil.wrapObject(o);
        if (o instanceof EvalObject) {
            return ((EvalObject) o).isEmpty();
        }
        return false;
    }
}
