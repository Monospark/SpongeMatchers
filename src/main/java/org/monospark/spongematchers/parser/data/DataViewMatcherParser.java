package org.monospark.spongematchers.parser.data;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.data.DataEntry;
import org.monospark.spongematchers.matcher.data.DataViewMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.SpongeMatcherParser;
import org.monospark.spongematchers.util.PatternBuilder;
import org.spongepowered.api.data.DataView;

public final class DataViewMatcherParser extends SpongeMatcherParser<DataView> {

    public DataViewMatcherParser() {
        super("data view");
    }

    @Override
    protected Pattern createAcceptancePattern() {
        return new PatternBuilder()
                .appendNonCapturingPart(".+")
                .build();
    }

    @Override
    protected SpongeMatcher<DataView> parse(Matcher matcher) throws SpongeMatcherParseException {
        Optional<? extends DataEntry> entry = DataEntryParser.parseDataEntry(matcher.group());
        if (!entry.isPresent()) {
            throw new SpongeMatcherParseException("Invalid data view matcher: " + matcher.group());
        } else {
            return new DataViewMatcher(entry.get());
        }
    }
}
