package org.monospark.spongematchers.matcher.base;

import org.monospark.spongematchers.matcher.SpongeMatcher;

public final class IntegerMatcher {

    private IntegerMatcher() {}

    public static SpongeMatcher<Long> value(long value) {
        return new SpongeMatcher<Long>() {

            @Override
            public boolean matches(Long o) {
                return o.longValue() == value;
            }

            @Override
            public String toString() {
                return Long.toString(value);
            }
        };
    }

    public static SpongeMatcher<Long> greaterThan(long value) {
        return new SpongeMatcher<Long>() {

            @Override
            public boolean matches(Long o) {
                return o.longValue() > value;
            }

            @Override
            public String toString() {
                return ">" + Long.toString(value);
            }
        };
    }

    public static SpongeMatcher<Long> greaterThanOrEqual(long value) {
        return new SpongeMatcher<Long>() {

            @Override
            public boolean matches(Long o) {
                return o.longValue() >= value;
            }

            @Override
            public String toString() {
                return ">=" + Long.toString(value);
            }
        };
    }

    public static SpongeMatcher<Long> lessThan(long value) {
        return new SpongeMatcher<Long>() {

            @Override
            public boolean matches(Long o) {
                return o.longValue() < value;
            }

            @Override
            public String toString() {
                return "<" + Long.toString(value);
            }
        };
    }

    public static SpongeMatcher<Long> lessThanOrEqual(long value) {
        return new SpongeMatcher<Long>() {

            @Override
            public boolean matches(Long o) {
                return o.longValue() <= value;
            }

            @Override
            public String toString() {
                return "<=" + Long.toString(value);
            }
        };
    }
}
