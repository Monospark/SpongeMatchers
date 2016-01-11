package org.monospark.spongematchers.parser.primitive;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import java.util.Optional;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParser;

public class BooleanMatcherTest {

    @Test
    public void parseMatcher_True_ReturnsCorrectMatcher() {
        String input = "true";
        
        verifyMatcher(input, false, true);
    }
    
    @Test
    public void parseMatcher_False_ReturnsCorrectMatcher() {
        String input = "false";
        
        verifyMatcher(input, true, false);
    }
    
    @Test
    public void parseMatcher_TrueAndFalse_ReturnsCorrectMatcher() {
        String input1 = "true|false";
        String input2 = " true  | \t false ";
        
        verifyMatcher(input1, true, true);
        verifyMatcher(input2, true, true);
    }
    
    @Test
    public void parseMatcher_FalseAndTrue_ReturnsCorrectMatcher() {
        String input1 = "false|true";
        String input2 = " false  | \t true ";
        
        verifyMatcher(input1, true, true);
        verifyMatcher(input2, true, true);
    }
    
    private void verifyMatcher(String input, boolean matchFalse, boolean matchTrue) {
        Optional<SpongeMatcher<Boolean>> matcher = SpongeMatcherParser.BOOLEAN.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matchFalse ? matches(false) : not(matches(false)));
        assertThat(matcher.get(), matchTrue ? matches(true) : not(matches(true)));
    }
}
