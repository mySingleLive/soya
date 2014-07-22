package org.soya.ast.stmt;

import org.soya.ast.expr.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Jun Gong
 */
public class MatchStatment {

    private Expression expression;

    private List<MatchItemStatement> items = new ArrayList<MatchItemStatement>();

    public MatchStatment(Expression expression, List<MatchItemStatement> items) {
        this.expression = expression;
        this.items = items;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public List<MatchItemStatement> getItems() {
        return items;
    }

    public void setItems(List<MatchItemStatement> items) {
        this.items = items;
    }
}
