package org.monospark.spongematchers.parser.element;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.ConnectedElement.Operator;

public class ConnectedElementParserTest {

    @Test
    public void parseElements_ConnectedElementsWithOr_ReturnsCorrectStringElement()
            throws SpongeMatcherParseException {
        String input = "'test1'|'test2'";
        
        verifyConnectedElement(input, Operator.OR);
    }
    
    @Test
    public void parseElements_ConnectedElementsWithOrWithSpaces_ReturnsCorrectStringElement()
            throws SpongeMatcherParseException {
        String input = "    'test1' |   'test2' ";
        
        verifyConnectedElement(input, Operator.OR);
    }
    
    @Test
    public void parseElements_ConnectedElementsWithAnd_ReturnsCorrectStringElement()
            throws SpongeMatcherParseException {
        String input = "'test1'&'test2'";
        
        verifyConnectedElement(input, Operator.AND);
    }
    
    @Test
    public void parseElements_ConnectedElementsWithAndWithSpaces_ReturnsCorrectStringElement()
            throws SpongeMatcherParseException {
        String input = "    'test1' &   'test2' ";
        
        verifyConnectedElement(input, Operator.AND);
    }
    
    private void verifyConnectedElement(String input, Operator op) throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement(input);
        
        ConnectedElement con = (ConnectedElement) element;
        assertThat(con.getOperator(), equalTo(op));
        BaseElement<?> base1 = (BaseElement<?>) con.getFirstElement();
        BaseElement<?> base2 = (BaseElement<?>) con.getSecondElement();
        assertThat(base1.getString(), equalTo("'test1'"));
        assertThat(base2.getString(), equalTo("'test2'"));
    }
}
