package org.monospark.spongematchers.parser.sponge.data;

import java.util.Optional;

import org.monospark.spongematchers.matcher.sponge.data.DataNull;

public final class DataNullParser extends DataEntryParser<DataNull> {

    @Override
    Optional<DataNull> parse(String string) {
        return Optional.ofNullable(string.equalsIgnoreCase("null") ? new DataNull() : null);
    }
}
