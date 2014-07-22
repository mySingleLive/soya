package org.soya.ast.expr;

/**
 * @author: Jun Gong
 */
public class MarkupExpression extends Expression {
    private String text;


    public MarkupExpression(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String toString() {
        return text;
    }
}
