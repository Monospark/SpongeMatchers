package org.monospark.spongematchers.matcher.complex;

import java.util.Optional;

import org.monospark.spongematchers.matcher.SpongeMatcher;

public final class OptionalMatcher {

    private OptionalMatcher() {}
    
    public static <T> SpongeMatcher<Optional<T>> matchEmpty() {
        return new SpongeMatcher<Optional<T>>() {

            @Override
            public boolean matches(Optional<T> o) {
                return !o.isPresent();
            }

            @Override
            public String toString() {
               return "empty";
            }
        };
    }
    
    public static <T> SpongeMatcher<Optional<T>> wrapper(SpongeMatcher<T> matcher) {
        return new SpongeMatcher<Optional<T>>() {

            @Override
            public boolean matches(Optional<T> o) {
                return o.isPresent() ? matcher.matches(o.get()) : false;
            }

            @Override
            public String toString() {
               return matcher.toString() + "(optional)";
            }
        };
    }
}
