package org.monospark.spongematchers.type;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.sponge.PositionLocationMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public final class PositionLocationType extends MatcherType<Location<World>> {

    private static final MatcherType<Map<String, Object>> TYPE = MatcherType.definedMap()
            .addEntry("x", MatcherType.FLOATING_POINT)
            .addEntry("y", MatcherType.FLOATING_POINT)
            .addEntry("z", MatcherType.FLOATING_POINT)
            .addEntry("world", MatcherType.WORLD)
            .build();

    PositionLocationType() {}

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
            return PositionLocationMatcher.create(TYPE.parseMatcher(element));
        } catch (SpongeMatcherParseException e) {
            throw new SpongeMatcherParseException("Couldn't parse position location matcher: "
                    + element.getString(), e);
        }
    }
}
