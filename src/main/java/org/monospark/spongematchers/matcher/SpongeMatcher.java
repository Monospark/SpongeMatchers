package org.monospark.spongematchers.matcher;

import java.util.Set;

import org.monospark.spongematchers.type.MatcherType;

public abstract class SpongeMatcher<T> {

    private MatcherType<T> type;

    protected SpongeMatcher(MatcherType<T> type) {
        this.type = type;
    }

    public abstract boolean matches(T o);

    public final MatcherType<T> getType() {
        return type;
    }

    public static <T> SpongeMatcher<T> or(Set<SpongeMatcher<T>> matchers) {
        if (matchers.size() == 0) {
            throw new IllegalArgumentException("Matcher set must contain at least one matcher");
        }
        MatcherType<?> type = matchers.iterator().next().getType();
        for (SpongeMatcher<?> matcher : matchers) {
            if (!matcher.getType().equals(type)) {
                throw new IllegalArgumentException("All matchers in the set must have the same matcher type");
            }
        }

        return new SpongeMatcher<T>(matchers.iterator().next().getType()) {

            @Override
            public boolean matches(T o) {
                for (SpongeMatcher<T> matcher : matchers) {
                    if (matcher.matches(o)) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public String toString() {
                StringBuilder builder = new StringBuilder();
                for (SpongeMatcher<T> matcher : matchers) {
                    builder.append(matcher.toString()).append("|");
                }
                builder.deleteCharAt(builder.length() - 1);
                return builder.toString();
            }
        };
    }

    public static <T> SpongeMatcher<T> and(Set<SpongeMatcher<T>> matchers) {
        if (matchers.size() == 0) {
            throw new IllegalArgumentException("Matcher set must contain at least one matcher");
        }
        MatcherType<?> type = matchers.iterator().next().getType();
        for (SpongeMatcher<?> matcher : matchers) {
            if (!matcher.getType().equals(type)) {
                throw new IllegalArgumentException("All matchers in the set must have the same matcher type");
            }
        }

        return new SpongeMatcher<T>(matchers.iterator().next().getType()) {

            @Override
            public boolean matches(T o) {
                for (SpongeMatcher<T> matcher : matchers) {
                    if (!matcher.matches(o)) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public String toString() {
                StringBuilder builder = new StringBuilder();
                for (SpongeMatcher<T> matcher : matchers) {
                    builder.append(matcher.toString()).append("&");
                }
                builder.deleteCharAt(builder.length() - 1);
                return builder.toString();
            }
        };
    }

    public static <T> SpongeMatcher<T> not(SpongeMatcher<T> matcher) {
        return new SpongeMatcher<T>(matcher.getType()) {

            @Override
            public boolean matches(T o) {
                return !matcher.matches(o);
            }

            @Override
            public String toString() {
                return "!" + matcher.toString();
            }
        };
    }

    public static <T> SpongeMatcher<T> wildcard(MatcherType<T> type) {
        return new SpongeMatcher<T>(type) {

            @Override
            public boolean matches(T o) {
                return true;
            }

            @Override
            public String toString() {
                return "*";
            }
        };
    }
}
