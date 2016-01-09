package org.monospark.spongematchers.parser;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.BaseMatchers;
import org.monospark.spongematchers.matcher.ItemStackMatcher;
import org.monospark.spongematchers.matcher.SpongeMatcher;
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
                    .appendCapturingPart(SpongeMatcherParser.INTEGER.getAcceptanceRegex(), "damage")
                .closeParantheses()
                .optional()
                .openAnonymousParantheses()
                    .appendNonCapturingPart("(")
                    .appendCapturingPart(SpongeMatcherParser.INTEGER.getAcceptanceRegex(), "amount")
                    .appendNonCapturingPart(")")
                .closeParantheses()
                .optional()
                .build();
    }

    @Override
    protected Optional<SpongeMatcher<ItemStack>> parse(Matcher matcher) {
        Optional<SpongeMatcher<ItemType>> type = SpongeMatcherParser.ITEM_TYPE.parseMatcher(matcher.group("type"));
        Optional<SpongeMatcher<Long>> damage = SpongeMatcherParser.INTEGER.parseMatcher(matcher.group("damage"));
        Optional<SpongeMatcher<Long>> amount = SpongeMatcherParser.INTEGER.parseMatcher(matcher.group("amount"));
        
        return type.isPresent() && damage.isPresent() && amount.isPresent() ?
                Optional.of(ItemStackMatcher.create(type.get(), damage.get(), amount.get(), BaseMatchers.wildcard(),
                BaseMatchers.wildcard())) : Optional.empty();
    }
}
