package soya.lang;

import org.soya.runtime.MetaClassUtil;
import org.soya.runtime.PatternUtil;

/**
 * @author: Jun Gong
 */
public class NotPattern extends ObjectPattern {

    public NotPattern(Object object) {
        super(MetaClassUtil.createMetaClass(NotPattern.class), object);
    }

    public boolean isMatch(Object obj) throws Throwable {
        return  !PatternUtil.isMatch(obj, object);
    }

    @Override
    public String toString() {
        return "not " + object.toString();
    }
}
