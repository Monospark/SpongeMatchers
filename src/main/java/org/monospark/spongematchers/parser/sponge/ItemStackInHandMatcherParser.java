package org.monospark.spongematchers.parser.sponge;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.sponge.item.ItemStackInHandMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.SpongeMatcherParser;
import org.monospark.spongematchers.util.PatternBuilder;
import org.spongepowered.api.item.inventory.ItemStack;

public final class ItemStackInHandMatcherParser extends SpongeMatcherParser<Optional<ItemStack>> {

    public ItemStackInHandMatcherParser() {
        super("item stack or hand");
    }

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
            SpongeMatcher<ItemStack> stackMatcher = SpongeMatcherParser.ITEM_STACK.parseMatcher(stackPart);
            return ItemStackInHandMatcher.itemStack(stackMatcher);
        } else {
            return ItemStackInHandMatcher.hand();
        }
    }
}
