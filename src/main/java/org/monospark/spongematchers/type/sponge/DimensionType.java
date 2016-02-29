package org.monospark.spongematchers.type.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.sponge.DimensionMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.world.Dimension;

public final class DimensionType extends SpongeObjectType<Dimension> {

    public DimensionType() {
        super("dimension", Dimension.class, m -> DimensionMatcher.create(m));
    }

    @Override
    protected MatcherType<Map<String, Object>> createType() {
        return MatcherType.fixedMap()
                .addEntry("name", MatcherType.STRING)
                .addEntry("type", MatcherType.STRING)
                .addEntry("respawnAllowed", MatcherType.BOOLEAN)
                .addEntry("waterEvaporating", MatcherType.BOOLEAN)
                .addEntry("sky", MatcherType.BOOLEAN)
                .addEntry("height", MatcherType.INTEGER)
                .addEntry("buildHeight", MatcherType.INTEGER)
                .build();
    }
}
