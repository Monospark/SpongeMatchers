package org.monospark.spongematchers.type.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.sponge.BiomeLocationMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public final class BiomeLocationType extends SpongeObjectType<Location<World>> {

    public BiomeLocationType() {
        super("biome location", Location.class, m -> BiomeLocationMatcher.create(m));
    }

    @Override
    protected MatcherType<Map<String, Object>> createType() {
        return MatcherType.fixedMap()
                .addEntry("x", MatcherType.INTEGER)
                .addEntry("y", MatcherType.INTEGER)
                .addEntry("world", MatcherType.WORLD)
                .build();
    }
}
