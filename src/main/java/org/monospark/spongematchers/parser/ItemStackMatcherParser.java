package org.monospark.spongematchers.parser;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.BaseMatchers;
import org.monospark.spongematchers.matcher.ItemStackMatcher;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.util.PatternBuilder;
import org.spongepowered.api.item.inventory.ItemStack;

public final class ItemStackMatcherParser extends SpongeMatcherParser<ItemStack> {
    
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
        Optional<SpongeMatcher<String>> type = SpongeMatcherParser.STRING.parseMatcher(matcher.group("type"));
        Optional<SpongeMatcher<Long>> damage = SpongeMatcherParser.INTEGER.parseMatcher(matcher.group("damage"));
        Optional<SpongeMatcher<Long>> amount = SpongeMatcherParser.INTEGER.parseMatcher(matcher.group("amount"));
        
        if (type.isPresent() && damage.isPresent() && amount.isPresent()) {
                return ItemStackMatcher.create(type.get(), damage.get(), amount.get(), BaseMatchers.wildcard(),
                        BaseMatchers.wildcard());
        } else {
            throw new SpongeMatcherParseException("Invalid item stack matcher: " + matcher.group());
        }
    }
}
