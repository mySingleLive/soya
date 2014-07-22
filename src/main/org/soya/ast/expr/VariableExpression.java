package org.soya.ast.expr;

/**
 * @author: Jun Gong
 */
public class VariableExpression extends Expression {
	
	public static final VariableExpression THIS = new VariableExpression("this");
	public static final VariableExpression INNER_THIS = new VariableExpression("this");
	
    private final String variableName;

    public VariableExpression(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableName() {
        return variableName;
    }

    public String getExpressionName() {
        return getVariableName();
    }

    @Override
    public boolean findVarReference(String varName) {
        return variableName.equals(varName);
    }

    public String toString() {
        return "[variable " + variableName + "]";
    }
}
