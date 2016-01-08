package org.monospark.spongematchers.matcher;

import java.util.Arrays;

public final class IntMatchers {

    private IntMatchers() {}
    
    public static SpongeMatcher<Integer> value(int value) {
        return new SpongeMatcher<Integer>() {
            @Override
            public boolean matches(Integer o) {
                return o.intValue() == value;
            }
        };
    }
    
    public static SpongeMatcher<Integer> range(int start, int end) {
        if(start > end) {
            throw new IllegalArgumentException("The start value of the range must be less than the end value");
        }
        if(start == end) {
            throw new IllegalArgumentException("The start value must not be equal to the end value");
        }
        
        return new SpongeMatcher<Integer>() {
            @Override
            public boolean matches(Integer o) {
                return o.intValue() >= start && o.intValue() <= end;
            }
        };
    }
    
    public static SpongeMatcher<Integer> amount(int[] values) {
        return new SpongeMatcher<Integer>() {
            @Override
            public boolean matches(Integer o) {
                return Arrays.stream(values).anyMatch(i -> o.intValue() == i);
            }
        };
    }
    
    public static SpongeMatcher<Integer> greaterThan(int value) {
        return new SpongeMatcher<Integer>() {
            @Override
            public boolean matches(Integer o) {
                return o.intValue() > value;
            }
        };
    }
    
    public static SpongeMatcher<Integer> greaterThanOrEqual(int value) {
        return new SpongeMatcher<Integer>() {
            @Override
            public boolean matches(Integer o) {
                return o.intValue() >= value;
            }
        };
    }
    
    public static SpongeMatcher<Integer> lessThan(int value) {
        return new SpongeMatcher<Integer>() {
            @Override
            public boolean matches(Integer o) {
                return o.intValue() < value;
            }
        };
    }
    
    public static SpongeMatcher<Integer> lessThanOrEqual(int value) {
        return new SpongeMatcher<Integer>() {
            @Override
            public boolean matches(Integer o) {
                return o.intValue() <= value;
            }
        };
    }
}
