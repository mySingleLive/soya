package soya.lang;

import org.soya.runtime.MetaClassUtil;

/**
 * @author: Jun Gong
 */
public class SoayBoolean extends EvalObject {
    private boolean value;

    public SoayBoolean(boolean value) {
        super(MetaClassUtil.Boolean);
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public String toString() {
        return new StringBuffer().append(value).toString();
    }
}
