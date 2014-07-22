package soya.lang;

import org.soya.runtime.PatternUtil;

import java.util.*;

/**
 * @author: Jun Gong
 */
public class Tuple extends AbstractList {

	private final boolean isPair;
    private final Object[] values;
    
    public Tuple() {
    	this(new Object[0], false);
    }

    public Tuple(Object[] values) {
    	this(values, false);
    }
    
    public Tuple(Object[] values, boolean isPair) {
        this.values = values;
        this.isPair = isPair;
    }

    public int size() {
        return values.length;
    }

    public boolean isEmpty() {
        return values == null || values.length == 0;
    }

    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    public Object[] toArray() {
        return values.clone();
    }

    public Object[] toArray(Object[] objects) {
        System.arraycopy(values, 0, objects, 0, objects.length);
        return objects;
    }

    public int indexOf(Object o) {
        for (int i = 0; i < values.length; i++) {
            try {
                if (PatternUtil.isMatch(o, values[i])) {
                    return i;
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        int lastIndex = -1;
        for (int i = 0; i < values.length; i++) {
            try {
                if (PatternUtil.isMatch(o, values[i])) {
                    lastIndex = i;
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        return lastIndex;
    }

    public List subList(int i, int i2) {
        return Arrays.asList(values).subList(i, i2);
    }

    public Object get(int i) {
        return values[i];
    }

	public boolean isPair() {
		return isPair;
	}

	public String join(String s) {
		StringBuffer sb = new StringBuffer();
		int len = values.length;
		for (int i = 0; i < len; i++) {
			Object value = values[i];
			sb.append(value.toString());
			if (i < len - 1) {
				sb.append(s);
			}
		}
		return sb.toString();
	}

    public String toString() {
        if (size() == 0) {
            return "()";
        }
    	if (isPair) {
    		return join("x");
    	}
        StringBuffer buffer = new StringBuffer();
        buffer.append("(");
        for (int i = 0; i < size(); i++) {
            Object o = get(i);
            if (o instanceof String) {
                buffer.append("\"");
                buffer.append(o);
                buffer.append("\"");
            }
            else {
                buffer.append(o.toString());
            }
            if (i < size() - 1) {
                buffer.append(", ");
            }
        }
        buffer.append(")");
        return buffer.toString();
    }
}
