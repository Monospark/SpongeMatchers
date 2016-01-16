package org.monospark.spongematchers.matcher.sponge.data;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;

public final class DataValue<T> implements DataEntry {
    
    private Type<T> valueType;
    
    private SpongeMatcher<T> valueMatcher;

    public DataValue(Type<T> valueType, SpongeMatcher<T> valueMatcher) {
        this.valueType = valueType;
        this.valueMatcher = valueMatcher;
    }

    @Override
    public boolean matchesEntry(List<String> currentPath, DataView v) {
        DataQuery query = DataQuery.of(currentPath);
        Optional<? extends T> queried = valueType.getData(v, query);
        return queried.isPresent() ? valueMatcher.matches(queried.get()) : false;
    }

    public Type<T> getType() {
        return valueType;
    }

    public SpongeMatcher<T> getMatcher() {
        return valueMatcher;
    }

    public static final class Type<T> {
        
        public static final Type<Boolean> BOOLEAN = new Type<Boolean>((v, q) -> v.getBoolean(q));
        public static final Type<Long> INTEGER = new Type<Long>((v, q) -> v.getLong(q));
        public static final Type<Double> FLOATING_POINT = new Type<Double>((v, q) -> v.getDouble(q));
        public static final Type<String> STRING = new Type<String>((v, q) -> v.getString(q));

        private BiFunction<DataView, DataQuery, Optional<? extends T>> dataFunction;

        private Type(BiFunction<DataView, DataQuery, Optional<? extends T>> dataFunction) {
            this.dataFunction = dataFunction;
        }

        private Optional<? extends T> getData(DataView v, DataQuery q) {
            return dataFunction.apply(v, q);
        }
    }
}