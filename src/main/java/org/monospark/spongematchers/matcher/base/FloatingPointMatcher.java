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
