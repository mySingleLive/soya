package org.soya.codegen;

import antlr.Token;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;
import org.soya.antlr.SoyaToken;
import org.soya.antlr.SyntaxException;
import org.soya.antlr.parser.SoyaParserTokenTypes;
import org.soya.antlr.util.DateTimeUtil;
import org.soya.ast.*;
import org.soya.ast.expr.*;
import org.soya.ast.stmt.*;
import org.soya.codegen.frame.*;
import org.soya.util.NameUtil;
import soya.lang.Closure;
import soya.lang.Null;
import soya.lang.Script;
import soya.util.SoyaAssertionOperator;

import java.io.File;
import java.net.URL;
import java.util.*;

import static org.soya.antlr.parser.SoyaParserTokenTypes.*;

/**
 * @author: Jun Gong
 */
public class WriterVisitor {

    protected final ClassGenerator classGenerator;
	protected final CompilationUnit unit;
	protected final GeneratorAdapter ga;
    protected final MethodWriter methodWriter;
    protected StackFrame stackFrame;
    protected final ClassNode classNode;
    protected MethodNode methodNode;
    protected SymbolTable rootSymbolTable;
    protected List<Runnable> exceptions = new ArrayList<Runnable>();

    public WriterVisitor(ClassGenerator classGenerator, CompilationUnit unit, GeneratorAdapter ga, MethodWriter methodWriter, SymbolTable symbolTable, ClassNode classNode, MethodNode methodNode) {
        this.classGenerator = classGenerator;
    	this.unit = unit;
		this.ga = ga;
        this.methodWriter = methodWriter;
        this.stackFrame = new StackFrame(this);
        this.classNode = classNode;
        this.methodNode = methodNode;
        this.rootSymbolTable = symbolTable;
    }

    public void addError(Exception e) {
        if (e instanceof ClassGeneratorException) {
            e = new SyntaxException(unit.getSourceCode().getName(), (ClassGeneratorException) e);
        }
        unit.getSourceCode().addError(e);
    }


    public void pushVarScope(SymbolTable st) {
        stackFrame.pushVariableScope(st);
    }

    public void popVarScope() {
        stackFrame.popVariableScope();
    }

    public SymbolTable getRootSymbolTable() {
        return rootSymbolTable;
    }

    public ClassGenerator getClassGenerator() {
        return classGenerator;
    }

    public ClassNode getClassNode() {
        return classNode;
    }

    public MethodNode getMethodNode() {
        return methodNode;
    }

    public void loadThis() {
        if (classGenerator.isClosureClass()) {
            ga.loadArg(0);
        }
        else {
            ga.loadThis();
        }
    }

    public void pushShell() {
        ga.invokeStatic(TypeUtil.P_SHELL_TYPE, MethodUtil.getMethod("org.soya.runtime.SoyaShell getSharedShell ()"));
    }

    public void pushNull() {
        ga.getStatic(TypeUtil.P_NULL_TYPE, "NULL", TypeUtil.P_NULL_TYPE);
    }

    public void visitLineNumber(TreeNode node) {
        if (node == null) {
            return;
        }
        int line = node.getLine();
        if (line < 0) {
            return;
        }
        Label label = new Label();
        ga.visitLabel(label);
        ga.visitLineNumber(line, label);
    }

    public StackFrame getStackFrame() {
        return stackFrame;
    }

    public void setField(FieldNode field, Expression valueExpression) {
        if (field.isStatic()) {
            setStaticField(field.getName(), valueExpression, TypeUtil.getType(classNode), TypeUtil.OBJECT_TYPE);
        }
        else {
            setFieldValueFromThis(field.getName(), valueExpression, TypeUtil.getType(classNode), TypeUtil.OBJECT_TYPE);
        }
    }

    public void setFieldValueFromThis(String fieldName, Expression valueExpression, Type owner, Type type) {
        loadThis();
        visitExpression(valueExpression);
        ga.putField(owner, fieldName, type);
    }

    public void setStaticField(String fieldName, Expression valueExpression, Type owner, Type type) {
        visitExpression(valueExpression);
        ga.putStatic(owner, fieldName, type);
    }
    
    public void loadFieldValueFromThis(String fieldName, Type owner, Type type) {
        loadThis();
        ga.getField(owner, fieldName, type);
    }

    public void loadStaticField(String fieldName, Type owner, Type type) {
        ga.getStatic(owner, fieldName, type);
    }

    public void loadField(FieldNode fieldNode) {
        if (fieldNode.isStatic()) {
            loadStaticField(fieldNode.getName(), TypeUtil.getType(classNode), TypeUtil.OBJECT_TYPE);
        }
        else {
            loadFieldValueFromThis(fieldNode.getName(), TypeUtil.getType(classNode), TypeUtil.OBJECT_TYPE);
        }
    }

    public List<Runnable> getExceptions() {
        return exceptions;
    }

    public void visitBlockNode(BlockNode blockNode) {
        List<Statement> statements = blockNode.getStatements();
        int len = statements.size();
        for (int i = 0; i < len; i++) {
        	Statement statement = statements.get(i);
            visitStatement(statement);
            if (i < len - 1) {
            	ga.pop();
            }
        }
    }

    public void visitStatement(Statement statement) {

        if (stackFrame.getSymbolTable().isHasReturned()
                || stackFrame.getSymbolTable().isHasReturnedInSubBlock()
                || stackFrame.getSymbolTable().isHasBreaked()) {
            addError(new ClassGeneratorException("Unreachable statement", statement));
            return;
        }

        visitLineNumber(statement);

        if (statement instanceof ConstructorCallStatement) {
            addError(new ClassGeneratorException(
                    "Call to 'super()' must be first statement in constructor body",
                    statement));
        }
        else if (statement instanceof AssertStatement) {
            visitAssertStatement((AssertStatement) statement);
        }
        else if (statement instanceof ThrowStatement) {
            visitThrowStatement((ThrowStatement) statement);
        }
        else if (statement instanceof IfStatement) {
            visitIfStatement((IfStatement) statement);
        }
        else if (statement instanceof ForStatement) {
        	visitForStatement((ForStatement) statement);
        }
        else if (statement instanceof TryStatement) {
            visitTryStatement((TryStatement) statement);
        }
        else if (statement instanceof ReturnStatement) {
            visitReturnStatement((ReturnStatement) statement);
        }
        else if (statement instanceof BreakStatement) {
            visitBreakStatement((BreakStatement) statement);
        }
        if (statement instanceof ExpressionStatement) {
            visitExpressionStatement((ExpressionStatement) statement);
        }
    }

    public void visitExpressionStatement(ExpressionStatement statement) {
        Expression expression = statement.getExpression();
        visitExpression(expression);
    }

    private void jumpIfFalse(Expression expression, Label label) {
        ClassNode type = expression.getType();
        if (type != ClassNode.BOOLEAN) {
            pushShell();
        }

        visitExpression(expression);

        if (type != ClassNode.BOOLEAN) {
            ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.TEST_CONDITION_COMMAND);
        }
        else {
            ga.unbox(TypeUtil.getType(boolean.class));
        }
        ga.ifZCmp(GeneratorAdapter.EQ, label);
    }

    public void checkContructorCallArguments(ArgumentListExpression argumentListExpression) {
        List<Expression> argExprs = argumentListExpression.getExpressions();
        for (Expression argExpr : argExprs) {
            if (argExpr instanceof MethodCallExpression) {
                MethodCallExpression call = (MethodCallExpression) argExpr;
                Expression objExpr = call.getObj();
                String methodName = call.getMethod().toString();
                if (objExpr != null) {
                    if (objExpr instanceof VariableExpression &&
                            ((VariableExpression) objExpr).getVariableName().equals("this")) {
                        addError(new ClassGeneratorException("Cannot reference '" + classNode.getName() + "." +
                                methodName + "' before supertype constructor has been called", call));
                    }
                }
                else if (classNode.containsMethodWithSuperClass(methodName, -1)) {
                    addError(new ClassGeneratorException("Cannot reference '" + classNode.getName() + "." +
                            methodName + "' before supertype constructor has been called", call));
                }
            }
            else if (argExpr instanceof PropertyExpression) {
                PropertyExpression prop = (PropertyExpression) argExpr;
                Expression objExpr = prop.getObj();
                String propName = prop.getProperty().getExpressionName();
                if (objExpr != null) {
                    if (objExpr instanceof VariableExpression &&
                            ((VariableExpression) objExpr).getVariableName().equals("this")) {
                        addError(new ClassGeneratorException("Cannot reference '" + classNode.getName() + "." +
                                propName + "' before supertype constructor has been called", prop));
                    }
                }
                else if (classNode.containsMethodWithSuperClass(propName, -1)) {
                    addError(new ClassGeneratorException("Cannot reference '" + classNode.getName() + "." +
                            propName + "' before supertype constructor has been called", prop));
                }
            }
            else if (argExpr instanceof VariableExpression) {
                String varName = ((VariableExpression) argExpr).getVariableName();
                FieldNode fieldNode = classNode.findField(varName);
                if (fieldNode != null && !fieldNode.isStatic()) {
                    addError(new ClassGeneratorException("Cannot reference '" + classNode.getName() + "." +
                            varName + "' before supertype constructor has been called", argExpr));
                }
            }
        }
    }

    public void visitConstructorCallStatement(ConstructorCallStatement statement) {
        visitLineNumber(statement);
        ArgumentListExpression arguments = statement.getArguments();
        checkContructorCallArguments(arguments);
        ClassNode superClass = classNode.getSuperClassNode();
        Type superType = TypeUtil.getType(superClass);

        List<ConstructorNode> constructorNodes = getSortedConstructors(statement, superClass);
        ga.loadThis();
        visitArgumentListExpression(arguments);

        pushShell();
        visitClassExpression(new ClassExpression(superClass));
        makeArgumentListExpressionArray(arguments);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.CHOOSE_CONSTRUCOTR);

        // create switch
        Label[] targets = new Label[constructorNodes.size()];
        int[] indices = new int[constructorNodes.size()];
        for (int i = 0; i < targets.length; i++) {
            targets[i] = new Label();
            indices[i] = i;
        }
        Label defaultLabel = new Label();
        Label afterSwitch = new Label();
        ga.visitLookupSwitchInsn(defaultLabel, indices, targets);
        for (int i = 0; i < targets.length; i++) {
            ga.visitLabel(targets[i]);
            ConstructorNode cn = constructorNodes.get(i);
            ParameterNode[] parameters =  cn.getParameters();
            Method conMethod = MethodUtil.getConstructorMethod(parameters);
            ga.invokeConstructor(superType, conMethod);
            ga.goTo(afterSwitch);
        }
        ga.visitLabel(defaultLabel);
        Type exceptionType = Type.getType("java/lang/IllegalArgumentException");
        ga.newInstance(exceptionType);
        ga.dup();
        ga.push("illegal constructor number");
        ga.invokeConstructor(exceptionType, MethodUtil.getConstructorMethod(new Class[] {String.class}));
        ga.throwException();
        ga.visitLabel(afterSwitch);

/*
        ga.loadThis();
        Method conMethod = MethodUtil.getConstructorMethod();
        ga.invokeConstructor(superType, conMethod);
*/
    }

    private List getSortedConstructors(ConstructorCallStatement cotrCall, ClassNode invokerClassNode) {
        // sort in a new list to prevent side effects
        List<ConstructorNode> constructorNodes = invokerClassNode.getConstructorList();
        List<ConstructorNode> constructorNodeList  = new ArrayList();
        for (ConstructorNode cotrNode : constructorNodes) {
            if (cotrNode.getParameters().length == cotrCall.getArguments().getExpressions().size() ||
                    cotrNode.isVarArgs()) {
                constructorNodeList.add(cotrNode);
            }
        }

        Comparator comp = new Comparator() {
            public int compare(Object arg0, Object arg1) {
                ConstructorNode c1 = (ConstructorNode) arg0;
                ConstructorNode c2 = (ConstructorNode) arg1;
                String paramTypesStr1 = Arrays.toString(c1.getParameterTypes());
                String paramTypesStr2 = Arrays.toString(c2.getParameterTypes());
                return paramTypesStr1.compareTo(paramTypesStr2);
            }
        };
        Collections.sort(constructorNodeList, comp);
        return constructorNodeList;
    }

    public void visitAssertStatement(AssertStatement statement) {
        pushShell();
        Expression argument1 = statement.getArgument1();
        Expression argument2 = statement.getArgument2();
        String snippet = unit.getSourceCode().getSnippet(argument1);
        ga.push(snippet);
        int operator = -1;
        Expression left = null;
        Expression right = null;

        if (argument1 instanceof OperationExpression) {
            Token token = ((OperationExpression) argument1).getOperator();
            switch (token.getType()) {
                case GT:
                    operator = SoyaAssertionOperator.ASRT_GT;
                    break;
                case LT:
                    operator = SoyaAssertionOperator.ASRT_LT;
                    break;
                case GE:
                    operator = SoyaAssertionOperator.ASRT_GE;
                    break;
                case LE:
                    operator = SoyaAssertionOperator.ASRT_LE;
                    break;
                case EQUAL:
                    operator = SoyaAssertionOperator.ASRT_EQ;
                    break;
                case NOT_EQUAL:
                    operator = SoyaAssertionOperator.ASRT_NE;
                    break;
                case IS:
                    operator = SoyaAssertionOperator.ASRT_IS;
                    break;
                case REGEX_MATCH:
                    operator = SoyaAssertionOperator.ASRT_RM;
                    break;
                case REGEX_NOT_MATCH:
                    operator = SoyaAssertionOperator.ASRT_NR;
                    break;
            }
        }


        if (operator != -1) { // case 1: operator assertion
            OperationExpression operationExpression = (OperationExpression) argument1;
            left = operationExpression.getLeft();
            right = operationExpression.getRight();
            visitExpression(left);
            visitExpression(right);
            ga.push(operator);
            if (argument2 != null) {
                visitExpression(argument2);
            }
            else {
                ga.push("");
            }
            if (!isLoggableAssertionExpression(left)) {
                ga.push("");
            }
            else {
                String leftSnippet = unit.getSourceCode().getSnippet(left);
                ga.push(leftSnippet);
            }

            if (!isLoggableAssertionExpression(right)) {
                ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.ASSERT_OPERATOR_WITHOUT_RIGHT);
            }
            else {
                String rightSnippet = unit.getSourceCode().getSnippet(right);
                ga.push(rightSnippet);
                ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.ASSERT_OPERATOR);
            }
        }
        else { // case 2: boolean assertion
            visitExpression(statement.getArgument1());
            if (argument2 != null) {
                visitExpression(argument2);
            }
            else {
                ga.push("");
            }
            ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.ASSERT_TRUE);
        }
        pushNull();
    }

    private boolean isLoggableAssertionExpression(Expression expression) {
        if (expression instanceof ConstantExpression) {
            return false;
        }
        else if (expression instanceof VariableExpression) {
            VariableExpression variableExpression = (VariableExpression) expression;
            if (variableExpression.getVariableName().equals("null")) {
                return false;
            }
            if (unit.getClassLoader().classExists(variableExpression.getVariableName())) {
                return false;
            }
        }
        else if (expression instanceof PatternExpression) {
            return false;
        }
        else if (expression instanceof RegexPatternExpression) {
            if (((RegexPatternExpression) expression).getParts().size() == 0) {
                return false;
            }
        }
        return !expression.isConstant();
    }

    public void visitThrowStatement(ThrowStatement statement) {
        pushShell();
        Expression expression = statement.getExpression();
        visitExpression(expression);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.THROW_COMMAND);
        pushNull();
    }

    public void visitIfStatement(IfStatement statement) {
        Expression conditionExpression = statement.getCondtion();
        BlockNode ifBody = statement.getIfBody();
        BlockNode elseBody = statement.getElseBody();

        Label elseLabel = new Label();
        jumpIfFalse(conditionExpression, elseLabel);

        SymbolTable ifSt = new SymbolTable(stackFrame.getSymbolTable(), ScopeType.SCOPE_IF);
//        visitBlockNodeWithNewSymbolTable(ifBody, ifSt);
        pushVarScope(ifSt);
        visitBlockNode(ifBody);
        popVarScope();

        Label endLabel = new Label();
        ga.goTo(endLabel);
        ga.visitLabel(elseLabel);

        if (elseBody != null) {
            SymbolTable elseSt = new SymbolTable(stackFrame.getSymbolTable(), ScopeType.SCOPE_ELSE);
            elseSt.setIfSymbolTable(ifSt);

            pushVarScope(elseSt);
            visitBlockNode(elseBody);
            popVarScope();
        }
        else {
            pushNull();
        }
        ga.visitLabel(endLabel);
    }


    public void visitForStatement(ForStatement statement) {
        boolean forEach = statement.isForEach();
        BlockNode body = statement.getBody();
        
        SymbolTable st = new SymbolTable(stackFrame.getSymbolTable(), ScopeType.SCOPE_LOOP);


        if (forEach) {
        	visitForEachStatement(statement, body);
        }
        else {
            stackFrame.pushVariableScope(st);
            Expression initExpression = statement.getInitialExpression();
            Expression conditionExpression = statement.getConditionExpression();
            Expression stepExpression = statement.getStepExpression();

            if (initExpression != null) {
                visitExpression(initExpression);
                ga.pop();
            }

            Label beginLabel = new Label();
            ga.visitLabel(beginLabel);

            Label endLabel = new Label();
            stackFrame.getSymbolTable().setEndLabel(endLabel);
            if (conditionExpression != null) {
                jumpIfFalse(conditionExpression, endLabel);
            }

            visitBlockNode(body);
            ga.pop();

            if (stepExpression != null) {
                visitExpression(stepExpression);
                ga.pop();
            }

            ga.goTo(beginLabel);
            ga.visitLabel(endLabel);
            stackFrame.popVariableScope();
            pushNull();
        }
    }
    
    public void visitForEachStatement(ForStatement statement, BlockNode body) {
        SymbolTable foreachSt = new SymbolTable(stackFrame.getSymbolTable(), ScopeType.SCOPE_LOOP);
        stackFrame.pushVariableScope(foreachSt);
        OperationExpression inExpression = statement.getInExpression();
        VariableExpression variableExresspion = (VariableExpression)inExpression.getLeft();
        Expression rightExpression = inExpression.getRight();
        String varName = variableExresspion.getVariableName();
        String rangeName = varName + "_object";
        String indexName = varName + "_index";

        Label beginLabel = new Label();
        Label endLabel = new Label();

        visitExpression(rightExpression);
        ga.dup();
        int rangeIndex = getStackFrame().getVariableWriter().declareLocalVariable(this, rangeName);

        ga.newInstance(TypeUtil.P_INT_TYPE);
        ga.dup();
        ga.push(0);
        ga.invokeConstructor(TypeUtil.P_INT_TYPE, MethodUtil.getConstructorMethod(int.class));
        ga.dup();
        int index = getStackFrame().getVariableWriter().declareLocalVariable(this, indexName);
        ga.invokeVirtual(TypeUtil.P_INT_TYPE, MethodUtil.getMethod("int getValue ()"));
        ga.invokeInterface(TypeUtil.P_COLLECTION_TYPE, MethodUtil.getMethod("java.lang.Object getWithIndex (int)"));
        int varIndex = getStackFrame().getVariableWriter().declareLocalVariable(this, varName);

        ga.mark(beginLabel);

        visitBlockNode(body);
        ga.pop();

        ga.loadLocal(index);
        ga.invokeVirtual(TypeUtil.P_INT_TYPE, MethodUtil.getMethod("soya.lang.Int increment ()"));
        ga.dup();
        ga.storeLocal(index);
        ga.invokeVirtual(TypeUtil.P_INT_TYPE, MethodUtil.getMethod("int getValue ()"));
        ga.loadLocal(rangeIndex);
        ga.invokeInterface(TypeUtil.P_COLLECTION_TYPE, MethodUtil.getMethod("int size ()"));
        ga.ifICmp(GeneratorAdapter.GE, endLabel);

        ga.loadLocal(rangeIndex);
        ga.loadLocal(index);
        ga.invokeVirtual(TypeUtil.P_INT_TYPE, MethodUtil.getMethod("int getValue ()"));
        ga.invokeInterface(TypeUtil.P_COLLECTION_TYPE, MethodUtil.getMethod("java.lang.Object getWithIndex (int)"));
        ga.storeLocal(varIndex);

        ga.goTo(beginLabel);
        ga.mark(endLabel);
        pushNull();
        popVarScope();
    }

    public void visitTryStatement(TryStatement tryStatement) {
        final Label startLabel = new Label();
        final Label tryEndLabel = new Label();
        Label endLabel = new Label();
        int catchLen = tryStatement.getCatchStatements().size();

        ga.visitLabel(startLabel);
        BlockNode block = tryStatement.getBlock();
        visitBlockNode(block);
        ga.goTo(endLabel);
        ga.visitLabel(tryEndLabel);

        for (int i = 0; i < catchLen; i++) {
            CatchStatement catchStatement = tryStatement.getCatchStatements().get(i);
            final Label catchLabel = new Label();
            ga.visitLabel(catchLabel);
            String parameterName = catchStatement.getParameter();
            stackFrame.getSymbolTable().getVariableWriter().declareLocalVariable(this, parameterName);
            visitBlockNode(catchStatement.getBlock());
            ga.goTo(endLabel);
            exceptions.add(new Runnable() {
                public void run() {
                ga.visitTryCatchBlock(startLabel, tryEndLabel, catchLabel, "java/lang/Throwable");
                }
            });
        }
        ga.visitLabel(endLabel);
    }

    public void visitReturnStatement(ReturnStatement statement) {
        Expression expression = statement.getExpression();
        if (expression != null) {
            visitExpression(expression);
        }
        else {
            pushNull();
        }
        ga.returnValue();
        stackFrame.getSymbolTable().setHasReturned(true);
    }

    public void visitBreakStatement(BreakStatement statement) {
        SymbolTable loopSt = stackFrame.getSymbolTable().getParent(ScopeType.SCOPE_LOOP);
        if (loopSt == null) {
            addError(new ClassGeneratorException("Break outside loop", statement));
            return;
        }
        stackFrame.popBreak();
        ga.goTo(loopSt.getEndLabel());
    }

    public void visitExpression(Expression expression) {
        if (expression instanceof ConstantExpression) {
            visitConstantExpression((ConstantExpression) expression);
        }
        else if (expression instanceof ClassExpression) {
            visitClassExpression((ClassExpression) expression);
        }
        else if (expression instanceof UnaryExpression) {
            visitUnaryExpression((UnaryExpression) expression);
        }
        else if (expression instanceof PostfixExpression) {
            visitPostfixExpression((PostfixExpression) expression);
        }
        else if (expression instanceof StringExpression) {
            visitStringExpression((StringExpression) expression);
        }
        else if (expression instanceof ReferenceExpression) {
            visitReferenceExpression((ReferenceExpression) expression);
        }
        else if (expression instanceof RegexPatternExpression) {
            visitRegexPatternExpression((RegexPatternExpression) expression);
        }
        else if (expression instanceof FileExpression) {
            visitFileExpression((FileExpression) expression);
        }
        else if (expression instanceof DateTimeExpression) {
            visitDateTimeExpression((DateTimeExpression) expression);
        }
        else if (expression instanceof TupleExpression) {
        	visitTupleExpression((TupleExpression) expression);
        }
        else if (expression instanceof ListExpression) {
            visitListExpression((ListExpression) expression);
        }
        else if (expression instanceof MapExpression) {
            visitMapExpression((MapExpression) expression);
        }
        else if (expression instanceof RangeExpression) {
            visitRangeExpression((RangeExpression) expression);
        }
        else if (expression instanceof PatternExpression) {
            visitPatternExpression((PatternExpression) expression);
        }
        else if (expression instanceof MatchExpression) {
        	visitMatchExpression((MatchExpression) expression);
        }
        else if (expression instanceof MatchVarDefExpression) {
            visitMatchVarDefExpression((MatchVarDefExpression) expression);
        }
        else if (expression instanceof ConditionExpression) {
             visitConditionExpression((ConditionExpression) expression);
        }
        else if (expression instanceof ClosureExpression) {
        	visitClosureExpression((ClosureExpression) expression);
        }
        else if (expression instanceof NewExpression) {
            visitNewExpression((NewExpression) expression);
        }
        else if (expression instanceof PropertyExpression) {
        	visitPropertyExpression((PropertyExpression) expression);
        }
        else if (expression instanceof PatternGroupExpression) {
            visitPatternGroupExpression((PatternGroupExpression) expression);
        }
        else if (expression instanceof OperationExpression) {
            visitOperationExpression((OperationExpression) expression);
        }
        else if (expression instanceof IndexExpression) {
        	visitIndexExpression((IndexExpression) expression);
        }
        else if (expression instanceof VariableExpression) {
            visitVariableExpression((VariableExpression) expression);
        }
        else if (expression instanceof MethodCallExpression) {
            visitMethodCallExpression((MethodCallExpression) expression);
        }
    }

    public void visitStringExpression(StringExpression expression) {
        List<Expression> parts = expression.getParts();
        Type stringBufferType = TypeUtil.getType(StringBuffer.class);
        ga.newInstance(stringBufferType);
        ga.dup();
        ga.invokeConstructor(stringBufferType, MethodUtil.getConstructorMethod());
        for (int i = 0; i < parts.size(); i++) {
            Expression part = parts.get(i);
            if (part instanceof ConstantExpression) {
                Object value = ((ConstantExpression) part).getValue();
                if (value instanceof String && ((String) value).isEmpty()) {
                    continue;   // Skip empty strings
                }
            }
            visitExpression(part);
            ga.invokeVirtual(stringBufferType, MethodUtil.STRING_BUFFER_APPEND_COMMAND);
        }
        ga.invokeVirtual(stringBufferType, MethodUtil.TO_STRING_COMMAND);
    }

    public void visitReferenceExpression(ReferenceExpression expression) {
        pushShell();
        ga.push(classNode.getName());
        this.loadThis();
        VariableExpression variableExpression = (VariableExpression) expression.getNameExpression();
        ga.push(variableExpression.getVariableName());
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.GET_REFERENCE);
    }

    public void visitRegexPatternExpression(RegexPatternExpression expression) {
/*
        ga.newInstance(TypeUtil.P_REGEX_PATTERN_TYPE);
        ga.dup();
        visitStringExpression(new StringExpression(expression.getParts()));
        ga.push(expression.getFlag());
        ga.invokeConstructor(TypeUtil.P_REGEX_PATTERN_TYPE, MethodUtil.getConstructorMethod(new ClassNode[] {
                ClassNode.STRING, ClassNode.INT
        }));
*/
        pushShell();
        visitStringExpression(new StringExpression(expression.getParts()));
        ga.push(expression.getFlag());
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.REGEX_PATTERN);
    }

    public void visitFileExpression(FileExpression expression) {
        ga.newInstance(TypeUtil.P_FILE);
        ga.dup();
        visitStringExpression(new StringExpression(expression.getParts()));
        ga.invokeConstructor(TypeUtil.P_FILE, MethodUtil.getConstructorMethod(ClassNode.STRING));
    }

    public void visitDateTimeExpression(DateTimeExpression expression) {
        ClassNode type = expression.getType();
        if (type == ClassNode.PDURATION) {
        	ga.push(expression.getHour());
        	ga.push(expression.getMinute());
        	ga.push(expression.getSecond());
        	ga.invokeStatic(TypeUtil.getType(DateTimeUtil.class), MethodUtil.CREATE_TIME_COMMAND);
        }
        else if (type == ClassNode.DATE) {
            pushShell();
//            ga.newInstance(TypeUtil.P_DATE);
//            ga.dup();
        	ga.push(expression.getYear());
        	ga.push(expression.getMonth());
        	ga.push(expression.getDay());
        	ga.push(expression.getHour());
        	ga.push(expression.getMinute());
        	ga.push(expression.getSecond());
            Expression timezone = expression.getTimezone();
            if (timezone != null) {
                visitExpression(timezone);
                ga.checkCast(TypeUtil.OBJECT_TYPE);
                ga.invokeVirtual(TypeUtil.OBJECT_TYPE, MethodUtil.getMethod("java.lang.String toString ()"));
            }
            else {
                ga.push("");
            }
            ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.NEW_DATE_TIME_COMMAND);

/*
            if (timezone != null) {
                visitExpression(timezone);
                ga.checkCast(TypeUtil.OBJECT_TYPE);
                ga.invokeVirtual(TypeUtil.OBJECT_TYPE, MethodUtil.getMethod("java.lang.String toString ()"));
                ga.invokeStatic(TypeUtil.getType(DateTimeUtil.class), MethodUtil.CREATE_DATE_TIME_COMMAND);
                ga.invokeConstructor(TypeUtil.P_DATE, MethodUtil.getConstructorMethod(new Class[] {Date.class}));
            }
            else {
                ga.push("");
                ga.invokeStatic(TypeUtil.getType(DateTimeUtil.class), MethodUtil.CREATE_DATE_TIME_COMMAND);
                ga.invokeConstructor(TypeUtil.P_DATE, MethodUtil.getConstructorMethod(new Class[] {Date.class}));
            }
*/
        }
    }
    
    public void visitTupleExpression(TupleExpression expression) {
        List<Expression> expressions = expression.getItems();
        ga.newInstance(TypeUtil.P_TUPLE);
        ga.dup();
        ga.push(expressions.size());
        ga.newArray(TypeUtil.OBJECT_TYPE);
        for (int i = 0; i < expressions.size(); i++) {
        	ga.dup();
        	ga.push(i);
            Expression expr = expressions.get(i);
            visitExpression(expr);
            ga.arrayStore(TypeUtil.OBJECT_TYPE);
        }
        ga.push(expression.isPair());
        ga.invokeConstructor(TypeUtil.P_TUPLE, MethodUtil.getConstructorMethod(new ClassNode[] {
        	ClassNode.OBJECT_ARRAY, ClassNode.BOOLEAN
        }));
    }

    private void defineExpresionMarchVarItem(Expression expression) {
        if (expression instanceof ListExpression) {
            ListExpression listExpression = (ListExpression) expression;
            for (Expression expr : listExpression.getItems()) {
                if (expr instanceof ListExpression) {
                    defineExpresionMarchVarItem(expr);
                }
                else if (expr instanceof MatchVarDefExpression) {
                    MatchVarDefExpression mvarDefExpression = (MatchVarDefExpression) expr;
                    stackFrame.getSymbolTable().getVariableWriter().declareNullVariable(this, mvarDefExpression.getName(), expression);
                }
            }
        }
        else if (expression instanceof MapExpression) {
            MapExpression mapExpression = (MapExpression) expression;
            for (MapEntryExpression entryExpr : mapExpression.getEntries()) {
                Expression valueExpr = entryExpr.getValue();
                if (valueExpr instanceof ListExpression) {
                    defineExpresionMarchVarItem(valueExpr);
                }
                else if (valueExpr instanceof MatchVarDefExpression) {
                    MatchVarDefExpression mvarDefExpression = (MatchVarDefExpression) valueExpr;
                    stackFrame.getSymbolTable().getVariableWriter().declareNullVariable(this, mvarDefExpression.getName(), expression);
                }

            }
        }
    }

    public void visitListExpression(ListExpression expression) {
    	if (expression instanceof TupleExpression) {
    		visitTupleExpression((TupleExpression) expression);
    		return;
    	}
        List<Expression> expressions = expression.getItems();
        defineExpresionMarchVarItem(expression);

        ga.newInstance(TypeUtil.P_LIST);
        ga.dup();
        ga.invokeConstructor(TypeUtil.P_LIST, MethodUtil.getConstructorMethod());
        Method addMethod = MethodUtil.getMethod("boolean add (Object)");
        for (int i = 0; i < expressions.size(); i++) {
        	ga.dup();
            Expression expr = expressions.get(i);
            visitExpression(expr);
            ga.invokeVirtual(TypeUtil.P_LIST, addMethod);
            ga.pop();
        }
    }

    public void visitMapExpression(MapExpression expression) {
        defineExpresionMarchVarItem(expression);
        List<MapEntryExpression> entries = expression.getEntries();
        Type mapType = TypeUtil.P_MAP_TYPE;
        ga.newInstance(mapType);
        ga.dup();
        ga.invokeConstructor(mapType, MethodUtil.getConstructorMethod());
        for (MapEntryExpression entry : entries) {
        	ga.dup();
            Expression keyExpr = entry.getKey();
            Expression valueExpr = entry.getValue();
            visitExpression(keyExpr);
            visitExpression(valueExpr);
            ga.invokeVirtual(mapType, MethodUtil.MAP_PUT_COMMAND);
            ga.pop();
        }
    }

    public void visitRangeExpression(RangeExpression expression) {
        Type ObjectRangeType = TypeUtil.getType(ClassNode.POBJECT_RANGE);
        Expression fromExpr = expression.getFrom();
        Expression toExpr = expression.getTo();
        ga.newInstance(ObjectRangeType);
        ga.dup();
        visitExpression(fromExpr);
        visitExpression(toExpr);
        ga.push(expression.isIncludeFrom());
        ga.push(expression.isIncludeTo());
        ga.invokeConstructor(ObjectRangeType, MethodUtil.getConstructorMethod(
                new Class[]{Comparable.class, Comparable.class, boolean.class, boolean.class}));
    }


    public void visitPatternExpression(PatternExpression expression) {
        visitPatternExpression(expression, null);
    }

    public void visitPatternExpression(PatternExpression expression, String alias) {
        ClassNode type = expression.getClassNode();
        ga.newInstance(TypeUtil.P_CLASS_PATTERN_TYPE);
        ga.dup();
        pushShell();
        ga.push(type.getName());
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.GET_METACLASS);
        if (alias != null) {
            ga.push(alias);
            stackFrame.getSymbolTable().getVariableWriter().loadVarScopeFromLocal(this);
            ga.invokeConstructor(TypeUtil.P_CLASS_PATTERN_TYPE, MethodUtil.getConstructorMethod(new ClassNode[] {
                ClassNode.META_CLASS, ClassNode.STRING, ClassNode.PVarScope
            }));
        }
        else {
            ga.invokeConstructor(TypeUtil.P_CLASS_PATTERN_TYPE, MethodUtil.getConstructorMethod(ClassNode.META_CLASS));
        }
    }
    
    public void visitMatchExpression(MatchExpression expression) {
    	Expression expr = expression.getExpression();
    	org.soya.ast.MatchBlock block = expression.getBlock();
    	List<MatchItem> items = block.getItems();

        SymbolTable matchSt = new SymbolTable(stackFrame.getSymbolTable(), ScopeType.SCOPE_MATCH);
        pushVarScope(matchSt);

        if (expr == null) {
            if (stackFrame.getArgumentVariables().size() == 0) {
//                addError(new ClassGeneratorException("invlide match statement", expression));
//                return;
                expr = new VariableExpression("null");
            }
            else {
                expr = new VariableExpression(stackFrame.getFirstArgumentName());
            }
        }

        stackFrame.getVariableWriter().declareVariable(this, "_match_obj", expr);

        Label noMatchLabel = new Label();
    	Label endLabel = new Label();

    	int len = items.size();

    	Label[] lbs = new Label[len];

    	for (int i = 0; i < len - 1; i++) {
    		lbs[i] = new Label();
    	}

    	for (int i = 0; i < len; i++) {
    		MatchItem item = items.get(i);
    		visitMatchItem(this, matchSt, item, i, lbs, noMatchLabel, endLabel);
    	}

        ga.visitLabel(noMatchLabel);
        pushNull();
        ga.visitLabel(endLabel);
        popVarScope();
    }

    public void visitMatchItem(WriterVisitor wv, SymbolTable parentSymtableTable, MatchItem item, int i, Label[] labels,
                               Label noMatchLabel, Label endLabel) {
        SymbolTable st = new SymbolTable(wv.getStackFrame().getSymbolTable(), ScopeType.SCOPE_MATCH_ITEM);
        wv.pushVarScope(st);

        visitLineNumber(item);
        if (i > 0) {
            ga.visitLabel(labels[i - 1]);
        }

        pushShell();
        wv.getStackFrame().getVariableWriter().loadVariable(wv, "_match_obj", item);
        Expression patternExpr = item.getPattern();
        wv.visitExpression(patternExpr);
    	ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.IS_MATCH_COMMAND);

    	if (i < labels.length - 1) {
    		ga.ifZCmp(GeneratorAdapter.EQ, labels[i]);
    	}
    	else {
    		ga.ifZCmp(GeneratorAdapter.EQ, noMatchLabel);
    	}

//        wv.pushVarScope(st);
        BlockNode blockNode = item.getBlock();
        if (blockNode != null) {
//        WriterVisitor bwv = new WriterVisitor(classGenerator, unit, ga, st, classNode, methodNode, wv.getVariableWriter());
//        bwv.visitBlockNode(blockNode);
            wv.visitBlockNode(blockNode);
        }
        else {
            visitExpression(new ConstantExpression(true));
        }
        wv.popVarScope();

        ga.goTo(endLabel);
    }

    public void visitMatchVarDefExpression(MatchVarDefExpression expression) {
        Expression expr = expression.getExpression();
        String alias = expression.getName();
        if (expr instanceof PatternExpression) {
            visitPatternExpression((PatternExpression) expr, alias);
        }
        else if (expr instanceof ConstantExpression) {
            addError(new ClassGeneratorException("invalid alias '" + alias + "'", expression));
        }
        else {
            ga.newInstance(TypeUtil.P_OBJECT_PATTERN_TYPE);
            ga.dup();
            visitExpression(expr);
            ga.push(alias);
            stackFrame.getSymbolTable().getVariableWriter().loadVarScopeFromLocal(this);
            ga.invokeConstructor(TypeUtil.P_OBJECT_PATTERN_TYPE, MethodUtil.getConstructorMethod(new ClassNode[] {
                    ClassNode.OBJECT, ClassNode.STRING, ClassNode.PVarScope
            }));
        }
    }

    public void visitConditionExpression(ConditionExpression expression) {
        Expression condition = expression.getCondition();
        Expression trueExpression = expression.getTrueExpression();
        Expression falseExpression = expression.getFalseExpression();

        if (trueExpression == null && falseExpression != null) {
            visitExpression(condition);
            String conditionVarName = "%$condition";
            int count = getStackFrame().getVariableWriter().declareTempDotVariable(this, conditionVarName);
            condition = new VariableExpression(conditionVarName + "_" + count);
            trueExpression = new VariableExpression(conditionVarName + "_" + count);
        }
        else if (falseExpression == null) {
            falseExpression = new ConstantExpression(null);
        }

        if (!(trueExpression instanceof BlockNode)) {
            trueExpression =  new BlockNode(new ExpressionStatement(trueExpression));
        }
        if (!(falseExpression instanceof BlockNode)) {
            falseExpression =  new BlockNode(new ExpressionStatement(falseExpression));
        }

        visitIfStatement(new IfStatement(
                condition, (BlockNode) trueExpression, (BlockNode) falseExpression));
    }

    public void visitClosureExpression(ClosureExpression expression) {
        ClosureWriter closureWriter = new ClosureWriter(classGenerator, methodNode, stackFrame.getVariableWriter());
        ClassNode closureClass = closureWriter.getClosureClass(expression);

        pushShell();
        visitClassExpression(new ClassExpression(closureClass));
        ArgumentListExpression args = new ArgumentListExpression();
        args.addArgument(new VariableExpression("this"));
//        args.addArgument(new VariableExpression(ScopeVariableWriter.LOCAL_NAME_PREFIX));
        VariableWriter variableWriter = stackFrame.getVariableWriter();
        args.addArgument(new VariableExpression(((ScopeVariableWriter) variableWriter).getScopeClassLocalName()));
        args.addArgument(new ConstantExpression(expression.isCallOnAssign()));
        makeArgumentListExpressionArray(args);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.NEW_INSTANCE_COMMAND);
        ga.dup();
        ga.checkCast(TypeUtil.getType(Closure.class));
        ga.push(expression.getParameters().size());
        ga.invokeVirtual(TypeUtil.getType(Closure.class), MethodUtil.getMethod("void setNumberOfParameters (int)"));

        /*
        Type closureType = TypeUtil.getType(closureClass);
        ga.newInstance(closureType);
        ga.dup();
        loadThis();
        ga.invokeConstructor(closureType, MethodUtil.getConstructorMethod(new Class[] {
        	Object.class
        }));
        */
    }
    
    public void visitNewExpression(NewExpression expression) {
        ClassNode classNode = expression.getType();
        ArgumentListExpression args = expression.getArguments();
        pushShell();
        visitClassExpression(new ClassExpression(classNode));
        makeArgumentListExpressionArray(args);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.NEW_INSTANCE_COMMAND);
    }
    
    public void visitPropertyExpression(PropertyExpression expression) {
    	Expression objExpr = expression.getObj();
    	Expression propExpr = expression.getProperty();
        String dotTmpName = "%&tmp";
        String propName = null;
        int tmpCount = 0;

        if (expression.getParentExpression() == null) {
            expression.setEndLabel(new Label());
            expression.setNullLabel(new Label());
        }

        if (objExpr instanceof DotExpression) {
            ((DotExpression) objExpr).setParentExpression(expression);
        }
        if (expression.isStatic()) {
            VariableExpression varExpr = (VariableExpression) propExpr;
            propName = varExpr.getVariableName();
        }
        if (expression.isStatic() && classNode.containsStaticMethodWithSuperClass(propName, 0)) {
            visitMethodCallExpression(new MethodCallExpression(classNode, new ConstantExpression(propName), new ArgumentListExpression()));
        }
        else {
            if (!expression.isStatic()) {
                visitExpression(objExpr);
                tmpCount = getStackFrame().getVariableWriter().declareTempDotVariable(this, dotTmpName);
            }

            if (!expression.isStatic() && expression.isCheckNull()) {
                Label lastNullLabel = expression.getLastParentNullLabel();
                jumpIfFalse(new OperationExpression(
                        new SoyaToken(NOT_EQUAL),
                        new VariableExpression(dotTmpName + "_" + tmpCount),
                        new ConstantExpression(null)), lastNullLabel);
            }

            if (propExpr instanceof VariableExpression) {
                VariableExpression varExpr = (VariableExpression) propExpr;
                propName = varExpr.getVariableName();
            }

            if (expression.isVisitEach()) {
                pushShell();
                ga.push(classNode.getName());
                visitExpression(objExpr);
                ga.push(propName);
                makeArgumentListExpressionArray(new ArgumentListExpression(new Expression[0]));
                ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.STAR_DOT);
                return;
            }

            if (propName.equals("class") || propName.equals("getClass")) {
                pushShell();
                visitExpression(objExpr);
                ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.GET_OBJECT_CLASS);
                return;
            }


            pushShell();
            ga.push(classNode.getName());

            if (expression.isStatic()) {
                ClassNode ownerClass = expression.getOwnerType();
                if (ownerClass != null) {
                    ga.push(ownerClass.getName());
                    ga.push(propName);
                }
            }
            else {
    //        	visitExpression(objExpr);
                getStackFrame().getVariableWriter().loadLocalVariable(this, dotTmpName + "_" + tmpCount);
                ga.push(propName);
            }

            if (expression.isReference() && !expression.isStatic()) {
                ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.GET_REFERENCE);
            }
            else if (expression.isStatic()) {
                ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.GET_STATIC_VALUE);
            }
            else {
                ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.GET_PROPERTY_VALUE);
            }
        }

        if (expression.getParentExpression() == null) {
            ga.goTo(expression.getEndLabel());
            ga.visitLabel(expression.getNullLabel());
            pushNull();
            ga.visitLabel(expression.getEndLabel());
        }
    }

    public void visitSetPropertyValueExpression(PropertyExpression expression, Expression valueExpr) {
    	Expression objExpr = expression.getObj();
    	Expression propExpr = expression.getProperty();

    	if (propExpr instanceof VariableExpression) {
    		VariableExpression varExpr = (VariableExpression) propExpr;
        	pushShell();
        	visitExpression(objExpr);
        	ga.push(varExpr.getVariableName());
        	ga.push(1);
            ga.newArray(TypeUtil.OBJECT_TYPE);
            ga.dup();
        	ga.push(0);
        	visitExpression(valueExpr);
        	ga.arrayStore(TypeUtil.OBJECT_TYPE);
        	ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.SET_PROPERTY_VALUE);
        	pushNull();
    	}
    }

    public void visitConstantExpression(ConstantExpression expression) {
        Object value = expression.getValue();
        if (value == null || value instanceof Null) {
            pushNull();
        }
        else if (value instanceof Integer) {
            newInt(((Integer) value).intValue());
        }
        else if (value instanceof Double) {
            newDouble(((Double) value).doubleValue());
        }
        else if (value instanceof Float) {
        	newFloat(((Float) value).floatValue());
        }
        else if (expression.getType() == ClassNode.REGEX_PATTERN) {
            newConstantRegex((String) value);
        }
        else if (value instanceof String) {
            newConstantString((String) value);
        }
        else if (value instanceof Boolean) {
        	ga.push(((Boolean) value).booleanValue());
//        	ga.box(TypeUtil.BOOLEAN_TYPE);
            ga.box(TypeUtil.getType(boolean.class));
        }
        else if (value instanceof java.net.URL) {
            newURL((URL) value);
        }
        else if (value instanceof File) {
        	newFile((File) value);
        }
    }

    public void visitClassExpression(ClassExpression expression) {
        ga.push(TypeUtil.getType(expression.getClassNode()));
    }

    public void visitUnaryExpression(UnaryExpression expression) {
        Token opt = expression.getOperator();

        if (opt.getType() == NOT) {
            visitNotPattern(expression.getExpression());
            return;
        }

        pushShell();
        visitExpression(expression.getExpression());
        if (opt.getType() == U_PLUS) {
            ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.POSITIVE);
        }
        else if (opt.getType() == U_MINUS) {
            ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.NEGAVITE);
        }
        else if (opt.getType() == LNOT) {
            ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.NOT_COMMAND);
            ga.box(TypeUtil.getType(boolean.class));
        }
    }

    public void visitPostfixExpression(PostfixExpression expression) {
        Token opt = expression.getOperator();
        Expression expr = expression.getExpression();
        int optType = 0;
        switch (opt.getType()) {
            case POST_INC:
                optType = PLUS;
                break;
            case POST_DEC:
                optType = MINUS;
                break;
        }
        if (optType != 0) {
            visitExpression(expr);
            visitAssignExpression(expr,
                    new OperationExpression(
                        new SoyaToken(optType),
                        expr,
                        new ConstantExpression(1)));
            ga.pop();
        }
    }

    public void newInt(int value) {
        ga.newInstance(TypeUtil.P_INT_TYPE);
        ga.dup();
        ga.push(value);
        ga.invokeConstructor(TypeUtil.P_INT_TYPE, MethodUtil.getConstructorMethod(ClassNode.INT));
    }

    public void newFloat(float value) {
        ga.newInstance(TypeUtil.P_FLOAT_TYPE);
        ga.dup();
        ga.push(value);
        ga.invokeConstructor(TypeUtil.P_FLOAT_TYPE, MethodUtil.getConstructorMethod(ClassNode.FLOAT));
    }

    public void newDouble(double value) {
        ga.newInstance(TypeUtil.P_FLOAT_TYPE);
        ga.dup();
        ga.push(value);
        ga.invokeConstructor(TypeUtil.P_FLOAT_TYPE, MethodUtil.getConstructorMethod(ClassNode.DOUBLE));
    }

    public void newConstantRegex(String value) {
        ga.newInstance(TypeUtil.P_REGEX_PATTERN_TYPE);
        ga.dup();
        ga.push(value);
        ga.invokeConstructor(TypeUtil.P_REGEX_PATTERN_TYPE, MethodUtil.getConstructorMethod(ClassNode.STRING));
    }

    public void newConstantString(String value) {
        Type stringType = TypeUtil.getType(ClassNode.PSTRING);
        ga.newInstance(stringType);
        ga.dup();
        ga.push(value);
        ga.invokeConstructor(stringType, MethodUtil.getConstructorMethod(ClassNode.STRING));
    }

    public void newURL(URL url) {
        ga.newInstance(TypeUtil.P_URL);
        ga.dup();
        ga.push(url.toString());
        ga.invokeConstructor(TypeUtil.P_URL, MethodUtil.getConstructorMethod(ClassNode.STRING));
    }
    
    public void newFile(File file) {
    	ga.newInstance(TypeUtil.P_FILE);
    	ga.dup();
    	ga.push(file.getPath());
    	ga.invokeConstructor(TypeUtil.P_FILE, MethodUtil.getConstructorMethod(ClassNode.STRING));
    }
    
    public void visitIndexExpression(IndexExpression expression) {
    	pushShell();
    	Expression indexee = expression.getIndexee();
    	visitExpression(indexee);
    	ArgumentListExpression args = expression.getArguments();

    	makeIndexArgumentListExpressionArray(args);
    	ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.INDEX_COMMAND);
    }

    public void visitVariableExpression(VariableExpression expression) {
        String varName = expression.getVariableName();
        if (varName.equals("it")) {
        }
        if (expression == VariableExpression.INNER_THIS) {
            ga.loadThis();
        }
        else if (varName.equals("null")) {
            pushNull();
        }
        else if (varName.equals("this") || varName.equals("super")) {
        	loadThis();
        }
        else if (stackFrame.containsArgument(varName)) {
            stackFrame.getSymbolTable().getVariableWriter().loadArgument(this, varName);
        }
/*        else if (stackFrame.getSymbolTable() == null) {
            if (classNode.containsField(varName)) {
                FieldNode fieldNode = classNode.getFieldNode(varName);

//                loadFieldValueFromThis(varName, TypeUtil.getType(classNode), TypeUtil.OBJECT_TYPE);
                loadField(fieldNode);
            }
            else {
                invokeDynamicMethod(null, null, varName, false, null);
            }
        }*/
        else if (stackFrame.getSymbolTable().containsVariable(varName)) {
            stackFrame.loadVariable(varName, expression);
        }
        else if (stackFrame.getSymbolTable().containsLocalVariable(varName)) {
            stackFrame.getSymbolTable().getVariableWriter().loadLocalVariable(this, varName);
        }
        else if (classNode.getProperties().containsKey(varName)) {
        	loadFieldValueFromThis(varName, TypeUtil.getType(classNode), TypeUtil.OBJECT_TYPE);
        }
        else if (classNode.containsField(varName)) {
//        	loadFieldValueFromThis(varName, TypeUtil.getType(classNode), TypeUtil.OBJECT_TYPE);
            loadField(classNode.getFieldNode(varName));
        }
        else if (classNode.containsStaticMethodWithSuperClass(varName, 0)) {
            visitMethodCallExpression(new MethodCallExpression(classNode, new ConstantExpression(varName), new ArgumentListExpression()));
        }
        else if (needGetScriptBundle()) {
            pushShell();
            loadThis();
            ga.checkCast(TypeUtil.getType(Script.class));
            ga.push(varName);
            ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.getMethod("java.lang.Object getScriptVariable(soya.lang.Script, java.lang.String)"));
        }
        else {
        	invokeDynamicMethod(null, null, varName, false, null);
        }
    }

    public void visitPatternGroupExpression(PatternGroupExpression expression) {
        List<Expression> patterns = expression.getExpressions();
        if (patterns.size() == 1) {
            visitExpression(patterns.get(0));
        }
        else if (patterns.size() > 1) {
            OperationExpression operationExpression = null;
            SoyaToken opt = new SoyaToken(MAND);
            for (int i = 0 ; i < patterns.size(); i++) {
                Expression pattern = patterns.get(i);
                if (operationExpression == null) {
                    i++;
                    Expression pattern2 = patterns.get(i);
                    operationExpression = new OperationExpression(opt, pattern, pattern2);
                }
                else {
                    operationExpression = new OperationExpression(opt, operationExpression, pattern);
                }
            }
            String alias = expression.getAlias();
            if (expression.isArgument() && alias != null) {
                visitMatchVarDefExpression(new MatchVarDefExpression(alias, new PatternGroupExpression(patterns)));
            }
            else {
                visitOperationExpression(operationExpression);
            }
        }
    }

    public void visitOperationExpression(OperationExpression expression) {
        Expression leftExpr  = expression.getLeft();
        Expression rightExpr = expression.getRight();
        Token op = expression.getOperator();
        switch(op.getType()) {
	        case ASSIGN:
                visitAssignExpression(leftExpr, rightExpr);
                break;
            case PLUS_ASSIGN:
	        case MINUS_ASSIGN:
	        case STAR_ASSIGN:
	        case DIV_ASSIGN:
                rightExpr = new OperationExpression(
                        new SoyaToken(getOperatorByAssignOperator(op.getType())),
                        leftExpr,
                        rightExpr);
	        	visitAssignExpression(leftExpr, rightExpr);
	        	break;
            case INIT_ASSIGN:
                visitInitAssignCommand((VariableExpression) leftExpr, rightExpr);
                break;
            case PLUS:
                visitPlusCommand(leftExpr, rightExpr);
                break;
            case MINUS:
                visitMinusCommand(leftExpr, rightExpr);
                break;
            case STAR:
                visitMultiCommand(leftExpr, rightExpr);
                break;
            case DIV:
                visitDivCommand(leftExpr, rightExpr);
                break;
            case MOD:
            	visitModCommand(leftExpr, rightExpr);
            	break;
            case POW:
            case DB_STAR:
                visitPowCommand(leftExpr, rightExpr);
                break;
            case SoyaParserTokenTypes.GT:
                visitGreateThenCommand(leftExpr, rightExpr);
                break;
            case SoyaParserTokenTypes.LT:
                visitLessThenCommand(leftExpr, rightExpr);
                break;
            case SoyaParserTokenTypes.GE:
                visitGreateEqualCommand(leftExpr, rightExpr);
                break;
            case SoyaParserTokenTypes.LE:
                visitLessEqualCommand(leftExpr, rightExpr);
                break;
            case SoyaParserTokenTypes.EQUAL:
                visitEqualCommand(leftExpr, rightExpr);
                break;
            case SoyaParserTokenTypes.NOT_EQUAL:
                visitNotEqualCommand(leftExpr, rightExpr);
                break;
            case LAND:
                visitAndCommand(leftExpr, rightExpr);
                break;
            case LOR:
                visitOrCommand(leftExpr, rightExpr);
                break;
            case BAND:
                visitBitAndCommand(leftExpr, rightExpr);
                break;
            case BOR:
                visitBitOrCommand(leftExpr, rightExpr);
                break;
            case IS:
                visitIsCoammand(leftExpr, rightExpr);
                break;
            case INSTANCEOF:
                visitInstanceOfCoammand(leftExpr, rightExpr);
                break;
            case XOR:
                visitXorCommand(leftExpr, rightExpr);
                break;
            case REGEX_MATCH:
                visitMatchRegexCommand(leftExpr, rightExpr);
                break;
            case REGEX_NOT_MATCH:
                visitNotMatchRegexCommand(leftExpr, rightExpr);
                break;
            case MOR:
                visitOrPattern(leftExpr, rightExpr);
                break;
            case MAND:
                visitAndPattern(leftExpr, rightExpr);
                break;
            case BSL:
                visitBitShiftLeft(leftExpr, rightExpr);
                break;
            case BSR:
                visitBitShiftRight(leftExpr, rightExpr);
                break;
            case SL:
                visitShitLeft(leftExpr, rightExpr);
                break;
            case SR:
                visitShitRight(leftExpr, rightExpr);
                break;
        }
    }

    private boolean needGetScriptBundle() {
        if (classNode.isScript() && !(methodWriter != null && methodWriter.isScriptRunMethod())) {
            return true;
        }
        if (classGenerator.isClosureClass()) {
            ClassNode parent = classNode.getOutermostClassNode();
            while (classGenerator.isClosureClass(parent)) {
                parent = parent.getOutermostClassNode();
            }
            if (parent.isScript()) {
                return true;
            }
        }
        return false;
    }
    
    public void visitAssignExpression(Expression leftExpr, Expression rightExpr) {
    	if (leftExpr instanceof VariableExpression) {
    		VariableExpression varExpr = (VariableExpression) leftExpr;
            String varName = varExpr.getVariableName();
            int i = stackFrame.getSymbolTable().getLocalVariable(varName);
            if (i != -1) {
                visitExpression(rightExpr);
	            ga.dup();
                stackFrame.getSymbolTable().getVariableWriter(i).storeLocalVariable(stackFrame.getSymbolTable(), this, varName);
            }
            else if (stackFrame.getSymbolTable().containsVariable(varName)) {
                stackFrame.storeVariable(varName, rightExpr);
            }
            else if (needGetScriptBundle()) {
                pushShell();
                loadThis();
                ga.checkCast(TypeUtil.getType(Script.class));
                ga.push(varName);
                visitExpression(rightExpr);
                ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.getMethod("void setScriptVariable (soya.lang.Script, java.lang.String, java.lang.Object)"));
            }
            else {
                loadThis();
                visitExpression(rightExpr);
                ga.putField(TypeUtil.getType(classNode), varName, TypeUtil.OBJECT_TYPE);
            }
            pushNull();
    	}
    	else if (leftExpr instanceof PropertyExpression) {
    		visitSetPropertyValueExpression((PropertyExpression) leftExpr, rightExpr);
    	}
        else if (leftExpr instanceof MethodCallExpression) {
            MethodCallExpression methodCall = (MethodCallExpression) leftExpr;
            String methodName = "";
            if (methodCall.getMethod() instanceof ConstantExpression) {
                methodName = ((ConstantExpression) methodCall.getMethod()).getValue().toString();
            }
            else  {
                methodName = methodCall.getMethod().toString();
            }
            ArgumentListExpression argList = methodCall.getArguments();
            Expression assignable = rightExpr;
            if (argList.isHaveMatchVarDef()) {
                if (argList.isHaveMatchVarInExpression(rightExpr)) {
                    List<ParameterNode> closureParams = new ArrayList<ParameterNode>();
                    BlockNode blockNode = new BlockNode();
                    blockNode.addStatement(new ExpressionStatement(rightExpr));
                    ClosureExpression closureExpression = new ClosureExpression(
                            closureParams, blockNode, true);
                    assignable = closureExpression;
                }
            }
            argList.addArgument(assignable);
            MethodCallExpression setterMethod = new MethodCallExpression(
                    methodCall.getObj(),
                    new ConstantExpression(NameUtil.toSetterName(methodName)),
                    argList);
            visitMethodCallExpression(setterMethod);
        }
        else if (leftExpr instanceof IndexExpression) {
        	IndexExpression indexExpr = (IndexExpression) leftExpr;
            ArgumentListExpression argList = indexExpr.getArguments();
            boolean hasArgVarDef = argList.isHaveMatchVarDef() && argList.isHaveMatchVarInExpression(rightExpr);
            if (hasArgVarDef) {
                SymbolTable symTable = new SymbolTable(stackFrame.getSymbolTable(), ScopeType.SCOPE_INVOKE);
                pushVarScope(symTable);
                List<MatchVarDefExpression> matchVarDefExpressions = argList.getMatchExpressions();
                for (MatchVarDefExpression mvarDefExpression : matchVarDefExpressions) {
                    stackFrame.getSymbolTable().getVariableWriter().declareNullVariable(this, mvarDefExpression.getName(), mvarDefExpression);
                }
            }
            pushShell();
        	visitExpression(indexExpr.getIndexee());
            makeIndexArgumentListExpressionArray(argList);
            Expression assignable = rightExpr;
            if (hasArgVarDef) {
                List<ParameterNode> closureParams = new ArrayList<ParameterNode>();
                BlockNode blockNode = new BlockNode();
                blockNode.addStatement(new ExpressionStatement(rightExpr));
                ClosureExpression closureExpression = new ClosureExpression(
                        closureParams, blockNode, true);
                assignable = closureExpression;
            }
            visitExpression(assignable);
        	ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.SET_INDEX_COMMAND);
            if (hasArgVarDef) {
                popVarScope();
            }
        }
    }

    private int getOperatorByAssignOperator(int op) {
        switch (op) {
            case PLUS_ASSIGN:
                return PLUS;
            case MINUS_ASSIGN:
                return MINUS;
            case STAR_ASSIGN:
                return STAR;
            case DIV_ASSIGN:
                return DIV;
        }
        return 0;
    }
    
    public void visitAssignOption(int op, Expression leftExpr, Expression rightExpr) {
        switch (op) {
	        case PLUS_ASSIGN:
	        	visitPlusCommand(leftExpr, rightExpr);
	            break;
	        case MINUS_ASSIGN:
	        	visitMinusCommand(leftExpr, rightExpr);
	        	break;
	        case STAR_ASSIGN:
	        	visitMultiCommand(leftExpr, rightExpr);
	        	break;
	        case DIV_ASSIGN:
	        	visitDivCommand(leftExpr, rightExpr);
	        	break;
        }
    }

    public void visitMethodCallExpression(MethodCallExpression expression) {
        Expression objExpression = expression.getObj();
        boolean isSuper = false;
        if (objExpression instanceof VariableExpression) {
            if (((VariableExpression) objExpression).getVariableName().equals("super")) {
                isSuper = true;
            }
        }
        ConstantExpression method = (ConstantExpression) expression.getMethod();
        ArgumentListExpression args = expression.getArguments();

        String methodName = (String) method.getValue();

        if (objExpression == null &&
                (stackFrame.containsArgument(methodName)
                        || stackFrame.getSymbolTable().containsLocalVariable(methodName)
                        || stackFrame.getSymbolTable().containsVariable(methodName))) {
            // call closure
            MethodCallExpression callClosureExpr = new MethodCallExpression(
                    new VariableExpression(methodName),
                    new ConstantExpression("call"),
                    args);
            visitMethodCallExpression(callClosureExpr);
        }
        else if (expression.isStatic()) {
            invokeStaticMethod(expression, expression.getOwnerType(), (String) method.getValue(), args, false);
        }
        else if (objExpression == null) {
            if (methodNode.isStatic() || classNode.containsStaticMethodWithSuperClass(methodName, args.getExpressions().size())) {
                invokeStaticMethod(expression, classNode, (String) method.getValue(), args, true);
            }
            else {
                invokeDynamicMethod(expression, objExpression, (String) method.getValue(), isSuper, args);
            }
        }
        else {
            invokeDynamicMethod(expression, objExpression, (String) method.getValue(), isSuper, args);
        }
    }

    public void invokeStaticMethod(MethodCallExpression expression, ClassNode ownerType, String methodName, ArgumentListExpression args, boolean evn) {
        if (args != null && args.isHaveMatchVarDef()) {
            SymbolTable symTable = new SymbolTable(stackFrame.getSymbolTable(), ScopeType.SCOPE_INVOKE);
            pushVarScope(symTable);
            List<MatchVarDefExpression> matchVarDefExpressions = args.getMatchExpressions();
            for (MatchVarDefExpression mvarDefExpression : matchVarDefExpressions) {
                stackFrame.getSymbolTable().getVariableWriter().declareNullVariable(this, mvarDefExpression.getName(), expression);
            }
        }
        pushShell();
        if (!evn) {
            ga.push(classNode.getName());
        }
        ga.push(ownerType.getName());
        ga.push(methodName);
        if (args == null) {
            ga.push(0);
            ga.newArray(TypeUtil.OBJECT_TYPE);
        }
        else {
            makeArgumentListExpressionArray(args);
        }
        if (evn) {
            ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.INVOKE_SYS_EVN_METHOD_COMMAND);
        }
        else {
            ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.INVOKE_STATIC_METHOD_COMMAND);
        }
        if (args != null && args.isHaveMatchVarDef()) {
            popVarScope();
        }
    }


    public void invokeDynamicMethod(MethodCallExpression expression, Expression objExpr, String methodName, boolean isSuper, ArgumentListExpression args) {
        String dotTmpName = "%&tmp";
        int tmpCount = 0;
        boolean defaultInvoker = false;

        if (objExpr != null) {
            visitExpression(objExpr);
            tmpCount = getStackFrame().getVariableWriter().declareTempDotVariable(this, dotTmpName);
        }

        if (expression != null && objExpr != null) {
            if (expression.getParentExpression() == null) {
                expression.setEndLabel(new Label());
                expression.setNullLabel(new Label());
            }

            if (objExpr instanceof DotExpression) {
                ((DotExpression) objExpr).setParentExpression(expression);
            }

            if (expression.isCheckNull()) {
                Label lastNullLabel = expression.getLastParentNullLabel();
                jumpIfFalse(new OperationExpression(
                        new SoyaToken(NOT_EQUAL),
                        new VariableExpression(dotTmpName + "_" + tmpCount),
                        new ConstantExpression(null)), lastNullLabel);
            }
        }

        pushShell();
        ga.push(classNode.getName());
        if (objExpr == null) {
            if (!methodNode.isStatic()) {
                loadThis();
            }
            defaultInvoker = true;
        }
        else {
            getStackFrame().getVariableWriter().loadLocalVariable(this, dotTmpName + "_" + tmpCount);
        }
/*
    	if (objExpr == null) {
    		loadThis();
    	}
    	else {
    		visitExpression(objExpr);
    	}
*/
        if (args != null && args.isHaveMatchVarDef()) {
            SymbolTable symTable = new SymbolTable(stackFrame.getSymbolTable(), ScopeType.SCOPE_INVOKE);
            pushVarScope(symTable);
            List<MatchVarDefExpression> matchVarDefExpressions = args.getMatchExpressions();
            for (MatchVarDefExpression mvarDefExpression : matchVarDefExpressions) {
                stackFrame.getSymbolTable().getVariableWriter().declareNullVariable(this, mvarDefExpression.getName(), objExpr);
            }
        }

        ga.push(methodName);
        if (args == null) {
        	ga.push(0);
        	ga.newArray(TypeUtil.OBJECT_TYPE);
        }
        else {
        	makeArgumentListExpressionArray(args);
        }
        if (defaultInvoker) {
            if (methodNode.isStatic()) {
                ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.INVOKE_STATIC_DEFAULT_INVOKER_METHOD_COMMAND);
            }
            else {
                ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.INVOKE_DEFAULT_INVOKER_METHOD_COMMAND);
            }
        }
        else if (expression.isVisitEach()) {
            ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.STAR_DOT);
        }
        else {
            ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.INVOKE_METHOD_COMMAND);
        }
        if (args != null && args.isHaveMatchVarDef()) {
            popVarScope();
        }

        if (expression != null && objExpr != null && expression.getParentExpression() == null) {
            ga.goTo(expression.getEndLabel());
            ga.visitLabel(expression.getNullLabel());
            ga.visitLabel(expression.getEndLabel());
        }
    }

    public void visitArgumentListExpression(ArgumentListExpression args) {
        List<Expression> exprs = args.getExpressions();
        for (Expression expr : exprs) {
            visitExpression(expr);
        }
    }

    public void makeArgumentListExpressionArray(ArgumentListExpression args) {
        List<Expression> exprs = args.getExpressions();
        ga.push(exprs.size());
        ga.newArray(TypeUtil.OBJECT_TYPE);
        for (int i = 0; i < exprs.size(); i++) {
        	ga.dup();
        	ga.push(i);
            Expression expr = exprs.get(i);
            visitExpression(expr);
            ga.box(TypeUtil.OBJECT_TYPE);
            ga.arrayStore(TypeUtil.getType(ClassNode.OBJECT));
        }
    }
    
    public void makeIndexArgumentListExpressionArray(ArgumentListExpression args) {
        List<Expression> exprs = args.getExpressions();
        ga.push(exprs.size());
        ga.newArray(TypeUtil.OBJECT_TYPE);
        for (int i = 0; i < exprs.size(); i++) {
        	ga.dup();
        	ga.push(i);
            Expression expr = exprs.get(i);
//            visitIndexArgExpression(expr);
            visitExpression(expr);
            ga.box(TypeUtil.OBJECT_TYPE);
            ga.arrayStore(TypeUtil.getType(ClassNode.OBJECT));
        }
    }
    
    public void visitIndexArgExpression(Expression expression) {
    	if (expression instanceof OperationExpression) {
    		BlockNode block = new BlockNode();
    		block.addStatement(new ExpressionStatement(expression));
    		ClosureExpression closureExpr = new ClosureExpression(new ArrayList<ParameterNode>(), block);
    		visitClosureExpression(closureExpr);
    	}
    	else {
            visitExpression(expression);
    	}
    }


    public void visitInitAssignCommand(VariableExpression leftExpr, Expression rightExpr) {
        ClassNode leftType = leftExpr.getType();
        ClassNode rightType = rightExpr.getType();
        String varName = leftExpr.getVariableName();
        Type localType = TypeUtil.getType(rightType);
        stackFrame.getVariableWriter().declareVariable(stackFrame.getSymbolTable(), this, varName, rightExpr);
        pushNull();
    }

    public void visitPlusCommand(Expression leftExpr, Expression rightExpr) {
    	pushShell();
        visitExpression(leftExpr);
        visitExpression(rightExpr);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.PLUS_COMMAND);
    }

    public void visitMinusCommand(Expression leftExpr, Expression rightExpr) {
    	pushShell();
        visitExpression(leftExpr);
        visitExpression(rightExpr);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.MINUS_COMMAND);
    }

    public void visitMultiCommand(Expression leftExpr, Expression rightExpr) {
    	pushShell();
        visitExpression(leftExpr);
        visitExpression(rightExpr);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.MULTI_COMMAND);
    }

    public void visitDivCommand(Expression leftExpr, Expression rightExpr) throws ClassGeneratorException {
    	pushShell();
        visitExpression(leftExpr);
        visitExpression(rightExpr);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.DIV_COMMAND);
    }
    
    public void visitModCommand(Expression leftExpr, Expression rightExpr) throws ClassGeneratorException {
    	pushShell();
        visitExpression(leftExpr);
        visitExpression(rightExpr);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.MOD_COMMAND);
    }

    public void visitPowCommand(Expression leftExpr, Expression rightExpr) throws ClassGeneratorException {
        pushShell();
        visitExpression(leftExpr);
        visitExpression(rightExpr);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.POW_COMMAND);
    }


    public void visitGreateThenCommand(Expression leftExpr, Expression rightExpr) {
    	pushShell();
        visitExpression(leftExpr);
        visitExpression(rightExpr);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.GT_COMMAND);
        ga.box(TypeUtil.getType(boolean.class));
    }

    public void visitLessThenCommand(Expression leftExpr, Expression rightExpr) {
    	pushShell();
        visitExpression(leftExpr);
        visitExpression(rightExpr);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.LT_COMMAND);
        ga.box(TypeUtil.getType(boolean.class));
    }

    public void visitGreateEqualCommand(Expression leftExpr, Expression rightExpr) {
    	pushShell();
        visitExpression(leftExpr);
        visitExpression(rightExpr);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.GE_COMMAND);
        ga.box(TypeUtil.getType(boolean.class));
    }

    public void visitLessEqualCommand(Expression leftExpr, Expression rightExpr) {
    	pushShell();
        visitExpression(leftExpr);
        visitExpression(rightExpr);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.LE_COMMAND);
        ga.box(TypeUtil.getType(boolean.class));
    }

    public void visitEqualCommand(Expression leftExpr, Expression rightExpr) {
    	pushShell();
        visitExpression(leftExpr);
        visitExpression(rightExpr);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.EQ_COMMAND);
        ga.box(TypeUtil.getType(boolean.class));
    }

    public void visitNotEqualCommand(Expression leftExpr, Expression rightExpr) {
        pushShell();
        visitExpression(leftExpr);
        visitExpression(rightExpr);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.NE_COMMAND);
        ga.box(TypeUtil.getType(boolean.class));
    }

    public void visitAndCommand(Expression leftExpr, Expression rightExpr) {
        pushShell();
        visitExpression(leftExpr);
        visitExpression(rightExpr);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.AND_COMMAND);
        ga.box(TypeUtil.getType(boolean.class));
    }

    public void visitOrCommand(Expression leftExpr, Expression rightExpr) {
        pushShell();
        visitExpression(leftExpr);
        visitExpression(rightExpr);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.OR_COMMAND);
        ga.box(TypeUtil.getType(boolean.class));
    }

    public void visitBitAndCommand(Expression leftExpr, Expression rightExpr) {
        pushShell();
        visitExpression(leftExpr);
        visitExpression(rightExpr);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.BIT_AND_COMMAND);
    }

    public void visitBitOrCommand(Expression leftExpr, Expression rightExpr) {
        pushShell();
        visitExpression(leftExpr);
        visitExpression(rightExpr);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.BIT_OR_COMMAND);
    }

    public void visitXorCommand(Expression leftExpr, Expression rightExpr) {
        pushShell();
        visitExpression(leftExpr);
        visitExpression(rightExpr);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.XOR_COMMAND);
        //ga.box(TypeUtil.getType(boolean.class));
    }

    public void visitInstanceOfCoammand(Expression leftExpr, Expression rightExpr) {
        pushShell();
        visitExpression(leftExpr);
        visitExpression(rightExpr);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.INSTANCEOF_COMMAND);
        ga.box(TypeUtil.getType(boolean.class));
    }


    public void visitIsCoammand(Expression leftExpr, Expression rightExpr) {
        pushShell();
        visitExpression(leftExpr);
        visitExpression(rightExpr);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.IS_MATCH_COMMAND);
        ga.box(TypeUtil.getType(boolean.class));
    }

    public void visitMatchRegexCommand(Expression leftExpr, Expression rightExpr) {
        pushShell();
        visitExpression(leftExpr);
        visitExpression(rightExpr);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.MATCH_REGEX_COMMAND);
        ga.box(TypeUtil.getType(boolean.class));
    }

    public void visitNotMatchRegexCommand(Expression leftExpr, Expression rightExpr) {
        pushShell();
        visitExpression(leftExpr);
        visitExpression(rightExpr);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.NOT_MATCH_REGEX_COMMAND);
        ga.box(TypeUtil.getType(boolean.class));
    }


    public void visitOrPattern(Expression leftExpr, Expression rightExpr) {
        ga.newInstance(TypeUtil.P_OR_PATTERN_TYPE);
        ga.dup();
        visitExpression(leftExpr);
        visitExpression(rightExpr);
        ga.invokeConstructor(TypeUtil.P_OR_PATTERN_TYPE,
                MethodUtil.getConstructorMethod(
                        new ClassNode[]{
                                ClassNode.OBJECT, ClassNode.OBJECT
                        }));
    }

    public void visitAndPattern(Expression leftExpr, Expression rightExpr) {
        ga.newInstance(TypeUtil.P_AND_PATTERN_TYPE);
        ga.dup();
        visitExpression(leftExpr);
        visitExpression(rightExpr);
        ga.invokeConstructor(TypeUtil.P_AND_PATTERN_TYPE,
                MethodUtil.getConstructorMethod(
                        new ClassNode[]{
                                ClassNode.OBJECT, ClassNode.OBJECT
                        }));
    }


    public void visitNotPattern(Expression objExpr) {
        ga.newInstance(TypeUtil.P_NOT_PATTERN_TYPE);
        ga.dup();
        visitExpression(objExpr);
        ga.invokeConstructor(TypeUtil.P_NOT_PATTERN_TYPE,
                MethodUtil.getConstructorMethod(
                        new ClassNode[]{
                                ClassNode.OBJECT
                        }));
    }

    public void visitBitShiftLeft(Expression leftExpr, Expression rightExpr) {
        pushShell();
        visitExpression(leftExpr);
        visitExpression(rightExpr);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.BIT_SL_COMMAND);
    }

    public void visitBitShiftRight(Expression leftExpr, Expression rightExpr) {
        pushShell();
        visitExpression(leftExpr);
        visitExpression(rightExpr);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.BIT_SR_COMMAND);
    }

    public void visitShitLeft(Expression leftExpr, Expression rightExpr) {
        pushShell();
        visitExpression(leftExpr);
        visitExpression(rightExpr);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.SL_COMMAND);
    }

    public void visitShitRight(Expression leftExpr, Expression rightExpr) {
        pushShell();
        visitExpression(leftExpr);
        visitExpression(rightExpr);
        ga.invokeVirtual(TypeUtil.P_SHELL_TYPE, MethodUtil.SR_COMMAND);
    }

    public GeneratorAdapter getGA() {
        return ga;
    }
}
