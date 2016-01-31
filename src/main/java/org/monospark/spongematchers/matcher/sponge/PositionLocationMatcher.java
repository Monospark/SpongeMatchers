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
    public boolean matches(Location<?> o) {
        if (o.hasBiome() || o.hasBlock()) {
            return false;
        }
        
        return super.matches(o);
    }

    @Override
    protected void fillMap(Location<?> o, Map<String, Object> map) {
        map.put("x", o.getPosition().getX());
        map.put("y", o.getPosition().getY());
        map.put("y", o.getPosition().getZ());
    }
}
