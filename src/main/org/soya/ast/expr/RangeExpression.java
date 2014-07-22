package org.soya.ast.expr;

/**
 * @author: Jun Gong
 */
public class RangeExpression extends Expression {

    private Expression from;
    private Expression to;
    private boolean includeFrom;
    private boolean includeTo;

    public RangeExpression(Expression from, Expression to, boolean includeFrom, boolean includeTo) {
        this.from = from;
        this.to = to;
        this.includeFrom = includeFrom;
        this.includeTo = includeTo;
    }

    public Expression getFrom() {
        return from;
    }

    public void setFrom(Expression from) {
        this.from = from;
    }

    public Expression getTo() {
        return to;
    }

    public void setTo(Expression to) {
        this.to = to;
    }

    public boolean isIncludeFrom() {
        return includeFrom;
    }

    public void setIncludeFrom(boolean includeFrom) {
        this.includeFrom = includeFrom;
    }

    public boolean isIncludeTo() {
        return includeTo;
    }

    public void setIncludeTo(boolean includeTo) {
        this.includeTo = includeTo;
    }

    public String toString() {
        return (includeFrom ? "[" : "(") + from + ".." +
                to + (includeTo ? "]" : ")");
    }
}
