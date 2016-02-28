package org.monospark.spongematchers.type;

import java.util.Map;

import org.monospark.spongematchers.matcher.sponge.EntityMatcher;
import org.spongepowered.api.entity.Entity;

public final class EntityType extends SpongeObjectType<Entity> {

    EntityType() {
        super("entity", Entity.class, m -> EntityMatcher.create(m));
    }

    @Override
    protected MatcherType<Map<String, Object>> createType() {
        return MatcherType.fixedMap()
                .addEntry("type", MatcherType.STRING)
                .addEntry("location", MatcherType.POSITION_LOCATION)
                .addEntry("rotX", MatcherType.FLOATING_POINT)
                .addEntry("rotY", MatcherType.FLOATING_POINT)
                .addEntry("rotZ", MatcherType.FLOATING_POINT)
                .addEntry("vehicle", MatcherType.optional(MatcherType.ENTITY))
                .addEntry("passenger", MatcherType.optional(MatcherType.ENTITY))
                .addEntry("baseVehicle", MatcherType.ENTITY)
                .build();
    }
}
