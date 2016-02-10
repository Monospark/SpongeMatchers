package org.monospark.spongematchers.matcher;

import java.util.Set;

public interface SpongeMatcher<T> {

    boolean matches(T o);

    static <T> SpongeMatcher<T> or(Set<SpongeMatcher<T>> matchers) {
        return new SpongeMatcher<T>() {

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

    static <T> SpongeMatcher<T> and(Set<SpongeMatcher<T>> matchers) {
        return new SpongeMatcher<T>() {

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

    static <T> SpongeMatcher<T> not(SpongeMatcher<T> matcher) {
        return new SpongeMatcher<T>() {

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

    static <T, M extends SpongeMatcher<T>> M wildcard() {
        @SuppressWarnings("unchecked")
        M matcher = (M) new SpongeMatcher<T>() {

            @Override
            public boolean matches(T o) {
                return true;
            }

            @Override
            public String toString() {
                return "*";
            }
        };
        return matcher;
    }
}
