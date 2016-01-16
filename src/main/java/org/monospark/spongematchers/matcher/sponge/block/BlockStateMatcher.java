package org.monospark.spongematchers.matcher.sponge.block;

import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.Optional;

import org.monospark.spongematchers.matcher.BaseMatchers;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.sponge.block.BlockTraitMatcher.Type;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.trait.BlockTrait;

import com.google.common.collect.Maps;

public final class BlockStateMatcher implements SpongeMatcher<BlockState> {

    private SpongeMatcher<BlockType> typeMatcher;
    
    private Map<String, BlockTraitMatcher<?>> traitMatchers;

    private BlockStateMatcher(SpongeMatcher<BlockType> typeMatcher, Map<String, BlockTraitMatcher<?>> traitMatchers) {
        this.typeMatcher = typeMatcher;
        this.traitMatchers = traitMatchers;
    }

    @Override
    public boolean matches(BlockState o) {
        if (!typeMatcher.matches(o.getType())) {
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

        public Builder() {
            typeMatcher = BaseMatchers.wildcard();
            traitMatchers = Maps.newHashMap();
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
        
        public SpongeMatcher<BlockState> build() {
            return new BlockStateMatcher(typeMatcher, traitMatchers);
        }
    }
}
