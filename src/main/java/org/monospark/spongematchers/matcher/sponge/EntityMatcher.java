package org.monospark.spongematchers.matcher.sponge;

import java.util.Map;
import java.util.Optional;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.FixedMapMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public final class EntityMatcher extends SpongeObjectMatcher<Entity> {

    public static SpongeMatcher<Entity> create(SpongeMatcher<String> type, SpongeMatcher<Location<World>> location,
            SpongeMatcher<Double> rotX, SpongeMatcher<Double> rotY, SpongeMatcher<Double> rotZ,
            SpongeMatcher<Optional<Entity>> vehicle, SpongeMatcher<Optional<Entity>> passenger,
            SpongeMatcher<Entity> baseVehicle) {
        SpongeMatcher<Map<String, Object>> matcher = FixedMapMatcher.builder()
                .addMatcher("type", type)
                .addMatcher("location", location)
                .addMatcher("rotX", rotX)
                .addMatcher("rotY", rotY)
                .addMatcher("rotZ", rotZ)
                .addMatcher("vehicle", vehicle)
                .addMatcher("passenger", passenger)
                .addMatcher("baseVehicle", baseVehicle)
                .build();

        return new EntityMatcher(matcher);
    }

    public static SpongeMatcher<Entity> create(SpongeMatcher<Map<String, Object>> matcher) {
        return new EntityMatcher(matcher);
    }

    private EntityMatcher(SpongeMatcher<Map<String, Object>> matcher) {
        super(MatcherType.ENTITY, matcher);
    }

    @Override
    protected void fillMap(Entity o, Map<String, Object> map) {
        map.put("type", o.getType().getId());
        map.put("location", o.getLocation());
        map.put("rotX", o.getRotation().getX());
        map.put("rotY", o.getRotation().getY());
        map.put("rotZ", o.getRotation().getZ());
        map.put("vehicle", o.getVehicle());
        map.put("passenger", o.getPassenger());
        map.put("baseVehicle", o.getBaseVehicle());
    }
}
