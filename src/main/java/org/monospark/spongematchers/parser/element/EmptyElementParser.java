package org.monospark.spongematchers.parser.element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class EmptyElementParser extends StringElementParser {

    EmptyElementParser() {}
    
    @Override
    Pattern createPattern() {
        return Pattern.compile("(?:E|e)mpty");
    }

    @Override
    void parse(Matcher matcher, StringElementContext context) {
        context.addElement(new EmptyElement(matcher));
    }
}
