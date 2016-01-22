package org.monospark.spongematchers.parser.sponge.data;

import org.junit.Test;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.base.BaseMatcherParser;
import org.monospark.spongematchers.testutil.ExceptionChecker;

public class DataViewMatcherParserTest {

    @Test
    public void parse_DataValue_ReturnsCorrectDataValue() throws SpongeMatcherParseException {
        String input = "<-5";
        
        BaseMatcherParser.DATA_VIEW.parseMatcher(input);
    }
    
    @Test
    public void parse_InvalidDataValue_ThrowsException() {
        String input = "<-5!";
        
        ExceptionChecker.check(SpongeMatcherParseException.class,
                () -> BaseMatcherParser.DATA_VIEW.parseMatcher(input));
    }
}
