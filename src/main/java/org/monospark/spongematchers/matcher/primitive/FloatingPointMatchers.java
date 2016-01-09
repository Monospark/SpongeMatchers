package org.monospark.spongematchers.matcher.primitive;

import java.util.Arrays;

import org.monospark.spongematchers.matcher.SpongeMatcher;

public final class FloatingPointMatchers {

    private FloatingPointMatchers() {}
    
    public static SpongeMatcher<Double> value(double value) {
        return new SpongeMatcher<Double>() {
            @Override
            public boolean matches(Double o) {
                return o.doubleValue() == value;
            }
        };
    }
    
    public static SpongeMatcher<Double> range(double start, double end) {
        if(start > end) {
            throw new IllegalArgumentException("The start value of the range must be less than the end value");
        }
        if(start == end) {
            throw new IllegalArgumentException("The start value must not be equal to the end value");
        }
        
        return new SpongeMatcher<Double>() {
            @Override
            public boolean matches(Double o) {
                return o.doubleValue() >= start && o.doubleValue() <= end;
            }
        };
    }
    
    public static SpongeMatcher<Double> amount(double[] values) {
        return new SpongeMatcher<Double>() {
            @Override
            public boolean matches(Double o) {
                return Arrays.stream(values).anyMatch(i -> o.doubleValue() == i);
            }
        };
    }
    
    public static SpongeMatcher<Double> greaterThan(double value) {
        return new SpongeMatcher<Double>() {
            @Override
            public boolean matches(Double o) {
                return o.doubleValue() > value;
            }
        };
    }
    
    public static SpongeMatcher<Double> greaterThanOrEqual(double value) {
        return new SpongeMatcher<Double>() {
            @Override
            public boolean matches(Double o) {
                return o.doubleValue() >= value;
            }
        };
    }
    
    public static SpongeMatcher<Double> lessThan(double value) {
        return new SpongeMatcher<Double>() {
            @Override
            public boolean matches(Double o) {
                return o.doubleValue() < value;
            }
        };
    }
    
    public static SpongeMatcher<Double> lessThanOrEqual(double value) {
        return new SpongeMatcher<Double>() {
            @Override
            public boolean matches(Double o) {
                return o.doubleValue() <= value;
            }
        };
    }
}
