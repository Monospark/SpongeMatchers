package org.monospark.spongematchers.matcher.sponge;

import java.util.function.Function;

import org.monospark.spongematchers.matcher.CompoundMatcher;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.spongepowered.api.world.Location;

import com.flowpowered.math.vector.Vector2i;
import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;

public final class LocationMatcher<T> implements SpongeMatcher<Location<?>>{

    public static SpongeMatcher<Location<?>> position(SpongeMatcher<Double> x, SpongeMatcher<Double> y,
            SpongeMatcher<Double> z) {
        SpongeMatcher<Vector3d> posMatcher = CompoundMatcher.<Vector3d>builder()
                .addMatcher(x, v -> v.getX())
                .addMatcher(y, v -> v.getY())
                .addMatcher(z, v -> v.getZ())
                .build();
        return new LocationMatcher<>(Type.POSITION, posMatcher);
    }
    
    public static SpongeMatcher<Location<?>> block(SpongeMatcher<Integer> x, SpongeMatcher<Integer> y,
            SpongeMatcher<Integer> z) {
        SpongeMatcher<Vector3i> posMatcher = CompoundMatcher.<Vector3i>builder()
                .addMatcher(x, v -> v.getX())
                .addMatcher(y, v -> v.getY())
                .addMatcher(z, v -> v.getZ())
                .build();
        return new LocationMatcher<>(Type.BLOCK, posMatcher);
    }
    
    public static SpongeMatcher<Location<?>> biome(SpongeMatcher<Integer> x, SpongeMatcher<Integer> y) {
        SpongeMatcher<Vector2i> posMatcher = CompoundMatcher.<Vector2i>builder()
                .addMatcher(x, v -> v.getX())
                .addMatcher(y, v -> v.getY())
                .build();
        return new LocationMatcher<>(Type.BIOME, posMatcher);
    }
    
    private Type<T> type;
    
    private SpongeMatcher<T> matcher;

    private LocationMatcher(Type<T> type, SpongeMatcher<T> matcher) {
        this.type = type;
        this.matcher = matcher;
    }

    @Override
    public boolean matches(Location<?> o) {
        return matcher.matches(type.getLocationFunction().apply(o));
    }

    private static final class Type<T> {
        
        public static final Type<Vector3d> POSITION = new Type<Vector3d>(l -> l.getPosition());
        public static final Type<Vector3i> BLOCK = new Type<Vector3i>(l -> l.getBlockPosition());
        public static final Type<Vector2i> BIOME = new Type<Vector2i>(l -> l.getBiomePosition());
        
        private Function<Location<?>, T> locationFunction;

        private Type(Function<Location<?>, T> locationFunction) {
            this.locationFunction = locationFunction;
        }
        
        public Function<Location<?>, T> getLocationFunction() {
            return locationFunction;
        }
    }
}
