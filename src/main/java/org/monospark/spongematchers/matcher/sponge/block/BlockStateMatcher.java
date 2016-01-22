package org.monospark.spongematchers.matcher.sponge.block;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.sponge.block.BlockTraitMatcher.Type;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.trait.BlockTrait;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;

import com.google.common.collect.Maps;

public final class BlockStateMatcher implements SpongeMatcher<BlockState> {

    private SpongeMatcher<BlockType> typeMatcher;
    
    private Map<String, BlockTraitMatcher<?>> traitMatchers;
    
    private SpongeMatcher<Optional<DataView>> dataMatcher;

    private BlockStateMatcher(SpongeMatcher<BlockType> typeMatcher, Map<String, BlockTraitMatcher<?>> traitMatchers,
            SpongeMatcher<Optional<DataView>> dataMatcher) {
        this.typeMatcher = typeMatcher;
        this.traitMatchers = traitMatchers;
        this.dataMatcher = dataMatcher;
    }

    @Override
    public boolean matches(BlockState o) {
        if (!typeMatcher.matches(o.getType())) {
            return false;
        }
        
        Optional<DataView> data = o.toContainer().getView(DataQuery.of("UnsafeData"));
        if (!dataMatcher.matches(data)) {
            return false;
        }

        for (Entry<String, BlockTraitMatcher<?>> traitMatcher : traitMatchers.entrySet()) {
            Optional<BlockTrait<?>> trait = o.getTrait(traitMatcher.getKey());
            if (!trait.isPresent()) {
                return false;
            }
            
            Optional<?> value = o.getTraitValue(trait.get());
            if (!matchesTrait(traitMatcher.getValue(), value.get())) {
                return false;
            }
        }
        return true;
    }
    
    @SuppressWarnings("unchecked")
    private <T> boolean matchesTrait(BlockTraitMatcher<T> matcher, Object value) {
        return matcher.getMatcher().matches((T) value);
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static final class Builder {
        
        private SpongeMatcher<BlockType> typeMatcher;
        
        private Map<String, BlockTraitMatcher<?>> traitMatchers;
        
        private SpongeMatcher<Optional<DataView>> dataMatcher;

        public Builder() {
            typeMatcher = SpongeMatcher.wildcard();
            traitMatchers = Maps.newHashMap();
            dataMatcher = SpongeMatcher.wildcard();
        }
        
        public Builder type(SpongeMatcher<BlockType> typeMatcher) {
            this.typeMatcher = Objects.requireNonNull(typeMatcher);
            return this;
        }
        
        public Builder addBooleanTrait(String name, SpongeMatcher<Boolean> traitMatcher) {
            traitMatchers.put(name, new BlockTraitMatcher<>(Type.BOOLEAN_TRAIT, traitMatcher));
            return this;
        }
        
        public Builder addIntegerTrait(String name, SpongeMatcher<Integer> traitMatcher) {
            traitMatchers.put(name, new BlockTraitMatcher<>(Type.INTEGER_TRAIT, traitMatcher));
            return this;
        }
        
        public Builder addEnumTrait(String name, SpongeMatcher<String> traitMatcher) {
            traitMatchers.put(name, new BlockTraitMatcher<>(Type.ENUM_TRAIT, traitMatcher));
            return this;
        }
        
        public Builder data(SpongeMatcher<Optional<DataView>> dataMatcher) {
            this.dataMatcher = Objects.requireNonNull(dataMatcher);
            return this;
        }
        
        public SpongeMatcher<BlockState> build() {
            return new BlockStateMatcher(typeMatcher, traitMatchers, dataMatcher);
        }
    }
}
