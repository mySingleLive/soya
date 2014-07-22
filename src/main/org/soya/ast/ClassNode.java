package org.soya.ast;


import org.objectweb.asm.Opcodes;
import org.soya.ast.expr.MatchExpression;
import org.soya.runtime.SoyaShell;
import soya.lang.*;
import org.soya.runtime.VarScope;
import org.soya.util.NameUtil;
import soya.lang.Float;
import soya.util.pattern.*;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * @author: Jun Gong
 */
public class ClassNode extends AnnotatedNode {

    public static ClassNode VOID = new ClassNode(Void.TYPE, null);
    public static ClassNode OBJECT = new ClassNode(Object.class, null);
    public static ClassNode STRING = new ClassNode(String.class);
    public static ClassNode BOOLEAN = new ClassNode(boolean.class);
    public static ClassNode BYTE = new ClassNode(byte.class);
    public static ClassNode CHAR = new ClassNode(char.class);
    public static ClassNode INT = new ClassNode(int.class);
    public static ClassNode SHORT = new ClassNode(short.class);
    public static ClassNode LONG = new ClassNode(long.class);
    public static ClassNode FLOAT = new ClassNode(float.class);
    public static ClassNode DOUBLE = new ClassNode(double.class);
    public static ClassNode LANG_INTEGER = new ClassNode(Integer.class);
    public static ClassNode LANG_FLOAT = new ClassNode(java.lang.Float.class);
    public static ClassNode LANG_NUMBER = new ClassNode(Number.class);
    public static ClassNode BIG_INTEGER = new ClassNode(BigInteger.class);
    public static ClassNode BIG_DECIMAL = new ClassNode(BigDecimal.class);
    public static ClassNode OVERRIDE = new ClassNode(Override.class);
    public static ClassNode DATE = new ClassNode(Date.class);
    public static ClassNode URL = new ClassNode(java.net.URL.class);
    
    public static ClassNode SYSTEM = new ClassNode(java.lang.System.class);

    public static ClassNode SCRIPT = new ClassNode(Script.class);
    public static ClassNode FILE = new ClassNode(File.class, OBJECT);
    public static ClassNode COLLECTION = new ClassNode(SoyaCollection.class);
    public static ClassNode LIST = new ClassNode(List.class);
    public static ClassNode MAP = new ClassNode(Map.class);
    public static ClassNode RANGE = new ClassNode(Range.class);
    public static ClassNode PATTERN = new ClassNode(Pattern.class);
    public static ClassNode META_CLASS = new ClassNode(MetaClass.class);

    public static ClassNode OBJECT_ARRAY = new ClassNode(Object[].class);
    public static ClassNode STRING_ARRAY = new ClassNode(String[].class);
    public static ClassNode INT_ARRAY = new ClassNode(int[].class);

    public static ClassNode POBJECT = new ClassNode(SoyaObject.class);
    public static ClassNode PEVAL_OBJECT = new ClassNode(EvalObject.class);
    public static ClassNode PNULL = new ClassNode(Null.class, PEVAL_OBJECT);
    public static ClassNode PEmpty = new ClassNode(Empty.class, PEVAL_OBJECT);
    public static ClassNode PINT = new ClassNode(Int.class, POBJECT);
    public static ClassNode PFLOAT = new ClassNode(Float.class, POBJECT);
    public static ClassNode PSTRING = new ClassNode(SoyaString.class, POBJECT);
    public static ClassNode PREGEX_PATTERN = new ClassNode(RegexPattern.class, POBJECT);
    public static ClassNode REGEX_PATTERN = new ClassNode(RegexPattern.class, PATTERN);
    public static ClassNode PLINE = new ClassNode(StringLine.class, PSTRING);
    public static ClassNode PTUPLE = new ClassNode(Tuple.class, POBJECT);
    public static ClassNode PLIST = new ClassNode(SoyaList.class, POBJECT);
    public static ClassNode PMAP = new ClassNode(SoyaMap.class, POBJECT);
    public static ClassNode PClosure = new ClassNode(Closure.class, POBJECT);
    public static ClassNode PVarScope = new ClassNode(VarScope.class, POBJECT);
    public static ClassNode PURL = new ClassNode(SoyaURL.class, POBJECT);
    public static ClassNode PFILE = new ClassNode(SoyaFile.class, POBJECT);
    public static ClassNode POBJECT_RANGE = new ClassNode(ObjectRange.class, POBJECT);
    public static ClassNode POBJECT_PATTERN = new ClassNode(ObjectPattern.class, POBJECT);
    public static ClassNode PCLASS_PATTERN = new ClassNode(ClassPattern.class, POBJECT);
    public static ClassNode POR_PATTERN = new ClassNode(Or.class, POBJECT);
    public static ClassNode PAND_PATTERN = new ClassNode(And.class, POBJECT);
    public static ClassNode PNOT_PATTERN = new ClassNode(NotPattern.class, POBJECT);
    public static ClassNode PDATE = new ClassNode(SoyaDate.class, POBJECT);
    public static ClassNode PDURATION = new ClassNode(TimeDuration.class, POBJECT);
    public static ClassNode SHELL = new ClassNode(SoyaShell.class, POBJECT);
    public static ClassNode PINDEX = new ClassNode(Index.class, POBJECT);
    public static ClassNode PKEY = new ClassNode(Key.class, POBJECT);


    public static ClassNode[] classes = new ClassNode[] {
            OBJECT, STRING, BOOLEAN, BYTE, CHAR, INT, SHORT, LONG, FLOAT,
            DOUBLE, BIG_INTEGER, BIG_DECIMAL, SCRIPT, LIST, MAP, RANGE,
            DATE, PDURATION
    };


    private String packageName;
    private String name;
    private ClassNode superClassNode;
    private Map<String, PropertyNode> properties = new HashMap<String, PropertyNode>();
    private List<PropertyNode> propertyList = new ArrayList<PropertyNode>();
    private Map<String, List<MethodNode>> methods = new HashMap<String, List<MethodNode>>();
    private List<MethodNode> methodList = new ArrayList<MethodNode>();
    private List<ConstructorNode> constructorList = new ArrayList<ConstructorNode>();
    private Map<String, FieldNode> fields = new HashMap<String, FieldNode>();
    private List<FieldNode> fieldList = new ArrayList<FieldNode>();
    private ClassNode[] interfaces;
    private List<InnerClassNode> innerClassList = new ArrayList<InnerClassNode>();
    private CompilationUnit compilationUnit;
    private MatchExpression pattern;
    private boolean isInited = false;
    private boolean isInterface = false;
    private boolean isAnnotation = false;
    private boolean isRetention = false;
    private boolean isArray = false;
    private boolean isPatternClass = false;
    private boolean script = false;
    private Class jclass;

    public static ClassNode make(String name) {
        if (name == null || name.length() == 0) {
            return OBJECT;
        }
        for (int i = 0; i < classes.length; i++) {
            ClassNode cls = classes[i];
            if (cls.getName().equals(name)) {
                return cls;
            }
        }
        Class clazz = null;
        try {
            clazz = SoyaClassLoader.getSystemClassLoader().loadClass(name);
        } catch (ClassNotFoundException e) {
        }
        if (clazz != null) {
            return new ClassNode(clazz);
        }
        return new ClassNode(name, Modifier.PUBLIC, OBJECT);
    }
    
    public static ClassNode make(Class clazz) {
        for (int i = 0; i < classes.length; i++) {
            ClassNode cls = classes[i];
            if (cls.getName().equals(clazz.getName())) {
                return cls;
            }
        }
        if (ClassNodeUtil.cachedClassNodes.containsKey(clazz.getName())) {
            return ClassNodeUtil.cachedClassNodes.get(clazz.getName());
        }
        ClassNode classNode = new ClassNode(clazz);
        ClassNodeUtil.cachedClassNodes.put(clazz.getName(), classNode);
        return classNode;
    }


    public ClassNode(Class jclass) {
        this(jclass, OBJECT);
    }
    
    public ClassNode(Class jclass, ClassNode superClassNode) {
        this(jclass.getName(), jclass.getModifiers(), superClassNode, jclass.isArray(), jclass.isInterface(), jclass.isAnnotation());
        this.jclass = jclass;
        Annotation[] annotations = this.jclass.getAnnotations();
        for (Annotation ann : annotations) {
            if (ann instanceof Retention) {
                isRetention = true;
            }
        }

    }

    public ClassNode(String name, int modifier, ClassNode superClassNode) {
        this(name, modifier, superClassNode, false);
    }

    public ClassNode(String name, int modifier, ClassNode superClassNode, boolean array) {
        this(name, modifier, superClassNode, array, false, false);
    }

    public ClassNode(String name, int modifier, ClassNode superClassNode, boolean array, boolean isInterface, boolean isAnnotation) {
        this.name = name;
/*
        try {
            this.jclass = Class.forName(name);
        } catch (ClassNotFoundException e) {
        }
        if (this.jclass != null) {
            this.modifier = this.jclass.getModifiers();
            this.isInterface = this.jclass.isInterface();
            this.isAnnotation = this.jclass.isAnnotation();
        }
        else {
*/
            this.modifier = modifier;
            this.superClassNode = superClassNode;
            this.isArray = array;
            this.isInterface = isInterface;
            this.isAnnotation = isAnnotation;
//        }
    }


    public void initClassNode() {
        if (isInited || jclass == null) {
            return;
        }
        isInited = true;
    	Field[] fields = jclass.getDeclaredFields();
    	for (Field f : fields) {
    		addField(new FieldNode(f.getModifiers(), f.getName(), null));
    	}
    	
    	Method[] methods = jclass.getDeclaredMethods();
    	for (Method m : methods) {
    		Class[] paramTypes = m.getParameterTypes();
    		ParameterNode[] params = new ParameterNode[paramTypes.length];
    		for (int i = 0; i < paramTypes.length; i++) {
    			Class pt = paramTypes[i];
    			params[i] = new ParameterNode(ClassNode.make(pt), "p" + i);
    		}
    		addMethod(new MethodNode(m.getModifiers(), m.getName(), ClassNode.make(m.getReturnType()), params, null));
    	}

        Constructor[] cons = this.jclass.getConstructors();
        for (Constructor con : cons) {
            ConstructorNode constructorNode = new ConstructorNode(con);
            this.addConstructor(constructorNode);
        }
    }
    
    public boolean containsField(String fieldName) {
        initClassNode();
        FieldNode fieldNode = fields.get(fieldName);
        if (fieldNode == null) {
            return false;
        }
        return true;
    }

    public boolean containsStaticField(String fieldName) {
        initClassNode();
        FieldNode fieldNode = fields.get(fieldName);
        if (fieldNode.isStatic()) {
            return false;
        }
        if (fieldNode == null) {
            return false;
        }
        return true;
    }

    public boolean containsFieldWidthSuperClass(String fieldName) {
        if (containsField(fieldName)) {
            return true;
        }
        if (superClassNode != null) {
            return superClassNode.containsFieldWidthSuperClass(fieldName);
        }
        return false;
    }

    public boolean containsStaticFieldWidthSuperClass(String fieldName) {
        if (containsStaticField(fieldName)) {
            return true;
        }
        if (superClassNode != null) {
            return superClassNode.containsFieldWidthSuperClass(fieldName);
        }
        return false;
    }


    public FieldNode findField(String fieldName) {
        FieldNode fieldNode = fields.get(fieldName);
        if (fieldNode != null) {
            return fieldNode;
        }
        if (superClassNode != null) {
            return superClassNode.findField(fieldName);
        }
        return null;
    }

    public void addField(FieldNode fieldNode) {
    	if (!containsField(fieldNode.getName())) {
    		fieldList.add(fieldNode);
    		fields.put(fieldNode.getName(), fieldNode);
    	}
    }
    
    public List<FieldNode> getFieldList() {
		return fieldList;
	}

    public FieldNode getFieldNode(String fieldName) {
        return fields.get(fieldName);
    }

    public boolean containsGetterAndSetter(String propertyName) {
        return containsSetter(propertyName) && containsGetter(propertyName);
    }
    
    public boolean containsSetter(String propertyName) {
    	if (properties.containsKey(propertyName)) {
    		if (properties.get(propertyName).hasSetter()) {
    			return true;
    		}
    	}
    	if (containsMethod(NameUtil.toSetterName(propertyName))) {
    		return true;
    	}
    	return false;
    }
    
    public boolean containsGetter(String propertyName) {
    	if (properties.containsKey(propertyName)) {
    		if (properties.get(propertyName).hasGetter()) {
    			return true;
    		}
    	}
    	if (containsMethod(NameUtil.toGetterName(propertyName))) {
    		return true;
    	}
    	return false;
    }


	public void addProperty(PropertyNode propertyNode) {
    	propertyNode.setClassNode(this);
    	String propertyName = propertyNode.getName();
    	if (!properties.containsKey(propertyName)) {
    		properties.put(propertyName, propertyNode);
    		propertyList.add(propertyNode);
    	}
    }
	
	public boolean containsMethod(String methodName) {
        return containsMethod(methodName, -1);
	}


    public boolean containsStaticMethod(String methodName, int paramNum) {
        initClassNode();
        List<MethodNode> methodNodeList = methods.get(methodName);
        if (methodNodeList == null) {
            return false;
        }
        for (MethodNode methodNode : methodNodeList) {
            if (!methodNode.isStatic()) {
                continue;
            }
            if (paramNum == -1) {
                return true;
            }
            if (methodNode.getParameters().length == paramNum) {
                return true;
            }
        }
        return false;
    }

    public boolean containsMethod(String methodName, int paramNum) {
        initClassNode();
        List<MethodNode> methodNodeList = methods.get(methodName);
        if (methodNodeList == null) {
            return false;
        }
        for (MethodNode methodNode : methodNodeList) {
            if (methodNode.isStatic()) {
                continue;
            }
            if (paramNum == -1) {
                return true;
            }
            if (methodNode.getParameters().length == paramNum) {
                return true;
            }
        }
        return false;
    }

    public boolean containsMethodWithSuperClass(String methodName, int paramNum) {
        if (containsMethod(methodName, paramNum)) {
            return true;
        }
        if (superClassNode != null) {
            return superClassNode.containsMethodWithSuperClass(methodName, paramNum);
        }
        return false;
    }

    public boolean containsStaticMethodWithSuperClass(String methodName, int paramNum) {
        if (containsStaticMethod(methodName, paramNum)) {
            return true;
        }
        if (superClassNode != null) {
            return superClassNode.containsStaticMethodWithSuperClass(methodName, paramNum);
        }
        return false;
    }


    public void addMethod(MethodNode methodNode) {
        methodNode.setClassNode(this);
        String methodName = methodNode.getName();
        if (methods.containsKey(methodName)) {
            methods.get(methodName).add(methodNode);
        }
        else  {
            List<MethodNode> methodList = new ArrayList<MethodNode>();
            methodList.add(methodNode);
            methods.put(methodName, methodList);
        }
        methodList.add(methodNode);
    }

    public Object newInstance() {
        return null;
    }


    public String getName() {
        if (packageName != null && !packageName.isEmpty()) {
            return packageName + '.' + name;
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getShortName() {
        String name = getName();
        int idx = name.lastIndexOf('.');
        if (idx > 0) {
            return name.substring(idx + 1);
        }
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public ClassNode getSuperClassNode() {
        return superClassNode;
    }

    public void setSuperClassNode(ClassNode superClassNode) {
        this.superClassNode = superClassNode;
    }
    
	public ClassNode getOutermostClassNode() {
		return this;
	}

    public MatchExpression getPattern() {
        return pattern;
    }

    public void setPattern(MatchExpression pattern) {
        this.pattern = pattern;
    }

    public List<PropertyNode> getPropertyList() {
        return propertyList;
    }

    public ClassNode[] getInterfaces() {
        return interfaces;
    }

    public CompilationUnit getCompilationUnit() {
        return compilationUnit;
    }

    public boolean isInterface() {
        return isInterface;
    }

    public boolean isAnnotation() {
        return isAnnotation;
    }

    public boolean isRetention() {
        return isRetention;
    }

    public boolean isArray() {
        return isArray;
    }

    public boolean isPatternClass() {
        return isPatternClass;
    }

    public void setPatternClass(boolean patternClass) {
        isPatternClass = patternClass;
        if (isPatternClass) {
            modifier = modifier | Opcodes.ACC_ABSTRACT;
        }
    }

    public boolean isScript() {
        return script;
    }

    public void setScript(boolean script) {
        this.script = script;
    }

    public Class getJclass() {
        return jclass;
    }

    public void setJclass(Class jclass) {
        if (this.jclass != jclass) {
            this.jclass = jclass;
            this.initClassNode();
        }
	}

	public List<ConstructorNode> getConstructorList() {
        return constructorList;
    }

    public void addConstructor(ConstructorNode constructorNode) {
        constructorList.add(constructorNode);
    }

    public List<InnerClassNode> getInnerClassList() {
        return innerClassList;
    }

    public void addInnerClass(InnerClassNode innerClassNode) {
        innerClassList.add(innerClassNode);
    }

    public boolean equals(Object obj) {
    	if (!(obj instanceof ClassNode)) {
    		return false;
    	}
    	if (this == obj) {
    		return true;
    	}
    	ClassNode node = (ClassNode) obj;
    	if (getName().equals(node.getName())) {
    		return true;
    	}
    	return false;
    }
    
    public boolean isKindOfClass(ClassNode classNode) {
    	if (this.equals(classNode)) {
    		return true;
    	}
    	if (superClassNode == null) {
    		return false;
    	}
    	if (superClassNode.equals(classNode)) {
    		return true;
    	}
    	return superClassNode.isKindOfClass(classNode);
    }

    public Map<String, List<MethodNode>> getMethods() {
        return methods;
    }

    public List<MethodNode> getMethodList() {
        return methodList;
    }
    
    public Map<String, PropertyNode> getProperties() {
		return properties;
	}

	public String toString() {
        StringBuffer buffer = new StringBuffer("[class: ");
        buffer.append(getName());
        ClassNode superClassNode = getSuperClassNode();
        if (superClassNode != null) {
            buffer.append(" extends ");
            buffer.append(superClassNode.getName());
        }
        if (propertyList.size() > 0) {
            buffer.append(" proerties: ");
            buffer.append(propertyList);
        }
        buffer.append("]");
        return buffer.toString();
    }
}
