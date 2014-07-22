package org.soya.ast.expr;

import org.soya.ast.ClassNode;

/**
 * @author: Jun Gong
 */
public class PropertyExpression extends DotExpression {
    private Expression obj;
    private Expression property;
    private boolean isReference = false;

    public PropertyExpression(Expression obj, Expression property) {
        this.obj = obj;
        this.property = property;
    }
    
    public PropertyExpression(ClassNode ownerType, Expression property) {
        this.ownerType = ownerType;
        this.property = property;
        this.isStatic = true;
    }

    public Expression getObj() {
        return obj;
    }

    public Expression getProperty() {
        return property;
    }

    public String getDotName() {
        String left = null;
        if (obj != null && obj instanceof DotExpression) {
            left = ((DotExpression) obj).getDotName();
        }
        else if (obj instanceof Expression) {
            left = obj.getExpressionName();
        }
        else {
            left = obj.toString();
        }

        String right = null;
        if (property != null && property instanceof DotExpression) {
            right = ((DotExpression) property).getDotName();
        }
        else if (property instanceof Expression) {
            right = property.getExpressionName();
        }
        else {
            right = property.toString();
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
        return "[prop obj: " + obj + " name: " + property + "]";
    }

    public boolean findVarReference(String varName) {
        return obj.findVarReference(varName) || property.findVarReference(varName);
    }

    public boolean isReference() {
        return isReference;
    }

    public void setReference(boolean reference) {
        isReference = reference;
    }

}
