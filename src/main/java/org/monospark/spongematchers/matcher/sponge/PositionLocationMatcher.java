package org.monospark.spongematchers.matcher.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.MapMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public final class PositionLocationMatcher extends SpongeObjectMatcher<Location<World>> {

    public static SpongeMatcher<Location<World>> create(SpongeMatcher<Double> x, SpongeMatcher<Double> y,
            SpongeMatcher<Double> z, SpongeMatcher<World> world) {
        SpongeMatcher<Map<String, Object>> matcher = MapMatcher.builder()
                .addMatcher("x", MatcherType.FLOATING_POINT, x)
                .addMatcher("y", MatcherType.FLOATING_POINT, y)
                .addMatcher("z", MatcherType.FLOATING_POINT, z)
                .addMatcher("world", MatcherType.WORLD, world)
                .build();
        return new PositionLocationMatcher(matcher);
    }

    public static SpongeMatcher<Location<World>> create(SpongeMatcher<Map<String, Object>> matcher) {
        return new PositionLocationMatcher(matcher);
    }

    private PositionLocationMatcher(SpongeMatcher<Map<String, Object>> matcher) {
        super(matcher);
    }

    @Override
    protected void fillMap(Location<World> o, Map<String, Object> map) {
        map.put("x", o.getPosition().getX());
        map.put("y", o.getPosition().getY());
        map.put("z", o.getPosition().getZ());
        map.put("world", o.getExtent());
    }
}
