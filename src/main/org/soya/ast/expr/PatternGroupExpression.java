package org.soya.ast.expr;

import java.util.List;

/**
 * Created by Gonjun on 7/29/14.
 */
public class PatternGroupExpression extends Expression {

    private List<Expression> expressions;
    private boolean argument = false;
    private String alias;

    public PatternGroupExpression(List<Expression> expressions) {
        this.expressions = expressions;
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

    public boolean isArgument() {
        return argument;
    }

    public void setArgument(boolean argument) {
        this.argument = argument;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer("(");
        if (expressions != null) {
            for (int i = 0; i < expressions.size(); i++) {
                Expression expr = expressions.get(i);
                buffer.append(expr.toString());
                if (i < expressions.size() - 1) {
                    buffer.append(' ');
                }
            }
        }
        buffer.append(")");
        return buffer.toString();
    }
}
