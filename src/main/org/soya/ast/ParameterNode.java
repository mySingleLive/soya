package org.soya.ast;

import org.soya.ast.expr.Expression;

/**
 * @author: Jun Gong
 */
public class ParameterNode extends TreeNode {

    public static ParameterNode[] EMPTY_PARAMETER_LIST = new ParameterNode[] {};

    private final ClassNode type;
    private final String name;
    private Expression expression;
    private final boolean consntat;

    public ParameterNode(ClassNode type, String name, Expression expression, boolean consntat) {
        this.type = type;
        this.name = name;
        this.expression = expression;
        this.consntat = consntat;
    }

    public ParameterNode(ClassNode type, String name) {
        this(type, name, null, false);
    }


    public ClassNode getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public boolean isConsntat() {
        return consntat;
    }

    public String toString() {
        return "[param: " + (consntat ? expression : name) + "]";
    }
}
