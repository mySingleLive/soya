package org.soya.ast.stmt;

import org.soya.ast.expr.Expression;

/**
 * @author: Jun Gong
 */
public class ReturnStatement extends Statement {
    private Expression expression;

    public ReturnStatement(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }
}
