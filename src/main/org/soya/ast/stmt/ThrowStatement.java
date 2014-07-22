package org.soya.ast.stmt;

import org.soya.ast.expr.Expression;

/**
 * @author: Jun Gong
 */
public class ThrowStatement extends Statement {
    private Expression expression;

    public ThrowStatement(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "[throw " + expression.toString() + "]";
    }
}
