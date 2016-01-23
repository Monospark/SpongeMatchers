package org.monospark.spongematchers.parser.type;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.monospark.spongematchers.parser.element.StringElementParser;
import org.monospark.spongematchers.testutil.ExceptionChecker;

public class BaseTypeTest {

    @Test
    public void parseMatcher_NonBaseElement_ThrowsException() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("empty");
        ExceptionChecker.check(SpongeMatcherParseException.class, () -> MatcherType.BOOLEAN.parseMatcher(element));
    }
    
    @Test
    public void parseMatcher_DifferentBaseType_ThrowsException() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("1");
        ExceptionChecker.check(SpongeMatcherParseException.class, () -> MatcherType.BOOLEAN.parseMatcher(element));
    }
    
    @Test
    public void parseMatcher_ValidBaseElement_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("false");

        SpongeMatcher<Boolean> matcher = MatcherType.BOOLEAN.parseMatcher(element);

        assertThat(matcher, matches(false));
        assertThat(matcher, not(matches(true)));
    }
}
