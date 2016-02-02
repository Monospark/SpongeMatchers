package org.monospark.spongematchers.matcher;

public interface SpongeMatcher<T> {

    boolean matches(T o);

    static <T> SpongeMatcher<T> or(SpongeMatcher<T> matcher1, SpongeMatcher<T> matcher2) {
        return new SpongeMatcher<T>() {

            @Override
            public boolean matches(T o) {
                return matcher1.matches(o) || matcher2.matches(o);
            }

            @Override
            public String toString() {
                return matcher1.toString() + "|" + matcher2.toString();
            }
        };
    }

    static <T> SpongeMatcher<T> and(SpongeMatcher<T> matcher1, SpongeMatcher<T> matcher2) {
        return new SpongeMatcher<T>() {

            @Override
            public boolean matches(T o) {
                return matcher1.matches(o) && matcher2.matches(o);
            }

            @Override
            public String toString() {
                return matcher1.toString() + "&" + matcher2.toString();
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
