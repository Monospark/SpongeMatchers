package org.monospark.spongematchers.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.BaseMatchers;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.SpongeMatchers;
import org.monospark.spongematchers.util.PatternBuilder;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.item.Enchantment;

public class ItemEnchantmentMatcherParser extends SpongeMatcherParser<ItemEnchantment> {

    @Override
    protected Pattern createAcceptanceRegex() {
        return new PatternBuilder()
                .appendCapturingPart(SpongeMatcherParser.ENCHANTMENT.getAcceptanceRegex(), "enchantment")
                .openAnonymousParantheses()
                    .appendNonCapturingPart("(")
                    .appendCapturingPart(SpongeMatcherParser.INTEGER.getAcceptanceRegex(), "level")
                    .appendNonCapturingPart(")")
                .closeParantheses()
                .optional()
                .build();
    }

    @Override
    protected SpongeMatcher<ItemEnchantment> parse(Matcher matcher) throws SpongeMatcherFormatException {
        SpongeMatcher<Enchantment> enchantment =
                SpongeMatcherParser.ENCHANTMENT.parseMatcherUnsafe(matcher.group("type"));
        SpongeMatcher<Integer> level = matcher.group("level") != null ?
                SpongeMatcherParser.INTEGER.parseMatcherUnsafe(matcher.group("level")) : BaseMatchers.wildcard();
        
        return SpongeMatchers.itemEnchantment(enchantment, level);
    }
}
