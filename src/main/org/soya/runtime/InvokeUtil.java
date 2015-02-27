package org.soya.runtime;

import soya.env.SystemEnvironment;
import org.soya.util.NameUtil;
import soya.lang.*;
import soya.lang.Float;

import java.io.File;
import java.lang.Long;
import java.lang.reflect.*;
import java.net.URL;
import java.util.*;

/**
 * @author: Jun Gong
 */
public class InvokeUtil {

    public static boolean isNull(Object obj) {
        return obj == null || obj == Null.NULL;
    }

    public static boolean isNumber(Object obj) {
        return isInt(obj) || isFloat(obj) || isDouble(obj);
    }

    public static boolean isInt(Object obj) {
        if (obj instanceof Integer || obj instanceof Int) {
            return true;
        }
        return false;
    }

    public static boolean isFloat(Object obj) {
        if (obj instanceof Float || obj instanceof java.lang.Float) {
            return true;
        }
        return false;
    }

    public static boolean isDouble(Object obj) {
        if (obj instanceof Double || obj instanceof java.lang.Double) {
            return true;
        }
        return false;
    }


    public static int intValue(Integer o) {
        return ((Integer) o).intValue();
    }

    public static int intValue(Int o) {
        return ((Int) o).getValue();
    }

    public static Object invokeStaticMethod(Class invokerClass, Class clazz, String methodName, Object[] argObjs) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = findJavaMethod(invokerClass, clazz.getDeclaredMethods(), methodName, argObjs, true);
        if (method != null) {
            Class[] argTypes = method.getParameterTypes();
            method.setAccessible(true);
            Object ret = method.invoke(clazz, makeUnboxedArray(argTypes, argObjs));
            return wrapObject(ret);
        }
        throw new NoSuchMethodException("static " + clazz.getSimpleName() + "." + methodName);
    }

    public static Object invokeStaticDefaultInvokerMethod(Class invokerClass, String methodName, Object[] argObjs) throws Throwable {
        return invokeStaticMethod(invokerClass, invokerClass, methodName, argObjs);
    }


    public static Object invokeDefaultInvokerMethod(Class invokerClass, Object obj, String methodName, Object[] argObjs) throws Throwable {
        Object[] rets = findObjectMethodAndArgArray(obj, methodName, argObjs);
        Method method = (Method) rets[0];
        Object[] argArray = (Object[]) rets[1];
        if (method == null) {
            return invokeSystemEvnMethod(invokerClass, methodName, argObjs);
        }
        Class[] methodTypes = method.getParameterTypes();
        if (argArray == null) {
            argArray = makeUnboxedArray(methodTypes, argObjs);
        }
        try {
            Object ret = method.invoke(obj, argArray);
            return wrapObject(ret);
        } catch (Throwable e) {
            if (e instanceof NoSuchMethodException) {
                return invokeSystemEvnMethod(invokerClass, methodName, argObjs);
            }
            else if (e instanceof InvocationTargetException) {
                Throwable e2 = ((InvocationTargetException) e).getTargetException();
                if (e2 instanceof NoSuchMethodException) {
                    return invokeSystemEvnMethod(invokerClass, methodName, argObjs);
                }
            }
            throw e;
        }
//        try {
//            if (obj instanceof SoyaObject) {
//                return invokePObjectMethod((SoyaObject) obj, methodName, argObjs);
//            }
//            else {
//                return invokeJavaObjectMethod(obj, methodName, argObjs);
//            }
//        } catch (NoSuchMethodException e1) {
//            return invokeSystemEvnMethod(invokerClass, methodName, argObjs);
//        }
    }


    public static Object invokePoJoMethod(Object obj, String methodName, Object[] argObjs) throws Throwable {
        if (obj instanceof SoyaObject) {
            return invokePObjectMethod((SoyaObject) obj, methodName, argObjs);
        }
        else {
            return invokeJavaObjectMethod(obj, methodName, argObjs);
        }
    }

    public static Object invokePObjectMethod(SoyaObject obj, String methodName, Object[] argObjs) throws Throwable {
        return obj.invokeMethod(methodName, argObjs);
    }

    public static Object invokeSystemEvnMethod(Class clazz, String methodName, Object[] argObjs) throws Throwable {
        Method method = findJavaMethod(clazz, clazz.getDeclaredMethods(), methodName, argObjs, true);
        if (method != null) {
            Class[] argTypes = method.getParameterTypes();
            method.setAccessible(true);
            return method.invoke(clazz, makeUnboxedArray(argTypes, argObjs));
        }
        SystemEnvironment systemEnvironment = SystemEnvironment.getSystemEnvironmentInstance();
        method = findJavaMethod(clazz, SystemEnvironment.class.getMethods(), methodName, argObjs, false);
        if (method != null) {
            Class[] argTypes = method.getParameterTypes();
            return method.invoke(systemEnvironment, makeUnboxedArray(argTypes, argObjs));
        }
        throw new NoSuchMethodException("static " + clazz.getSimpleName() + "." + methodName);
    }

    public static void setJavaObjectPropertyValue(Object obj, String propertyName, Object[] args) throws NoSuchPropertyException, InvocationTargetException, IllegalAccessException {
        Class clazz = obj.getClass();
        String setterName = NameUtil.toSetterName(propertyName);
        Method method = InvokeUtil.getJavaObjectMethod(obj, setterName, args);
        if (method != null) {
            Class[] paramTypes = method.getParameterTypes();
            method.invoke(obj, InvokeUtil.makeUnboxedArray(paramTypes, args));
        }
        try {
            clazz.getField(propertyName).set(obj, args[0]);
        } catch (NoSuchFieldException e) {
            throw new NoSuchPropertyException(MetaClassUtil.createMetaClass(clazz), propertyName);
        }
    }

    public static Object getJavaObjectPropertyValue(Class invokerClz, Object obj, String propertyName) throws Exception {
        Class clazz = obj.getClass();
        Method method = null;
        try {
            method = clazz.getMethod(propertyName, new Class[] {});
        } catch (NoSuchMethodException e) {
        }
        if (method == null) {
            try {
                method = clazz.getMethod(NameUtil.toGetterName(propertyName), new Class[] {});
            } catch (NoSuchMethodException e) {
            }
        }
        if (method != null) {
            if (method.getDeclaringClass().isAssignableFrom(invokerClz)) {
                method.setAccessible(true);
            }
            Object ret = method.invoke(obj, new Object[] {});
            return wrapObject(ret);
        }
        try {
            return clazz.getField(propertyName).get(obj);
        } catch (NoSuchFieldException ef) {
            if (clazz.isArray()) {
                if (propertyName.equals("length")) {
                    return ((Object[]) obj).length;
                }
            }
            throw new NoSuchMethodException(obj.getClass() + "." + propertyName + "() or " +
                    obj.getClass() + "." + NameUtil.toGetterName(propertyName) + "()");
        }
    }
    
    public static Class[] getObjectClasses(Object[] objs) {
        if (objs == null) {
            return new Class[0];
        }
    	Class[] types = new Class[objs.length];
        for (int i = 0; i < objs.length; i++) {
        	types[i] = objs[i].getClass();
        }
    	return types;
    }

    private static class SortedMethod {
        public int number;
        public Method method;
        public SoyaMethod pmethod;

        public SortedMethod(int number, Method method) {
            this.number = number;
            this.method = method;
        }
    }

    private static SortedMethod sortedMethod(Method method, Class[] argTypes) {
        Class[] pTypes = method.getParameterTypes();
        int number = 0;
        for (int i = 0; i < pTypes.length; i++) {
            Class pType = pTypes[i];
            Class aType = argTypes[i];
            if (!pType.isAssignableFrom(aType)) {
                if (SoyaString.class.isAssignableFrom(aType) ||
                        Int.class.isAssignableFrom(aType) ||
                        Float.class.isAssignableFrom(aType) ||
                        Double.class.isAssignableFrom(aType) ||
                        Null.class.isAssignableFrom(aType)) {
                    continue;
                }

                return null;
            }
            int n = 0;
            aType = pType;
            while (aType != null) {
                aType = aType.getSuperclass();
                n++;
            }
            number += n;
        }
        return new SortedMethod(number, method);
    }

    public static SoyaMethod findPMethod(List<SoyaMethod> methodList, Class[] paramterTypes, boolean isStatic) {
        SJMethod method = null;
        SJMethod varArgsMethod = null;
        SortedMethod sortedMethod = null;
        for (int i = 0; i < methodList.size(); i++) {
            method = (SJMethod)methodList.get(i);
            Method m = method.getMethod();
            Class[] pTypes = m.getParameterTypes();

            if (InvokeUtil.matchPoJoMethodParameterTypes(pTypes, paramterTypes)) {
                if (m.isVarArgs()) {
                    varArgsMethod = method;
                    continue;
                }
                SortedMethod sm = sortedMethod(m, paramterTypes);
                if (sm == null) {
                    continue;
                }
                if (sortedMethod == null || sortedMethod.number > sm.number) {
                    sortedMethod = sm;
                    sortedMethod.pmethod = method;
                    continue;
                }
                break;
            }
            method = null;
        }
        if (sortedMethod != null) {
            if (sortedMethod.pmethod == null) {
                return new SJMethod(sortedMethod.method);
            }
            return sortedMethod.pmethod;
        }
        if (method == null) {
            return varArgsMethod;
        }
        return method;
    }

    public static Constructor findJavaConstructor(Class invokerClass, Constructor[] constructors, Object[] argObjs) {
        Constructor constructor = null;
        Class[] argTypes = getObjectClasses(argObjs);
        Class[] constructorParamTypes = null;
        Constructor varArgsConstructor = null;
        for (int i = 0; i < constructors.length; i++) {
            constructor = constructors[i];
            constructorParamTypes = constructor.getParameterTypes();
            if (matchPoJoMethodParameterTypes(constructorParamTypes, argTypes)) {
                break;
            }
            if (constructor.isVarArgs()) {
                varArgsConstructor = constructor;
            }
            constructor = null;
        }

        if (constructor == null) {
            constructor = varArgsConstructor;
        }
        return constructor;
    }

    public static Method findJavaMethod(Class invokerClass, Method[] methods, String methodName, Object[] argObjs, boolean isStatic) {
        Method method = null;
        Class[] argTypes = getObjectClasses(argObjs);
        Class[] methodTypes = null;
        SortedMethod sortedMethod = null;
        Method varMethod = null;
        for (int i = 0; i < methods.length; i++) {
            method = methods[i];
            if (method.getName().equals(methodName)) {
                methodTypes = method.getParameterTypes();
                if (matchPoJoMethodParameterTypes(methodTypes, argTypes)) {
                    if ((method.getModifiers() & Modifier.PUBLIC) != 0) {
                        break;
                    }
                    if (method.getDeclaringClass().isAssignableFrom(invokerClass)) {
                        break;
                    }
                }
                if (method.isVarArgs()) {
                    varMethod = method;
                }
            }
            if (i == methods.length - 1) {
                method = null;
            }
        }
        if (sortedMethod != null) {
            return sortedMethod.method;
        }
        if (method == null) {
            return varMethod;
        }
        return method;
    }

    public static Method getJavaObjectMethod(Object obj, String methodName, Object[] argObjs) {
        Class cls = obj.getClass();
        Method methods[] = cls.getMethods();
        return findJavaMethod(obj.getClass(), methods, methodName, argObjs, false);
    }

    public static Object[] findObjectMethodAndArgArray(Object obj, String methodName, Object[] argObjs) throws InvocationTargetException, IllegalAccessException {
        Method method = getJavaObjectMethod(obj, methodName, argObjs);
        Object[] argArray = null;
        if (method == null) {
            SoyaList argsList = new SoyaList(argObjs);
            method =  getJavaObjectMethod(obj, "invokeUndefinedMethod", new Object[] {methodName, argsList});
            if (method != null) {
                argArray = new Object[] {methodName, new SoyaList(argObjs)};
            }
        }
        if (method == null) {
            if (NameUtil.isGetterName(methodName)) {
                method =  getJavaObjectMethod(obj, "getUndefinedProperty", new Object[0]);
            }
        }
        if (method == null) {
            if (NameUtil.isSetterName(methodName)) {
                if (argObjs != null && argObjs.length > 0) {
                    method =  getJavaObjectMethod(obj, "setUndefinedProperty", new Object[] {argObjs[0]});
                }
            }
        }
        Class[] methodTypes = method.getParameterTypes();
        if (argArray == null) {
            if (method.isVarArgs()) {
                argArray = makeVarArgUnboxedArray(methodTypes, argObjs);
            }
            else {
                argArray = makeUnboxedArray(methodTypes, argObjs);
            }
        }
        return new Object[] {method, argArray};
    }

    public static Object invokeJavaObjectMethod(Object obj, String methodName, Object[] argObjs) throws Throwable {
        Object[] rets = findObjectMethodAndArgArray(obj, methodName, argObjs);
        Method method = (Method) rets[0];
        Object[] argArray = (Object[]) rets[1];
        if (method == null) {
        	throw new NoSuchMethodException(obj.getClass() + "." + methodName);
        }
        try {
            Object ret = method.invoke(obj, argArray);
            return wrapObject(ret);
        } catch (Throwable t) {
            if (t instanceof InvocationTargetException) {
                t = ((InvocationTargetException) t).getTargetException();
            }
            if (t instanceof NoSuchMethodException) {
                if (NameUtil.isGetterName(methodName) || NameUtil.isBooleanGetterrName(methodName)) {
                    try {
                        method = obj.getClass().getMethod("getUndefinedProperty", new Class[] {String.class});
                        String getterName;
                        if (NameUtil.isGetterName(methodName)) {
                            getterName = NameUtil.getNameFromGetter(methodName);
                        }
                        else {
                            getterName = NameUtil.getNameFromBooleanGetter(methodName);
                        }
                        return method.invoke(obj, new Object[] {getterName});
                    } catch (Throwable t2) {
                        throw t2;
                    }
                }
                else if (NameUtil.isSetterName(methodName)) {
                    if (NameUtil.isSetterName(methodName)) {
                        if (argObjs != null && argObjs.length > 0) {
                            try {
                                method = obj.getClass().getMethod("setUndefinedProperty", new Class[] {String.class, Object.class});
                                return method.invoke(obj, new Object[] {
                                        NameUtil.getNameFromSetter(methodName), argObjs[0]});
                            } catch (Throwable t3) {
                                throw t3;
                            }
                        }
                    }
                }
                else {
                    try {
                        return getJavaObjectPropertyValue(obj.getClass(), obj, methodName);
                    } catch (Throwable t4) {
                        return invokeJavaObjectMethod(obj, NameUtil.toGetterName(methodName), argObjs);
                    }
                }
            }
            throw t;
        }
    }


    public static Object transformToJavaObject(Class targetType, Object obj) {
/*
        if (obj.getClass().equals(targetType)) {
            return obj;
        }
*/
        if (obj instanceof Null) {
            return null;
        }
        if (boolean.class.isAssignableFrom(targetType) || Boolean.class.isAssignableFrom(targetType)) {
        	if (obj instanceof Boolean) {
        		return ((Boolean) obj).booleanValue();
        	}
        }
        else if (int.class.isAssignableFrom(targetType) || Integer.class.isAssignableFrom(targetType) ||
                long.class.isAssignableFrom(targetType) || java.lang.Long.class.isAssignableFrom(targetType)) {
            if (obj instanceof Int) {
                return ((Int) obj).getValue();
            }
        }
        else if (double.class.isAssignableFrom(targetType) || java.lang.Double.class.isAssignableFrom(targetType)) {
            if (obj instanceof Float) {
                return ((Float) obj).getValue();
            }
        }
        else if (float.class.isAssignableFrom(targetType) || java.lang.Float.class.isAssignableFrom(targetType)) {
            if (obj instanceof Float) {
                double dvalue = ((Float) obj).getValue();
                return java.lang.Float.valueOf((float) dvalue);
            }
        }
        else if (CharSequence.class.isAssignableFrom(targetType)) {
        	return obj.toString();
        }
        else if (Int.class.isAssignableFrom(targetType)) {
            if (obj instanceof Integer) {
                return new Int(((Integer) obj).intValue());
            }
        }
        else if (Float.class.isAssignableFrom(targetType)) {
            if (obj instanceof java.lang.Float) {
                return new Float(((java.lang.Float) obj).floatValue());
            }
        }
        else if (URL.class.isAssignableFrom(targetType)) {
        	if (obj instanceof SoyaURL) {
        		return ((SoyaURL) obj).getURL();
        	}
        }
        else if (Date.class.isAssignableFrom(targetType)) {
            if (obj instanceof SoyaDate) {
                return ((SoyaDate) obj).getDate();
            }
        }
        else if (SoyaURL.class.isAssignableFrom(targetType)) {
        	if (obj instanceof URL) {
        		return new SoyaURL((URL) obj);
        	}
        }
        else if (File.class.isAssignableFrom(targetType)) {
        	if (obj instanceof SoyaFile) {
        		return ((SoyaFile) obj).getFile();
        	}
        }
        else if (SoyaFile.class.isAssignableFrom(targetType)) {
        	if (obj instanceof File) {
        		return new SoyaFile((File) obj);
        	}
        }
        return obj;
    }

    public static Object wrapObject(Object obj) {

        if (obj == null) {
            return Null.NULL;
        }

        if (obj instanceof SoyaObject) {
            return obj;
        }

        if (obj.getClass().isArray()) {
            return new SoyaList((Object[]) obj);
        }
        else if (obj instanceof Boolean) {
        	return obj;
        }
        else if (obj instanceof Integer) {
            return new Int(((Integer) obj).intValue());
        }
        else if (obj instanceof Long) {
            return new Int(((Long) obj).intValue());
        }
        else if (obj instanceof java.lang.Float) {
            return new Float(((java.lang.Float) obj).floatValue());
        }
        else if (obj instanceof Double) {
            return new Float(((Double) obj).doubleValue());
        }
        else if (obj instanceof String) {
            return new SoyaString(obj.toString());
        }
        else if (obj instanceof Character) {
            return new SoyaString(obj.toString());
        }
        else if (obj instanceof Map && !(obj instanceof SoyaMap)) {
            return new SoyaMap((Map) obj);
        }
        else if (obj instanceof Collection) {
            if (obj instanceof List && !(obj instanceof SoyaList)) {
                return new SoyaList((List) obj);
            }
            return new SoyaList(((Collection) obj).toArray());
        }
        else if (obj instanceof URL) {
    		return new SoyaURL((URL) obj);
        }
        else if (obj instanceof Date) {
            return new SoyaDate((Date) obj);
        }
        else if (obj instanceof File) {
        	return new SoyaFile((File) obj);
        }
        return obj;
    }

    public static Object[] makeUnboxedArray(Class[] targetTypes, Object[] args) {
        if (args.length == 1 && args[0] instanceof Tuple) {
        	args = ((Tuple) args[0]).toArray();
        }
        Object[] results = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            try {
                Class t = Object.class;
                if (targetTypes != null && targetTypes.length > i) {
                    t = targetTypes[i];
                    results[i] = transformToJavaObject(t, args[i]);
                }
                else {
                    results[i] = args[i];
                }

            } catch (Throwable t) {
                System.out.println(t);
            }
        }
        return results;
    }


    public static Object[] makeVarArgUnboxedArray(Class[] targetTypes, Object[] args) {
        if (args.length == 1 && args[0] instanceof Tuple) {
            args = ((Tuple) args[0]).toArray();
        }
        Object[] results = new Object[targetTypes.length];
        for (int i = 0; i < targetTypes.length; i++) {
            if (i < targetTypes.length - 1) {
                results[i] = transformToJavaObject(targetTypes[i], args[i]);
            }
            else {
                int len = args.length - (targetTypes.length - 1);
                Object[] varRets =  new Object[len];
                for (int j = 0; j < len; j++) {
                    varRets[j] = transformToJavaObject(targetTypes[i], args[i + j]);
                }
                results[i] = varRets;
            }
        }
        return results;
    }


    public static boolean matchPoJoMethodParameterType(Class pType, Class aType) {
        if (pType.isAssignableFrom(aType)) {
            return true;
        }

        if (pType.isArray()) {
            if (aType.isArray() || List.class.isAssignableFrom(aType)) {
                return true;
            }
            else {
                return false;
            }
        }

        if (int.class.isAssignableFrom(pType) || Integer.class.isAssignableFrom(pType) ||
                long.class.isAssignableFrom(pType) || java.lang.Long.class.isAssignableFrom(pType) ||
                Int.class.isAssignableFrom(pType)) {
            if (int.class.isAssignableFrom(aType) || Integer.class.isAssignableFrom(aType) ||
                    long.class.isAssignableFrom(pType) || java.lang.Long.class.isAssignableFrom(pType) ||
                    Int.class.isAssignableFrom(aType)) {
                return true;
            }
        }
        if (float.class.isAssignableFrom(pType) || java.lang.Float.class.isAssignableFrom(pType) ||
                Float.class.isAssignableFrom(pType)) {
            if (float.class.isAssignableFrom(aType) || java.lang.Float.class.isAssignableFrom(aType) ||
                    Float.class.isAssignableFrom(aType)) {
                return true;
            }
        }
        if (String.class.isAssignableFrom(pType) || SoyaString.class.isAssignableFrom(pType)) {
            if (String.class.isAssignableFrom(aType) || SoyaString.class.isAssignableFrom(aType)) {
                return true;
            }
        }
        if (boolean.class.isAssignableFrom(pType) || Boolean.class.isAssignableFrom(pType)) {
        	if (Boolean.class.isAssignableFrom(aType) || boolean.class.isAssignableFrom(aType)) {
        		return true;
        	}
        }
        if (Date.class.isAssignableFrom(pType) || SoyaDate.class.isAssignableFrom(pType)) {
            if (Date.class.isAssignableFrom(aType) || SoyaDate.class.isAssignableFrom(aType)) {
                return true;
            }
        }
        if (File.class.isAssignableFrom(pType) || SoyaFile.class.isAssignableFrom(pType)) {
            if (File.class.isAssignableFrom(aType) || SoyaFile.class.isAssignableFrom(aType)) {
                return true;
            }
        }
        if (URL.class.isAssignableFrom(pType) || SoyaURL.class.isAssignableFrom(pType)) {
            if (URL.class.isAssignableFrom(aType) || SoyaDate.class.isAssignableFrom(aType)) {
                return true;
            }
        }
        return false;
    }

    public static boolean matchPoJoMethodParameterTypesWithVarArgs(Class[] pTypes, Class[] aTypes) {
        if (pTypes.length == 0) return false;

        int constLen = pTypes.length - 1;

        if (constLen >= aTypes.length) return false;

        Class lastPType = pTypes[pTypes.length - 1];
        if (!lastPType.isArray()) return false;

        Class[] pConstTypes = Arrays.copyOf(pTypes, constLen);
        Class[] aConstTypes = Arrays.copyOf(aTypes, constLen);

        if (!matchPoJoMethodParameterTypes(pConstTypes, aConstTypes)) {
            return false;
        }
        return true;
    }


    public static boolean matchPoJoMethodParameterTypes(Class[] pTypes, Class[] aTypes) {
        if (pTypes.length == 0 && aTypes.length == 0) {
            return true;
        }
        if (pTypes.length != aTypes.length) {
/*
        	if (aTypes.length == 1 && Tuple.class.isAssignableFrom(aTypes[0]) && pTypes.length > 0) {
        		return true;
        	}
*/
            return false;
        }
        for (int i = 0; i < pTypes.length; i++) {
            if (!matchPoJoMethodParameterType(pTypes[i], aTypes[i])) {
                return false;
            }
        }
        return true;
    }


}
