package org.monospark.spongematchers.type.sponge;

import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.MapMatcher;
import org.monospark.spongematchers.matcher.sponge.DataViewMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.MapElement;
import org.monospark.spongematchers.parser.element.StringElement;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.data.DataView;

import com.google.common.collect.ImmutableSet;

public final class DataViewType extends MatcherType<DataView> {

    private Set<MatcherType<?>> types;
    
    public DataViewType() {
        super("data view");
    }

    @Override
    public boolean canMatch(Object o) {
        // TODO Auto-generated method stub
        return false;
    }
    
    private Set<MatcherType<?>> getAvailableTypes() {
        if (types == null) {
            types = ImmutableSet.of(MatcherType.BOOLEAN, MatcherType.INTEGER,
                    MatcherType.FLOATING_POINT, MatcherType.STRING);
        }
        return types;
    }

    @Override
    protected boolean canParse(StringElement element, boolean deep) {
        if (!(element instanceof MapElement)) {
            return false;
        }
        
        if (!deep) {
            return true;
        }
        
        MapElement mapElement = (MapElement) element;
        out: for (Entry<String, StringElement> entry : mapElement.getElements().entrySet()) {
            for (MatcherType<?> type : getAvailableTypes()) {
                if (MatcherType.optional(type).canParseMatcher(entry.getValue(), true)) {
                    continue out;
                }
                if (MatcherType.optional(MatcherType.list(type)).canParseMatcher(element, true)) {
                    continue out;
                }
            }
            return false;
        }
        return true;
    }

    @Override
    protected SpongeMatcher<DataView> parse(StringElement element) throws SpongeMatcherParseException {
        MapElement mapElement = (MapElement) element;
        MapMatcher.Builder builder = MapMatcher.builder();
        for (Entry<String, StringElement> entry : mapElement.getElements().entrySet()) {
            DataMatcher<?> parsedMatcher = parseDataMatcher(entry.getValue());
            addDataMatcher(entry.getKey(), parsedMatcher, builder);
        }
        return DataViewMatcher.create(builder.build());
    }
    
    private <T> void addDataMatcher(String name, DataMatcher<T> matcher, MapMatcher.Builder builder)
            throws SpongeMatcherParseException {
        builder.addOptionalMatcher(name, matcher.getType(), matcher.getMatcher());
    }
    
    private DataMatcher<?> parseDataMatcher(StringElement element) throws SpongeMatcherParseException {
        for (MatcherType<?> type : getAvailableTypes()) {
            DataMatcher<?> matcher = parseDataMatcherForType(type, element);
            if (matcher != null) {
                return matcher;
            }
        }
        
        throw new SpongeMatcherParseException("Invalid data matcher: " + element.getString());
    }
    
    private <T> DataMatcher<?> parseDataMatcherForType(MatcherType<T> type, StringElement element)
            throws SpongeMatcherParseException {
        if (MatcherType.optional(type).canParseMatcher(element, true)) {
            return new DataMatcher<T>(MatcherType.optional(type).parseMatcher(element), type);
        }
        
        if (MatcherType.optional(MatcherType.list(type)).canParseMatcher(element, true)) {
            return new DataMatcher<List<T>>(MatcherType.optional(MatcherType.list(type)).parseMatcher(element),
                    MatcherType.list(type));
        }
        
        return null;
    }
    
    private static final class DataMatcher<T> {
        
        private SpongeMatcher<Optional<T>> matcher;
        
        private MatcherType<T> type;

        private DataMatcher(SpongeMatcher<Optional<T>> matcher, MatcherType<T> type) {
            this.matcher = matcher;
            this.type = type;
        }
 
        public SpongeMatcher<Optional<T>> getMatcher() {
            return matcher;
        }

        public MatcherType<T> getType() {
            return type;
        }
    }
}
