package org.monospark.spongematchers.type.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.sponge.BlockMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.block.BlockSnapshot;

public final class BlockType extends SpongeObjectType<BlockSnapshot> {

    public BlockType() {
        super("block", BlockSnapshot.class, m -> BlockMatcher.create(m));
    }

    @Override
    protected MatcherType<Map<String, Object>> createType() {
        return MatcherType.fixedMap()
                .addEntry("state", MatcherType.BLOCK_STATE)
                .addEntry("location", MatcherType.BLOCK_LOCATION)
                .build();
    }
}
