package org.monospark.spongematchers.matcher.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.MapMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.world.Dimension;

public final class DimensionMatcher extends SpongeObjectMatcher<Dimension> {

    public static SpongeMatcher<Dimension> create(SpongeMatcher<String> name, SpongeMatcher<String> type,
            SpongeMatcher<Boolean> respawnAllowed, SpongeMatcher<Boolean> waterEvaporating,
            SpongeMatcher<Boolean> sky, SpongeMatcher<Long> height, SpongeMatcher<Long> buildHeight) {
        SpongeMatcher<Map<String, Object>> matcher = MapMatcher.builder()
                .addMatcher("name", MatcherType.STRING, name)
                .addMatcher("type", MatcherType.STRING, type)
                .addMatcher("respawnAllowed", MatcherType.BOOLEAN, respawnAllowed)
                .addMatcher("waterEvaporating", MatcherType.BOOLEAN, waterEvaporating)
                .addMatcher("sky", MatcherType.BOOLEAN, sky)
                .addMatcher("height", MatcherType.INTEGER, height)
                .addMatcher("buildHeight", MatcherType.INTEGER, buildHeight)
                .build();
        return new DimensionMatcher(matcher);
    }

    public static SpongeMatcher<Dimension> create(SpongeMatcher<Map<String, Object>> matcher) {
        return new DimensionMatcher(matcher);
    }

    private DimensionMatcher(SpongeMatcher<Map<String, Object>> matcher) {
        super(matcher);
    }

    @Override
    protected void fillMap(Dimension o, Map<String, Object> map) {
        map.put("name", o.getName());
        map.put("type", o.getType().getId());
        map.put("respawnAllowed", o.allowsPlayerRespawns());
        map.put("waterEvaporating", o.doesWaterEvaporate());
        map.put("sky", o.hasSky());
        map.put("height", (long) o.getHeight());
        map.put("buildHeight", (long) o.getBuildHeight());
    }
}
