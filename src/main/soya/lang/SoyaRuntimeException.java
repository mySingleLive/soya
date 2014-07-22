package soya.lang;

/**
 * @author: Jun Gong
 */
public class SoyaRuntimeException extends RuntimeException {

    public SoyaRuntimeException() {
        super();
    }

    public SoyaRuntimeException(String s) {
        super(s);
    }

    public SoyaRuntimeException(String s, Throwable t) {
        super(s, t);
        initCause(t);
    }

    public SoyaRuntimeException(Throwable t) {
        super(t);
    }


}
