package org.soya.ast;

import antlr.CommonAST;
import antlr.Token;
import antlr.collections.AST;

/**
 * @author: Jun Gong
 */
public class SoyaCST extends CommonAST {
    private int line;
    private int column;
    private int endLine;
    private int endColumn;

    public SoyaCST() {
    }

    public SoyaCST(Token t) {
        super(t);
    }

    @Override
    public void initialize(Token t) {
        super.initialize(t);
        line = t.getLine();
        column = t.getColumn();
    }

    @Override
    public void initialize(AST ast) {
        super.initialize(ast);
        line = ast.getLine();
        column = ast.getColumn();
        if (ast instanceof SoyaCST) {
            SoyaCST past = (SoyaCST)ast;
            endLine = past.getEndLine();
            endColumn = past.getEndColumn();
        }
    }

    public void setFirst(Token tok) {
        setLine(tok.getLine());
        setColumn(tok.getColumn());
    }

    public void setFirst(AST ast) {
        setLine(ast.getLine());
        setColumn(ast.getColumn());
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getEndLine() {
        return endLine;
    }

    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }

    public int getEndColumn() {
        return endColumn;
    }

    public void setEndColumn(int endColumn) {
        this.endColumn = endColumn;
    }

}
