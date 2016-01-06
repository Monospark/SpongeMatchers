package org.monospark.spongematchers.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.BaseMatchers;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.util.PatternBuilder;

public final class IntMatcherParser extends SpongeMatcherParser<Integer> {

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
                    .appendNonCapturingPart("\\d+")
                    .openAnonymousParantheses()
                        .appendNonCapturingPart(",\\d+")
                    .closeParantheses()
                    .oneOrMore()
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
    
    public SpongeMatcher<Integer> parse(Matcher matcher) {
        if (matcher.group("range") != null) {
            int start = Integer.valueOf(matcher.group("range start"));
            int end = Integer.valueOf(matcher.group("range end"));
            return BaseMatchers.intRange(start, end);
        } else if (matcher.group("amount") != null) {
            String[] split = matcher.group("amount").split(",");
            int[] values = new int[split.length];
            for (int i = 0; i < split.length; i++) {
                values[i] = Integer.valueOf(split[i]);
            }
            return BaseMatchers.intAmount(values);
        } else if (matcher.group("value") != null) {
            int value = Integer.parseInt(matcher.group("value"));
            return BaseMatchers.intValue(value);
        } else if (matcher.group("greater") != null) {
            return BaseMatchers.intGreaterThan(Integer.parseInt(matcher.group("greater value")));
        } else if (matcher.group("greater or equal") != null) {
            return BaseMatchers.intGreaterThanOrEqual(Integer.parseInt(matcher.group("greater or equal value")));
        } else if (matcher.group("less") != null) {
            return BaseMatchers.intLessThan(Integer.parseInt(matcher.group("less value")));
        } else {
            return BaseMatchers.intLessThanOrEqual(Integer.parseInt(matcher.group("less or equal value")));
        }
    }
}
