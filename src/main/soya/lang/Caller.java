package soya.lang;

/**
 * @author: Jun Gong
 */
public interface Caller extends Pattern {

    public Object getObject();

    public Object call() throws Throwable;

    public Object call(Object it) throws Throwable;

    public Object call(Object...args) throws Throwable;
}
