package org.soya.antlr;

import antlr.Token;
import antlr.collections.AST;
import org.soya.ast.SoyaCST;
import org.soya.runtime.SoyaException;
import org.soya.codegen.ClassGeneratorException;

/**
 * @author: Jun Gong
 */
public class SyntaxException extends SoyaException {

    private String souceName;

    private int line;
    private int column;
    private int endLine;
    private int endColumn;

    public SyntaxException(String sourceName, String message, int line, int column, int endLine, int endColumn) {
        super(message);
        this.souceName = sourceName;
        this.line = line;
        this.column = column;
        this.endLine = endLine;
        this.endColumn = endColumn;
    }

    public SyntaxException(String sourceName, String message, int line, int column) {
        this(sourceName, message, line, column, line, column);
    }

    public SyntaxException(String sourceName, String message, Token token) {
        super(message);
        this.souceName = sourceName;
        this.line = token.getLine();
        this.column = token.getColumn();
        if (token instanceof SoyaToken) {
            SoyaToken ptoken = (SoyaToken)token;
            this.endLine = ptoken.getEndLine();
            this.endColumn = ptoken.getEndColumn();
        }
    }

    public SyntaxException(String sourceName, ClassGeneratorException exception) {
        this(sourceName, exception.getMessage(),
                exception.getLine(), exception.getColumn(), exception.getEndLine(), exception.getEndColumn());
    }

    public SyntaxException(String sourceName, String message, AST cst) {
        super(message);
        this.souceName = sourceName;
        this.line = cst.getLine();
        this.column = cst.getColumn();
        if (cst instanceof SoyaCST) {
            SoyaCST pcst = (SoyaCST)cst;
            this.endLine = pcst.getEndLine();
            this.endColumn = pcst.getEndColumn();
        }

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

    public String getMessage() {
        return "Syntax Error(" + line + "," + column + "): " + souceName + ":" + line + ": " + super.getMessage();
    }
}
