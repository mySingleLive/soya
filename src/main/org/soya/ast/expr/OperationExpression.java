package org.soya.ast.expr;

import static org.soya.antlr.parser.SoyaParserTokenTypes.*;

import antlr.Token;
import org.soya.ast.ClassNode;

/**
 * @author: Jun Gong
 */
public class OperationExpression extends Expression {
    private final Token operator;

    private Expression left;
    private Expression right;

    public OperationExpression(Token opreator, Expression left, Expression right) {
        this.operator = opreator;
        this.left = left;
        this.right = right;
    }

    public boolean isConstant() {
        boolean leftConstant = left.isConstant();
        boolean rightConstant = right.isConstant();
        return leftConstant && rightConstant;
    }

    @Override
    public int getLine() {
        return left.getLine();
    }

    @Override
    public int getColumn() {
        return left.getColumn();
    }

    @Override
    public int getLastLine() {
        return right.getLastLine();
    }

    @Override
    public int getLastColumn() {
        return right.getLastColumn();
    }

    public Token getOperator() {
        return operator;
    }

    public Expression getLeft() {
        return left;
    }

    public void setLeft(Expression left) {
        this.left = left;
    }

    public Expression getRight() {
        return right;
    }

    public void setRight(Expression right) {
        this.right = right;
    }

    @Override
    public boolean findVarReference(String varName) {
        return left.findVarReference(varName) || right.findVarReference(varName);
    }

    public ClassNode getType() {
        ClassNode leftType = left.getType();
        ClassNode rightType = right.getType();
        switch (operator.getType()) {
            case GT:
            case LT:
            case GE:
            case LE:
            case EQUAL:
            case NOT_EQUAL:
                return ClassNode.BOOLEAN;
            case ASSIGN:
            case PLUS_ASSIGN:
            case MINUS_ASSIGN:
            case STAR_ASSIGN:
            case DIV_ASSIGN:
                return getRight().getType();
        }
        return ClassNode.POBJECT;
    }

    public String toString() {
        return "[" + left.toString() + " " +
                operator.getText() + " " +
                right.toString() + "]";
    }
    
    public boolean isAssginment() {
        switch (operator.getType()) {
	        case ASSIGN:
	        case PLUS_ASSIGN:
	        case MINUS_ASSIGN:
	        case STAR_ASSIGN:
	        case DIV_ASSIGN:
	        	return true;
        }
    	return false;
    }
}
