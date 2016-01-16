package org.monospark.spongematchers.matcher.sponge.property;

import org.monospark.spongematchers.matcher.SpongeMatcher;

public final class PropertyMatcher<T> {

    private Type<T> type;
    
    private SpongeMatcher<T> matcher;

    public PropertyMatcher(Type<T> type, SpongeMatcher<T> matcher) {
        this.type = type;
        this.matcher = matcher;
    }

    public Type<T> getType() {
        return type;
    }

    public SpongeMatcher<T> getMatcher() {
        return matcher;
    }

    public static final class Type<T> {
        
        public static final Type<Boolean> BOOLEAN = new Type<Boolean>(Boolean.class);
        public static final Type<Integer> INTEGER = new Type<Integer>(Integer.class);
        public static final Type<Double> DOUBLE = new Type<Double>(Double.class);

        private Class<T> typeClass;

        private Type(Class<T> typeClass) {
            this.typeClass = typeClass;
        }

        public Class<T> getTypeClass() {
            return typeClass;
        }
    }
}
