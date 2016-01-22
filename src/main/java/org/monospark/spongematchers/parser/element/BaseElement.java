package org.monospark.spongematchers.parser.element;

import org.monospark.spongematchers.parser.base.BaseMatcherParser;

public final class BaseElement<T> extends StringElement {

    private String string;
    
    private BaseMatcherParser<T> parser;

    BaseElement(int start, int end, String string, BaseMatcherParser<T> parser) {
        super(start, end);
        this.string = string;
        this.parser = parser;
    }

    public String getString() {
        return string;
    }
    
    public BaseMatcherParser<T> getParser() {
        return parser;
    }
}
