package soya.lang;

/**
 * @author: Jun Gong
 */
public abstract class WrapperCommand<T, R> {

    public abstract R wrap(T t);
}
