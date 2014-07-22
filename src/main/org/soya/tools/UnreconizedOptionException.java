package org.soya.tools;

/**
 * @author: Jun Gong
 */
public class UnreconizedOptionException extends Exception {

    private String option;

    public UnreconizedOptionException(String option) {
        super();
        this.option = option;
    }

    public String getOption() {
        return option;
    }

    public String getMessage() {
        return "Unrecognized option: " + option + "\n-help will display more infomation.";
    }
}
