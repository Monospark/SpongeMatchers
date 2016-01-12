package org.monospark.spongematchers.parser.data;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.SpongeMatcherParser;
import org.monospark.spongematchers.testutil.ExceptionChecker;
import org.spongepowered.api.data.DataView;

public class DataViewMatcherParserTest {

    @Test
    public void parse_DataValue_ReturnsCorrectDataValue() throws SpongeMatcherParseException {
        String input = "<-5";
        
        Optional<SpongeMatcher<DataView>> entry = SpongeMatcherParser.DATA_VIEW.parseMatcher(input);
        
        assertTrue(entry.isPresent());
    }
    
    @Test
    public void parse_InvalidDataValue_ThrowsException() {
        String input = "<-5!";
        
        ExceptionChecker.check(SpongeMatcherParseException.class,
                () -> SpongeMatcherParser.DATA_VIEW.parseMatcher(input));
    }
}
