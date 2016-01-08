package org.monospark.spongematchers.matcher.data;

import java.util.List;

import org.spongepowered.api.data.DataView;

public interface DataEntry {

    abstract boolean matchesEntry(List<String> currentPath, DataView v);
}
