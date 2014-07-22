package org.soya.ast.expr;

import org.objectweb.asm.Label;
import org.soya.ast.ClassNode;

/**
 * @author: Jun Gong
 */
public abstract class DotExpression extends Expression {
    protected DotExpression parentExpression;
    protected boolean checkNull = false;
    protected boolean isStatic = false;
    protected boolean visitEach = false;
    protected ClassNode ownerType;
    protected Label nullLabel;
    protected Label endLabel;

    public DotExpression getParentExpression() {
        return parentExpression;
    }

    public void setParentExpression(DotExpression parentExpression) {
        this.parentExpression = parentExpression;
    }

    public boolean isCheckNull() {
        return checkNull;
    }

    public void setCheckNull(boolean checkNull) {
        this.checkNull = checkNull;
    }

    public Label getNullLabel() {
        return nullLabel;
    }

    public void setNullLabel(Label nullLabel) {
        this.nullLabel = nullLabel;
    }

    public Label getEndLabel() {
        return endLabel;
    }

    public void setEndLabel(Label endLabel) {
        this.endLabel = endLabel;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    public boolean isVisitEach() {
        return visitEach;
    }

    public void setVisitEach(boolean visitEach) {
        this.visitEach = visitEach;
    }

    public String getExpressionName() {
        return getDotName();
    }

    public abstract String getDotName();

    public ClassNode getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(ClassNode ownerType) {
        this.ownerType = ownerType;
    }

    public DotExpression getLastParentExpression() {
        if (parentExpression == null) {
            return this;
        }
        return parentExpression.getLastParentExpression();
    }

    public Label getLastParentEndLabel() {
        return getLastParentExpression().getEndLabel();
    }

    public Label getLastParentNullLabel() {
        return getLastParentExpression().getNullLabel();
    }

}
