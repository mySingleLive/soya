package org.soya.codegen;


import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;
import org.soya.ast.*;
import org.soya.ast.stmt.ConstructorCallStatement;
import org.soya.ast.stmt.Statement;
import org.soya.codegen.frame.ScopeType;
import org.soya.codegen.frame.SymbolTable;
import org.soya.runtime.VarScope;

import java.util.ArrayList;
import java.util.List;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;

/**
 * @author: Jun Gong
 */
public class MethodWriter {
    
	protected final GeneratorAdapter ga;
    protected final ClassNode thisClass;
    protected final MethodNode methodNode;
    protected final CompilationUnit unit;
    protected final ClassGenerator classGenerator;
    protected List<Runnable> exceptions = new ArrayList<Runnable>();
    protected WriterVisitor wv;

    public MethodWriter(ClassGenerator classGenerator, MethodNode methodNode) {
        this(classGenerator, classGenerator.getUnit(), classGenerator.getThisClass(), methodNode, classGenerator.getCw(), null);
    }

    public MethodWriter(ClassGenerator classGenerator, MethodNode methodNode, SymbolTable parentSymbolTable) {
        this(classGenerator, classGenerator.getUnit(), classGenerator.getThisClass(), methodNode, classGenerator.getCw(), parentSymbolTable);
    }

    public MethodWriter(ClassGenerator classGenerator, CompilationUnit unit, ClassNode thisClass, MethodNode methodNode, ClassVisitor cv, SymbolTable parentSymbolTable) {
        this(classGenerator, unit, thisClass, methodNode, methodNode.getModifier(), Method.getMethod(BytecodeUtil.getMethodName(methodNode)), cv, parentSymbolTable);
    }

    public MethodWriter(ClassGenerator classGenerator, CompilationUnit unit, ClassNode thisClass, MethodNode methodNode, int access, Method method, ClassVisitor cv, SymbolTable parentSymbolTable) {
        SymbolTable methodSymbolTable = null;
        if (classGenerator.isClosureClass()) {
//            this.stackFrame = new StackFrame(
//                    ((InnerClassNode) thisClass).getOuterSymbolTable(),
//                    FrameType.SCOPE_METHOD);
            methodSymbolTable = new SymbolTable(
                    ((InnerClassNode) thisClass).getOuterSymbolTable(),
                    ScopeType.SCOPE_METHOD);
        }
        else {
            methodSymbolTable = new SymbolTable(parentSymbolTable, ScopeType.SCOPE_METHOD);
        }
        this.classGenerator = classGenerator;
		this.ga = new GeneratorAdapter(access, method, null, null, cv);
        this.unit = unit;
        this.thisClass = thisClass;
        this.methodNode = methodNode;
        this.wv = new WriterVisitor(classGenerator, unit, ga, this, methodSymbolTable, thisClass, methodNode);
	}

    public void addException(Runnable exception) {
        exceptions.add(exception);
    }

    public void addArgument(ParameterNode param, int i) {
        wv.getStackFrame().addArgument(param, i);
    }

    public void addArguments() {
        ParameterNode[] params = methodNode.getParameters();
        for (int i = 0; i < params.length; i++) {
            ParameterNode param = params[i];
            addArgument(param, i);
        }
    }

    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return this.ga.visitAnnotation(desc, visible);
    }

    private boolean isCallSuperConstrucotrInFirstStatment(BlockNode blockNode) {
        List<Statement> statements = blockNode.getStatements();
        if (statements == null || statements.isEmpty()) {
            return false;
        }
        Statement firstStatement = statements.get(0);
        return (firstStatement instanceof ConstructorCallStatement) &&
                ((ConstructorCallStatement) firstStatement).isSuper();
    }

    public void visitConstructor() {
        ConstructorNode constructorNode = (ConstructorNode) methodNode;
        Class[] paramTypes = constructorNode.getParameterTypes();
        Method conMethod = MethodUtil.getConstructorMethod(paramTypes);
        BlockNode blockNode = constructorNode.getBody();
        List<Statement> statements = blockNode.getStatements();
        if (thisClass instanceof InnerClassNode &&
                (((InnerClassNode) thisClass).isVarScopeClass() ||
                        ((InnerClassNode) thisClass).isClosureClass())) {
            ga.loadThis();
            Type superType = TypeUtil.getType(thisClass.getSuperClassNode());
            for (int i = 0; i < paramTypes.length; i++) {
                ga.loadArg(i);
            }
            ga.invokeConstructor(superType, conMethod);
            ga.returnValue();
            ga.endMethod();
            return;
        }
        wv.getStackFrame().pushVariableScope(wv.getRootSymbolTable());
        addArguments();
        if (!isCallSuperConstrucotrInFirstStatment(blockNode)) {
            ga.loadThis();
            Type superType = TypeUtil.getType(thisClass.getSuperClassNode());
            ga.invokeConstructor(superType, MethodUtil.getConstructorMethod());
        }
        else {
            wv.visitConstructorCallStatement((ConstructorCallStatement) statements.remove(0));
        }
        classGenerator.visitInitializeFields(ga, wv);
        classGenerator.visitInitializeProperties(ga, wv);
        if (blockNode != null && !statements.isEmpty()) {
            wv.visitBlockNode(blockNode);
            ga.pop();
        }
        ga.returnValue();
        ga.endMethod();
        wv.getStackFrame().popSymbolTable();
    }

    public boolean isScriptRunMethod() {
        if (!(this.thisClass.isScript() && methodNode.getModifier() == ACC_PUBLIC &&
                methodNode.getName().equals("run"))) {
            return false;
        }
        Class[] paramTypes = methodNode.getParameterTypes();
        if (paramTypes.length != 1 && paramTypes[0] != Object[].class) {
            return false;
        }
        return true;
    }

    public void visitMethod() {
        wv.getStackFrame().pushVariableScope(wv.getRootSymbolTable());
        if (isScriptRunMethod()) {
            wv.loadThis();
            wv.getStackFrame().getVariableWriter().loadVarScopeFromLocal(wv);
            wv.getGA().putField(TypeUtil.getType(thisClass), "bundle", TypeUtil.getType(VarScope.class));
        }
        addArguments();
        BlockNode block = methodNode.getBody();
        wv.visitBlockNode(block);
        if (block.getStatements().size() == 0) {
            wv.pushNull();
        }
        if (!wv.getStackFrame().getSymbolTable().isHasReturned()) {
            if (methodNode.getReturnType() == ClassNode.VOID) {
                ga.pop();
            }
            ga.returnValue();
        }

        List<Runnable> exceptions =  wv.getExceptions();
        for (int i = 0; i < exceptions.size(); i++) {
            Runnable runnable = exceptions.get(i);
            runnable.run();
        }
        ga.endMethod();
        wv.getStackFrame().popVariableScope();
    }

	public GeneratorAdapter getGeneratorAdapter() {
		return ga;
	}

}
