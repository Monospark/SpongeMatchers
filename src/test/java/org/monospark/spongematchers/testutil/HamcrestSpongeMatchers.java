package org.monospark.spongematchers.testutil;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.monospark.spongematchers.matcher.SpongeMatcher;

public class HamcrestSpongeMatchers {

    public static <T> Matcher<SpongeMatcher<T>> matches(T object) {
        return new TypeSafeMatcher<SpongeMatcher<T>>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("matches " + object);
            }

            @Override
            protected boolean matchesSafely(SpongeMatcher<T> item) {
                return item.matches(object);
            }
        };
    }
}
