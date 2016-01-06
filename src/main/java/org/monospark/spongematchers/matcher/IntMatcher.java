package org.monospark.spongematchers.matcher;

import java.util.Arrays;

public abstract class IntMatcher implements SpongeMatcher<Integer> {

    public static final class Value extends IntMatcher {
        
        private int value;
        
        public Value(int value) {
            if (value < 0) {
                throw new IllegalArgumentException("Value must be positive");
            }
            
            this.value = value;
        }

        @Override
        public boolean matches(Integer o) {
            return o.intValue() == value;
        }
    }
    
    public static final class Range extends IntMatcher {
        
        private int start;
        
        private int end;
        
        public Range(int start, int end) {
            if (start < 0 || end < 0) {
                throw new IllegalArgumentException("All values must be positive");
            }
            if(start > end) {
                throw new IllegalArgumentException("The start value of the range must be less than the end value");
            }
            if(start == end) {
                throw new IllegalArgumentException("The start value must not be equal to the end value");
            }
            
            this.start = start;
            this.end = end;
        }

        @Override
        public boolean matches(Integer o) {
            return o.intValue() >= start && o.intValue() <= end;
        }
    }
    
    public static final class Amount extends IntMatcher {
        
        private int[] values;
        
        public Amount(int[] values) {
            for (int value : values) {
                if (value < 0) {
                    throw new IllegalArgumentException("All values must be positive");
                }
            }
            
            this.values = values;
        }

        @Override
        public boolean matches(Integer o) {
            return Arrays.stream(values).anyMatch(i -> o.intValue() == i);
        }
    }
    
    public static final class GreaterThan extends IntMatcher {
        
        private int value;
        
        public GreaterThan(int value) {
            if (value < 0) {
                throw new IllegalArgumentException("Value must be positive");
            }
            
            this.value = value;
        }

        @Override
        public boolean matches(Integer o) {
            return o.intValue() > value;
        }
    }
    
    public static final class GreaterThanOrEqual extends IntMatcher {
        
        private int value;
        
        public GreaterThanOrEqual(int value) {
            if (value < 0) {
                throw new IllegalArgumentException("Value must be positive");
            }
            
            this.value = value;
        }

        @Override
        public boolean matches(Integer o) {
            return o.intValue() >= value;
        }
    }
    
    public static final class LessThan extends IntMatcher {
        
        private int value;
        
        public LessThan(int value) {
            if (value < 0) {
                throw new IllegalArgumentException("Value must be positive");
            }
            
            this.value = value;
        }

        @Override
        public boolean matches(Integer o) {
            return o.intValue() < value;
        }
    }
    
    public static final class LessThanOrEqual extends IntMatcher {
        
        private int value;
        
        public LessThanOrEqual(int value) {
            if (value < 0) {
                throw new IllegalArgumentException("Value must be positive");
            }
            
            this.value = value;
        }

        @Override
        public boolean matches(Integer o) {
            return o.intValue() <= value;
        }
    } 
}
