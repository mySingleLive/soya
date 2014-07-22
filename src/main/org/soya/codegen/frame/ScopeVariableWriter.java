package org.soya.codegen.frame;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.soya.ast.*;
import org.soya.ast.expr.Expression;
import org.soya.codegen.*;
import org.soya.runtime.VarScope;
import org.soya.util.NameUtil;

import java.util.List;

/**
 * @author: Jun Gong
 */
public class ScopeVariableWriter extends VariableWriter {
    public static final String LOCAL_NAME_PREFIX = "__varscope__";
    
    protected final ClassGenerator classGenerator;
    protected final ClassNode thisClass;
    protected final CompilationUnit unit;
    protected final int index;
    protected ClassNode createdVarScopeClass = null;
    
    public ScopeVariableWriter(ClassGenerator classGenerator, CompilationUnit unit, ClassNode thisClass, MethodNode methodNode, SymbolTable symbolTable) {
    	this(null, classGenerator, unit, thisClass, methodNode, symbolTable);
    }

    public ScopeVariableWriter(VariableWriter parent, ClassGenerator classGenerator, CompilationUnit unit, ClassNode thisClass, MethodNode methodNode, SymbolTable symbolTable) {
    	this.parent = parent;
        this.classGenerator = classGenerator;
        this.unit = unit;
        this.thisClass = thisClass;
        this.methodNode = methodNode;
        this.index = classGenerator.getVarscopeCounter();
        classGenerator.setVarscopeCounter(index + 1);
        this.symbolTable = symbolTable;
        this.symbolTable.setVariableWriter(this);
        classGenerator.addVariableWriter(this);
    }

    public ClassNode getVarScopeClass() {
        if (createdVarScopeClass == null) {
            createdVarScopeClass = createVarScopeClass();
            thisClass.addInnerClass((InnerClassNode) createdVarScopeClass);
            classGenerator.addInnerClass(createdVarScopeClass);
        }
        return createdVarScopeClass;
    }

    public ClassNode createVarScopeClass() {
        ClassNode outermostClass = thisClass.getOutermostClassNode();
        String name = NameUtil.getVarScopeClassName(outermostClass, thisClass, methodNode, index);
        if (name.contains("<clinit>")) {
            name = name.replace("<clinit>", "clinit");
        }
        else if (name.contains("<init>")) {
            name = name.replace("<init>", "init");
        }
        InnerClassNode result = new InnerClassNode(thisClass, name, Opcodes.ACC_PUBLIC, ClassNode.PVarScope, null, null);
        result.setVarScopeClass(true);
        return result;
    }

    public void configVarScopeClass() {
        List<String> varFieldNames = symbolTable.getVarFieldNameList();
        for (String varFieldName : varFieldNames) {
            FieldNode field = new FieldNode(Opcodes.ACC_PUBLIC, varFieldName, null);
            createdVarScopeClass.addField(field);
        }
    }

    public ClassNode getCreatedVarScopeClass() {
        return createdVarScopeClass;
    }

//    public ClassNode getVarScopeClass() {
//        return getCreatedVarScopeClass();
//    }

    public String getScopeClassLocalName() {
        return LOCAL_NAME_PREFIX + index;
    }

    public void storeVarScopeToLocal(WriterVisitor wv) {
        declareLocalVariable(wv, getScopeClassLocalName());
    }


    private void loadVarScopeFromLocal(WriterVisitor wv, int level, ClassNode locatedClassNode) {
        loadLocalVariable(wv, getScopeClassLocalName());
        Type originVarScopeType = TypeUtil.getType(VarScope.class);
        Type ownerType = TypeUtil.getType(getVarScopeClass());

        int i = 0;
        while (i < level) {
            if (i > 0) {
                ownerType = originVarScopeType;
            }
            wv.getGA().getField(
                    ownerType,
                    "parent",
                    originVarScopeType);
            i++;
        }
        if (level > 0) {
            ClassNode clsNode = getVarScopeClass();
            wv.getGA().checkCast(TypeUtil.getType(locatedClassNode));
        }
    }

    public void loadVarScopeFromLocal(WriterVisitor wv) {
        loadLocalVariable(wv, getScopeClassLocalName());
        Type originVarScopeType = TypeUtil.getType(VarScope.class);
        Type ownerType = TypeUtil.getType(getVarScopeClass());

//        int i = 0;
//        while (i < level) {
//            if (i > 0) {
//                ownerType = originVarScopeType;
//            }
//            wv.getGA().getField(
//                    ownerType,
//                    "parent",
//                    originVarScopeType);
//            i++;
//        }
//        if (level > 0) {
            ClassNode clsNode = getVarScopeClass();
            ownerType = TypeUtil.getType(clsNode);
            wv.getGA().checkCast(ownerType);
//        }
    }

    public void loadVariable(WriterVisitor wv, String varName, int level) {
    	loadVarScopeFromLocal(wv);
        ClassNode classNode = getVarScopeClass();
        Type ownerType = TypeUtil.getType(classNode);
        wv.getGA().getField(
                ownerType,
                NameUtil.getVarScopeFieldName(varName),
                TypeUtil.OBJECT_TYPE);
    }

    private void storeVariable(WriterVisitor wv, String varName, int level) {
        ClassNode clsNode = getVarScopeClass();
        Type ownerType = TypeUtil.getType(clsNode);
        wv.getGA().putField(
                ownerType,
                NameUtil.getVarScopeFieldName(varName),
                TypeUtil.OBJECT_TYPE);
    }

    public void declareVariable(SymbolTable st, WriterVisitor wv, String varName, Expression expression) {
        loadVarScopeFromLocal(wv);
        wv.visitExpression(expression);

        String varFieldName = null;
        try {
            varFieldName = st.declareVariable(varName, expression);
        } catch (ClassGeneratorException cge) {
            wv.addError(cge);
        }

        if (varFieldName == null) {
            return;
        }
        storeVariable(wv, varName, 0);
    }

    public void declareArgument(SymbolTable st, WriterVisitor wv, ParameterNode param, int i) {
        loadVarScopeFromLocal(wv);
        wv.getGA().loadArg(i);

        String paramName = param.getName();
        String varFieldName = null;
        try {
//            varFieldName = st.declareVariable(paramName, param);
            varFieldName = st.declareArgument(paramName, param);
        } catch (ClassGeneratorException cge) {
            wv.addError(cge);
        }

        if (varFieldName == null) {
            return;
        }
        storeVariable(wv, paramName, 0);
    }


    @Override
    public void loadVariableFromClosure(WriterVisitor wv, String varName, Expression expression, int level, ClassNode locatedClassNode) {
        loadVarScopeFromLocal(wv, level, locatedClassNode);
        Type ownerType = TypeUtil.getType(locatedClassNode);
        wv.getGA().getField(
                ownerType,
                NameUtil.getVarScopeFieldName(varName),
                TypeUtil.OBJECT_TYPE);
    }

    @Override
    public void storeVariableFromClosure(WriterVisitor wv, String varName, Expression expression, int level, ClassNode locatedClassNode) {
        loadVarScopeFromLocal(wv, level, locatedClassNode);
        wv.visitExpression(expression);
        Type ownerType = TypeUtil.getType(locatedClassNode);
        wv.getGA().putField(
                ownerType,
                NameUtil.getVarScopeFieldName(varName),
                TypeUtil.OBJECT_TYPE);
    }


    public void storeVariable(SymbolTable st, WriterVisitor wv, String varName, Expression expression) {
        loadVarScopeFromLocal(wv);
        wv.visitExpression(expression);
        int level = st.getVariableLevel(varName);
        if (level == -1) {
            throw new ClassGeneratorException("Cannot find variable '" + varName + "'", expression);
        }
        storeVariable(wv, varName, level);
    }


    public void loadVariable(SymbolTable st, WriterVisitor wv, String varName, TreeNode node) {
/*
        int level = st.getVariableLevel(varName);
        if (level == -1) {
            throw new ClassGeneratorException("Cannot find variable '" + varName + "'", node);
        }
*/
        loadVariable(wv, varName, 0);
    }


}
