package org.monospark.spongematchers.type.sponge;

import java.util.Map;
import java.util.function.Function;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.sponge.LivingMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.monospark.spongematchers.type.advanced.FixedMapType.Builder;
import org.monospark.spongematchers.util.GenericsHelper;
import org.spongepowered.api.entity.living.Living;

public class LivingType<T extends Living> extends EntityType<T> {

    public LivingType() {
        super("living entity", Living.class, m -> GenericsHelper.genericWrapper(LivingMatcher.createLivingMatcher(m)));
    }

    LivingType(String name, Class<?> typeClass, Function<SpongeMatcher<Map<String, Object>>,
            SpongeMatcher<T>> creationFunction) {
        super(name, typeClass, creationFunction);
    }

    @Override
    protected void addAdditionalEntries(Builder builder) {
        builder
                .addEntry("health", MatcherType.FLOATING_POINT)
                .addEntry("maxHealth", MatcherType.FLOATING_POINT);
    }
}
