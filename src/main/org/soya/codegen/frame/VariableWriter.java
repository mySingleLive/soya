package org.soya.codegen.frame;

import org.soya.ast.ClassNode;
import org.soya.ast.MethodNode;
import org.soya.ast.ParameterNode;
import org.soya.ast.TreeNode;
import org.soya.ast.expr.ConstantExpression;
import org.soya.ast.expr.Expression;
import org.soya.codegen.ClassGeneratorException;
import org.soya.codegen.TypeUtil;
import org.soya.codegen.WriterVisitor;

/**
 * @author: Jun Gong
 */
public abstract class VariableWriter {
    protected MethodNode methodNode;
    protected SymbolTable symbolTable;
    protected VariableWriter parent;

    public MethodNode getMethodNode() {
        return methodNode;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public VariableWriter getParent() {
        return parent;
    }

    public abstract ClassNode getVarScopeClass();

    public VariableWriter getParentVariableWriter(int level) {
        if (level == 0) {
            return this;
        }
        if (parent == null) {
            return this;
        }
        return parent.getParentVariableWriter(level - 1);
    }

    public abstract void loadVarScopeFromLocal(WriterVisitor wv);

//    public abstract void loadVarScopeFromLocal(WriterVisitor wv, int level);

    public abstract void storeVarScopeToLocal(WriterVisitor wv);

    public int loadArgument(WriterVisitor wv, String argName) {
        if (!wv.getStackFrame().containsArgument(argName)) {
            return -1;
        }
        int i = wv.getStackFrame().getArgument(argName);
        if (i != -1) {
            wv.getGA().loadArg(i);
        }
        return i;
    }

    public abstract void declareVariable(SymbolTable st, WriterVisitor wv, String varName, Expression expression);

    public abstract void loadVariableFromClosure(WriterVisitor wv, String varName, Expression expression, int level, ClassNode locatedClassNode);

    public abstract void storeVariableFromClosure(WriterVisitor wv, String varName, Expression expression, int level, ClassNode locatedClassNode);

    public abstract void declareArgument(SymbolTable st, WriterVisitor wv, ParameterNode param, int i);

    public void declareNullVariable(WriterVisitor wv, String varName, Expression expression) {
        declareNullVariable(symbolTable, wv, varName, expression);
    }

    public void declareNullVariable(SymbolTable st, WriterVisitor wv, String varName, Expression expression)  {
        declareVariable(st, wv, varName, new ConstantExpression(null));
    }

    public void declareVariable(WriterVisitor wv, String varName, Expression expression) {
        declareVariable(symbolTable, wv, varName, expression);
    }


    public void storeVariable(WriterVisitor wv, String varName, Expression expression) {
        storeVariable(symbolTable, wv, varName, expression);
    }

    public abstract void storeVariable(SymbolTable st, WriterVisitor wv, String varName, Expression expression);


    public void loadVariable(WriterVisitor wv, String varName, TreeNode node) {
        loadVariable(symbolTable, wv, varName, node);
    }

    public abstract void loadVariable(SymbolTable st, WriterVisitor wv, String varName, TreeNode node);


    //////////////////////////////////////////////////
    // LOCAL VARIABLES

//    public int storeLocalVariable(WriterVisitor wv, String varName) {
//        return storeLocalVariable(stackFrame, wv, varName);
//    }

    public int storeLocalVariable(SymbolTable st, WriterVisitor wv, String varName) {
        int i = -1;
        try {
            i = st.getLocalVariable(varName);
        } catch (ClassGeneratorException cge) {
            wv.addError(cge);
        }

        if (i != -1) {
            wv.getGA().storeLocal(i);
        }
        return i;
    }

    public int loadLocalVariable(WriterVisitor wv, String varName) {
        int i = -1;
        try {
            i = symbolTable.getLocalVariable(varName);
        } catch (ClassGeneratorException cge) {
            wv.addError(cge);
        }
        if (i != -1) {
            wv.getGA().loadLocal(i);
        }
        return i;
    }

    public int declareLocalVariable(WriterVisitor wv, String varName) {
        int i = wv.getGA().newLocal(TypeUtil.OBJECT_TYPE);
        if (i != -1) {
            try {
                symbolTable.declareLocalVariable(varName, i);
            } catch (ClassGeneratorException cge) {
                wv.addError(cge);
            }
            wv.getGA().storeLocal(i);
        }
        return i;
    }


    public int declareTempDotVariable(WriterVisitor wv, String varName) {
        int count = symbolTable.getDotVarCount();
        declareLocalVariable(wv, varName + "_" + count);
        symbolTable.setDotVarCount(count + 1);
        return count;
    }


}
