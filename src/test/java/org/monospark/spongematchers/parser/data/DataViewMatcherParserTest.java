package org.monospark.spongematchers.parser.data;

import org.junit.Test;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.SpongeMatcherParser;
import org.monospark.spongematchers.testutil.ExceptionChecker;

public class DataViewMatcherParserTest {

    @Test
    public void parse_DataValue_ReturnsCorrectDataValue() throws SpongeMatcherParseException {
        String input = "<-5";
        
        SpongeMatcherParser.DATA_VIEW.parseMatcher(input);
    }
    
    @Test
    public void parse_InvalidDataValue_ThrowsException() {
        String input = "<-5!";
        
        ExceptionChecker.check(SpongeMatcherParseException.class,
                () -> SpongeMatcherParser.DATA_VIEW.parseMatcher(input));
    }
}
