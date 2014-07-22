package org.soya.codegen.frame;

import org.objectweb.asm.Label;
import org.soya.ast.TreeNode;
import org.soya.codegen.ClassGeneratorException;
import org.soya.util.NameUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Jun Gong
 */
public class SymbolTable {
    protected SymbolTable parent;
    protected VariableWriter variableWriter;
    protected boolean hasReturned = false;
    protected boolean hasBreaked = false;
    protected boolean hasReturnedInSubBlock = false;
    protected Label endLabel;
    protected ScopeType blockType;
    protected SymbolTable ifSymbolTable;
    protected Map<String, Integer> localVariables = new HashMap<String, Integer>();
    protected Map<String, String> variables = new HashMap<String, String>();
    protected Map<String, String> arguments = new HashMap<String, String>();
    protected List<String> varFieldNameList = new ArrayList<String>();
    protected int dotVarCount = 0;

    public SymbolTable(ScopeType blockType) {
        this.blockType = blockType;
    }

/*
    public SymbolTable(SymbolTable parent) {
        this(parent, BlockType.FRAME_UNDEFINED);
    }
*/

    public SymbolTable(SymbolTable parent, ScopeType blockType) {
        this(blockType);
        this.parent = parent;
    }

    public VariableWriter getVariableWriter() {
        return variableWriter;
    }

    public VariableWriter getVariableWriter(int level) {
        SymbolTable st =  getSymbolTable(level);
        if (st != null) {
            return st.getVariableWriter();
        }
        return null;
    }

    public void setVariableWriter(VariableWriter variableWriter) {
        this.variableWriter = variableWriter;
    }

    public boolean isHasReturned() {
        return hasReturned;
    }

    public void setHasReturned(boolean hasReturned) {
        this.hasReturned = hasReturned;
        if (hasReturned) {
            if (blockType == ScopeType.SCOPE_ELSE) {
                if (ifSymbolTable.isHasReturned()) {
                    getParent().setHasReturnedInSubBlock(true);
                }
            }
        }
    }

    public boolean isHasReturnedInSubBlock() {
        return hasReturnedInSubBlock;
    }

    public void setHasReturnedInSubBlock(boolean hasReturnedInSubBlock) {
        this.hasReturnedInSubBlock = hasReturnedInSubBlock;
    }

    public boolean isHasBreaked() {
        return hasBreaked;
    }

    public void setHasBreaked(boolean hasBreaked) {
        this.hasBreaked = hasBreaked;
    }

    public Label getEndLabel() {
        return endLabel;
    }

    public void setEndLabel(Label endLabel) {
        this.endLabel = endLabel;
    }

    public ScopeType getBlockType() {
        return blockType;
    }

    public void setBlockType(ScopeType blockType) {
        this.blockType = blockType;
    }

    public SymbolTable getIfSymbolTable() {
        return ifSymbolTable;
    }

    public void setIfSymbolTable(SymbolTable ifSymbolTable) {
        this.ifSymbolTable = ifSymbolTable;
    }

    public void declareLocalVariable(String varName, int i) {
        localVariables.put(varName, i);
    }

    public int getLocalVariable(String varName) {
        Integer i = localVariables.get(varName);
        if (i == null) {
            if (parent == null) {
                return -1;
            }
            return parent.getLocalVariable(varName);
        }
        return i.intValue();
    }

    public Map<String, String> getVariables() {
        return variables;
    }

    public Map<String, Integer> getLocalVariables() {
        return localVariables;
    }

    public boolean containsLocalVariable(String varName) {
        if (localVariables.containsKey(varName)) {
            return true;
        }
        if (parent != null) {
            return parent.containsLocalVariable(varName);
        }
        return false;
    }

    public List<String> getVarFieldNameList() {
        return varFieldNameList;
    }

    public boolean hasDependentVarScope() {
        return blockType != ScopeType.SCOPE_IF
                && blockType != ScopeType.SCOPE_ELSE
                && blockType != ScopeType.SCOPE_LOOP
//                && blockType != ScopeType.SCOPE_MATCH
                && blockType != ScopeType.SCOPE_MATCH_ITEM;
    }

    public void addVarFieldName(String varFieldName) {
        varFieldNameList.add(varFieldName);
        if (!hasDependentVarScope() && parent != null) {
            parent.addVarFieldName(varFieldName);
        }
    }

    public String declareArgument(String argName, TreeNode node) {
        if (arguments.containsKey(argName)) {
            throw new ClassGeneratorException("Variable '" + argName + "' is already defined in the scope.", node);
        }
        String varFieldName = NameUtil.getVarScopeFieldName(argName);
        variables.put(argName, varFieldName);
        arguments.put(argName, varFieldName);
        addVarFieldName(varFieldName);
        return varFieldName;
    }

    public String declareVariable(String varName, TreeNode node) throws ClassGeneratorException {
        if (containsVariable(varName)) {
            throw new ClassGeneratorException("Variable '" + varName + "' is already defined in the scope.", node);
        }
        String varFieldName = NameUtil.getVarScopeFieldName(varName);
        variables.put(varName, varFieldName);
        addVarFieldName(varFieldName);
        return varFieldName;
    }

    public int getVariableLevel(String varName, int level) {
        String fieldName = variables.get(varName);
        if (fieldName == null) {
            int i = level;
            if (parent == null) {
                return -1;
            }
            if (hasDependentVarScope()) {
                i++;
            }
            return parent.getVariableLevel(varName, i);
        }
        return level;
    }

    public int getVariableLevel(String varName) {
        return getVariableLevel(varName, 0);
    }

    public boolean containsVariable(String varName) {
        if (variables.containsKey(varName)) {
            return true;
        }
        if (parent != null) {
            return parent.containsVariable(varName);
        }
        return false;
    }

    public boolean containsVariable(String varName, int level, TreeNode node) throws ClassGeneratorException {
        SymbolTable st = getSymbolTable(level, node);
        int x = 0;
        if (st.getVariables().containsKey(varName)) {
            return true;
        }
        return false;
    }

    public SymbolTable getSymbolTable(int level) {
        return getSymbolTable(level, null);
    }

    public SymbolTable getSymbolTable(int level, TreeNode node) throws ClassGeneratorException {
        if (level == 0) {
            return this;
        }
        if (parent != null) {
            return parent.getSymbolTable(level - 1, node);
        }
        throw new ClassGeneratorException("find SymbolTable error, level: " + level, node);
    }

    public SymbolTable getParent() {
        return parent;
    }

    public SymbolTable getParent(ScopeType type) {
        if (blockType == type) {
            return this;
        }
        if (parent != null) {
            return parent.getParent(type);
        }
        return null;
    }

    public int getDotVarCount() {
        return dotVarCount;
    }

    public void setDotVarCount(int dotVarCount) {
        this.dotVarCount = dotVarCount;
    }
}
