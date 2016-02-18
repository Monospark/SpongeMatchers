package org.monospark.spongematchers.matcher.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.MapMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.world.Location;

public final class BlockMatcher extends SpongeObjectMatcher<BlockSnapshot> {

    public static SpongeMatcher<BlockSnapshot> create(SpongeMatcher<BlockState> state,
            SpongeMatcher<Location<?>> location) {
        SpongeMatcher<Map<String, Object>> matcher = MapMatcher.builder()
                .addMatcher("state", MatcherType.BLOCK_STATE, state)
                .addMatcher("location", MatcherType.BLOCK_LOCATION, location)
                .build();
        return new BlockMatcher(matcher);
    }

    public static SpongeMatcher<BlockSnapshot> create(SpongeMatcher<Map<String, Object>> matcher) {
        return new BlockMatcher(matcher);
    }

    private BlockMatcher(SpongeMatcher<Map<String, Object>> matcher) {
        super(matcher);
    }

    @Override
    protected void fillMap(BlockSnapshot o, Map<String, Object> map) {
        map.put("state", o.getExtendedState());
        map.put("location", o.getLocation().get());
    }
}
