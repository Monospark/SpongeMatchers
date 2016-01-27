package org.monospark.spongematchers.parser.type;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import java.util.Map;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.monospark.spongematchers.parser.element.StringElementParser;

import com.google.common.collect.ImmutableMap;

public class MapTypeTest {

    @Test
    public void canParse_NonMapElement_ReturnsFalse() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("empty");
        
        boolean canParse = MatcherType.map().addEntry("test", MatcherType.BOOLEAN).build().canParse(element, false);
        
        assertThat(canParse, is(false));
    }
    
    @Test
    public void canParse_MapWithUnknownEntry_ReturnsFalse() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("{testing:true}");
        
        boolean canParse = MatcherType.map().addEntry("test", MatcherType.BOOLEAN).build().canParse(element, false);
        
        assertThat(canParse, is(false));
    }
    
    @Test
    public void canParse_DeepAndDifferentValueType_ReturnsFalse() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("{test:1}");
        
        boolean canParse = MatcherType.map().addEntry("test", MatcherType.BOOLEAN).build().canParse(element, true);
        
        assertThat(canParse, is(false));
    }
    
    @Test
    public void canParse_DeepAndValidMapElements_ReturnsTrue() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("{test:true}");
        
        boolean canParse = MatcherType.map().addEntry("test", MatcherType.BOOLEAN).build().canParse(element, true);
        
        assertThat(canParse, is(true));
    }
    
    @Test
    public void parseMatcher_ValidMapElements_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("{boolean:true,integer:1}");
        
        SpongeMatcher<Map<String, Object>> matcher = MatcherType.map()
                .addEntry("boolean", MatcherType.BOOLEAN)
                .addEntry("integer", MatcherType.INTEGER)
                .build()
                .parseMatcher(element);
        
        assertThat(matcher, matches(ImmutableMap.of("boolean", true, "integer", 1L)));
        assertThat(matcher, matches(ImmutableMap.of("boolean", true, "integer", 1L, "other", "test")));
        assertThat(matcher, not(matches(ImmutableMap.of("boolean", false, "integer", 1L))));
    }
}
