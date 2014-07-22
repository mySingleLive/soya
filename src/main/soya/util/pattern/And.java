package soya.util.pattern;

import org.soya.runtime.PatternUtil;
import soya.lang.Pattern;

/**
 * @author: Jun Gong
 */
public class And implements Pattern {

    private final Object left;
    private final Object right;

    public And(Object left, Object right) {
        this.left = left;
        this.right = right;
    }

    public boolean isMatch(Object obj) throws Throwable {
        return PatternUtil.isMatch(obj, left) && PatternUtil.isMatch(obj, right);
    }

    public Object getLeft() {
        return left;
    }

    public Object getRight() {
        return right;
    }
}
