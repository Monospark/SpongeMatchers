package org.monospark.spongematchers.matcher;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.data.meta.ItemEnchantment;

public final class SpongeMatchers {

    private SpongeMatchers() {}

    public static <T extends CatalogType> SpongeMatcher<T> type(String nameRegex) {
        return CompoundMatcher.<T>builder()
                .addMatcher(BaseMatchers.regex(nameRegex), t -> t.getId())
                .build();
    }
 
    public static SpongeMatcher<ItemEnchantment> itemEnchantment(SpongeMatcher<String> enchantment,
            SpongeMatcher<Long> level) {
        return CompoundMatcher.<ItemEnchantment>builder()
                .addMatcher(enchantment, e -> e.getEnchantment().getId())
                .addMatcher(level, e -> (long) e.getLevel())
                .build();
    }
}
