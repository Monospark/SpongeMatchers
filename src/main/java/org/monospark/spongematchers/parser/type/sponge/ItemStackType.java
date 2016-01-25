package org.monospark.spongematchers.parser.type.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.sponge.item.ItemStackMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.monospark.spongematchers.parser.type.MatcherType;
import org.spongepowered.api.item.inventory.ItemStack;

public final class ItemStackType extends MatcherType<ItemStack> {

    private static final MatcherType<Map<String, Object>> MAP_TYPE = MatcherType.map()
            .addEntry("type", MatcherType.STRING)
            .addEntry("damage", MatcherType.INTEGER)
            .addEntry("amount", MatcherType.INTEGER)
            .build();
    
    public ItemStackType() {
        super("item stack", ItemStack.class);
    }

    @Override
    public boolean canParse(StringElement element, boolean deep) {
        return MAP_TYPE.canParse(element, deep);
    }
    
    @Override
    protected SpongeMatcher<ItemStack> parse(StringElement element) throws SpongeMatcherParseException {
        SpongeMatcher<Map<String, Object>> matcher = MAP_TYPE.parseMatcher(element);
        return ItemStackMatcher.create(matcher);  
    }
}
