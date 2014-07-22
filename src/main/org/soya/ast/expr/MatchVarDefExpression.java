package org.soya.ast.expr;

/**
 * @author: Jun Gong
 */
public class MatchVarDefExpression extends Expression {
    private String name;
    private Expression expression;

    public MatchVarDefExpression(String name, Expression expression) {
        this.name = name;
        this.expression = expression;
    }

    public String getName() {
        return name;
    }

    public Expression getExpression() {
        return expression;
    }

    public String toString() {
        return expression + " " + name;
    }
}
