package org.monospark.spongematchers.parser.element;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.LiteralElement.Type;

public class LiteralElementParserTest {

    @Test
    public void parseElements_AbsentLowerCase_ReturnsCorrectStringElement() throws SpongeMatcherParseException {
        String input = "absent";

        StringElement element = StringElementParser.parseStringElement(input);

        LiteralElement e = (LiteralElement) element;
        assertThat(e.getType(), is(Type.ABSENT));
    }

    @Test
    public void parseElements_AbsentUpperCase_ReturnsCorrectStringElement() throws SpongeMatcherParseException {
        String input = "Absent";

        StringElement element = StringElementParser.parseStringElement(input);

        LiteralElement e = (LiteralElement) element;
        assertThat(e.getType(), is(Type.ABSENT));
    }

    @Test
    public void parseElements_EmptyList_ReturnsCorrectStringElement() throws SpongeMatcherParseException {
        String input = "[]";

        StringElement element = StringElementParser.parseStringElement(input);

        LiteralElement e = (LiteralElement) element;
        assertThat(e.getType(), is(Type.NONE));
    }

    @Test
    public void parseElements_Wildcard_ReturnsCorrectStringElement() throws SpongeMatcherParseException {
        String input = "*";

        StringElement element = StringElementParser.parseStringElement(input);

        LiteralElement e = (LiteralElement) element;
        assertThat(e.getType(), is(Type.WILDCARD));
    }
}
