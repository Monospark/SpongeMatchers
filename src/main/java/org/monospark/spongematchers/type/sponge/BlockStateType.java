package org.monospark.spongematchers.type.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.sponge.BlockStateMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.block.BlockState;

public final class BlockStateType extends SpongeObjectType<BlockState> {

    public BlockStateType() {
        super("block state", BlockState.class, m -> BlockStateMatcher.create(m));
    }


    @Override
    protected MatcherType<Map<String, Object>> createType() {
        MatcherType<Map<String, Object>> traitType = MatcherType.variableMap(MatcherType.multi()
                .addType(MatcherType.BOOLEAN)
                .addType(MatcherType.INTEGER)
                .addType(MatcherType.STRING)
                .build());

        return MatcherType.fixedMap()
                .addEntry("type", MatcherType.BLOCK_TYPE)
                .addEntry("traits", traitType)
                .addEntry("data", MatcherType.DATA_VIEW)
                .build();
    }
}
