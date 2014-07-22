package org.soya.ast.expr;

import org.soya.ast.ClassNode;
import org.soya.ast.TreeNode;

/**
 * @author: Jun Gong
 */
public abstract class Expression extends TreeNode {

    private ClassNode type = ClassNode.POBJECT;

    public ClassNode getType() {
        return type;
    }

    public void setType(ClassNode type) {
        this.type = type;
    }

    public boolean findVarReference(String varName) {
        return false;
    }

    public String getExpressionName() {
        return toString();
    }

    public String toString() {
        return "";
    }

    public boolean isConstant() {
        return false;
    }
}
