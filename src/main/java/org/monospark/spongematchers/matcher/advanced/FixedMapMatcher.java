package org.monospark.spongematchers.matcher.advanced;

import java.util.Map;
import java.util.Map.Entry;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.monospark.spongematchers.type.advanced.FixedMapType;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

public final class FixedMapMatcher extends SpongeMatcher<Map<String, Object>> {

    private Map<String, SpongeMatcher<?>> map;

    private FixedMapMatcher(MatcherType<Map<String, Object>> type, Map<String, SpongeMatcher<?>> entries) {
        super(type);
        this.map = entries;
    }

    @Override
    public boolean matches(Map<String, Object> o) {
        for (Entry<String, SpongeMatcher<?>> entry : map.entrySet()) {
            if (!matchesEntry(entry.getKey(), entry.getValue(), o)) {
                return false;
            }
        }
        return true;
    }

    private <T> boolean matchesEntry(String key, SpongeMatcher<T> matcher, Map<String, Object> o) {
        @SuppressWarnings("unchecked")
        T object = (T) o.get(key);

        if (object == null) {
            return false;
        } else if (!matcher.getType().canMatch(object)) {
            return false;
        }

        boolean matches = matcher.matches((T) object);
        return matches;
    }

    @Override
    public String toString() {
        return "{" + Joiner.on(",").withKeyValueSeparator(":").join(map) + "}";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Map<String, SpongeMatcher<?>> map;

        private FixedMapType.Builder typeBuilder;

        private Builder() {
            map = Maps.newHashMap();
            typeBuilder = MatcherType.fixedMap();
        }

        public Builder addMatcher(String key, SpongeMatcher<?> matcher) {
            map.put(key, matcher);
            typeBuilder.addEntry(key, matcher.getType());
            return this;
        }

        public SpongeMatcher<Map<String, Object>> build() {
            return new FixedMapMatcher(typeBuilder.build(), map);
        }
    }
}
