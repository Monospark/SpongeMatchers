package org.monospark.spongematchers.parser.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.base.BooleanMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParser;
import org.monospark.spongematchers.util.PatternBuilder;

public final class BooleanMatcherParser extends SpongeMatcherParser<Boolean> {

    public BooleanMatcherParser() {
        super("boolean");
    }

    @Override
    protected Pattern createAcceptancePattern() {
        return new PatternBuilder()
                .appendCapturingPart("true", "true")
                .or()
                .appendCapturingPart("false", "false")
                .build();
    }

    @Override
    protected SpongeMatcher<Boolean> parse(Matcher matcher) {
        if (matcher.group("true") != null) {
            return BooleanMatcher.trueOnly();
        } else  {
            return BooleanMatcher.falseOnly();
        }
    }
}