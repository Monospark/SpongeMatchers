package org.monospark.spongematchers.parser;

import java.util.Optional;
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
    public Optional<SpongeMatcher<T>> parse(Matcher matcher) {
        boolean hasModName = matcher.group("mod") != null;
        if (hasModName) {
            return Optional.of(SpongeMatchers.type(matcher.group("mod"), matcher.group("type")));
        } else {
            return Optional.of(SpongeMatchers.type(matcher.group("type")));
        }
    }
}
