package org.monospark.spongematchers.parser.primitive;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import java.util.Optional;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.SpongeMatcherParser;
import org.monospark.spongematchers.testutil.ExceptionChecker;

public class IntegerMatcherParserTest {

    @Test
    public void parseMatcher_Range_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
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
    public void parseMatcher_RangeWithSpaces_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
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
    public void parseMatcher_InvalidRange_ThrowsException() {
        String input = "2-1";
        
        ExceptionChecker.check(SpongeMatcherParseException.class,
                () -> SpongeMatcherParser.INTEGER.parseMatcher(input));
    }
    
    @Test
    public void parseMatcher_Amount_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
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
    public void parseMatcher_AmountWithDuplicaes_ThrowsException() {
        String input = "(1,2,3,3)";
        
        ExceptionChecker.check(SpongeMatcherParseException.class,
                () -> SpongeMatcherParser.INTEGER.parseMatcher(input));
    }
    
    @Test
    public void parseMatcher_Value_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = "1";
        
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(1L));
        assertThat(matcher.get(), not(matches(2L)));
    }
    
    @Test
    public void parseMatcher_GreaterThan_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = ">1";
        
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(2L));
        assertThat(matcher.get(), not(matches(1L)));
    }
    
    @Test
    public void parseMatcher_GreaterThanWithSpaces_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = ">    -1";
        
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(0L));
        assertThat(matcher.get(), not(matches(-2L)));
    }
    
    @Test
    public void parseMatcher_GreaterThanOrEqual_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = ">=1";
        
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(2L));
        assertThat(matcher.get(), matches(1L));
        assertThat(matcher.get(), not(matches(0L)));
    }
    
    @Test
    public void parseMatcher_GreaterThanOrEqualWithSpaces_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = ">=   -1";
        
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(0L));
        assertThat(matcher.get(), matches(-1L));
        assertThat(matcher.get(), not(matches(-2L)));
    }
    
    @Test
    public void parseMatcher_LessThan_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = "<1";
        
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(0L));
        assertThat(matcher.get(), not(matches(2L)));
    }
    
    @Test
    public void parseMatcher_LessThanWithSpaces_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = "<    -3";
        
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(-4L));
        assertThat(matcher.get(), not(matches(-2L)));
    }
    
    @Test
    public void parseMatcher_LessThanOrEqual_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = "<=1";
        
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(0L));
        assertThat(matcher.get(), matches(1L));
        assertThat(matcher.get(), not(matches(2L)));
    }
    
    @Test
    public void parseMatcher_LessThanOrEqualWithSpaces_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = "<=   -2";
        
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(-2L));
        assertThat(matcher.get(), matches(-3L));
        assertThat(matcher.get(), not(matches(-1L)));
    }
}
