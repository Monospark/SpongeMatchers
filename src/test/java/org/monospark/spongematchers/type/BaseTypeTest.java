package org.monospark.spongematchers.type;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.monospark.spongematchers.parser.element.StringElementParser;
import org.monospark.spongematchers.type.MatcherType;

public class BaseTypeTest {

    @Test
    public void canParse_NonBaseElement_ReturnsFalse() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("empty");
        
        boolean canParse = MatcherType.BOOLEAN.canParse(element, false);
        
        assertThat(canParse, is(false));
    }
    
    @Test
    public void canParse_DifferentBaseType_ReturnsFalse() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("1");
        
        boolean canParse = MatcherType.BOOLEAN.canParse(element, false);
        
        assertThat(canParse, is(false));
    }

    @Test
    public void parseMatcher_ValidBaseElement_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("false");

        SpongeMatcher<Boolean> matcher = MatcherType.BOOLEAN.parseMatcher(element);

        assertThat(matcher, matches(false));
        assertThat(matcher, not(matches(true)));
    }
}
