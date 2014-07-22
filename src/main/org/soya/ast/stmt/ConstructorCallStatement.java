package org.soya.ast.stmt;

import org.soya.ast.expr.ArgumentListExpression;

/**
 * @author: Jun Gong
 */
public class ConstructorCallStatement extends Statement {
    private boolean isSuper;
    private ArgumentListExpression arguments;

    public ConstructorCallStatement(boolean isSuper, ArgumentListExpression arguments) {
        this.isSuper = isSuper;
        this.arguments = arguments;
    }

    public boolean isSuper() {
        return isSuper;
    }

    public void setSuper(boolean aSuper) {
        isSuper = aSuper;
    }

    public ArgumentListExpression getArguments() {
        return arguments;
    }

    public void setArguments(ArgumentListExpression arguments) {
        this.arguments = arguments;
    }

    public String toString() {
        return "[super contructor call: " + arguments + "]";
    }
}
