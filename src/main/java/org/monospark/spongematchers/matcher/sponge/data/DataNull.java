package org.monospark.spongematchers.matcher.sponge.data;

import java.util.List;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;

public final class DataNull implements DataEntry {

    @Override
    public boolean matchesEntry(List<String> currentPath, DataView v) {
        return !v.get(DataQuery.of(currentPath)).isPresent();
    }
}
