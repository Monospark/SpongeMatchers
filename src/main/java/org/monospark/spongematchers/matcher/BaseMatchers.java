package org.monospark.spongematchers.matcher;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import com.google.common.collect.Sets;

public final class BaseMatchers {

    private BaseMatchers() {}
    
    public static <T, M extends SpongeMatcher<T>> M wildcard() {
        @SuppressWarnings("unchecked")
        M matcher = (M) new SpongeMatcher<T>() {

            @Override
            public boolean matches(T o) {
                return true;
            }
        };
        return matcher;
    }
    
    public static <T> SpongeMatcher<Optional<T>> optionalWrapper(SpongeMatcher<T> matcher) {
        return new SpongeMatcher<Optional<T>>() {
            @Override
            public boolean matches(Optional<T> o) {
                return o.isPresent() ? matcher.matches(o.get()) : false;
            }
        };
    }
    
    @SafeVarargs
    public static <T> SpongeMatcher<T> amount(SpongeMatcher<T>... matchers) {
        return amount(Sets.newHashSet(matchers));
    }
    
    public static <T> SpongeMatcher<T> amount(Set<SpongeMatcher<T>> matchers) {
        return new SpongeMatcher<T>() {
            @Override
            public boolean matches(T o) {
                for (SpongeMatcher<T> m : matchers) {
                    if (m.matches(o)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }
    
    public static <T> SpongeMatcher<List<T>> listWrapper(SpongeMatcher<T> matcher, boolean allMatch) {
        return new SpongeMatcher<List<T>>() {
            @Override
            public boolean matches(List<T> o) {
                for (T element : o) {
                    boolean matches = matcher.matches(element);
                    if (matches && !allMatch) {
                        return true;
                    }
                    else if (!matches && allMatch) {
                        return false;
                    }
                }
                return true;
            }
        };
    }

    public static SpongeMatcher<Integer> intValue(int value) {
        return new SpongeMatcher<Integer>() {
            @Override
            public boolean matches(Integer o) {
                return o.intValue() == value;
            }
        };
    }
    
    public static SpongeMatcher<Integer> intRange(int start, int end) {
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
    
    public static SpongeMatcher<Integer> intAmount(int[] values) {
        return new SpongeMatcher<Integer>() {
            @Override
            public boolean matches(Integer o) {
                return Arrays.stream(values).anyMatch(i -> o.intValue() == i);
            }
        };
    }
    
    public static SpongeMatcher<Integer> intGreaterThan(int value) {
        return new SpongeMatcher<Integer>() {
            @Override
            public boolean matches(Integer o) {
                return o.intValue() > value;
            }
        };
    }
    
    public static SpongeMatcher<Integer> intGreaterThanOrEqual(int value) {
        return new SpongeMatcher<Integer>() {
            @Override
            public boolean matches(Integer o) {
                return o.intValue() >= value;
            }
        };
    }
    
    public static SpongeMatcher<Integer> intLessThan(int value) {
        return new SpongeMatcher<Integer>() {
            @Override
            public boolean matches(Integer o) {
                return o.intValue() < value;
            }
        };
    }
    
    public static SpongeMatcher<Integer> intLessThanOrEqual(int value) {
        return new SpongeMatcher<Integer>() {
            @Override
            public boolean matches(Integer o) {
                return o.intValue() <= value;
            }
        };
    }
    
    public static SpongeMatcher<String> regex(String regex) {
        Pattern pattern = Pattern.compile(regex);
        return new SpongeMatcher<String>() {

            @Override
            public boolean matches(String o) {
                return pattern.matcher(o).matches();
            }
        };
    }
}
