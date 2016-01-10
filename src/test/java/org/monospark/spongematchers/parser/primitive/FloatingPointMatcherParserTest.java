package org.monospark.spongematchers.parser.primitive;

import static org.hamcrest.CoreMatchers.not;
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
        Optional<SpongeMatcher<Double>> matcher = SpongeMatcherParser.FLOATING_POINT.parseMatcher("-1.2f-3.1f");
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(-1.1D));
        assertThat(matcher.get(), matches(3D));
        assertThat(matcher.get(), not(matches(-2D)));
        assertThat(matcher.get(), not(matches(4D)));
    }
    
    @Test
    public void parseMatcher_Amount_ReturnsCorrectMatcher() {
        Optional<SpongeMatcher<Double>> matcher = SpongeMatcherParser.FLOATING_POINT.parseMatcher("(-1f,3.1f,5.2f)");
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(-1D));
        assertThat(matcher.get(), matches(3.1D));
        assertThat(matcher.get(), matches(5.2D));
        assertThat(matcher.get(), not(matches(2D)));
        assertThat(matcher.get(), not(matches(4D)));
    }
    
    @Test
    public void parseMatcher_Value_ReturnsCorrectMatcher() {
        Optional<SpongeMatcher<Double>> matcher = SpongeMatcherParser.FLOATING_POINT.parseMatcher(".1f");
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(.1D));
        assertThat(matcher.get(), not(matches(2D)));
    }
    
    @Test
    public void parseMatcher_GreaterThan_ReturnsCorrectMatcher() {
        Optional<SpongeMatcher<Double>> matcher = SpongeMatcherParser.FLOATING_POINT.parseMatcher(">1f");
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(2D));
        assertThat(matcher.get(), not(matches(1D)));
    }
    
    @Test
    public void parseMatcher_GreaterThanOrEqual_ReturnsCorrectMatcher() {
        Optional<SpongeMatcher<Double>> matcher = SpongeMatcherParser.FLOATING_POINT.parseMatcher(">=1f");
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(2D));
        assertThat(matcher.get(), matches(1D));
        assertThat(matcher.get(), not(matches(0.9D)));
    }
    
    @Test
    public void parseMatcher_LessThan_ReturnsCorrectMatcher() {
        Optional<SpongeMatcher<Double>> matcher = SpongeMatcherParser.FLOATING_POINT.parseMatcher("<-1f");
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(-2D));
        assertThat(matcher.get(), not(matches(0.2D)));
    }
    
    @Test
    public void parseMatcher_LessThanOrEqual_ReturnsCorrectMatcher() {
        Optional<SpongeMatcher<Double>> matcher = SpongeMatcherParser.FLOATING_POINT.parseMatcher("<=1f");
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(0D));
        assertThat(matcher.get(), matches(1D));
        assertThat(matcher.get(), not(matches(2D)));
    }
}
