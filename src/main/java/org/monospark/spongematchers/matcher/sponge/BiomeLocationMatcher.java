package org.monospark.spongematchers.matcher.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.MapMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.world.Location;

public final class BiomeLocationMatcher extends SpongeObjectMatcher<Location<?>> {

    public static SpongeMatcher<Location<?>> create(SpongeMatcher<Long> x, SpongeMatcher<Long> y) {
        SpongeMatcher<Map<String, Object>> matcher = MapMatcher.builder()
                .addMatcher("x", MatcherType.INTEGER, x)
                .addMatcher("y", MatcherType.INTEGER, y)
                .build();
        return new BiomeLocationMatcher(matcher);
    }

    public static SpongeMatcher<Location<?>> create(SpongeMatcher<Map<String, Object>> matcher) {
        return new BiomeLocationMatcher(matcher);
    }

    private BiomeLocationMatcher(SpongeMatcher<Map<String, Object>> matcher) {
        super(matcher);
    }

    @Override
    protected void fillMap(Location<?> o, Map<String, Object> map) {
        map.put("x", (long) o.getBiomePosition().getX());
        map.put("y", (long) o.getBiomePosition().getY());
    }
}
