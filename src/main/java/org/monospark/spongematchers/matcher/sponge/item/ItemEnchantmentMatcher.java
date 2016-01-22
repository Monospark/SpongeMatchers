package org.monospark.spongematchers.matcher.sponge.item;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.CompoundMatcher;
import org.spongepowered.api.data.meta.ItemEnchantment;

public final class ItemEnchantmentMatcher {

    private ItemEnchantmentMatcher() {}
    
    public static SpongeMatcher<ItemEnchantment> create(SpongeMatcher<String> enchantment) {
        return create(enchantment, SpongeMatcher.wildcard());
    }
    
    public static SpongeMatcher<ItemEnchantment> create(SpongeMatcher<String> enchantment,
            SpongeMatcher<Long> level) {
        return CompoundMatcher.<ItemEnchantment>builder()
                .addMatcher(enchantment, e -> e.getEnchantment().getId())
                .addMatcher(level, e -> (long) e.getLevel())
                .build();
    }
}
