package org.monospark.spongematchers.base;

import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.monospark.spongematchers.SpongeMatcherParser;

public final class McNameMatcherParser extends SpongeMatcherParser<McNameMatcher> {

    @Override
    protected Pattern createAcceptanceRegex() {
        return McNameMatcher.NAME_REGEX;
    }
    
    public McNameMatcher parse(java.util.regex.Matcher matcher) {
        String regex = matcher.group();
        for (Entry<String, String> entry : McNameMatcher.REPLACEMENTS.entrySet()) {
            regex = regex.replace(entry.getKey(), entry.getValue());
        }

        Pattern pattern = Pattern.compile(regex);
        return new McNameMatcher(pattern);
    }
}
