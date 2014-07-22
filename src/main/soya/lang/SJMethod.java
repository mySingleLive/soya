package soya.lang;

import org.soya.runtime.InvokeUtil;
import org.soya.runtime.MetaClassUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author: Jun Gong
 */
public class SJMethod extends EvalObject implements SoyaMethod {

    protected Object object;
    protected final Method method;

    public SJMethod(Method method) {
        super(MetaClassUtil.Method);
        this.method = method;
    }

    public int getModifier() {
        return method.getModifiers();
    }

    public String getMethodName() {
        return method.getName();
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public boolean isVarArgs() {
        return method.isVarArgs();
    }

    public String toString() {
        return method.toString();
    }

    public List getAnnotations() {
        Annotation[] annotations = method.getAnnotations();
        return new SoyaList(annotations);
    }

    public Object call() throws Throwable {
        return invoke(object);
    }

    public Object call(Object...args) throws Throwable {
        return invoke(object, args);
    }

    public Object invoke(Object invoker) throws Throwable {
        return invoke(invoker, null, null);
    }

    public Object invoke(Object invoker, Object[] args) throws Throwable {
        return invoke(invoker, null, args);
    }

    public Object invoke(Object invoker, Class[] parameterTypes, Object[] args) throws Throwable {
        //System.out.println("Invoke: " + ((EvalObject) invoker).getMetaClass().getName() + ", method: " + method.getName() + ", args: " + Arrays.asList(args));
        if (args == null) {
            args = new Object[0];
        }
        Object result = null;
        if (parameterTypes != null) {
            result = method.invoke(invoker, InvokeUtil.makeUnboxedArray(parameterTypes, args));
        }
        else {
            result = method.invoke(invoker, args);
        }
        return InvokeUtil.wrapObject(result);
    }

    public Method getMethod() {
        return method;
    }

    @Override
    public Class getObjectClass() {
        return Method.class;
    }
}
