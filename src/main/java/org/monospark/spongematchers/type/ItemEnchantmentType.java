package org.monospark.spongematchers.type;

import java.util.Map;

import org.monospark.spongematchers.matcher.sponge.ItemEnchantmentMatcher;
import org.spongepowered.api.data.meta.ItemEnchantment;

public final class ItemEnchantmentType extends SpongeObjectType<ItemEnchantment> {

    ItemEnchantmentType() {
        super("item enchantment", ItemEnchantment.class, m -> ItemEnchantmentMatcher.create(m));
    }

    @Override
    protected MatcherType<Map<String, Object>> createType() {
        return MatcherType.fixedMap()
                .addEntry("id", MatcherType.STRING)
                .addEntry("level", MatcherType.INTEGER)
                .build();
    }
}
