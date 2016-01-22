package org.monospark.spongematchers.parser.base;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;

public class BooleanMatcherTest {

    @Test
    public void parseMatcher_True_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = "true";
        
        SpongeMatcher<Boolean> matcher = BaseMatcherParser.BOOLEAN.parseMatcher(input);
        
        assertThat(matcher, matches(true));
        assertThat(matcher, not(matches(false)));
    }
    
    @Test
    public void parseMatcher_False_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = "false";
        
        SpongeMatcher<Boolean> matcher = BaseMatcherParser.BOOLEAN.parseMatcher(input);
        
        assertThat(matcher, matches(false));
        assertThat(matcher, not(matches(true)));
    }
}
