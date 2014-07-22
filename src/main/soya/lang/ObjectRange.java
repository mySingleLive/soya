package soya.lang;

import org.soya.runtime.InvokeUtil;
import org.soya.runtime.PatternUtil;

import java.lang.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;


/**
 * @author: Jun Gong
 */
public class ObjectRange extends AbstractObject implements Range {
    private Comparable from;
    private Comparable to;
    private Object step;
    private boolean includeFrom;
    private boolean includeTo;
    private int size = 0;

    public ObjectRange(Comparable from, Comparable to) {
        this(from, to, null);
    }

    public ObjectRange(Comparable from, Comparable to, Comparable step) {
        this(from, to, true, true, step);
    }

    public ObjectRange(Comparable from, Comparable to, boolean includeFrom, boolean includeTo) {
        this(from, to, includeFrom, includeTo, null);
    }

    public ObjectRange(Comparable from, Comparable to, boolean includeFrom, boolean includeTo, Comparable step) {
//    	super(MetaClassUtil.Range);
        this.from = from;
        this.to = to;
        this.includeFrom = includeFrom;
        this.includeTo = includeTo;
        if (step == null) {
            if ((from instanceof Integer || from instanceof Long)
                    && (to instanceof Integer || to instanceof Long)) {
            	step = new Integer(1);
            }
            else if ((from instanceof Int || from instanceof Float)
                    && (to instanceof Int || to instanceof Float)) {
            	step = new Int(1);
            }
        }
        this.step = step;
        this.size = 0;
    }

    public Comparable getFrom() {
        return from;
    }

    public Comparable getTo() {
        return to;
    }

    public Object getStep() {
        return step;
    }

    public boolean isIncludeFrom() {
        return includeFrom;
    }

    public boolean isIncludeTo() {
        return includeTo;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(from);
        if (includeFrom && includeTo) {
            buffer.append("..");
        }
        else if (includeFrom && !includeTo) {
            buffer.append("..<");
        }
        else if (!includeFrom && includeTo) {
            buffer.append(">..");
        }
        else if (!includeFrom && !includeTo) {
            buffer.append(">..<");
        }
        buffer.append(to);
        return buffer.toString();
    }
    
    protected int intStepValue() {
    	if (step instanceof Integer){
    		return ((Integer) step).intValue();
    	}
    	else if (step instanceof java.lang.Float) {
    		return (int) ((java.lang.Float) step).floatValue();
    	}
    	else if (step instanceof Int) {
    		return ((Int) step).getValue();
    	}
    	else if (step instanceof Float) {
    		return (int) ((Float) step).getValue();
    	}
    	return 1;
    }

    protected Object increment(Object value, int i) {
        if (i <= 0) {
            return value;
        }
        if (value instanceof Integer) {
            return ((Integer) value).intValue() + i;
        }
        if (value instanceof Int) {
            return ((Int) value).plus(i);
        }
        int j = 0;
        for (; j < i; j++) {
            value = increment(value);
        }
        return value;
    }

    protected Object increment(Object value) {
    	if (value instanceof Integer) {
    		return new Integer(((Integer) value).intValue() + intStepValue());
    	}
    	else if (value instanceof Character) {
    		return new Character((char) (((Character) value).charValue() + intStepValue()));
    	}
    	else if (value instanceof Int) {
    		return ((Int) value).plus(((Int)step).getValue());
    	}
        else if (value instanceof CharSequence) {
            int len = ((CharSequence) value).length();
            int pos = 1;
            int intStep = intStepValue();
            char lastC = ((CharSequence) value).charAt(len - pos);
            lastC += intStep;
            return ((CharSequence) value).subSequence(0, len - pos) + String.valueOf(lastC);
        }
		return null;
	}

    public List get(int index1, int index2) {
        return subList(index1, index2);
    }
	
	public Object get(int i) {
        if (i < 0) {
            i = size() + i;
        }
        if (i >= size()) {
            throw new IndexOutOfBoundsException("Index: " + i + " is too big for range: " + this);
        }
        Object value;
        value = from;
        if (!includeFrom) {
            value = increment(value);
        }
        value = increment(value, i);
		return value;
	}

    private int includeSize(int s) {
        if (includeFrom && includeTo) {
            return s + 1;
        }
        else if (!includeFrom && includeTo) {
            return s;
        }
        else if (includeFrom && !includeTo) {
            return s;
        }
        else {
            return s - 1;
        }
    }

    private BigInteger includeSize(BigDecimal s) {
        if (includeFrom && includeTo) {
            return s.add(new BigDecimal("1")).toBigInteger();
        }
        else if (!includeFrom && includeTo) {
            return s.toBigInteger();
        }
        else if (includeFrom && !includeTo) {
            return s.toBigInteger();
        }
        else {
            return s.subtract(new BigDecimal("1")).toBigInteger();
        }
    }


    public int size() {
        if (size == 0) {
            if ((from instanceof Integer || from instanceof Long)
                    && (to instanceof Integer || to instanceof Long)) {
                long fromNum = ((Number) from).longValue();
                long toNum = ((Number) to).longValue();
                size = includeSize((int) (toNum - fromNum));
            } else if (from instanceof Character && to instanceof Character) {
                char fromNum = (Character) from;
                char toNum = (Character) to;
                size = includeSize((int) (toNum - fromNum));
            } else if (from instanceof BigDecimal || to instanceof BigDecimal ||
                       from instanceof BigInteger || to instanceof BigInteger) {
                BigDecimal fromNum = new BigDecimal("" + from);
                BigDecimal toNum = new BigDecimal("" + to);
                BigInteger sizeNum = toNum.subtract(fromNum).add(new BigDecimal(1.0)).toBigInteger();
                size = includeSize(toNum.subtract(fromNum)).intValue();
            } else {
                size = 0;
                Comparable first = from;
                Comparable value = from;
                while (to.compareTo(value) >= 0) {
                    value = (Comparable) increment(value);
                    size++;
                    if (first.compareTo(value) >= 0) {
                    	break;
                    }
                }
            }
            if (!includeFrom) {
                size--;
            }
            if (!includeTo) {
                size--;
            }
        }
        return size;
    }

    @Override
    public Object getWithIndex(int index) {
        return get(index);
    }

    public boolean add(Object e) {
		return false;
	}

	public void add(int index, Object element) {
	}

	public boolean addAll(Collection c) {
		return false;
	}

	public boolean addAll(int index, Collection c) {
		return false;
	}

	public void clear() {
	}

	public boolean contains(Object o) {
        if (o instanceof Comparable) {
            Comparable obj = (Comparable) o;

            int fromResult;
            if (from == null || from == Null.NULL) {
                fromResult = 0;
            }
            else {
                fromResult = obj.compareTo(from);
            }

            int toResult;
            if (to == null || to == Null.NULL) {
                toResult = 0;
            }
            else {
                toResult = obj.compareTo(to);
            }

            if (includeFrom && includeTo) {
                return fromResult >= 0 && toResult <= 0;
            }
            else if (includeFrom && !includeTo) {
                return fromResult >= 0 && toResult < 0;
            }
            else if (!includeFrom && includeTo) {
                return fromResult > 0 && toResult <= 0;
            }
            else if (!includeFrom && !includeTo) {
                return fromResult > 0 && toResult < 0;
            }
        }
        return false;
	}

	public boolean containsAll(Collection c) {
		return false;
	}

	public int indexOf(Object o) {
		return 0;
	}

	public boolean isEmpty() {
		return false;
	}

	public Iterator iterator() {
		return null;
	}

	public int lastIndexOf(Object o) {
		return 0;
	}

	public ListIterator listIterator() {
		return null;
	}

	public ListIterator listIterator(int index) {
		return null;
	}

	public boolean remove(Object o) {
		return false;
	}

	public Object remove(int index) {
		return null;
	}

	public boolean removeAll(Collection c) {
		return false;
	}

	public boolean retainAll(Collection c) {
		return false;
	}

	public Object set(int index, Object element) {
		return null;
	}


    public void each(Closure closure) throws Throwable {
        int len = size();
        for (int i = 0; i < len; i++) {
            Object o = get(i);
            o = InvokeUtil.wrapObject(o);
            closure.call(o);
        }
    }

    public void each(Object pattern, Closure closure) throws Throwable {
        int len = size();
        ClassPattern indexPattern = null;
        if (PatternUtil.hasIndexPattern(pattern)) {
            indexPattern = (ClassPattern) pattern;
        }
        for (int i = 0; i < len; i++) {
            Object obj = get(i);
            if (indexPattern != null) {
                indexPattern.setValue(new Int(i));
                closure.call(InvokeUtil.wrapObject(obj));
            }
            else if (PatternUtil.isMatch(obj, pattern)) {
                closure.call(InvokeUtil.wrapObject(obj));
            }
        }
    }

    public void each(Object pattern1, Object pattern2, Closure closure) throws Throwable {
        int len = size();
        ClassPattern indexPattern = null;
        Object pattern = null;
        if (PatternUtil.hasIndexPattern(pattern1)) {
            indexPattern = (ClassPattern) pattern1;
            pattern = pattern2;
        }
        else if (PatternUtil.hasIndexPattern(pattern2)) {
            indexPattern = (ClassPattern) pattern2;
            pattern = pattern1;
        }
        else {
            pattern = pattern1;
        }

        for (int i = 0; i < len; i++) {
            if (indexPattern != null) {
                indexPattern.setValue(new Int(i));
            }
            Object obj = get(i);
            if (PatternUtil.isMatch(obj, pattern)) {
                closure.call(InvokeUtil.wrapObject(obj));
            }
        }
    }


	public List subList(int index1, int index2) {
        List ret = new SoyaList();
        if (index1 < 0) {
            index1 = size() + index1;
        }
        if (index2 < 0) {
            index2 = size() + index2;
        }
        if (index2 > index1) {
            index2 = Math.min(index2, size() - 1);
            for (int i = index1; i <= index2; i++) {
                ret.add(get(i));
            }
        }
        else {
            ret.add(get(index1));
        }
        return ret;
	}

	public Object[] toArray() {
        int len = size();
        Object[] rets = new Object[len];
        for (int i = 0; i < len; i++) {
            rets[i] = get(i);
        }
		return rets;
	}

	public Object[] toArray(Object[] a) {
		return null;
	}

    public Range and(Range right) {
        return right;
    }

    public Range or(Range right) {
        return right;
    }

    public boolean isMatch(Object obj) {
        return this.contains(obj);
    }
}
