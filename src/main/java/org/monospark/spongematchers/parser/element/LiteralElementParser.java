package org.monospark.spongematchers.parser.element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.LiteralElement.Type;

public final class LiteralElementParser extends StringElementParser {

    private Type type;

    LiteralElementParser(Type type) {
        this.type = type;
    }

    @Override
    Pattern createPattern() {
        return type.getPattern();
    }

    @Override
    void parse(Matcher matcher, StringElementContext context) throws SpongeMatcherParseException {
        context.addElement(new LiteralElement(matcher, type));
    }
}
