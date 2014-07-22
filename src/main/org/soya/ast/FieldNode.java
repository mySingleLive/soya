package org.soya.ast;

import org.soya.ast.expr.Expression;

/**
 * @author: Jun Gong
 */
public class FieldNode extends AnnotatedNode {
    private String name;
    private Expression initValue;

    public FieldNode(int modifier, String name, Expression initValue) {
        this.modifier = modifier;
        this.name = name;
        this.initValue = initValue;
    }

    public String getName() {
        return name;
    }

    public void setInitValue(Expression initValue) {
        this.initValue = initValue;
    }

    public Expression getInitValue() {
        return initValue;
    }

    public String toString() {
        return "[field name: " + name + " initValue: " + initValue + "]";
    }
}
