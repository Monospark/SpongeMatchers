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
        super("map");
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
    protected boolean canParse(StringElement element, boolean deep) {
        if (!(element instanceof MapElement)) {
            return false;
        }

        if (!deep) {
            return true;
        }

        MapElement mapElement = (MapElement) element;
        out: for (Entry<String, StringElement> entry : mapElement.getElements().entrySet()) {
            for (MatcherType<?> type : types) {
                if (MatcherType.optional(type).canParseMatcher(entry.getValue(), true)) {
                    continue out;
                }
                if (MatcherType.optional(MatcherType.list(type)).canParseMatcher(element, true)) {
                    continue out;
                }
            }
            return false;
        }
        return true;
    }

    @Override
    protected SpongeMatcher<Map<String, Object>> parse(StringElement element) throws SpongeMatcherParseException {
        MapElement mapElement = (MapElement) element;
        MapMatcher.Builder builder = MapMatcher.builder();
        for (Entry<String, StringElement> entry : mapElement.getElements().entrySet()) {
            TypedMatcher<?> parsedMatcher = parseTypedMatcher(entry.getValue());
            addTypedMatcher(entry.getKey(), parsedMatcher, builder);
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

        throw new SpongeMatcherParseException("Invalid matcher: " + element.getString());
    }

    private <T> TypedMatcher<?> parseTypedMatcherForType(MatcherType<T> type, StringElement element)
            throws SpongeMatcherParseException {
        if (MatcherType.optional(type).canParseMatcher(element, true)) {
            return new TypedMatcher<T>(MatcherType.optional(type).parseMatcher(element), type);
        }
        return null;
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
