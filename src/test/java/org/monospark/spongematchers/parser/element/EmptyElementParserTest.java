package org.monospark.spongematchers.parser.element;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;

public class EmptyElementParserTest {

    @Test
    public void parseElements_EmptyElementLowerCase_ReturnsCorrectStringElement()
            throws SpongeMatcherParseException {
        String input = "empty";
        
        StringElement element = StringElementParser.parseStringElement(input);

        assertTrue(element instanceof EmptyElement);
    }
    
    @Test
    public void parseElements_EmptyElementUpperCase_ReturnsCorrectStringElement()
            throws SpongeMatcherParseException {
        String input = "Empty";
        
        StringElement element = StringElementParser.parseStringElement(input);

        assertTrue(element instanceof EmptyElement);
    }
}
