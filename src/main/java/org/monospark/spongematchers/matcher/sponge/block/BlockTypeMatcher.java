package org.monospark.spongematchers.matcher.sponge.block;

import org.monospark.spongematchers.matcher.SpongeMatchers;
import org.monospark.spongematchers.matcher.CompoundMatcher;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.data.property.PropertyHolder;

public final class BlockTypeMatcher {

    private BlockTypeMatcher() {}
    
    public static SpongeMatcher<BlockType> id(SpongeMatcher<String> typeId) {
        return create(typeId, SpongeMatchers.wildcard());
    }
    
    public static SpongeMatcher<BlockType> create(SpongeMatcher<String> typeId,
            SpongeMatcher<PropertyHolder> properties) {
        return CompoundMatcher.<BlockType>builder()
                .addMatcher(typeId, t -> t.getId())
                .addMatcher(properties, t -> t)
                .build();
    }
}
