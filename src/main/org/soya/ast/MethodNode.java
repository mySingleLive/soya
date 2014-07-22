package org.soya.ast;

import java.util.List;

/**
 * @author: Jun Gong
 */
public class MethodNode extends AnnotatedNode {
    private final String name;
    private ClassNode returnType;
    private ParameterNode[] parameters;
    private BlockNode body;
    private ClassNode classNode;
    private boolean isVarArgs = false;
    private boolean hasClosure = false;
    private boolean isSetter = false;
    private boolean isGetter = false;

    public MethodNode(int modifier, String methodName, ClassNode returnType, ParameterNode[] parameters, BlockNode body) {
        this(null, modifier, methodName, returnType, parameters, body);
    }


    public MethodNode(List<AnnotationNode> annotationList, int modifier, String methodName, ClassNode returnType, ParameterNode[] parameters, BlockNode body) {
        super(annotationList);
        this.modifier = modifier;
        this.name = methodName;
        this.returnType = returnType;
        this.parameters = parameters;
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public ParameterNode[] getParameters() {
        return parameters;
    }

    public ClassNode getReturnType() {
		return returnType;
	}

	public BlockNode getBody() {
        return body;
    }

    public ClassNode getClassNode() {
        return classNode;
    }
    
    public boolean isHasClosure() {
		return hasClosure;
	}

	public void setHasClosure(boolean hasClosure) {
		this.hasClosure = hasClosure;
	}

	public void setClassNode(ClassNode classNode) {
        this.classNode = classNode;
    }

    public boolean isSetter() {
        return isSetter;
    }

    public void setSetter(boolean setter) {
        isSetter = setter;
    }

    public boolean isGetter() {
        return isGetter;
    }

    public void setGetter(boolean getter) {
        isGetter = getter;
    }

    public boolean isSetterOrGetter() {
        return  isSetter() || isGetter();
    }

    public Class[] getParameterTypes() {
        Class[] types = new Class[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            types[i] = parameters[i].getType().getJclass();
        }
        return types;
    }

    public boolean isVarArgs() {
        return isVarArgs;
    }

    public void setVarArgs(boolean varArgs) {
        isVarArgs = varArgs;
    }

    public String toString() {
        return "[" + (!annotationList.isEmpty() ? annotationList + " " : "") + "method: " + name + "]";
    }
}
