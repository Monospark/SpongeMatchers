package org.monospark.spongematchers.matcher.data;

import java.util.List;
import java.util.Optional;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;

import com.google.common.collect.ImmutableList;

public final class DataList implements DataEntry {
    
    private List<DataEntry> entries;

    public DataList(List<DataEntry> entries) {
        this.entries = ImmutableList.copyOf(entries);
    }

    @Override
    public boolean matchesEntry(List<String> currentPath, DataView v) {
        Optional<List<DataView>> viewList = v.getViewList(DataQuery.of(currentPath));
        if (!viewList.isPresent() || viewList.get().size() != entries.size()) {
            return false;
        }
        
        for (int i = 0; i < entries.size(); i++) {
            boolean matches = entries.get(i).matchesEntry(currentPath, viewList.get().get(i));
            if (!matches) {
                return false;
            }
        }
        return true;
    }
}
