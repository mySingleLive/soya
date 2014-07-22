package org.soya.codegen.frame;

import org.objectweb.asm.Type;
import org.soya.ast.*;
import org.soya.ast.expr.Expression;
import org.soya.codegen.*;
import org.soya.runtime.VarScope;

import java.util.*;

/**
 * @author: Jun Gong
 */
public class StackFrame {
    protected ClassGenerator classGenerator;
    protected WriterVisitor writerVisitor;
    protected LinkedList<SymbolTable> symbolTables = new LinkedList<SymbolTable>();
    protected SymbolTable symbolTable;
    protected Map<String, Integer> argumentVariables = new HashMap<String, Integer>();
    protected String firstArgumentName;

    public StackFrame(WriterVisitor writerVisitor) {
        this.writerVisitor = writerVisitor;
        classGenerator = writerVisitor.getClassGenerator();
    }

    public VariableWriter getVariableWriter() {
        if (symbolTable == null) {
            return null;
        }
        return symbolTable.getVariableWriter();
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void pushSymbolTable(SymbolTable symbolTable) {
        symbolTables.add(symbolTable);
        this.symbolTable = symbolTable;
    }

    public void popSymbolTable() {
        symbolTables.removeLast();
        if (symbolTables.size() > 0) {
            this.symbolTable = symbolTables.getLast();
        }
        else {
            this.symbolTable = null;
        }
    }

    public void pushVariableScope(SymbolTable symbolTable) {
        CompilationUnit unit = classGenerator.getUnit();
        ClassNode classNode = writerVisitor.getClassNode();
        MethodNode methodNode = writerVisitor.getMethodNode();

        if (!symbolTable.hasDependentVarScope()) {
            pushVariableScope(symbolTable, new StaticVariableWriter(getVariableWriter(), symbolTable));
            return;
        }

        VariableWriter variableWriter = null;
        VariableWriter parent = getVariableWriter();

        if (classGenerator.isClosureClass()) {
            variableWriter = new ScopeVariableWriter(((InnerClassNode) classNode).getExternalVariableWriter(),
                    classGenerator, unit, classNode, methodNode, symbolTable);
        }
        else {
            variableWriter = new ScopeVariableWriter(parent, classGenerator, unit, classNode, methodNode, symbolTable);
        }

        pushVariableScope(symbolTable, variableWriter);

        ClassNode varscopeClass = variableWriter.getVarScopeClass();
        Type varscopeType = TypeUtil.getType(varscopeClass);
        writerVisitor.getGA().newInstance(varscopeType);
        writerVisitor.getGA().dup();
        if (parent == null && classGenerator.isClosureClass() && !methodNode.isStatic()) {
            Type thisType = TypeUtil.getType(classNode);
            writerVisitor.getGA().loadThis();
            writerVisitor.getGA().getField(thisType, "externalVariables", TypeUtil.getType(VarScope.class));
            writerVisitor.getGA().invokeConstructor(varscopeType, MethodUtil.getConstructorMethod(VarScope.class));
        }
        else {
            if (parent != null) {
                parent.loadVarScopeFromLocal(writerVisitor);
                writerVisitor.getGA().invokeConstructor(varscopeType, MethodUtil.getConstructorMethod(VarScope.class));
            }
            else {
                writerVisitor.getGA().invokeConstructor(varscopeType, MethodUtil.getConstructorMethod());
            }
        }
        variableWriter.storeVarScopeToLocal(writerVisitor);

    }

    public void pushVariableScope(SymbolTable symbolTable, VariableWriter variableWriter) {
        symbolTable.setVariableWriter(variableWriter);
        pushSymbolTable(symbolTable);
    }

    public void popVariableScope() {
        popSymbolTable();
    }

    public void popBreak() {
        getSymbolTable().setHasBreaked(true);
//        SymbolTable loopSt = getSymbolTable().getParent(ScopeType.SCOPE_LOOP);
//        ScopeType type = getSymbolTable().blockType;
    }

    public SymbolTable getSymbolTableByVariable(String varName) {
        SymbolTable st = getSymbolTable();
        for (; st != null; st = st.getParent()) {
            if (st.getVariables().containsKey(varName)) {
                return st;
            }
        }
        return null;
    }

    private int getVariableWriterLevelByVariableName(String varName) {
        int level = 0;
        SymbolTable st = getSymbolTable();
        VariableWriter variableWriter = st.getVariableWriter();
        ClassNode varScopeClass = variableWriter.getVarScopeClass();
        for (; st != null; st = st.getParent()) {
            variableWriter = st.getVariableWriter();
            ClassNode vsClass = variableWriter.getVarScopeClass();
            if (!(variableWriter instanceof StaticVariableWriter) &&
                    vsClass != null && vsClass != varScopeClass) {
                level++;
            }
            if (st.getVariables().containsKey(varName)) {
                break;
            }
        }
        return level;
    }

    public void loadVariable(String varName, Expression expression) {
        if (classGenerator.isClosureClass()) {
            int level = getVariableWriterLevelByVariableName(varName);
            ClassNode locatedClassNode = getSymbolTableByVariable(varName).getVariableWriter().getVarScopeClass();
            getVariableWriter().loadVariableFromClosure(writerVisitor, varName, expression, level, locatedClassNode);
        }
        else if (expression != null) {
            getSymbolTableByVariable(varName).getVariableWriter().loadVariable(writerVisitor, varName, expression);
        }
    }

    public void storeVariable(String varName, Expression expression) {
        if (classGenerator.isClosureClass()) {
            int level = getVariableWriterLevelByVariableName(varName);
            ClassNode locatedClassNode = getSymbolTableByVariable(varName).getVariableWriter().getVarScopeClass();
            getVariableWriter().storeVariableFromClosure(writerVisitor, varName, expression, level, locatedClassNode);
        }
        else {
            getSymbolTableByVariable(varName).getVariableWriter().storeVariable(writerVisitor, varName, expression);
        }

    }

/*
//    public VariableWriter getVariableWriter() {
//        return variableWriter;
//    }
//
//    public VariableWriter getVariableWriter(int level) {
//        StackFrame st =  getSymbolTable(level);
//        if (st != null) {
//            return st.getVariableWriter();
//        }
//        return null;
//    }
//
//    public void setVariableWriter(VariableWriter variableWriter) {
//        this.variableWriter = variableWriter;
//    }
//
//    public boolean isHasReturned() {
//        return hasReturned;
//    }
//
//    public void setHasReturned(boolean hasReturned) {
//        this.hasReturned = hasReturned;
//        if (hasReturned) {
//            if (blockType == FrameType.SCOPE_ELSE) {
//                if (ifSymbolTable.isHasReturned()) {
//                    getParent().setHasReturnedInSubBlock(true);
//                }
//            }
//        }
//    }
*/

/*
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

    public FrameType getBlockType() {
        return blockType;
    }

    public void setBlockType(FrameType blockType) {
        this.blockType = blockType;
    }

    public StackFrame getIfSymbolTable() {
        return ifSymbolTable;
    }

    public void setIfSymbolTable(StackFrame ifSymbolTable) {
        this.ifSymbolTable = ifSymbolTable;
    }
*/

    public int getArgument(String argName) {
        if (argumentVariables.size() == 0) {
            return -1;
        }
        Integer i = argumentVariables.get(argName);
        if (i == null) {
            return -1;
        }
        return i.intValue();
    }

    public void addArgument(ParameterNode param, int i) {
        String paramName = param.getName();
        if (argumentVariables.containsKey(paramName)) {
            return;
        }
        if (i == 0) {
            firstArgumentName = paramName;
        }
        argumentVariables.put(paramName, i);
        getVariableWriter().declareArgument(symbolTable, writerVisitor, param, i);
    }

    public String getFirstArgumentName() {
        return firstArgumentName;
    }

    public void setFirstArgumentName(String firstArgumentName) {
        this.firstArgumentName = firstArgumentName;
    }

    public Map<String, Integer> getArgumentVariables() {
        return argumentVariables;
    }

    public boolean containsArgument(String argName) {
        return argumentVariables.containsKey(argName);
    }

/*
    public void declareLocalVariable(String varName, int i) {
        if (containsArgument(varName)) {
            return;
        }
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
        return blockType != FrameType.SCOPE_IF && blockType != FrameType.SCOPE_ELSE && blockType != FrameType.SCOPE_MATCH_ITEM;
    }

    public void addVarFieldName(String varFieldName) {
        varFieldNameList.add(varFieldName);
        if (!hasDependentVarScope() && parent != null) {
            parent.addVarFieldName(varFieldName);
        }
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
        StackFrame st = getSymbolTable(level, node);
        int x = 0;
        if (st.getVariables().containsKey(varName)) {
            return true;
        }
        return false;
    }

    public StackFrame getSymbolTable(int level) {
        return getSymbolTable(level, null);
    }

    public StackFrame getSymbolTable(int level, TreeNode node) throws ClassGeneratorException {
        if (level == 0) {
            return this;
        }
        if (parent != null) {
            return parent.getSymbolTable(level - 1, node);
        }
        throw new ClassGeneratorException("find SymbolTable error, level: " + level, node);
    }

    public StackFrame getParent() {
        return parent;
    }

    public StackFrame getParent(FrameType type) {
        if (blockType == type) {
            return this;
        }
        if (parent != null) {
            return parent.getParent(type);
        }
        return null;
    }
*/

}
