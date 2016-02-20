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

    protected DimensionType() {
        super("dimension");
    }

    @Override
    public boolean canMatch(Object o) {
        return o instanceof Dimension;
    }

    @Override
    protected boolean canParse(StringElement element, boolean deep) {
        return TYPE.canParseMatcher(element, deep);
    }

    @Override
    protected SpongeMatcher<Dimension> parse(StringElement element) throws SpongeMatcherParseException {
        return DimensionMatcher.create(TYPE.parseMatcher(element));
    }
}
