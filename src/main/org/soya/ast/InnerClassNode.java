package org.soya.ast;

import org.soya.codegen.frame.SymbolTable;
import org.soya.codegen.frame.VariableWriter;

/**
 * @author: Jun Gong
 */
public class InnerClassNode extends ClassNode {

    private boolean isClosureClass = false;
    private boolean isVarScopeClass = false;
	
	private ClassNode outerClassNode;
	private SymbolTable outerSymbolTable;
	private VariableWriter externalVariableWriter;

    public InnerClassNode(ClassNode outerClassNode, String name, int modifier, ClassNode superClassNode,
                          SymbolTable outerSymbolTable, VariableWriter externalVariableWriter) {
        this(outerClassNode, name, modifier, superClassNode, outerSymbolTable, externalVariableWriter, false);
    }

	public InnerClassNode(ClassNode outerClassNode, String name, int modifier, ClassNode superClassNode,
                          SymbolTable outerSymbolTable, VariableWriter externalVariableWriter, boolean array) {
		super(name, modifier, superClassNode, array);
		this.outerClassNode = outerClassNode;
		this.outerSymbolTable = outerSymbolTable;
        this.externalVariableWriter = externalVariableWriter;
	}

	public ClassNode getOuterClassNode() {
		return outerClassNode;
	}

	public void setOuterClassNode(ClassNode outerClassNode) {
		this.outerClassNode = outerClassNode;
	}

    public VariableWriter getExternalVariableWriter() {
        return externalVariableWriter;
    }

    public boolean isClosureClass() {
        return isClosureClass;
    }

    public void setClosureClass(boolean closureClass) {
        isClosureClass = closureClass;
    }

    public SymbolTable getOuterSymbolTable() {
		return outerSymbolTable;
	}

	public void setOuterSymbolTable(SymbolTable outerSymbolTable) {
		this.outerSymbolTable = outerSymbolTable;
	}

	public boolean isVarScopeClass() {
        return isVarScopeClass;
    }

    public void setVarScopeClass(boolean varScopeClass) {
        isVarScopeClass = varScopeClass;
    }

    public ClassNode getOutermostClassNode() {
		ClassNode outermostClassNode = outerClassNode;
		while (outermostClassNode instanceof InnerClassNode) {
			outermostClassNode = ((InnerClassNode) outermostClassNode).getOuterClassNode();
		}
		return outermostClassNode;
	}

}
