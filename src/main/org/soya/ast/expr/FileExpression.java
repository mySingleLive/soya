package org.soya.ast.expr;

import org.soya.ast.ClassNode;

import java.util.List;

/**
 * @author: Jun Gong
 */
public class FileExpression extends Expression {
    private List<Expression> parts;

    public FileExpression(List<Expression> parts) {
        setType(ClassNode.FILE);
        this.parts = parts;
    }

    public List<Expression> getParts() {
        return parts;
    }

    @Override
    public String toString() {
        return "[file: " + parts.toString() + "]";
    }
}
