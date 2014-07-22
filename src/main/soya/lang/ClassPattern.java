package soya.lang;

import org.soya.runtime.MetaClassUtil;
import org.soya.runtime.PatternUtil;
import org.soya.runtime.VarScope;

import java.io.File;
import java.lang.*;
import java.lang.Double;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: Jun Gong
 */
public class ClassPattern extends ObjectPattern implements Pattern {

    private final MetaClass metaClass;

    public ClassPattern(Class clazz) {
        this(MetaClassUtil.createMetaClass(clazz));
    }

    public ClassPattern(Class clazz, String alias, VarScope varScope) {
        this(MetaClassUtil.createMetaClass(clazz), alias, varScope);
    }

    public ClassPattern(MetaClass metaClass) {
        this(metaClass, null, null);
    }

    public ClassPattern(MetaClass metaClass, String alias, VarScope varScope) {
        super(MetaClassUtil.ClassPattern);
        this.metaClass = metaClass;
        this.alias = alias;
        this.varScope = varScope;
    }


    private boolean isCaseOfClass(Object obj) throws InvocationTargetException, IllegalAccessException {
        Class clazz = metaClass.getClazz();
        if (clazz.equals(Object.class)) {
            return true;
        }

        Method isCaseMethod = null;
        try {
            isCaseMethod = clazz.getMethod("isCase", new Class[]{Object.class});
        }
        catch (Throwable t) {
        }

        try {
            if (isCaseMethod != null) {
                Object caseRet = isCaseMethod.invoke(clazz, obj);
                if (caseRet instanceof Boolean) {
                    return  ((Boolean) caseRet).booleanValue();
                }
            }
        } catch (Throwable t) {
        }

        if (obj instanceof SoyaString && clazz.isAssignableFrom(String.class)) {
            return true;
        }
        else if (obj instanceof Int || obj instanceof Integer || obj.getClass().isAssignableFrom(int.class)) {
            if (clazz.isAssignableFrom(Int.class) ||
                    clazz.isAssignableFrom(Integer.class) ||
                    clazz.isAssignableFrom(int.class) ||
                    clazz.isAssignableFrom(Number.class))
                return true;
        }

        if (obj instanceof java.lang.Float || obj instanceof Float ||
                obj instanceof Double ||
                obj.getClass().isAssignableFrom(float.class) ||
                obj.getClass().isAssignableFrom(double.class)) {
            if (clazz.isAssignableFrom(Double.class) ||
                    clazz.isAssignableFrom(Float.class) ||
                    clazz.isAssignableFrom(java.lang.Float.class) ||
                    clazz.isAssignableFrom(Number.class)) {
                return true;
            }
        }

        if (obj instanceof SoyaFile || obj instanceof File) {
            if (clazz.isAssignableFrom(SoyaFile.class) || clazz.isAssignableFrom(File.class)) {
                return true;
            }
        }

        if (clazz.isAssignableFrom(obj.getClass())) {
            return true;
        }
        return false;
    }

    public boolean isCase(Object obj) throws Throwable {
        boolean classPartRet = isCaseOfClass(obj);
        if (!classPartRet) return false;

        if (!conditions.isEmpty()) {
            for (int i = 0; i < conditions.size(); i++) {
                Object condition = conditions.get(i);
                if (condition instanceof SoyaMap) {
                    if (!PatternUtil.isMatchProperties(obj, (SoyaMap) condition)) {
                        return false;
                    }
                }
                else if (condition instanceof Closure) {
                    Closure closure = (Closure) condition;
                    Object result = closure.call(obj);
                    if (result == null || result == Null.NULL || result.equals(Boolean.FALSE)) {
                        return false;
                    }
                }
                else if (!PatternUtil.isMatch(obj, condition)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isMatch(Object obj) throws Throwable {
        boolean ret = isCase(obj);
        if (ret) {
            setValue(obj);
        }
        return ret;
    }

    public MetaClass getMetaClass() {
        return metaClass;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ClassPattern) {
            Class clazz = ((ClassPattern) obj).getMetaClass().clazz;
            return clazz.equals(clazz);
        }
        return false;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(metaClass.getName());
        if (!conditions.isEmpty()) {
            buffer.append(conditions);
        }
        return buffer.toString();
    }

    public ClassPattern clone() {
        ClassPattern classPattern = new ClassPattern(metaClass);
        classPattern.setConditions(conditions);
        return classPattern;
    }
}
