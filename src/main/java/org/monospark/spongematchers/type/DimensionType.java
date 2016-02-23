package org.monospark.spongematchers.type;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.sponge.DimensionMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.spongepowered.api.world.Dimension;

public final class DimensionType extends MatcherType<Dimension> {

    private static final MatcherType<Map<String, Object>> TYPE = MatcherType.definedMap()
            .addEntry("name", MatcherType.STRING)
            .addEntry("type", MatcherType.STRING)
            .addEntry("respawnAllowed", MatcherType.BOOLEAN)
            .addEntry("waterEvaporating", MatcherType.BOOLEAN)
            .addEntry("sky", MatcherType.BOOLEAN)
            .addEntry("height", MatcherType.INTEGER)
            .addEntry("buildHeight", MatcherType.INTEGER)
            .build();

    DimensionType() {}

    @Override
    public boolean canMatch(Object o) {
        return o instanceof Dimension;
    }

    @Override
    protected boolean checkElement(StringElement element) {
        return TYPE.acceptsElement(element);
    }

    @Override
    protected SpongeMatcher<Dimension> parse(StringElement element) throws SpongeMatcherParseException {
        try {
            return DimensionMatcher.create(TYPE.parseMatcher(element));
        } catch (SpongeMatcherParseException e) {
            throw new SpongeMatcherParseException("Couldn't parse dimension matcher: " + element.getString(), e);
        }
    }
}
