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
    public void canParse_DeepAndDifferentElementType_ReturnsFalse() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("1");
        
        boolean canParse = MatcherType.optional(MatcherType.BOOLEAN).canParse(element, true);
        
        assertThat(canParse, is(false));
    }
    
    @Test
    public void canParse_NotDeepAndAnyElement_ReturnsTrue() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("1");
        
        boolean canParse = MatcherType.optional(MatcherType.BOOLEAN).canParse(element, false);
        
        assertThat(canParse, is(true));
    }
    
    @Test
    public void parseMatcher_EmptyElement_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("empty");
        
        SpongeMatcher<Optional<Boolean>> matcher = MatcherType.optional(MatcherType.BOOLEAN).parseMatcher(element);
        
        assertThat(matcher, matches(Optional.empty()));
    }
    
    @Test
    public void parseMatcher_ValueTypeElement_ReturnsCorrectMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("true");
        
        SpongeMatcher<Optional<Boolean>> matcher = MatcherType.optional(MatcherType.BOOLEAN).parseMatcher(element);
        
        assertThat(matcher, matches(Optional.of(true)));
    }
}
