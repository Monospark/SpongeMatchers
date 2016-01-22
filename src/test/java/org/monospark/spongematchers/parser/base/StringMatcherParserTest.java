package org.monospark.spongematchers.parser.base;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.testutil.ExceptionChecker;

public class StringMatcherParserTest {

    @Test
    public void parseMatcher_ValidRegex_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = "'(?:test)+'";
        
        SpongeMatcher<String> matcher = BaseMatcherParser.STRING.parseMatcher(input);
        
        assertThat(matcher, matches("testtesttest"));
        assertThat(matcher, not(matches("testing")));
    }
    
    @Test
    public void parseMatcher_InvalidRegex_ThrowsException() throws SpongeMatcherParseException {
        String input = "'(test))'";
        
        ExceptionChecker.check(SpongeMatcherParseException.class, () -> BaseMatcherParser.STRING.parseMatcher(input));
    }
}
