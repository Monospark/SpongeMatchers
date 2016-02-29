package org.monospark.spongematchers.type.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.sponge.PositionLocationMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public final class PositionLocationType extends SpongeObjectType<Location<World>> {

    public PositionLocationType() {
        super("position location", Location.class, m -> PositionLocationMatcher.create(m));
    }

    @Override
    protected MatcherType<Map<String, Object>> createType() {
        return MatcherType.fixedMap()
                .addEntry("x", MatcherType.FLOATING_POINT)
                .addEntry("y", MatcherType.FLOATING_POINT)
                .addEntry("z", MatcherType.FLOATING_POINT)
                .addEntry("world", MatcherType.WORLD)
                .build();
    }
}
