package org.monospark.spongematchers.parser.element;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.util.PatternBuilder;

import com.google.common.collect.Lists;

public final class ListElementParser extends StringElementParser {

    ListElementParser() {}

    @Override
    Pattern createPattern() {
        return new PatternBuilder()
                .appendNonCapturingPart("\\[\\s*")
                .appendNonCapturingPart(StringElementParser.REPLACE_PATTERN)
                .openAnonymousParantheses()
                    .appendNonCapturingPart("\\s*,\\s*")
                    .appendNonCapturingPart(StringElementParser.REPLACE_PATTERN)
                .closeParantheses()
                .zeroOrMore()
                .appendNonCapturingPart("\\s*\\]")
                .build();
    }

    @Override
    void parse(Matcher matcher, StringElementContext context) throws SpongeMatcherParseException {
        List<StringElement> elements = Lists.newArrayList();
        Matcher elementMatcher = StringElementParser.REPLACE_PATTERN.matcher(matcher.group());
        while (elementMatcher.find()) {
            StringElement element = context.getElementAt(matcher.start() + elementMatcher.start(),
                    matcher.start() + elementMatcher.end());
            context.removeElement(element);
            elements.add(element);
        }
        context.addElement(new ListElement(matcher, elements));
    }
}
