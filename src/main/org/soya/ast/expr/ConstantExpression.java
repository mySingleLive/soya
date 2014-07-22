package org.soya.ast.expr;

/**
 * @author: Jun Gong
 */
public class ConstantExpression extends Expression {

    private Object value;

    public ConstantExpression(Object value) {
        this.value = value;
    }

    public boolean isConstant() {
        return true;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String toString() {
        return value == null ? "null" : value.toString();
    }
}
