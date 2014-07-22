package org.soya.ast.stmt;

import org.soya.ast.BlockNode;
import org.soya.ast.expr.Expression;
import org.soya.ast.expr.OperationExpression;

/**
 * @author: Jun Gong
 */
public class ForStatement extends Statement {

    private Expression initialExpression;
    private Expression conditionExpression;
    private Expression stepExpression;
    private OperationExpression inExpression;
    private BlockNode body;
    private final boolean forEach;

    public ForStatement(Expression initialExpression, Expression conditionExpression,
                        Expression stepExpression, BlockNode body) {
        this.initialExpression = initialExpression;
        this.conditionExpression = conditionExpression;
        this.stepExpression = stepExpression;
        this.body = body;
        this.forEach = false;
    }

    public ForStatement(OperationExpression inExpression, BlockNode body) {
        this.inExpression = inExpression;
        this.body = body;
        this.forEach = true;
    }

    public Expression getInitialExpression() {
        return initialExpression;
    }

    public void setInitialExpression(Expression initialExpression) {
        this.initialExpression = initialExpression;
    }

    public Expression getConditionExpression() {
        return conditionExpression;
    }

    public void setConditionExpression(Expression conditionExpression) {
        this.conditionExpression = conditionExpression;
    }

    public Expression getStepExpression() {
        return stepExpression;
    }

    public void setStepExpression(Expression stepExpression) {
        this.stepExpression = stepExpression;
    }

    public BlockNode getBody() {
        return body;
    }

    public void setBody(BlockNode body) {
        this.body = body;
    }

    public OperationExpression getInExpression() {
        return inExpression;
    }

    public void setInExpression(OperationExpression inExpression) {
        this.inExpression = inExpression;
    }

    public boolean isForEach() {
        return forEach;
    }

    public String toString() {
        if (forEach) {
            return "[for each: " + inExpression + " block: " + body + "]";
        }
        return "[for cond1: " + initialExpression +
                " cond2: " + conditionExpression +
                " cond3: " + stepExpression + " block: " + body +
                "]";
    }
}
