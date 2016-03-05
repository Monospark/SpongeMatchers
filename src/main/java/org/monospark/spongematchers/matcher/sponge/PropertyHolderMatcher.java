package org.monospark.spongematchers.matcher.sponge;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.advanced.FixedMapMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.monospark.spongematchers.util.PatternBuilder;
import org.spongepowered.api.data.Property;
import org.spongepowered.api.data.property.PropertyHolder;

public final class PropertyHolderMatcher extends SpongeObjectMatcher<PropertyHolder> {

    private static final Pattern PROPERTY_NAME_PATTERN = new PatternBuilder()
            .appendCapturingPart(".", "firstChar")
            .appendCapturingPart(".+?", "rest")
            .appendNonCapturingPart("Property")
            .build();

    public static SpongeMatcher<PropertyHolder> create(SpongeMatcher<Map<String, Object>> matcher) {
        return new PropertyHolderMatcher(matcher);
    }

    private PropertyHolderMatcher(SpongeMatcher<Map<String, Object>> matcher) {
        super(MatcherType.PROPERTY_HOLDER, matcher);
    }

    @Override
    protected void fillMap(PropertyHolder o, Map<String, Object> map) {
        for (Property<?, ?> property : o.getApplicableProperties()) {
            map.put(convertPropertyName(property.getKey().toString()), makeMatchable(property.getValue()));
        }
    }

    private String convertPropertyName(String name) {
        Matcher matcher = PROPERTY_NAME_PATTERN.matcher(name);
        matcher.matches();
        return matcher.group("firstChar").toLowerCase() + matcher.group("rest");
    }

    private Object makeMatchable(Object o) {
        if (o instanceof Byte) {
            return ((Byte) o).longValue();
        } else if (o instanceof Short) {
            return ((Short) o).longValue();
        } else if (o instanceof Integer) {
            return ((Integer) o).longValue();
        } else if (o instanceof Float) {
            return ((Float) o).doubleValue();
        }

        if (o instanceof Boolean || o instanceof Long || o instanceof Double) {
            return o;
        } else {
            return o.toString().toLowerCase();
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private FixedMapMatcher.Builder builder;

        private Builder() {
            builder = FixedMapMatcher.builder();
        }

        public Builder addBooleanProperty(String name, SpongeMatcher<Boolean> matcher) {
            builder.addMatcher(name, matcher);
            return this;
        }

        public Builder addIntegerProperty(String name, SpongeMatcher<Long> matcher) {
            builder.addMatcher(name, matcher);
            return this;
        }

        public Builder addFloatingPointProperty(String name, SpongeMatcher<Double> matcher) {
            builder.addMatcher(name, matcher);
            return this;
        }

        public <T> Builder addAbstractProperty(String name, MatcherType<T> type, SpongeMatcher<T> matcher) {
            builder.addMatcher(name, matcher);
            return this;
        }

        public SpongeMatcher<PropertyHolder> build() {
            return new PropertyHolderMatcher(builder.build());
        }
    }
}
