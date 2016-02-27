package org.monospark.spongematchers.type;

import java.util.Map;

import org.monospark.spongematchers.matcher.sponge.DataViewMatcher;
import org.spongepowered.api.data.DataView;

public final class DataViewType extends SpongeObjectType<DataView> {

    DataViewType() {
        super("data view", DataView.class, m -> DataViewMatcher.create(m));
    }

    @Override
    protected MatcherType<Map<String, Object>> createType() {
        return MatcherType.variableMap(MatcherType.multi()
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
                .build());
    }
}
