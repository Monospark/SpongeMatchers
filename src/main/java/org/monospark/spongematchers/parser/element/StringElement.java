package org.monospark.spongematchers.parser.element;

import java.util.regex.Matcher;

public abstract class StringElement {

    private int start;

    private int end;

    private String string;

    StringElement(Matcher matcher) {
        start = matcher.start();
        end = matcher.end();
        string = matcher.group();
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
