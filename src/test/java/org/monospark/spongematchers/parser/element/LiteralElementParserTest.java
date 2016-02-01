package org.monospark.spongematchers.parser.element;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.LiteralElement.Type;

public class LiteralElementParserTest {

    @Test
    public void parseElements_EmptyLowerCase_ReturnsCorrectStringElement() throws SpongeMatcherParseException {
        String input = "empty";
        
        StringElement element = StringElementParser.parseStringElement(input);

        LiteralElement e = (LiteralElement) element;
        assertThat(e.getType(), is(Type.EMPTY));
    }
    
    @Test
    public void parseElements_EmptyUpperCase_ReturnsCorrectStringElement() throws SpongeMatcherParseException {
        String input = "Empty";
        
        StringElement element = StringElementParser.parseStringElement(input);

        LiteralElement e = (LiteralElement) element;
        assertThat(e.getType(), is(Type.EMPTY));
    }
    
    @Test
    public void parseElements_EmptyWithSucceedingColon_GetsIgnored() throws SpongeMatcherParseException {
        String input = "{empty:1}";
        
        StringElement element = StringElementParser.parseStringElement(input);
        
        assertThat(element instanceof MapElement, is(true));
    }
    
    @Test
    public void parseElements_Wildcard_ReturnsCorrectStringElement() throws SpongeMatcherParseException {
        String input = "*";
        
        StringElement element = StringElementParser.parseStringElement(input);

        LiteralElement e = (LiteralElement) element;
        assertThat(e.getType(), is(Type.WILDCARD));
    }
}
