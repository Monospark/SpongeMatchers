package org.monospark.spongematchers.parser.element;

import java.util.List;
import java.util.regex.Matcher;

public final class ListElement extends StringElement {

    private List<StringElement> elements;
    
    ListElement(Matcher matcher, List<StringElement> elements) {
        super(matcher);
        this.elements = elements;
    }

    public List<StringElement> getElements() {
        return elements;
    }
}
