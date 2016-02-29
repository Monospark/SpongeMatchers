package org.monospark.spongematchers.matcher.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.FixedMapMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.data.meta.ItemEnchantment;

public final class ItemEnchantmentMatcher extends SpongeObjectMatcher<ItemEnchantment> {

    public static SpongeMatcher<ItemEnchantment> create(SpongeMatcher<String> id, SpongeMatcher<Long> level) {
        return new ItemEnchantmentMatcher(FixedMapMatcher.builder()
                .addMatcher("id", id)
                .addMatcher("level", level)
                .build());
    }

    public static ItemEnchantmentMatcher create(SpongeMatcher<Map<String, Object>> matcher) {
        return new ItemEnchantmentMatcher(matcher);
    }

    private ItemEnchantmentMatcher(SpongeMatcher<Map<String, Object>> matcher) {
        super(MatcherType.ITEM_ENCHANTMENT, matcher);
    }

    @Override
    protected void fillMap(ItemEnchantment o, Map<String, Object> map) {
        map.put("id", o.getEnchantment().getId());
        map.put("level", (long) o.getLevel());
    }
}
