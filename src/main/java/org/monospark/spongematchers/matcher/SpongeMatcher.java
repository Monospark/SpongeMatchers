package org.monospark.spongematchers.matcher;

public interface SpongeMatcher<T> {

    boolean matches(T o);
}
