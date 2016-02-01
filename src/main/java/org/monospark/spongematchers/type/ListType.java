package org.monospark.spongematchers.type;

import java.util.List;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.ListMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.ListElement;
import org.monospark.spongematchers.parser.element.PatternElement;
import org.monospark.spongematchers.parser.element.PatternElement.Type;
import org.monospark.spongematchers.parser.element.StringElement;

import com.google.common.collect.Lists;

public final class ListType<T> extends MatcherType<List<T>> {

    private MatcherType<T> type;

    ListType(MatcherType<T> type) {
        super(type.getName() + " list");
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
    protected boolean canParse(StringElement element, boolean deep) {
        if (element instanceof ListElement) {
            if (!deep) {
                return true;
            }
            
            ListElement list = (ListElement) element;
            for (StringElement e : list.getElements()) {
                if (!type.canParse(e, true)) {
                    return false;
                }
            }
            return true;
        } else if(element instanceof PatternElement) {
            PatternElement pattern = (PatternElement) element;
            return deep ? type.canParse(pattern.getElement(), true) : true;
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
                matchers.add(type.parseMatcher(listElement));
            }
            return ListMatcher.matchExactly(matchers);
        } else if(element instanceof PatternElement) {
            PatternElement pattern = (PatternElement) element;
            if (pattern.getType().equals(Type.LIST_MATCH_ANY)) {
                SpongeMatcher<T> matcher = type.parseMatcher(pattern.getElement());
                return ListMatcher.matchAny(matcher);
            } else if(pattern.getType().equals(Type.LIST_MATCH_ALL)) {
                SpongeMatcher<T> matcher = type.parseMatcher(pattern.getElement());
                return ListMatcher.matchAll(matcher);
            }
        }
        throw new AssertionError();
    }
}
