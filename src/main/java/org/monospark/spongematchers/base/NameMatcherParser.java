package org.monospark.spongematchers.base;

import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.monospark.spongematchers.SpongeMatcherParser;

public final class NameMatcherParser extends SpongeMatcherParser<NameMatcher> {

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
