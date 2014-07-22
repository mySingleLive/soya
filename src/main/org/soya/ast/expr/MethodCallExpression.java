package org.soya.ast.expr;

import org.soya.ast.ClassNode;

/**
 * @author: Jun Gong
 */
public class MethodCallExpression extends DotExpression {

    private Expression obj;
    private Expression method;
    private ArgumentListExpression arguments;

    public MethodCallExpression(Expression obj, Expression method, ArgumentListExpression arguments) {
        this.obj = obj;
        this.method = method;
        this.arguments = arguments;
    }

    public MethodCallExpression(ClassNode ownerType, Expression method, ArgumentListExpression arguments) {
        this.ownerType = ownerType;
        this.method = method;
        this.arguments = arguments;
        this.isStatic = true;
    }

    public Expression getObj() {
        return obj;
    }

    public void setObj(Expression obj) {
        this.obj = obj;
    }

    public Expression getMethod() {
        return method;
    }

    public void setMethod(Expression method) {
        this.method = method;
    }

    public ArgumentListExpression getArguments() {
        return arguments;
    }

    public void setArguments(ArgumentListExpression arguments) {
        this.arguments = arguments;
    }

    @Override
    public boolean findVarReference(String varName) {
        return obj.findVarReference(varName) || arguments.findVarReference(varName);
    }

    public String getDotName() {
        String left = null;
        if (obj != null && obj instanceof DotExpression) {
            left = ((DotExpression) obj).getDotName();
        }
        else if (obj instanceof VariableExpression) {
            left = ((VariableExpression) obj).getVariableName();
        }
        else {
            left = obj.toString();
        }

        String right = null;
        if (method != null && method instanceof DotExpression) {
            right = ((DotExpression) method).getDotName();
        }
        else if (method instanceof VariableExpression) {
            right = ((VariableExpression) method).getVariableName();
        }
        else {
            right = method.toString();
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
        return "[invoke obj: " + obj + " method: " + method + " " + arguments + "]";
    }
}
