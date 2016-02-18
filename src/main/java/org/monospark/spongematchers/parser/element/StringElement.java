package org.monospark.spongematchers.parser.element;

import java.util.regex.Matcher;

public abstract class StringElement {

    private int start;

    private int end;

    private String string;

    StringElement(String string, Matcher matcher) {
        this.string = string;
        start = matcher.start();
        end = matcher.end();
    }

    public final int getStart() {
        return start;
    }

    public final int getEnd() {
        return end;
    }

    public final String getString() {
        return string;
    }
}
