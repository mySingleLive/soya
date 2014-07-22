package soya.lang;

import org.soya.runtime.MetaClassUtil;

import java.util.List;

/**
 * @author: Jun Gong
 */
public abstract class AbstractObject implements SoyaObject {

    protected MetaClass metaClass;

    public AbstractObject() {
        this(MetaClassUtil.PObject);
    }

    public AbstractObject(MetaClass metaClass) {
        this.metaClass = metaClass;
    }

    public Object invokeMethod(SoyaMethod method, Object[] args) throws Throwable {
        return null;
    }

    public Object invokeMethod(String methodName, Object[] args) throws Throwable {
        return null;
    }


    public Object match(Object obj) {
        return null;
    }

    public MetaClass getMetaClass() {
        return metaClass;
    }
    
    public void setMetaClass(MetaClass metaClass) {
    	this.metaClass = metaClass;
    }

    public void setPropertyValue(String propertyName, Object value) throws Exception {
    }

    public Object getPropertyValue(String propertyName) throws Throwable {
        return null;
    }

    public List<Property> getProperties() {
        return null;
    }

    public List<SoyaMethod> getPublicMethods() {
        return null;
    }

    public SoyaMethod getPublicMethod(String methodName) {
        return null;
    }

    public boolean isNull() {
        return false;
    }

    public Object get(Object[] indexes) throws Throwable {
        return null;
    }

    public void set(Object[] indexes, Object obj) throws Throwable {
    }

    public void set(Object index, Object obj) throws Throwable {
    }

    @Override
    public Class getObjectClass() {
        return metaClass.clazz;
    }
}
