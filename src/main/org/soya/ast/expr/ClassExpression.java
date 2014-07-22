package org.soya.ast.expr;

import org.soya.ast.ClassNode;

/**
 * @author: Jun Gong
 */
public class ClassExpression extends Expression {
    private ClassNode classNode;

    public ClassExpression(ClassNode classNode) {
        this.classNode = classNode;
    }

    public ClassNode getClassNode() {
        return classNode;
    }

    public void setClassNode(ClassNode classNode) {
        this.classNode = classNode;
    }

    @Override
    public String toString() {
        return "[get-class " + classNode.toString() + "]";
    }
}
