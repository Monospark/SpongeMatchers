package org.monospark.spongematchers.type;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.MapMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.MapElement;
import org.monospark.spongematchers.parser.element.StringElement;

public final class VariableMapType extends MatcherType<Map<String, Object>> {

    private MatcherType<?> type;

    VariableMapType(MatcherType<?> type) {
        this.type = type;
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
            if (type.canMatch(value)) {
                continue out;
            }
            return false;
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

        MapElement mapElement = (MapElement) element;
        MapMatcher.Builder builder = MapMatcher.builder();
        for (Entry<String, StringElement> entry : mapElement.getElements().entrySet()) {
            try {
                if (MatcherType.optional(type).canParseMatcher(entry.getValue())) {
                    addMatcher(entry.getKey(), type, builder, entry.getValue());
                } else {
                    throw new SpongeMatcherParseException("Invalid matcher for key \"" + entry.getKey() + "\": "
                            + entry.getValue().getString());
                }
            } catch (SpongeMatcherParseException e) {
                throw new SpongeMatcherParseException("Couldn't parse map matcher: " + element.getString(), e);
            }
        }
        return builder.build();
    }

    private <T> void addMatcher(String name, MatcherType<T> type, MapMatcher.Builder builder, StringElement element)
            throws SpongeMatcherParseException {
        SpongeMatcher<Optional<T>> matcher = MatcherType.optional(type).parseMatcher(element);
        builder.addOptionalMatcher(name, type, matcher);
    }
}
