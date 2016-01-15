package org.monospark.spongematchers.parser.sponge;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.BaseMatchers;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.sponge.ItemStackMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.SpongeMatcherParser;
import org.monospark.spongematchers.util.PatternBuilder;
import org.spongepowered.api.item.inventory.ItemStack;

public final class ItemStackMatcherParser extends SpongeMatcherParser<ItemStack> {
    
    public ItemStackMatcherParser() {
        super("item stack");
    }

    @Override
    protected Pattern createAcceptancePattern() {
        return new PatternBuilder()
                .appendCapturingPart(".+", "type")
                .openAnonymousParantheses()
                    .appendNonCapturingPart("/")
                    .appendCapturingPart(".+", "damage")
                .closeParantheses()
                .optional()
                .openAnonymousParantheses()
                    .appendNonCapturingPart("(")
                    .appendCapturingPart(".+", "amount")
                    .appendNonCapturingPart(")")
                .closeParantheses()
                .openAnonymousParantheses()
                    .appendNonCapturingPart("<")
                    .appendCapturingPart(".+", "enchantments")
                    .appendNonCapturingPart(">")
                .closeParantheses()
                .optional()
                .build();
    }

    @Override
    protected SpongeMatcher<ItemStack> parse(Matcher matcher) throws SpongeMatcherParseException {
        SpongeMatcher<String> type = SpongeMatcherParser.STRING.parseMatcher(matcher.group("type"));
        SpongeMatcher<Long> damage = SpongeMatcherParser.INTEGER.parseMatcher(matcher.group("damage"));
        SpongeMatcher<Long> amount = SpongeMatcherParser.INTEGER.parseMatcher(matcher.group("amount"));
        return ItemStackMatcher.create(type, damage, amount, BaseMatchers.wildcard(), BaseMatchers.wildcard());
    }
}
