package org.monospark.spongematchers.parser.primitive;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.primitive.FloatingPointMatchers;
import org.monospark.spongematchers.parser.SpongeMatcherParser;
import org.monospark.spongematchers.util.PatternBuilder;

public final class FloatingPointMatcherParser extends SpongeMatcherParser<Double> {

    private static final String FLOATING_POINT_REGEX = "(?:\\d+)?.\\d+f";
    
    @Override
    protected Pattern createAcceptanceRegex() {
        return new PatternBuilder()
                .openNamedParantheses("range")
                    .appendCapturingPart(FLOATING_POINT_REGEX, "range start")
                    .appendNonCapturingPart("-")
                    .appendCapturingPart(FLOATING_POINT_REGEX, "range end")
                .closeParantheses()
                .or()
                .openNamedParantheses("amount")
                    .appendNonCapturingPart("(")
                        .openNamedParantheses("amount values")
                            .appendNonCapturingPart(FLOATING_POINT_REGEX)
                            .openAnonymousParantheses()
                                .appendNonCapturingPart(FLOATING_POINT_REGEX)
                            .closeParantheses()
                        .closeParantheses()
                    .oneOrMore()
                    .appendNonCapturingPart(")")
                .closeParantheses()
                .or()
                .openNamedParantheses("value")
                    .appendNonCapturingPart(FLOATING_POINT_REGEX)
                .closeParantheses()
                .openNamedParantheses("greater")
                    .appendNonCapturingPart(">")
                    .appendCapturingPart(FLOATING_POINT_REGEX, "greater value")
                .closeParantheses()
                .or()
                .openNamedParantheses("greater or equal")
                    .appendNonCapturingPart(">=")
                    .appendCapturingPart(FLOATING_POINT_REGEX, "greater or equal value")
                .closeParantheses()
                .openNamedParantheses("less")
                    .appendNonCapturingPart("<")
                    .appendCapturingPart(FLOATING_POINT_REGEX, "less value")
                .closeParantheses()
                .or()
                .openNamedParantheses("greater or equal")
                    .appendNonCapturingPart("<=")
                    .appendCapturingPart(FLOATING_POINT_REGEX, "less or equal value")
                .closeParantheses()
                .build();
    }
    
    public Optional<SpongeMatcher<Double>> parse(Matcher matcher) {
        if (matcher.group("range") != null) {
            double start = Double.valueOf(matcher.group("range start"));
            double end = Double.valueOf(matcher.group("range end"));
            return Optional.of(FloatingPointMatchers.range(start, end));
        } else if (matcher.group("amount") != null) {
            String[] split = matcher.group("amount").split(",");
            double[] values = new double[split.length];
            for (int i = 0; i < split.length; i++) {
                values[i] = Double.valueOf(split[i]);
            }
            return Optional.of(FloatingPointMatchers.amount(values));
        } else if (matcher.group("value") != null) {
            double value = Double.valueOf(matcher.group("value"));
            return Optional.of(FloatingPointMatchers.value(value));
        } else if (matcher.group("greater") != null) {
            return Optional.of(FloatingPointMatchers.greaterThan(Double.valueOf(matcher.group("greater value"))));
        } else if (matcher.group("greater or equal") != null) {
            return Optional.of(FloatingPointMatchers.greaterThanOrEqual(Double.valueOf(
                    matcher.group("greater or equal value"))));
        } else if (matcher.group("less") != null) {
            return Optional.of(FloatingPointMatchers.lessThan(Double.valueOf(matcher.group("less value"))));
        } else {
            return Optional.of(FloatingPointMatchers.lessThanOrEqual(Double.valueOf(
                    matcher.group("less or equal value"))));
        }
    }
}
