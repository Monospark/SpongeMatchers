package org.monospark.spongematchers.type.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.sponge.ItemEnchantmentMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.data.meta.ItemEnchantment;

public final class ItemEnchantmentType extends SpongeObjectType<ItemEnchantment> {

    public ItemEnchantmentType() {
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
