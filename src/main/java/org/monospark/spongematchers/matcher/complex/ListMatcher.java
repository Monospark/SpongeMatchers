package org.monospark.spongematchers.matcher.complex;

import java.util.Arrays;
import java.util.List;

import org.monospark.spongematchers.matcher.SpongeMatcher;

public final class ListMatcher {

    private ListMatcher() {}

    public static <T> SpongeMatcher<List<T>> none() {
        return new SpongeMatcher<List<T>>() {

            @Override
            public boolean matches(List<T> o) {
                return o.size() == 0;
            }
        };
    }

    public static <T> SpongeMatcher<List<T>> matchAny(SpongeMatcher<T> matcher) {
        return createFromSingleMatcher(matcher, false);
    }

    public static <T> SpongeMatcher<List<T>> matchAll(SpongeMatcher<T> matcher) {
        return createFromSingleMatcher(matcher, true);
    }

    public static <T> SpongeMatcher<List<T>> createFromSingleMatcher(SpongeMatcher<T> matcher, boolean allMatch) {
        return new SpongeMatcher<List<T>>() {

            @Override
            public boolean matches(List<T> o) {
                for (T element : o) {
                    boolean matches = matcher.matches(element);
                    if (matches && !allMatch) {
                        return true;
                    } else if (!matches && allMatch) {
                        return false;
                    }
                }
                return allMatch;
            }

            @Override
            public String toString() {
                return (allMatch ? "all:" : "any:") + matcher.toString();
            }
        };
    }

    @SuppressWarnings("unchecked")
    public static <T> SpongeMatcher<List<T>> matchExactly(SpongeMatcher<T>... matchers) {
        return matchExactly(Arrays.asList(matchers));
    }

    public static <T> SpongeMatcher<List<T>> matchExactly(List<SpongeMatcher<T>> matchers) {
        return new SpongeMatcher<List<T>>() {

            @Override
            public boolean matches(List<T> o) {
                if (o.size() != matchers.size()) {
                    return false;
                }

                for (int i = 0; i < matchers.size(); i++) {
                    if (!matchers.get(i).matches(o.get(i))) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public String toString() {
                return matchers.toString();
            }
        };
    }
}
