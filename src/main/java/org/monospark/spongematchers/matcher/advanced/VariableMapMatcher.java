package org.monospark.spongematchers.matcher.advanced;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.type.MatcherType;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

public final class VariableMapMatcher extends SpongeMatcher<Map<String, Object>> {

    private Map<String, SpongeMatcher<Optional<?>>> map;

    private VariableMapMatcher(MatcherType<Map<String, Object>> type, Map<String, SpongeMatcher<Optional<?>>> entries) {
        super(type);
        this.map = entries;
    }

    @Override
    public boolean matches(Map<String, Object> o) {
        for (Entry<String, SpongeMatcher<Optional<?>>> entry : map.entrySet()) {
            if (!matchesEntry(entry.getKey(), entry.getValue(), o)) {
                return false;
            }
        }
        return true;
    }

    private boolean matchesEntry(String key, SpongeMatcher<Optional<?>> matcher, Map<String, Object> o) {
        Optional<?> object = Optional.ofNullable(o.get(key));

        if (!matcher.getType().canMatch(object)) {
            return false;
        }

        boolean matches = matcher.matches(object);
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

        private Map<String, SpongeMatcher<Optional<?>>> map;


        private Builder() {
            map = Maps.newHashMap();
        }

        public Builder addMatcher(String key, SpongeMatcher<Optional<?>> matcher) {
            map.put(key, matcher);
            return this;
        }

        public SpongeMatcher<Map<String, Object>> build(MatcherType<?> type) {
            return new VariableMapMatcher(MatcherType.variableMap(type), map);
        }
    }
}
