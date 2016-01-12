package org.monospark.spongematchers.parser.primitive;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.BaseMatchers;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.primitive.BooleanMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParser;
import org.monospark.spongematchers.util.PatternBuilder;

public final class BooleanMatcherParser extends SpongeMatcherParser<Boolean> {

    @Override
    protected Pattern createAcceptancePattern() {
        return new PatternBuilder()
                .appendCapturingPart("true", "true")
                .or()
                .appendCapturingPart("false", "false")
                .or()
                .openNamedParantheses("both")
                    .openAnonymousParantheses()
                        .appendNonCapturingPart("true\\s*\\|\\s*false")
                    .closeParantheses()
                    .or()
                    .openAnonymousParantheses()
                        .appendNonCapturingPart("false\\s*\\|\\s*true")
                    .closeParantheses()
                .closeParantheses()
                .build();
    }

    @Override
    protected SpongeMatcher<Boolean> parse(Matcher matcher) {
        if (matcher.group("true") != null) {
            return BooleanMatcher.trueOnly();
        } else if (matcher.group("false") != null) {
            return BooleanMatcher.falseOnly();
        } else {
            return BaseMatchers.wildcard();
        }
    }
}
