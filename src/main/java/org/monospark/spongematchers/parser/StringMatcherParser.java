package org.monospark.spongematchers.parser;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.monospark.spongematchers.matcher.BaseMatchers;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.util.PatternBuilder;

public final class StringMatcherParser extends SpongeMatcherParser<String> {

    @Override
    protected Pattern createAcceptanceRegex() {
        return new PatternBuilder()
                .appendNonCapturingPart("'")
                .appendCapturingPart(".+", "regex")
                .appendNonCapturingPart("'")
                .build();
    }

    @Override
    protected Optional<SpongeMatcher<String>> parse(Matcher matcher) {
        try {
            return Optional.of(BaseMatchers.regex(matcher.group("regex")));
        } catch (PatternSyntaxException e) {
            return Optional.empty();
        }
    }
}
