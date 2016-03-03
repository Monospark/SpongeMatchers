package org.monospark.spongematchers.type.advanced;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import java.util.List;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.monospark.spongematchers.parser.element.StringElementParser;
import org.monospark.spongematchers.testutil.ExceptionChecker;
import org.monospark.spongematchers.type.MatcherType;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class ListTypeTest {

    @Test
    public void canMatch_NonList_ReturnsFalse() throws SpongeMatcherParseException {
        Object o = 5;

        boolean canMatch = MatcherType.list(MatcherType.BOOLEAN).canMatch(o);

        assertThat(canMatch, is(false));
    }

    @Test
    public void canMatch_ListWithElementsOfDifferentType_ReturnsFalse() throws SpongeMatcherParseException {
        Object o = ImmutableList.of(1, 2, 3);

        boolean canMatch = MatcherType.list(MatcherType.BOOLEAN).canMatch(o);

        assertThat(canMatch, is(false));
    }

    @Test
    public void canMatch_ListWithElementsOfSameType_ReturnsTrue() throws SpongeMatcherParseException {
        Object o = ImmutableList.of(true, false, false);

        boolean canMatch = MatcherType.list(MatcherType.BOOLEAN).canMatch(o);

        assertThat(canMatch, is(true));
    }



    @Test
    public void canParse_NonListElement_ReturnsFalse() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("1");

        assertThat(MatcherType.list(MatcherType.BOOLEAN).canParseMatcher(element), is(false));
    }

    @Test
    public void canParse_DifferentLiteralElement_ReturnsFalse() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("absent");

        assertThat(MatcherType.list(MatcherType.BOOLEAN).canParseMatcher(element), is(false));
    }

    @Test
    public void canParse_NoneLiteral_ReturnsTrue() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("none");

        assertThat(MatcherType.list(MatcherType.BOOLEAN).canParseMatcher(element), is(true));
    }

    @Test
    public void canParse_ListMatchAnyLiteral_ReturnsTrue() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("matchAny: 1");

        assertThat(MatcherType.list(MatcherType.BOOLEAN).canParseMatcher(element), is(true));
    }

    @Test
    public void canParse_ListMatchAnyAll_ReturnsTrue() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("matchAll: 1");

        assertThat(MatcherType.list(MatcherType.BOOLEAN).canParseMatcher(element), is(true));
    }

    @Test
    public void canParse_ValidListElement_ReturnsTrue() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("[true,false]");

        assertThat(MatcherType.list(MatcherType.BOOLEAN).canParseMatcher(element), is(true));
    }



    @Test
    public void parse_NonListElement_ThrowsException() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("1");

        ExceptionChecker.check(SpongeMatcherParseException.class,
                () -> MatcherType.list(MatcherType.BOOLEAN).parseMatcher(element));
    }

    @Test
    public void parse_DifferentLiteralElement_ThrowsException() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("absent");

        ExceptionChecker.check(SpongeMatcherParseException.class,
                () -> MatcherType.list(MatcherType.BOOLEAN).parseMatcher(element));
    }

    @Test
    public void parse_DifferentElementType_ThrowsException() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("[1,2]");

        ExceptionChecker.check(SpongeMatcherParseException.class,
                () -> MatcherType.list(MatcherType.BOOLEAN).parseMatcher(element));
    }

    @Test
    public void parse_ListMatchAnyAndDifferentElementType_ThrowsException() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("matchAny:1");

        ExceptionChecker.check(SpongeMatcherParseException.class,
                () -> MatcherType.list(MatcherType.BOOLEAN).parseMatcher(element));
    }

    @Test
    public void parse_ListMatchAllAndDifferentElementType_ThrowsException() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("matchAll:1");

        ExceptionChecker.check(SpongeMatcherParseException.class,
                () -> MatcherType.list(MatcherType.BOOLEAN).parseMatcher(element));
    }

    @Test
    public void parse_ValidListElement_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("[true,false]");

        SpongeMatcher<List<Boolean>> matcher = MatcherType.list(MatcherType.BOOLEAN).parseMatcher(element);

        assertThat(matcher, matches(Lists.newArrayList(true, false)));
    }

    @Test
    public void parse_ListMatchAnyElement_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("matchAny:false");

        SpongeMatcher<List<Boolean>> matcher = MatcherType.list(MatcherType.BOOLEAN).parseMatcher(element);

        assertThat(matcher, matches(Lists.newArrayList(true, false)));
    }

    @Test
    public void parse_ListMatchAllElement_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("matchAll:false");

        SpongeMatcher<List<Boolean>> matcher = MatcherType.list(MatcherType.BOOLEAN).parseMatcher(element);

        assertThat(matcher, matches(Lists.newArrayList(false, false)));
    }

    @Test
    public void parse_NoneElement_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("none");

        SpongeMatcher<List<Boolean>> matcher = MatcherType.list(MatcherType.BOOLEAN).parseMatcher(element);

        assertThat(matcher, matches(Lists.newArrayList()));
    }
}
