package org.monospark.spongematchers.matcher.base;

import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.SpongeMatcher;

public final class StringMatcher {

    private StringMatcher() {}
    
    public static SpongeMatcher<String> create(String regex) {
        Pattern pattern = Pattern.compile(regex);
        return new SpongeMatcher<String>() {

            @Override
            public boolean matches(String o) {
                return pattern.matcher(o).matches();
            }
        };
    }
}
