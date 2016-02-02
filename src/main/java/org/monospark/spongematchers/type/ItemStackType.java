package org.monospark.spongematchers.type;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.sponge.ItemStackMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.spongepowered.api.item.inventory.ItemStack;

public final class ItemStackType extends MatcherType<ItemStack> {

    private static final MatcherType<Map<String, Object>> MAP_TYPE = MatcherType.definedMap()
            .addEntry("type", MatcherType.STRING)
            .addEntry("durability", MatcherType.INTEGER)
            .addEntry("quantity", MatcherType.INTEGER)
            .addEntry("data", MatcherType.DATA_VIEW)
            .build();

    ItemStackType() {
        super("item stack");
    }

    @Override
    public boolean canMatch(Object o) {
        return o instanceof ItemStack;
    }

    @Override
    protected boolean canParse(StringElement element, boolean deep) {
        return MAP_TYPE.canParseMatcher(element, deep);
    }

    @Override
    protected SpongeMatcher<ItemStack> parse(StringElement element) throws SpongeMatcherParseException {
        SpongeMatcher<Map<String, Object>> matcher = MAP_TYPE.parseMatcher(element);
        return ItemStackMatcher.create(matcher);
    }
}
