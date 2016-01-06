package org.monospark.spongematchers.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.SpongeMatchers;
import org.monospark.spongematchers.util.PatternBuilder;
import org.spongepowered.api.CatalogType;

public final class TypeMatcherParser<T extends CatalogType> extends SpongeMatcherParser<T> {

    @Override
    protected Pattern createAcceptanceRegex() {
        return new PatternBuilder()
            .openAnonymousParantheses()
                .appendCapturingPart(".+", "mod")
                .appendNonCapturingPart(":")
            .closeParantheses()
            .optional()
            .appendCapturingPart(".+", "type")
            .build();
    }
    
    @Override
    public SpongeMatcher<T> parse(Matcher matcher) throws SpongeMatcherFormatException {
        boolean hasModName = matcher.group("mod") != null;
        if (hasModName) {
            return SpongeMatchers.type(matcher.group("mod"), matcher.group("type"));
        } else {
            return SpongeMatchers.type(matcher.group("type"));
        }
    }
}
