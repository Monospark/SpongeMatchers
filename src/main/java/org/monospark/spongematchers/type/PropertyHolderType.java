package org.monospark.spongematchers.type;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.sponge.PropertyHolderMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.spongepowered.api.data.property.PropertyHolder;

public final class PropertyHolderType extends MatcherType<PropertyHolder> {

    private MatcherType<Map<String, Object>> type = MatcherType.undefinedMap()
            .addType(MatcherType.BOOLEAN)
            .addType(MatcherType.INTEGER)
            .addType(MatcherType.FLOATING_POINT)
            .addType(MatcherType.STRING)
            .build();

    PropertyHolderType() {
        super("property holder");
    }

    @Override
    public boolean canMatch(Object o) {
        return o instanceof PropertyHolder;
    }

    @Override
    protected boolean canParse(StringElement element, boolean deep) {
        return type.canParseMatcher(element, deep);
    }

    @Override
    protected SpongeMatcher<PropertyHolder> parse(StringElement element) throws SpongeMatcherParseException {
        return PropertyHolderMatcher.create(type.parseMatcher(element));
    }
}
