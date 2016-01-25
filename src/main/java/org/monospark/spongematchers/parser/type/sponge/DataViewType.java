package org.monospark.spongematchers.parser.type.sponge;

import java.util.Map.Entry;
import java.util.Set;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.complex.MapMatcher;
import org.monospark.spongematchers.matcher.sponge.DataViewMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.MapElement;
import org.monospark.spongematchers.parser.element.StringElement;
import org.monospark.spongematchers.parser.type.MatcherType;
import org.spongepowered.api.data.DataView;

import com.google.common.collect.ImmutableSet;

public final class DataViewType extends MatcherType<DataView> {

    private static final Set<MatcherType<?>> TYPES = ImmutableSet.of(MatcherType.DATA_VIEW, MatcherType.BOOLEAN,
            MatcherType.INTEGER, MatcherType.FLOATING_POINT, MatcherType.STRING);
    
    public DataViewType() {
        super("data view", DataView.class);
    }

    @Override
    public boolean canParse(StringElement element, boolean deep) {
        if (!(element instanceof MapElement)) {
            return false;
        }
        
        if (!deep) {
            return true;
        }
        
        MapElement mapElement = (MapElement) element;
        out: for (Entry<String, StringElement> entry : mapElement.getElements().entrySet()) {
            for (MatcherType<?> type : TYPES) {
                if (MatcherType.optional(type).canParse(entry.getValue(), true)) {
                    continue out;
                }
                if (MatcherType.optional(MatcherType.list(type)).canParse(element, true)) {
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
            DataMatcher parsedMatcher = parseDataMatcher(entry.getValue());
            builder.addMatcher(entry.getKey(), parsedMatcher.getObjectClass(), parsedMatcher.getMatcher());
        }
        return DataViewMatcher.create(builder.build());
    }
    
    private DataMatcher parseDataMatcher(StringElement element) throws SpongeMatcherParseException {
        for (MatcherType<?> type : TYPES) {
            if (MatcherType.optional(type).canParse(element, true)) {
                return new DataMatcher(MatcherType.optional(type).parseMatcher(element), type.getObjectClass());
            }
            
            if (MatcherType.optional(MatcherType.list(type)).canParse(element, true)) {
                return new DataMatcher(MatcherType.optional(MatcherType.list(type)).parseMatcher(element),
                        type.getObjectClass());
            }
        }
        
        throw new SpongeMatcherParseException("Invalid data matcher: " + element.getString());
    }
    
    private static final class DataMatcher {
        
        private SpongeMatcher<?> matcher;
        
        private Class<?> objectClass;

        private DataMatcher(SpongeMatcher<?> matcher, Class<?> objectClass) {
            this.matcher = matcher;
            this.objectClass = objectClass;
        }
 
        public SpongeMatcher<?> getMatcher() {
            return matcher;
        }

        public Class<?> getObjectClass() {
            return objectClass;
        }
    }
}
