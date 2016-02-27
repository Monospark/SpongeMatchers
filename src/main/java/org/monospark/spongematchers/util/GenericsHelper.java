package org.monospark.spongematchers.util;

public final class GenericsHelper {

    private GenericsHelper() {}

    @SuppressWarnings("unchecked")
    public static <T, U> T genericWrapper(U object) {
        return (T) object;
    }
}
