package org.monospark.spongematchers.parser.data;

import java.util.Optional;
import java.util.regex.Matcher;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.data.DataValue;
import org.monospark.spongematchers.matcher.data.DataValue.Type;
import org.monospark.spongematchers.parser.SpongeMatcherParser;

public final class DataValueParser<T> extends DataEntryParser<DataValue<?>> {

    private Type<T> type;
    
    private SpongeMatcherParser<T> parser;

    DataValueParser(Type<T> type, SpongeMatcherParser<T> parser) {
        this.type = type;
        this.parser = parser;
    }

    Optional<DataValue<?>> parse(String string) {
        Matcher matcher = parser.getAcceptanceRegex().matcher(string);
        if (!matcher.matches()) {
            return Optional.empty();
        }
        
        Optional<SpongeMatcher<T>> spongeMatcher = parser.parseMatcher(string);
        if (!spongeMatcher.isPresent()) {
            return Optional.empty();
        }
        
        return Optional.of(new DataValue<T>(type, parser.parseMatcher(string).get()));
    }
    
    boolean canParse(String string) {
        return parser.getAcceptanceRegex().matcher(string).matches();
    }
}
