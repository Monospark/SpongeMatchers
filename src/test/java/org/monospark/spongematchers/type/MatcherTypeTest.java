package org.monospark.spongematchers.type;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElementParser;
import org.monospark.spongematchers.type.MatcherType;

public class MatcherTypeTest {

    @Test
    public void parseMatcher_MatcherWithConjunctions_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        String input = "(('test1'|'test2')&!('test2'))";
        
        SpongeMatcher<String> matcher = MatcherType.STRING.parseMatcher(StringElementParser.parseStringElement(input));
        
        assertThat(matcher, matches("test1"));
        assertThat(matcher, not(matches("test2")));
    }
}
