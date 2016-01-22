package org.monospark.spongematchers.matcher.complex;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import org.monospark.spongematchers.matcher.SpongeMatcher;

public final class CompoundMatcher<T> implements SpongeMatcher<T> {

    private Map<SpongeMatcher<?>, Function<T, ?>> matchers;
 
    private CompoundMatcher(Map<SpongeMatcher<?>, Function<T, ?>> matchers) {
        this.matchers = matchers;
    }

    @Override
    public boolean matches(T o) {
        for (Entry<SpongeMatcher<?>, Function<T, ?>> matcher : matchers.entrySet()) {
            boolean matches = matches(matcher.getKey(), (Object) matcher.getValue().apply(o));
            if (!matches) {
                return false;
            }
        }
        return true;
    }
    
    @SuppressWarnings("unchecked")
    private <O> boolean matches(SpongeMatcher<O> matcher, Object o) {
        return matcher.matches((O) o);
    }
    
    public static <T> Builder<T> builder() {
        return new Builder<>();
    }
    
    public static final class Builder<T> {
        
        private Map<SpongeMatcher<?>, Function<T, ?>> matchers;

        private Builder() {
            this.matchers = new HashMap<SpongeMatcher<?>, Function<T, ?>>();
        }
        
        public <M> Builder<T> addMatcher(SpongeMatcher<M> matcher, Function<T, M> function) {
            matchers.put(matcher, function);
            return this;
        }

        public CompoundMatcher<T> build() {
            return new CompoundMatcher<>(matchers);
        }
    }
}
