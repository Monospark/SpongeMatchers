package org.monospark.spongematchers;

public interface SpongeMatcher<T> {

    public static <T, M extends SpongeMatcher<T>> M wildcard() {
        @SuppressWarnings("unchecked")
        M matcher = (M) new SpongeMatcher<T>() {

            @Override
            public boolean matches(T o) {
                return true;
            }
        };
        return matcher;
    }
    
    boolean matches(T o);
}
