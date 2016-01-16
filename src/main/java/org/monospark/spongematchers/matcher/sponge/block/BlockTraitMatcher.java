package org.monospark.spongematchers.matcher.sponge.block;

import java.util.function.Function;

import org.monospark.spongematchers.matcher.SpongeMatcher;

public final class BlockTraitMatcher<T> {

    private Type<T> type;
    
    private SpongeMatcher<T> matcher;

    public BlockTraitMatcher(Type<T> type, SpongeMatcher<T> matcher) {
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
        
        public static final Type<Boolean> BOOLEAN_TRAIT = new Type<Boolean>(Boolean.class, o -> (Boolean) o);
        public static final Type<Integer> INTEGER_TRAIT = new Type<Integer>(Integer.class, o -> (Integer) o);
        public static final Type<String> ENUM_TRAIT = new Type<String>(String.class, o -> o.toString());

        private Class<T> typeClass;
        
        private Function<Object, T> valueFunction;

        private Type(Class<T> typeClass, Function<Object, T> valueFunction) {
            this.typeClass = typeClass;
            this.valueFunction = valueFunction;
        }

        public Class<T> getTypeClass() {
            return typeClass;
        }

        public Function<Object, T> getValueFunction() {
            return valueFunction;
        }
    }
}
