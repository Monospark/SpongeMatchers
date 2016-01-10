package org.monospark.spongematchers.parser.primitive;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.BaseMatchers;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.primitive.BooleanMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParser;
import org.monospark.spongematchers.util.PatternBuilder;

public final class BooleanMatcherParser extends SpongeMatcherParser<Boolean> {

    @Override
    protected Pattern createAcceptanceRegex() {
        return new PatternBuilder()
                .appendCapturingPart("true", "true")
                .or()
                .appendCapturingPart("false", "false")
                .or()
                .openNamedParantheses("both")
                    .openAnonymousParantheses()
                        .appendNonCapturingPart("true\\s*\\|\\s*false")
                    .closeParantheses()
                    .or()
                    .openAnonymousParantheses()
                        .appendNonCapturingPart("false\\s*\\|\\s*true")
                    .closeParantheses()
                .closeParantheses()
                .build();
    }

    @Override
    protected Optional<SpongeMatcher<Boolean>> parse(Matcher matcher) {
        if (matcher.group("true") != null) {
            return Optional.of(BooleanMatcher.trueOnly());
        } else if (matcher.group("false") != null) {
            return Optional.of(BooleanMatcher.falseOnly());
        } else {
            return Optional.of(BaseMatchers.wildcard());
        }
    }
}
