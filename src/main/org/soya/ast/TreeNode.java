package org.soya.ast;

import antlr.collections.AST;

/**
 * @author: Jun Gong
 */
public class TreeNode {
    private int line;
    private int column;
    private int lastLine;
    private int lastColumn;

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

    public int getLastLine() {
        return lastLine;
    }

    public void setLastLine(int lastLine) {
        this.lastLine = lastLine;
    }

    public int getLastColumn() {
        return lastColumn;
    }

    public void setLastColumn(int lastColumn) {
        this.lastColumn = lastColumn;
    }

    public void config(AST cst) {
        setLine(cst.getLine());
        setColumn(cst.getColumn());
        if (cst instanceof SoyaCST) {
            setLastLine(((SoyaCST) cst).getEndLine());
            setLastColumn(((SoyaCST) cst).getEndColumn());

            if (lastLine == 0 && lastColumn == 0) {
                String text = cst.getText();
                int len = text.length();
                int lines = 0;
                int col = getColumn();
                for (int i = 0; i < len; i++) {
                    char c = text.charAt(i);
                    if (c == '\n') {
                        lines++;
                        col = 0;
                    }
                    else {
                        col++;
                    }
                }
                lines += getLine();
                setLastLine(lines);
                setLastColumn(col);
            }
        }
    }
}
