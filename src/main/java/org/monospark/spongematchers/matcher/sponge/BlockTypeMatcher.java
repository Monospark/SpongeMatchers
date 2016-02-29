package org.monospark.spongematchers.matcher.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.advanced.FixedMapMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.data.property.PropertyHolder;

public final class BlockTypeMatcher extends SpongeObjectMatcher<BlockType> {

    public static SpongeMatcher<BlockType> create(SpongeMatcher<String> id, SpongeMatcher<PropertyHolder> properties) {
        return new BlockTypeMatcher(FixedMapMatcher.builder()
                .addMatcher("id", id)
                .addMatcher("properties", properties)
                .build());
    }

    public static SpongeMatcher<BlockType> create(SpongeMatcher<Map<String, Object>> matcher) {
        return new BlockTypeMatcher(matcher);
    }

    private BlockTypeMatcher(SpongeMatcher<Map<String, Object>> matcher) {
        super(MatcherType.BLOCK_TYPE, matcher);
    }

    @Override
    protected void fillMap(BlockType o, Map<String, Object> map) {
        map.put("id", o.getId());
        map.put("properties", o);
    }
}
