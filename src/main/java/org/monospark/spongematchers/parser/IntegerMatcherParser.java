package org.monospark.spongematchers.parser;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.primitive.IntegerMatchers;
import org.monospark.spongematchers.util.PatternBuilder;

public final class IntegerMatcherParser extends SpongeMatcherParser<Long> {

    @Override
    protected Pattern createAcceptanceRegex() {
        return new PatternBuilder()
                .openNamedParantheses("range")
                    .appendCapturingPart("\\d+", "range start")
                    .appendNonCapturingPart("-")
                    .appendCapturingPart("\\d+", "range end")
                .closeParantheses()
                .or()
                .openNamedParantheses("amount")
                    .appendNonCapturingPart("(")
                        .openNamedParantheses("amount values")
                            .appendNonCapturingPart("\\d+")
                            .openAnonymousParantheses()
                                .appendNonCapturingPart(",\\d+")
                            .closeParantheses()
                        .closeParantheses()
                    .oneOrMore()
                    .appendNonCapturingPart(")")
                .closeParantheses()
                .or()
                .openNamedParantheses("value")
                    .appendNonCapturingPart("\\d+")
                .closeParantheses()
                .openNamedParantheses("greater")
                    .appendNonCapturingPart(">")
                    .appendCapturingPart("\\d+", "greater value")
                .closeParantheses()
                .or()
                .openNamedParantheses("greater or equal")
                    .appendNonCapturingPart(">=")
                    .appendCapturingPart("\\d+", "greater or equal value")
                .closeParantheses()
                .openNamedParantheses("less")
                    .appendNonCapturingPart("<")
                    .appendCapturingPart("\\d+", "less value")
                .closeParantheses()
                .or()
                .openNamedParantheses("greater or equal")
                    .appendNonCapturingPart("<=")
                    .appendCapturingPart("\\d+", "less or equal value")
                .closeParantheses()
                .build();
    }
    
    public Optional<SpongeMatcher<Long>> parse(Matcher matcher) {
        if (matcher.group("range") != null) {
            long start = Integer.valueOf(matcher.group("range start"));
            long end = Integer.valueOf(matcher.group("range end"));
            return Optional.of(IntegerMatchers.range(start, end));
        } else if (matcher.group("amount") != null) {
            String[] split = matcher.group("amount").split(",");
            long[] values = new long[split.length];
            for (int i = 0; i < split.length; i++) {
                values[i] = Integer.valueOf(split[i]);
            }
            return Optional.of(IntegerMatchers.amount(values));
        } else if (matcher.group("value") != null) {
            long value = Integer.parseInt(matcher.group("value"));
            return Optional.of(IntegerMatchers.value(value));
        } else if (matcher.group("greater") != null) {
            return Optional.of(IntegerMatchers.greaterThan(Integer.parseInt(matcher.group("greater value"))));
        } else if (matcher.group("greater or equal") != null) {
            return Optional.of(IntegerMatchers.greaterThanOrEqual(Integer.parseInt(matcher.group("greater or equal value"))));
        } else if (matcher.group("less") != null) {
            return Optional.of(IntegerMatchers.lessThan(Integer.parseInt(matcher.group("less value"))));
        } else {
            return Optional.of(IntegerMatchers.lessThanOrEqual(Integer.parseInt(matcher.group("less or equal value"))));
        }
    }
}
