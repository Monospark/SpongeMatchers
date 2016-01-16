package org.monospark.spongematchers.parser.sponge.data;

import java.util.Optional;
import java.util.Set;

import org.monospark.spongematchers.matcher.sponge.data.DataEntry;
import org.monospark.spongematchers.matcher.sponge.data.DataValue.Type;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.SpongeMatcherParser;

import com.google.common.collect.Sets;

public abstract class DataEntryParser<T extends DataEntry> {

    public static final Set<DataEntryParser<?>> PARSERS = createDataEntryParsers();
    
    private static Set<DataEntryParser<?>> createDataEntryParsers() {
        Set<DataEntryParser<?>> parsers = Sets.newHashSet();
        parsers.add(new DataNullParser());
        parsers.add(new DataValueParser<Boolean>(Type.BOOLEAN, SpongeMatcherParser.BOOLEAN));
        parsers.add(new DataValueParser<Long>(Type.INTEGER, SpongeMatcherParser.INTEGER));
        parsers.add(new DataValueParser<Double>(Type.FLOATING_POINT, SpongeMatcherParser.FLOATING_POINT));
        parsers.add(new DataValueParser<String>(Type.STRING, SpongeMatcherParser.STRING));
        parsers.add(new DataListParser());
        parsers.add(new DataMapParser());
        return parsers;
    }
    
    static Optional<? extends DataEntry> parseDataEntry(String string) throws SpongeMatcherParseException {
        String trimmed = string.trim();
        for (DataEntryParser<?> parser : PARSERS) {
            Optional<? extends DataEntry> entry = parser.parse(trimmed);
            if (entry.isPresent()) {
                return entry;
            }
        }
        return Optional.empty();
    }

    abstract Optional<T> parse(String string) throws SpongeMatcherParseException;
}