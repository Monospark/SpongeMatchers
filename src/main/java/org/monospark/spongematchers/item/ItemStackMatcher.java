package org.monospark.spongematchers.item;

import org.monospark.spongematchers.SpongeMatcher;
import org.monospark.spongematchers.base.IntMatcher;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.item.inventory.ItemStack;

public final class ItemStackMatcher implements SpongeMatcher<ItemStack> {

    private ItemTypeMatcher type;
    
    private IntMatcher damage;
    
    private IntMatcher amount;

    private ItemStackMatcher(ItemTypeMatcher typeName, IntMatcher damage, IntMatcher amount) {
        this.type = typeName;
        this.damage = damage;
        this.amount = amount;
    }

    @Override
    public boolean matches(ItemStack stack) {
        return type.matches(stack.getItem()) &&
                damage.matches(stack.toContainer().getInt(DataQuery.of("UnsafeDamage")).get()) &&
                amount.matches(stack.getQuantity());
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static final class Builder {
        
        private ItemTypeMatcher type;
        
        private IntMatcher damage;
        
        private IntMatcher amount;
        
        private Builder() {
            damage = SpongeMatcher.wildcard();
            amount = SpongeMatcher.wildcard();
        }
        
        public Builder type(ItemTypeMatcher type) {
            this.type = type;
            return this;
        }
        
        public Builder damage(IntMatcher damage) {
            this.damage = damage;
            return this;
        }
        
        public Builder amount(IntMatcher amount) {
            this.amount = amount;
            return this;
        }
        
        public ItemStackMatcher build() {
            return new ItemStackMatcher(type, damage, amount);
        }
    }
}
