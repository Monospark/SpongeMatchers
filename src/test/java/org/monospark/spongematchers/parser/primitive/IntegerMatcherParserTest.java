package org.monospark.spongematchers.parser.primitive;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import java.util.Optional;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParser;

public class IntegerMatcherParserTest {

    @Test
    public void parseMatcher_Range_ReturnsCorrectMatcher() {
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher("-1-3");
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(-1L));
        assertThat(matcher.get(), matches(1L));
        assertThat(matcher.get(), matches(3L));
        assertThat(matcher.get(), not(matches(-2L)));
        assertThat(matcher.get(), not(matches(4L)));
    }
    
    @Test
    public void parseMatcher_Amount_ReturnsCorrectMatcher() {
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher("(-1,3,5)");
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(-1L));
        assertThat(matcher.get(), matches(3L));
        assertThat(matcher.get(), matches(5L));
        assertThat(matcher.get(), not(matches(2L)));
        assertThat(matcher.get(), not(matches(4L)));
    }
    
    @Test
    public void parseMatcher_Value_ReturnsCorrectMatcher() {
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher("1");
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(1L));
        assertThat(matcher.get(), not(matches(2L)));
    }
    
    @Test
    public void parseMatcher_GreaterThan_ReturnsCorrectMatcher() {
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher(">1");
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(2L));
        assertThat(matcher.get(), not(matches(1L)));
    }
    
    @Test
    public void parseMatcher_GreaterThanOrEqual_ReturnsCorrectMatcher() {
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher(">=1");
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(2L));
        assertThat(matcher.get(), matches(1L));
        assertThat(matcher.get(), not(matches(0L)));
    }
    
    @Test
    public void parseMatcher_LessThan_ReturnsCorrectMatcher() {
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher("<-1");
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(-2L));
        assertThat(matcher.get(), not(matches(0L)));
    }
    
    @Test
    public void parseMatcher_LessThanOrEqual_ReturnsCorrectMatcher() {
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher("<=1");
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(0L));
        assertThat(matcher.get(), matches(1L));
        assertThat(matcher.get(), not(matches(2L)));
    }
}
