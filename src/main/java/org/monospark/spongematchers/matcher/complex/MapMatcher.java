package org.monospark.spongematchers.matcher.complex;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.monospark.spongematchers.matcher.MatcherHelper;
import org.monospark.spongematchers.matcher.SpongeMatcher;

import com.google.common.collect.Sets;

public final class MapMatcher implements SpongeMatcher<Map<String, Object>> {

    private Set<MatcherEntry> entries;
    
    private MapMatcher(Set<MatcherEntry> entries) {
        this.entries = entries;
    }

    @Override
    public boolean matches(Map<String, Object> o) {
        for (MatcherEntry entry : entries) {
            Optional<?> object = Optional.ofNullable(o.get(entry.getKey()));
            if (object.isPresent()) {
                Class<?> objectClass = object.get().getClass();
                if (!entry.getValueClass().isAssignableFrom(objectClass)) {
                    return false;
                }
            }
            
            SpongeMatcher<Optional<?>> matcher = entry.getMatcher();
            boolean matches = matcher.matches(object);
            if (!matches) {
                return false;
            }
        }
        return true;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static final class Builder {
        
        private Set<MatcherEntry> entries;
        
        private Builder() {
            entries = Sets.newHashSet();
        }
        
        public <T> Builder addMatcher(String key, Class<?> valueClass, SpongeMatcher<T> matcher) {
            entries.add(new MatcherEntry(key, valueClass, MatcherHelper.genericWrapper(OptionalMatcher.wrapper(matcher))));
            return this;
        }
        
        public <T> Builder addOptionalMatcher(String key, Class<?> valueClass, SpongeMatcher<Optional<T>> matcher) {
            entries.add(new MatcherEntry(key, valueClass, MatcherHelper.genericWrapper(matcher)));
            return this;
        }
        
        public SpongeMatcher<Map<String, Object>> build() {
            return new MapMatcher(entries);
        }
    }
    
    private static final class MatcherEntry {
        
        private String key;
        
        private Class<?> valueClass;
        
        private SpongeMatcher<Optional<?>> matcher;

        private MatcherEntry(String key, Class<?> valueClass, SpongeMatcher<Optional<?>> matcher) {
            this.key = key;
            this.valueClass = valueClass;
            this.matcher = matcher;
        }

        private String getKey() {
            return key;
        }

        private Class<?> getValueClass() {
            return valueClass;
        }

        private SpongeMatcher<Optional<?>> getMatcher() {
            return matcher;
        }
    }
}
