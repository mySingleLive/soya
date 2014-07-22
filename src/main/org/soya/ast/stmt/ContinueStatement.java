package org.soya.ast.stmt;

import org.soya.ast.expr.Expression;

/**
 * @author: Jun Gong
 */
public class ContinueStatement extends Statement {

    private Expression expression;

    public ContinueStatement(Expression expression) {
        this.expression = expression;
    }
}
