package org.monospark.spongematchers.matcher.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;

import com.google.common.collect.Maps;

public abstract class SpongeObjectMatcher<T> implements SpongeMatcher<T> {

    private SpongeMatcher<Map<String, Object>> matcher; 

    public SpongeObjectMatcher(SpongeMatcher<Map<String, Object>> matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean matches(T o) {
        Map<String, Object> map = Maps.newHashMap();
        fillMap(o, map);
        return matcher.matches(map);
    }

    protected abstract void fillMap(T o, Map<String, Object> map);

    @Override
    public String toString() {
       return matcher.toString();
    }
}
