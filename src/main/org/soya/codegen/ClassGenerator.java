package org.soya.codegen;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;
import org.objectweb.asm.util.CheckClassAdapter;
import org.soya.antlr.SoyaToken;
import org.soya.antlr.parser.SoyaParserTokenTypes;
import org.soya.ast.*;
import org.soya.ast.expr.*;
import org.soya.ast.stmt.ReturnStatement;
import org.soya.codegen.frame.ScopeType;
import org.soya.codegen.frame.ScopeVariableWriter;
import org.soya.codegen.frame.SymbolTable;
import org.soya.runtime.SoyaShell;
import soya.lang.SoyaClassLoader;
import org.soya.util.NameUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.objectweb.asm.Opcodes.*;


/**
 * @author: Jun Gong
 */
public class ClassGenerator {

    private CompilationUnit unit;
    private int bytecodeVersion = V1_6;
    private ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
    protected ClassVisitor cv = new CheckClassAdapter(cw);
    protected SoyaClassLoader classLoader;
    private LinkedList<ClassNode> innerClassList = new LinkedList<ClassNode>();
    private LinkedList<ScopeVariableWriter> variableWriterList = new LinkedList<ScopeVariableWriter>();
    private ClassNode thisClass;
    private SymbolTable scriptRunSymtable;
    private ClassVerfier classVerfier = new ClassVerfier();
    private LinkedList<PropertyNode> toInitPropertyList = new LinkedList<PropertyNode>();
    private int closureCounter = 0;
    private int varscopeCounter = 0;

    public ClassGenerator(CompilationUnit unit, SoyaClassLoader classLoader) {
    	this.unit = unit;
        this.classLoader = classLoader;
    }

    public void visitClass(ClassNode classNode) throws ClassNotFoundException {
    	thisClass = classNode;
    	Class superClazz = unit.getClassLoader().loadClass(thisClass.getSuperClassNode().getName());
    	thisClass.getSuperClassNode().setJclass(superClazz);

        classVerfier.visitClass(thisClass);

        cv.visit(bytecodeVersion,
                classNode.getModifier(),
                BytecodeUtil.getClassInternalName(classNode),
                null,
                BytecodeUtil.getClassInternalName(classNode.getSuperClassNode()),
                null);
        visitSource();
        visitAnnotations(classNode, cv);
        visitPattern();
        visitProperties();
        visitFields();
        visitStaticInitializerMethod();
        visitConstructors();
        List<MethodNode> methodList = classNode.getMethodList();
        for (MethodNode mnode : methodList) {
            visitMethod(mnode);
        }
        visitVariableWriters();
        visitInnerClasses();
        visitInnerClass(classNode);
        cv.visitEnd();
    }
    
    public void visitEnd() {
        cw.visitEnd();
    }

    public CompilationUnit getUnit() {
        return unit;
    }

    public int getBytecodeVersion() {
        return bytecodeVersion;
    }

    public ClassWriter getCw() {
        return cw;
    }

    public ClassVisitor getCv() {
        return cv;
    }

    public SoyaClassLoader getClassLoader() {
        return classLoader;
    }
    
    public void addInnerClass(ClassNode innerClass) {
    	innerClassList.add(innerClass);
    }

    public LinkedList<ClassNode> getInnerClassList() {
		return innerClassList;
	}
    
    public void addVariableWriter(ScopeVariableWriter variableWriter) {
    	variableWriterList.add(variableWriter);
    }

    public LinkedList<ScopeVariableWriter> getVariableWriterList() {
		return variableWriterList;
	}

	public boolean isClosureClass() {
        return isClosureClass(thisClass);
    }

    public boolean isClosureClass(ClassNode classNode) {
        return classNode instanceof InnerClassNode && ((InnerClassNode) classNode).isClosureClass();
    }

	public ClassNode getThisClass() {
        return thisClass;
    }

    public SymbolTable getScriptRunSymtable() {
        return scriptRunSymtable;
    }

    public void setScriptRunSymtable(SymbolTable scriptRunSymtable) {
        this.scriptRunSymtable = scriptRunSymtable;
    }

    public int getClosureCounter() {
        return closureCounter;
    }

    public void setClosureCounter(int closureCounter) {
        this.closureCounter = closureCounter;
    }

    public int getVarscopeCounter() {
        return varscopeCounter;
    }

    public void setVarscopeCounter(int varscopeCounter) {
        this.varscopeCounter = varscopeCounter;
    }

    public byte[] toByteArray() {
    	return cw.toByteArray();
    }

    public void visitSource() {
        String sourceName = unit.getSourceCode().getName();
        int index = sourceName.lastIndexOf("\\");
        if (index != -1) {
            sourceName = sourceName.substring(index + 1);
        }
        else {
            index = sourceName.lastIndexOf("/");
            if (index != -1) {
                sourceName = sourceName.substring(index + 1);
            }
        }
        cv.visitSource(sourceName, null);
    }

    public void visitVariableWriters() {
    	List<ScopeVariableWriter> variableWriters = getVariableWriterList();
    	for (ScopeVariableWriter varWriter : variableWriters) {
    		varWriter.configVarScopeClass();
    	}
    }
    
    public void visitInnerClasses() {
        List<ClassNode> innerClassNodes = getInnerClassList();
        for (ClassNode innerClassNode : innerClassNodes) {
            visitInnerClass(innerClassNode);
        }
    }

    public void visitInnerClass(ClassNode classNode) {
        if (!(classNode instanceof InnerClassNode)) {
            return;
        }
        InnerClassNode innerClassNode = (InnerClassNode) classNode;
        String innerClassName = innerClassNode.getName();
        String innerClassInternalName = BytecodeUtil.getClassInternalName(innerClassNode);
        int index = innerClassName.lastIndexOf('$');
        if (index >= 0) innerClassName = innerClassName.substring(index + 1);
        String outerClassName = BytecodeUtil.getClassInternalName(innerClassNode.getOuterClassNode());
        cv.visitInnerClass(innerClassInternalName, outerClassName, innerClassName, innerClassNode.getModifier());
    }

    public void visitPattern() {
        MatchExpression pattern = thisClass.getPattern();
        if (pattern == null) {
            return;
        }
        BlockNode isCaseBlock = new BlockNode();
        isCaseBlock.addStatement(
                new ReturnStatement(
                        new OperationExpression(
                                new SoyaToken(SoyaParserTokenTypes.NOT_EQUAL),
                                pattern,
                                new VariableExpression("null"))));
        MethodNode isCaseMethod = new MethodNode(
                Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC,
                "isCase", ClassNode.OBJECT,
                new ParameterNode[] {
                    new ParameterNode(ClassNode.OBJECT, "obj")
                },
                isCaseBlock);
        thisClass.addMethod(isCaseMethod);
    }

    private boolean isLoggerField(FieldNode fieldNode) {
        return fieldNode.isStatic() && fieldNode.isStatic() && fieldNode.getName().equals("log");
    }
    
    public void visitFields() {
        boolean hasLogger = false;
    	List<FieldNode> fieldList = thisClass.getFieldList();
    	for (int i = 0; i < fieldList.size(); i++) {
    		FieldNode fieldNode = fieldList.get(i);
    		visitField(fieldNode);
            if (isLoggerField(fieldNode)) {
                hasLogger = true;
            }
    	}
        if (!hasLogger) {
            FieldNode loggerField = new FieldNode(
                    ACC_PUBLIC | ACC_STATIC | ACC_FINAL,
                    "log",
                    new MethodCallExpression(ClassNode.make(SoyaShell.class),
                            new ConstantExpression("getLogger"),
                            new ArgumentListExpression(new ClassExpression(thisClass))));
            thisClass.addField(loggerField);
            visitField(loggerField);
        }
    }
    
    public void visitField(FieldNode fieldNode) {
        Expression initExpression = fieldNode.getInitValue();
        Object initValue = null;
        if (initExpression instanceof ConstantExpression) {
            initValue = ((ConstantExpression) initExpression).getValue();
        }
		FieldVisitor fv =  cv.visitField(
                fieldNode.getModifier(),
                fieldNode.getName(),
                BytecodeUtil.getTypeDescription(Object.class),
                null,
                null);
        visitAnnotations(fieldNode, fv);
		fv.visitEnd();
    }

    public void visitProperties() {
    	List<PropertyNode> propertyList = thisClass.getPropertyList();
    	for (int i = 0; i < propertyList.size(); i++) {
    		PropertyNode propertyNode = propertyList.get(i);
    		visitProperty(propertyNode);
    	}
    }

    public void visitProperty(PropertyNode propertyNode) {
        String propertyName = propertyNode.getName();
        if (!thisClass.getSuperClassNode().containsField(propertyName)) {
            thisClass.addField(new FieldNode(ACC_PRIVATE, propertyName, propertyNode.getInitValue()));
        }

        if (propertyNode.hasGetter()) {
            visitGetter(propertyNode);
        }
        if (propertyNode.hasSetter()) {
            visitSetter(propertyNode);
        }
    }
    
    public void visitSetter(PropertyNode propertyNode) {
    	String propertyName = propertyNode.getName();
    	String setterName = NameUtil.toSetterName(propertyName);
        if (thisClass.containsMethod(setterName, 1)) {
            return;
        }
        Method setterMethod = Method.getMethod("java.lang.Object " + setterName + " (java.lang.Object)");
        GeneratorAdapter ga = new GeneratorAdapter(ACC_PUBLIC, setterMethod, null, null, cw);
        ga.loadThis();
        ga.loadArg(0);
        ga.putField(TypeUtil.getType(thisClass), propertyName, TypeUtil.OBJECT_TYPE);
        ga.loadThis();
        ga.returnValue();
        ga.endMethod();
    }
    
    public void visitGetter(PropertyNode propertyNode) {
    	String propertyName = propertyNode.getName();
    	String getterName = NameUtil.toGetterName(propertyName);
        if (thisClass.containsMethod(getterName, 0)) {
            return;
        }
    	Method setterMethod = Method.getMethod("java.lang.Object " + getterName + " ()");
        GeneratorAdapter ga = new GeneratorAdapter(ACC_PUBLIC, setterMethod, null, null, cw);
        ga.loadThis();
        ga.getField(TypeUtil.getType(thisClass), propertyName, TypeUtil.OBJECT_TYPE);
        ga.returnValue();
        ga.endMethod();
    }

    public void visitAnnotations(AnnotatedNode node, Object visitor) {
        List<AnnotationNode> annotationNodeList = node.getAnnotationList();
        for (AnnotationNode annotationNode : annotationNodeList) {
            ClassNode type = annotationNode.getAnnotationType();
            AnnotationVisitor annotationVisitor = getAnnotationVisitor(type, visitor);
            Iterator ait = annotationNode.getAttributes().keySet().iterator();
            while (ait.hasNext()) {
                String name = (String) ait.next();
                Expression expression = annotationNode.getAttributes().get(name);
                if (expression instanceof ConstantExpression) {
                    annotationVisitor.visit(name, ((ConstantExpression) expression).getValue());
                }
                else if (expression instanceof PropertyExpression) {
                    PropertyExpression propertyExpression = (PropertyExpression)expression;
                    ClassNode propObjType = propertyExpression.getObj().getType();
                    ConstantExpression propExpression = (ConstantExpression) propertyExpression.getProperty();
                    annotationVisitor.visitEnum(null,
                            BytecodeUtil.getTypeDescription(propObjType), String.valueOf(propExpression.getValue()));
                }
            }
            annotationVisitor.visitEnd();
        }
    }

    public AnnotationVisitor getAnnotationVisitor(ClassNode type, Object visitor) {
        String desc = BytecodeUtil.getTypeDescription(type);
        if (visitor instanceof ClassVisitor) {
            return ((ClassVisitor) visitor).visitAnnotation(desc, type.isRetention());
        }
        else if (visitor instanceof FieldVisitor) {
            return ((FieldVisitor) visitor).visitAnnotation(desc, type.isRetention());
        }
        else if (visitor instanceof MethodVisitor) {
            return ((MethodVisitor) visitor).visitAnnotation(desc, type.isRetention());
        }
        else if (visitor instanceof MethodWriter) {
            return ((MethodWriter) visitor).visitAnnotation(desc, type.isRetention());
        }
        return null;
    }


    public void visitInitializeStaticFields(GeneratorAdapter ga, WriterVisitor wv) {
        List<FieldNode> fieldNodes = thisClass.getFieldList();
        for (int i = 0; i < fieldNodes.size(); i++) {
            FieldNode fieldNode = fieldNodes.get(i);
            if (fieldNode.isStatic()) {
                visitInitializeField(fieldNode, ga, wv);
            }
        }
    }

    public void visitInitializeFields(GeneratorAdapter ga, WriterVisitor wv) {
    	List<FieldNode> fieldNodes = thisClass.getFieldList();
    	for (int i = 0; i < fieldNodes.size(); i++) {
    		FieldNode fieldNode = fieldNodes.get(i);
            if (!fieldNode.isStatic()) {
                visitInitializeField(fieldNode, ga, wv);
            }
    	}
    }
    
    public void visitInitializeField(FieldNode fieldNode, GeneratorAdapter ga, WriterVisitor wv) {
        String propertyName = fieldNode.getName();
        Expression initExpression = fieldNode.getInitValue();

        if (initExpression == null) {
            return;
        }

        if (thisClass.containsField(propertyName) || thisClass.getSuperClassNode().containsField(propertyName)) {
            wv.setField(fieldNode, fieldNode.getInitValue());
        }
    }

    public void visitInitializeProperties(GeneratorAdapter ga, WriterVisitor wv) {
        List<PropertyNode> propertyList = thisClass.getPropertyList();
        for (int i = 0; i < propertyList.size(); i++) {
            PropertyNode propertyNode = propertyList.get(i);
            visitInitializeProperty(propertyNode, ga, wv);
        }
    }

    public void visitInitializeProperty(PropertyNode propertyNode, GeneratorAdapter ga, WriterVisitor wv) {
        String propertyName = propertyNode.getName();
        Expression initExpression = propertyNode.getInitValue();

        if (initExpression == null) {
            return;
        }

        if (thisClass.containsField(propertyName) || thisClass.getSuperClassNode().containsField(propertyName)) {
            wv.setFieldValueFromThis(propertyNode.getName(), propertyNode.getInitValue(),
                    TypeUtil.getType(thisClass), TypeUtil.OBJECT_TYPE);
        }
    }

    public void visitStaticInitializerMethod() {
        if (thisClass instanceof InnerClassNode && ((InnerClassNode) thisClass).isVarScopeClass()) {
            return;
        }
        Method clmethod = MethodUtil.getStaticInitializerMethod();
        GeneratorAdapter ga = new GeneratorAdapter(ACC_PUBLIC | ACC_STATIC, clmethod, null, null, cw);
        MethodNode methodNode = new MethodNode(ACC_PUBLIC | ACC_STATIC, "<clinit>", ClassNode.VOID, new ParameterNode[0], null);
        WriterVisitor wv = new WriterVisitor(
                this, unit, ga, null,
                new SymbolTable(ScopeType.SCOPE_METHOD),
                thisClass, methodNode);
        wv.getStackFrame().pushVariableScope(wv.getRootSymbolTable());
        visitInitializeStaticFields(ga, wv);
        ga.returnValue();
        ga.endMethod();
        wv.getStackFrame().popVariableScope();
    }


    public void visitConstructors() {
        List<ConstructorNode> cons = thisClass.getConstructorList();
        if (cons.isEmpty()) {
            Class superClass = thisClass.getSuperClassNode().getJclass();
            Constructor[] cs = superClass.getDeclaredConstructors();
            for (int i = 0; i < cs.length; i++) {
                Constructor c = cs[i];
                if ((c.getModifiers() & Modifier.PUBLIC) == 1) {
                    Class[] paramTypes = c.getParameterTypes();
                    visitConstructor(new ConstructorNode(c));
                }
            }
        }
        else {
            for (ConstructorNode con : cons) {
                visitConstructor(con);
            }
        }
    }

    public void visitConstructor(ConstructorNode constructorNode) {
        MethodWriter mw = new MethodWriter(this, constructorNode);
        mw.visitConstructor();
    }

    public void visitMethod(MethodNode methodNode) {
        MethodWriter mw = new MethodWriter(this, methodNode);
        visitAnnotations(methodNode, mw);
        mw.visitMethod();
    }
    
}
