package org.monospark.spongematchers;


public class MatcherFormatException extends Exception {

    private static final long serialVersionUID = -8985255826847314919L;

    public MatcherFormatException(String message) {
        super(message);
    }

    public MatcherFormatException(Throwable cause) {
        super(cause);
    }
}
