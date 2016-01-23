package org.monospark.spongematchers.parser.type;

import static org.hamcrest.CoreMatchers.not;
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

public class MapTypeTest {

    @Test
    public void parseMatcher_NonMapElement_ThrowsException() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("empty");
        ExceptionChecker.check(SpongeMatcherParseException.class,
                () -> MatcherType.map().addEntry("test", MatcherType.BOOLEAN).build().parseMatcher(element));
    }
    
    @Test
    public void parseMatcher_ValidMapElement_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
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
