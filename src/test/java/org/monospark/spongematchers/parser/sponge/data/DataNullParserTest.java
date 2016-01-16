package org.monospark.spongematchers.parser.sponge.data;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.monospark.spongematchers.matcher.sponge.data.DataEntry;
import org.monospark.spongematchers.matcher.sponge.data.DataNull;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.sponge.data.DataEntryParser;

public class DataNullParserTest {

    @Test
    public void parseDataEntry_LowerCaseNull_ReturnsCorrectDataNull() throws SpongeMatcherParseException {
        String input = "null";
        
        Optional<? extends DataEntry> entry = DataEntryParser.parseDataEntry(input);
        
        assertTrue(entry.isPresent());
        assertTrue(entry.get() instanceof DataNull);
    }
    
    @Test
    public void parseDataEntry_UpperCaseNull_ReturnsCorrectDataNull() throws SpongeMatcherParseException {
        String input = "Null";
        
        Optional<? extends DataEntry> entry = DataEntryParser.parseDataEntry(input);
        
        assertTrue(entry.isPresent());
        assertTrue(entry.get() instanceof DataNull);
    }
}
