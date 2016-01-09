package org.monospark.spongematchers.parser.data;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.data.DataEntry;
import org.monospark.spongematchers.matcher.data.DataViewMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParser;
import org.monospark.spongematchers.util.PatternBuilder;
import org.spongepowered.api.data.DataView;

public final class DataViewMatcherParser extends SpongeMatcherParser<DataView> {

    @Override
    protected Pattern createAcceptanceRegex() {
        return new PatternBuilder()
                .appendNonCapturingPart(".+")
                .build();
    }

    @Override
    protected Optional<SpongeMatcher<DataView>> parse(Matcher matcher) {
        Optional<? extends DataEntry> entry = DataEntryParser.parseDataEntry(matcher.group());
        if (!entry.isPresent()) {
            return Optional.empty();
        } else {
            return Optional.of(new DataViewMatcher(entry.get()));
        }
    }
}
