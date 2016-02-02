package org.monospark.spongematchers.matcher.sponge;

import java.util.Map;
import java.util.Optional;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.MapMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.item.inventory.ItemStack;

public final class ItemStackMatcher extends SpongeObjectMatcher<ItemStack> {

    public static SpongeMatcher<ItemStack> create(SpongeMatcher<String> type, SpongeMatcher<Long> damage,
            SpongeMatcher<Long> amount, SpongeMatcher<Optional<DataView>> data) {
        return new ItemStackMatcher(MapMatcher.builder()
                .addMatcher("type", MatcherType.STRING, type)
                .addMatcher("durability", MatcherType.INTEGER, damage)
                .addMatcher("quantity", MatcherType.INTEGER, amount)
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
        if (o.toContainer().getView(DataQuery.of("UnsafeData")).isPresent()) {
            map.put("data", o.toContainer().getView(DataQuery.of("UnsafeData")).get());
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private SpongeMatcher<String> typeMatcher;

        private SpongeMatcher<Long> amountMatcher;

        private SpongeMatcher<Long> amount;

        private SpongeMatcher<Optional<DataView>> dataMatcher;

        private Builder() {
            amountMatcher = SpongeMatcher.wildcard();
            amount = SpongeMatcher.wildcard();
            dataMatcher = SpongeMatcher.wildcard();
        }

        public Builder type(SpongeMatcher<String> typeMatcher) {
            this.typeMatcher = typeMatcher;
            return this;
        }

        public Builder damage(SpongeMatcher<Long> damageMatcher) {
            this.amountMatcher = damageMatcher;
            return this;
        }

        public Builder amount(SpongeMatcher<Long> amountMatcher) {
            this.amountMatcher = amountMatcher;
            return this;
        }

        public Builder data(SpongeMatcher<Optional<DataView>> dataMatcher) {
            this.dataMatcher = dataMatcher;
            return this;
        }

        public SpongeMatcher<ItemStack> build() {
            return create(typeMatcher, amountMatcher, amount, dataMatcher);
        }
    }
}
