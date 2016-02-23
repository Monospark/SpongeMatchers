package org.monospark.spongematchers.type;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.MapMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.MapElement;
import org.monospark.spongematchers.parser.element.StringElement;

import com.google.common.collect.Sets;

public final class UndefinedMapType extends MatcherType<Map<String, Object>> {

    private Set<MatcherType<?>> types;

    private UndefinedMapType(Set<MatcherType<?>> types) {
        this.types = types;
    }

    @Override
    public boolean canMatch(Object o) {
        if (!(o instanceof Map)) {
            return false;
        }

        Map<?, ?> map = (Map<?, ?>) o;
        out: for (Entry<?, ?> entry : map.entrySet()) {
            if (!(entry.getKey() instanceof String)) {
                return false;
            }

            Object value = entry.getValue();
            for (MatcherType<?> type : types) {
                if (type.canMatch(value)) {
                    continue out;
                }
            }
            return false;
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

        MapElement mapElement = (MapElement) element;
        MapMatcher.Builder builder = MapMatcher.builder();
        for (Entry<String, StringElement> entry : mapElement.getElements().entrySet()) {
            try {
                TypedMatcher<?> parsedMatcher = parseTypedMatcher(entry.getValue());
                addTypedMatcher(entry.getKey(), parsedMatcher, builder);
            } catch (SpongeMatcherParseException e) {
                throw new SpongeMatcherParseException("Couldn't parse map matcher: " + element.getString(), e);
            }
        }
        return builder.build();
    }

    private <T> void addTypedMatcher(String name, TypedMatcher<T> matcher, MapMatcher.Builder builder)
            throws SpongeMatcherParseException {
        builder.addOptionalMatcher(name, matcher.getType(), matcher.getMatcher());
    }

    private TypedMatcher<?> parseTypedMatcher(StringElement element) throws SpongeMatcherParseException {
        for (MatcherType<?> type : types) {
            TypedMatcher<?> matcher = parseTypedMatcherForType(type, element);
            if (matcher != null) {
                return matcher;
            }
        }

        throw new SpongeMatcherParseException("Couldn't parse map value: " + element.getString());
    }

    private <T> TypedMatcher<?> parseTypedMatcherForType(MatcherType<T> type, StringElement element)
            throws SpongeMatcherParseException {
        if (MatcherType.optional(type).acceptsElement(element)) {
            return new TypedMatcher<T>(MatcherType.optional(type).parseMatcher(element), type);
        } else {
            return null;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Set<MatcherType<?>> types;

        private Builder() {
            types = Sets.newHashSet();
        }

        public Builder addType(MatcherType<?> type) {
            types.add(type);
            return this;
        }

        public MatcherType<Map<String, Object>> build() {
            return new UndefinedMapType(types);
        }
    }

    private static final class TypedMatcher<T> {

        private SpongeMatcher<Optional<T>> matcher;

        private MatcherType<T> type;

        private TypedMatcher(SpongeMatcher<Optional<T>> matcher, MatcherType<T> type) {
            this.matcher = matcher;
            this.type = type;
        }

        public SpongeMatcher<Optional<T>> getMatcher() {
            return matcher;
        }

        public MatcherType<T> getType() {
            return type;
        }
    }
}
