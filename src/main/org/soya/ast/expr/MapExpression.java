package org.soya.ast.expr;

import org.soya.ast.ClassNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Jun Gong
 */
public class MapExpression extends Expression {

    private List<MapEntryExpression> entries;

    public MapExpression() {
        this(new ArrayList<MapEntryExpression>());
    }

    public MapExpression(List<MapEntryExpression> entries) {
        this.entries = entries;
        this.setType(ClassNode.MAP);
    }

    public List<MapEntryExpression> getEntries() {
        return entries;
    }

    public void setEntries(List<MapEntryExpression> entries) {
        this.entries = entries;
    }

    public void addEntry(MapEntryExpression entry) {
        entries.add(entry);
    }

    public String toString() {
        return "[map: " + entries + "]";
    }
}
