package org.soya.ast;

import org.soya.ast.expr.Expression;

/**
 * @author: Jun Gong
 */
public class PropertyNode extends TreeNode {
	public static int PROPERTY_GETTER_SETTER = 0;
	public static int PROPERTY_GETTER_ONLY = 1;
	public static int PROPERTY_SETTER_ONLY = 2;
	
	private int modifier;
	private ClassNode classNode;
	private String name;
	private int mode;
	private Expression initValue;
	
	public PropertyNode(int modifier, String name, int mode, Expression initValue) {
		this.modifier = modifier;
		this.name = name;
		this.mode = mode;
		this.initValue = initValue;
	}
	
	public ClassNode getClassNode() {
		return classNode;
	}

	public void setClassNode(ClassNode classNode) {
		this.classNode = classNode;
	}

	public int getModifier() {
		return modifier;
	}

	public void setModifier(int modifier) {
		this.modifier = modifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public Expression getInitValue() {
		return initValue;
	}

	public void setInitValue(Expression initValue) {
		this.initValue = initValue;
	}
	
	
	public boolean hasSetter() {
		return mode == PROPERTY_SETTER_ONLY || mode == PROPERTY_GETTER_SETTER;
	}
	
	public boolean hasGetter() {
		return mode == PROPERTY_GETTER_ONLY || mode == PROPERTY_GETTER_SETTER;
	}
}
