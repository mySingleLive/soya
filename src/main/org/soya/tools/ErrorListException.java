package org.soya.tools;

import java.util.*;

/**
 * @author: Jun Gong
 */
public class ErrorListException extends RuntimeException {

    private ErrorList errorList;

    public ErrorListException(ErrorList errorList) {
        this.errorList = errorList;
    }

    public void printErrors() {
        LinkedList<Exception> errors = errorList.getErrors();
        if (errors.isEmpty()) {
            return;
        }
        Exception ex = errors.getFirst();
        System.err.println(ex.getLocalizedMessage());
        errors.removeFirst();
        printErrors();
    }

    public void printStackTrace() {
        printErrors();
    }
}
