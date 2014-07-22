package org.soya.util;

import org.soya.ast.ClassNode;
import org.soya.ast.MethodNode;


/**
 * @author: Jun Gong
 */
public class NameUtil {

    public static boolean isGetterName(String name) {
        if (name.length() < 4) {
            return false;
        }
        return name.substring(0, 3).equals("get");
    }

    public static boolean isSetterName(String name) {
        if (name.length() < 4) {
            return false;
        }
        return name.substring(0, 3).equals("set");
    }

    public static boolean isBooleanGetterrName(String name) {
        if (name.length() < 3) {
            return false;
        }
        return name.substring(0, 2).equals("is");
    }


    public static String toGetterName(String name) {
        return tohPrefixialCamelName(name, "get");
    }

    public static String toBooleanGetterName(String name) {
        return tohPrefixialCamelName(name, "is");
    }

    public static String toSetterName(String name) {
        return tohPrefixialCamelName(name, "set");
    }

    public static String getNameFromGetter(String getterName) {
        if (isGetterName(getterName)) {
            String name = getterName.substring(3, getterName.length());
            return Character.toLowerCase(name.charAt(0)) + name.substring(1, name.length());
        }
        return getterName;
    }

    public static String getNameFromSetter(String setterName) {
        if (isSetterName(setterName)) {
            String name = setterName.substring(3, setterName.length());
            return Character.toLowerCase(name.charAt(0)) + name.substring(1, name.length());
        }
        return setterName;
    }

    public static String getNameFromBooleanGetter(String booleanGetterName) {
        if (isBooleanGetterrName(booleanGetterName)) {
            String name = booleanGetterName.substring(2, booleanGetterName.length());
            return Character.toLowerCase(name.charAt(0)) + name.substring(1, name.length());
        }
        return booleanGetterName;
    }


    public static String tohPrefixialCamelName(String name, String prefix) {
        if (name == null || name.length() == 0) {
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        String camelName = toCamelName(name);
        return buffer.append(prefix).append(camelName).toString();
    }

    public static String toCamelName(String name) {
        if (name == null || name.length() == 0) {
            return "";
        }
        char firstChar = name.charAt(0);
        if (!Character.isLetter(firstChar)) {
            return name;
        }
        char upperChar = Character.toUpperCase(firstChar);
        StringBuffer buffer = new StringBuffer();
        return buffer.append(upperChar).append(name.substring(1, name.length())).toString();
    }

    public static String getVarScopeFieldName(String varName) {
        return "__var_" + varName;
    }

    private static String getInnerClassName(ClassNode owner, ClassNode enclosingClass, MethodNode enclosingMethod, String postfix) {
        String ownerShortName = owner.getShortName();
        String classShortName = enclosingClass.getShortName();
        if (classShortName.equals(ownerShortName)) {
            classShortName = "";
        }
        else {
            classShortName += "_";
        }
        // remove $
        int dp = classShortName.lastIndexOf("$");
        if (dp >= 0) {
            classShortName = classShortName.substring(dp + 1);
        }
        // remove leading _
        if (classShortName.startsWith("_")) {
            classShortName = classShortName.substring(1);
        }
        String methodName = "";
        if (enclosingMethod != null) {
            methodName = enclosingMethod.getName() + "_";
/*
            if (enclosingClass.isDerivedFrom(ClassHelper.CLOSURE_TYPE)) {
                methodName = "";
            }
            methodName = methodName.replace('<', '_');
            methodName = methodName.replace('>', '_');
            methodName = methodName.replaceAll(" ", "_");
                        */
        }
        String ret = enclosingClass.getName() + "$" + "_" + methodName + postfix;
        return ret;
    }
    
    public static String getClosureClassName(ClassNode owner, ClassNode enclosingClass, MethodNode enclosingMethod, int index) {
        return getInnerClassName(owner, enclosingClass, enclosingMethod, "closure_" + index);
    }

    public static String getVarScopeClassName(ClassNode owner, ClassNode enclosingClass, MethodNode enclosingMethod, int index) {
        return getInnerClassName(owner, enclosingClass, enclosingMethod, "varscope_" + index);
    }

}
