package org.monospark.spongematchers.parser;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.base.BaseMatcherParser;
import org.monospark.spongematchers.testutil.ExceptionChecker;

public class SpongeMatcherParserTest {

    @Test
    public void parseMatcher_NullArgument_ThrowsNullPointerException() {
        ExceptionChecker.check(NullPointerException.class, () -> BaseMatcherParser.INTEGER.parseMatcher(null));
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
        SpongeMatcher<Long> matcher = BaseMatcherParser.INTEGER.parseMatcher(input);
        
        assertThat(matcher, matches(3L));
        assertThat(matcher, matches(5L));
        assertThat(matcher, matches(6L));
        assertThat(matcher, matches(8L));
        assertThat(matcher, not(matches(-2L)));
    }
}
