package org.monospark.spongematchers.type;

import java.util.Optional;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.OptionalMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.LiteralElement;
import org.monospark.spongematchers.parser.element.LiteralElement.Type;
import org.monospark.spongematchers.parser.element.StringElement;

public final class OptionalType<T> extends MatcherType<Optional<T>> {

    private MatcherType<T> type;

    OptionalType(MatcherType<T> type) {
        super("optional " + type.getName());
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
    protected boolean canParse(StringElement element, boolean deep) {
        boolean isEmptyOptional = element instanceof LiteralElement
                && ((LiteralElement) element).getType() == Type.EMPTY;
        if (deep && !isEmptyOptional) {
            return type.canParse(element, true);
        } else {
            return true;
        }
    }

    @Override
    protected SpongeMatcher<Optional<T>> parse(StringElement element) throws SpongeMatcherParseException {
        if (element instanceof LiteralElement && ((LiteralElement) element).getType() == Type.EMPTY) {
            return OptionalMatcher.matchEmpty();
        } else {
            return OptionalMatcher.wrapper(type.parseMatcher(element));
        }
    }
}
