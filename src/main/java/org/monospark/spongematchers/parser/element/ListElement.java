package org.monospark.spongematchers.parser.element;

import java.util.List;
import java.util.regex.Matcher;

public final class ListElement extends StringElement {

    private List<StringElement> elements;

    ListElement(String string, Matcher matcher, List<StringElement> elements) {
        super(string, matcher);
        this.elements = elements;
    }

    public List<StringElement> getElements() {
        return elements;
    }
}
