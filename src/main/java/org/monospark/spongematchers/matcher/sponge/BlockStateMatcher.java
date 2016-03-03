package org.monospark.spongematchers.matcher.sponge;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.advanced.FixedMapMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.trait.BlockTrait;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;

import com.google.common.collect.Maps;

public final class BlockStateMatcher extends SpongeObjectMatcher<BlockState> {

    public static SpongeMatcher<BlockState> create(SpongeMatcher<BlockType> type,
            SpongeMatcher<Map<String, Object>> traits, SpongeMatcher<Optional<DataView>> data) {
        return new BlockStateMatcher(FixedMapMatcher.builder()
                .addMatcher("type", type)
                .addMatcher("traits", traits)
                .addMatcher("data", data)
                .build());
    }

    public static SpongeMatcher<BlockState> create(SpongeMatcher<Map<String, Object>> matcher) {
        return new BlockStateMatcher(matcher);
    }

    private BlockStateMatcher(SpongeMatcher<Map<String, Object>> matcher) {
        super(MatcherType.BLOCK_STATE, matcher);
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
        for (Entry<BlockTrait<?>, ?> entry : state.getTraitMap().entrySet()) {
            map.put(entry.getKey().getName(), makeMatchable(entry.getKey(), entry.getValue()));
        }
        return map;
    }

    private Object makeMatchable(BlockTrait<?> trait, Object value) {
        if (trait.getValueClass().equals(Boolean.class)) {
            return value;
        } else if (trait.getValueClass().equals(Integer.class)) {
            return ((Integer) value).longValue();
        } else {
            return value.toString();
        }
    }
}
