package soya.lang;

/**
 * @author: Jun Gong
 */
public class NoSuchPropertyException extends SoyaRuntimeException {

    protected MetaClass clazz;
    protected String propertyName;

    public NoSuchPropertyException(MetaClass clazz, String propertyName) {
        this.clazz = clazz;
        this.propertyName = propertyName;
    }

    public NoSuchPropertyException(MetaClass clazz, String propertyName, Throwable th) {
        super(th);
        this.clazz = clazz;
        this.propertyName = propertyName;
    }

    public String getMessage() {
        return clazz.getName() + "." + propertyName;
    }

    public MetaClass getClazz() {
        return clazz;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
