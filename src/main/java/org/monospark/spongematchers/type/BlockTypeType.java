package org.monospark.spongematchers.type;

import java.util.Map;

import org.monospark.spongematchers.matcher.sponge.BlockTypeMatcher;
import org.spongepowered.api.block.BlockType;

public final class BlockTypeType extends SpongeObjectType<BlockType> {

    BlockTypeType() {
        super("block type", BlockType.class, m -> BlockTypeMatcher.create(m));
    }

    @Override
    protected MatcherType<Map<String, Object>> createType() {
        return MatcherType.fixedMap()
                .addEntry("id", MatcherType.STRING)
                .addEntry("properties", MatcherType.PROPERTY_HOLDER)
                .build();
    }
}
