package org.monospark.spongematchers;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class SpongeMatcherParser<M extends SpongeMatcher<?>> {

    private Pattern acceptanceRegex;
    
    protected SpongeMatcherParser() {
        acceptanceRegex = createAcceptanceRegex();
    }

    protected abstract Pattern createAcceptanceRegex();
    
    public final M parseMatcher(String string) throws SpongeMatcherFormatException {
        Objects.requireNonNull(string);
        
        Matcher matcher = acceptanceRegex.matcher(string);
        if (!matcher.matches()) {
            throw new SpongeMatcherFormatException("Invalid input: " + string);
        }
        
        return parse(matcher);
    }
    
    public final M parseMatcherUnsafe(String string) {
        Objects.requireNonNull(string);
        
        Matcher matcher = acceptanceRegex.matcher(string);
        return parse(matcher);
    }
    
    protected abstract M parse(Matcher matcher);

    public Pattern getAcceptanceRegex() {
        return acceptanceRegex;
    }
}
