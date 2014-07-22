package org.soya.ast;

import org.soya.antlr.SyntaxException;
import org.soya.ast.stmt.Statement;
import org.soya.codegen.ClassGenerator;
import soya.lang.SoyaClassLoader;
import org.soya.tools.SourceCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author: Jun Gong
 */
public class CompilationUnit extends TreeNode {
    private String packageName;
    private String currentClassName;
    private ClassNode currentClassNode;
    private Map<String, ImportNode> imports = new HashMap<String, ImportNode>();
    private List<ImportNode> starImports = new ArrayList<ImportNode>();
    private List<EnvironmentNode> environments = new ArrayList<EnvironmentNode>();
    private Map<String, ClassNode> classes = new HashMap<String, ClassNode>();
    private Map<String, FieldNode> fields = new HashMap<String, FieldNode>();
    private List<MethodNode> methods = new ArrayList<MethodNode>();
    private BlockNode block = new BlockNode();
    private SourceCode sourceCode;
    private ClassNode mainClassNode;
    private ClassNode scriptClassNode;
    private SoyaClassLoader classLoader;
    private List<Class> targetClasses = new ArrayList<Class>();
    private Class targetClass;

    public CompilationUnit(SourceCode sourceCode, SoyaClassLoader classLoader) {
        this.sourceCode = sourceCode;
        this.classLoader = classLoader;
        addImport(new ImportNode(ClassNode.SYSTEM, "System"));
        addImport(new ImportNode(ClassNode.OBJECT, "Object"));
        addImport(new ImportNode(ClassNode.OBJECT, "_"));
        addImport(new ImportNode(ClassNode.PEmpty, "Empty"));
        addImport(new ImportNode(ClassNode.PINT, "int"));
        addImport(new ImportNode(ClassNode.PFLOAT, "float"));
        addImport(new ImportNode(ClassNode.PSTRING, "String"));
        addImport(new ImportNode(ClassNode.PLINE, "Line"));
        addImport(new ImportNode(ClassNode.LANG_INTEGER, "Integer"));
        addImport(new ImportNode(ClassNode.LANG_FLOAT, "Float"));
        addImport(new ImportNode(ClassNode.LANG_NUMBER, "Number"));
        addImport(new ImportNode(ClassNode.PDATE, "Date"));
        addImport(new ImportNode(ClassNode.PDURATION, "Duration"));
        addImport(new ImportNode(ClassNode.RANGE, "Range"));
        addImport(new ImportNode(ClassNode.PTUPLE, "Tuple"));
        addImport(new ImportNode(ClassNode.FILE, "File"));
        addImport(new ImportNode(ClassNode.PURL, "URL"));
        addImport(new ImportNode(ClassNode.LIST, "List"));
        addImport(new ImportNode(ClassNode.MAP, "Map"));
        addImport(new ImportNode(ClassNode.REGEX_PATTERN, "RegexPattern"));
        addImport(new ImportNode(ClassNode.OVERRIDE, "Override"));
        addImport(new ImportNode(ClassNode.PINDEX, "Index"));
        addImport(new ImportNode(ClassNode.PKEY, "Key"));
        addStarImport(new ImportNode("java.lang"));
        addStarImport(new ImportNode("java.io"));
        addStarImport(new ImportNode("java.math"));
        addStarImport(new ImportNode("java.net"));
        addStarImport(new ImportNode("java.util"));
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getCurrentClassName() {
        return currentClassName;
    }

    public void setCurrentClassName(String currentClassName) {
        this.currentClassName = currentClassName;
    }

    public ClassNode getCurrentClassNode() {
        return currentClassNode;
    }

    public void setCurrentClassNode(ClassNode currentClassNode) {
        this.currentClassNode = currentClassNode;
    }

    public void generateClass(ClassNode classNode) throws ClassNotFoundException {
        ClassGenerator cg = new ClassGenerator(this, classLoader);
        cg.visitClass(classNode);

        sourceCode.getErrorList().interruptIfHasErrors();

        Class clazz = classLoader.defineClass(classNode.getName(), cg.toByteArray());
        
        targetClasses.add(clazz);
        
        if (targetClass == null) {
        	targetClass = clazz;
        }
        
        LinkedList<ClassNode> innerClasses = cg.getInnerClassList();

        while (!innerClasses.isEmpty()) {
        	ClassNode innerClass = innerClasses.removeFirst();
        	//System.out.println("generate inner class: " + innerClass);
        	generateClass(innerClass);
        }
    }

    public void addImport(ImportNode importNode) {
        imports.put(importNode.getAlias(), importNode);
    }

    public void addStarImport(ImportNode startImportNode) {
        starImports.add(startImportNode);
    }

    public void addClassList(List<ClassNode> classes) {
        for (ClassNode clz : classes) {
            addClass(clz);
        }
    }

    public void addClass(ClassNode clazz) {
        String name = clazz.getName();
        TreeNode chachedClass = classes.get(name);
        if (chachedClass != null) {
            String message = "Invalid duplicate class definition of class " + clazz.getName();
            sourceCode.getErrorList().addException(new SyntaxException(sourceCode.getName(),
                    message, clazz.getLine(), clazz.getColumn()));
        }
        else {
            classes.put(name, clazz);
            imports.put(clazz.getShortName(), new ImportNode(clazz, clazz.getShortName()));
        }
    }

    public void addField(FieldNode fieldNode) {
        String name = fieldNode.getName();
        FieldNode cachedField = fields.get(name);
        if (cachedField != null) {
            String message = "Field '" + fieldNode.getName() + "' is aleady in the scope";
            sourceCode.getErrorList().addException(new SyntaxException(sourceCode.getName(),
                    message, fieldNode.getLine(), fieldNode.getColumn()));
        }
        else {
            fields.put(name, fieldNode);
        }
    }

    public void addMethod(MethodNode methodNode) {
        methods.add(methodNode);
    }

    public void addStatement(Statement statement) {
        block.addStatement(statement);
    }

    public ClassNode getMainClassNode() {
        return mainClassNode;
    }

    public void setMainClassNode(ClassNode mainClassNode) {
        this.mainClassNode = mainClassNode;
    }

    public ClassNode getScriptClassNode() {
        return scriptClassNode;
    }

    public void setScriptClassNode(ClassNode scriptClassNode) {
        this.scriptClassNode = scriptClassNode;
        this.mainClassNode = scriptClassNode;
    }

    public Map<String, ImportNode> getImports() {
        return imports;
    }

    public List<ImportNode> getStarImports() {
        return starImports;
    }

    public List<EnvironmentNode> getEnvironments() {
        return environments;
    }

    public Map<String, ClassNode> getClasses() {
        return classes;
    }

    public List<MethodNode> getMethods() {
        return methods;
    }

    public SourceCode getSourceCode() {
        return sourceCode;
    }

    public BlockNode getBlock() {
        return block;
    }

	public List<Class> getTargetClasses() {
		return targetClasses;
	}

	public Class getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(Class targetClass) {
		this.targetClass = targetClass;
	}

    public SoyaClassLoader getClassLoader() {
        return classLoader;
    }
}