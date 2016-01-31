package org.monospark.spongematchers.matcher.sponge;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.MapMatcher;
import org.monospark.spongematchers.type.BlockStateType;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.trait.BlockTrait;
import org.spongepowered.api.block.trait.EnumTrait;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;

import com.google.common.collect.Maps;

public final class BlockStateMatcher extends SpongeObjectMatcher<BlockState> {

    public static SpongeMatcher<BlockState> create(SpongeMatcher<BlockType> type,
            SpongeMatcher<Map<String, Object>> traits, SpongeMatcher<Optional<DataView>> data) {
        return new BlockStateMatcher(MapMatcher.builder()
                .addMatcher("type", MatcherType.BLOCK_TYPE, type)
                .addMatcher("traits", BlockStateType.TRAIT_TYPE, traits)
                .addOptionalMatcher("data", MatcherType.DATA_VIEW, data)
                .build());
    }
    
    public static SpongeMatcher<BlockState> create(SpongeMatcher<Map<String, Object>> matcher) {
        return new BlockStateMatcher(matcher);
    }
    
    private BlockStateMatcher(SpongeMatcher<Map<String, Object>> matcher) {
        super(matcher);
    }

    @Override
    protected void fillMap(BlockState o, Map<String, Object> map) {
        map.put("type", o.getType());
        map.put("traits", createTraits(o));
        if (o.toContainer().get(DataQuery.of("UnsafeData")).isPresent()) {
            map.put("data", o.toContainer().get(DataQuery.of("UnsafeData")).get());
        }
    }
    
    private Map<String, Object> createTraits(BlockState state) {
        Map<String, Object> map = Maps.newHashMap();
        for (Entry<BlockTrait<?>,?> entry : state.getTraitMap().entrySet()) {
            Object value = entry.getKey() instanceof EnumTrait ? entry.getValue().toString() : entry.getValue();
            map.put(entry.getKey().getId(), value);
        }
        return map;
    }
}
