package org.monospark.spongematchers.base;

import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.monospark.spongematchers.MatcherParser;

public final class NameMatcherParser extends MatcherParser<NameMatcher> {

    @Override
    protected Pattern createAcceptanceRegex() {
        return NameMatcher.NAME_REGEX;
    }
    
    public NameMatcher parse(java.util.regex.Matcher matcher) {
        String regex = matcher.group();
        for (Entry<String, String> entry : NameMatcher.REPLACEMENTS.entrySet()) {
            regex = regex.replace(entry.getKey(), entry.getValue());
        }

        Pattern pattern = Pattern.compile(regex);
        return new NameMatcher(pattern);
    }
}
