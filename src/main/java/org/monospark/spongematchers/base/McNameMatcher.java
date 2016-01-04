package org.monospark.spongematchers.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.monospark.spongematchers.SpongeMatcher;

public final class McNameMatcher implements SpongeMatcher<String> {
    
    public static final Map<String, String> REPLACEMENTS = createReplacements();
    
    public static final Pattern NAME_REGEX = createRegex();
    
    private static Map<String, String> createReplacements() {
        Map<String, String> replacements = new HashMap<String, String>();
        replacements.put("*", ".*");
        replacements.put("?", ".");
        return replacements;
    }

    private static Pattern createRegex() {
        StringBuilder toAppend = new StringBuilder();
        for (Entry<String, String> entry : REPLACEMENTS.entrySet()) {
            toAppend.append("|").append(entry.getKey());
        }
        return Pattern.compile("[a-zA-Z](?:\\w" + toAppend.toString() + ")+");
    }
    
    private Pattern regex;

    public McNameMatcher(String regex) {
        if (!NAME_REGEX.matcher(regex).matches()) {
            throw new IllegalArgumentException("Invalid name regex: " + regex);
        }
        
        this.regex = Pattern.compile(regex);
    }
    
    McNameMatcher(Pattern regex) {
        this.regex = regex;
    }

    @Override
    public boolean matches(String o) {
        return regex.matcher(o).matches();
    }
}
