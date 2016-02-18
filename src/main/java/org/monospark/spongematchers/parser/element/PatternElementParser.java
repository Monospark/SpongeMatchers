package org.monospark.spongematchers.parser.element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.PatternElement.Type;

public final class PatternElementParser extends StringElementParser {

    private Type type;

    PatternElementParser(Type type) {
        this.type = type;
    }

    @Override
    Pattern createPattern() {
        return type.getPattern();
    }

    @Override
    void parse(Matcher matcher, StringElementContext context) throws SpongeMatcherParseException {
        int start = matcher.start("element");
        int end = matcher.end("element");
        StringElement element = context.getElementAt(start, end);
        context.removeElement(element);
        context.addElement(new PatternElement(context.getOriginalStringAt(matcher.start(), matcher.end()),
                matcher, type, element));
    }
}
