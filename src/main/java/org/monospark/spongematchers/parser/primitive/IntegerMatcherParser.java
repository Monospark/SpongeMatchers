package org.monospark.spongematchers.parser.primitive;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.primitive.IntegerMatchers;
import org.monospark.spongematchers.parser.SpongeMatcherParser;
import org.monospark.spongematchers.util.PatternBuilder;

public final class IntegerMatcherParser extends SpongeMatcherParser<Long> {

    @Override
    protected Pattern createAcceptanceRegex() {
        String integerPattern = "(?:-)?\\d+";
        return new PatternBuilder()
                .openNamedParantheses("range")
                    .appendCapturingPart(integerPattern, "rangestart")
                    .appendNonCapturingPart("-")
                    .appendCapturingPart(integerPattern, "rangeend")
                .closeParantheses()
                .or()
                .openNamedParantheses("amount")
                    .appendNonCapturingPart("\\(")
                        .openNamedParantheses("amountvalues")
                            .appendNonCapturingPart(integerPattern)
                            .openAnonymousParantheses()
                                .appendNonCapturingPart("," + integerPattern)
                            .closeParantheses()
                            .oneOrMore()
                        .closeParantheses()
                    .appendNonCapturingPart("\\)")
                .closeParantheses()
                .or()
                .appendCapturingPart(integerPattern, "value")
                .or()
                .openNamedParantheses("greater")
                    .appendNonCapturingPart(">")
                    .appendCapturingPart(integerPattern, "greatervalue")
                .closeParantheses()
                .or()
                .openNamedParantheses("greaterorequal")
                    .appendNonCapturingPart(">=")
                    .appendCapturingPart(integerPattern, "greaterorequalvalue")
                .closeParantheses()
                .or()
                .openNamedParantheses("less")
                    .appendNonCapturingPart("<")
                    .appendCapturingPart(integerPattern, "lessvalue")
                .closeParantheses()
                .or()
                .openNamedParantheses("lessorequal")
                    .appendNonCapturingPart("<=")
                    .appendCapturingPart(integerPattern, "lessorequalvalue")
                .closeParantheses()
                .build();
    }
    
    public Optional<SpongeMatcher<Long>> parse(Matcher matcher) {
        if (matcher.group("range") != null) {
            long start = Long.valueOf(matcher.group("rangestart"));
            long end = Long.valueOf(matcher.group("rangeend"));
            return Optional.of(IntegerMatchers.range(start, end));
        } else if (matcher.group("amount") != null) {
            String[] split = matcher.group("amountvalues").split(",");
            long[] values = new long[split.length];
            for (int i = 0; i < split.length; i++) {
                values[i] = Long.valueOf(split[i]);
            }
            return Optional.of(IntegerMatchers.amount(values));
        } else if (matcher.group("value") != null) {
            long value = Long.valueOf(matcher.group("value"));
            return Optional.of(IntegerMatchers.value(value));
        } else if (matcher.group("greater") != null) {
            return Optional.of(IntegerMatchers.greaterThan(Long.valueOf(matcher.group("greatervalue"))));
        } else if (matcher.group("greaterorequal") != null) {
            return Optional.of(IntegerMatchers.greaterThanOrEqual(Long.valueOf(matcher.group("greaterorequalvalue"))));
        } else if (matcher.group("less") != null) {
            return Optional.of(IntegerMatchers.lessThan(Long.valueOf(matcher.group("lessvalue"))));
        } else {
            return Optional.of(IntegerMatchers.lessThanOrEqual(Long.valueOf(matcher.group("lessorequalvalue"))));
        }
    }
}
