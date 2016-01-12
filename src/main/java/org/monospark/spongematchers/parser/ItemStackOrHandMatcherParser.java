package org.monospark.spongematchers.parser;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.util.PatternBuilder;
import org.spongepowered.api.item.inventory.ItemStack;

public class ItemStackOrHandMatcherParser extends SpongeMatcherParser<Optional<ItemStack>> {

    @Override
    protected Pattern createAcceptancePattern() {
        return new PatternBuilder()
                .appendCapturingPart("none", "hand")
                .or()
                .appendCapturingPart(".+", "stack")
                .build();
    }

    @Override
    protected SpongeMatcher<Optional<ItemStack>> parse(Matcher matcher) throws SpongeMatcherParseException {
        String stackPart = matcher.group("stack");
        if (stackPart != null) {
            Optional<SpongeMatcher<ItemStack>> stackMatcher = SpongeMatcherParser.ITEM_STACK.parseMatcher(stackPart);
            if (!stackMatcher.isPresent()) {
                throw new SpongeMatcherParseException("Invalid item stack matcher: " + matcher.group());
            }
            
            return new SpongeMatcher<Optional<ItemStack>>() {
                @Override
                public boolean matches(Optional<ItemStack> o) {
                    return o.isPresent() ? stackMatcher.get().matches(o.get()) : false;
                } 
            };
        } else {
            return new SpongeMatcher<Optional<ItemStack>>() {
                @Override
                public boolean matches(Optional<ItemStack> o) {
                    return !o.isPresent();
                } 
            };
        }
    }
}
