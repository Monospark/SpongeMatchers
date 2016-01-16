package org.monospark.spongematchers.matcher.base;

import java.util.Optional;

import org.monospark.spongematchers.matcher.SpongeMatcher;

public final class OptionalMatcher {

    private OptionalMatcher() {}
    
    public static <T> SpongeMatcher<Optional<T>> falseOnEmpty(SpongeMatcher<T> matcher) {
        return create(matcher, false); 
    }
    
    public static <T> SpongeMatcher<Optional<T>> trueOnEmpty(SpongeMatcher<T> matcher) {
        return create(matcher, true); 
    }
    
    public static <T> SpongeMatcher<Optional<T>> create(SpongeMatcher<T> matcher, boolean matchOnEmpty) {
        return new SpongeMatcher<Optional<T>>() {
            @Override
            public boolean matches(Optional<T> o) {
                if (!o.isPresent()) {
                    return matchOnEmpty;
                } else {
                    return matcher.matches(o.get());
                }
            }
        };
    }
}
