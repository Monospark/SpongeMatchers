package org.monospark.spongematchers;

import java.util.Objects;
import java.util.regex.Pattern;

public abstract class MatcherParser<M extends Matcher<?>> {

    private Pattern acceptanceRegex;
    
    protected MatcherParser() {
        acceptanceRegex = createAcceptanceRegex();
    }

    protected abstract Pattern createAcceptanceRegex();
    
    public final M parseMatcher(String string) throws MatcherFormatException {
        Objects.requireNonNull(string);
        
        java.util.regex.Matcher matcher = acceptanceRegex.matcher(string);
        if (!matcher.matches()) {
            throw new MatcherFormatException("Invalid input: " + string);
        }
        
        return parse(matcher);
    }
    
    public final M parseMatcherUnsafe(String string) {
        Objects.requireNonNull(string);
        
        java.util.regex.Matcher matcher = acceptanceRegex.matcher(string);
        return parse(matcher);
    }
    
    protected abstract M parse(java.util.regex.Matcher matcher);

    public Pattern getAcceptanceRegex() {
        return acceptanceRegex;
    }
}
