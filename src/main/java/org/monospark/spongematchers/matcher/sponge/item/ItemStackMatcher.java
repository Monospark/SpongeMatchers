package org.monospark.spongematchers.matcher.sponge.item;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.CompoundMatcher;
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
        
        private SpongeMatcher<String> typeMatcher;
        
        private SpongeMatcher<Long> amountMatcher;
        
        private SpongeMatcher<Long> amount;
        
        private SpongeMatcher<List<ItemEnchantment>> enchantmentsMatcher;

        private SpongeMatcher<Optional<DataView>> dataMatcher;
        
        private Builder() {
            amountMatcher = SpongeMatcher.wildcard();
            amount = SpongeMatcher.wildcard();
            enchantmentsMatcher = SpongeMatcher.wildcard();
            dataMatcher = SpongeMatcher.wildcard();
        }
        
        public Builder type(SpongeMatcher<String> typeMatcher) {
            this.typeMatcher = typeMatcher;
            return this;
        }
        
        public Builder damage(SpongeMatcher<Long> damageMatcher) {
            this.amountMatcher = damageMatcher;
            return this;
        }
        
        public Builder amount(SpongeMatcher<Long> amountMatcher) {
            this.amountMatcher = amountMatcher;
            return this;
        }
        
        public Builder enchantments(SpongeMatcher<List<ItemEnchantment>> enchantmentsMatcher) {
            this.enchantmentsMatcher = enchantmentsMatcher;
            return this;
        }
        
        public Builder data(SpongeMatcher<Optional<DataView>> dataMatcher) {
            this.dataMatcher = dataMatcher;
            return this;
        }
        
        public SpongeMatcher<ItemStack> build() {
            return create(typeMatcher, amountMatcher, amount, enchantmentsMatcher, dataMatcher);
        }
    }
    
    public static SpongeMatcher<ItemStack> create(SpongeMatcher<String> type, SpongeMatcher<Long> damage,
            SpongeMatcher<Long> amount, SpongeMatcher<List<ItemEnchantment>> enchantments,
            SpongeMatcher<Optional<DataView>> data) {
        return CompoundMatcher.<ItemStack>builder()
                .addMatcher(type, s -> s.getItem().getId())
                .addMatcher(damage, s -> s.toContainer().getInt(DataQuery.of("UnsafeDamage")).get().longValue())
                .addMatcher(amount, s -> (long) s.getQuantity())
                .addMatcher(enchantments, s -> s.get(Keys.ITEM_ENCHANTMENTS).orElse(Collections.emptyList()))
                .addMatcher(data, s -> s.toContainer().getView(DataQuery.of("UnsafeData")))
                .build();
    }
}
