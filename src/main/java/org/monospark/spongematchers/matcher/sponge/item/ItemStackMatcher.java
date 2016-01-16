package org.monospark.spongematchers.matcher.sponge.item;

import java.util.Collections;
import java.util.List;

import org.monospark.spongematchers.matcher.BaseMatchers;
import org.monospark.spongematchers.matcher.CompoundMatcher;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.item.inventory.ItemStack;

public final class ItemStackMatcher {

    private ItemStackMatcher() {}
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static final class Builder {
        
        private SpongeMatcher<String> type;
        
        private SpongeMatcher<Long> damage;
        
        private SpongeMatcher<Long> amount;
        
        private SpongeMatcher<List<ItemEnchantment>> enchantments;

        private SpongeMatcher<DataView> data;
        
        private Builder() {
            damage = BaseMatchers.wildcard();
            amount = BaseMatchers.wildcard();
            enchantments = BaseMatchers.wildcard();
            data = BaseMatchers.wildcard();
        }
        
        public Builder type(SpongeMatcher<String> type) {
            this.type = type;
            return this;
        }
        
        public SpongeMatcher<ItemStack> build() {
            return create(type, damage, amount, enchantments, data);
        }
    }
    
    public static SpongeMatcher<ItemStack> create(SpongeMatcher<String> type, SpongeMatcher<Long> damage,
            SpongeMatcher<Long> amount, SpongeMatcher<List<ItemEnchantment>> enchantments,
            SpongeMatcher<DataView> data) {
        return CompoundMatcher.<ItemStack>builder()
                .addMatcher(type, s -> s.getItem().getId())
                .addMatcher(damage, s -> s.toContainer().getInt(DataQuery.of("UnsafeDamage")).get().longValue())
                .addMatcher(amount, s -> (long) s.getQuantity())
                .addMatcher(enchantments, s -> s.get(Keys.ITEM_ENCHANTMENTS).orElse(Collections.emptyList()))
                .addMatcher(data, s -> s.toContainer().getView(DataQuery.of("UnsafeData")).get())
                .build();
    }
}