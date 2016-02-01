package org.monospark.spongematchers.matcher.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.MapMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.world.Location;

public final class PositionLocationMatcher extends SpongeObjectMatcher<Location<?>> {

    public static SpongeMatcher<Location<?>> create(SpongeMatcher<Double> x, SpongeMatcher<Double> y,
            SpongeMatcher<Double> z) {
        SpongeMatcher<Map<String, Object>> matcher = MapMatcher.builder()
                .addMatcher("x", MatcherType.FLOATING_POINT, x)
                .addMatcher("y", MatcherType.FLOATING_POINT, y)
                .addMatcher("z", MatcherType.FLOATING_POINT, z)
                .build();
        return new PositionLocationMatcher(matcher);
    }
    
    public static SpongeMatcher<Location<?>> create(SpongeMatcher<Map<String, Object>> matcher) {
        return new PositionLocationMatcher(matcher);
    }
    
    private PositionLocationMatcher(SpongeMatcher<Map<String, Object>> matcher) {
        super(matcher);
    }

    @Override
    protected void fillMap(Location<?> o, Map<String, Object> map) {
        map.put("x", (double) o.getPosition().getX());
        map.put("y", (double) o.getPosition().getY());
        map.put("y", (double) o.getPosition().getZ());
    }
}
