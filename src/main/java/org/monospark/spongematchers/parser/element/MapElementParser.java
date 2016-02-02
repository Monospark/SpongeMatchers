package org.monospark.spongematchers.parser.element;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.util.PatternBuilder;

import com.google.common.collect.Maps;

public final class MapElementParser extends StringElementParser {

    private static final Pattern ENTRY_PATTERN = new PatternBuilder()
            .appendCapturingPart(StringElementParser.REPLACE_PATTERN, "key")
            .appendNonCapturingPart("\\s*:\\s*")
            .appendCapturingPart(StringElementParser.REPLACE_PATTERN, "value")
            .build();
    
    MapElementParser() {}
    
    @Override
    Pattern createPattern() {
        Pattern entryPattern = new PatternBuilder()
                .appendNonCapturingPart(StringElementParser.REPLACE_PATTERN)
                .appendNonCapturingPart("\\s*:\\s*")
                .appendNonCapturingPart(StringElementParser.REPLACE_PATTERN)
                .build();
        return new PatternBuilder()
                .appendNonCapturingPart("\\{\\s*")
                .appendNonCapturingPart(entryPattern)
                .openAnonymousParantheses()
                    .appendNonCapturingPart("\\s*,\\s*")
                    .appendNonCapturingPart(entryPattern)
                .closeParantheses()
                .zeroOrMore()
                .appendNonCapturingPart("\\s*\\}")
                .build();
    }

    @Override
    void parse(Matcher matcher, StringElementContext context) throws SpongeMatcherParseException {
        Map<String,StringElement> entries = Maps.newHashMap();
        Matcher entryMatcher = ENTRY_PATTERN.matcher(matcher.group());
        while (entryMatcher.find()) {
            StringElement key = context.getElementAt(matcher.start() + entryMatcher.start("key"),
                    matcher.start() + entryMatcher.end("key"));
            if (!(key instanceof MapKeyElement)) {
                throw new SpongeMatcherParseException("Invalid map key: " + key.getString());
            }
            
            String keyName = ((MapKeyElement) key).getName();
            context.removeElement(key);
            StringElement value = context.getElementAt(matcher.start() + entryMatcher.start("value"),
                    matcher.start() + entryMatcher.end("value"));
            context.removeElement(value);
            entries.put(keyName, value);
        }
        context.addElement(new MapElement(matcher, entries));
    }
}
