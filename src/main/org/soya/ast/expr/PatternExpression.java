package org.soya.ast.expr;

import org.soya.ast.ClassNode;

/**
 * @author: Jun Gong
 */
public class PatternExpression extends Expression {
    private ClassNode classNode;
    private String alias;

    public PatternExpression(ClassNode classNode) {
        this(classNode, null);
    }

    public PatternExpression(ClassNode classNode, String alias) {
        this.classNode = classNode;
        this.alias = alias;
    }

    public ClassNode getClassNode() {
        return classNode;
    }

    public void setClassNode(ClassNode classNode) {
        this.classNode = classNode;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String toString() {
        return "[pattern class: " + classNode
                + (alias != null ? " alias: " + alias : "")
                + "]";
    }
}
