package soya.lang;

import java.lang.*;
import java.lang.Double;
import java.math.BigDecimal;

/**
 * Float, equals to java.lang.Float and java.lang.Double on JVM.
 * @author: Jun Gong
 */
public class Float extends AbstractObject implements Comparable {

    private double value;

    public Float(float value) {
        this.value = Double.valueOf(java.lang.Float.toString(value));
    }

    public Float(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Int) {
            return value == ((Int) obj).getValue();
        }
        if (obj instanceof Integer) {
            return value == ((Integer) obj).intValue();
        }
        if (obj instanceof Float) {
            return value == ((Float) obj).getValue();
        }
        if (obj instanceof java.lang.Float) {
            return value == ((java.lang.Float) obj).floatValue();
        }
        if (obj instanceof java.lang.Double) {
            return value == ((Double) obj).doubleValue();
        }
        return false;
    }

    public String toString() {
        return java.lang.Double.toString(value);
    }

    public double plus(int i) {
        return value + i;
    }

    public double plus(float f) {
        if (value < java.lang.Float.MAX_VALUE - f) {
            return Double.valueOf(Double.toString((float) (value + f)));
        }
        return value + f;
    }

    public double plus(double d) {
        return value + d;
    }

    public String plus(String s) {
        return value + s;
    }

    public double minus(int i) {
        BigDecimal b1 = new BigDecimal(Double.toString(value));
        BigDecimal b2 = new BigDecimal(i);
        return b1.subtract(b2).doubleValue();
    }

    public double minus(float f) {
        BigDecimal b1 = new BigDecimal(Double.toString(value));
        BigDecimal b2 = new BigDecimal(java.lang.Float.toString(f));
        return b1.subtract(b2).doubleValue();
    }

    public double minus(double d) {
        BigDecimal b1 = new BigDecimal(Double.toString(value));
        BigDecimal b2 = new BigDecimal(Double.toString(d));
        return b1.subtract(b2).doubleValue();
    }

    public double multi(int i) {
        BigDecimal b1 = new BigDecimal(Double.toString(value));
        BigDecimal b2 = new BigDecimal(i);
        return b1.multiply(b2).doubleValue();
    }

    public double multi(float f) {
        BigDecimal b1 = new BigDecimal(Double.toString(value));
        BigDecimal b2 = new BigDecimal(java.lang.Float.toString(f));
        return b1.multiply(b2).doubleValue();
    }

    public double multi(double d) {
        BigDecimal b1 = new BigDecimal(Double.toString(value));
        BigDecimal b2 = new BigDecimal(Double.toString(d));
        return b1.multiply(b2).doubleValue();
    }

    public double div(int i) {
        BigDecimal b1 = new BigDecimal(Double.toString(value));
        BigDecimal b2 = new BigDecimal(i);
        return b1.divide(b2).doubleValue();
    }

    public double div(float f) {
        BigDecimal b1 = new BigDecimal(Double.toString(value));
        BigDecimal b2 = new BigDecimal(java.lang.Float.toString(f));
        return b1.divide(b2).doubleValue();
    }

    public double div(double d) {
        BigDecimal b1 = new BigDecimal(Double.toString(value));
        BigDecimal b2 = new BigDecimal(Double.toString(d));
        return b1.divide(b2).doubleValue();
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Int) {
            int i = ((Int) o).getValue();
            return (value > i) ? 1 : ((value < i) ? -1 : 0);
        }
        else if (o instanceof Integer) {
            int i = ((Integer) o).intValue();
            return (value > i) ? 1 : ((value < i) ? -1 : 0);
        }
        else if (o instanceof Float) {
            double v = ((Float) o).getValue();
            return (value > v) ? 1 : ((value < v) ? -1 : 0);
        }
        else if (o instanceof java.lang.Float) {
            float v = ((java.lang.Float) o).floatValue();
            return (value > v) ? 1 : ((value < v) ? -1 : 0);
        }
        else if (o instanceof Double) {
            double v = ((Double) o).doubleValue();
            return (value > v) ? 1 : ((value < v) ? -1 : 0);
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

    public boolean greaterThan(Float f) {
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

    public boolean lessThan(Float f) {
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

    public boolean greaterEquals(Float f) {
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

    public boolean lessEquals(Float f) {
        return value <= f.getValue();
    }

    public boolean lessEquals(Double d) {
        return value <= d;
    }

    public int intValue() {
        return (int) getValue();
    }

    public long longValue() {
        return (long) getValue();
    }

    public float floatValue() {
        return (float) getValue();
    }

    public double doubleValue() {
        return getValue();
    }
}
