package org.monospark.spongematchers.parser.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.base.IntegerMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.util.PatternBuilder;

public final class IntegerMatcherParser extends BaseMatcherParser<Long> {

    IntegerMatcherParser() {
        super("integer", Long.class);
    }

    @Override
    protected Pattern createAcceptancePattern() {
        String integerPattern = "(?:-)?\\d+";
        return new PatternBuilder()
                .openNamedParantheses("range")
                    .appendCapturingPart(integerPattern, "rangestart")
                    .appendNonCapturingPart("\\s*-\\s*")
                    .appendCapturingPart(integerPattern, "rangeend")
                .closeParantheses()
                .or()
                .appendCapturingPart(integerPattern, "value")
                .or()
                .openNamedParantheses("greater")
                    .appendNonCapturingPart(">\\s*")
                    .appendCapturingPart(integerPattern, "greatervalue")
                .closeParantheses()
                .or()
                .openNamedParantheses("greaterorequal")
                    .appendNonCapturingPart(">=\\s*")
                    .appendCapturingPart(integerPattern, "greaterorequalvalue")
                .closeParantheses()
                .or()
                .openNamedParantheses("less")
                    .appendNonCapturingPart("<\\s*")
                    .appendCapturingPart(integerPattern, "lessvalue")
                .closeParantheses()
                .or()
                .openNamedParantheses("lessorequal")
                    .appendNonCapturingPart("<=\\s*")
                    .appendCapturingPart(integerPattern, "lessorequalvalue")
                .closeParantheses()
                .build();
    }
    
    public SpongeMatcher<Long> parse(Matcher matcher) throws SpongeMatcherParseException {
        if (matcher.group("range") != null) {
            long start = parseLong(matcher.group("rangestart"));
            long end = parseLong(matcher.group("rangeend"));
            if (start >= end) {
                throw new SpongeMatcherParseException("The start value of a range must be smaller than the end value");
            }
            return IntegerMatcher.range(start, end);
        } else if (matcher.group("value") != null) {
            long value = parseLong(matcher.group("value"));
            return IntegerMatcher.value(value);
        } else if (matcher.group("greater") != null) {
            return IntegerMatcher.greaterThan(parseLong(matcher.group("greatervalue")));
        } else if (matcher.group("greaterorequal") != null) {
            return IntegerMatcher.greaterThanOrEqual(parseLong(matcher.group("greaterorequalvalue")));
        } else if (matcher.group("less") != null) {
            return IntegerMatcher.lessThan(parseLong(matcher.group("lessvalue")));
        } else {
            return IntegerMatcher.lessThanOrEqual(parseLong(matcher.group("lessorequalvalue")));
        }
    }
    
    private long parseLong(String s) throws SpongeMatcherParseException {
        try {
            return Long.parseLong(s);
        } catch(NumberFormatException e) {
            throw new SpongeMatcherParseException(e);
        }
    }
}
