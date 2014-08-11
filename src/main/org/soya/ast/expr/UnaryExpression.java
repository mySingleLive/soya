package org.soya.ast.expr;

import antlr.Token;

/**
 * @author: Jun Gong
 */
public class UnaryExpression extends Expression {
    private Token operator;
    private Expression expression;

    public UnaryExpression(Token operator, Expression expression) {
        this.operator = operator;
        this.expression = expression;
    }

    public Token getOperator() {
        return operator;
    }

    public void setOperator(Token operator) {
        this.operator = operator;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }


    @Override
    public boolean findVarReference(String varName) {
        return expression.findVarReference(varName);
    }

    public String toString() {
        return operator.getText() + " " + expression;
    }
}
