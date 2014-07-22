package soya.lang;

import java.util.List;

/**
 * @author: Jun Gong
 */
public interface SoyaMethod extends SoyaObject {
    public int getModifier();
    public String getMethodName();
    public List getAnnotations();
    public Object getObject();
    public void setObject(Object obj);
    public boolean isVarArgs();
    public Object call() throws Throwable;
    public Object call(Object...args) throws Throwable;
    public Object invoke(Object invoker) throws Throwable;
    public Object invoke(Object invoker, Object[] args) throws Throwable;
    public Object invoke(Object invoker, Class[] argTypes, Object[] args) throws Throwable;
}
