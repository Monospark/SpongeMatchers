package org.monospark.spongematchers.parser.primitive;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.primitive.IntegerMatchers;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.SpongeMatcherParser;
import org.monospark.spongematchers.util.PatternBuilder;

public final class IntegerMatcherParser extends SpongeMatcherParser<Long> {

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
    
    public SpongeMatcher<Long> parse(Matcher matcher) throws SpongeMatcherParseException {
        if (matcher.group("range") != null) {
            long start = parseLong(matcher.group("rangestart"));
            long end = parseLong(matcher.group("rangeend"));
            if (start >= end) {
                throw new SpongeMatcherParseException("The start value of a range must be smaller than the end value");
            }
            return IntegerMatchers.range(start, end);
        } else if (matcher.group("amount") != null) {
            String[] split = matcher.group("amountvalues").replaceAll("\\s*", "").split(",");
            long[] values = new long[split.length];
            for (int i = 0; i < split.length; i++) {
                values[i] = parseLong(split[i]);
            }
            
            //Check for duplicates
            if (Arrays.stream(values).distinct().toArray().length != values.length) {
                throw new SpongeMatcherParseException("The integer amount contains duplicates");
            }
            
            return IntegerMatchers.amount(values);
        } else if (matcher.group("value") != null) {
            long value = parseLong(matcher.group("value"));
            return IntegerMatchers.value(value);
        } else if (matcher.group("greater") != null) {
            return IntegerMatchers.greaterThan(parseLong(matcher.group("greatervalue")));
        } else if (matcher.group("greaterorequal") != null) {
            return IntegerMatchers.greaterThanOrEqual(parseLong(matcher.group("greaterorequalvalue")));
        } else if (matcher.group("less") != null) {
            return IntegerMatchers.lessThan(parseLong(matcher.group("lessvalue")));
        } else {
            return IntegerMatchers.lessThanOrEqual(parseLong(matcher.group("lessorequalvalue")));
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
