package org.monospark.spongematchers.type;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.sponge.BiomeLocationMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.spongepowered.api.world.Location;

public final class BiomeLocationType extends MatcherType<Location<?>> {

    private static final MatcherType<Map<String,Object>> TYPE = MatcherType.definedMap()
            .addEntry("x", MatcherType.INTEGER)
            .addEntry("y", MatcherType.INTEGER)
            .build();
    
    protected BiomeLocationType() {
        super("biome location");
    }

    @Override
    public boolean canMatch(Object o) {
        return o instanceof Location;
    }

    @Override
    protected boolean canParse(StringElement element, boolean deep) {
        return TYPE.canParseMatcher(element, deep);
    }

    @Override
    protected SpongeMatcher<Location<?>> parse(StringElement element) throws SpongeMatcherParseException {
        return BiomeLocationMatcher.create(TYPE.parseMatcher(element));
    }
}
