package org.monospark.spongematchers.parser.element;

import java.util.regex.Matcher;

import org.monospark.spongematchers.parser.element.PatternElementParser.Type;

public final class PatternElement extends StringElement {
    
    private Type type;
    
    private StringElement element;

    PatternElement(Matcher matcher, Type type, StringElement element) {
        super(matcher);
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
