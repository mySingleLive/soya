package org.soya.ast.expr;

/**
 * @author: Jun Gong
 */
public class ReferenceExpression extends DotExpression {
    private Expression objExpression;
    private Expression nameExpression;

    public ReferenceExpression(Expression objExpression, Expression nameExpression) {
        this.objExpression = objExpression;
        this.nameExpression = nameExpression;
    }

    public Expression getNameExpression() {
        return nameExpression;
    }

    public void setNameExpression(Expression nameExpression) {
        this.nameExpression = nameExpression;
    }

    public Expression getObjExpression() {
        return objExpression;
    }

    public void setObjExpression(Expression objExpression) {
        this.objExpression = objExpression;
    }

    public String getDotName() {
        String left = null;
        if (objExpression != null && objExpression instanceof DotExpression) {
            left = ((DotExpression) objExpression).getDotName();
        }
        else if (objExpression instanceof VariableExpression) {
            left = ((VariableExpression) objExpression).getVariableName();
        }
        else {
            left = objExpression.toString();
        }

        String right = null;
        if (nameExpression != null && nameExpression instanceof DotExpression) {
            right = ((DotExpression) nameExpression).getDotName();
        }
        else if (nameExpression instanceof VariableExpression) {
            right = ((VariableExpression) nameExpression).getVariableName();
        }
        else {
            right = nameExpression.toString();
        }
        StringBuffer buffer = new StringBuffer();
        if (left != null) {
            buffer.append(left);
            buffer.append(".");
        }
        if (right != null) {
            buffer.append(right);
        }
        return buffer.toString();
    }

    public String toString() {
        return "[ref obj:" + objExpression + ", name: " + nameExpression + "]";
    }

}
