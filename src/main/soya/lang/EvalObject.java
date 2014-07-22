package soya.lang;

import org.soya.runtime.InvokeUtil;
import org.soya.runtime.MetaClassUtil;
import org.soya.util.ArrayUtil;
import org.soya.util.NameUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author: Jun Gong
 */
public abstract class EvalObject extends AbstractObject {

    private final Class[] UNDEFINED_METHOD_ARG_TYPES = new Class[] {String.class, List.class};
    private final Class[] UNDEFINED_PROPERTY_GETTER_ARG_TYPES = new Class[] {String.class};
    private final Class[] UNDEFINED_PROPERTY_SETTER_ARG_TYPES = new Class[] {String.class, List.class};


    protected Object object;

    public EvalObject(MetaClass cls) {
        super(cls);
    }

    public EvalObject(Class clz) {
        super(MetaClassUtil.createMetaClass(clz));
    }

    public EvalObject() {
        super(MetaClassUtil.PObject);
    }

    protected Object getObject() {
        return object;
    }

    protected void setObject(Object object) {
        this.object = object;
    }

    public Object invokeUndefinedMethod(String name, List args) throws NoSuchMethodException {
        throw new NoSuchMethodException(name);
    }

    public Object getUndefinedProperty(String name) throws NoSuchPropertyException {
        throw new NoSuchPropertyException(getMetaClass(), name);
    }

    public void setUndefinedProperty(String name, Object value) throws NoSuchPropertyException {
        throw new NoSuchPropertyException(getMetaClass(), name);
    }

    public boolean isEmpty() {
        return false;
    }

    private SoyaMethod getUndefinedPropertyGetter() {
        try {
            return getMetaClass().getMethod("getUndefinedProperty", UNDEFINED_PROPERTY_GETTER_ARG_TYPES);
        } catch (NoSuchMethodException e) {
        }
        return null;
    }

    private SoyaMethod getUndefinedPropertySetter() {
        try {
            return getMetaClass().getMethod("setUndefinedProperty", UNDEFINED_PROPERTY_GETTER_ARG_TYPES);
        } catch (NoSuchMethodException e) {
        }
        return null;
    }


    public Object invokeMethod(SoyaMethod method, Object[] args) throws Throwable {
        return method.invoke(this, args);
    }

    public Object invokeMethod(String methodName, Object[] args) throws Throwable {
        MetaClass cls = getMetaClass();
        Class[] argTypes = InvokeUtil.getObjectClasses(args);
        SoyaMethod method = null;
        try {
            method = cls.getMethod(methodName, argTypes);
        } catch (NoSuchMethodException e) {
            try {
                // First, find the customed method "invokeUndefinedMethod";
                return invokeUndefinedMethod(methodName, new SoyaList(args));
            } catch (NoSuchMethodException e2) {
                if (NameUtil.isGetterName(methodName)) {
                    // Case 1: Method name is a getter
                    try {
                        return getUndefinedProperty(NameUtil.getNameFromGetter(methodName));
                    } catch (NoSuchPropertyException e3) {
                        throw e;
                    }
                }
                else if (NameUtil.isSetterName(methodName)) {
                    // Case 2: Method name is a setter
                    if (args != null && args.length > 0) {
                        try {
                            setUndefinedProperty(NameUtil.getNameFromSetter(methodName), args[0]);
                            return Null.NULL;
                        } catch (NoSuchPropertyException e4) {
                            throw e;
                        }
                    }
                }
                else if (NameUtil.isBooleanGetterrName(methodName)) {
                    // Case 3: Method name is a boolean-type getter
                    try {
                        return getUndefinedProperty(NameUtil.getNameFromBooleanGetter(methodName));
                    } catch (NoSuchPropertyException e4) {
                        throw e;
                    }
                }
                else {
                    throw e;
                }
            }
        }

        try {
            Class[] paramTypes = ((SJMethod) method).getMethod().getParameterTypes();
            args = adjustArguments(method, paramTypes, argTypes, args);
            return method.invoke(this, paramTypes, args);
        } catch (InvocationTargetException ite) {
            throw ite.getTargetException();
        } catch (Throwable t) {
            throw t;
        }
    }


    private Object[] adjustArguments(SoyaMethod method, Class[] paramTypes, Class[] argTypes, Object[] args) {
        if (method.isVarArgs()) {
            int constLen = paramTypes.length - 1;
            Object[] newArgs = new Object[constLen + 1];
            Object[] varArgs = new Object[args.length - constLen];
            int v = 0;

            for (int i = 0; i < args.length; i++) {
                if (i < constLen) {
                    newArgs[i] = args[i];
                }
                else {
                    varArgs[v] = args[i];
                    v++;
                }
            }
            newArgs[newArgs.length - 1] = varArgs;
            args = newArgs;
        }
        else {
            Object[] newArgs = new Object[args.length];
            for (int i = 0; i < paramTypes.length; i++) {
                Class pType = paramTypes[i];
                Object arg = null;
                arg = args[i];
                if (pType.isArray()) {
                    arg = ArrayUtil.makeArray(arg);
                }
                newArgs[i] = arg;
            }
            args = newArgs;
        }
        return args;
    }


    public Object getPropertyValue(String propertyName) throws Throwable {
        MetaClass cls = getMetaClass();
        try {
            return invokeMethod(propertyName, new Object[0]);
        } catch (NoSuchMethodException e) {
            String getterName = NameUtil.toGetterName(propertyName);
            try {
                return invokeMethod(getterName, new Object[0]);
            } catch (NoSuchMethodException e2) {
                try {
                    return cls.getClazz().getField(propertyName).get(this);
                } catch (NoSuchFieldException ef) {
                    return getUndefinedProperty(propertyName);
                }
            } catch (Exception e3) {
                throw e3;
            }
        }
    }

    public void setPropertyValue(String propertyName, Object[] values) throws Throwable {
        MetaClass cls = getMetaClass();
        String setterName = NameUtil.toSetterName(propertyName);
        try {
            invokeMethod(setterName, values);
        } catch (NoSuchMethodException e) {
            try {
                cls.getClazz().getField(propertyName).set(this, values[0]);
            } catch (NoSuchFieldException ef) {
                if (values != null && values.length > 0) {
                    setUndefinedProperty(propertyName, values[0]);
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

}
