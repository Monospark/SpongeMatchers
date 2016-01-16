package org.monospark.spongematchers.matcher.base;

import java.util.Arrays;

import org.monospark.spongematchers.matcher.SpongeMatcher;

public final class IntegerMatcher {

    private IntegerMatcher() {}
    
    public static SpongeMatcher<Long> value(long value) {
        return new SpongeMatcher<Long>() {
            @Override
            public boolean matches(Long o) {
                return o.longValue() == value;
            }
        };
    }
    
    public static SpongeMatcher<Long> range(long start, long end) {
        if(start > end) {
            throw new IllegalArgumentException("The start value of the range must be less than the end value");
        }
        if(start == end) {
            throw new IllegalArgumentException("The start value must not be equal to the end value");
        }
        
        return new SpongeMatcher<Long>() {
            @Override
            public boolean matches(Long o) {
                return o.longValue() >= start && o.longValue() <= end;
            }
        };
    }
    
    public static SpongeMatcher<Long> amount(long[] values) {
        return new SpongeMatcher<Long>() {
            @Override
            public boolean matches(Long o) {
                return Arrays.stream(values).anyMatch(i -> o.longValue() == i);
            }
        };
    }
    
    public static SpongeMatcher<Long> greaterThan(long value) {
        return new SpongeMatcher<Long>() {
            @Override
            public boolean matches(Long o) {
                return o.longValue() > value;
            }
        };
    }
    
    public static SpongeMatcher<Long> greaterThanOrEqual(long value) {
        return new SpongeMatcher<Long>() {
            @Override
            public boolean matches(Long o) {
                return o.longValue() >= value;
            }
        };
    }
    
    public static SpongeMatcher<Long> lessThan(long value) {
        return new SpongeMatcher<Long>() {
            @Override
            public boolean matches(Long o) {
                return o.longValue() < value;
            }
        };
    }
    
    public static SpongeMatcher<Long> lessThanOrEqual(long value) {
        return new SpongeMatcher<Long>() {
            @Override
            public boolean matches(Long o) {
                return o.longValue() <= value;
            }
        };
    }
}
