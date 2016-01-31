package org.monospark.spongematchers.type;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.sponge.PositionLocationMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.spongepowered.api.world.Location;

public final class PositionLocationType extends MatcherType<Location<?>> {

    private static final MatcherType<Map<String,Object>> TYPE = MatcherType.definedMap()
            .addEntry("x", MatcherType.FLOATING_POINT)
            .addEntry("y", MatcherType.FLOATING_POINT)
            .addEntry("z", MatcherType.FLOATING_POINT)
            .build();
    
    protected PositionLocationType() {
        super("position location");
    }

    @Override
    public boolean canMatch(Object o) {
        if (!(o instanceof Location)) {
            return false;
        }
        
        return !((Location<?>) o).hasBlock() && !((Location<?>) o).hasBiome();
    }

    @Override
    protected boolean canParse(StringElement element, boolean deep) {
        return TYPE.canParseMatcher(element, deep);
    }

    @Override
    protected SpongeMatcher<Location<?>> parse(StringElement element) throws SpongeMatcherParseException {
        return PositionLocationMatcher.create(TYPE.parseMatcher(element));
    }
}