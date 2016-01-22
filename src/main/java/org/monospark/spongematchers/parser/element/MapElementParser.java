package org.monospark.spongematchers.parser.element;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.util.PatternBuilder;

import com.google.common.collect.Maps;

public final class MapElementParser extends StringElementParser {

    private static final Pattern ENTRY_PATTERN = new PatternBuilder()
            .appendCapturingPart("[^\\{\\},:]+", "entryname")
            .appendNonCapturingPart("\\s*:\\s*")
            .appendCapturingPart(StringElementParser.REPLACE_PATTERN, "entrycontent")
            .build();
    
    MapElementParser() {}
    
    @Override
    Pattern createPattern() {
        Pattern entryPattern = new PatternBuilder()
                .appendNonCapturingPart("[^\\{\\},:]+")
                .appendNonCapturingPart("\\s*:\\s*")
                .appendNonCapturingPart(StringElementParser.REPLACE_PATTERN)
                .build();
        return new PatternBuilder()
                .appendNonCapturingPart("\\{")
                .appendNonCapturingPart(entryPattern)
                .openAnonymousParantheses()
                    .appendNonCapturingPart("\\s*,\\s*")
                    .appendNonCapturingPart(entryPattern)
                .closeParantheses()
                .zeroOrMore()
                .appendNonCapturingPart("\\}")
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
