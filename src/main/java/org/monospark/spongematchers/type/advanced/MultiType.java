package org.monospark.spongematchers.type.advanced;

import java.util.Set;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.monospark.spongematchers.type.MatcherType;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;

public final class MultiType extends MatcherType<Object> {

    private Set<MatcherType<?>> types;

    public MultiType(Set<MatcherType<?>> types) {
        this.types = types;
    }

    @Override
    public boolean canMatch(Object o) {
        for (MatcherType<?> type : types) {
            if (type.canMatch(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean canParse(StringElement element) {
        for (MatcherType<?> type : types) {
            if (type.canParseMatcher(element)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected SpongeMatcher<Object> parse(StringElement element) throws SpongeMatcherParseException {
        for (MatcherType<?> type : types) {
            if (type.canParseMatcher(element)) {
                return (SpongeMatcher<Object>) type.parseMatcher(element);
            }
        }
        throw new SpongeMatcherParseException("Unsupported matcher type: " + element.getString() + ", allowed: "
                + Joiner.on(",").join(types));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Set<MatcherType<?>> types;

        private Builder() {
            this.types = Sets.newLinkedHashSet();
        }

        public Builder addType(MatcherType<?> type) {
            types.add(type);
            return this;
        }

        public MatcherType<Object> build() {
            return new MultiType(types);
        }
    }
}
