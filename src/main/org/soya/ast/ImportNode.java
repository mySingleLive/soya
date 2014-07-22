package org.soya.ast;

/**
 * @author: Jun Gong
 */
public class ImportNode extends TreeNode {
    private final String packageName;
    private final ClassNode type;
    private final String alias;

    public ImportNode(String packageName) {
        this.packageName = packageName;
        this.type = null;
        this.alias = null;
    }

    public ImportNode(ClassNode type, String alias) {
        this.packageName = null;
        this.type = type;
        this.alias = alias;
    }

    public ImportNode(ClassNode type) {
        this(type, null);
    }

    public ClassNode getType() {
        return type;
    }

    public String getAlias() {
        return alias;
    }

    public String getPackageName() {
        return packageName;
    }
}
