package soya.lang;

import org.soya.runtime.MetaClassUtil;

/**
 * @author: Jun Gong
 */
public class Null extends EvalObject {

    public static Null NULL = new Null();

    private Null() {
        super(MetaClassUtil.Null);
    }

    public boolean isNull() {
        return true;
    }

    public boolean isEmpty() {
        return true;
    }

    public boolean equals(Object o) {
        return o == NULL || o == null;
    }

    public String toString() {
        return "null";
    }
}
