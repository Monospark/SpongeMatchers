package org.monospark.spongematchers.parser;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import java.util.Optional;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.testutil.ExceptionChecker;

public class SpongeMatcherParserTest {

    @Test
    public void parseMatcher_NullArgument_ThrowsNullPointerException() {
        ExceptionChecker.check(NullPointerException.class, () -> SpongeMatcherParser.INTEGER.parseMatcher(null));
    }
    
    @Test
    public void parseMatcher_MatcherAmount_ReturnsMatcherAmount() throws SpongeMatcherParseException {
        String input = "3|5-8|1";
        
        checkIntegerMatcher(input);
    }
    
    @Test
    public void parseMatcher_MatcherAmountWithSpaces_ReturnsMatcherAmount() throws SpongeMatcherParseException {
        String input1 = "3  |5-8| 1 ";
        String input2 = "   3 |5-8   |1";
        
        checkIntegerMatcher(input1);
        checkIntegerMatcher(input2);
    }
    
    private void checkIntegerMatcher(String input) throws SpongeMatcherParseException {
        Optional<SpongeMatcher<Long>> matcher = SpongeMatcherParser.INTEGER.parseMatcher(input);
        
        assertTrue(matcher.isPresent());
        assertThat(matcher.get(), matches(3L));
        assertThat(matcher.get(), matches(5L));
        assertThat(matcher.get(), matches(6L));
        assertThat(matcher.get(), matches(8L));
        assertThat(matcher.get(), not(matches(-2L)));
    }
}
