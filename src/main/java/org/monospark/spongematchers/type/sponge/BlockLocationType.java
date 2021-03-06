package org.monospark.spongematchers.type.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.sponge.BlockLocationMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public final class BlockLocationType extends SpongeObjectType<Location<World>> {

    public BlockLocationType() {
        super("block location", Location.class, m -> BlockLocationMatcher.create(m));
    }

    @Override
    protected MatcherType<Map<String, Object>> createType() {
        return MatcherType.fixedMap()
                .addEntry("x", MatcherType.INTEGER)
                .addEntry("y", MatcherType.INTEGER)
                .addEntry("z", MatcherType.INTEGER)
                .addEntry("world", MatcherType.WORLD)
                .build();
    }
}
