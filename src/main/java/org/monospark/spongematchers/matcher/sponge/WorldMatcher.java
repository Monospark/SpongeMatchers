package org.monospark.spongematchers.matcher.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.FixedMapMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.world.Dimension;
import org.spongepowered.api.world.World;

public final class WorldMatcher extends SpongeObjectMatcher<World> {

    public static SpongeMatcher<World> create(SpongeMatcher<String> name, SpongeMatcher<Dimension> dimension,
            SpongeMatcher<Long> seed, SpongeMatcher<Map<String, String>> gameRules) {
        SpongeMatcher<Map<String, Object>> matcher = FixedMapMatcher.builder()
                .addMatcher("name", name)
                .addMatcher("dimension", dimension)
                .addMatcher("seed", seed)
                .addMatcher("gameRules", gameRules)
                .build();
        return new WorldMatcher(matcher);
    }

    public static SpongeMatcher<World> create(SpongeMatcher<Map<String, Object>> matcher) {
        return new WorldMatcher(matcher);
    }

    private WorldMatcher(SpongeMatcher<Map<String, Object>> matcher) {
        super(MatcherType.WORLD, matcher);
    }

    @Override
    protected void fillMap(World o, Map<String, Object> map) {
        map.put("name", o.getName());
        map.put("dimension", o.getDimension());
        map.put("seed", o.getProperties().getSeed());
        map.put("gameRules", o.getGameRules());
    }
}
