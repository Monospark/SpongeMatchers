package org.monospark.spongematchers.parser.element;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;

public class MapElementParserTest {

    @Test
    public void parseElements_MapElementWithSingleEntry_ReturnsCorrectStringElement()
            throws SpongeMatcherParseException {
        String input = "{test:'test'}";
        
        StringElement element = StringElementParser.parseStringElement(input);
        
        MapElement map = (MapElement) element;
        BaseElement<?> base = (BaseElement<?>) map.getElement("test").get();
        assertThat(base.getString(), equalTo("'test'"));
    }
    
    @Test
    public void parseElements_MapElementWithSingleEntryWithSpaces_ReturnsCorrectStringElement()
            throws SpongeMatcherParseException {
        String input = " { test  :  'test' }  ";
        
        StringElement element = StringElementParser.parseStringElement(input);
        
        MapElement map = (MapElement) element;
        BaseElement<?> base = (BaseElement<?>) map.getElement("test").get();
        assertThat(base.getString(), equalTo("'test'"));
    }
    
    @Test
    public void parseElements_MapElement_ReturnsCorrectStringElement() throws SpongeMatcherParseException {
        String input = "{firsttest:'test1',secondtest:'test2',thirdtest:'test3'}";
        
        StringElement element = StringElementParser.parseStringElement(input);
        
        MapElement map = (MapElement) element;
        BaseElement<?> base1 = (BaseElement<?>) map.getElement("firsttest").get();
        assertThat(base1.getString(), equalTo("'test1'"));
        BaseElement<?> base2 = (BaseElement<?>) map.getElement("secondtest").get();
        assertThat(base2.getString(), equalTo("'test2'"));
        BaseElement<?> base3 = (BaseElement<?>) map.getElement("thirdtest").get();
        assertThat(base3.getString(), equalTo("'test3'"));
    }
}
