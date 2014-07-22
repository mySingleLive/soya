package soya.lang;

import org.soya.runtime.InvokeUtil;
import org.soya.runtime.MetaClassUtil;

import java.util.List;

/**
 * @author: Jun Gong
 */
public class MethodArray extends SoyaList implements SoyaMethod, Caller {

    private Object object;

    public MethodArray() {
        this.metaClass = MetaClassUtil.MethodArray;
    }

    public MethodArray(SoyaMethod[] methodArray) {
        super(methodArray);
        this.metaClass = MetaClassUtil.MethodArray;
    }

    public MethodArray(List<SoyaMethod> methodList) {
        super(methodList);
        this.metaClass = MetaClassUtil.MethodArray;
    }

    public boolean add(SoyaMethod o) {
        return super.add(o);
    }

    public SoyaMethod get(int i) {
        return (SoyaMethod) super.get(i);
    }

    public SoyaMethod first() {
        return (SoyaMethod) super.first();
    }


    @Override
    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public boolean isVarArgs() {
        if (size() > 0) {
            return first().isVarArgs();
        }
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getObject() {
        return object;
    }

    @Override
    public int getModifier() {
        if (size() > 0) {
            return first().getModifier();
        }
        return -1;
    }

    @Override
    public String getMethodName() {
        if (size() > 0) {
            return first().getMethodName();
        }
        return null;
    }

    @Override
    public List getAnnotations() {
        if (size() > 0) {
            return first().getAnnotations();
        }
        return null;
    }

    @Override
    public boolean isMatch(Object obj) throws Throwable {
        Object ret = call(obj);
        if (ret instanceof Boolean) {
            return ((Boolean) ret).booleanValue();
        }
        return !InvokeUtil.isNull(ret);
    }

    public Object call() throws Throwable {
        return call(new Object[0]);
    }

    public Object call(Object it) throws Throwable {
        return invoke(object, new Object[] {it});
    }

    public Object call(Object... args) throws Throwable {
        return invoke(object, args);
    }

    public Object invoke(Object invoker) throws Throwable {
        return invoke(invoker, null);
    }

    public Object invoke(Object invoker, Object[] args) throws Throwable {
        return invoke(invoker, InvokeUtil.getObjectClasses(args), args);
    }

    public Object invoke(Object invoker, Class[] argTypes, Object[] args) throws Exception, Throwable {
        SoyaMethod method = findMethod(argTypes);
        if (method != null) {
            return method.invoke(invoker, argTypes, args);
        }
        return null;
    }

    private SoyaMethod findMethod(Class[] paramTypes) {
        return InvokeUtil.findPMethod(list, paramTypes, false);
    }

}
