package org.monospark.spongematchers.matcher.sponge;

import java.util.Map;
import java.util.Map.Entry;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;

import com.google.common.collect.Maps;

public final class DataViewMatcher implements SpongeMatcher<DataView> {

    public static DataViewMatcher create(SpongeMatcher<Map<String, Object>> matcher) {
        return new DataViewMatcher(matcher);
    }
    
    private SpongeMatcher<Map<String, Object>> matcher;

    private DataViewMatcher(SpongeMatcher<Map<String, Object>> matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean matches(DataView v) {
        return matcher.matches(toMap(v));
    }
    
    private Map<String, Object> toMap(DataView view) {
        Map<String, Object> map = Maps.newHashMap();
        for (Entry<DataQuery, Object> entry : view.getValues(false).entrySet()) {
            String name = entry.getKey().getParts().get(entry.getKey().getParts().size() - 1);
            map.put(name, entry.getValue());
        }
        return map;
    }
}
