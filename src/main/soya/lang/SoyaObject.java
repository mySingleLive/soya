package soya.lang;

import java.util.List;

/**
 * @author: Jun Gong
 */
public interface SoyaObject {
    public Object invokeMethod(SoyaMethod method, Object[] args) throws Exception, Throwable;
    public Object invokeMethod(String methodName, Object[] args) throws Exception, Throwable;
    public boolean isNull();
    public Object match(Object obj);
    public MetaClass getMetaClass();
    public Class getObjectClass();
    public Object get(Object[] indexes) throws Throwable;
    public void set(Object[] indexes, Object obj) throws Throwable;
    public void set(Object index, Object obj) throws Throwable;
    public void setPropertyValue(String propertyName, Object value) throws Exception;
    public Object getPropertyValue(String propertyName) throws Exception, Throwable;
    public List<Property>getProperties();
    public List<SoyaMethod> getPublicMethods();
    public SoyaMethod getPublicMethod(String methodName);
}
