package org.monospark.spongematchers.parser.element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class EmptyElementParser extends StringElementParser {

    @Override
    Pattern createPattern() {
        return Pattern.compile("E|empty");
    }

    @Override
    void parse(Matcher matcher, StringElementContext context) {
        context.addElement(new EmptyElement(matcher.start(), matcher.end()));
    }
}
