package org.monospark.spongematchers.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.BaseMatchers;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.SpongeMatchers;
import org.monospark.spongematchers.util.PatternBuilder;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

public final class ItemStackMatcherParser extends SpongeMatcherParser<ItemStack> {
    
    @Override
    protected Pattern createAcceptanceRegex() {
        return new PatternBuilder()
                .appendCapturingPart(SpongeMatcherParser.ITEM_TYPE.getAcceptanceRegex(), "type")
                .openAnonymousParantheses()
                    .appendNonCapturingPart(":")
                    .appendCapturingPart(SpongeMatcherParser.INT.getAcceptanceRegex(), "damage")
                .closeParantheses()
                .optional()
                .openAnonymousParantheses()
                    .appendNonCapturingPart("(")
                    .appendCapturingPart(SpongeMatcherParser.INT.getAcceptanceRegex(), "amount")
                    .appendNonCapturingPart(")")
                .closeParantheses()
                .optional()
                .build();
    }

    @Override
    protected SpongeMatcher<ItemStack> parse(Matcher matcher) {
        SpongeMatcher<ItemType> type = SpongeMatcherParser.ITEM_TYPE.parseMatcherUnsafe(matcher.group("type"));
        SpongeMatcher<Integer> damage = SpongeMatcherParser.INT.parseMatcherUnsafe(matcher.group("damage"));
        SpongeMatcher<Integer> amount = SpongeMatcherParser.INT.parseMatcherUnsafe(matcher.group("amount"));
        
        return SpongeMatchers.itemStack(type, damage, amount, BaseMatchers.wildcard());
    }
}
