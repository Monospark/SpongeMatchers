package org.monospark.spongematchers.matcher.advanced;

import java.util.Arrays;
import java.util.List;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.type.MatcherType;

public final class ListMatcher {

    private ListMatcher() {}

    public static <T> SpongeMatcher<List<T>> none(MatcherType<T> type) {
        return new SpongeMatcher<List<T>>(MatcherType.list(type)) {

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
        return new SpongeMatcher<List<T>>(MatcherType.list(matcher.getType())) {

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
        if (matchers.size() == 0) {
            throw new IllegalArgumentException("Matcher list must contain at least one matcher");
        }
        MatcherType<?> type = matchers.get(0).getType();
        for (int i = 1; i < matchers.size(); i++) {
            if (!matchers.get(i).getType().equals(type)) {
                throw new IllegalArgumentException("All matchers in the list must have the same matcher type");
            }
        }

        return new SpongeMatcher<List<T>>(MatcherType.list(matchers.get(0).getType())) {

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
