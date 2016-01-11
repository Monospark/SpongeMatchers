package org.monospark.spongematchers.parser.primitive;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import java.util.Optional;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParser;

public class FloatingPointMatcherParserTest {

    @Test
    public void parseMatcher_Range_ReturnsCorrectMatcher() {
        String input = "-1f-3f";
        
        Optional<SpongeMatcher<Double>> matcher = SpongeMatcherParser.FLOATING_POINT.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(-1D));
        assertThat(matcher.get(), matches(1D));
        assertThat(matcher.get(), matches(3D));
        assertThat(matcher.get(), not(matches(-2D)));
        assertThat(matcher.get(), not(matches(4D)));
    }
    
    @Test
    public void parseMatcher_RangeWithSpaces_ReturnsCorrectMatcher() {
        String input = " -5f --3f ";
        
        Optional<SpongeMatcher<Double>> matcher = SpongeMatcherParser.FLOATING_POINT.parseMatcher(input);

        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(-5D));
        assertThat(matcher.get(), matches(-4D));
        assertThat(matcher.get(), matches(-3D));
        assertThat(matcher.get(), not(matches(-2D)));
        assertThat(matcher.get(), not(matches(4D)));
    }
    
    @Test
    public void parseMatcher_InvalidRange_ReturnsEmptyOptional() {
        String input = "2f-1f";
        
        Optional<SpongeMatcher<Double>> matcher = SpongeMatcherParser.FLOATING_POINT.parseMatcher(input);
        
        assertFalse(matcher.isPresent());
    }
    
    @Test
    public void parseMatcher_Amount_ReturnsCorrectMatcher() {
        String input = "(-1.1f,3.1f,5f)";
        
        Optional<SpongeMatcher<Double>> matcher = SpongeMatcherParser.FLOATING_POINT.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(-1.1D));
        assertThat(matcher.get(), matches(3.1D));
        assertThat(matcher.get(), matches(5D));
        assertThat(matcher.get(), not(matches(2D)));
        assertThat(matcher.get(), not(matches(4D)));
    }
    
    @Test
    public void parseMatcher_AmountWithDuplicaes_ReturnsEmptyOptional() {
        String input = "(1f,2f,3f,3f)";
        
        Optional<SpongeMatcher<Double>> matcher = SpongeMatcherParser.FLOATING_POINT.parseMatcher(input);
        
        assertFalse(matcher.isPresent());
    }
    
    @Test
    public void parseMatcher_Value_ReturnsCorrectMatcher() {
        String input = "-1.2f";
        
        Optional<SpongeMatcher<Double>> matcher = SpongeMatcherParser.FLOATING_POINT.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(-1.2D));
        assertThat(matcher.get(), not(matches(2D)));
    }
    
    @Test
    public void parseMatcher_GreaterThan_ReturnsCorrectMatcher() {
        String input = ">1.1f";
        
        Optional<SpongeMatcher<Double>> matcher = SpongeMatcherParser.FLOATING_POINT.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(2D));
        assertThat(matcher.get(), not(matches(1D)));
    }
    
    @Test
    public void parseMatcher_GreaterThanWithSpaces_ReturnsCorrectMatcher() {
        String input = ">    .1f";
        
        Optional<SpongeMatcher<Double>> matcher = SpongeMatcherParser.FLOATING_POINT.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(0.2D));
        assertThat(matcher.get(), not(matches(0D)));
    }
    
    @Test
    public void parseMatcher_GreaterThanOrEqual_ReturnsCorrectMatcher() {
        String input = ">=1.2f";
        
        Optional<SpongeMatcher<Double>> matcher = SpongeMatcherParser.FLOATING_POINT.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(2D));
        assertThat(matcher.get(), matches(1.2D));
        assertThat(matcher.get(), not(matches(0D)));
    }
    
    @Test
    public void parseMatcher_GreaterThanOrEqualWithSpaces_ReturnsCorrectMatcher() {
        String input = ">=   -3.1f";
        
        Optional<SpongeMatcher<Double>> matcher = SpongeMatcherParser.FLOATING_POINT.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(-2.5D));
        assertThat(matcher.get(), matches(-3.1D));
        assertThat(matcher.get(), not(matches(-4D)));
    }
    
    @Test
    public void parseMatcher_LessThan_ReturnsCorrectMatcher() {
        String input = "<1f";
        
        Optional<SpongeMatcher<Double>> matcher = SpongeMatcherParser.FLOATING_POINT.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(0D));
        assertThat(matcher.get(), not(matches(2D)));
    }
    
    @Test
    public void parseMatcher_LessThanWithSpaces_ReturnsCorrectMatcher() {
        String input = "<    2.1f";
        
        Optional<SpongeMatcher<Double>> matcher = SpongeMatcherParser.FLOATING_POINT.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(2D));
        assertThat(matcher.get(), not(matches(2.2D)));
    }
    
    @Test
    public void parseMatcher_LessThanOrEqual_ReturnsCorrectMatcher() {
        String input = "<=.992f";
        
        Optional<SpongeMatcher<Double>> matcher = SpongeMatcherParser.FLOATING_POINT.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(0D));
        assertThat(matcher.get(), matches(0.992D));
        assertThat(matcher.get(), not(matches(2D)));
    }
    
    @Test
    public void parseMatcher_LessThanOrEqualWithSpaces_ReturnsCorrectMatcher() {
        String input = "<=   -1.123f";
        
        Optional<SpongeMatcher<Double>> matcher = SpongeMatcherParser.FLOATING_POINT.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(-2D));
        assertThat(matcher.get(), matches(-1.123D));
        assertThat(matcher.get(), not(matches(-1D)));
    }
}
