package org.monospark.spongematchers.type.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.sponge.ItemEnchantmentMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.data.meta.ItemEnchantment;

public final class ItemEnchantmentType extends MatcherType<ItemEnchantment> {

    private static final MatcherType<Map<String, Object>> MAP_TYPE = MatcherType.definedMap()
            .addEntry("id", MatcherType.STRING)
            .addEntry("level", MatcherType.INTEGER)
            .build();
    
    public ItemEnchantmentType() {
        super("item enchantment");
    }

    @Override
    public boolean canMatch(Object o) {
        return o instanceof ItemEnchantment;
    }
    
    @Override
    protected boolean canParse(StringElement element, boolean deep) {
        return MAP_TYPE.canParseMatcher(element, deep);
    }
    
    @Override
    protected SpongeMatcher<ItemEnchantment> parse(StringElement element) throws SpongeMatcherParseException {
        SpongeMatcher<Map<String, Object>> matcher = MAP_TYPE.parseMatcher(element);
        return ItemEnchantmentMatcher.create(matcher); 
    }
}
