package org.monospark.spongematchers.parser.base;

import static org.junit.Assert.assertThat;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.testutil.ExceptionChecker;

public class IntegerMatcherParserTest {

    @Test
    public void parseMatcher_Range_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = "-1-3";
        
        SpongeMatcher<Long> matcher = BaseMatcherParser.INTEGER.parseMatcher(input);
        
        assertThat(matcher, matches(-1L));
        assertThat(matcher, matches(1L));
        assertThat(matcher, matches(3L));
    }
    
    @Test
    public void parseMatcher_RangeWithSpaces_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = " -5 --3 ";
        
        SpongeMatcher<Long> matcher = BaseMatcherParser.INTEGER.parseMatcher(input);

        assertThat(matcher, matches(-5L));
        assertThat(matcher, matches(-4L));
        assertThat(matcher, matches(-3L));
    }
    
    @Test
    public void parseMatcher_InvalidRange_ThrowsException() {
        String input = "2-1";
        
        ExceptionChecker.check(SpongeMatcherParseException.class,
                () -> BaseMatcherParser.INTEGER.parseMatcher(input));
    }

    @Test
    public void parseMatcher_Value_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = "1";
        
        SpongeMatcher<Long> matcher = BaseMatcherParser.INTEGER.parseMatcher(input);
        
        assertThat(matcher, matches(1L));
    }
    
    @Test
    public void parseMatcher_GreaterThan_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = ">1";
        
        SpongeMatcher<Long> matcher = BaseMatcherParser.INTEGER.parseMatcher(input);
        
        assertThat(matcher, matches(2L));
    }
    
    @Test
    public void parseMatcher_GreaterThanWithSpaces_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = ">    -1";
        
        SpongeMatcher<Long> matcher = BaseMatcherParser.INTEGER.parseMatcher(input);
        
        assertThat(matcher, matches(0L));
    }
    
    @Test
    public void parseMatcher_GreaterThanOrEqual_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = ">=1";
        
        SpongeMatcher<Long> matcher = BaseMatcherParser.INTEGER.parseMatcher(input);
        
        assertThat(matcher, matches(2L));
        assertThat(matcher, matches(1L));
    }
    
    @Test
    public void parseMatcher_GreaterThanOrEqualWithSpaces_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = ">=   -1";
        
        SpongeMatcher<Long> matcher = BaseMatcherParser.INTEGER.parseMatcher(input);
        
        assertThat(matcher, matches(0L));
        assertThat(matcher, matches(-1L));
    }
    
    @Test
    public void parseMatcher_LessThan_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = "<1";
        
        SpongeMatcher<Long> matcher = BaseMatcherParser.INTEGER.parseMatcher(input);
        
        assertThat(matcher, matches(0L));
    }
    
    @Test
    public void parseMatcher_LessThanWithSpaces_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = "<    -3";
        
        SpongeMatcher<Long> matcher = BaseMatcherParser.INTEGER.parseMatcher(input);
        
        assertThat(matcher, matches(-4L));
    }
    
    @Test
    public void parseMatcher_LessThanOrEqual_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = "<=1";
        
        SpongeMatcher<Long> matcher = BaseMatcherParser.INTEGER.parseMatcher(input);
        
        assertThat(matcher, matches(0L));
        assertThat(matcher, matches(1L));
    }
    
    @Test
    public void parseMatcher_LessThanOrEqualWithSpaces_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = "<=   -2";
        
        SpongeMatcher<Long> matcher = BaseMatcherParser.INTEGER.parseMatcher(input);
        
        assertThat(matcher, matches(-2L));
        assertThat(matcher, matches(-3L));
    }
}
