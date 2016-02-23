package org.monospark.spongematchers.type;

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

import com.google.common.collect.ImmutableMap;

public class DefinedMapTypeTest {

    @Test
    public void canMatch_NonMap_ReturnsFalse() throws SpongeMatcherParseException {
        Object o = 5;

        boolean canMatch = MatcherType.definedMap()
                .addEntry("test", MatcherType.BOOLEAN)
                .build()
                .canMatch(o);

        assertThat(canMatch, is(false));
    }

    @Test
    public void canMatch_MapWithValueOfDifferentType_ReturnsFalse() throws SpongeMatcherParseException {
        Object o = ImmutableMap.of("test", 1);

        boolean canMatch = MatcherType.definedMap()
                .addEntry("test", MatcherType.BOOLEAN)
                .build()
                .canMatch(o);

        assertThat(canMatch, is(false));
    }

    @Test
    public void canMatch_MapWithValueOfSameType_ReturnsTrue() throws SpongeMatcherParseException {
        Object o = ImmutableMap.of("test", true);

        boolean canMatch = MatcherType.definedMap()
                .addEntry("test", MatcherType.BOOLEAN)
                .build()
                .canMatch(o);

        assertThat(canMatch, is(true));
    }



    @Test
    public void checkElement_NonMapElement_ReturnsFalse() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("absent");

        assertThat(MatcherType.definedMap().addEntry("test", MatcherType.BOOLEAN).build().acceptsElement(element),
                is(false));
    }

    @Test
    public void checkElement_MapElement_ReturnsTrue() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("{'testing':true}");

        assertThat(MatcherType.definedMap().addEntry("testing", MatcherType.BOOLEAN).build().acceptsElement(element),
                is(true));
    }



    @Test
    public void parse_MapWithUnknownEntry_ThrowsException() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("{'wrong':true}");

        ExceptionChecker.check(SpongeMatcherParseException.class,
                () -> MatcherType.definedMap().addEntry("test", MatcherType.BOOLEAN).build().parseMatcher(element));
    }

    @Test
    public void parse_DifferentValueType_ThrowsException() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("{'test':1}");

        ExceptionChecker.check(SpongeMatcherParseException.class,
                () -> MatcherType.definedMap().addEntry("test", MatcherType.BOOLEAN).build().parseMatcher(element));
    }

    @Test
    public void parse_ValidMapElements_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("{'boolean':true,'integer':1}");

        SpongeMatcher<Map<String, Object>> matcher = MatcherType.definedMap()
                .addEntry("boolean", MatcherType.BOOLEAN)
                .addEntry("integer", MatcherType.INTEGER)
                .build()
                .parseMatcher(element);

        assertThat(matcher, matches(ImmutableMap.of("boolean", true, "integer", 1L)));
        assertThat(matcher, matches(ImmutableMap.of("boolean", true, "integer", 1L, "other", "test")));
    }
}
