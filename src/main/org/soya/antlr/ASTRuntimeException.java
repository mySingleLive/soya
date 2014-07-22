package org.soya.antlr;

import antlr.collections.AST;

/**
 * @author: Jun Gong
 */
public class ASTRuntimeException extends RuntimeException {

    private final AST ast;

    public ASTRuntimeException(AST ast, String message) {
        super(message + description(ast));
        this.ast = ast;
    }

    protected static String description(AST node) {
        return (node != null) ? " at line: " + node.getLine() + " column: " + node.getColumn() : "";
    }

    public AST getAst() {
        return ast;
    }

    public int getLine() {
        return ast.getLine();
    }

    public int getColumn() {
        return ast.getColumn();
    }
}
