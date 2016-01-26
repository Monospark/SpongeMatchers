package org.monospark.spongematchers.matcher.sponge.location;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.MapMatcher;
import org.monospark.spongematchers.matcher.sponge.SpongeObjectMatcher;
import org.spongepowered.api.world.Location;

public final class BlockLocationMatcher extends SpongeObjectMatcher<Location<?>> {

    public static SpongeMatcher<Location<?>> create(SpongeMatcher<Long> x, SpongeMatcher<Long> y) {
        SpongeMatcher<Map<String, Object>> matcher = MapMatcher.builder()
                .addMatcher("x", Long.class, x)
                .addMatcher("y", Long.class, y)
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
    public boolean matches(Location<?> o) {
        if (!o.hasBlock()) {
            return false;
        }
        
        return super.matches(o);
    }

    @Override
    protected void fillMap(Location<?> o, Map<String, Object> map) {
        map.put("x", o.getBlockPosition().getX());
        map.put("y", o.getBlockPosition().getY());
    }
}