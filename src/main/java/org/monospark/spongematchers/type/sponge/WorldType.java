package org.monospark.spongematchers.type.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.sponge.WorldMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.world.World;

public final class WorldType extends SpongeObjectType<World> {

    public WorldType() {
        super("world", World.class, m -> WorldMatcher.create(m));
    }

    @Override
    protected MatcherType<Map<String, Object>> createType() {
        return MatcherType.fixedMap()
                .addEntry("name", MatcherType.STRING)
                .addEntry("dimension", MatcherType.DIMENSION)
                .addEntry("seed", MatcherType.INTEGER)
                .addEntry("gameRules", MatcherType.variableMap(MatcherType.STRING))
                .build();
    }
}
