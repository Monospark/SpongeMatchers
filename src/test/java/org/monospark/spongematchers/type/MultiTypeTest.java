package org.monospark.spongematchers.type;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.monospark.spongematchers.parser.element.StringElementParser;
import org.monospark.spongematchers.testutil.ExceptionChecker;

public class MultiTypeTest {

    @Test
    public void canMatch_ObjectThatCannotBeMatchedByAnyType_ReturnsFalse() throws SpongeMatcherParseException {
        Object o = "test";

        boolean canMatch = MatcherType.multi()
                .addType(MatcherType.BOOLEAN)
                .addType(MatcherType.INTEGER)
                .build()
                .canMatch(o);

        assertThat(canMatch, is(false));
    }

    @Test
    public void canMatch_ObjectThatCanBeMatchedByAType_ReturnsTrue() throws SpongeMatcherParseException {
        Object o = 5L;

        boolean canMatch = MatcherType.multi()
                .addType(MatcherType.BOOLEAN)
                .addType(MatcherType.INTEGER)
                .build()
                .canMatch(o);

        assertThat(canMatch, is(true));
    }



    @Test
    public void canParse_ElementThatCannotBeParsedByAnyType_ReturnsFalse() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("absent");

        boolean canParse = MatcherType.multi()
                .addType(MatcherType.BOOLEAN)
                .addType(MatcherType.INTEGER)
                .build()
                .canParse(element);

        assertThat(canParse, is(false));
    }

    @Test
    public void canParse_ElementThatCanBeParsedByAType_ReturnsTrue() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("'test'");

        boolean canParse = MatcherType.multi()
                .addType(MatcherType.BOOLEAN)
                .addType(MatcherType.INTEGER)
                .addType(MatcherType.STRING)
                .build()
                .canParse(element);

        assertThat(canParse, is(true));
    }



    @Test
    public void parse_ElementThatCannotBeParsedByAnyType_ThrowsException() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("absent");

        ExceptionChecker.check(SpongeMatcherParseException.class,
                () -> MatcherType.multi()
                        .addType(MatcherType.BOOLEAN)
                        .addType(MatcherType.INTEGER)
                        .build()
                        .parse(element));
    }

    @Test
    public void parse_ElementThatCanBeParsedByAType_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("true");

        SpongeMatcher<Object> matcher = MatcherType.multi()
                .addType(MatcherType.BOOLEAN)
                .addType(MatcherType.INTEGER)
                .build()
                .parse(element);

        assertThat(matcher, matches(true));
    }
}
