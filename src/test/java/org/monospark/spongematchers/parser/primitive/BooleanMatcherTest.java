package org.monospark.spongematchers.parser.primitive;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import java.util.Optional;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.SpongeMatcherParser;

public class BooleanMatcherTest {

    @Test
    public void parseMatcher_True_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = "true";
        
        Optional<SpongeMatcher<Boolean>> matcher = SpongeMatcherParser.BOOLEAN.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(true));
        assertThat(matcher.get(), not(matches(false)));
    }
    
    @Test
    public void parseMatcher_False_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = "false";
        
        Optional<SpongeMatcher<Boolean>> matcher = SpongeMatcherParser.BOOLEAN.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(false));
        assertThat(matcher.get(), not(matches(true)));
    }
}
