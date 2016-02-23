package org.monospark.spongematchers.type;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.sponge.BiomeLocationMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public final class BiomeLocationType extends MatcherType<Location<World>> {

    private static final MatcherType<Map<String, Object>> TYPE = MatcherType.definedMap()
            .addEntry("x", MatcherType.INTEGER)
            .addEntry("y", MatcherType.INTEGER)
            .addEntry("world", MatcherType.WORLD)
            .build();

    BiomeLocationType() {}

    @Override
    public boolean canMatch(Object o) {
        return o instanceof Location;
    }

    @Override
    protected boolean checkElement(StringElement element) {
        return TYPE.acceptsElement(element);
    }

    @Override
    protected SpongeMatcher<Location<World>> parse(StringElement element) throws SpongeMatcherParseException {
        try {
            return BiomeLocationMatcher.create(TYPE.parseMatcher(element));
        } catch (SpongeMatcherParseException e) {
            throw new SpongeMatcherParseException("Couldn't parse biome location: " + element.getString(), e);
        }
    }
}
