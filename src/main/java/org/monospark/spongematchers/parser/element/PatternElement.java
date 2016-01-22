package org.monospark.spongematchers.parser.element;

import org.monospark.spongematchers.parser.element.PatternElementParser.Type;

public final class PatternElement extends StringElement {
    
    private Type type;
    
    private StringElement element;

    PatternElement(int start, int end, Type type, StringElement element) {
        super(start, end);
        this.type = type;
        this.element = element;
    }

    public Type getType() {
        return type;
    }

    public StringElement getElement() {
        return element;
    }
}
