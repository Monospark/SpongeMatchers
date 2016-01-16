package org.monospark.spongematchers.matcher.sponge.item;

import java.util.Optional;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.spongepowered.api.item.inventory.ItemStack;

public final class ItemStackInHandMatcher {

    private ItemStackInHandMatcher() {}
    
    public static SpongeMatcher<Optional<ItemStack>> itemStack(SpongeMatcher<ItemStack> stackMatcher) {
        return create(stackMatcher, false);
    }
    
    public static SpongeMatcher<Optional<ItemStack>> create(SpongeMatcher<ItemStack> stackMatcher, boolean matchHand) {
        return new SpongeMatcher<Optional<ItemStack>>() {
            @Override
            public boolean matches(Optional<ItemStack> o) {
                return o.isPresent() ? stackMatcher.matches(o.get()) : matchHand;
            } 
        };
    }
    
    public static SpongeMatcher<Optional<ItemStack>> hand() {
        return new SpongeMatcher<Optional<ItemStack>>() {
            @Override
            public boolean matches(Optional<ItemStack> o) {
                return !o.isPresent();
            } 
        };
    }
}
