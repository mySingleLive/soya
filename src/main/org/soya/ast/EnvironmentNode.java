package org.soya.ast;

import org.soya.ast.stmt.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Jun Gong
 */
public class EnvironmentNode extends TreeNode {
    private final String name;
    private Statement body;
    private PackageNode packageNode;
    private List<ImportNode> imports = new ArrayList<ImportNode>();
    private List<ClassNode> classes = new ArrayList<ClassNode>();
    private List<MethodNode> methods = new ArrayList<MethodNode>();

    public EnvironmentNode(String name, Statement body) {
        this.name = name;
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public Statement getBody() {
        return body;
    }

    public void setBody(Statement body) {
        this.body = body;
    }

    public List<ImportNode> getImports() {
        return imports;
    }

    public void setImports(List<ImportNode> imports) {
        this.imports = imports;
    }

    public List<ClassNode> getClasses() {
        return classes;
    }

    public void setClasses(List<ClassNode> classes) {
        this.classes = classes;
    }

    public List<MethodNode> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodNode> methods) {
        this.methods = methods;
    }
}
