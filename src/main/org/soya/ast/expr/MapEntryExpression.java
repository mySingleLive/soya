package org.soya.ast.expr;

/**
 * @author: Jun Gong
 */
public class MapEntryExpression extends Expression {

    private Expression key;

    private Expression value;

    public MapEntryExpression(Expression key, Expression value) {
        this.key = key;
        this.value = value;
        this.setType(value.getType());
    }

    public Expression getKey() {
        return key;
    }

    public void setKey(Expression key) {
        this.key = key;
    }

    public boolean isConstant() {
        return value.isConstant();
    }

    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
        this.value = value;
    }

    public String toString() {
        return "[map key: " + key + " value: " + value + "]";
    }
}
