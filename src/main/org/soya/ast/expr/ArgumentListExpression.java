package org.soya.ast.expr;

import org.soya.ast.ClassNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: Jun Gong
 */
public class ArgumentListExpression extends Expression {

    private List<Expression> expressions;
    private List<MatchVarDefExpression> matchExpressions;

    public ArgumentListExpression(Expression... args) {
        this();
        for (Expression arg : args) {
            addArgument(arg);
        }
    }


    public ArgumentListExpression() {
        this.expressions = new ArrayList<Expression>();
        this.matchExpressions = new LinkedList<MatchVarDefExpression>();
    }

    public void addArgument(Expression expression) {
        this.expressions.add(expression);
        if (expression instanceof MatchVarDefExpression) {
            matchExpressions.add((MatchVarDefExpression) expression);
        }
    }

    public Expression getArgument(int i) {
        return this.expressions.get(i);
    }

    public ClassNode[] getParameterTypes() {
        int len = expressions.size();
        ClassNode[] results = new ClassNode[len];
        for (int i = 0; i < len; i++) {
            results[i] = expressions.get(i).getType();
        }
        return results;
    }

    public List<MatchVarDefExpression> getMatchExpressions() {
        return matchExpressions;
    }

    public boolean isHaveMatchVarDef() {
        return !matchExpressions.isEmpty();
    }

    public boolean findVarReference(String varName) {
        for (Expression expr : expressions) {
            if (expr.findVarReference(varName)) {
                return true;
            }
        }
        return false;
    }

    public boolean isHaveMatchVarInExpression(Expression expr) {
        for (MatchVarDefExpression var : matchExpressions) {
            if (expr.findVarReference(var.getName())) {
                return true;
            }
        }
        return false;
    }

    public Object[] toArray() {
        return expressions.toArray();
    }

    public String toString() {
        return "args: " + expressions;
    }

    public List<Expression> getExpressions() {
        return expressions;
    }
}
