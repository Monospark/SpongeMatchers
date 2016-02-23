package org.monospark.spongematchers.type;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.sponge.PropertyHolderMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.spongepowered.api.data.property.PropertyHolder;

public final class PropertyHolderType extends MatcherType<PropertyHolder> {

    private static final MatcherType<Map<String, Object>> TYPE = MatcherType.undefinedMap()
            .addType(MatcherType.BOOLEAN)
            .addType(MatcherType.INTEGER)
            .addType(MatcherType.FLOATING_POINT)
            .addType(MatcherType.STRING)
            .build();

    PropertyHolderType() {}

    @Override
    public boolean canMatch(Object o) {
        return o instanceof PropertyHolder;
    }

    @Override
    protected boolean checkElement(StringElement element) {
        return TYPE.acceptsElement(element);
    }

    @Override
    protected SpongeMatcher<PropertyHolder> parse(StringElement element) throws SpongeMatcherParseException {
        try {
            return PropertyHolderMatcher.create(TYPE.parseMatcher(element));
        } catch (SpongeMatcherParseException e) {
            throw new SpongeMatcherParseException("Couldn't parse property holder matcher: "
                    + element.getString(), e);
        }
    }
}
