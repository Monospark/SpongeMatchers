package org.monospark.spongematchers.matcher.complex;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.monospark.spongematchers.matcher.MatcherHelper;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.type.MatcherType;

import com.google.common.collect.Sets;

public final class MapMatcher implements SpongeMatcher<Map<String, Object>> {

    private Set<MatcherEntry<?>> entries;
    
    private MapMatcher(Set<MatcherEntry<?>> entries) {
        this.entries = entries;
    }

    @Override
    public boolean matches(Map<String, Object> o) {
        for (MatcherEntry<?> entry : entries) {
            if (!matchesEntry(entry, o)) {
                return false;
            }
        }
        return true;
    }
    
    private <T> boolean matchesEntry(MatcherEntry<T> entry, Map<String, Object> o) {
        @SuppressWarnings("unchecked")
        Optional<T> object = (Optional<T>) Optional.ofNullable(o.get(entry.getKey()));
        if (object.isPresent()) {
            if (!entry.getType().canMatch(object.get())) {
                return false;
            }
        }
        
        SpongeMatcher<Optional<T>> matcher = entry.getMatcher();
        boolean matches = matcher.matches(object);
        return matches;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static final class Builder {
        
        private Set<MatcherEntry<?>> entries;
        
        private Builder() {
            entries = Sets.newHashSet();
        }
        
        public <T> Builder addMatcher(String key, MatcherType<T> type, SpongeMatcher<T> matcher) {
            entries.add(new MatcherEntry<T>(key, type,
                    MatcherHelper.genericWrapper(OptionalMatcher.wrapper(matcher))));
            return this;
        }
        
        public <T> Builder addOptionalMatcher(String key, MatcherType<T> type, SpongeMatcher<Optional<T>> matcher) {
            entries.add(new MatcherEntry<T>(key, type, MatcherHelper.genericWrapper(matcher)));
            return this;
        }
        
        public SpongeMatcher<Map<String, Object>> build() {
            return new MapMatcher(entries);
        }
    }
    
    private static final class MatcherEntry<T> {
        
        private String key;
        
        private MatcherType<Optional<T>> type;
        
        private SpongeMatcher<Optional<T>> matcher;

        private MatcherEntry(String key, MatcherType<T> type, SpongeMatcher<Optional<T>> matcher) {
            this.key = key;
            this.type = MatcherType.optional(type);
            this.matcher = matcher;
        }

        private String getKey() {
            return key;
        }

        public MatcherType<Optional<T>> getType() {
            return type;
        }

        public SpongeMatcher<Optional<T>> getMatcher() {
            return matcher;
        }
    }
}
