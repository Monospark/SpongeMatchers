package org.monospark.spongematchers.type.advanced;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import java.util.Map;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.monospark.spongematchers.parser.element.StringElementParser;
import org.monospark.spongematchers.testutil.ExceptionChecker;
import org.monospark.spongematchers.type.MatcherType;

import com.google.common.collect.ImmutableMap;

public class VariableMapTypeTest {

    @Test
    public void canMatch_NonMap_ReturnsFalse() throws SpongeMatcherParseException {
        Object o = 5;

        boolean canMatch = MatcherType.variableMap(MatcherType.BOOLEAN).canMatch(o);

        assertThat(canMatch, is(false));
    }

    @Test
    public void canMatch_MapWithValueOfDifferentType_ReturnsFalse() throws SpongeMatcherParseException {
        Object o = ImmutableMap.of("test", 1);

        boolean canMatch = MatcherType.variableMap(MatcherType.BOOLEAN).canMatch(o);

        assertThat(canMatch, is(false));
    }

    @Test
    public void canMatch_MapWithValueOfSameType_ReturnsTrue() throws SpongeMatcherParseException {
        Object o = ImmutableMap.of("test", true);

        boolean canMatch = MatcherType.variableMap(MatcherType.BOOLEAN).canMatch(o);

        assertThat(canMatch, is(true));
    }



    @Test
    public void canParse_NonMapElement_ReturnsFalse() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("absent");

        assertThat(MatcherType.variableMap(MatcherType.BOOLEAN).canParseMatcher(element), is(false));
    }

    @Test
    public void canParse_ValidMapElement_ReturnsTrue() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("{'test':true}");

        assertThat(MatcherType.variableMap(MatcherType.BOOLEAN).canParseMatcher(element), is(true));
    }



    @Test
    public void parse_NonMapElement_ThrowsException() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("absent");

        ExceptionChecker.check(SpongeMatcherParseException.class,
                () -> MatcherType.variableMap(MatcherType.BOOLEAN).parseMatcher(element));
    }

    @Test
    public void parse_DifferentValueType_ThrowsException() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("{'test':1}");

        ExceptionChecker.check(SpongeMatcherParseException.class,
                () -> MatcherType.variableMap(MatcherType.multi()
                        .addType(MatcherType.BOOLEAN)
                        .addType(MatcherType.FLOATING_POINT)
                        .build())
                        .parseMatcher(element));
    }

    @Test
    public void parse_ValidMapElements_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement(
                "{'boolean':true,'integer':1,'string':'string'}");

        SpongeMatcher<Map<String, Object>> matcher = MatcherType.variableMap(MatcherType.multi()
                .addType(MatcherType.BOOLEAN)
                .addType(MatcherType.INTEGER)
                .addType(MatcherType.STRING)
                .build())
                .parseMatcher(element);

        assertThat(matcher, matches(ImmutableMap.of("boolean", true, "integer", 1L, "string", "string")));
        assertThat(matcher, matches(
                ImmutableMap.of("boolean", true, "integer", 1L, "string", "string", "other", "test")));
    }
}
