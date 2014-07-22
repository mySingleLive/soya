package org.soya.ast.expr;

import java.util.List;

import org.soya.ast.ClassNode;

/**
 * @author: Jun Gong
 */
public class StringExpression extends Expression {
    private List<Expression> parts;

    public StringExpression(List<Expression> parts) {
    	setType(ClassNode.PSTRING);
        this.parts = parts;
    }

    public List<Expression> getParts() {
        return parts;
    }

    public String toString() {
        return "[String: " + parts + "]";
    }
}
