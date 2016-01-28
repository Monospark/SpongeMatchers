package org.monospark.spongematchers.type;

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

    private Set<MapTypeEntry<?>> entries;
    
    private MapType(Set<MapTypeEntry<?>> entries) {
        super("map");
        this.entries = entries;
    }

    @Override
    public boolean canMatch(Object o) {
        if (!(o instanceof Map)) {
            return false;
        }
        
        Map<?,?> map = (Map<?, ?>) o;
        for (MapTypeEntry<?> entry : entries) {
            Object value = map.get(entry.getKey());
            if (!entry.getType().canMatch(value)) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    protected boolean canParse(StringElement element, boolean deep) {
        if (!(element instanceof MapElement)) {
            return false;
        }
        
        MapElement map = (MapElement) element;
        out: for (String key : map.getElements().keySet()) {
            for (MapTypeEntry<?> entry : entries) {
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
        
        for (MapTypeEntry<?> entry : entries) {
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
        for (MapTypeEntry<?> entry : entries) {
            addMapEntry(mapElement, builder, entry);
        }
        return builder.build();
    }
    
    private <T> void addMapEntry(MapElement mapElement, MapMatcher.Builder builder, MapTypeEntry<T> entry)
            throws SpongeMatcherParseException {
        Optional<StringElement> value = mapElement.getElement(entry.getKey());
        if (value.isPresent()) {
            builder.addOptionalMatcher(entry.getKey(), entry.getType(),
                    MatcherType.optional(entry.getType()).parseMatcher(value.get()));
        } else {
            builder.addOptionalMatcher(entry.getKey(), entry.getType(), SpongeMatcher.wildcard());
        }
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static final class Builder {
        
        private Set<MapTypeEntry<?>> entries;
        
        private Builder() {
            entries = Sets.newHashSet();
        }

        public <T> Builder addEntry(String key, MatcherType<T> type) {
            entries.add(new MapTypeEntry<T>(key, type));
            return this;
        }
        
        public MatcherType<Map<String, Object>> build() {
            return new MapType<>(entries);
        }
    }

    private static final class MapTypeEntry<T> {
        
        private String key;
        
        private MatcherType<T> type;

        private MapTypeEntry(String key, MatcherType<T> type) {
            this.key = key;
            this.type = type;
        }

        public String getKey() {
            return key;
        }

        public MatcherType<T> getType() {
            return type;
        }
    }
}
