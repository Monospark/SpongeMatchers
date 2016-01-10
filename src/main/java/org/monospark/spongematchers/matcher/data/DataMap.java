package org.monospark.spongematchers.matcher.data;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.spongepowered.api.data.DataView;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

public final class DataMap implements DataEntry {
    
    private Map<String, DataEntry> entries;

    public DataMap(Map<String, DataEntry> entries) {
        entries = ImmutableMap.copyOf(entries);
    }

    @Override
    public boolean matchesEntry(List<String> currentPath, DataView v) {
        for (Entry<String,DataEntry> mapEntry : entries.entrySet()) {
            if (!mapEntry.getValue().matchesEntry(createPathCopy(currentPath, mapEntry.getKey()), v)) {
                return false;
            }
        }
        return true;
    }
    
    private List<String> createPathCopy(List<String> path, String next) {
        List<String> copy = Lists.newArrayList(path);
        copy.add(next);
        return copy;
    }

    public Map<String, DataEntry> getEntries() {
        return entries;
    }
}
