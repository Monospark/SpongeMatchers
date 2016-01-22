package org.monospark.spongematchers.matcher;


public final class MatcherLogic {

    private MatcherLogic() {}
    
    public static <T> SpongeMatcher<T> or(SpongeMatcher<T> matcher1, SpongeMatcher<T> matcher2) {
        return o -> matcher1.matches(o) || matcher2.matches(o);
    }
    
    public static <T> SpongeMatcher<T> and(SpongeMatcher<T> matcher1, SpongeMatcher<T> matcher2) {
        return o -> matcher1.matches(o) && matcher2.matches(o);
    }
    
    public static <T> SpongeMatcher<T> not(SpongeMatcher<T> matcher) {
        return o -> !matcher.matches(o);
    }
}
