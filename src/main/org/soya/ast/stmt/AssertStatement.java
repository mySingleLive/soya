package org.soya.ast.stmt;

import org.soya.ast.expr.Expression;

/**
 * @author: Jun Gong
 */
public class AssertStatement extends Statement {

    private Expression argument1;
    private Expression argument2;

    public AssertStatement(Expression argument) {
        this(argument, null);
    }

    public AssertStatement(Expression argument1, Expression argument2) {
        this.argument1 = argument1;
        this.argument2 = argument2;
    }

    public Expression getArgument1() {
        return argument1;
    }

    public void setArgument1(Expression argument1) {
        this.argument1 = argument1;
    }

    public Expression getArgument2() {
        return argument2;
    }

    public void setArgument2(Expression argument2) {
        this.argument2 = argument2;
    }
}
