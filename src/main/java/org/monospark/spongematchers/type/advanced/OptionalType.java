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
        if (element instanceof LiteralElement) {
            LiteralElement literal = (LiteralElement) element;
            return literal.getType() == Type.ABSENT || literal.getType() == Type.EXISTENT;
        }
        return type.canParseMatcher(element);
    }

    @Override
    protected SpongeMatcher<Optional<T>> parse(StringElement element) throws SpongeMatcherParseException {
        if (element instanceof LiteralElement) {
            LiteralElement literal = (LiteralElement) element;
            if (literal.getType() == Type.ABSENT) {
                return OptionalMatcher.absent(type);
            } else if (literal.getType() == Type.EXISTENT) {
                return OptionalMatcher.existent(type);
            }
        }

        return OptionalMatcher.wrapper(type.parseMatcher(element));
    }
}
