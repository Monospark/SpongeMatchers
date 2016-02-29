package org.monospark.spongematchers.matcher.sponge;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;

public final class DataViewMatcher extends SpongeObjectMatcher<DataView> {

    public static DataViewMatcher create(SpongeMatcher<Map<String, Object>> matcher) {
        return new DataViewMatcher(matcher);
    }

    private DataViewMatcher(SpongeMatcher<Map<String, Object>> matcher) {
        super(MatcherType.DATA_VIEW, matcher);
    }

    @Override
    protected void fillMap(DataView o, Map<String, Object> map) {
        for (Entry<DataQuery, Object> entry : o.getValues(false).entrySet()) {
            String name = entry.getKey().getParts().get(entry.getKey().getParts().size() - 1);
            Optional<DataView> dataView = o.getView(entry.getKey());
            if (dataView.isPresent()) {
                map.put(name, dataView.get());
            } else {
                map.put(name, makeMatchable(entry.getValue()));
            }
        }
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
