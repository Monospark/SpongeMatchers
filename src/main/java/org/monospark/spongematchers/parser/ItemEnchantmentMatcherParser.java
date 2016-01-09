package org.monospark.spongematchers.parser;

import java.util.Optional;
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
    protected Optional<SpongeMatcher<ItemEnchantment>> parse(Matcher matcher) {
        Optional<SpongeMatcher<Enchantment>> enchantment =
                SpongeMatcherParser.ENCHANTMENT.parseMatcher(matcher.group("type"));
        Optional<SpongeMatcher<Long>> level = matcher.group("level") != null ?
                SpongeMatcherParser.INTEGER.parseMatcher(matcher.group("level")) : BaseMatchers.wildcard();
        
        return enchantment.isPresent() && level.isPresent() ?
                Optional.of(SpongeMatchers.itemEnchantment(enchantment.get(), level.get())) : Optional.empty();
    }
}
