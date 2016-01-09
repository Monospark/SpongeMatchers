package org.monospark.spongematchers.parser.data;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.data.DataEntry;
import org.monospark.spongematchers.matcher.data.DataMap;
import org.monospark.spongematchers.parser.ParserHelper;
import org.monospark.spongematchers.util.PatternBuilder;

import com.google.common.collect.Maps;

public class DataMapParser extends DataEntryParser<DataMap> {
    
    private static final Pattern DATA_MAP_PATTERN = new PatternBuilder()
            .appendNonCapturingPart("{")
            .appendCapturingPart(".+", "content")
            .appendNonCapturingPart("}")
            .build();
    
    private static final Pattern MAP_ENTRY_PATTERN = new PatternBuilder()
            .appendCapturingPart("'\\w+'", "name")
            .appendNonCapturingPart(":")
            .appendCapturingPart(".+", "content")
            .build();

    @Override
    Optional<DataMap> parse(String string) {
        Matcher matcher = DATA_MAP_PATTERN.matcher(string);
        if (!matcher.matches()) {
            return Optional.empty();
        }
        
        Optional<List<Entry<String,DataEntry>>> entries =
                ParserHelper.<Entry<String,DataEntry>>tokenize(matcher.group("content"), ",",
                s -> {
                    Matcher currentMatcher = MAP_ENTRY_PATTERN.matcher(s);
                    if (!currentMatcher.matches()) {
                        Optional.empty();
                    }
                    
                    Optional<? extends DataEntry> dataEntry =
                            DataEntryParser.parseDataEntry(currentMatcher.group("content"));
                    return Optional.of(Maps.immutableEntry(currentMatcher.group("name"), dataEntry.get()));
                });

        if (!entries.isPresent()) {
            return Optional.empty();
        }
        
        Map<String,DataEntry> map = Maps.newHashMap();
        for (Entry<String,DataEntry> entry : entries.get()) {
            map.put(entry.getKey(), entry.getValue());
        }
        return Optional.of(new DataMap(map));
    }
}
