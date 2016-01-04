package org.monospark.spongematchers.item;

import java.util.regex.Pattern;

import org.monospark.spongematchers.SpongeMatcherParser;
import org.monospark.spongematchers.SpongeMatcherType;
import org.monospark.spongematchers.base.McNameMatcher;
import org.monospark.spongematchers.util.PatternBuilder;

public final class ItemTypeMatcherParser extends SpongeMatcherParser<ItemTypeMatcher> {

    @Override
    protected Pattern createAcceptanceRegex() {
        return new PatternBuilder()
            .openAnonymousParantheses()
                .appendCapturingPart(SpongeMatcherType.NAME.getAcceptanceRegex(), "mod")
                .appendNonCapturingPart(":")
            .closeParantheses()
            .optional()
            .appendCapturingPart(SpongeMatcherType.NAME.getAcceptanceRegex(), "type")
            .build();
    }
    
    @Override
    public ItemTypeMatcher parse(java.util.regex.Matcher matcher) {
        McNameMatcher modName = SpongeMatcherType.NAME.getParser().parseMatcherUnsafe(matcher.group("mod"));
        McNameMatcher typeName = SpongeMatcherType.NAME.getParser().parseMatcherUnsafe(matcher.group("type"));
        return new ItemTypeMatcher(modName  != null ? modName : new McNameMatcher("minecraft"), typeName);
    }
}
