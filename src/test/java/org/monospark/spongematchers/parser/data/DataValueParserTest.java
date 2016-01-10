package org.monospark.spongematchers.parser.data;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import java.util.Optional;

import org.junit.Test;
import org.monospark.spongematchers.matcher.data.DataEntry;
import org.monospark.spongematchers.matcher.data.DataValue;
import org.monospark.spongematchers.matcher.data.DataValue.Type;

public class DataValueParserTest {

    @Test
    public void parseDataEntry_IntegerMatcher_ReturnsIntegerDataEntry() {
        String input = "<-5";
        
        Optional<? extends DataEntry> entry = DataEntryParser.parseDataEntry(input);
        
        assertTrue(entry.isPresent());
        assertTrue(entry.get() instanceof DataValue);
        @SuppressWarnings("unchecked")
        DataValue<Long> value = (DataValue<Long>) entry.get();
        assertTrue(value.getType().equals(Type.INTEGER));
        assertThat(value.getMatcher(), matches(-6L));
        assertThat(value.getMatcher(), not(matches(-1L)));
    }
    
    @Test
    public void parseDataEntry_StringMatcher_ReturnsStringDataEntry() {
        String input = "'test, string!'";
        
        Optional<? extends DataEntry> entry = DataEntryParser.parseDataEntry(input);
        
        assertTrue(entry.isPresent());
        assertTrue(entry.get() instanceof DataValue);
        @SuppressWarnings("unchecked")
        DataValue<String> value = (DataValue<String>) entry.get();
        assertTrue(value.getType().equals(Type.STRING));
        assertThat(value.getMatcher(), matches("test, string!"));
        assertThat(value.getMatcher(), not(matches("test")));
    }
}
