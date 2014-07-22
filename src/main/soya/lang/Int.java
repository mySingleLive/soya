package soya.lang;

import org.soya.runtime.MetaClassUtil;

import java.lang.*;
import java.math.BigDecimal;

/**
 * Int, equals to java.lang.Integer and java.lang.Long on JVM.
 * @author: Jun Gong
 */
public class Int extends AbstractObject implements Comparable, Pattern {

    private int value;

    public Int(int value) {
        super(MetaClassUtil.Int);
        this.value = value;
    }

    public boolean equals(Object o) {
        if (o instanceof Int) {
            return value == ((Int) o).getValue();
        }
        else if (o instanceof Integer) {
            return value == ((Integer) o).intValue();
        }
        else if (o instanceof soya.lang.Float) {
            return value == ((soya.lang.Float) o).getValue();
        }
        return false;
    }

    public int hashCode() {
        return value;
    }

    public String toString() {
        return value + "";
    }

    public int getValue() {
        return value;
    }

    public int compareTo(Int o) {
        int i = o.getValue();
        return (value > i) ? 1 : ((value < i) ? -1 : 0);
    }

    public int compareTo(soya.lang.Float o) {
        double v = o.getValue();
        return (value > v) ? 1 : ((value < v) ? -1 : 0);
    }

    public int compareTo(Integer o) {
        int i = o.intValue();
        return (value > i) ? 1 : ((value < i) ? -1 : 0);
    }

    public int compareTo(java.lang.Long o) {
        long l = o.longValue();
        return (value > l) ? 1 : ((value < l) ? -1 : 0);
    }

    public int compareTo(java.lang.Float o) {
        double v = o.floatValue();
        return (value > v) ? 1 : ((value < v) ? -1 : 0);
    }

    public int compareTo(Double o) {
        double v = o.doubleValue();
        return (value > v) ? 1 : ((value < v) ? -1 : 0);
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Int) {
            return compareTo((Int) o);
        }
        else if (o instanceof Integer) {
            return compareTo(((Integer) o).intValue());
        }
        else if (o instanceof soya.lang.Float) {
            return compareTo((soya.lang.Float) o);
        }
        else if (o instanceof Double) {
            return compareTo((Double) o);
        }
        else if (o instanceof java.lang.Float) {
            return compareTo((java.lang.Float) o);
        }
        return -1;
    }

    public boolean greaterThan(Integer i) {
        return value > i;
    }

    public boolean greaterThan(java.lang.Float f) {
        return value > f;
    }

    public boolean greaterThan(Int i) {
        return value > i.getValue();
    }

    public boolean greaterThan(soya.lang.Float f) {
        return value > f.getValue();
    }

    public boolean greaterThan(Double d) {
        return value > d;
    }

    public boolean lessThan(Integer i) {
        return value < i;
    }

    public boolean lessThan(java.lang.Float f) {
        return value < f;
    }

    public boolean lessThan(Int i) {
        return value < i.getValue();
    }

    public boolean lessThan(soya.lang.Float f) {
        return value < f.getValue();
    }

    public boolean lessThan(Double d) {
        return value < d;
    }

    public boolean greaterEquals(Integer i) {
        return value >= i;
    }

    public boolean greaterEquals(java.lang.Float f) {
        return value >= f;
    }

    public boolean greaterEquals(Int i) {
        return value >= i.getValue();
    }

    public boolean greaterEquals(soya.lang.Float f) {
        return value >= f.getValue();
    }

    public boolean greaterEquals(Double d) {
        return value >= d;
    }

    public boolean lessEquals(Integer i) {
        return value <= i;
    }

    public boolean lessEquals(java.lang.Float f) {
        return value <= f;
    }

    public boolean lessEquals(Int i) {
        return value <= i.getValue();
    }

    public boolean lessEquals(soya.lang.Float f) {
        return value <= f.getValue();
    }

    public boolean lessEquals(Double d) {
        return value <= d;
    }

    public int plus(int i) {
        return value + i;
    }


    public float plus(float f) {
        return value + f;
    }

    public double plus(double f) {
        return value + f;
    }

    public String plus(String s) {
        return value + s;
    }

    public int minus(int i) {
        return value - i;
    }

    public float minus(float f) {
        return value - f;
    }

    public double minus(double d) {
        return value - d;
    }


    public int multi(int i) {
        return value * i;
    }

    public float multi(float f) {
        BigDecimal b1 = new BigDecimal(value);
        BigDecimal b2 = new BigDecimal(java.lang.Float.toString(f));
        return b1.multiply(b2).intValue();

    }

    public double multi(double d) {
        BigDecimal b1 = new BigDecimal(value);
        BigDecimal b2 = new BigDecimal(Double.toString(d));
        return b1.multiply(b2).doubleValue();

    }
    
    public double div(int i) {
        return (value * 1.0) / i;
    }

    public double div(float f) {
        BigDecimal b1 = new BigDecimal(value);
        BigDecimal b2 = new BigDecimal(java.lang.Float.toString(f));
        return b1.divide(b2).doubleValue();

    }

    public double div(double d) {
        BigDecimal b1 = new BigDecimal(value);
        BigDecimal b2 = new BigDecimal(Double.toString(d));
        return b1.divide(b2).doubleValue();

    }

    public double pow(int i) {
        return Math.pow(value, i);
    }

    public double pow(float f) {
        BigDecimal b = new BigDecimal(java.lang.Float.toString(f));
        return Math.pow(value, b.doubleValue());
    }

    public double pow(double d) {
        BigDecimal b = new BigDecimal(Double.toString(d));
        return Math.pow(value, b.doubleValue());
    }

    public char toChar() {
        return (char) value;
    }

    public Int increment() {
    	return new Int(value + 1);
    }

    public boolean isMatch(Object obj) {
        return this.equals(obj);
    }

    public int xor(int i) {
        return value ^ i;
    }

    public int shiftLeft(int i) {
        return value << i;
    }

    public int shiftRight(int i) {
        return value >> i;
    }

    public int bitShiftLeft(int i) {
        return value << i;
    }

    public int bitShiftRight(int i) {
        return value >> i;
    }

    public int bitAnd(int i) {
        return value & i;
    }

    public int bitOr(int i) {
        return value | i;
    }

}
