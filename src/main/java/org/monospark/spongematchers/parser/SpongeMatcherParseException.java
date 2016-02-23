package org.monospark.spongematchers.parser;


public final class SpongeMatcherParseException extends Exception {

    private static final long serialVersionUID = -8985255826847314919L;

    public SpongeMatcherParseException(String message) {
        super(message);
    }

    public SpongeMatcherParseException(Throwable cause) {
        super(cause);
    }

    public SpongeMatcherParseException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
