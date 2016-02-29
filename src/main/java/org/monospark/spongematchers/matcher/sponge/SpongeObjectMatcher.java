package org.monospark.spongematchers.matcher.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.type.MatcherType;

import com.google.common.collect.Maps;

public abstract class SpongeObjectMatcher<T> extends SpongeMatcher<T> {

    private SpongeMatcher<Map<String, Object>> matcher;

    public SpongeObjectMatcher(MatcherType<T> type, SpongeMatcher<Map<String, Object>> matcher) {
        super(type);
        this.matcher = matcher;
    }

    @Override
    public final boolean matches(T o) {
        Map<String, Object> map = Maps.newHashMap();
        fillMap(o, map);
        return matcher.matches(map);
    }

    protected abstract void fillMap(T o, Map<String, Object> map);

    @Override
    public final String toString() {
        return matcher.toString();
    }
}
