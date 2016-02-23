package org.monospark.spongematchers.type;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.sponge.ItemEnchantmentMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.spongepowered.api.data.meta.ItemEnchantment;

public final class ItemEnchantmentType extends MatcherType<ItemEnchantment> {

    private static final MatcherType<Map<String, Object>> TYPE = MatcherType.definedMap()
            .addEntry("id", MatcherType.STRING)
            .addEntry("level", MatcherType.INTEGER)
            .build();

    ItemEnchantmentType() {}

    @Override
    public boolean canMatch(Object o) {
        return o instanceof ItemEnchantment;
    }

    @Override
    protected boolean checkElement(StringElement element) {
        return TYPE.acceptsElement(element);
    }

    @Override
    protected SpongeMatcher<ItemEnchantment> parse(StringElement element) throws SpongeMatcherParseException {
        try {
            return ItemEnchantmentMatcher.create(TYPE.parseMatcher(element));
        } catch (SpongeMatcherParseException e) {
            throw new SpongeMatcherParseException("Couldn't parse item enchantment matcher: " + element.getString(), e);
        }
    }
}
