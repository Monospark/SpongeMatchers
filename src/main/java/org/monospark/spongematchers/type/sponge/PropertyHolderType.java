package org.monospark.spongematchers.type.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.sponge.PropertyHolderMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.data.property.PropertyHolder;

public final class PropertyHolderType extends SpongeObjectType<PropertyHolder> {

    public PropertyHolderType() {
        super("property holder", PropertyHolder.class, m -> PropertyHolderMatcher.create(m));
    }

    @Override
    protected MatcherType<Map<String, Object>> createType() {
        return MatcherType.variableMap(MatcherType.multi()
                .addType(MatcherType.BOOLEAN)
                .addType(MatcherType.INTEGER)
                .addType(MatcherType.FLOATING_POINT)
                .addType(MatcherType.STRING)
                .build());
    }
}
