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

public final class DefinedMapType extends MatcherType<Map<String, Object>> {

    private Set<MapTypeEntry<?>> entries;

    private DefinedMapType(Set<MapTypeEntry<?>> entries) {
        this.entries = entries;
    }

    @Override
    public boolean canMatch(Object o) {
        if (!(o instanceof Map)) {
            return false;
        }

        Map<?, ?> map = (Map<?, ?>) o;
        for (MapTypeEntry<?> entry : entries) {
            Object value = map.get(entry.getKey());
            if (!entry.getType().canMatch(value)) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected boolean checkElement(StringElement element) {
        return element instanceof MapElement;
    }

    @Override
    protected SpongeMatcher<Map<String, Object>> parse(StringElement element) throws SpongeMatcherParseException {
        if (!(element instanceof MapElement)) {
            throw new SpongeMatcherParseException("Invalid map matcher: " + element.getString());
        }

        MapElement map = (MapElement) element;
        out: for (String key : map.getElements().keySet()) {
            for (MapTypeEntry<?> entry : entries) {
                if (key.equals(entry.getKey())) {
                    continue out;
                }
            }

            throw new SpongeMatcherParseException("The map matcher " + element.getString()
                    + " contains an unknown key: " + key);
        }

        MapMatcher.Builder builder = MapMatcher.builder();
        for (MapTypeEntry<?> entry : entries) {
            addMapEntry(map, builder, entry);
        }
        return builder.build();
    }

    private <T> void addMapEntry(MapElement mapElement, MapMatcher.Builder builder, MapTypeEntry<T> entry)
            throws SpongeMatcherParseException {
        Optional<StringElement> value = mapElement.getElement(entry.getKey());
        if (value.isPresent()) {
            try {
                builder.addOptionalMatcher(entry.getKey(), entry.getType(),
                        MatcherType.optional(entry.getType()).parseMatcher(value.get()));
            } catch (SpongeMatcherParseException e) {
                throw new SpongeMatcherParseException("Couldn't parse the value for the key " + entry.getKey()
                        + " in the map matcher: " + mapElement.getString(), e);
            }
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
            return new DefinedMapType(entries);
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
