package org.monospark.spongematchers.parser.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.base.FloatingPointMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.util.PatternBuilder;

public final class FloatingPointMatcherParser extends BaseMatcherParser<Double> {

    FloatingPointMatcherParser() {
        super("floating point number");
    }

    @Override
    protected Pattern createAcceptancePattern() {
        Pattern floatingPointPattern = new PatternBuilder()
                .appendNonCapturingPart("-")
                .optional()
                .openAnonymousParantheses()
                    .openAnonymousParantheses()
                        .openAnonymousParantheses()
                            .appendNonCapturingPart("\\d+")
                        .closeParantheses()
                        .optional()
                        .appendNonCapturingPart("\\.\\d+")
                    .closeParantheses()
                    .or()
                    .openAnonymousParantheses()
                        .appendNonCapturingPart("\\d+")
                    .closeParantheses()
                .closeParantheses()
                .openAnonymousParantheses()
                    .appendNonCapturingPart("f")
                    .or()
                    .appendNonCapturingPart("F")
                 .closeParantheses()
                .build();
        return new PatternBuilder()
                .openNamedParantheses("range")
                    .appendCapturingPart(floatingPointPattern, "rangestart")
                    .appendNonCapturingPart("\\s*-\\s*")
                    .appendCapturingPart(floatingPointPattern, "rangeend")
                .closeParantheses()
                .or()
                .appendCapturingPart(floatingPointPattern, "value")
                .or()
                .openNamedParantheses("greater")
                    .appendNonCapturingPart(">\\s*")
                    .appendCapturingPart(floatingPointPattern, "greatervalue")
                .closeParantheses()
                .or()
                .openNamedParantheses("greaterorequal")
                    .appendNonCapturingPart(">=\\s*")
                    .appendCapturingPart(floatingPointPattern, "greaterorequalvalue")
                .closeParantheses()
                .or()
                .openNamedParantheses("less")
                    .appendNonCapturingPart("<\\s*")
                    .appendCapturingPart(floatingPointPattern, "lessvalue")
                .closeParantheses()
                .or()
                .openNamedParantheses("lessorequal")
                    .appendNonCapturingPart("<=\\s*")
                    .appendCapturingPart(floatingPointPattern, "lessorequalvalue")
                .closeParantheses()
                .build();
    }
    
    public SpongeMatcher<Double> parse(Matcher matcher) throws SpongeMatcherParseException {
        if (matcher.group("range") != null) {
            double start = parseDouble(matcher.group("rangestart"));
            double end = parseDouble(matcher.group("rangeend"));
            if (start >= end) {
                throw new SpongeMatcherParseException("The start value of a range must be smaller than the end value");
            }
            return FloatingPointMatcher.range(start, end);
        } else if (matcher.group("value") != null) {
            double value = parseDouble(matcher.group("value"));
            return FloatingPointMatcher.value(value);
        } else if (matcher.group("greater") != null) {
            return FloatingPointMatcher.greaterThan(parseDouble(matcher.group("greatervalue")));
        } else if (matcher.group("greaterorequal") != null) {
            return FloatingPointMatcher.greaterThanOrEqual(parseDouble(matcher.group("greaterorequalvalue")));
        } else if (matcher.group("less") != null) {
            return FloatingPointMatcher.lessThan(parseDouble(matcher.group("lessvalue")));
        } else {
            return FloatingPointMatcher.lessThanOrEqual(parseDouble(matcher.group("lessorequalvalue")));
        }
    }
    
    private double parseDouble(String s) throws SpongeMatcherParseException {
        try {
            return Double.parseDouble(s);
        } catch(NumberFormatException e) {
            throw new SpongeMatcherParseException(e);
        }
    }
}
