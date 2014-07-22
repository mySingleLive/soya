package org.soya.ast.expr;

import org.soya.ast.BlockNode;
import org.soya.ast.ParameterNode;

import java.util.List;

/**
 * @author: Jun Gong
 */
public class ClosureExpression extends Expression {
    private List<ParameterNode> parameters;

    private BlockNode body;
    private boolean callOnAssign = false;

    public ClosureExpression(List<ParameterNode> parameters, BlockNode body) {
        this(parameters, body, false);
    }

    public ClosureExpression(List<ParameterNode> parameters, BlockNode body, boolean callOnAssign) {
        this.parameters = parameters;
        this.body = body;
        this.callOnAssign = callOnAssign;
    }

    public List<ParameterNode> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterNode> parameters) {
        this.parameters = parameters;
    }

    public boolean isCallOnAssign() {
        return callOnAssign;
    }

    public void setCallOnAssign(boolean callOnAssign) {
        this.callOnAssign = callOnAssign;
    }

    public BlockNode getBody() {
        return body;
    }

    public void setBody(BlockNode body) {
        this.body = body;
    }
    
    public String toString() {
    	return "[closure " + body + "]";
    }
}
