package org.monospark.spongematchers.matcher.sponge;

import java.util.Map;
import java.util.Optional;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.MapMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.property.PropertyHolder;
import org.spongepowered.api.item.inventory.ItemStack;

public final class ItemStackMatcher extends SpongeObjectMatcher<ItemStack> {

    public static SpongeMatcher<ItemStack> create(SpongeMatcher<String> type, SpongeMatcher<Long> damage,
            SpongeMatcher<Long> amount, SpongeMatcher<PropertyHolder> properties,
            SpongeMatcher<Optional<DataView>> data) {
        return new ItemStackMatcher(MapMatcher.builder()
                .addMatcher("type", MatcherType.STRING, type)
                .addMatcher("durability", MatcherType.INTEGER, damage)
                .addMatcher("quantity", MatcherType.INTEGER, amount)
                .addMatcher("properties", MatcherType.PROPERTY_HOLDER, properties)
                .addOptionalMatcher("data", MatcherType.DATA_VIEW, data)
                .build());
    }

    public static ItemStackMatcher create(SpongeMatcher<Map<String, Object>> matcher) {
        return new ItemStackMatcher(matcher);
    }

    private ItemStackMatcher(SpongeMatcher<Map<String, Object>> matcher) {
        super(matcher);
    }

    @Override
    protected void fillMap(ItemStack o, Map<String, Object> map) {
        map.put("type", o.getItem().getId());
        map.put("durability", o.toContainer().getLong(DataQuery.of("UnsafeDamage")).get());
        map.put("quantity", (long) o.getQuantity());
        map.put("properties", o);
        if (o.toContainer().getView(DataQuery.of("UnsafeData")).isPresent()) {
            map.put("data", o.toContainer().getView(DataQuery.of("UnsafeData")).get());
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private SpongeMatcher<String> typeMatcher;

        private SpongeMatcher<Long> durabilityMatcher;

        private SpongeMatcher<Long> quantityMatcher;

        private SpongeMatcher<PropertyHolder> propertiesMatcher;

        private SpongeMatcher<Optional<DataView>> dataMatcher;

        private Builder() {
            typeMatcher = SpongeMatcher.wildcard();
            durabilityMatcher = SpongeMatcher.wildcard();
            quantityMatcher = SpongeMatcher.wildcard();
            propertiesMatcher = SpongeMatcher.wildcard();
            dataMatcher = SpongeMatcher.wildcard();
        }

        public Builder type(SpongeMatcher<String> typeMatcher) {
            this.typeMatcher = typeMatcher;
            return this;
        }

        public Builder durability(SpongeMatcher<Long> durabilityMatcher) {
            this.durabilityMatcher = durabilityMatcher;
            return this;
        }

        public Builder quantity(SpongeMatcher<Long> quantityMatcher) {
            this.quantityMatcher = quantityMatcher;
            return this;
        }

        public Builder properties(SpongeMatcher<PropertyHolder> propertiesMatcher) {
            this.propertiesMatcher = propertiesMatcher;
            return this;
        }

        public Builder data(SpongeMatcher<Optional<DataView>> dataMatcher) {
            this.dataMatcher = dataMatcher;
            return this;
        }

        public SpongeMatcher<ItemStack> build() {
            return create(typeMatcher, durabilityMatcher, quantityMatcher, propertiesMatcher, dataMatcher);
        }
    }
}
