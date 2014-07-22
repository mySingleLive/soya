package org.soya.codegen.frame;

import org.soya.ast.ClassNode;
import org.soya.ast.ParameterNode;
import org.soya.ast.TreeNode;
import org.soya.ast.expr.Expression;
import org.soya.codegen.WriterVisitor;

/**
 * @author: Jun Gong
 */
public class StaticVariableWriter extends VariableWriter {

    public StaticVariableWriter(VariableWriter variableWriter, SymbolTable symbolTable) {
        this.parent = variableWriter;
        this.symbolTable = symbolTable;
    }

    @Override
    public ClassNode getVarScopeClass() {
        return parent.getVarScopeClass();
    }


    @Override
    public void loadVarScopeFromLocal(WriterVisitor wv) {
        parent.loadVarScopeFromLocal(wv);
    }


    @Override
    public void storeVarScopeToLocal(WriterVisitor wv) {
        parent.storeVarScopeToLocal(wv);
    }

    @Override
    public void declareVariable(SymbolTable st, WriterVisitor wv, String varName, Expression expression) {
        parent.declareVariable(st, wv, varName, expression);
    }

    @Override
    public void loadVariableFromClosure(WriterVisitor wv, String varName, Expression expression, int level, ClassNode locatedClassNode) {
        parent.loadVariableFromClosure(wv, varName, expression, level, locatedClassNode);
    }

    @Override
    public void storeVariableFromClosure(WriterVisitor wv, String varName, Expression expression, int level, ClassNode locatedClassNode) {
        parent.storeVariableFromClosure(wv, varName, expression, level, locatedClassNode);
    }

    @Override
    public void declareArgument(SymbolTable st, WriterVisitor wv, ParameterNode param, int i) {
        parent.declareArgument(st, wv, param, i);
    }

    @Override
    public void storeVariable(SymbolTable st, WriterVisitor wv, String varName, Expression expression) {
        parent.storeVariable(st, wv, varName, expression);
    }

    @Override
    public void loadVariable(SymbolTable st, WriterVisitor wv, String varName, TreeNode node) {
        parent.loadVariable(st, wv, varName, node);
    }
}
