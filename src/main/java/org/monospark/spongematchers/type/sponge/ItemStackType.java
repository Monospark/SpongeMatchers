package org.monospark.spongematchers.type.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.sponge.ItemStackMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.item.inventory.ItemStack;

public final class ItemStackType extends SpongeObjectType<ItemStack> {

    public ItemStackType() {
        super("item stack", ItemStack.class, m -> ItemStackMatcher.create(m));
    }

    @Override
    protected MatcherType<Map<String, Object>> createType() {
        return MatcherType.fixedMap()
                .addEntry("type", MatcherType.STRING)
                .addEntry("durability", MatcherType.INTEGER)
                .addEntry("quantity", MatcherType.INTEGER)
                .addEntry("properties", MatcherType.PROPERTY_HOLDER)
                .addEntry("data", MatcherType.DATA_VIEW)
                .build();
    }
}
