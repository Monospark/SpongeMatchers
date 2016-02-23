package org.monospark.spongematchers.type;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.sponge.ItemStackMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.spongepowered.api.item.inventory.ItemStack;

public final class ItemStackType extends MatcherType<ItemStack> {

    private static final MatcherType<Map<String, Object>> TYPE = MatcherType.definedMap()
            .addEntry("type", MatcherType.STRING)
            .addEntry("durability", MatcherType.INTEGER)
            .addEntry("quantity", MatcherType.INTEGER)
            .addEntry("properties", MatcherType.PROPERTY_HOLDER)
            .addEntry("data", MatcherType.DATA_VIEW)
            .build();

    ItemStackType() {}

    @Override
    public boolean canMatch(Object o) {
        return o instanceof ItemStack;
    }

    @Override
    protected boolean checkElement(StringElement element) {
        return TYPE.acceptsElement(element);
    }

    @Override
    protected SpongeMatcher<ItemStack> parse(StringElement element) throws SpongeMatcherParseException {
        try {
            return ItemStackMatcher.create(TYPE.parseMatcher(element));
        } catch (SpongeMatcherParseException e) {
            throw new SpongeMatcherParseException("Couldn't parse item stack matcher: " + element.getString(), e);
        }
    }
}
