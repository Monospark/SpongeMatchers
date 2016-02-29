package org.monospark.spongematchers.matcher.complex;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.type.MatcherType;

public final class MultiMatcher extends SpongeMatcher<Object> {

    public static SpongeMatcher<Object> create(MatcherType<?> type, SpongeMatcher<?> matcher) {
        return new MultiMatcher(type, matcher);
    }

    private SpongeMatcher<?> matcher;

    @SuppressWarnings("unchecked")
    private MultiMatcher(MatcherType<?> type, SpongeMatcher<?> matcher) {
        super((MatcherType<Object>) type);
        this.matcher = matcher;
    }

    @Override
    public boolean matches(Object o) {
        if (getType().canMatch(o)) {
            return matchesObject(o, matcher);
        } else {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    private <T> boolean matchesObject(Object o, SpongeMatcher<T> matcher) {
        return matcher.matches((T) o);
    }
}
