package org.monospark.spongematchers.matcher.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.MapMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public final class BiomeLocationMatcher extends SpongeObjectMatcher<Location<World>> {

    public static SpongeMatcher<Location<World>> create(SpongeMatcher<Long> x, SpongeMatcher<Long> y,
            SpongeMatcher<World> world) {
        SpongeMatcher<Map<String, Object>> matcher = MapMatcher.builder()
                .addMatcher("x", MatcherType.INTEGER, x)
                .addMatcher("y", MatcherType.INTEGER, y)
                .addMatcher("world", MatcherType.WORLD, world)
                .build();
        return new BiomeLocationMatcher(matcher);
    }

    public static SpongeMatcher<Location<World>> create(SpongeMatcher<Map<String, Object>> matcher) {
        return new BiomeLocationMatcher(matcher);
    }

    private BiomeLocationMatcher(SpongeMatcher<Map<String, Object>> matcher) {
        super(matcher);
    }

    @Override
    protected void fillMap(Location<World> o, Map<String, Object> map) {
        map.put("x", (long) o.getBiomePosition().getX());
        map.put("y", (long) o.getBiomePosition().getY());
        map.put("world", o.getExtent());
    }
}
