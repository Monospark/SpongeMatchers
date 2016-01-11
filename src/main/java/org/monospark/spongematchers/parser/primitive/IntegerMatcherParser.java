package org.monospark.spongematchers.parser.primitive;

import java.util.Arrays;
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
                    .appendNonCapturingPart("\\s*-\\s*")
                    .appendCapturingPart(integerPattern, "rangeend")
                .closeParantheses()
                .or()
                .openNamedParantheses("amount")
                    .appendNonCapturingPart("\\(\\s*")
                        .openNamedParantheses("amountvalues")
                            .appendNonCapturingPart(integerPattern)
                            .openAnonymousParantheses()
                                .appendNonCapturingPart("\\s*,\\s*" + integerPattern)
                            .closeParantheses()
                            .oneOrMore()
                        .closeParantheses()
                    .appendNonCapturingPart("\\s*\\)")
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
    
    public Optional<SpongeMatcher<Long>> parse(Matcher matcher) {
        if (matcher.group("range") != null) {
            long start = Long.valueOf(matcher.group("rangestart"));
            long end = Long.valueOf(matcher.group("rangeend"));
            if (start >= end) {
                return Optional.empty();
            }
            return Optional.of(IntegerMatchers.range(start, end));
        } else if (matcher.group("amount") != null) {
            String[] split = matcher.group("amountvalues").replaceAll("\\s*", "").split(",");
            long[] values = new long[split.length];
            for (int i = 0; i < split.length; i++) {
                values[i] = Long.valueOf(split[i]);
            }
            
            //Check for duplicates
            if (Arrays.stream(values).distinct().toArray().length != values.length) {
                return Optional.empty();
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
