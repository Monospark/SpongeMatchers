package org.monospark.spongematchers.type;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.MapMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.MapElement;
import org.monospark.spongematchers.parser.element.StringElement;

import com.google.common.collect.Maps;

public final class FixedMapType extends MatcherType<Map<String, Object>> {

    private Map<String, MatcherType<?>> entries;

    private FixedMapType(Map<String, MatcherType<?>> entries) {
        this.entries = entries;
    }

    @Override
    public boolean canMatch(Object o) {
        if (!(o instanceof Map)) {
            return false;
        }

        Map<?, ?> map = (Map<?, ?>) o;
        for (Entry<String, MatcherType<?>> entry : entries.entrySet()) {
            Object value = map.get(entry.getKey());
            if (!entry.getValue().canMatch(value)) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected boolean canParse(StringElement element) {
        return element instanceof MapElement;
    }

    @Override
    protected SpongeMatcher<Map<String, Object>> parse(StringElement element) throws SpongeMatcherParseException {
        if (!(element instanceof MapElement)) {
            throw new SpongeMatcherParseException("Invalid map matcher: " + element.getString());
        }

        MapElement map = (MapElement) element;
        out: for (String key : map.getElements().keySet()) {
            for (String entryKey : entries.keySet()) {
                if (key.equals(entryKey)) {
                    continue out;
                }
            }

            throw new SpongeMatcherParseException("The map matcher " + element.getString()
                    + " contains an unknown key: " + key);
        }

        MapMatcher.Builder builder = MapMatcher.builder();
        for (Entry<String, MatcherType<?>> entry : entries.entrySet()) {
            addMapEntry(map, builder, entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

    private <T> void addMapEntry(MapElement mapElement, MapMatcher.Builder builder, String key, MatcherType<T> type)
            throws SpongeMatcherParseException {
        Optional<StringElement> value = mapElement.getElement(key);
        if (value.isPresent()) {
            try {
                builder.addMatcher(key, type, type.parseMatcher(value.get()));
            } catch (SpongeMatcherParseException e) {
                throw new SpongeMatcherParseException("Couldn't parse the value for the key \"" + key
                        + "\" in the map matcher: " + mapElement.getString(), e);
            }
        } else {
            builder.addOptionalMatcher(key, type, SpongeMatcher.wildcard());
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Map<String, MatcherType<?>> entries;

        private Builder() {
            entries = Maps.newHashMap();
        }

        public <T> Builder addEntry(String key, MatcherType<T> type) {
            entries.put(key, type);
            return this;
        }

        public MatcherType<Map<String, Object>> build() {
            return new FixedMapType(entries);
        }
    }
}
