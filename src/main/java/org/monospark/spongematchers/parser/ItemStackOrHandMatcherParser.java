package org.monospark.spongematchers.parser;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.util.PatternBuilder;
import org.spongepowered.api.item.inventory.ItemStack;

public class ItemStackOrHandMatcherParser extends SpongeMatcherParser<Optional<ItemStack>> {

    @Override
    protected Pattern createAcceptanceRegex() {
        return new PatternBuilder()
                .appendCapturingPart(SpongeMatcherParser.ITEM_STACK.getAcceptanceRegex(), "stack")
                .or()
                .appendCapturingPart("none", "hand")
                .build();
    }

    @Override
    protected Optional<SpongeMatcher<Optional<ItemStack>>> parse(Matcher matcher) {
        String stackPart = matcher.group("stack");
        if (stackPart != null) {
            Optional<SpongeMatcher<ItemStack>> stackMatcher = SpongeMatcherParser.ITEM_STACK.parseMatcher(stackPart);
            if (!stackMatcher.isPresent()) {
                return Optional.empty();
            }
            
            return Optional.of(new SpongeMatcher<Optional<ItemStack>>() {
                @Override
                public boolean matches(Optional<ItemStack> o) {
                    return o.isPresent() ? stackMatcher.get().matches(o.get()) : false;
                } 
            });
        } else {
            return Optional.of(new SpongeMatcher<Optional<ItemStack>>() {
                @Override
                public boolean matches(Optional<ItemStack> o) {
                    return !o.isPresent();
                } 
            });
        }
    }
}
