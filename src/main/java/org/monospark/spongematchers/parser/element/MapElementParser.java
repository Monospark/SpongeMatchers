package org.monospark.spongematchers.parser.element;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.util.PatternBuilder;

import com.google.common.collect.Maps;

public final class MapElementParser extends StringElementParser {

    private static final Pattern ENTRY_PATTERN = new PatternBuilder()
            .appendCapturingPart("[^\\{\\},:]+", "entryname")
            .appendNonCapturingPart(":")
            .appendNonCapturingPart("\\s*")
            .appendCapturingPart(StringElementParser.REPLACE_PATTERN, "entrycontent")
            .build();
    
    @Override
    Pattern createPattern() {
        return new PatternBuilder()
                .appendNonCapturingPart("{")
                .appendNonCapturingPart(ENTRY_PATTERN)
                .openAnonymousParantheses()
                .appendNonCapturingPart("\\s*,\\s*")
                    .appendNonCapturingPart(ENTRY_PATTERN)
                .closeParantheses()
                .zeroOrMore()
                .appendNonCapturingPart("}")
                .build();
    }

    @Override
    void parse(Matcher matcher, StringElementContext context) {
        createMap(matcher, context);
    }
    
    private void createMap(Matcher matcher, StringElementContext context) {
        Map<String,StringElement> entries = Maps.newHashMap();
        Matcher entryMatcher = ENTRY_PATTERN.matcher(matcher.group());
        while (entryMatcher.find()) {
            String name = matcher.group("entryname");
            StringElement element = context.getElementAt(entryMatcher.start(), entryMatcher.end());
            context.removeElement(element);
            entries.put(name, element);
        }
        context.addElement(new MapElement(matcher.start(), matcher.end(), entries));;
    }
}
