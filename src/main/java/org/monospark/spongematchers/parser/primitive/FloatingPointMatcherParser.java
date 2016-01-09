package org.monospark.spongematchers.parser.primitive;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.primitive.FloatingPointMatchers;
import org.monospark.spongematchers.parser.SpongeMatcherParser;
import org.monospark.spongematchers.util.PatternBuilder;

public final class FloatingPointMatcherParser extends SpongeMatcherParser<Double> {

    @Override
    protected Pattern createAcceptanceRegex() {
        String floatingPointPattern = "(?:-)?(?:\\d+)?.\\d+f";
        return new PatternBuilder()
                .openNamedParantheses("range")
                    .appendCapturingPart(floatingPointPattern, "rangestart")
                    .appendNonCapturingPart("-")
                    .appendCapturingPart(floatingPointPattern, "rangeend")
                .closeParantheses()
                .or()
                .openNamedParantheses("amount")
                    .appendNonCapturingPart("\\(")
                        .openNamedParantheses("amountvalues")
                            .appendNonCapturingPart(floatingPointPattern)
                            .openAnonymousParantheses()
                                .appendNonCapturingPart("," + floatingPointPattern)
                            .closeParantheses()
                            .oneOrMore()
                        .closeParantheses()
                    .appendNonCapturingPart("\\)")
                .closeParantheses()
                .or()
                .appendCapturingPart(floatingPointPattern, "value")
                .or()
                .openNamedParantheses("greater")
                    .appendNonCapturingPart(">")
                    .appendCapturingPart(floatingPointPattern, "greatervalue")
                .closeParantheses()
                .or()
                .openNamedParantheses("greaterorequal")
                    .appendNonCapturingPart(">=")
                    .appendCapturingPart(floatingPointPattern, "greaterorequalvalue")
                .closeParantheses()
                .or()
                .openNamedParantheses("less")
                    .appendNonCapturingPart("<")
                    .appendCapturingPart(floatingPointPattern, "lessvalue")
                .closeParantheses()
                .or()
                .openNamedParantheses("lessorequal")
                    .appendNonCapturingPart("<=")
                    .appendCapturingPart(floatingPointPattern, "lessorequalvalue")
                .closeParantheses()
                .build();
    }
    
    public Optional<SpongeMatcher<Double>> parse(Matcher matcher) {
        if (matcher.group("range") != null) {
            double start = Double.valueOf(matcher.group("rangestart"));
            double end = Double.valueOf(matcher.group("rangeend"));
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
            return Optional.of(FloatingPointMatchers.greaterThan(Double.valueOf(matcher.group("greatervalue"))));
        } else if (matcher.group("greaterorequal") != null) {
            return Optional.of(FloatingPointMatchers.greaterThanOrEqual(Double.valueOf(
                    matcher.group("greaterorequalvalue"))));
        } else if (matcher.group("less") != null) {
            return Optional.of(FloatingPointMatchers.lessThan(Double.valueOf(matcher.group("lessvalue"))));
        } else {
            return Optional.of(FloatingPointMatchers.lessThanOrEqual(Double.valueOf(
                    matcher.group("lessorequalvalue"))));
        }
    }
}
