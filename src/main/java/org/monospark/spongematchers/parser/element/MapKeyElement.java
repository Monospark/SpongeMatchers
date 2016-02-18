package org.monospark.spongematchers.parser.element;

import java.util.regex.Matcher;

public final class MapKeyElement extends StringElement {

    private String name;

    MapKeyElement(String string, Matcher matcher, String name) {
        super(string, matcher);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
