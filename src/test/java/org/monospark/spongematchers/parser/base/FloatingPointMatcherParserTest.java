package org.monospark.spongematchers.parser.base;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.testutil.ExceptionChecker;

public class FloatingPointMatcherParserTest {

    @Test
    public void parseMatcher_Range_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = "-1f-3f";
        
        SpongeMatcher<Double> matcher = BaseMatcherParser.FLOATING_POINT.parseMatcher(input);
        
        assertThat(matcher, matches(-1D));
        assertThat(matcher, matches(1D));
        assertThat(matcher, matches(3D));
        assertThat(matcher, not(matches(-2D)));
        assertThat(matcher, not(matches(4D)));
    }
    
    @Test
    public void parseMatcher_RangeWithSpaces_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = " -5f --3f ";
        
        SpongeMatcher<Double> matcher = BaseMatcherParser.FLOATING_POINT.parseMatcher(input);

        assertThat(matcher, matches(-5D));
        assertThat(matcher, matches(-4D));
        assertThat(matcher, matches(-3D));
        assertThat(matcher, not(matches(-2D)));
        assertThat(matcher, not(matches(4D)));
    }
    
    @Test
    public void parseMatcher_InvalidRange_ThrowsException() {
        String input = "2f-1f";
        
        ExceptionChecker.check(SpongeMatcherParseException.class,
                () -> BaseMatcherParser.FLOATING_POINT.parseMatcher(input));
    }

    @Test
    public void parseMatcher_Value_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = "-1.2f";
        
        SpongeMatcher<Double> matcher = BaseMatcherParser.FLOATING_POINT.parseMatcher(input);
        
        assertThat(matcher, matches(-1.2D));
        assertThat(matcher, not(matches(2D)));
    }
    
    @Test
    public void parseMatcher_GreaterThan_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = ">1.1f";
        
        SpongeMatcher<Double> matcher = BaseMatcherParser.FLOATING_POINT.parseMatcher(input);
        
        assertThat(matcher, matches(2D));
        assertThat(matcher, not(matches(1D)));
    }
    
    @Test
    public void parseMatcher_GreaterThanWithSpaces_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = ">    .1f";
        
        SpongeMatcher<Double> matcher = BaseMatcherParser.FLOATING_POINT.parseMatcher(input);
        
        assertThat(matcher, matches(0.2D));
        assertThat(matcher, not(matches(0D)));
    }
    
    @Test
    public void parseMatcher_GreaterThanOrEqual_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = ">=1.2f";
        
        SpongeMatcher<Double> matcher = BaseMatcherParser.FLOATING_POINT.parseMatcher(input);
        
        assertThat(matcher, matches(2D));
        assertThat(matcher, matches(1.2D));
        assertThat(matcher, not(matches(0D)));
    }
    
    @Test
    public void parseMatcher_GreaterThanOrEqualWithSpaces_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = ">=   -3.1f";
        
        SpongeMatcher<Double> matcher = BaseMatcherParser.FLOATING_POINT.parseMatcher(input);
        
        assertThat(matcher, matches(-2.5D));
        assertThat(matcher, matches(-3.1D));
        assertThat(matcher, not(matches(-4D)));
    }
    
    @Test
    public void parseMatcher_LessThan_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = "<1f";
        
        SpongeMatcher<Double> matcher = BaseMatcherParser.FLOATING_POINT.parseMatcher(input);
        
        assertThat(matcher, matches(0D));
        assertThat(matcher, not(matches(2D)));
    }
    
    @Test
    public void parseMatcher_LessThanWithSpaces_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = "<    2.1f";
        
        SpongeMatcher<Double> matcher = BaseMatcherParser.FLOATING_POINT.parseMatcher(input);
        
        assertThat(matcher, matches(2D));
        assertThat(matcher, not(matches(2.2D)));
    }
    
    @Test
    public void parseMatcher_LessThanOrEqual_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = "<=.992f";
        
        SpongeMatcher<Double> matcher = BaseMatcherParser.FLOATING_POINT.parseMatcher(input);
        
        assertThat(matcher, matches(0D));
        assertThat(matcher, matches(0.992D));
        assertThat(matcher, not(matches(2D)));
    }
    
    @Test
    public void parseMatcher_LessThanOrEqualWithSpaces_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = "<=   -1.123f";
        
        SpongeMatcher<Double> matcher = BaseMatcherParser.FLOATING_POINT.parseMatcher(input);
        
        assertThat(matcher, matches(-2D));
        assertThat(matcher, matches(-1.123D));
        assertThat(matcher, not(matches(-1D)));
    }
}
