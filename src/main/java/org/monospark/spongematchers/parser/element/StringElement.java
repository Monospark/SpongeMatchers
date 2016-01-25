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

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public String getString() {
        return string;
    }
}
