package org.monospark.spongematchers.type;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.sponge.DataViewMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.spongepowered.api.data.DataView;

public final class DataViewType extends MatcherType<DataView> {

    private MatcherType<Map<String,Object>> type;
    
    DataViewType() {
        super("data view");
    }
    
    private MatcherType<Map<String,Object>> getMatcherType() {
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
    protected boolean canParse(StringElement element, boolean deep) {
        return getMatcherType().canParseMatcher(element, deep);
    }

    @Override
    protected SpongeMatcher<DataView> parse(StringElement element) throws SpongeMatcherParseException {
        return DataViewMatcher.create(getMatcherType().parseMatcher(element));
    }
}
