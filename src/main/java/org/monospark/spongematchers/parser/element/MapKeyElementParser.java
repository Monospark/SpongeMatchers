package org.monospark.spongematchers.parser.element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.util.PatternBuilder;

public final class MapKeyElementParser extends StringElementParser {

    @Override
    Pattern createPattern() {
        return new PatternBuilder()
                .appendNonCapturingPart("'")
                .appendCapturingPart("[^']+", "name")
                .appendNonCapturingPart("'")
                .appendNonCapturingPart("(?=\\s*:)")
                .build();
    }

    @Override
    void parse(Matcher matcher, StringElementContext context) throws SpongeMatcherParseException {
        context.addElement(new MapKeyElement(context.getOriginalStringAt(matcher.start(), matcher.end()),
                matcher, matcher.group("name")));
    }
}
