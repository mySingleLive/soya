package org.soya.ast.expr;

/**
 * @author: Jun Gong
 */
public class IndexExpression extends Expression {
    private final Expression indexee;
    private ArgumentListExpression arguments;

    public IndexExpression(Expression indexee, ArgumentListExpression arguments) {
        this.indexee = indexee;
        this.arguments = arguments;
    }

    public Expression getIndexee() {
        return indexee;
    }

    public ArgumentListExpression getArguments() {
        return arguments;
    }

    public boolean findVarReference(String varName) {
        return indexee.findVarReference(varName) || arguments.findVarReference(varName);
    }

    public String toString() {
        return "[index obj: " + indexee + ", " + arguments + "]";
    }
}
