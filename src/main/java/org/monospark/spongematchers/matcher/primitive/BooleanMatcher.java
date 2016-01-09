package org.monospark.spongematchers.matcher.primitive;

import org.monospark.spongematchers.matcher.SpongeMatcher;

public final class BooleanMatcher implements SpongeMatcher<Boolean> {

    public static SpongeMatcher<Boolean> trueOnly() {
        return new BooleanMatcher(false, true);
    }
    
    public static SpongeMatcher<Boolean> falseOnly() {
        return new BooleanMatcher(true, false);
    }
    
    private boolean matchOnFalse;
    
    private boolean matchOnTrue;

    private BooleanMatcher(boolean matchOnFalse, boolean matchOnTrue) {
        this.matchOnFalse = matchOnFalse;
        this.matchOnTrue = matchOnTrue;
    }

    @Override
    public boolean matches(Boolean o) {
        return o.booleanValue() ? matchOnTrue : matchOnFalse;
    }
}
