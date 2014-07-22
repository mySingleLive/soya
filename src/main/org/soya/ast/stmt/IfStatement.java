package org.soya.ast.stmt;

import org.soya.ast.BlockNode;
import org.soya.ast.expr.Expression;

/**
 * @author: Jun Gong
 */
public class IfStatement extends Statement {

    private Expression condtion;

    private BlockNode ifBody;

    private BlockNode elseBody;

    public IfStatement(Expression condtion, BlockNode ifBody, BlockNode elseBody) {
        this.condtion = condtion;
        this.ifBody = ifBody;
        this.elseBody = elseBody;
    }

    public Expression getCondtion() {
        return condtion;
    }

    public void setCondtion(Expression condtion) {
        this.condtion = condtion;
    }

    public BlockNode getIfBody() {
        return ifBody;
    }

    public void setIfBody(BlockNode ifBody) {
        this.ifBody = ifBody;
    }

    public BlockNode getElseBody() {
        return elseBody;
    }

    public void setElseBody(BlockNode elseBody) {
        this.elseBody = elseBody;
    }

    public String toString() {
        return "[if condition: " + condtion + " body: " + ifBody +
                (elseBody != null ? (" else: " + elseBody) : "") + "]";
    }
}
