package org.monospark.spongematchers.matcher.base;

import org.monospark.spongematchers.matcher.SpongeMatcher;

public final class FloatingPointMatcher {

    private FloatingPointMatcher() {}

    public static SpongeMatcher<Double> value(double value) {
        return new SpongeMatcher<Double>() {

            @Override
            public boolean matches(Double o) {
                return o.doubleValue() == value;
            }

            @Override
            public String toString() {
                return Double.toString(value);
            }
        };
    }

    public static SpongeMatcher<Double> range(double start, double end) {
        if (start > end) {
            throw new IllegalArgumentException("The start value of the range must be less than the end value");
        }
        if (start == end) {
            throw new IllegalArgumentException("The start value must not be equal to the end value");
        }

        return new SpongeMatcher<Double>() {

            @Override
            public boolean matches(Double o) {
                return o.doubleValue() >= start && o.doubleValue() <= end;
            }

            @Override
            public String toString() {
                return Double.toString(start) + "-" + Double.toString(end);
            }
        };
    }

    public static SpongeMatcher<Double> greaterThan(double value) {
        return new SpongeMatcher<Double>() {

            @Override
            public boolean matches(Double o) {
                return o.doubleValue() > value;
            }

            @Override
            public String toString() {
                return ">" + Double.toString(value);
            }
        };
    }

    public static SpongeMatcher<Double> greaterThanOrEqual(double value) {
        return new SpongeMatcher<Double>() {

            @Override
            public boolean matches(Double o) {
                return o.doubleValue() >= value;
            }

            @Override
            public String toString() {
                return ">=" + Double.toString(value);
            }
        };
    }

    public static SpongeMatcher<Double> lessThan(double value) {
        return new SpongeMatcher<Double>() {

            @Override
            public boolean matches(Double o) {
                return o.doubleValue() < value;
            }

            @Override
            public String toString() {
                return "<" + Double.toString(value);
            }
        };
    }

    public static SpongeMatcher<Double> lessThanOrEqual(double value) {
        return new SpongeMatcher<Double>() {

            @Override
            public boolean matches(Double o) {
                return o.doubleValue() <= value;
            }

            @Override
            public String toString() {
                return "<=" + Double.toString(value);
            }
        };
    }
}
