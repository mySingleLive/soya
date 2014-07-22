package org.soya.ast.expr;

import org.soya.ast.ClassNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Jun Gong
 */
public class ListExpression extends Expression {

    private List<Expression> items;

    public ListExpression(List<Expression> items) {
        this.items = items;
        this.setType(ClassNode.LIST);
    }

    public ListExpression() {
        this(null);
    }

    public boolean isConstant() {
        for (Expression item : items) {
            if (item.isConstant()) {
                return true;
            }
        }
        return false;
    }

    public void addExpression(Expression expression) {
        getItems().add(expression);
    }

    public List<Expression> getItems() {
        if (items == null) {
            items = new ArrayList<Expression>();
        }
        return items;
    }

    public void setItems(List<Expression> items) {
        this.items = items;
    }

    public String toString() {
        return "[list " + items.toString() + "]";
    }
}
