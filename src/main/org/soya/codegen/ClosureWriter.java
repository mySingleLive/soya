package org.soya.codegen;

import org.objectweb.asm.Opcodes;
import org.soya.ast.*;
import org.soya.ast.expr.*;
import org.soya.ast.stmt.ConstructorCallStatement;
import org.soya.codegen.frame.SymbolTable;
import org.soya.codegen.frame.VariableWriter;
import org.soya.util.NameUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Jun Gong
 */
public class ClosureWriter  {

    protected final ClassGenerator classGenerator;
    protected final ClassNode thisClass;
    protected final CompilationUnit unit;
    protected final SymbolTable symbolTable;
    protected final MethodNode methodNode;
    protected final VariableWriter externalVariableWriter;
    protected final int index;
    protected final Map<ClosureExpression, ClassNode> closureClassCache = new HashMap<ClosureExpression, ClassNode>();

    public ClosureWriter(ClassGenerator classGenerator, MethodNode methodNode,
                         VariableWriter externalVariableWriter) {
        this(classGenerator, classGenerator.getUnit(), classGenerator.getThisClass(),
                methodNode, externalVariableWriter.getSymbolTable(), externalVariableWriter);
    }

    public ClosureWriter(ClassGenerator classGenerator, CompilationUnit unit, ClassNode thisClass,
                         MethodNode methodNode, SymbolTable symbolTable, VariableWriter externalVariableWriter) {
        this.classGenerator = classGenerator;
    	this.unit = unit;
    	this.thisClass = thisClass;
    	this.methodNode = methodNode;
    	this.index = classGenerator.getClosureCounter();
        classGenerator.setClosureCounter(index + 1);
    	this.symbolTable = symbolTable;
        this.externalVariableWriter = externalVariableWriter;
	}

    public ClassNode getClosureClass(ClosureExpression expression) {
    	ClassNode closureClass = closureClassCache.get(expression);
    	if (closureClass == null) {
    		closureClass = createClosureClass(expression);
    		closureClassCache.put(expression, closureClass);
            thisClass.addInnerClass((InnerClassNode) closureClass);
            classGenerator.addInnerClass(closureClass);
    	}
    	return closureClass;
    }
    
    public ClassNode createClosureClass(ClosureExpression expression) {
    	ClassNode outermostClass = thisClass.getOutermostClassNode();
    	String name = NameUtil.getClosureClassName(outermostClass, thisClass, methodNode, index);
    	InnerClassNode result = new InnerClassNode(thisClass, name, Opcodes.ACC_PUBLIC, ClassNode.PClosure, symbolTable, externalVariableWriter);
        result.setClosureClass(true);

        List<ParameterNode> paramList = expression.getParameters();
        
/*
        if (paramList.size() > 1) {
        
        	ParameterNode[] callParams = new ParameterNode[paramList.size()];
        	for (int i = 0; i < paramList.size(); i++) {
        		callParams[i] = paramList.get(i);
        	}

        	BlockNode callBlock = new BlockNode();
        	ArgumentListExpression callDoCallArgs = new ArgumentListExpression();
        	callDoCallArgs.addArgument(new VariableExpression("object"));
        	callDoCallArgs.addArgument(new ConstantExpression(null));

        	for (int i = 0; i < callParams.length; i++) {
        		ParameterNode callParam = callParams[i];
        		callDoCallArgs.addArgument(new VariableExpression(callParam.getName()));
        	}
        	callBlock.addStatement(
        			new ExpressionStatement(
        					new MethodCallExpression(
        							VariableExpression.INNER_THIS,
        							new ConstantExpression("doCall"),
        							callDoCallArgs)));

        	MethodNode callMethod = new MethodNode(
        			Opcodes.ACC_PUBLIC, 
        			"call",
        			ClassNode.OBJECT, 
        			callParams,
        			callBlock, false);
        	result.addMethod(callMethod);
        }
*/

/*
        ConstructorNode constructorNode1 = new ConstructorNode(Opcodes.ACC_PUBLIC,
                new ParameterNode[] {new ParameterNode(ClassNode.OBJECT, "o")},
                new BlockNode(new ConstructorCallStatement(true,
                        new ArgumentListExpression(new VariableExpression("o")))));
        result.addConstructor(constructorNode1);
*/

        ConstructorNode constructorNode2 = new ConstructorNode(Opcodes.ACC_PUBLIC,
                new ParameterNode[] {
                        new ParameterNode(ClassNode.OBJECT, "o"),
                        new ParameterNode(ClassNode.PVarScope, "externalVariables"),
                        new ParameterNode(ClassNode.BOOLEAN, "callOnAssign")
                },
                new BlockNode(new ConstructorCallStatement(true,
                        new ArgumentListExpression(
                                new VariableExpression("o"),
                                new VariableExpression("externalVariables"),
                                new VariableExpression("callOnAssign")))));
        result.addConstructor(constructorNode2);


        ParameterNode[] params = new ParameterNode[Math.max(paramList.size() + 1, 2)];

        params[0] = new ParameterNode(ClassNode.OBJECT, "outerObject");
        params[1] = new ParameterNode(ClassNode.OBJECT, "it");

        for (int i = 0; i < paramList.size(); i++) {
            params[i + 1] = paramList.get(i);
        }

        MethodNode doCallMethod = new MethodNode(Opcodes.ACC_PUBLIC, "doCall", ClassNode.OBJECT,
                params, expression.getBody());
        result.addMethod(doCallMethod);
    	return result;
    }

    public ParameterNode[] getCallMethodParameters(List<ParameterNode> callParams) {
        ParameterNode[] results = null;
        if (callParams.isEmpty()) {
            results = new ParameterNode[] {new ParameterNode(ClassNode.OBJECT, "it")};
        }
        else {
            results = (ParameterNode[]) callParams.toArray();
        }
        return results;
    }
}
