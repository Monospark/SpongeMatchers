package org.monospark.spongematchers.matcher.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.MapMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public final class BlockLocationMatcher extends SpongeObjectMatcher<Location<World>> {

    public static SpongeMatcher<Location<World>> create(SpongeMatcher<Long> x, SpongeMatcher<Long> y,
            SpongeMatcher<Long> z, SpongeMatcher<World> world) {
        SpongeMatcher<Map<String, Object>> matcher = MapMatcher.builder()
                .addMatcher("x", MatcherType.INTEGER, x)
                .addMatcher("y", MatcherType.INTEGER, y)
                .addMatcher("z", MatcherType.INTEGER, z)
                .addMatcher("world", MatcherType.WORLD, world)
                .build();
        return new BlockLocationMatcher(matcher);
    }

    public static SpongeMatcher<Location<World>> create(SpongeMatcher<Map<String, Object>> matcher) {
        return new BlockLocationMatcher(matcher);
    }

    private BlockLocationMatcher(SpongeMatcher<Map<String, Object>> matcher) {
        super(matcher);
    }

    @Override
    protected void fillMap(Location<World> o, Map<String, Object> map) {
        map.put("x", (long) o.getBlockX());
        map.put("y", (long) o.getBlockY());
        map.put("z", (long) o.getBlockZ());
        map.put("world", o.getExtent());
    }
}
