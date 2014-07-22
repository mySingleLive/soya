package org.soya.ast.expr;

import org.soya.ast.ClassNode;

/**
 * @author: Jun Gong
 */
public class NewExpression extends Expression {
    private ArgumentListExpression arguments;

    public NewExpression(ClassNode type, ArgumentListExpression arguments) {
        super.setType(type);
        this.arguments = arguments;
    }

    public ArgumentListExpression getArguments() {
        return arguments;
    }

    public boolean isConstant() {
        return true;
    }

    public String toString() {
        return "[new class:" + getType() + " " + arguments + "]";
    }
}
