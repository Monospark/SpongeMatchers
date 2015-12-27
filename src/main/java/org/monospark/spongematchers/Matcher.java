package org.monospark.spongematchers;

public interface Matcher<T> {

    public static <T, M extends Matcher<T>> M wildcard() {
        @SuppressWarnings("unchecked")
        M matcher = (M) new Matcher<T>() {

            @Override
            public boolean matches(T o) {
                return true;
            }
        };
        return matcher;
    }
    
    boolean matches(T o);
}
