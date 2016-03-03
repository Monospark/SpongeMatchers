package org.monospark.spongematchers.type.sponge;

import java.util.Map;
import java.util.function.Function;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.sponge.EntityMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.monospark.spongematchers.type.advanced.FixedMapType;
import org.monospark.spongematchers.type.advanced.FixedMapType.Builder;
import org.spongepowered.api.entity.Entity;

public class EntityType<T extends Entity> extends SpongeObjectType<T> {

    @SuppressWarnings("unchecked")
    public EntityType() {
        super("entity", Entity.class, m -> (SpongeMatcher<T>) EntityMatcher.createEntityMatcher(m));
    }

    EntityType(String name, Class<?> typeClass, Function<SpongeMatcher<Map<String, Object>>,
            SpongeMatcher<T>> creationFunction) {
        super(name, typeClass, creationFunction);
    }

    @Override
    protected final MatcherType<Map<String, Object>> createType() {
        Builder builder = MatcherType.fixedMap()
                .addEntry("type", MatcherType.STRING)
                .addEntry("location", MatcherType.POSITION_LOCATION)
                .addEntry("rotX", MatcherType.FLOATING_POINT)
                .addEntry("rotY", MatcherType.FLOATING_POINT)
                .addEntry("rotZ", MatcherType.FLOATING_POINT)
                .addEntry("vehicle", MatcherType.optional(MatcherType.ENTITY))
                .addEntry("passenger", MatcherType.optional(MatcherType.ENTITY))
                .addEntry("baseVehicle", MatcherType.ENTITY);
        addAdditionalEntries(builder);
        return builder.build();
    }

    protected void addAdditionalEntries(FixedMapType.Builder builder) {}
}
