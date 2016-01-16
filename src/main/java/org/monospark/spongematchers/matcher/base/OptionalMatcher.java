package org.monospark.spongematchers.matcher.base;

import java.util.Optional;

import org.monospark.spongematchers.matcher.SpongeMatcher;

public final class OptionalMatcher<T> implements SpongeMatcher<Optional<T>> {

    public static <T> SpongeMatcher<Optional<T>> falseOnEmpty(SpongeMatcher<T> matcher) {
        return create(matcher, false); 
    }
    
    public static <T> SpongeMatcher<Optional<T>> trueOnEmpty(SpongeMatcher<T> matcher) {
        return create(matcher, true); 
    }
    
    public static <T> SpongeMatcher<Optional<T>> create(SpongeMatcher<T> matcher, boolean matchOnEmpty) {
        return new OptionalMatcher<T>(matcher, matchOnEmpty);
    }
    
    private boolean matchOnEmpty;
    
    private SpongeMatcher<T> matcher;

    private OptionalMatcher(SpongeMatcher<T> matcher, boolean matchOnEmpty) {
        this.matcher = matcher;
        this.matchOnEmpty = matchOnEmpty;
    }

    @Override
    public boolean matches(Optional<T> o) {
        return !o.isPresent() ? matchOnEmpty : matcher.matches(o.get());
    }
}
