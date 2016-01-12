package org.monospark.spongematchers.parser;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.BaseMatchers;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.SpongeMatchers;
import org.monospark.spongematchers.util.PatternBuilder;
import org.spongepowered.api.data.meta.ItemEnchantment;

public class ItemEnchantmentMatcherParser extends SpongeMatcherParser<ItemEnchantment> {

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
        Optional<SpongeMatcher<String>> enchantment =
                SpongeMatcherParser.STRING.parseMatcher(matcher.group("enchantment"));
        Optional<SpongeMatcher<Long>> level = matcher.group("level") != null ?
                SpongeMatcherParser.INTEGER.parseMatcher(matcher.group("level")) : BaseMatchers.wildcard();
        
        if (enchantment.isPresent() && level.isPresent()) {
            return SpongeMatchers.itemEnchantment(enchantment.get(), level.get());
        } else {
            throw new SpongeMatcherParseException("Invalid item enchantment matcher: " + matcher.group());
        }
    }
}
