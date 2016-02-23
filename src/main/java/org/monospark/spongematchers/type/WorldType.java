package org.monospark.spongematchers.type;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.sponge.WorldMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.spongepowered.api.world.World;

public final class WorldType extends MatcherType<World> {

    private static final MatcherType<Map<String, Object>> TYPE = MatcherType.definedMap()
            .addEntry("name", MatcherType.STRING)
            .addEntry("dimension", MatcherType.DIMENSION)
            .addEntry("seed", MatcherType.INTEGER)
            .addEntry("gameRules", MatcherType.undefinedMap().addType(MatcherType.STRING).build())
            .build();

    WorldType() {}

    @Override
    public boolean canMatch(Object o) {
        return o instanceof World;
    }

    @Override
    protected boolean checkElement(StringElement element) {
        return TYPE.acceptsElement(element);
    }

    @Override
    protected SpongeMatcher<World> parse(StringElement element) throws SpongeMatcherParseException {
        try {
            return WorldMatcher.create(TYPE.parseMatcher(element));
        } catch (SpongeMatcherParseException e) {
            throw new SpongeMatcherParseException("Couldn't parse world matcher: "
                    + element.getString(), e);
        }
    }
}
