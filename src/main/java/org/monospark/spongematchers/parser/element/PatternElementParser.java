package org.monospark.spongematchers.parser.element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.util.PatternBuilder;

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
        context.addElement(new PatternElement(matcher.start(), matcher.end(), type, element));
    }

    public static enum Type {

        LIST_MATCH_ANY(new PatternBuilder()
                .appendNonCapturingPart("any\\s*:\\s*")
                .appendCapturingPart(StringElementParser.REPLACE_PATTERN, "element")
                .build()),
        
        LIST_MATCH_ALL(new PatternBuilder()
                .appendNonCapturingPart("all\\s*:\\s*")
                .appendCapturingPart(StringElementParser.REPLACE_PATTERN, "element")
                .build()),
        
        PARANTHESES(new PatternBuilder()
                .appendNonCapturingPart("\\(\\s*")
                .appendCapturingPart(StringElementParser.REPLACE_PATTERN, "element")
                .appendNonCapturingPart("\\s*\\)")
                .build()),
        
        NOT(new PatternBuilder()
                .appendNonCapturingPart("!\\s*")
                .appendCapturingPart(StringElementParser.REPLACE_PATTERN, "element")
                .build());
        
        private Pattern pattern;

        private Type(Pattern pattern) {
            this.pattern = pattern;
        }

        private Pattern getPattern() {
            return pattern;
        }
    }
}
