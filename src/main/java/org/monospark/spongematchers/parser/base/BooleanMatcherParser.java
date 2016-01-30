package org.monospark.spongematchers.parser.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.base.BooleanMatcher;
import org.monospark.spongematchers.util.PatternBuilder;

public final class BooleanMatcherParser extends BaseMatcherParser<Boolean> {

    BooleanMatcherParser() {
        super("boolean", Boolean.class);
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
            return BooleanMatcher.trueValue();
        } else  {
            return BooleanMatcher.falseValue();
        }
    }
}
