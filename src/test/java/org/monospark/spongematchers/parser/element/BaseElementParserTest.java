package org.monospark.spongematchers.parser.element;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.base.BaseMatcherParser;

public class BaseElementParserTest {

    @Test
    public void parseElements_BooleanMatcher_ReturnsCorrectStringElement() throws SpongeMatcherParseException {
        String input = "true";
        
        verifyStringElement(input, BaseMatcherParser.BOOLEAN);
    }
    
    @Test
    public void parseElements_IntegerMatcher_ReturnsCorrectStringElement() throws SpongeMatcherParseException {
        String input = "12";
        
        verifyStringElement(input, BaseMatcherParser.INTEGER);
    }
    
    @Test
    public void parseElements_FloatingPointMatcher_ReturnsCorrectStringElement() throws SpongeMatcherParseException {
        String input = "1.5f";
        
        verifyStringElement(input, BaseMatcherParser.FLOATING_POINT);
    }
    
    @Test
    public void parseElements_StringMatcher_ReturnsCorrectStringElement() throws SpongeMatcherParseException {
        String input = "'test'";
        
        verifyStringElement(input, BaseMatcherParser.STRING);
    }
    
    private void verifyStringElement(String input, BaseMatcherParser<?> parser) throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement(input);
        
        BaseElement<?> base = (BaseElement<?>) element;
        assertThat(base.getString(), equalTo(input));
        assertThat(base.getParser(), equalTo(parser));
    }
}
