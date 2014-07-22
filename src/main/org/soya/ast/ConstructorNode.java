package org.soya.ast;

import java.lang.reflect.Constructor;

/**
 * @author: Jun Gong
 */
public class ConstructorNode extends MethodNode {

    public ConstructorNode(int modifier, ParameterNode[] parameters, BlockNode body) {
        super(modifier, "<init>", ClassNode.VOID, parameters, body);
    }

    public ConstructorNode(Constructor constructor) {
        super(constructor.getModifiers(), "<init>", ClassNode.VOID, toParameters(constructor),
                new BlockNode());
        setVarArgs(constructor.isVarArgs());
    }

    public static ParameterNode[] toParameters(Constructor constructor) {
        Class[] paramTypes = constructor.getParameterTypes();
        ParameterNode[] rets = new ParameterNode[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            rets[i] = new ParameterNode(ClassNode.make(paramTypes[i]), "arg_" + i);
        }
        return rets;
    }


}
