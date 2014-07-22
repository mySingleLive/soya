package org.soya.ast;

import org.soya.ast.expr.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Jun Gong
 */
public class MatchBlock extends Expression {

    private List<MatchItem> items = new ArrayList<MatchItem>();
    private MatchItem elseItem;

    public MatchBlock() {
    }

    public void addMatchItem(MatchItem item) {
        items.add(item);
    }

    public List<MatchItem> getItems() {
        return items;
    }

    public void setItems(List<MatchItem> items) {
        this.items = items;
    }

    public MatchItem getElseItem() {
        return elseItem;
    }

    public void setElseItem(MatchItem elseItem) {
        this.elseItem = elseItem;
    }

    public String toString() {
        return items.toString();
    }
}
