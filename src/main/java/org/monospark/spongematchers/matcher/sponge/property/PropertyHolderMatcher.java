package org.monospark.spongematchers.matcher.sponge.property;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.sponge.property.PropertyMatcher.Type;
import org.spongepowered.api.data.Property;
import org.spongepowered.api.data.property.PropertyHolder;

import com.google.common.collect.Maps;

public final class PropertyHolderMatcher implements SpongeMatcher<PropertyHolder> {
    
    private Map<String, PropertyMatcher<?>> properties;

    private PropertyHolderMatcher(Map<String, PropertyMatcher<?>> properties) {
        this.properties = properties;
    }

    @Override
    public boolean matches(PropertyHolder o) {
        for (Entry<String, PropertyMatcher<?>> entry : properties.entrySet()) {
            Optional<Property<?,?>> property = o.getApplicableProperties().stream()
                    .filter(p -> p.getKey().equals(entry.getKey()))
                    .findAny();
            if (!property.isPresent()) {
                return false;
            }
            
            Object value = property.get().getValue();
            if (entry.getValue().getType().getClass().equals(value.getClass()) &&
                    !matchesProperty(entry.getValue(), value)) {
                return false;
            }
        }
        return true;
    }
    
    @SuppressWarnings("unchecked")
    private <T> boolean matchesProperty(PropertyMatcher<T> matcher, Object value) {
        return matcher.getMatcher().matches((T) value);
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static final class Builder {
        
        private Map<String, PropertyMatcher<?>> properties;
        
        private Builder() {
            properties = Maps.newHashMap();
        }
        
        public Builder addBooleanProperty(String name, SpongeMatcher<Boolean> matcher) {
            properties.put(name, new PropertyMatcher<>(Type.BOOLEAN, matcher));
            return this;
        }
        
        public Builder addIntegerProperty(String name, SpongeMatcher<Integer> matcher) {
            properties.put(name, new PropertyMatcher<>(Type.INTEGER, matcher));
            return this;
        }
        
        public Builder addDoubleProperty(String name, SpongeMatcher<Double> matcher) {
            properties.put(name, new PropertyMatcher<>(Type.DOUBLE, matcher));
            return this;
        }
        
        public SpongeMatcher<PropertyHolder> build() {
            return new PropertyHolderMatcher(properties);
        }
    }
}
