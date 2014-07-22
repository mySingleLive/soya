package org.soya.ast;

import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Jun Gong
 */
public abstract class AnnotatedNode extends TreeNode {
    protected int modifier = Opcodes.ACC_PUBLIC;
    protected List<AnnotationNode> annotationList;

    public int getModifier() {
        return modifier;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

    public boolean isStatic() {
        return (modifier & Opcodes.ACC_STATIC) == Opcodes.ACC_STATIC;
    }

    public boolean isAbstract() {
        return (modifier & Opcodes.ACC_ABSTRACT) == Opcodes.ACC_ABSTRACT;
    }

    public boolean isPublic() {
        return (modifier & Opcodes.ACC_PUBLIC) == Opcodes.ACC_PUBLIC;
    }

    public boolean isPrivate() {
        return (modifier & Opcodes.ACC_PRIVATE) == Opcodes.ACC_PRIVATE;
    }

    public boolean isProtected() {
        return (modifier & Opcodes.ACC_PROTECTED) == Opcodes.ACC_PROTECTED;
    }

    public void setAnnotationList(List<AnnotationNode> annotationList) {
        this.annotationList = annotationList;
    }

    public List<AnnotationNode> getAnnotationList() {
        return annotationList;
    }

    public AnnotatedNode() {
        annotationList = new ArrayList<AnnotationNode>();
    }

    public AnnotatedNode(List<AnnotationNode> annotationList) {
        if (annotationList == null) {
            annotationList = new ArrayList<AnnotationNode>();
        }
        this.annotationList = annotationList;
    }

    public void addAnnotation(AnnotationNode annotation) {
        annotationList.add(annotation);
    }
}
