package org.monospark.spongematchers.matcher.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.MapMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.world.Location;

public final class BlockLocationMatcher extends SpongeObjectMatcher<Location<?>> {

    public static SpongeMatcher<Location<?>> create(SpongeMatcher<Long> x, SpongeMatcher<Long> y,
            SpongeMatcher<Long> z) {
        SpongeMatcher<Map<String, Object>> matcher = MapMatcher.builder()
                .addMatcher("x", MatcherType.INTEGER, x)
                .addMatcher("y", MatcherType.INTEGER, y)
                .addMatcher("z", MatcherType.INTEGER, z)
                .build();
        return new BlockLocationMatcher(matcher);
    }
    
    public static SpongeMatcher<Location<?>> create(SpongeMatcher<Map<String, Object>> matcher) {
        return new BlockLocationMatcher(matcher);
    }
    
    private BlockLocationMatcher(SpongeMatcher<Map<String, Object>> matcher) {
        super(matcher);
    }

    @Override
    protected void fillMap(Location<?> o, Map<String, Object> map) {
        map.put("x", (long) o.getBlockPosition().getX());
        map.put("y", (long) o.getBlockPosition().getY());
        map.put("z", (long) o.getBlockPosition().getZ());
    }
}
