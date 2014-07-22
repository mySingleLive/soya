package soya.util.pattern;

import org.soya.runtime.PatternUtil;
import soya.lang.Pattern;

/**
 * @author: Gongjun
 */
public class Or implements Pattern {

    private final Object left;
    private final Object right;
    private int size = -1;

    public Or(Object left, Object right) {
        this.left = left;
        this.right = right;
    }

    public boolean isMatch(Object obj) throws Throwable {
        return PatternUtil.isMatch(obj, left) || PatternUtil.isMatch(obj, right);
    }

    public Object getLeft() {
        return left;
    }

    public Object getRight() {
        return right;
    }

}
