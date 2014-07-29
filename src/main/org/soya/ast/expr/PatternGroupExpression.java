package org.soya.ast.expr;

import java.util.List;

/**
 * Created by Gonjun on 7/29/14.
 */
public class PatternGroupExpression extends Expression {

    private List<Expression> expressions;

    public PatternGroupExpression(List<Expression> expressions) {
        this.expressions = expressions;
    }

    public List<Expression> getExpressions() {
        return expressions;
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
