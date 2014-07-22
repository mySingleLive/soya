package org.soya.ast.expr;

/**
 * @author: Jun Gong
 */
public class NotExpression extends Expression {

    private Expression expression;

    public NotExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public boolean isConstant() {
        return expression.isConstant();
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }
}
