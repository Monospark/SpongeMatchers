package org.monospark.spongematchers.parser.element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.parser.base.BaseMatcherParser;

public final class BaseElementParser<T> extends StringElementParser {

    private BaseMatcherParser<T> parser;

    BaseElementParser(BaseMatcherParser<T> parser) {
        this.parser = parser;
    }

    @Override
    Pattern createPattern() {
        return parser.getAcceptancePattern();
    }

    @Override
    void parse(Matcher matcher, StringElementContext context) {
        context.addElement(new BaseElement<>(matcher.start(), matcher.end(), matcher.group(), parser));
    }
}
