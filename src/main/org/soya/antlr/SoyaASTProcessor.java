package org.soya.antlr;

import antlr.collections.AST;
import org.objectweb.asm.Opcodes;
import org.soya.antlr.parser.SoyaParser;
import org.soya.antlr.util.DateTimeUtil;
import org.soya.antlr.util.TupleUtil;
import org.soya.ast.*;
import org.soya.ast.expr.*;
import org.soya.ast.expr.MatchExpression;
import org.soya.ast.stmt.*;
import soya.lang.Null;
import soya.lang.SoyaClassLoader;
import org.soya.tools.SourceCode;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.soya.antlr.parser.SoyaParserTokenTypes.*;

/**
 * @author: Jun Gong
 */
public class SoyaASTProcessor {

    private AST cst;

    private CompilationUnit ast;

    private SourceCode source;

    private SoyaClassLoader classLoader;

    public SoyaASTProcessor(AST cst, SourceCode source, SoyaClassLoader classLoader) {
        this.cst = cst;
        this.source = source;
        this.classLoader = classLoader;
    }

    public CompilationUnit processASTFromCST() throws SyntaxException {
        ast = new CompilationUnit(source, classLoader);
        ClassNode scriptClassNode = createScipteClass();
        ast.setCurrentClassNode(scriptClassNode);
        ast.setCurrentClassName(scriptClassNode.getShortName());

        if (cst.getType() == COMPILATION_UNIT) {
            AST child = cst.getFirstChild();
            if (child.getType() == PACKAGE) {
                defPackage(child);
                child = child.getNextSibling();
            }
            if (child.getType() == STATEMENTS) {
                for (child = child.getFirstChild(); child  != null; child = child.getNextSibling()) {
                    convertAST(child);
                }
            }
        }
        Map<String, ClassNode> classes = ast.getClasses();
        if (classes.isEmpty()) {
            ast.setScriptClassNode(scriptClassNode);
            List<MethodNode> methodNodes = ast.getMethods();
            for (MethodNode method : methodNodes) {
                scriptClassNode.addMethod(method);
            }
            MethodNode runMethodNode = new MethodNode(Opcodes.ACC_PUBLIC, "run", ClassNode.OBJECT,
                    new ParameterNode[] {new ParameterNode(ClassNode.OBJECT_ARRAY, "arguments")},
                    ast.getBlock());
            scriptClassNode.addMethod(runMethodNode);


            MethodNode mainMethodNode = new MethodNode(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "main", ClassNode.VOID,
                    new ParameterNode[] {new ParameterNode(ClassNode.STRING_ARRAY, "args")},
                    new BlockNode(new ExpressionStatement(
                            new MethodCallExpression(
                                    ClassNode.SHELL,
                                    new ConstantExpression("doScriptMain"),
                                    new ArgumentListExpression(
                                            new ClassExpression(scriptClassNode),
                                            new VariableExpression("args")))
                    )));
            scriptClassNode.addMethod(mainMethodNode);

        }
        return ast;
    }

    private ClassNode createScipteClass() {
        String name = source.getName();
        int len = name.length();
        String pkgName = null;
        int lastDotIndex = name.lastIndexOf(".");

        String ext = name.substring(lastDotIndex, len);
        if (ext.equals("." + source.getConfiguration().getExtension())) {
            name = name.substring(0, lastDotIndex);
            name = name.replace('/', '.');
            name = name.replace('\\', '.');

            lastDotIndex = name.lastIndexOf('.');
            if (lastDotIndex != -1) {
                pkgName = name.substring(0, lastDotIndex);
                name = name.substring(lastDotIndex + 1);
            }
        }

        ClassNode scriptClassNode = new ClassNode(name, Opcodes.ACC_PUBLIC, ClassNode.SCRIPT);
        scriptClassNode.setPackageName(pkgName);
        scriptClassNode.setScript(true);
        return scriptClassNode;
    }

    private void convertAST(AST cst) throws SyntaxException {
        int type = cst.getType();
        switch (type) {
            case IMPORT:
                defImport(cst);
                break;
            case CLASS:
                defClass(cst);
                break;
/*
            case FIELD:
                defField(cst);
                break;
*/
            case METHOD_DEF:
                defMethod(cst);
                break;
            default:
                Statement statement = statement(cst);
                ast.addStatement(statement);
                break;
        }
    }

    public void defPackage(AST cst) {
        AST child = cst.getFirstChild();
        String packageName = qualifiedName(child);
        ast.setPackageName(packageName);
    }

    private void defImport(AST cst) {
        assertCSTType(cst, IMPORT);
        AST child = cst.getFirstChild();

        AST packageNode = child.getFirstChild();
        String packageName = qualifiedName(packageNode);
        AST nameNode = packageNode.getNextSibling();
        if (nameNode.getType() == STAR) {
            ast.addStarImport(new ImportNode(packageName));
        }
        else {
            String name = nameNode.getText();
            ClassNode cls = ClassNode.make(packageName + "." + name);
            ImportNode importNode = new ImportNode(cls, name);
            importNode.config(cst);
            ast.addImport(importNode);
        }
    }

    public static String qualifiedName(AST cst) {
        if (cst.getType() == ID) {
            return cst.getText();
        }
        if (cst.getType() == DOT) {
            AST child = cst.getFirstChild();
            StringBuffer buffer = new StringBuffer();
            buffer.append(qualifiedName(child));
            child = child.getNextSibling();

            for (; child != null; child = child.getNextSibling()) {
                buffer.append(".");
                buffer.append(qualifiedName(child));
            }
            return buffer.toString();
        }
        else {
            return cst.getText();
        }
    }


    protected void defClass(AST cst) throws SyntaxException {
        assertCSTType(cst, CLASS);
        AST child = cst.getFirstChild();
        boolean isPattern = false;
        List<AnnotationNode> annotationNodes = null;
        if (child.getType() == ANNOTATION_LIST) {
            annotationNodes = annotationList(child);
            child = child.getNextSibling();
        }

        if (child.getType() == K_PATTERN) {
            isPattern = true;
            child = child.getNextSibling();
        }

        String className = child.getText();
        ast.setCurrentClassName(className);
        
        child = child.getNextSibling();
        ClassNode extendsClass = ClassNode.PEVAL_OBJECT;
        
        if (child.getType() == EXTENDS_TYPE) {
        	extendsClass = makeType(child);
        	child = child.getNextSibling();
        }

        ClassNode classNode = ast.getCurrentClassNode();
        if (classNode == null) {
            classNode = new ClassNode(className, Opcodes.ACC_PUBLIC, extendsClass);
            ast.setCurrentClassNode(classNode);
        }
        else {
            classNode.setName(className);
            classNode.setModifier(Opcodes.ACC_PUBLIC);
            classNode.setSuperClassNode(extendsClass);
            classNode.setScript(false);
        }
        classNode.setPatternClass(isPattern);
        classNode.setPackageName(packageName());

        if (annotationNodes != null) {
            for (AnnotationNode ann : annotationNodes) {
                classNode.addAnnotation(ann);
            }
        }

        if (child.getType() == MATCH_BLOCK) {
            MatchBlock matchBlock = matchBlock(child);
            MatchExpression pattern = new MatchExpression(null, matchBlock);
            classNode.setPattern(pattern);
            child = child.getNextSibling();
        }

        if (child != null) {
            for (AST statCst = child.getFirstChild() ; statCst != null; statCst = statCst.getNextSibling()) {
                switch (statCst.getType()) {
                    case CONSTRUCTOR_DEF:
                        defConstructor(statCst, classNode);
                        break;
                    case METHOD_DEF:
                        defMethod(statCst, classNode);
                        break;
                    case FIELD:
                        defField(statCst, classNode);
                        break;
                    case HASH_ENTRY:
                        classHashEntry(statCst, classNode);
                        break;
                }
            }
        }

        if (ast.getClasses().containsKey(className)) {
            throw new SyntaxException(source.getName(), "Duplicate class '" + className + "'", cst);
        }
        else {
            ast.addClass(classNode);
            if (ast.getMainClassNode() == null) {
                ast.setMainClassNode(classNode);
            }
        }
    }

    protected String packageName() {
        String pkg = ast.getPackageName();
        if (pkg != null) {
            return pkg;
        }

        String src = source.getName();
        int i = src.lastIndexOf('/');
        if (i < 0) {
            return null;
        }
        pkg = src.substring(0, i);
        File directory = new File("tmp");
        String path = null;
        try {
            path = directory.getCanonicalPath();
        } catch (IOException e) {
        }
        if (path == null) {
            return null;
        }
        pkg = pkg.substring(path.length() - 3);
        pkg = pkg.replace('/', '.');
        return pkg;
    }
    
    protected void classHashEntry(AST cst, ClassNode classNode) throws SyntaxException {
    	assertCSTType(cst, HASH_ENTRY);
    	AST child = cst.getFirstChild().getNextSibling();
    	String keyName = child.getText();
    	child = child.getNextSibling();
    	
    	if (keyName.equals("property")) {
    		defProperties(child, classNode, PropertyNode.PROPERTY_GETTER_SETTER);
    	}
    	else if (keyName.equals("readonly")) {
    		defProperties(child, classNode, PropertyNode.PROPERTY_GETTER_ONLY);
    	}
    	else if (keyName.equals("writeonly")) {
    		defProperties(child, classNode, PropertyNode.PROPERTY_SETTER_ONLY);
    	}
        else {
            defProperty(Opcodes.ACC_PUBLIC, cst, classNode, PropertyNode.PROPERTY_GETTER_SETTER);
        }
    }
    
    protected void defProperties(AST cst, ClassNode classNode, int mode) throws SyntaxException {
    	assertCSTType(cst, HASH);
    	for (AST child = cst.getFirstChild(); child != null; child = child.getNextSibling()) {
    		defProperty(Opcodes.ACC_PUBLIC, child, classNode, mode);
    	}
    }
    
    protected void defProperty(int modifier, AST cst, ClassNode classNode, int mode) throws SyntaxException {
    	assertCSTType(cst, HASH_ENTRY);
    	AST child = cst.getFirstChild().getNextSibling();
    	String proerptyName = child.getText();
    	child = child.getNextSibling();
    	Expression initValueExpr = expression(child);
    	PropertyNode propertyNode = new PropertyNode(modifier, proerptyName, mode, initValueExpr);
    	classNode.addProperty(propertyNode);
    }

    protected AnnotationNode annotation(AST cst) throws SyntaxException {
        assertCSTType(cst, ANNOTATION);
        AST child = cst.getFirstChild();
        ClassNode type = findTypeFromImported(child.getText());
        if (type == null) {
            throw new SyntaxException(source.getName(),
                    "Cannot resolve symbol '" + child.getText() + "'", child);
        }
        else if (!type.isAnnotation()) {
            throw new SyntaxException(source.getName(),
                    "expecting annotation type, found '" + type.getName() + "'", child);
        }
        AnnotationNode annotationNode = new AnnotationNode(type);
        return annotationNode;
    }

    protected List<AnnotationNode> annotationList(AST cst) throws SyntaxException {
        List<AnnotationNode> annotationList = new ArrayList<AnnotationNode>();
        assertCSTType(cst, ANNOTATION_LIST);
        for (AST child = cst.getFirstChild(); child != null; child = child.getNextSibling()) {
            AnnotationNode annotationNode = annotation(child);
            annotationList.add(annotationNode);
        }
        return annotationList;
    }

    protected void defField(AST cst) throws SyntaxException {
        defField(cst, null);
    }

    protected void defField(AST cst, ClassNode classNode) throws SyntaxException {
        List<AnnotationNode> annotationList = null;
        String fieldName = "";
        Expression initExpression = null;
        AST child = cst.getFirstChild();
        int modifiers = 0;

        if (child.getType() == ANNOTATION_LIST) {
            annotationList = annotationList(child);
            child = child.getNextSibling();
        }
        if (child.getType() == MODIFIERS) {
            modifiers = modifiers(child);
            child = child.getNextSibling();
        }
        if ((modifiers & Opcodes.ACC_PUBLIC) == 0 &&
                (modifiers & Opcodes.ACC_PRIVATE) == 0 &&
                (modifiers & Opcodes.ACC_PROTECTED) == 0) {
            modifiers = modifiers | Opcodes.ACC_PUBLIC;
        }
        fieldName = child.getText();
        child = child.getNextSibling();
        if (child != null) {
            initExpression = expression(child);
        }
        FieldNode fieldNode = new FieldNode(modifiers, fieldName, initExpression);
        fieldNode.config(cst);
        if (classNode != null) {
            classNode.addField(fieldNode);
        }
        else {
            ast.addField(fieldNode);
        }
    }

    protected void defConstructor(AST cst, ClassNode classNode) throws SyntaxException {
        ConstructorNode constructorNode = null;
        int modifiers = 0;
        List<AnnotationNode> annotationList;
        AST child = cst.getFirstChild();
        if (child.getType() == ANNOTATION_LIST) {
            annotationList = annotationList(child);
            child = child.getNextSibling();
        }

        if (child.getType() == MODIFIERS) {
            modifiers = modifiers(child);
            child = child.getNextSibling();
        }
        if ((modifiers & Opcodes.ACC_PUBLIC) == 0 &&
                (modifiers & Opcodes.ACC_PRIVATE) == 0 &&
                (modifiers & Opcodes.ACC_PROTECTED) == 0) {
            modifiers = modifiers | Opcodes.ACC_PUBLIC;
        }

        ParameterNode[] parameters = ParameterNode.EMPTY_PARAMETER_LIST;
        BlockNode block = null;

        if (child.getType() == PARAM_LIST) {
            parameters = parameters(child);

            child = child.getNextSibling();
            if (child != null && child.getType() == BLOCK) {
                block = blockNode(child);
            }
        }
        else if (child.getType() == BLOCK) {
            block = blockNode(child);
        }

        constructorNode = new ConstructorNode(modifiers, parameters, block);
        constructorNode.config(cst);
        classNode.addConstructor(constructorNode);

    }

    protected void defMethod(AST cst) throws SyntaxException {
        defMethod(cst, null);
    }

    protected void defMethod(AST cst, ClassNode classNode) throws SyntaxException {
        MethodNode methodNode = null;
        ClassNode returnType = null;
        List<AnnotationNode> annotationList = null;
        int modifiers = 0;
        AST child = cst.getFirstChild();

        if (child.getType() == ANNOTATION_LIST) {
            annotationList = annotationList(child);
            child = child.getNextSibling();
        }

        if (child.getType() == MODIFIERS) {
            modifiers = modifiers(child);
            child = child.getNextSibling();
        }
        if ((modifiers & Opcodes.ACC_PUBLIC) == 0 &&
                (modifiers & Opcodes.ACC_PRIVATE) == 0 &&
                (modifiers & Opcodes.ACC_PROTECTED) == 0) {
            modifiers = modifiers | Opcodes.ACC_PUBLIC;
        }

        String methodName = child.getText();
        child = child.getNextSibling();

        ParameterNode[] parameters = ParameterNode.EMPTY_PARAMETER_LIST;
        BlockNode block = null;

        if (child.getType() == PARAM_LIST) {
            parameters = parameters(child);

            child = child.getNextSibling();
            if (child != null && child.getType() == BLOCK) {
                block = blockNode(child);
            }
        }
        else if (child.getType() == BLOCK) {
            block = blockNode(child);
        }

        methodNode = new MethodNode(annotationList, modifiers, methodName, returnType, parameters, block);
        methodNode.config(cst);

        if (classNode != null) {
            classNode.addMethod(methodNode);
        }
        else {
            ast.addMethod(methodNode);
        }
    }

    protected int modifiers(AST cst) {
        assertCSTType(cst, MODIFIERS);
        int result = 0;
        for (AST child = cst.getFirstChild(); child != null; child = child.getNextSibling()) {
            switch (child.getType()) {
                case LITERAL_public:
                    result = modifierBit(cst, result, Opcodes.ACC_PUBLIC);
                    break;
                case LITERAL_private:
                    result = modifierBit(cst, result, Opcodes.ACC_PRIVATE);
                    break;
                case LITERAL_protected:
                    result = modifierBit(cst, result, Opcodes.ACC_PROTECTED);
                    break;
                case LITERAL_abstract:
                    result = modifierBit(cst, result, Opcodes.ACC_ABSTRACT);
                    break;
                case LITERAL_static:
                    result = modifierBit(cst, result, Opcodes.ACC_STATIC);
                    break;
                case LITERAL_final:
                    result = modifierBit(cst, result, Opcodes.ACC_FINAL);
                    break;
                default:
                    unknownAST(cst);
                    break;
            }
        }
        return result;
    }

    protected int modifierBit(AST cst, int oldBit, int bit) {
        if ((oldBit & bit) != 0) {
            throw new ASTRuntimeException(cst, "Cannot repeat modifier: " + cst.getText());
        }
        return oldBit | bit;
    }


    protected ParameterNode[] parameters(AST cst) throws SyntaxException {
        assertCSTType(cst, PARAM_LIST);
        AST child = cst.getFirstChild();
        if (child == null) {
            return ParameterNode.EMPTY_PARAMETER_LIST;
        }
        ParameterNode[] results = new ParameterNode[cst.getNumberOfChildren()];
        int i = 0;
        do {
            ParameterNode param = parameter(child);
            child = child.getNextSibling();
            results[i] = param;
            i++;
        } while (child != null);
        return results;
    }

    protected ParameterNode parameter(AST cst) throws SyntaxException {
        assertCSTType(cst, PARAM);
        ClassNode type = ClassNode.OBJECT;
        AST child = cst.getFirstChild();
        if (child == null) {
            return null;
        }
        String paramName = null;
        boolean bConsntat = false;
        Expression constant = null;

        if (child.getType() == ID) {
            paramName = child.getText();
        }
        else  {
            bConsntat = true;
            constant = expression(child);
            type = constant.getType();
        }
        ParameterNode param = new ParameterNode(type, paramName, bConsntat ? constant : null, bConsntat);
        param.config(cst);
        return param;
    }

    protected BlockNode blockNode(AST cst) throws SyntaxException {
        assertCSTType(cst, BLOCK);
        BlockNode blockNode = new BlockNode();
        blockNode.config(cst);
        AST child = cst.getFirstChild();
        if (child == null) {
        	return blockNode;
        }
        if (child.getType() == STATEMENTS) {
            for (child = child.getFirstChild(); child != null; child = child.getNextSibling()) {
                Statement statement = statement(child);
                blockNode.addStatement(statement);
            }
        }
        else {
            Statement statement = statement(child);
            blockNode.addStatement(statement);
        }
        return blockNode;
    }

    protected Statement statement(AST cst) throws SyntaxException {
        Statement statement = null;
        switch (cst.getType()) {
            case METHOD_DEF:
                throw new SyntaxException(source.getName(), "Not support inner method", cst);
            case CLASS:
                throw new SyntaxException(source.getName(), "Not support inner class", cst);
            case ASSERT:
                statement = assertStatement(cst);
                break;
            case THROW:
                statement = throwStatement(cst);
                break;
            case SUPER_COTR_CALL:
                statement = constructorCallStatement(cst);
                break;
            case IF:
                statement = ifStatement(cst);
                break;
            case FOR:
                statement = forStatement(cst);
                break;
            case TRY:
                statement = tryStatement(cst);
                break;
            case MATCH_BLOCK:
                statement = matchStatment(cst);
                break;
            case K_RETURN:
                statement = returnStatement(cst);
                break;
            case K_BREAK:
                statement = breakStatement(cst);
                break;
            default:
                statement = expressionStatement(cst);
                break;
        }
        return statement;
    }

    protected AssertStatement assertStatement(AST cst) throws SyntaxException {
        assertCSTType(cst, ASSERT);
        AST first = cst.getFirstChild();
        AST second = first.getNextSibling();
        Expression arg1 = expression(first);
        Expression arg2 = null;
        if (second != null) {
            arg2 = expression(second);
        }
        AssertStatement statement = new AssertStatement(arg1, arg2);
        statement.config(cst);
        return statement;
    }

    protected ThrowStatement throwStatement(AST cst) throws SyntaxException {
        assertCSTType(cst, THROW);
        AST first = cst.getFirstChild();
        Expression expression = expression(first);
        ThrowStatement statement = new ThrowStatement(expression);
        statement.config(cst);
        return statement;
    }

    protected ConstructorCallStatement constructorCallStatement(AST cst) throws SyntaxException {
        boolean isSuper = (cst.getType() == SUPER_COTR_CALL);
        AST child = cst.getFirstChild();
        ArgumentListExpression args = argumentListExpression(child);
        ConstructorCallStatement constructorCallStatement = new ConstructorCallStatement(isSuper, args);
        constructorCallStatement.config(cst);
        return constructorCallStatement;
    }

    protected IfStatement ifStatement(AST cst) throws SyntaxException {
        assertCSTType(cst, IF);
        AST child = cst.getFirstChild();

        Expression ifCondition = expression(child);

        child = child.getNextSibling();
        assertCSTType(child, BLOCK);
        BlockNode ifBlock = blockNode(child);

        child = child.getNextSibling();
        BlockNode elseBlock = null;
        if (child != null) {
            if (child.getType() == BLOCK) {
                elseBlock = blockNode(child);
            }
            else if (child.getType() == IF) {
                elseBlock = new BlockNode(ifStatement(child));
            }
        }

        IfStatement ifStatement = new IfStatement(ifCondition, ifBlock, elseBlock);
        ifStatement.config(cst);
        return ifStatement;
    }

    protected ForStatement forStatement(AST cst) throws SyntaxException {
        assertCSTType(cst, FOR);
        int numOfChildren = cst.getNumberOfChildren();
        AST child = cst.getFirstChild();
        Expression expression1 = null, expression2 = null, expression3 = null;
        OperationExpression inExpression = null;
        boolean forEach = false;

        if (numOfChildren > 1) {
            if (numOfChildren == 2) {
                if (child.getType() == IN) {
                    forEach = true;
                    inExpression = operationExpression(child);
                }
                else  {
                    expression1 = null;
                    expression2 = expression(child);
                    expression3 = null;
                }
            }
            else {
                Expression expr1 = expression(child);
                if (expr1.getType() == ClassNode.BOOLEAN) {
                    expression2 = expr1;
                }
                else {
                    expression1 = expr1;
                }

                if (numOfChildren > 2) {
                    child = child.getNextSibling();
                    Expression expr2 = expression(child);
                    if (expression2 == null) {
                        expression2 = expr2;
                    }
                    else {
                        expression3 = expr2;
                    }

                    if (numOfChildren > 3) {
                        child = child.getNextSibling();
                        expression3 = expression(child);
                    }
                }
            }

            child = child.getNextSibling();
            if (child == null) {
                return null;
            }
        }

        assertCSTType(child, BLOCK);
        BlockNode block = blockNode(child);

        ForStatement forStatement = null;
        if (forEach) {
            forStatement = new ForStatement(inExpression, block);
        }
        else {
            forStatement = new ForStatement(expression1, expression2, expression3, block);
        }
        forStatement.config(cst);
        return forStatement;
    }


    protected TryStatement tryStatement(AST cst) throws SyntaxException {
        assertCSTType(cst, TRY);
        AST child = cst.getFirstChild();
        BlockNode block = blockNode(child);
        TryStatement tryStatement = new TryStatement(block);
        tryStatement.config(cst);
        child = child.getNextSibling();
        assertCSTType(child, CATCH_LIST);
        for (child = child.getFirstChild() ; child != null; child = child.getNextSibling()) {
            CatchStatement catchStatement = catchStatement(child);
            tryStatement.addCatachStatement(catchStatement);
        }
        return tryStatement;
    }

    protected ExpressionStatement matchStatment(AST cst) throws SyntaxException {
//        AST child = cst.getFirstChild();
        MatchBlock block = matchBlock(cst);

        MatchExpression matchExpression = new MatchExpression(null, block);
        return new ExpressionStatement(matchExpression);
    }

    protected CatchStatement catchStatement(AST cst) throws SyntaxException {
        assertCSTType(cst, CATCH);
        AST child = cst.getFirstChild();
        String parameter = child.getText();
        child = child.getNextSibling();
        BlockNode block = blockNode(child);
        CatchStatement catchStatement = new CatchStatement(parameter, block);
        catchStatement.config(cst);
        return catchStatement;
    }

    protected ReturnStatement returnStatement(AST cst) throws SyntaxException {
        assertCSTType(cst, K_RETURN);
        AST child = cst.getFirstChild();
        Expression expression = null;
        if (child != null) {
            expression = expression(child);
        }
        ReturnStatement returnStatement = new ReturnStatement(expression);
        returnStatement.config(cst);
        return returnStatement;
    }

    protected BreakStatement breakStatement(AST cst) {
        assertCSTType(cst, K_BREAK);
        BreakStatement breakStatement = new BreakStatement();
        return breakStatement;
    }


    protected ExpressionStatement expressionStatement(AST cst) throws SyntaxException {
        Expression expression = expression(cst);
        ExpressionStatement expressionStatement = new ExpressionStatement(expression);
        expressionStatement.config(cst);
        return expressionStatement;
    }

    protected Expression expression(AST cst) throws SyntaxException {
        Expression expression = null;
        switch (cst.getType()) {
            case K_NULL:
                expression = constantExpression(cst, Null.NULL);
                break;
            case U_PLUS:
            case U_MINUS:
            case NOT:
            case LNOT:
                expression = unaryExpression(cst);
                break;
            case MARKUP:
                expression =  markupExpression(cst);
                break;
            case STAR_LIST:
                expression = starListExpression(cst);
                break;
            case LIST:
                expression = listExpression(cst);
                break;
            case HASH:
                expression = mapExpression(cst);
                break;
            case INTEGER:
                expression = integerExpression(cst);
                break;
            case FLOAT:
                expression = decimalExpression(cst);
                break;
            case K_TRUE:
                expression = constantExpression(cst, Boolean.TRUE);
                expression.setType(ClassNode.BOOLEAN);
                break;
            case K_FALSE:
                expression = constantExpression(cst, Boolean.FALSE);
                expression.setType(ClassNode.BOOLEAN);
                break;
            case STRING:
                expression = constantExpression(cst, cst.getText());
                expression.setType(ClassNode.PSTRING);
                break;
            case REGEX:
                expression = regexPatternConstantExpression(cst, cst.getText());
                break;
            case DATE:
                expression = datetimeExpression(cst);
                break;
            case TIME:
                expression = timeExpression(cst);
                break;
            case PAIR:
            	expression = TupleExpression(cst, true);
            	break;
            case STRING_CONSTRUCTOR:
                expression = stringExpression(cst);
                break;
            case REGEX_CONSTRUCTIOR:
                expression = regexPatternExpression(cst);
                break;
            case FILE_CONSTRUCTOR:
                expression = fileExpression(cst);
                break;
            case URL:
                expression = urlExpression(cst);
                break;
            case FILE_PATH:
            	expression = fileConstantExpression(cst);
            	break;
            case INDEX_OP:
                expression = indexExpression(cst);
                break;
            case OPTIONAL_DOT:
            case STAR_DOT:
            case DOT:
                expression = dotExpression(cst);
                break;
            case CLOSURE:
            	expression = closureExpression(cst);
            	break;
            case METHOD_CALL:
                expression = methodCallExpression(cst);
                break;
            case NEW:
                expression = newExpression(cst);
                break;
            case POST_INC:
            case POST_DEC:
                expression = postfixExpression(cst);
                break;
            case DB_DOT:
            case DB_DOT_LT:
            case GT_DB_DOT:
            case GT_DB_DOT_LT:
                expression = rangeExpression(cst);
                break;
            case U_DB_DOT:
            case U_DB_DOT_LT:
            case U_GT_DB_DOT:
            case U_GT_DB_DOT_LT:
                expression = postfixRangeExpression(cst);
                break;
            case K_AS:
                expression = matchVarDef(cst);
                break;
            case K_STEP:
                expression = stepExpression(cst);
                break;
            case K_MATCH:
                expression = matchExpression(cst);
                break;
            case QUESTION:
            case QUESTION_COLON:
                expression = conditionExpression(cst);
                break;
            case IF:
                expression = ifExpression(cst);
                break;
            case THIS:
            case SUPER:
            case ID:
                expression = identifier(cst);
                break;
            case MATCH_VAR_DEF:
                expression = matchVarDef(cst);
                break;
            case REFRENCE_NAME:
                expression = referenceExpression(cst);
                break;
            case ASSIGN:
            case PLUS_ASSIGN:
            case MINUS_ASSIGN:
            case STAR_ASSIGN:
            case DIV_ASSIGN:
            case MOD_ASSIGN:
            case INIT_ASSIGN:
            case PLUS:
            case MINUS:
            case STAR:
            case DIV:
            case MOD:
            case POW:
            case DB_STAR:
            case LT:
            case GT:
            case LE:
            case GE:
            case EQUAL:
            case NOT_EQUAL:
            case IS:
            case REGEX_MATCH:
            case REGEX_NOT_MATCH:
            case BAND:
            case BOR:
            case LAND:
            case LOR:
            case XOR:
            case MAND:
            case MOR:
            case IN:
            case INSTANCEOF:
            case BSR:
            case BSL:
            case SR:
            case SL:
                expression = operationExpression(cst);
                break;
            default:
                break;
        }
        expression.config(cst);
        return expression;
    }



    protected MatchExpression matchExpression(AST cst) throws SyntaxException {
        assertCSTType(cst, K_MATCH);
        AST child = cst.getFirstChild();
        Expression expression = expression(child);

        child = child.getNextSibling();
        MatchBlock block = matchBlock(child);

        MatchExpression matchExpression = new MatchExpression(expression, block);
        matchExpression.config(cst);
        return matchExpression;
    }

    protected MatchBlock matchBlock(AST cst) throws SyntaxException {
        if (cst == null) {
            return null;
        }
        assertCSTType(cst, MATCH_BLOCK);
        MatchBlock block = new MatchBlock();
        for (AST child = cst.getFirstChild(); child != null; child = child.getNextSibling()) {
            MatchItem item = matchItem(child);
            block.addMatchItem(item);
        }
        block.config(cst);
        return block;
    }

    protected MatchItem matchItem(AST cst) throws SyntaxException {
        assertCSTType(cst, MATCH_ITEM);

        AST child = cst.getFirstChild();
        Expression pattern = expression(child);

        child = child.getNextSibling();
        BlockNode block = null;
        if (child != null) {
            block = blockNode(child);
        }

        MatchItem item = new MatchItem(pattern, block);
        item.config(cst);
        return item;
    }

    protected ConditionExpression conditionExpression(AST cst) throws SyntaxException {
        AST child = cst.getFirstChild();
        Expression condition = expression(child);
        Expression trueExpression = null;
        Expression falseExpression = null;

        if (cst.getType() == QUESTION_COLON) {
            child = child.getNextSibling();
            falseExpression = expression(child);
        }
        else {
            child = child.getNextSibling();
            if (child != null) {
                trueExpression = expression(child);

                child = child.getNextSibling();

                if (child != null) {
                    falseExpression = expression(child);
                }
            }
        }

        ConditionExpression conditionExpression = new ConditionExpression(condition, trueExpression, falseExpression);
        conditionExpression.config(cst);
        return conditionExpression;
    }

    protected ConditionExpression ifExpression(AST cst) throws SyntaxException {
        AST child = cst.getFirstChild();
        Expression ifCondition = expression(child);

        child = child.getNextSibling();
        assertCSTType(child, BLOCK);
        BlockNode ifBlock = blockNode(child);

        child = child.getNextSibling();
        BlockNode elseBlock = null;
        if (child != null) {
            if (child.getType() == BLOCK) {
                elseBlock = blockNode(child);
            }
            else if (child.getType() == IF) {
                elseBlock = new BlockNode(ifStatement(child));
            }
        }

        ConditionExpression conditionExpression = new ConditionExpression(ifCondition, ifBlock, elseBlock);
        conditionExpression.config(cst);
        return conditionExpression;

    }

    protected DateTimeExpression datetimeExpression(AST cst) throws SyntaxException {
        String text = cst.getText();
        Expression zone = null;
        DateTimeExpression timeExpression = null;

        DateTimeExpression dateTimeExpression = new DateTimeExpression();

        AST child = cst.getFirstChild();
        if (child != null) {
            if (child.getType() == TIME) {
                timeExpression = timeExpression(child);
                child = child.getNextSibling();
            }

            if (child != null) {
                if (child.getType() == STRING_CONSTRUCTOR) {
                    zone = stringExpression(child);
                }
                else {
                    zone = constantExpression(child, child.getText());
                }
            }
        }

        DateTimeUtil.parseDateParts(text, dateTimeExpression, zone);
        if (timeExpression != null) {
            dateTimeExpression.setHour(timeExpression.getHour());
            dateTimeExpression.setMinute(timeExpression.getMinute());
            dateTimeExpression.setSecond(timeExpression.getSecond());
        }


        if (dateTimeExpression.isInvalid()) {
            throw new SyntaxException(source.getName(), "Invalid date: " + text, cst);
        }
        dateTimeExpression.setType(ClassNode.DATE);
        dateTimeExpression.config(cst);
        return dateTimeExpression;
    }
    
    protected TupleExpression TupleExpression(AST cst, boolean isPair) {
    	if (isPair) {
    		String text = cst.getText();
    		return TupleUtil.parsePair(text);
    	}
    	return null;
    }

    protected DateTimeExpression timeExpression(AST cst) {
        DateTimeExpression dateTimeExpression = new DateTimeExpression();
        String text = cst.getText();
        DateTimeUtil.parseTimeParts(text, dateTimeExpression);
        dateTimeExpression.setType(ClassNode.PDURATION);
        dateTimeExpression.config(cst);
        return dateTimeExpression;
    }

    protected IndexExpression indexExpression(AST cst) throws SyntaxException {
        assertCSTType(cst, INDEX_OP);

        AST child = cst.getFirstChild();
        Expression indexee = expression(child);

        child = child.getNextSibling();
        ArgumentListExpression args = argumentListExpression(child);

        IndexExpression indexExpression = new IndexExpression(indexee, args);
        indexExpression.config(cst);
        return indexExpression;
    }

    protected Expression dotExpression(AST cst) throws SyntaxException {
        String dotName = qualifiedName(cst);
        Class dotClass = null;
        PatternExpression patternExpression = null;
        if (dotName.equals(ast.getCurrentClassNode().getName())) {
            patternExpression = new PatternExpression(ast.getCurrentClassNode());
        }
        else {
            try {
                dotClass = classLoader.loadClass(dotName);
            } catch (ClassNotFoundException e) {
            }
            if (dotClass != null) {
                patternExpression = new PatternExpression(ClassNode.make(dotClass));
            }
        }
        if (patternExpression != null) {
            patternExpression.config(cst);
            return patternExpression;
        }

        AST leftChild = cst.getFirstChild();
        ClassNode type = findTypeFromImported(qualifiedName(leftChild));
        AST propertyChild = leftChild.getNextSibling();

        Expression property = null;
        boolean isReference = false;
        PropertyExpression propertyExpression = null;

        if (propertyChild.getType() == REFRENCE_NAME) {
            isReference = true;
            property = variableExpression(propertyChild);
        }
        else if (propertyChild.getType() == K_CLASS) {
            property = variableExpression(propertyChild);
        }
        else {
            property = expression(propertyChild);
        }

        if (type == null) {
	        Expression left = expression(leftChild);
	        propertyExpression = new PropertyExpression(left, property);
        }
        else {
	        propertyExpression = new PropertyExpression(type, property);
        }

        if (cst.getType() == OPTIONAL_DOT) {
            propertyExpression.setCheckNull(true);
        }
        else if (cst.getType() == STAR_DOT) {
            propertyExpression.setVisitEach(true);
        }

        propertyExpression.setReference(isReference);
        propertyExpression.config(cst);
        return propertyExpression;
    }

    protected PostfixExpression postfixExpression(AST cst) throws SyntaxException {
        int type = cst.getType();
        AST child = cst.getFirstChild();
        Expression expression = expression(child);
        SoyaToken token = makeToken(type, cst);
        PostfixExpression postfixExpression = new PostfixExpression(token, expression);
        postfixExpression.config(cst);
        return postfixExpression;
    }


    protected ClosureExpression closureExpression(AST cst) throws SyntaxException {
    	assertCSTType(cst, CLOSURE);
    	AST child = cst.getFirstChild();
    	List<ParameterNode> parameterList = null;

        if (child.getType() == PARAM_LIST) {
            ParameterNode[] params = parameters(child);
            parameterList = Arrays.asList(params);
            child = child.getNextSibling();
        }
        else {
            parameterList = new ArrayList<ParameterNode>();
        }
    	
    	BlockNode body = null;
    	if (child.getType() == BLOCK) {
    		body = blockNode(child);
    	}
    	ClosureExpression expression = new ClosureExpression(parameterList, body);
        expression.config(cst);
    	return expression;
    }

    protected MethodCallExpression methodCallExpression(AST cst) throws SyntaxException {
        AST child = cst.getFirstChild();
        int type = child.getType();
        if (type == ID) {
            String methodName = child.getText();
            ConstantExpression method = new ConstantExpression(methodName);
            VariableExpression obj = null;
            child = child.getNextSibling();
            ArgumentListExpression args = argumentListExpression(child);
            MethodCallExpression callExpression = new MethodCallExpression(obj, method, args);
            callExpression.config(cst);
            return callExpression;
        }
        else if (type == DOT || type == OPTIONAL_DOT || type == STAR_DOT) {
            AST leftChild = child.getFirstChild();
            AST rightChild = leftChild.getNextSibling();
            ClassNode classNode = findTypeFromImported(qualifiedName(leftChild));
            MethodCallExpression callExpression;
            ConstantExpression right = new ConstantExpression(rightChild.getText());
            right.config(rightChild);

            child = child.getNextSibling();
            ArgumentListExpression args = argumentListExpression(child);

            if (classNode != null) {
                callExpression = new MethodCallExpression(classNode, right, args);
            }
            else {
                Expression left = expression(leftChild);
                callExpression = new MethodCallExpression(left, right, args);
                if (type == OPTIONAL_DOT) {
                    callExpression.setCheckNull(true);
                }
                if (type == STAR_DOT) {
                    callExpression.setVisitEach(true);
                }
            }
            callExpression.config(cst);
            return callExpression;
        }
        return null;
    }

    protected ArgumentListExpression argumentListExpression(AST cst) throws SyntaxException {
        if (cst == null) {
            return new ArgumentListExpression();
        }
        ArgumentListExpression args = new ArgumentListExpression();
        if (cst.getType() == ARG_LIST) {
        	AST child = null;
            MapExpression mapExpression = null;
	        for (child = cst.getFirstChild(); child != null; child = child.getNextSibling()) {
                if (child.getType() == NAMED_ARG) {
                    if (mapExpression == null) {
                        mapExpression = new MapExpression();
                    }
                    MapEntryExpression entry = mapEntryExpression(child.getFirstChild());
                    mapExpression.addEntry(entry);
                    continue;
                }
                else {
                    if (mapExpression != null) {
                        args.addArgument(mapExpression);
                        mapExpression = null;
                    }
                    Expression expression = expression(child);
                    if (expression != null) {
                        args.addArgument(expression);
                    }
                }
	        }
            if (mapExpression != null) {
                args.addArgument(mapExpression);
            }

	        child = cst.getNextSibling();
	        if (child != null && child.getType() == CLOSURE) {
	        	Expression expression = closureExpression(child);
	        	args.addArgument(expression);
	        }
        }
        else if (cst.getType() == CLOSURE) {
        	Expression expression = closureExpression(cst);
        	args.addArgument(expression);
        }
        args.config(cst);
        return args;
    }

    protected NewExpression newExpression(AST cst) throws SyntaxException {
        assertCSTType(cst, NEW);
        ClassNode type = makeType(cst);
        AST child = cst.getFirstChild();

        AST argsExpr = child.getNextSibling();
        ArgumentListExpression arguments = argumentListExpression(argsExpr);
        NewExpression newExpression = new NewExpression(type, arguments);
        newExpression.config(cst);
        return newExpression;
    }
    
    protected ClassNode makeType(AST cst) {
        ClassNode result = ClassNode.POBJECT;
        AST child = cst.getFirstChild();
        if (child != null) {
        	String className = qualifiedName(child);
        	result = findTypeFromImported(className);
        	if (result != null) {
        		return result;
        	}
            result = ClassNode.make(className);
        }
        return result;
    }
    
    protected ClassNode findTypeFromImported(String className) {
        if (ast.getCurrentClassName() != null && ast.getCurrentClassName().equals(className)) {
            return ast.getCurrentClassNode();
        }
        Map<String, ImportNode> imports = ast.getImports();
    	if (imports.containsKey(className)) {
    		ImportNode node = imports.get(className);
    		return node.getType();
    	}
        List<ImportNode> startImports = ast.getStarImports();
        for (ImportNode startImport : startImports) {
            String pkg = startImport.getPackageName();
            String name = pkg + "." + className;
            boolean exists = ast.getClassLoader().classExists(name);
            if (exists) {
                return ClassNode.make(name);
            }
        }
    	return null;
    }

    protected UnaryExpression unaryExpression(AST cst) throws SyntaxException {
        int type = cst.getType();
        SoyaToken token = makeToken(type, cst);
        AST child = cst.getFirstChild();
        if (child == null) {
            return null;
        }
        Expression expression = expression(child);
        UnaryExpression unaryExpression = new UnaryExpression(token, expression);
        unaryExpression.config(cst);
        return unaryExpression;
    }

    protected StringExpression stringExpression(AST cst) throws SyntaxException {
        List<Expression> values = new ArrayList<Expression>();
        StringExpression stringExpression = null;

        for (AST child = cst.getFirstChild(); child != null; child = child.getNextSibling()) {
            int type = child.getType();
            String text = null;
            switch (type) {
                case STR_SQ_START:
                case STR_TQ_START:
                case STR_PART_MIDDLE:
                case STR_PART_END:
                    text = child.getText();
                    ConstantExpression constantExpression = constantExpression(child, text);
                    values.add(constantExpression);
                    break;
                default:
                    Expression expression = expression(child);
                    values.add(expression);
                    break;
            }
        }
        stringExpression = new StringExpression(values);
        stringExpression.config(cst);
        return stringExpression;
    }


    protected RegexPatternExpression regexPatternExpression(AST cst) throws SyntaxException {
        List<Expression> values = new ArrayList<Expression>();
        RegexPatternExpression regexpExpression = null;
        int flag = 0;
//        Object[] results = regexPatternAttributes(str);
        for (AST child = cst.getFirstChild(); child != null; child = child.getNextSibling()) {
            int type = child.getType();
            String text = null;
            switch (type) {
                case REGEX_START:
                case REGEX_MIDDLE:
                case REGEX_END:
                    text = child.getText();
                    ConstantExpression constantExpression = constantExpression(child, text);
                    values.add(constantExpression);
                    break;
                default:
                    Expression expression = expression(child);
                    values.add(expression);
                    break;
            }
        }
        /**
         * parse regex pattern flags
         * include (i, g, m, U)
         */
        if (values.size() > 0 && values.get(values.size() - 1) instanceof ConstantExpression) {
            ConstantExpression constantExpression = (ConstantExpression) values.get(values.size() - 1);
            Object[] results = regexPatternAttributes(constantExpression.getValue().toString());
            constantExpression.setValue(results[0]);
            flag = ((Integer) results[1]).intValue();
        }
        regexpExpression = new RegexPatternExpression(values);
        regexpExpression.setFlag(flag);
        regexpExpression.config(cst);
        return regexpExpression;
    }

    protected FileExpression fileExpression(AST cst) throws SyntaxException {
        List<Expression> values = new ArrayList<Expression>();
        FileExpression fileExpression = null;
        for (AST child = cst.getFirstChild(); child != null; child = child.getNextSibling()) {
            int type = child.getType();
            String text = null;
            switch (type) {
                case FILE_PATH_START:
                case FILE_PATH_MIDDLE:
                case FILE_PATH_END:
                    text = child.getText();
                    ConstantExpression constantExpression = constantExpression(child, text);
                    values.add(constantExpression);
                    break;
                default:
                    Expression expression = expression(child);
                    values.add(expression);
                    break;
            }
        }
        fileExpression = new FileExpression(values);
        fileExpression.config(cst);
        return fileExpression;
    }

    protected ConstantExpression urlExpression(AST cst) {
        String text = cst.getText();
        java.net.URL url = null;
        try {
            url = new java.net.URL(text);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ConstantExpression constantExpression = new ConstantExpression(url);
        constantExpression.config(cst);
        return constantExpression;
    }
    
    protected ConstantExpression fileConstantExpression(AST cst) {
    	String text = cst.getText();
    	File file = new File(text);
    	ConstantExpression constantExpression = new ConstantExpression(file);
        constantExpression.config(cst);
    	return constantExpression;
    }

    protected RangeExpression rangeExpression(AST cst) throws SyntaxException {
        AST child = cst.getFirstChild();
        Expression from = expression(child);
        Expression to = null;
        child = child.getNextSibling();
        if (child != null) {
            to = expression(child);
        }
        else {
            to = new VariableExpression("null");
        }

        boolean includeFrom = true, includeTo = true;

        if (to != null) {
            switch (cst.getType()) {
                case DB_DOT_LT:
                    includeFrom = true;
                    includeTo = false;
                    break;
                case GT_DB_DOT:
                    includeFrom = false;
                    includeTo = true;
                    break;
                case GT_DB_DOT_LT:
                    includeFrom = false;
                    includeTo = false;
                    break;
            }
        }

        RangeExpression rangeExpression = new RangeExpression(from, to, includeFrom, includeTo);
        rangeExpression.config(cst);
        return rangeExpression;
    }

    protected RangeExpression postfixRangeExpression(AST cst) throws SyntaxException {
        Expression from = new VariableExpression("null");
        AST child = cst.getFirstChild();
        Expression to = expression(child);

        boolean includeFrom = true, includeTo = true;

        if (to != null) {
            switch (cst.getType()) {
                case U_DB_DOT_LT:
                    includeFrom = true;
                    includeTo = false;
                    break;
                case U_GT_DB_DOT:
                    includeFrom = false;
                    includeTo = true;
                    break;
                case U_GT_DB_DOT_LT:
                    includeFrom = false;
                    includeTo = false;
                    break;
            }
        }

        RangeExpression rangeExpression = new RangeExpression(from, to, includeFrom, includeTo);
        rangeExpression.config(cst);
        return rangeExpression;
    }


    protected StepExpression stepExpression(AST cst) throws SyntaxException {
        assertCSTType(cst, K_STEP);
        AST child = cst.getFirstChild();
        Expression rang = expression(child);
        child = child.getNextSibling();
        if (child == null) {
            return null;
        }
        Expression step = expression(child);
        StepExpression stepExpression = new StepExpression(rang, step);
        stepExpression.config(cst);
        return stepExpression;
    }

    protected ConstantExpression decimalExpression(AST cst) {
        String text =  cst.getText();
        Double decimal = Double.parseDouble(text);
        ConstantExpression constantExpression = new ConstantExpression(decimal);
        constantExpression.setType(ClassNode.PFLOAT);
        constantExpression.config(cst);
        return constantExpression;
    }

    protected ConstantExpression integerExpression(AST cst) {
        String text =  cst.getText();
        Object integer = Integer.parseInt(text);
        ConstantExpression constantExpression = new ConstantExpression(integer);
        constantExpression.setType(ClassNode.PINT);
        constantExpression.config(cst);
        return constantExpression;
    }

    protected Object[] regexPatternAttributes(String str) {
        Object[] results = new Object[2];
        int pos = str.indexOf('/');
        int flag = 0;
        if (pos > -1) {
            String oldStr = new String(str);
            pos = str.lastIndexOf('/');
            str = str.substring(0, pos);
            pos += 1;
            for ( ; pos < oldStr.length(); pos++) {
                char c = oldStr.charAt(pos);
                switch (c) {
                    case 'i':
                        flag |= RegexPatternExpression.REGEX_FLAG_I;
                        break;
                    case 'g':
                        flag |= RegexPatternExpression.REGEX_FLAG_G;
                        break;
                    case 'm':
                        flag |= RegexPatternExpression.REGEX_FLAG_M;
                        break;
                }
            }
        }
        results[0] = str;
        results[1] = flag;
        return results;
    }

    protected RegexPatternExpression regexPatternConstantExpression(AST cst, String str) {
        Object[] results = regexPatternAttributes(str);
        str = (String) results[0];
        int flag = ((Integer) results[1]).intValue();
        RegexPatternExpression regexPatternExpression = new RegexPatternExpression(Arrays.<Expression>asList(new ConstantExpression(str)));
        regexPatternExpression.setFlag(flag);
        regexPatternExpression.config(cst);
        return regexPatternExpression;
    }

    protected ConstantExpression constantExpression(AST cst, Object value) {
        ConstantExpression constantExpression = new ConstantExpression(value);
        constantExpression.config(cst);
        return constantExpression;
    }

    protected Expression starListExpression(AST cst) throws SyntaxException {
        assertCSTType(cst, STAR_LIST);
        ListExpression ret = listExpression(cst);
        List<Expression> items = ret.getItems();
        if (items.size() == 1) {
            Expression item = items.get(0);
            if (item instanceof MapExpression) {
                return item;
            }
        }
        return ret;
    }

    protected ListExpression listExpression(AST cst) throws SyntaxException {
        List<Expression> expressions = new ArrayList<Expression>();
        AST child = cst.getFirstChild();
        while (child != null) {
            Expression expression = expression(child);
            child = child.getNextSibling();
            expressions.add(expression);
        }
        ListExpression listExpression = new ListExpression(expressions);
        return listExpression;
    }

    protected MapExpression mapExpression(AST cst) throws SyntaxException {
        assertCSTType(cst, HASH);
        List<MapEntryExpression> entris = new ArrayList<MapEntryExpression>();
        for (AST child = cst.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child.getType() == HASH_ENTRY) {
                MapEntryExpression entry = mapEntryExpression(child);
                entris.add(entry);
            }
        }
        MapExpression mapExpression = new MapExpression(entris);
        mapExpression.config(cst);
        return mapExpression;
    }

    protected MapEntryExpression mapEntryExpression(AST cst) throws SyntaxException {
        assertCSTType(cst, HASH_ENTRY);
        AST child = cst.getFirstChild();
        int sep = child.getType();

        child = child.getNextSibling();
        Expression key = null;

        if (sep == COLON && child.getType() == ID) {
            key = new ConstantExpression(child.getText());
        }
        else {
            key = expression(child);
        }

        child = child.getNextSibling();
        if (child.getType() == NAMED_ARG) {
            child.setType(HASH);
        }
        Expression value = expression(child);
        MapEntryExpression mapEntryExpression = new MapEntryExpression(key, value);
        return mapEntryExpression;
    }

    protected Expression identifier(AST cst) {
        String text = cst.getText();
        ClassNode type = findTypeFromImported(text);
        if (type != null) {
            PatternExpression patternExpression = new PatternExpression(type);
            patternExpression.config(cst);
            return patternExpression;
        }
        return variableExpression(cst);
    }

    protected MatchVarDefExpression matchVarDef(AST cst) throws SyntaxException {
//        assertCSTType(cst, MATCH_VAR_DEF);
        AST child = cst.getFirstChild();
        Expression expr = expression(child);
        child = child.getNextSibling();
        MatchVarDefExpression matchVarDefExpression = new MatchVarDefExpression(child.getText(), expr);
        return matchVarDefExpression;
    }

    protected ReferenceExpression referenceExpression(AST cst) {
        assertCSTType(cst, REFRENCE_NAME);
        ReferenceExpression referenceExpression = new ReferenceExpression(null, new VariableExpression(cst.getText()));
        referenceExpression.config(cst);
        return referenceExpression;
    }

    protected VariableExpression variableExpression(AST cst) {
        VariableExpression variableExresspion = new VariableExpression(cst.getText());
        return variableExresspion;
    }

    protected OperationExpression operationExpression(AST cst) throws SyntaxException {
        AST first = cst.getFirstChild();
        Expression left = expression(first);

        AST second = first.getNextSibling();

        int type = cst.getType();
        SoyaToken token = makeToken(type, cst);

        if (second == null) {
            return null;
        }

        Expression right = expression(second);

        OperationExpression operationExpression = new OperationExpression(token, left, right);
        operationExpression.config(cst);
        return operationExpression;
    }

    protected Expression markupExpression(AST cst) {
        assertCSTType(cst, MARKUP);
        MarkupExpression markupExpression = new MarkupExpression(cst.getText());
        return markupExpression;
    }

    protected String getCSTTypeName(int type) {
        return SoyaParser._tokenNames[type];
    }

    protected String getCSTTypeName(AST cst) {
        if (cst == null)
            return "null";
        return getCSTTypeName(cst.getType());
    }

    protected void assertCSTType(AST cst, int type) {
        if (cst == null) {
            throw new ASTRuntimeException(cst, "No child node available in AST when expecting type: " + getCSTTypeName(type));
        }
        else if (cst.getType() != type) {
            throw new ASTRuntimeException(cst, "Unexpected node type: " + getCSTTypeName(cst) + " found when expecting type: " + getCSTTypeName(type));
        }
    }

    protected void unknownAST(AST cst) {
        if (cst.getType() == CLASS) {
            throw new ASTRuntimeException(cst,
                    "Class definition not expected here. Please define the class at an appropriate place or perhaps try using a block/Closure instead.");
        }
        if (cst.getType() == METHOD_DEF) {
            throw new ASTRuntimeException(cst,
                    "Method definition not expected here. Please define the method at an appropriate place or perhaps try using a block/Closure instead.");
        }
        throw new ASTRuntimeException(cst, "Unknown type: " + getCSTTypeName(cst));
    }

    protected SoyaToken makeToken(int type, AST cst) {
        SoyaToken token = new SoyaToken(type);
        token.setText(cst.getText());
        token.setLine(cst.getLine());
        token.setColumn(cst.getColumn());
        token.setEndLine(cst.getLine());
        token.setEndColumn(cst.getColumn());
        return token;
    }

}

