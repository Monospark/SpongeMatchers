package org.monospark.spongematchers.matcher;

public final class SpongeMatchers {

    private SpongeMatchers() {}
    
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
}
