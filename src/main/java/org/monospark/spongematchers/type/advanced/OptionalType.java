package org.monospark.spongematchers.type.advanced;

import java.util.Optional;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.advanced.OptionalMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.LiteralElement;
import org.monospark.spongematchers.parser.element.LiteralElement.Type;
import org.monospark.spongematchers.type.MatcherType;
import org.monospark.spongematchers.parser.element.StringElement;

public final class OptionalType<T> extends MatcherType<Optional<T>> {

    private MatcherType<T> type;

    public OptionalType(MatcherType<T> type) {
        this.type = type;
    }

    @Override
    public boolean canMatch(Object o) {
        if (!(o instanceof Optional)) {
            return false;
        }

        Optional<?> optional = (Optional<?>) o;
        return optional.isPresent() ? type.canMatch(optional.get()) : true;
    }

    @Override
    protected boolean canParse(StringElement element) {
        boolean isAbsentOptional = element instanceof LiteralElement
                && ((LiteralElement) element).getType() == Type.ABSENT;
        return !isAbsentOptional ? type.canParseMatcher(element) : true;
    }

    @Override
    protected SpongeMatcher<Optional<T>> parse(StringElement element) throws SpongeMatcherParseException {
        if (element instanceof LiteralElement && ((LiteralElement) element).getType() == Type.ABSENT) {
            return OptionalMatcher.matchEmpty(type);
        } else {
            return OptionalMatcher.wrapper(type.parseMatcher(element));
        }
    }
}
