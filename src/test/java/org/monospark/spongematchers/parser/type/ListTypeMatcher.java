package org.monospark.spongematchers.parser.type;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import java.util.List;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.monospark.spongematchers.parser.element.StringElementParser;
import org.monospark.spongematchers.testutil.ExceptionChecker;

import com.google.common.collect.Lists;

public class ListTypeMatcher {

    @Test
    public void parseMatcher_NonListElement_ThrowsException() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("empty");
        ExceptionChecker.check(SpongeMatcherParseException.class,
                () -> MatcherType.list(MatcherType.BOOLEAN).parseMatcher(element));
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
        StringElement element = StringElementParser.parseStringElement("any:false");
        
        SpongeMatcher<List<Boolean>> matcher = MatcherType.list(MatcherType.BOOLEAN).parseMatcher(element);
        
        assertThat(matcher, matches(Lists.newArrayList(true, false)));
        assertThat(matcher, not(matches(Lists.newArrayList(true, true))));
    }
    
    @Test
    public void parseMatcher_ListMatchAllElement_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("all:false");
        
        SpongeMatcher<List<Boolean>> matcher = MatcherType.list(MatcherType.BOOLEAN).parseMatcher(element);
        
        assertThat(matcher, matches(Lists.newArrayList(false, false)));
        assertThat(matcher, not(matches(Lists.newArrayList(false, true))));
    }
}
