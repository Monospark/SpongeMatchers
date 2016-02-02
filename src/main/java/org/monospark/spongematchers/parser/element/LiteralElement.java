package org.monospark.spongematchers.parser.element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LiteralElement extends StringElement {

    private Type type;

    LiteralElement(Matcher matcher, Type type) {
        super(matcher);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public enum Type {

        EMPTY(Pattern.compile("(?:E|e)mpty")),

        NONE(Pattern.compile("(?:N|n)one")),

        WILDCARD(Pattern.compile("\\*"));

        private Pattern pattern;

        Type(Pattern pattern) {
            this.pattern = pattern;
        }

        public Pattern getPattern() {
            return pattern;
        }
    }
}
