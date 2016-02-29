package org.monospark.spongematchers.matcher.complex;

import java.util.Optional;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.type.MatcherType;

public final class OptionalMatcher {

    private OptionalMatcher() {}

    public static <T> SpongeMatcher<Optional<T>> matchEmpty(MatcherType<T> type) {
        return new SpongeMatcher<Optional<T>>(MatcherType.optional(type)) {

            @Override
            public boolean matches(Optional<T> o) {
                return !o.isPresent();
            }

            @Override
            public String toString() {
                return "absent";
            }
        };
    }

    public static <T> SpongeMatcher<Optional<T>> wrapper(SpongeMatcher<T> matcher) {
        return new SpongeMatcher<Optional<T>>(MatcherType.optional(matcher.getType())) {

            @Override
            public boolean matches(Optional<T> o) {
                return o.isPresent() ? matcher.matches(o.get()) : false;
            }

            @Override
            public String toString() {
                return matcher.toString();
            }
        };
    }
}
