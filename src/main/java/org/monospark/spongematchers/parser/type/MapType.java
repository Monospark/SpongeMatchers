package org.monospark.spongematchers.parser.type;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.MapMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.MapElement;
import org.monospark.spongematchers.parser.element.StringElement;

import com.google.common.collect.Sets;

public final class MapType<V> extends MatcherType<Map<String, Object>>{

    private Set<MapTypeEntry> entries;
    
    private MapType(Set<MapTypeEntry> entries) {
        super("map", Map.class);
        this.entries = entries;
    }

    @Override
    protected boolean canParse(StringElement element, boolean deep) {
        if (!(element instanceof MapElement)) {
            return false;
        }
        
        MapElement map = (MapElement) element;
        out: for (String key : map.getElements().keySet()) {
            for (MapTypeEntry entry : entries) {
                if (key.equals(entry.getKey())) {
                    continue out;
                }
            }
            
            //The map element contains an unknown entry
            return false;
        }
        
        if (!deep) {
            return true;
        }
        
        for (MapTypeEntry entry : entries) {
            Optional<StringElement> value = map.getElement(entry.getKey());
            if (value.isPresent()) {
                boolean canParse = entry.getType().canParse(value.get(), true);
                if (!canParse) {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    protected SpongeMatcher<Map<String, Object>> parse(StringElement element) throws SpongeMatcherParseException {
        MapMatcher.Builder builder = MapMatcher.builder();
        MapElement mapElement = (MapElement) element;
        for (MapTypeEntry entry : entries) {
            Optional<StringElement> value = mapElement.getElement(entry.getKey());
            if (value.isPresent()) {
                builder.addOptionalMatcher(entry.getKey(), entry.getType().getObjectClass(),
                        MatcherType.optional(entry.getType()).parseMatcher(value.get()));
            } else {
                builder.addOptionalMatcher(entry.getKey(), entry.getType().getObjectClass(), SpongeMatcher.wildcard());
            }
        }
        return builder.build();
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static final class Builder {
        
        private Set<MapTypeEntry> entries;
        
        private Builder() {
            entries = Sets.newHashSet();
        }

        public Builder addEntry(String key, MatcherType<?> type) {
            entries.add(new MapTypeEntry(key, type));
            return this;
        }
        
        public MatcherType<Map<String, Object>> build() {
            return new MapType<>(entries);
        }
    }

    private static final class MapTypeEntry {
        
        private String key;
        
        private MatcherType<?> type;

        private MapTypeEntry(String key, MatcherType<?> type) {
            this.key = key;
            this.type = type;
        }

        public String getKey() {
            return key;
        }

        public MatcherType<?> getType() {
            return type;
        }
    }
}
