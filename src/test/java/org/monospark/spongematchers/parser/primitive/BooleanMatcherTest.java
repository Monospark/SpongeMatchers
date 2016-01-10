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
        Optional<SpongeMatcher<Boolean>> matcher = SpongeMatcherParser.BOOLEAN.parseMatcher("true");
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(true));
        assertThat(matcher.get(), not(matches(false)));
    }
    
    @Test
    public void parseMatcher_False_ReturnsCorrectMatcher() {
        Optional<SpongeMatcher<Boolean>> matcher = SpongeMatcherParser.BOOLEAN.parseMatcher("false");
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(false));
        assertThat(matcher.get(), not(matches(true)));
    }
    
    @Test
    public void parseMatcher_TrueAndFalse_ReturnsCorrectMatcher() {
        Optional<SpongeMatcher<Boolean>> matcher = SpongeMatcherParser.BOOLEAN.parseMatcher("true|false");
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(true));
        assertThat(matcher.get(), matches(false));
    }
    
    @Test
    public void parseMatcher_FalseAndTrue_ReturnsCorrectMatcher() {
        Optional<SpongeMatcher<Boolean>> matcher = SpongeMatcherParser.BOOLEAN.parseMatcher("false|true");
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(true));
        assertThat(matcher.get(), matches(false));
    }
}
