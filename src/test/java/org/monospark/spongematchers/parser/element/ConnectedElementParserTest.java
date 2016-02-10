package org.monospark.spongematchers.parser.element;

import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.junit.Test;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.ConnectedElement.Operator;

public class ConnectedElementParserTest {

    @Test
    public void parseElements_ConnectedElementsWithOr_ReturnsCorrectStringElement() throws SpongeMatcherParseException {
        String input = "'test1'|'test2'|'test3'";

        verifyConnectedElement(input, Operator.OR);
    }

    @Test
    public void parseElements_ConnectedElementsWithOrWithSpaces_ReturnsCorrectStringElement()
            throws SpongeMatcherParseException {
        String input = "    'test1' |   'test2' |    'test3' ";

        verifyConnectedElement(input, Operator.OR);
    }

    @Test
    public void parseElements_ConnectedElementsWithAnd_ReturnsCorrectStringElement()
            throws SpongeMatcherParseException {
        String input = "'test1'&'test2'&'test3'";

        verifyConnectedElement(input, Operator.AND);
    }

    @Test
    public void parseElements_ConnectedElementsWithAndWithSpaces_ReturnsCorrectStringElement()
            throws SpongeMatcherParseException {
        String input = "    'test1' &   'test2' & 'test3'";

        verifyConnectedElement(input, Operator.AND);
    }

    private void verifyConnectedElement(String input, Operator op) throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement(input);

        ConnectedElement con = (ConnectedElement) element;
        assertThat(con.getOperator(), equalTo(op));
        Set<StringElement> elements = (Set<StringElement>) con.getElements();
        assertThat(elements.size(), is(3));
        for (StringElement e : elements) {
            BaseElement<?> base = (BaseElement<?>) e;
            assertThat(base.getString(), either(equalTo("'test1'")).or(equalTo("'test2'")).or(equalTo("'test3'")));
        }
    }
}
