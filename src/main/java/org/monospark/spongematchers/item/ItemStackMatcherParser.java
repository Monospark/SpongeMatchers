package org.monospark.spongematchers.item;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.MatcherParser;
import org.monospark.spongematchers.MatcherType;
import org.monospark.spongematchers.base.IntMatcher;
import org.monospark.spongematchers.item.ItemStackMatcher.Builder;
import org.monospark.spongematchers.util.PatternBuilder;

public final class ItemStackMatcherParser extends MatcherParser<ItemStackMatcher> {
    
    @Override
    protected Pattern createAcceptanceRegex() {
        return new PatternBuilder()
                .appendCapturingPart(MatcherType.ITEM_TYPE.getAcceptanceRegex(), "type")
                .openAnonymousParantheses()
                    .appendNonCapturingPart(":")
                    .appendCapturingPart(MatcherType.INT.getAcceptanceRegex(), "damage")
                .closeParantheses()
                .optional()
                .openAnonymousParantheses()
                    .appendNonCapturingPart("(")
                    .appendCapturingPart(MatcherType.INT.getAcceptanceRegex(), "amount")
                    .appendNonCapturingPart(")")
                .closeParantheses()
                .optional()
                .build();
    }

    @Override
    protected ItemStackMatcher parse(Matcher matcher) {
        ItemTypeMatcher type = MatcherType.ITEM_TYPE.getParser().parseMatcherUnsafe(matcher.group("type"));
        IntMatcher damage = MatcherType.INT.getParser().parseMatcherUnsafe(matcher.group("damage"));
        IntMatcher amount = MatcherType.INT.getParser().parseMatcherUnsafe(matcher.group("amount"));
        
        Builder builder = ItemStackMatcher.builder().type(type);
        if (damage != null) {
            builder.damage(damage);
        }
        if (amount != null) {
            builder.amount(amount);
        }
        return builder.build();
    }
}
