package org.monospark.spongematchers.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.monospark.spongematchers.matcher.BaseMatchers;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.util.PatternBuilder;

public final class StringMatcherParser extends SpongeMatcherParser<String> {

    @Override
    protected Pattern createAcceptancePattern() {
        return new PatternBuilder()
                .appendNonCapturingPart("'")
                .appendCapturingPart(".+", "regex")
                .appendNonCapturingPart("'")
                .build();
    }

    @Override
    protected SpongeMatcher<String> parse(Matcher matcher) throws SpongeMatcherParseException {
        try {
            return BaseMatchers.regex(matcher.group("regex"));
        } catch (PatternSyntaxException e) {
            throw new SpongeMatcherParseException(e);
        }
    }
}
