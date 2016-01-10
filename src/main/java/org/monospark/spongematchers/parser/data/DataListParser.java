package org.monospark.spongematchers.parser.data;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.data.DataEntry;
import org.monospark.spongematchers.matcher.data.DataList;
import org.monospark.spongematchers.parser.ParserHelper;
import org.monospark.spongematchers.util.PatternBuilder;

public final class DataListParser extends DataEntryParser<DataList> {

    private static final Pattern DATA_LIST_PATTERN = new PatternBuilder()
            .appendNonCapturingPart("\\[")
            .appendCapturingPart(".+", "listcontent")
            .appendNonCapturingPart("\\]")
            .build();

    @Override
    Optional<DataList> parse(String string) {
        Matcher matcher = DATA_LIST_PATTERN.matcher(string);
        if (!matcher.matches()) {
            return Optional.empty();
        }
        
        Optional<List<DataEntry>> entries = ParserHelper.<DataEntry>tokenize(matcher.group("listcontent"), ',',
                s -> DataEntryParser.parseDataEntry(s));
        if (!entries.isPresent()) {
            return Optional.empty();
        } else {
            return Optional.of(new DataList(entries.get()));
        }
    }
}
