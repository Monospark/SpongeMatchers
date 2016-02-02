package org.monospark.spongematchers.matcher.base;

import org.monospark.spongematchers.matcher.SpongeMatcher;

public final class BooleanMatcher implements SpongeMatcher<Boolean> {

    public static SpongeMatcher<Boolean> trueValue() {
        return new BooleanMatcher(true);
    }

    public static SpongeMatcher<Boolean> falseValue() {
        return new BooleanMatcher(false);
    }

    private boolean value;

    private BooleanMatcher(boolean value) {
        this.value = value;
    }

    @Override
    public boolean matches(Boolean o) {
        return o.booleanValue() == value;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }
}
