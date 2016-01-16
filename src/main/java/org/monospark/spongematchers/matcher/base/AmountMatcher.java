package org.monospark.spongematchers.matcher.base;

import java.util.Set;

import org.monospark.spongematchers.matcher.SpongeMatcher;

import com.google.common.collect.ImmutableSet;

public final class AmountMatcher<T> implements SpongeMatcher<T> {

    @SuppressWarnings("unchecked")
    public static <T> SpongeMatcher<T> create(SpongeMatcher<T>... matchers) {
        return new AmountMatcher<>(ImmutableSet.copyOf(matchers));
    }
    
    public static <T> SpongeMatcher<T> create(Set<SpongeMatcher<T>> matchers) {
        return new AmountMatcher<>(ImmutableSet.copyOf(matchers));
    }
    
    private Set<SpongeMatcher<T>> matchers;

    private AmountMatcher(Set<SpongeMatcher<T>> matchers) {
        this.matchers = matchers;
    }

    @Override
    public boolean matches(T o) {
        for (SpongeMatcher<T> m : matchers) {
            if (m.matches(o)) {
                return true;
            }
        }
        return false;
    }
}
