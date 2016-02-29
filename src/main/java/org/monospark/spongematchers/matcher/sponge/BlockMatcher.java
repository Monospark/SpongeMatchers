package org.monospark.spongematchers.matcher.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.advanced.FixedMapMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public final class BlockMatcher extends SpongeObjectMatcher<BlockSnapshot> {

    public static SpongeMatcher<BlockSnapshot> create(SpongeMatcher<BlockState> state,
            SpongeMatcher<Location<World>> location) {
        SpongeMatcher<Map<String, Object>> matcher = FixedMapMatcher.builder()
                .addMatcher("state", state)
                .addMatcher("location", location)
                .build();
        return new BlockMatcher(matcher);
    }

    public static SpongeMatcher<BlockSnapshot> create(SpongeMatcher<Map<String, Object>> matcher) {
        return new BlockMatcher(matcher);
    }

    private BlockMatcher(SpongeMatcher<Map<String, Object>> matcher) {
        super(MatcherType.BLOCK, matcher);
    }

    @Override
    protected void fillMap(BlockSnapshot o, Map<String, Object> map) {
        map.put("state", o.getExtendedState());
        map.put("location", o.getLocation().get());
    }
}
