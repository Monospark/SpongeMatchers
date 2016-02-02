package org.monospark.spongematchers.parser.element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.util.PatternBuilder;

public final class PatternElement extends StringElement {

    private Type type;

    private StringElement element;

    PatternElement(Matcher matcher, Type type, StringElement element) {
        super(matcher);
        this.type = type;
        this.element = element;
    }

    public Type getType() {
        return type;
    }

    public StringElement getElement() {
        return element;
    }


    public enum Type {

        LIST_MATCH_ANY(new PatternBuilder()
                .appendNonCapturingPart("matchAny\\s*:\\s*")
                .appendCapturingPart(StringElementParser.REPLACE_PATTERN, "element")
                .build()),

        LIST_MATCH_ALL(new PatternBuilder()
                .appendNonCapturingPart("matchAll\\s*:\\s*")
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

        Type(Pattern pattern) {
            this.pattern = pattern;
        }

        public Pattern getPattern() {
            return pattern;
        }
    }
}
