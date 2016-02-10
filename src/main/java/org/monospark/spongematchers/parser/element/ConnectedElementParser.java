package org.monospark.spongematchers.parser.element;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.ConnectedElement.Operator;
import org.monospark.spongematchers.util.PatternBuilder;

import com.google.common.collect.Sets;

public final class ConnectedElementParser extends StringElementParser {

    ConnectedElementParser() {}

    @Override
    Pattern createPattern() {
        return new PatternBuilder()
            .openNamedParantheses("or")
                .appendNonCapturingPart(StringElementParser.REPLACE_PATTERN)
                .openAnonymousParantheses()
                    .appendNonCapturingPart("\\s*\\|\\s*")
                    .appendNonCapturingPart(StringElementParser.REPLACE_PATTERN)
                .closeParantheses()
                .oneOrMore()
            .closeParantheses()
            .or()
            .openNamedParantheses("and")
                .appendNonCapturingPart(StringElementParser.REPLACE_PATTERN)
                .openAnonymousParantheses()
                    .appendNonCapturingPart("\\s*\\&\\s*")
                    .appendNonCapturingPart(StringElementParser.REPLACE_PATTERN)
                .closeParantheses()
                .oneOrMore()
            .closeParantheses()
            .build();
    }

    @Override
    void parse(Matcher matcher, StringElementContext context) throws SpongeMatcherParseException {
        String matched = matcher.group("or") != null ? matcher.group("or") : matcher.group("and");
        Operator op = matcher.group("or") != null ? Operator.OR : Operator.AND;
        Set<StringElement> elements = Sets.newHashSet();
        Matcher replaceMatcher = StringElementParser.REPLACE_PATTERN.matcher(matched);
        while (replaceMatcher.find()) {
            StringElement element = context.getElementAt(matcher.start() + replaceMatcher.start(),
                    matcher.start() + replaceMatcher.end());
            elements.add(element);
            context.removeElement(element);
        }
        context.addElement(new ConnectedElement(matcher, elements, op));
    }
}
