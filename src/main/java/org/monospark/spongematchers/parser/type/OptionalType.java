package org.monospark.spongematchers.parser.type;

import java.util.Optional;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.OptionalMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.EmptyElement;
import org.monospark.spongematchers.parser.element.StringElement;

public final class OptionalType<T> extends MatcherType<Optional<T>> {

    private MatcherType<T> type;

    OptionalType(MatcherType<T> type) {
        this.type = type;
    }

    @Override
    public SpongeMatcher<Optional<T>> parse(StringElement element) throws SpongeMatcherParseException {
        if (element instanceof EmptyElement) {
            return OptionalMatcher.matchEmpty();
        } else {
            return OptionalMatcher.wrapper(type.parseMatcher(element));
        }
    }

}