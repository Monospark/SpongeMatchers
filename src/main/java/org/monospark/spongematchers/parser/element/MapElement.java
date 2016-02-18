package org.monospark.spongematchers.parser.element;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;

public final class MapElement extends StringElement {

    private Map<String, StringElement> elements;

    MapElement(String string, Matcher matcher, Map<String, StringElement> elements) {
        super(string, matcher);
        this.elements = elements;
    }

    public Optional<StringElement> getElement(String key) {
        return Optional.ofNullable(elements.get(key));
    }

    public Map<String, StringElement> getElements() {
        return elements;
    }
}
