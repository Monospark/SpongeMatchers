package org.monospark.spongematchers.type.base;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.monospark.spongematchers.parser.element.StringElementParser;
import org.monospark.spongematchers.testutil.ExceptionChecker;
import org.monospark.spongematchers.type.MatcherType;

public class BaseTypeTest {

    @Test
    public void canMatch_DifferentClasses_ReturnsFalse() throws SpongeMatcherParseException {
        Object o = 5;

        boolean canMatch = MatcherType.BOOLEAN.canMatch(o);

        assertThat(canMatch, is(false));
    }

    @Test
    public void canMatch_SameClasses_ReturnsTrue() throws SpongeMatcherParseException {
        Object o = true;

        boolean canMatch = MatcherType.BOOLEAN.canMatch(o);

        assertThat(canMatch, is(true));
    }



    @Test
    public void canParse_NonBaseElement_ReturnsFalse() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("absent");

        assertThat(MatcherType.BOOLEAN.canParseMatcher(element), is(false));
    }

    @Test
    public void canParse_DifferentBaseType_ReturnsFalse() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("1");

        assertThat(MatcherType.BOOLEAN.canParseMatcher(element), is(false));
    }

    @Test
    public void canParse_ValidBaseElement_ReturnsTrue() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("false");

        assertThat(MatcherType.BOOLEAN.canParseMatcher(element), is(true));
    }



    @Test
    public void parse_NonBaseElement_ThrowsException() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("absent");

        ExceptionChecker.check(SpongeMatcherParseException.class, () -> MatcherType.BOOLEAN.parseMatcher(element));
    }

    @Test
    public void parse_DifferentBaseType_ThrowsException() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("1");

        ExceptionChecker.check(SpongeMatcherParseException.class, () -> MatcherType.BOOLEAN.parseMatcher(element));
    }

    @Test
    public void parse_ValidBaseElement_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("false");

        SpongeMatcher<Boolean> matcher = MatcherType.BOOLEAN.parseMatcher(element);

        assertThat(matcher, matches(false));
    }
}
