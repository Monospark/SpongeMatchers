package org.monospark.spongematchers.type;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.sponge.DataViewMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.spongepowered.api.data.DataView;

public final class DataViewType extends MatcherType<DataView> {

    private MatcherType<Map<String, Object>> type;

    DataViewType() {}

    private MatcherType<Map<String, Object>> getMatcherType() {
        if (type == null) {
            type = MatcherType.undefinedMap()
                    .addType(MatcherType.BOOLEAN)
                    .addType(MatcherType.INTEGER)
                    .addType(MatcherType.FLOATING_POINT)
                    .addType(MatcherType.STRING)
                    .addType(MatcherType.DATA_VIEW)
                    .addType(MatcherType.list(MatcherType.BOOLEAN))
                    .addType(MatcherType.list(MatcherType.INTEGER))
                    .addType(MatcherType.list(MatcherType.FLOATING_POINT))
                    .addType(MatcherType.list(MatcherType.STRING))
                    .addType(MatcherType.list(MatcherType.DATA_VIEW))
                    .build();
        }
        return type;
    }

    @Override
    public boolean canMatch(Object o) {
        return o instanceof DataView;
    }

    @Override
    protected boolean checkElement(StringElement element) {
        return getMatcherType().acceptsElement(element);
    }

    @Override
    protected SpongeMatcher<DataView> parse(StringElement element) throws SpongeMatcherParseException {
        try {
            return DataViewMatcher.create(getMatcherType().parseMatcher(element));
        } catch (SpongeMatcherParseException e) {
            throw new SpongeMatcherParseException("Couldn't parse data view matcher: " + element.getString(), e);
        }
    }
}
