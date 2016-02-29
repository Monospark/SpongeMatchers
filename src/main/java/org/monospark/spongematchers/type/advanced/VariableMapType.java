package org.monospark.spongematchers.type.advanced;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.advanced.VariableMapMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.MapElement;
import org.monospark.spongematchers.parser.element.StringElement;
import org.monospark.spongematchers.type.MatcherType;
import org.monospark.spongematchers.util.GenericsHelper;

public final class VariableMapType extends MatcherType<Map<String, Object>> {

    private MatcherType<?> type;

    public VariableMapType(MatcherType<?> type) {
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

            Optional<?> value = Optional.of(entry.getValue());
            if (MatcherType.optional(type).canMatch(value)) {
                continue out;
            } else {
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

        MapElement mapElement = (MapElement) element;
        VariableMapMatcher.Builder builder = VariableMapMatcher.builder();
        for (Entry<String, StringElement> entry : mapElement.getElements().entrySet()) {
            try {
                if (MatcherType.optional(type).canParseMatcher(entry.getValue())) {
                    builder.addMatcher(entry.getKey(),
                            GenericsHelper.genericWrapper(MatcherType.optional(type).parseMatcher(entry.getValue())));
                } else {
                    throw new SpongeMatcherParseException("Invalid matcher for key \"" + entry.getKey() + "\": "
                            + entry.getValue().getString());
                }
            } catch (SpongeMatcherParseException e) {
                throw new SpongeMatcherParseException("Couldn't parse map matcher: " + element.getString(), e);
            }
        }
        return builder.build(type);
    }
}
