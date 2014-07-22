package org.soya.util;

import java.util.List;

/**
 * @author: Jun Gong
 */
public class ArrayUtil {

    public static Object[] makeArray(Object o) {
        if (o.getClass().isArray()) {
            return (Object[]) o;
        }
        if (o instanceof List) {
            return ((List) o).toArray();
        }
        return null;
    }
}
