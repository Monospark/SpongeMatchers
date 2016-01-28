package org.monospark.spongematchers.matcher.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.MapMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.data.property.PropertyHolder;

public final class PropertyHolderMatcher extends SpongeObjectMatcher<PropertyHolder> {

    public static SpongeMatcher<PropertyHolder> create(SpongeMatcher<Map<String, Object>> matcher) {
        return new PropertyHolderMatcher(matcher);
    }
    
    private PropertyHolderMatcher(SpongeMatcher<Map<String, Object>> matcher) {
        super(matcher);
    }

    @Override
    protected void fillMap(PropertyHolder o, Map<String, Object> map) {
        o.getApplicableProperties().stream().forEach(p -> map.put(p.getClass().getName(), p.getValue()));
    }
       
    public static Builder builder() {
        return new Builder();
    }
    
    public static final class Builder {
        
        private MapMatcher.Builder builder;
        
        private Builder() {
            builder = MapMatcher.builder();
        }
        
        public Builder addBooleanProperty(String name, SpongeMatcher<Boolean> matcher) {
            builder.addMatcher(name, MatcherType.BOOLEAN, matcher);
            return this;
        }
        
        public Builder addIntegerProperty(String name, SpongeMatcher<Long> matcher) {
            builder.addMatcher(name, MatcherType.INTEGER, matcher);
            return this;
        }
        
        public Builder addFloatingPointProperty(String name, SpongeMatcher<Double> matcher) {
            builder.addMatcher(name, MatcherType.FLOATING_POINT, matcher);
            return this;
        }
        
        public SpongeMatcher<PropertyHolder> build() {
            return new PropertyHolderMatcher(builder.build());
        }
    }
}
