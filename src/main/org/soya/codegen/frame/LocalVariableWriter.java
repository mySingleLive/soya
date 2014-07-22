package org.soya.codegen.frame;

import org.soya.ast.ClassNode;
import org.soya.ast.ParameterNode;
import org.soya.ast.TreeNode;
import org.soya.ast.expr.Expression;
import org.soya.codegen.WriterVisitor;

/**
 * @author: Jun Gong
 */
public class LocalVariableWriter extends VariableWriter {


    @Override
    public ClassNode getVarScopeClass() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ScopeVariableWriter getParentVariableWriter(int level) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void loadVarScopeFromLocal(WriterVisitor wv) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void storeVarScopeToLocal(WriterVisitor wv) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void declareVariable(SymbolTable st, WriterVisitor wv, String varName, Expression expression) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void loadVariableFromClosure(WriterVisitor wv, String varName, Expression expression, int level, ClassNode locatedClassNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void storeVariableFromClosure(WriterVisitor wv, String varName, Expression expression, int level, ClassNode locatedClassNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void declareArgument(SymbolTable st, WriterVisitor wv, ParameterNode param, int i) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void storeVariable(SymbolTable st, WriterVisitor wv, String varName, Expression expression) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void loadVariable(SymbolTable st, WriterVisitor wv, String varName, TreeNode node) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
