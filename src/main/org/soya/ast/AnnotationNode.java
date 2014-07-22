package org.soya.ast;

import org.soya.ast.expr.Expression;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Jun Gong
 */
public class AnnotationNode extends TreeNode {
    private ClassNode annotationType;
    private Map<String, Expression> attributes = new HashMap<String, Expression>();

    public AnnotationNode(ClassNode annotationType) {
        this.annotationType = annotationType;
    }

    public ClassNode getAnnotationType() {
        return annotationType;
    }

    public Map<String, Expression> getAttributes() {
        return attributes;
    }

    public void setAttribute(String name, Expression expression) {
        attributes.put(name, expression);
    }

    public String toString() {
        return "[Annotation type: " + annotationType + " " + attributes + "]";
    }
}
