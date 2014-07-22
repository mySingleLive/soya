package org.soya.runtime;

import soya.lang.*;
import soya.util.pattern.And;
import soya.util.pattern.Index;
import soya.util.pattern.Or;

import java.util.Iterator;

/**
 * @author: Jun Gong
 */
public class PatternUtil {

    public static boolean hasIndexPattern(Object pattern) {
        if (pattern instanceof ClassPattern &&
                Index.class.isAssignableFrom(((ClassPattern) pattern).getMetaClass().getClazz())) {
            return true;
        }
        if (pattern instanceof Or) {
            return hasIndexPattern(((Or) pattern).getLeft()) ||
                    hasIndexPattern(((Or) pattern).getRight());
        }
        if (pattern instanceof And) {
            return hasIndexPattern(((And) pattern).getLeft()) ||
                    hasIndexPattern(((And) pattern).getRight());
        }
        if (pattern instanceof ObjectPattern) {
            return hasIndexPattern(((ObjectPattern) pattern).getObject());
        }
        return false;
    }

    public static boolean isMatch(Object obj, Object pattern) throws Throwable {
        if (obj.equals(pattern)) {
            return true;
        }
        if (pattern instanceof Pattern) {
            return ((Pattern) pattern).isMatch(obj);
        }
        return false;
    }
    
    public static boolean isMatch(Object obj, Object[] patterns) throws Throwable {
    	int len = patterns.length;
    	for (int i = 0; i < len; i++) {
    		Object pattern = patterns[i];
    		if (isMatch(obj, pattern)) {
    			return true;
    		}
    	}
    	return false;
    }

    public static boolean isMatchProperties(Object obj, SoyaMap properties) {
        Iterator<Object> keyIterator = properties.keySet().iterator();
        while (keyIterator.hasNext()) {
            Object objKey = keyIterator.next();
            String key = objKey.toString();
            try {
                Object result;
                if (obj instanceof EvalObject) {
                    result = ((EvalObject) obj).getPropertyValue(key);
                }
                else {
                    result = InvokeUtil.getJavaObjectPropertyValue(obj.getClass(), obj, key);
                }
                Object value = properties.get(objKey);
                if (!isMatch(result, value)) {
                    return false;
                }
            } catch (NoSuchMethodException e) {
                return false;
            } catch (Throwable throwable) {
                throwable.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                return false;
            }
        }
        return true;
    }

}
