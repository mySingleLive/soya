package org.soya.ast;

/**
 * @author: Jun Gong
 */
public class PackageNode extends TreeNode {
    private final String name;

    public PackageNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
