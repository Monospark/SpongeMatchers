package org.monospark.spongematchers.matcher;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

public final class DataViewMatcher implements SpongeMatcher<DataView> {

    private DataEntry root;

    private DataViewMatcher(DataEntry root) {
        this.root = root;
    }

    @Override
    public boolean matches(DataView v) {
        return root.matchesEntry(Collections.emptyList(), v);
    }

    public static abstract class DataEntry {

        abstract boolean matchesEntry(List<String> currentPath, DataView v);
    }
    
    public static final class DataMap extends DataEntry {
        
        private Map<String, DataEntry> entries;

        public DataMap(Map<String, DataEntry> entries) {
            entries = ImmutableMap.copyOf(entries);
        }

        @Override
        boolean matchesEntry(List<String> currentPath, DataView v) {
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
    }
    
    public static final class DataList extends DataEntry {
        
        private List<DataEntry> entries;

        public DataList(List<DataEntry> entries) {
            this.entries = ImmutableList.copyOf(entries);
        }

        @Override
        boolean matchesEntry(List<String> currentPath, DataView v) {
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
    
    public static final class DataValue<T> extends DataEntry {
        
        private Type<T> valueType;
        
        private SpongeMatcher<T> valueMatcher;

        public DataValue(Type<T> valueType, SpongeMatcher<T> valueMatcher) {
            this.valueType = valueType;
            this.valueMatcher = valueMatcher;
        }

        @SuppressWarnings("unchecked")
        @Override
        boolean matchesEntry(List<String> currentPath, DataView v) {
            DataQuery query = DataQuery.of(currentPath);
            Optional<Object> queried = v.get(query);
            return queried.isPresent() && valueType.getTypeClass().isAssignableFrom(queried.get().getClass()) ?
                    valueMatcher.matches((T) queried.get()) : false;
        }
        
        public static class Type<T> {
            
            public static final Type<Integer> INTEGER = new Type<Integer>(Integer.class);
            
            private Class<?> typeClass;

            private Type(Class<?> typeClass) {
                this.typeClass = typeClass;
            }

            private Class<?> getTypeClass() {
                return typeClass;
            }
        }
    }
}
