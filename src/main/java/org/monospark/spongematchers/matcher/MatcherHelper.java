package org.monospark.spongematchers.matcher;

public final class MatcherHelper {

    
    public static <T,U> SpongeMatcher<T> genericWrapper(SpongeMatcher<U> matcher) {
        return new SpongeMatcher<T>() {

            @SuppressWarnings("unchecked")
            @Override
            public boolean matches(T o) {
                return matcher.matches((U) o);
            }
        };
    }
}
