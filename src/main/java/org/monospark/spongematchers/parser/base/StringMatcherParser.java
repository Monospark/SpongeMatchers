package org.monospark.spongematchers.parser.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.base.StringMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.util.PatternBuilder;

public final class StringMatcherParser extends BaseMatcherParser<String> {

    StringMatcherParser() {
        super("string", String.class);
    }

    @Override
    protected Pattern createAcceptancePattern() {
        return new PatternBuilder()
                .appendCapturingPart("r", "regex")
                .optional()
                .appendNonCapturingPart("'")
                .appendCapturingPart("(\\'|.)+?", "content")
                .appendNonCapturingPart("'")
                .build();
    }

    @Override
    protected SpongeMatcher<String> parse(Matcher matcher) throws SpongeMatcherParseException {
        boolean isRegex = matcher.group("regex") != null;
        String replacedSingleQuotes = matcher.group("content").replace("\\'", "'");
        try {
            return isRegex ? StringMatcher.regex(replacedSingleQuotes) : StringMatcher.literal(replacedSingleQuotes);
        } catch (PatternSyntaxException e) {
            throw new SpongeMatcherParseException(e);
        }
    }
}
