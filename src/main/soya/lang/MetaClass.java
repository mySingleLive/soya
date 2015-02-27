package soya.lang;

import org.soya.runtime.InvokeUtil;
import org.soya.runtime.MetaClassUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author: Jun Gong
 */
public class MetaClass extends EvalObject implements Pattern {

    protected Class clazz;
    protected final String name;
    protected final MetaClass superClass;
    protected final Map<String, Object> staticValues = new HashMap<String, Object>();
    protected final Map<String, SoyaMethod> staticPublicMethods = new HashMap<String, SoyaMethod>();
    protected final Map<String, SoyaMethod> staticPrivateMethods = new HashMap<String, SoyaMethod>();
    protected final Map<String, Object> varValues = new HashMap<String, Object>();
    protected final Map<String, Property> properties = new HashMap<String, Property>();
    protected final Map<String, List<SoyaMethod>> methodMap = new HashMap<String, List<SoyaMethod>>();

    public MetaClass(String name, MetaClass superClass) {
        super(MetaClassUtil.MetaClass);
        this.name = name;
        this.superClass = superClass;
    }

    public MetaClass(Class clazz) {
        this(clazz, null);
    }

    public MetaClass(Class clazz, MetaClass superClass) {
        super(MetaClassUtil.MetaClass);
        this.clazz = clazz;
        this.name = clazz.getName();
        this.superClass = superClass;

        Method[] jmethods = clazz.getMethods();
        for (Method jmethod : jmethods) {
            SJMethod method = new SJMethod(jmethod);
            defineMethod(method);
        }

    }

    public String getName() {
        return name;
    }

    public MetaClass getSuperClass() {
        return superClass;
    }

    public boolean isNull() {
        return false;
    }

    public Object newInstance(Object[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        if (clazz != null) {
            Constructor[] cons = clazz.getConstructors();
            Constructor con = null;
            Class[] argTypes = new Class[args.length];
            Class[] paramTypes = null;

            for (int i = 0; i < args.length; i++) {
                argTypes[i] = args[i].getClass();
            }

            for (int i = 0; i < cons.length; i++) {
                con = cons[i];
                paramTypes = con.getParameterTypes();
                if (InvokeUtil.matchPoJoMethodParameterTypes(paramTypes, argTypes)) {
                    break;
                }
            }
            Object[] argArray = InvokeUtil.makeUnboxedArray(paramTypes, args);
            Object result = con.newInstance(argArray);
            if (result instanceof AbstractObject) {
                ((AbstractObject) result).setMetaClass(this);
            }
            return result;
        }
//        EvalObject instance = new EvalObject(this);
        return null;
    }

    public void defineMethod(SoyaMethod method) {
        String methodName = method.getMethodName();
        if (methodMap.containsKey(methodName)) {
            methodMap.get(methodName).add(method);
        }
        else {
            List<SoyaMethod> methodList = new ArrayList<SoyaMethod>();
            methodList.add(method);
            methodMap.put(methodName, methodList);
        }
    }

    public Map<String, List<SoyaMethod>> getMethodMap() {
        return methodMap;
    }

    public MethodArray findMethod(String methodName) {
        List<SoyaMethod> methodList = methodMap.get(methodName);
        MethodArray array = new MethodArray(methodList);
        return array;
    }

    public SoyaMethod getMethod(String methodName) throws NoSuchMethodException {
        if (!methodMap.containsKey(methodName)) {
            if (superClass == null) {
                throw new NoSuchMethodException(methodName + " of " + getName());
            }
            else {
                try {
                    return superClass.getMethod(methodName);
                }
                catch (NoSuchMethodException ex) {
                    throw new NoSuchMethodException(methodName + " of " + getName());
                }
            }
        }
        List<SoyaMethod> methodList = methodMap.get(methodName);
        if (methodList == null || methodList.size() == 0) {
            throw new NoSuchMethodException(methodName + " of " + getName());
        }
        return methodList.get(0);
    }

    public SoyaMethod getMethod(String methodName, Class[] paramterTypes) throws NoSuchMethodException {

        Method m = null;

        try {
            m = clazz.getMethod(methodName, paramterTypes);
        } catch (NoSuchMethodException e) {
        }
        if (m != null) {
            return new SJMethod(m);
        }

        if (!methodMap.containsKey(methodName)) {
            if (superClass == null) {
                throw new NoSuchMethodException(methodName + " of " + getName());
            }
            else {
                try {
                    return superClass.getMethod(methodName, paramterTypes);
                }
                catch (NoSuchMethodException ex) {
                    throw new NoSuchMethodException(methodName + " of " + getName());
                }
            }
        }
        List<SoyaMethod> methodList = methodMap.get(methodName);
        if (methodList == null || methodList.size() == 0) {
            throw new NoSuchMethodException(methodName + " of " + getName());
        }

        SoyaMethod method = InvokeUtil.findPMethod(methodList, paramterTypes, false);
        if (method == null) {
            throw new NoSuchMethodException(methodName + " of " + getName());
        }
        return method;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof MetaClass) {
            ((MetaClass) o).getName().equals(getName());
        }
        return false;
    }

    public boolean isMatch(Object obj) {
        return equals(obj);
    }
    
    public boolean isKindOf(Class clz) {
    	if (clazz == null) {
    		return false;
    	}
    	return clz.isAssignableFrom(clazz);
    }

    public boolean hasMethod(String methodName) {
        if (methodMap.containsKey(methodName)) {
            return true;
        }
        if (superClass != null) {
            if (methodMap.containsKey(methodName)) {
                return true;
            }
        }
        List<SoyaMethod> methodList = methodMap.get(methodName);
        if (methodList == null || methodList.size() == 0) {
            return false;
        }
        return true;
    }

    public Class getClazz() {
        return clazz;
    }

    public String toString() {
        return "<Class \"" + getName() +"\">";
    }
}
