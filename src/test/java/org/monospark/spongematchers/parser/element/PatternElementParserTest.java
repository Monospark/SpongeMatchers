package org.monospark.spongematchers.parser.element;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.PatternElement.Type;

public class PatternElementParserTest {

    @Test
    public void parseElements_ListMatchAnyType_ReturnsCorrectStringElement()
            throws SpongeMatcherParseException {
        String input = "any:'test'";
        
        verifyPatternElement(input, Type.LIST_MATCH_ANY);
    }
    
    @Test
    public void parseElements_ListMatchAnyTypeWithSpaces_ReturnsCorrectStringElement()
            throws SpongeMatcherParseException {
        String input = " any :   'test' ";
        
        verifyPatternElement(input, Type.LIST_MATCH_ANY);
    }
    
    @Test
    public void parseElements_ListMatchAllType_ReturnsCorrectStringElement()
            throws SpongeMatcherParseException {
        String input = "all:'test'";
        
        verifyPatternElement(input, Type.LIST_MATCH_ALL);
    }
    
    @Test
    public void parseElements_ListMatchAllTypeWithSpaces_ReturnsCorrectStringElement()
            throws SpongeMatcherParseException {
        String input = " all  :  'test' ";
        
        verifyPatternElement(input, Type.LIST_MATCH_ALL);
    }
    
    @Test
    public void parseElements_NotType_ReturnsCorrectStringElement()
            throws SpongeMatcherParseException {
        String input = "!'test'";
        
        verifyPatternElement(input, Type.NOT);
    }
    
    @Test
    public void parseElements_NotTypeWithSpaces_ReturnsCorrectStringElement()
            throws SpongeMatcherParseException {
        String input = "    ! 'test' ";
        
        verifyPatternElement(input, Type.NOT);
    }
    
    @Test
    public void parseElements_ParanthesesType_ReturnsCorrectStringElement()
            throws SpongeMatcherParseException {
        String input = "('test')";
        
        verifyPatternElement(input, Type.PARANTHESES);
    }
    
    @Test
    public void parseElements_ParanthesesTypeWithSpaces_ReturnsCorrectStringElement()
            throws SpongeMatcherParseException {
        String input = " (  'test' ) ";
        
        verifyPatternElement(input, Type.PARANTHESES);
    }
    
    private void verifyPatternElement(String input, Type type) throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement(input);
        
        PatternElement pattern = (PatternElement) element;
        assertThat(pattern.getType(), equalTo(type));
    }
}
