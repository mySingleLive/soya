package soya.lang;

/**
 * @author: Jun Gong
 */
public class NoSuchVariableException extends SoyaRuntimeException {

    public NoSuchVariableException(String variableName) {
        super(variableName);
    }
}
