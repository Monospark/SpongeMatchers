package org.monospark.spongematchers.parser.element;

import java.util.regex.Matcher;

import org.monospark.spongematchers.parser.base.BaseMatcherParser;

public final class BaseElement<T> extends StringElement {

    private BaseMatcherParser<T> parser;

    BaseElement(Matcher matcher, BaseMatcherParser<T> parser) {
        super(matcher);
        this.parser = parser;
    }

    public BaseMatcherParser<T> getParser() {
        return parser;
    }
}
