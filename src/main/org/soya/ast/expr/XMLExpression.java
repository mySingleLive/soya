package org.soya.ast.expr;

import java.util.List;

/**
 * @author: Jun Gong
 */
public class XMLExpression extends Expression {
    private String text;
    private Expression nodeName;
    private List<Expression> attributes;
    private List<Expression> children;

    public XMLExpression(String text) {
        this.text = text;
    }

    public Expression getNodeName() {
        return nodeName;
    }

    public void setNodeName(Expression nodeName) {
        this.nodeName = nodeName;
    }

    public List<Expression> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Expression> attributes) {
        this.attributes = attributes;
    }

    public List<Expression> getChildren() {
        return children;
    }

    public void setChildren(List<Expression> children) {
        this.children = children;
    }
}
