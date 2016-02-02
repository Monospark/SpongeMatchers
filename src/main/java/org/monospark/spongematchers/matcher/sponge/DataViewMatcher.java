package org.monospark.spongematchers.matcher.sponge;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

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
            Optional<DataView> dataView = view.getView(entry.getKey());
            if (dataView.isPresent()) {
                map.put(name, dataView.get());
            } else {
                map.put(name, makeMatchable(entry.getValue()));
            }
        }
        return map;
    }

    private Object makeMatchable(Object o) {
        if (o instanceof Byte) {
            return ((Byte) o).longValue();
        } else if (o instanceof Short) {
            return ((Short) o).longValue();
        } else if (o instanceof Integer) {
            return ((Integer) o).longValue();
        } else if (o instanceof Float) {
            return ((Float) o).doubleValue();
        } else {
            return o;
        }
    }
}
