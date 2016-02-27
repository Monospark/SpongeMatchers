package org.monospark.spongematchers.type;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.base.BaseMatcherParser;
import org.monospark.spongematchers.parser.element.BaseElement;
import org.monospark.spongematchers.parser.element.StringElement;

public final class BaseType<T> extends MatcherType<T> {

    private BaseMatcherParser<T> parser;

    BaseType(BaseMatcherParser<T> parser) {
        this.parser = parser;
    }

    @Override
    public boolean canMatch(Object o) {
        return parser.getBaseClass().equals(o.getClass());
    }

    @Override
    protected boolean canParse(StringElement element) {
        if (element instanceof BaseElement) {
            BaseElement<?> base = (BaseElement<?>) element;
            return base.getParser().equals(parser);
        } else {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected SpongeMatcher<T> parse(StringElement element) throws SpongeMatcherParseException {
        if (element instanceof BaseElement) {
            BaseElement<?> base = (BaseElement<?>) element;
            if (base.getParser().equals(parser)) {
                return (SpongeMatcher<T>) base.getParser().parseMatcher(base.getString());
            }
        }

        throw new SpongeMatcherParseException("Invalid " + parser.getName() + " matcher: " + element.getString());
    }

    BaseMatcherParser<T> getParser() {
        return parser;
    }
}
