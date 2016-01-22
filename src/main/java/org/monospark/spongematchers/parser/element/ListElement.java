package org.monospark.spongematchers.parser.element;

import java.util.List;

public final class ListElement extends StringElement {

    private List<StringElement> elements;
    
    ListElement(int start, int end, List<StringElement> elements) {
        super(start, end);
        this.elements = elements;
    }

    public List<StringElement> getElements() {
        return elements;
    }
}
