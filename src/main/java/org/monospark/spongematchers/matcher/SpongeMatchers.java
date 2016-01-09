package org.monospark.spongematchers.matcher;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.item.Enchantment;

public final class SpongeMatchers {

    private SpongeMatchers() {}

    public static <T extends CatalogType> SpongeMatcher<T> type(String typeName) {
        return type("minecraft", typeName);
    }
    
    public static <T extends CatalogType> SpongeMatcher<T> type(String modName, String typeName) {
        String fullRegex = modName + ":" + typeName;
        return CompoundMatcher.<T>builder()
                .addMatcher(BaseMatchers.regex(fullRegex), t -> t.getId())
                .build();
    }
 
    public static SpongeMatcher<ItemEnchantment> itemEnchantment(SpongeMatcher<Enchantment> enchantment,
            SpongeMatcher<Long> level) {
        return CompoundMatcher.<ItemEnchantment>builder()
                .addMatcher(enchantment, e -> e.getEnchantment())
                .addMatcher(level, e -> (long) e.getLevel())
                .build();
    }
}
