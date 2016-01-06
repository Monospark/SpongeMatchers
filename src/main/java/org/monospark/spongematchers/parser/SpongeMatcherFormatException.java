package org.monospark.spongematchers.parser;


public class SpongeMatcherFormatException extends Exception {

    private static final long serialVersionUID = -8985255826847314919L;

    public SpongeMatcherFormatException(String message) {
        super(message);
    }

    public SpongeMatcherFormatException(Throwable cause) {
        super(cause);
    }
}
