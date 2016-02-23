package org.monospark.spongematchers.type;

import java.util.List;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.ListMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.ListElement;
import org.monospark.spongematchers.parser.element.LiteralElement;
import org.monospark.spongematchers.parser.element.PatternElement;
import org.monospark.spongematchers.parser.element.PatternElement.Type;
import org.monospark.spongematchers.parser.element.StringElement;

import com.google.common.collect.Lists;

public final class ListType<T> extends MatcherType<List<T>> {

    private MatcherType<T> type;

    ListType(MatcherType<T> type) {
        this.type = type;
    }

    @Override
    public boolean canMatch(Object o) {
        if (!(o instanceof List)) {
            return false;
        }

        List<?> list = (List<?>) o;
        for (Object element : list) {
            if (!type.canMatch(element)) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected boolean checkElement(StringElement element) {
        if (element instanceof ListElement) {
            for (StringElement listElement : ((ListElement) element).getElements()) {
                if (!type.acceptsElement(listElement)) {
                    return false;
                }
            }
            return true;
        } else if (element instanceof PatternElement) {
            PatternElement pattern = (PatternElement) element;
            return pattern.getType() == Type.LIST_MATCH_ALL || pattern.getType() == Type.LIST_MATCH_ANY;
        } else if (element instanceof LiteralElement) {
            return ((LiteralElement) element).getType() == LiteralElement.Type.NONE;
        } else {
            return false;
        }
    }

    @Override
    protected SpongeMatcher<List<T>> parse(StringElement element) throws SpongeMatcherParseException {
        if (element instanceof ListElement) {
            ListElement list = (ListElement) element;
            List<SpongeMatcher<T>> matchers = Lists.newArrayList();
            for (StringElement listElement : list.getElements()) {
                matchers.add(parseElement(listElement));
            }
            return ListMatcher.matchExactly(matchers);
        } else if (element instanceof PatternElement) {
            PatternElement pattern = (PatternElement) element;
            if (pattern.getType().equals(Type.LIST_MATCH_ANY)) {
                return ListMatcher.matchAny(parseElement(pattern.getElement()));
            } else if (pattern.getType().equals(Type.LIST_MATCH_ALL)) {
                return ListMatcher.matchAll(parseElement(pattern.getElement()));
            }
        } else if (element instanceof LiteralElement) {
            LiteralElement literal = (LiteralElement) element;
            if (literal.getType() == LiteralElement.Type.NONE) {
                return ListMatcher.none();
            }
        }
        throw new SpongeMatcherParseException("Invalid list matcher: " + element.getString());
    }

    private SpongeMatcher<T> parseElement(StringElement element) throws SpongeMatcherParseException {
        try {
            return type.parseMatcher(element);
        } catch (SpongeMatcherParseException e) {
            throw new SpongeMatcherParseException("Couldn't parse list matcher: "
                    + element.getString(), e);
        }
    }
}
