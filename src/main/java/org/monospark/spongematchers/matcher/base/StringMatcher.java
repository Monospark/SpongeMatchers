package org.monospark.spongematchers.matcher.base;

import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.SpongeMatcher;

public final class StringMatcher {

    private StringMatcher() {}

    public static SpongeMatcher<String> literal(String literal) {
        return new SpongeMatcher<String>() {

            @Override
            public boolean matches(String o) {
                return o.equals(literal);
            }

            @Override
            public String toString() {
                return "'" + literal + "'";
            }
        };
    }

    public static SpongeMatcher<String> regex(String regex) {
        Pattern pattern = Pattern.compile(regex);
        return new SpongeMatcher<String>() {

            @Override
            public boolean matches(String o) {
                return pattern.matcher(o).matches();
            }

            @Override
            public String toString() {
                return "r'" + regex + "'";
            }
        };
    }
}
