package org.monospark.spongematchers.parser.sponge;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.BaseMatchers;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.sponge.ItemEnchantmentMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.SpongeMatcherParser;
import org.monospark.spongematchers.util.PatternBuilder;
import org.spongepowered.api.data.meta.ItemEnchantment;

public class ItemEnchantmentMatcherParser extends SpongeMatcherParser<ItemEnchantment> {

    public ItemEnchantmentMatcherParser() {
        super("item enchantment");
    }

    @Override
    protected Pattern createAcceptancePattern() {
        return new PatternBuilder()
                .appendCapturingPart(".+", "enchantment")
                .openAnonymousParantheses()
                    .appendNonCapturingPart("(")
                    .appendCapturingPart(".+", "level")
                    .appendNonCapturingPart(")")
                .closeParantheses()
                .optional()
                .build();
    }

    @Override
    protected SpongeMatcher<ItemEnchantment> parse(Matcher matcher) throws SpongeMatcherParseException {
        SpongeMatcher<String> enchantment = SpongeMatcherParser.STRING.parseMatcher(matcher.group("enchantment"));
        SpongeMatcher<Long> level = matcher.group("level") != null ?
                SpongeMatcherParser.INTEGER.parseMatcher(matcher.group("level")) : BaseMatchers.wildcard();
        return ItemEnchantmentMatcher.create(enchantment, level);
    }
}
