package org.monospark.spongematchers.parser.type;

import java.util.List;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.ListMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.ListElement;
import org.monospark.spongematchers.parser.element.PatternElement;
import org.monospark.spongematchers.parser.element.PatternElementParser.Type;
import org.monospark.spongematchers.parser.element.StringElement;

import com.google.common.collect.Lists;

public final class ListType<T> extends MatcherType<List<T>> {

    private MatcherType<T> type;

    ListType(MatcherType<T> type) {
        this.type = type;
    }

    @Override
    SpongeMatcher<List<T>> parse(StringElement element) throws SpongeMatcherParseException {
        if (element instanceof ListElement) {
            ListElement list = (ListElement) element;
            List<SpongeMatcher<T>> matchers = Lists.newArrayList();
            for (StringElement listElement : list.getElements()) {
                matchers.add(type.parseMatcher(listElement));
            }
            return ListMatcher.matchExactly(matchers);
        } else if(element instanceof PatternElement) {
            PatternElement pattern = (PatternElement) element;
            if (pattern.getType().equals(Type.LIST_MATCH_ANY)) {
                SpongeMatcher<T> matcher = type.parseMatcher(pattern);
                return ListMatcher.matchAny(matcher);
            } else if(pattern.getType().equals(Type.LIST_MATCH_ALL)) {
                SpongeMatcher<T> matcher = type.parseMatcher(pattern);
                return ListMatcher.matchAll(matcher);
            }
        }
        
        throw new SpongeMatcherParseException("The element must be a list matcher");
    }
}