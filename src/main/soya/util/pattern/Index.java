package soya.util.pattern;

import soya.lang.ClassPattern;
import org.soya.runtime.PatternUtil;

/**
 * @author: Jun Gong
 */
public class Index {

    private Index() {
    }

    public static boolean isCase(Object o) throws Throwable {
        return PatternUtil.isMatch(o, new ClassPattern(int.class));
    }
}
