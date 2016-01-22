package org.monospark.spongematchers.parser.type;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.base.BaseMatcherParser;
import org.monospark.spongematchers.parser.element.BaseElement;
import org.monospark.spongematchers.parser.element.StringElement;

public final class BaseType<T> extends MatcherType<T> {

    private BaseMatcherParser<T> parser;

    BaseType(BaseMatcherParser<T> parser) {
        super();
        this.parser = parser;
    }

    @SuppressWarnings("unchecked")
    @Override
    public SpongeMatcher<T> parse(StringElement element) throws SpongeMatcherParseException {
        if (!(element instanceof BaseElement)) {
            throw new SpongeMatcherParseException("The element must be a " + parser.getName() + " matcher");
        }
        
        BaseElement<?> e = (BaseElement<?>) element;
        if (!e.getParser().equals(parser)) {
            throw new SpongeMatcherParseException("Expected matcher: " + parser.getName() + ", but got: " +
                    e.getParser().getName());
        }
        return (SpongeMatcher<T>) e.getParser().parseMatcher(e.getString());
    }

    BaseMatcherParser<T> getParser() {
        return parser;
    }
}
