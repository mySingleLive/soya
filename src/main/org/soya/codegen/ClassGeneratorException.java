package org.soya.codegen;

import org.soya.ast.TreeNode;
import soya.lang.SoyaRuntimeException;

/**
 * @author: Jun Gong
 */
public class ClassGeneratorException extends SoyaRuntimeException {

    private TreeNode node;

    public int getLine() {
        return node.getLine();
    }

    public int getColumn() {
        return node.getColumn();
    }

    public int getEndLine() {
        return node.getLastLine();
    }

    public int getEndColumn() {
        return node.getLastColumn();
    }

    public TreeNode getNode() {
        return node;
    }

    public ClassGeneratorException(String msg, TreeNode node) {
        super(msg);
        this.node = node;
    }
}
