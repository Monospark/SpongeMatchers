package org.monospark.spongematchers.type;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import java.util.Optional;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.monospark.spongematchers.parser.element.StringElementParser;
import org.monospark.spongematchers.testutil.ExceptionChecker;

public class OptionalTypeTest {

    @Test
    public void canMatch_NonOptional_ReturnsFalse() throws SpongeMatcherParseException {
        Object o = 5;

        boolean canMatch = MatcherType.optional(MatcherType.BOOLEAN).canMatch(o);

        assertThat(canMatch, is(false));
    }

    @Test
    public void canMatch_OptionalWithDifferentValueType_ReturnsFalse() throws SpongeMatcherParseException {
        Object o = Optional.of(1);

        boolean canMatch = MatcherType.optional(MatcherType.BOOLEAN).canMatch(o);

        assertThat(canMatch, is(false));
    }

    @Test
    public void canMatch_OptionalWithSameValueType_ReturnsTrue() throws SpongeMatcherParseException {
        Object o = Optional.of(true);

        boolean canMatch = MatcherType.optional(MatcherType.BOOLEAN).canMatch(o);

        assertThat(canMatch, is(true));
    }



    @Test
    public void checkElement_ElementOfDifferentType_ReturnsFalse() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("true");

        assertThat(MatcherType.optional(MatcherType.INTEGER).checkElement(element), is(false));
    }

    @Test
    public void checkElement_ElementOfSameType_ReturnsTrue() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("1");

        assertThat(MatcherType.optional(MatcherType.INTEGER).checkElement(element), is(true));
    }

    @Test
    public void checkElement_AbsentElement_ReturnsTrue() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("absent");

        assertThat(MatcherType.optional(MatcherType.INTEGER).checkElement(element), is(true));
    }



    @Test
    public void parse_ElementOfDifferentType_ThrowsException() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("true");

        ExceptionChecker.check(SpongeMatcherParseException.class,
                () -> MatcherType.optional(MatcherType.INTEGER).parseMatcher(element));
    }

    @Test
    public void parse_ElementOfSameType_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("true");

        SpongeMatcher<Optional<Boolean>> matcher = MatcherType.optional(MatcherType.BOOLEAN).parseMatcher(element);

        assertThat(matcher, matches(Optional.of(true)));
    }

    @Test
    public void parse_AbsentElement_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("absent");

        SpongeMatcher<Optional<Boolean>> matcher = MatcherType.optional(MatcherType.BOOLEAN).parseMatcher(element);

        assertThat(matcher, matches(Optional.empty()));
    }
}
