package org.monospark.spongematchers.matcher.sponge.data;

import java.util.Collections;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.spongepowered.api.data.DataView;

public final class DataViewMatcher implements SpongeMatcher<DataView> {

    private DataEntry root;

    public DataViewMatcher(DataEntry root) {
        this.root = root;
    }

    @Override
    public boolean matches(DataView v) {
        return root.matchesEntry(Collections.emptyList(), v);
    }
    
    
}
