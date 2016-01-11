package org.monospark.spongematchers.parser.primitive;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import java.util.Optional;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParser;

public class IntegerMatcherParserTest {

    @Test
    public void parseMatcher_Range_ReturnsCorrectMatcher() {
        String input = "-1-3";
        
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(-1L));
        assertThat(matcher.get(), matches(1L));
        assertThat(matcher.get(), matches(3L));
        assertThat(matcher.get(), not(matches(-2L)));
        assertThat(matcher.get(), not(matches(4L)));
    }
    
    @Test
    public void parseMatcher_RangeWithSpaces_ReturnsCorrectMatcher() {
        String input = " -5 --3 ";
        
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher(input);

        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(-5L));
        assertThat(matcher.get(), matches(-4L));
        assertThat(matcher.get(), matches(-3L));
        assertThat(matcher.get(), not(matches(-2L)));
        assertThat(matcher.get(), not(matches(4L)));
    }
    
    @Test
    public void parseMatcher_InvalidRange_ReturnsEmptyOptional() {
        String input = "2-1";
        
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher(input);
        
        assertFalse(matcher.isPresent());
    }
    
    @Test
    public void parseMatcher_Amount_ReturnsCorrectMatcher() {
        String input = "(-1,3,5)";
        
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(-1L));
        assertThat(matcher.get(), matches(3L));
        assertThat(matcher.get(), matches(5L));
        assertThat(matcher.get(), not(matches(2L)));
        assertThat(matcher.get(), not(matches(4L)));
    }
    
    @Test
    public void parseMatcher_AmountWithDuplicaes_ReturnsEmptyOptional() {
        String input = "(1,2,3,3)";
        
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher(input);
        
        assertFalse(matcher.isPresent());
    }
    
    @Test
    public void parseMatcher_Value_ReturnsCorrectMatcher() {
        String input = "1";
        
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(1L));
        assertThat(matcher.get(), not(matches(2L)));
    }
    
    @Test
    public void parseMatcher_GreaterThan_ReturnsCorrectMatcher() {
        String input = ">1";
        
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(2L));
        assertThat(matcher.get(), not(matches(1L)));
    }
    
    @Test
    public void parseMatcher_GreaterThanWithSpaces_ReturnsCorrectMatcher() {
        String input = ">    -1";
        
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(0L));
        assertThat(matcher.get(), not(matches(-2L)));
    }
    
    @Test
    public void parseMatcher_GreaterThanOrEqual_ReturnsCorrectMatcher() {
        String input = ">=1";
        
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(2L));
        assertThat(matcher.get(), matches(1L));
        assertThat(matcher.get(), not(matches(0L)));
    }
    
    @Test
    public void parseMatcher_GreaterThanOrEqualWithSpaces_ReturnsCorrectMatcher() {
        String input = ">=   -1";
        
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(0L));
        assertThat(matcher.get(), matches(-1L));
        assertThat(matcher.get(), not(matches(-2L)));
    }
    
    @Test
    public void parseMatcher_LessThan_ReturnsCorrectMatcher() {
        String input = "<1";
        
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(0L));
        assertThat(matcher.get(), not(matches(2L)));
    }
    
    @Test
    public void parseMatcher_LessThanWithSpaces_ReturnsCorrectMatcher() {
        String input = "<    -3";
        
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(-4L));
        assertThat(matcher.get(), not(matches(-2L)));
    }
    
    @Test
    public void parseMatcher_LessThanOrEqual_ReturnsCorrectMatcher() {
        String input = "<=1";
        
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(0L));
        assertThat(matcher.get(), matches(1L));
        assertThat(matcher.get(), not(matches(2L)));
    }
    
    @Test
    public void parseMatcher_LessThanOrEqualWithSpaces_ReturnsCorrectMatcher() {
        String input = "<=   -2";
        
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(-2L));
        assertThat(matcher.get(), matches(-3L));
        assertThat(matcher.get(), not(matches(-1L)));
    }
}
