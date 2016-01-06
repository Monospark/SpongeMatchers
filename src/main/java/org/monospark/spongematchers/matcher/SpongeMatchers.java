package org.monospark.spongematchers.matcher;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.item.Enchantment;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

public final class SpongeMatchers {

    private SpongeMatchers() {}

    public static <T extends CatalogType> SpongeMatcher<T> type(String itemName) {
        return type("minecraft", itemName);
    }
    
    public static <T extends CatalogType> SpongeMatcher<T> type(String modName, String itemName) {
        String fullRegex = modName + ":" + itemName;
        return CompoundMatcher.<T>builder()
                .addMatcher(BaseMatchers.regex(fullRegex), t -> t.getId())
                .build();
    }
    
    public static SpongeMatcher<ItemStack> itemStack(SpongeMatcher<ItemType> type, SpongeMatcher<Integer> damage,
            SpongeMatcher<Integer> amount, SpongeMatcher<ItemEnchantment> enchantment) {
        return CompoundMatcher.<ItemStack>builder()
                .addMatcher(type, s -> s.getItem())
                .addMatcher(damage, s -> s.toContainer().getInt(DataQuery.of("UnsafeDamage")).get())
                .addMatcher(amount, s -> s.getQuantity())
                .addMatcher(BaseMatchers.optionalWrapper(BaseMatchers.listWrapper(enchantment, true)),
                        s -> s.get(Keys.ITEM_ENCHANTMENTS))
                .build();
    }
    
    public static SpongeMatcher<ItemEnchantment> itemEnchantment(SpongeMatcher<Enchantment> enchantment,
            SpongeMatcher<Integer> level) {
        return CompoundMatcher.<ItemEnchantment>builder()
                .addMatcher(enchantment, e -> e.getEnchantment())
                .addMatcher(level, e -> e.getLevel())
                .build();
    }
}
