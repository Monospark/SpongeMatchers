package org.monospark.spongematchers.parser.element;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;

public class ListElementParserTest {

    @Test
    public void parseElements_ListElementWithSingleElement_ReturnsCorrectStringElement()
            throws SpongeMatcherParseException {
        String input = "['test']";
        
        StringElement element = StringElementParser.parseStringElement(input);
        
        ListElement list = (ListElement) element;
        assertThat(list.getElements().size(), equalTo(1));
        BaseElement<?> base = (BaseElement<?>) list.getElements().get(0);
        assertThat(base.getString(), equalTo("'test'"));
    }
    
    @Test
    public void parseElements_ListElement_ReturnsCorrectStringElement()
            throws SpongeMatcherParseException {
        String input = "['test1','test2','test3']";
        
        StringElement element = StringElementParser.parseStringElement(input);
        
        ListElement list = (ListElement) element;
        assertThat(list.getElements().size(), equalTo(3));
        BaseElement<?> base1 = (BaseElement<?>) list.getElements().get(0);
        assertThat(base1.getString(), equalTo("'test1'"));
        BaseElement<?> base2 = (BaseElement<?>) list.getElements().get(1);
        assertThat(base2.getString(), equalTo("'test2'"));
        BaseElement<?> base3 = (BaseElement<?>) list.getElements().get(2);
        assertThat(base3.getString(), equalTo("'test3'"));
    }
    
    @Test
    public void parseElements_ListElementWithSpaces_ReturnsCorrectStringElement()
            throws SpongeMatcherParseException {
        String input = " [ 'test1'   , 'test2'   ,  'test3' ] ";
        
        StringElement element = StringElementParser.parseStringElement(input);
        
        ListElement list = (ListElement) element;
        assertThat(list.getElements().size(), equalTo(3));
        BaseElement<?> base1 = (BaseElement<?>) list.getElements().get(0);
        assertThat(base1.getString(), equalTo("'test1'"));
        BaseElement<?> base2 = (BaseElement<?>) list.getElements().get(1);
        assertThat(base2.getString(), equalTo("'test2'"));
        BaseElement<?> base3 = (BaseElement<?>) list.getElements().get(2);
        assertThat(base3.getString(), equalTo("'test3'"));
    }
}
