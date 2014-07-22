package org.soya.codegen;

import org.soya.ast.ClassNode;
import org.soya.ast.MethodNode;
import org.soya.ast.ParameterNode;

/**
 * @author: Jun Gong
 */
public class BytecodeUtil {

    public static String getClassInternalName(ClassNode t) {
        return t.getName().replace('.', '/');
    }

    public static String getClassInternalName(Class t) {
        return org.objectweb.asm.Type.getInternalName(t);
    }

    public static String getMethodDescriptor(ClassNode returnType, ParameterNode[] parameters) {
        StringBuffer buffer = new StringBuffer("(");
        for (int i = 0; i < parameters.length; i++) {
            buffer.append(getTypeDescription(parameters[i].getType()));
        }
        buffer.append(")");
        buffer.append(getTypeDescription(returnType));
        return buffer.toString();
    }

    public static String getMethodDescriptor(Class returnType, Class[] paramTypes) {
        // lets avoid class loading
        StringBuffer buffer = new StringBuffer("(");
        for (int i = 0; i < paramTypes.length; i++) {
            buffer.append(getTypeDescription(paramTypes[i]));
        }
        buffer.append(")");
        buffer.append(getTypeDescription(returnType));
        return buffer.toString();
    }

    public static String getTypeDescription(Class c) {
        return org.objectweb.asm.Type.getDescriptor(c);
    }

    public static String getTypeDescription(ClassNode c) {
        return getTypeDescription(c, true);
    }

    private static String getTypeDescription(ClassNode c, boolean end) {
        StringBuffer buf = new StringBuffer();
        ClassNode d = c;
        if (d.isArray()) {
            buf.append('[');
        }
        buf.append('L');
        String name = d.getName();
        int len = name.length();
        for (int i = 0; i < len; ++i) {
            char car = name.charAt(i);
            buf.append(car == '.' ? '/' : car);
        }
        if (end) buf.append(';');
        return buf.toString();
    }
    
    public static String getMethodName(MethodNode methodNode) {
        ParameterNode[] param = methodNode.getParameters();
        ClassNode returnType = methodNode.getReturnType();
        if (returnType == null) {
            returnType = ClassNode.OBJECT;
        }

        StringBuffer ret = new StringBuffer();
        ret.append(returnType.getName());
        ret.append(" ");
        ret.append(methodNode.getName());
        ret.append(" (");
        for (int i = 0; i < param.length; i++) {
            ClassNode pType = param[i].getType();
            String clsName = pType.getName();
            Class cls = pType.getJclass();
            if (cls != null) {
                clsName = cls.getCanonicalName();
                ret.append(clsName);
            }
            else {
                if (clsName.charAt(0) == '[') {
                    clsName = clsName.substring(1, clsName.length() - 1);
                }
                ret.append(clsName);
                if (pType.isArray()) {
                    ret.append("[]");
                }
            }
            if (i < param.length - 1) {
                ret.append(", ");
            }
        }
        ret.append(")");
        return ret.toString();
    }

    public static String getStaticInitializerName() {
        return "void <clinit> ()";
    }
   
    public static String getConstructorName() {
    	return "void <init> ()";
    }
    
    public static String getConstructorName(String param) {
    	return "void <init> (" + param + ")";
    }

    public static String getConstructorName(String[] params) {
    	StringBuffer buffer = new StringBuffer();
    	buffer.append("void <init> (");
    	for (int i = 0; i < params.length; i++) {
    		String param = params[i];
        	buffer.append(param);
            if (i < params.length - 1) {
            	buffer.append(", ");
            }
    	}
    	buffer.append(")");
    	return buffer.toString();
    }
    
    public static String getConstructorName(Class cls) {
    	return "void <init> (" + getParameterTypeName(cls) + ")";
    }
    
    public static String getParameterTypeName(Class cls) {
    	if (cls.isArray()) {
    		return cls.getCanonicalName();
    	}
    	return cls.getName();
    }
    
    public static String getParameterTypeName(ClassNode cls) {
    	Class clazz = cls.getJclass();
    	if (clazz != null) {
    		return getParameterTypeName(clazz);
    	}
    	return cls.getName();
    }


    public static String getConstructorName(Class[] clses) {
    	String[] paramTypeNames = new String[clses.length];
    	for (int i = 0; i < clses.length; i++) {
    		paramTypeNames[i] = getParameterTypeName(clses[i]);
    	}
    	return getConstructorName(paramTypeNames);
    }
    
    public static String getConstructorName(ClassNode[] paramTypes) {
    	String[] paramTypeNames = new String[paramTypes.length];
    	for (int i = 0; i < paramTypes.length; i++) {
    		paramTypeNames[i] = getParameterTypeName(paramTypes[i]);
    	}
    	return getConstructorName(paramTypeNames);
    }
    
    public static String getConstructorName(ClassNode paramType) {
    	return getConstructorName(paramType.getName());
    }

    public static String getConstructorName(ParameterNode[] params) {
        String[] paramTypeNames = new String[params.length];
        for (int i = 0; i < params.length; i++) {
            ClassNode type = params[i].getType();
            paramTypeNames[i] = getParameterTypeName(type);
        }
        return getConstructorName(paramTypeNames);
    }

}
