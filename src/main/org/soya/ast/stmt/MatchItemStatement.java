package org.soya.ast.stmt;

import org.soya.ast.expr.Expression;

/**
 * @author: Jun Gong
 */
public class MatchItemStatement extends Statement {

    private Expression condition;

    private Statement code;

    public MatchItemStatement(Expression condition, Statement code) {
        this.condition = condition;
        this.code = code;
    }

    public MatchItemStatement(Statement code) {
        this(null, code);
    }

    public MatchItemStatement(Expression condition) {
        this(condition, null);
    }

    public Expression getCondition() {
        return condition;
    }

    public void setCondition(Expression condition) {
        this.condition = condition;
    }

    public Statement getCode() {
        return code;
    }

    public void setCode(Statement code) {
        this.code = code;
    }
}
