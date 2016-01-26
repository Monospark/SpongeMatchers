package org.monospark.spongematchers.matcher.sponge.block;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.MapMatcher;
import org.monospark.spongematchers.matcher.sponge.SpongeObjectMatcher;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.data.property.PropertyHolder;

public final class BlockTypeMatcher extends SpongeObjectMatcher<BlockType> {

    public static SpongeMatcher<BlockType> id(SpongeMatcher<String> id) {
        return create(id, SpongeMatcher.wildcard());
    }
    
    public static SpongeMatcher<BlockType> create(SpongeMatcher<String> id, SpongeMatcher<PropertyHolder> properties) {
        return new BlockTypeMatcher(MapMatcher.builder()
                .addMatcher("id", String.class, id)
                .addMatcher("properties", PropertyHolder.class, properties)
                .build());
    }
    
    public static SpongeMatcher<BlockType> create(SpongeMatcher<Map<String, Object>> matcher) {
        return new BlockTypeMatcher(matcher);
    }
    
    private BlockTypeMatcher(SpongeMatcher<Map<String, Object>> matcher) {
        super(matcher);
    }

    @Override
    protected void fillMap(BlockType o, Map<String, Object> map) {
        map.put("id", o.getId());
        map.put("properties", o);
    }
}
