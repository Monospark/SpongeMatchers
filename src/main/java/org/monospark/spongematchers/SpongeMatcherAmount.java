package org.monospark.spongematchers;

import java.util.Set;

public final class SpongeMatcherAmount<T> implements SpongeMatcher<T> {

    private Set<SpongeMatcher<T>> matchers;

    public SpongeMatcherAmount(Set<SpongeMatcher<T>> matchers) {
        this.matchers = matchers;
    }

    @Override
    public boolean matches(T o) {
        for (SpongeMatcher<T> m : matchers) {
            if (m.matches(o)) {
                return true;
            }
        }
        return false;
    }
}
