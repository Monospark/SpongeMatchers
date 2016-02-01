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
    
    public static enum Type {

        EMPTY(Pattern.compile("(?:E|e)mpty(?:!>\\s*:)")),
        
        WILDCARD(Pattern.compile("\\*"));
        
        private Pattern pattern;

        private Type(Pattern pattern) {
            this.pattern = pattern;
        }

        public Pattern getPattern() {
            return pattern;
        }
    }
}
