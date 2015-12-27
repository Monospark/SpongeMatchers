package org.monospark.spongematchers.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.MatcherParser;
import org.monospark.spongematchers.base.IntMatcher.Amount;
import org.monospark.spongematchers.base.IntMatcher.Range;
import org.monospark.spongematchers.base.IntMatcher.Value;
import org.monospark.spongematchers.util.PatternBuilder;

public final class IntMatcherParser extends MatcherParser<IntMatcher> {

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
                .build();
    }
    
    public IntMatcher parse(Matcher matcher) {
        if (matcher.group("range") != null) {
            int start = Integer.valueOf(matcher.group("range start"));
            int end = Integer.valueOf(matcher.group("range end"));
            return new Range(start, end);
        } else if (matcher.group("amount") != null) {
            String[] split = matcher.group("amount").split(",");
            int[] values = new int[split.length];
            for (int i = 0; i < split.length; i++) {
                values[i] = Integer.valueOf(split[i]);
            }
            return new Amount(values);
        } else {
            int value = Integer.parseInt(matcher.group("value"));
            return new Value(value);
        }
    }
}
