package org.monospark.spongematchers.parser.element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.ConnectedElement.Operator;
import org.monospark.spongematchers.util.PatternBuilder;

public final class ConnectedElementParser extends StringElementParser {

    ConnectedElementParser() {}
    
    @Override
    Pattern createPattern() {
        return new PatternBuilder()
                .appendCapturingPart(StringElementParser.REPLACE_PATTERN, "firstelement")
                .openAnonymousParantheses()
                    .appendCapturingPart("\\s*\\|\\s*", "or")
                    .or()
                    .appendCapturingPart("\\s*&\\s*", "and")
                .closeParantheses() 
                .appendCapturingPart(StringElementParser.REPLACE_PATTERN, "secondelement")
                .build();
    }

    @Override
    void parse(Matcher matcher, StringElementContext context) throws SpongeMatcherParseException {
        StringElement element1 = context.getElementAt(matcher.start("firstelement"), matcher.end("firstelement"));
        StringElement element2 = context.getElementAt(matcher.start("secondelement"), matcher.end("secondelement"));
        Operator op = matcher.group("or") != null ? Operator.OR : Operator.AND;
        
        context.removeElement(element1);
        context.removeElement(element2);
        context.addElement(new ConnectedElement(matcher.start(), matcher.end(), element1, element2, op));
    }
}
