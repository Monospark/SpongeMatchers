package org.monospark.spongematchers.item;

import java.util.regex.Pattern;

import org.monospark.spongematchers.MatcherParser;
import org.monospark.spongematchers.MatcherType;
import org.monospark.spongematchers.base.NameMatcher;
import org.monospark.spongematchers.util.PatternBuilder;

public final class ItemTypeMatcherParser extends MatcherParser<ItemTypeMatcher> {

    @Override
    protected Pattern createAcceptanceRegex() {
        return new PatternBuilder()
            .openAnonymousParantheses()
                .appendCapturingPart(MatcherType.NAME.getAcceptanceRegex(), "mod")
                .appendNonCapturingPart(":")
            .closeParantheses()
            .optional()
            .appendCapturingPart(MatcherType.NAME.getAcceptanceRegex(), "type")
            .build();
    }
    
    @Override
    public ItemTypeMatcher parse(java.util.regex.Matcher matcher) {
        NameMatcher modName = MatcherType.NAME.getParser().parseMatcherUnsafe(matcher.group("mod"));
        NameMatcher typeName = MatcherType.NAME.getParser().parseMatcherUnsafe(matcher.group("type"));
        return new ItemTypeMatcher(modName  != null ? modName : new NameMatcher("minecraft"), typeName);
    }
}
