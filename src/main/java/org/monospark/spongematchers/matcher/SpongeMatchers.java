package org.monospark.spongematchers.matcher;

import java.util.Set;

import com.google.common.collect.Sets;

public final class SpongeMatchers {

    private SpongeMatchers() {}
    
    public static <T, M extends SpongeMatcher<T>> M wildcard() {
        @SuppressWarnings("unchecked")
        M matcher = (M) new SpongeMatcher<T>() {

            @Override
            public boolean matches(T o) {
                return true;
            }
        };
        return matcher;
    }
    
    @SafeVarargs
    public static <T> SpongeMatcher<T> amount(SpongeMatcher<T>... matchers) {
        return amount(Sets.newHashSet(matchers));
    }
    
    public static <T> SpongeMatcher<T> amount(Set<SpongeMatcher<T>> matchers) {
        return new SpongeMatcher<T>() {
            @Override
            public boolean matches(T o) {
                for (SpongeMatcher<T> m : matchers) {
                    if (m.matches(o)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }
}
