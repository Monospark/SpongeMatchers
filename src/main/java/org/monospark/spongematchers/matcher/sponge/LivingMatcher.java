package org.monospark.spongematchers.matcher.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.entity.living.Living;

public class LivingMatcher<T extends Living> extends EntityMatcher<T> {

    public static SpongeMatcher<Living> createLivingMatcher(SpongeMatcher<Map<String, Object>> matcher) {
        return new LivingMatcher<Living>(MatcherType.LIVING, matcher);
    }

    protected LivingMatcher(MatcherType<T> type, SpongeMatcher<Map<String, Object>> matcher) {
        super(type, matcher);
    }

    @Override
    protected void fillMap(T o, Map<String, Object> map) {
        super.fillMap(o, map);
        map.put("health", o.health().get());
        map.put("maxHealth", o.maxHealth().get());
    }
}
