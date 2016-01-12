package org.monospark.spongematchers.parser.data;

import java.util.Optional;

import org.monospark.spongematchers.matcher.data.DataNull;

public final class DataNullParser extends DataEntryParser<DataNull> {

    @Override
    Optional<DataNull> parse(String string) {
        return Optional.ofNullable(string.equalsIgnoreCase("null") ? new DataNull() : null);
    }
}
