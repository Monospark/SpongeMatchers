package org.monospark.spongematchers.type;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import java.util.List;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.monospark.spongematchers.parser.element.StringElementParser;
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
        
        boolean canParse = MatcherType.list(MatcherType.BOOLEAN).canParse(element, false);
        
        assertThat(canParse, is(false));
    }
    
    @Test
    public void canParse_DifferentPatternElement_ReturnsFalse() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("empty");
        
        boolean canParse = MatcherType.list(MatcherType.BOOLEAN).canParse(element, false);
        
        assertThat(canParse, is(false));
    }
    
    @Test
    public void canParse_DeepAndDifferentElementType_ReturnsFalse() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("[1,2]");
        
        boolean canParse = MatcherType.list(MatcherType.BOOLEAN).canParse(element, true);
        
        assertThat(canParse, is(false));
    }
    
    @Test
    public void canParse_ListMatchAnyAndDeepAndDifferentElementType_ReturnsFalse() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("matchAny:1");
        
        boolean canParse = MatcherType.list(MatcherType.BOOLEAN).canParse(element, true);
        
        assertThat(canParse, is(false));
    }

    @Test
    public void canParse_DeepAndValidListElement_ReturnsTrue() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("[true,false]");
        
        boolean canParse = MatcherType.list(MatcherType.BOOLEAN).canParse(element, true);
        
        assertThat(canParse, is(true));
    }
    
    @Test
    public void parseMatcher_ValidListElement_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("[true,false]");
        
        SpongeMatcher<List<Boolean>> matcher = MatcherType.list(MatcherType.BOOLEAN).parseMatcher(element);
        
        assertThat(matcher, matches(Lists.newArrayList(true, false)));
        assertThat(matcher, not(matches(Lists.newArrayList(false, true))));
    }
    
    @Test
    public void parseMatcher_ListMatchAnyElement_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("matchAny:false");
        
        SpongeMatcher<List<Boolean>> matcher = MatcherType.list(MatcherType.BOOLEAN).parseMatcher(element);
        
        assertThat(matcher, matches(Lists.newArrayList(true, false)));
        assertThat(matcher, not(matches(Lists.newArrayList(true, true))));
    }
    
    @Test
    public void parseMatcher_ListMatchAllElement_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("matchAll:false");
        
        SpongeMatcher<List<Boolean>> matcher = MatcherType.list(MatcherType.BOOLEAN).parseMatcher(element);
        
        assertThat(matcher, matches(Lists.newArrayList(false, false)));
        assertThat(matcher, not(matches(Lists.newArrayList(false, true))));
    }
}
