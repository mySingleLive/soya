package org.soya.ast.expr;


/**
 * @author: Jun Gong
 */
public class MatchExpression extends Expression {
    private Expression expression;
    private org.soya.ast.MatchBlock block;

    public MatchExpression(Expression expression, org.soya.ast.MatchBlock block) {
        this.expression = expression;
        this.block = block;
    }

    public Expression getExpression() {
        return expression;
    }

    public org.soya.ast.MatchBlock getBlock() {
        return block;
    }

    public String toString() {
        return "[match expresion: " + expression + " block: " + block + "]";
    }
}
