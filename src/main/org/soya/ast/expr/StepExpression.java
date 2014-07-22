package org.soya.ast.expr;

/**
 * @author: Jun Gong
 */
public class StepExpression extends Expression {
    private Expression range;
    private Expression step;

    public StepExpression(Expression range, Expression step) {
        this.range = range;
        this.step = step;
    }

    public Expression getRange() {
        return range;
    }

    public void setRange(Expression range) {
        this.range = range;
    }

    public Expression getStep() {
        return step;
    }

    public void setStep(Expression step) {
        this.step = step;
    }

    public String toString() {
        return "[rang: " + range + " step: " + step + "]";
    }
}
