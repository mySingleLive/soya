package org.soya.tools;

import org.soya.ast.MethodNode;
import org.soya.ast.expr.VariableExpression;

import java.util.Map;

/**
 * @author: Jun Gong
 */
public class SymbolTable {

    private Map<String, VariableExpression> variables;
    private Map<String, MethodNode> methods;

    public void registVariable(String name, VariableExpression variableExresspion) {
        if (variables.containsKey(name)) {

        }
    }
}
