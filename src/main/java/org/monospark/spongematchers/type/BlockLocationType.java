package org.monospark.spongematchers.type;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.sponge.BlockLocationMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public final class BlockLocationType extends MatcherType<Location<World>> {

    private static final MatcherType<Map<String, Object>> TYPE = MatcherType.definedMap()
            .addEntry("x", MatcherType.INTEGER)
            .addEntry("y", MatcherType.INTEGER)
            .addEntry("z", MatcherType.INTEGER)
            .addEntry("world", MatcherType.WORLD)
            .build();

    BlockLocationType() {}

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
            return BlockLocationMatcher.create(TYPE.parseMatcher(element));
        } catch (SpongeMatcherParseException e) {
            throw new SpongeMatcherParseException("Couldn't parse biome location matcher: " + element.getString(), e);
        }
    }
}
