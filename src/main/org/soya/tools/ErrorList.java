package org.soya.tools;

import java.util.LinkedList;

/**
 * @author: Jun Gong
 */
public class ErrorList {

    protected LinkedList<Exception> errors;

    protected LinkedList<?> warnings;

    protected Configuration configuration;

    public ErrorList(Configuration configuration) {
        this.configuration = configuration;
        errors = new LinkedList<Exception>();
    }

    public boolean hasErrors() {
        return errors.size() > 0;
    }

    public boolean hasWarnings() {
        return warnings.size() > 0;
    }

    public LinkedList<Exception> getErrors() {
        return errors;
    }

    public LinkedList<?> getWarnings() {
        return warnings;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public void addException(Exception exception) {
        errors.add(exception);
    }

    public void interruptIfHasErrors() throws ErrorListException {
        if (hasErrors()) {
            throw new ErrorListException(this);
        }
    }
}
