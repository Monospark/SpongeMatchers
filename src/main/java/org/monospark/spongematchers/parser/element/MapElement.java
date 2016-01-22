package org.monospark.spongematchers.parser.element;

import java.util.Map;
import java.util.Optional;

public final class MapElement extends StringElement {

    private Map<String,StringElement> elements;
    
    MapElement(int start, int end, Map<String,StringElement> elements) {
        super(start, end);
        this.elements = elements;
    }
    
    public Optional<StringElement> getElement(String key) {
        return Optional.ofNullable(elements.get(key));
    }
}
