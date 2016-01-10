package org.monospark.spongematchers.parser.data;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import java.util.Optional;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.data.DataEntry;
import org.monospark.spongematchers.matcher.data.DataMap;
import org.monospark.spongematchers.matcher.data.DataValue;

public class DataMapParserTest {

    public void parseDataEntry_DataMap_ReturnsCorrectDataMap() {
        String input = "{entry1:<-5,entry2:'test, string',entry3:(3,4)]";
        
        checkDataEntry(input);
    }
    
    public void parseDataEntry_DataMapWithSpaces_ReturnsCorrectDataMap() {
        String input = "{entry1:<-5,entry2:'test, string',entry3:(3,4)]";
        
        checkDataEntry(input);
    }
    
    @SuppressWarnings("unchecked")
    private void checkDataEntry(String input) {
        Optional<? extends DataEntry> entry = DataEntryParser.parseDataEntry(input);
        
        assertTrue(entry.isPresent());
        assertTrue(entry.get() instanceof DataMap);
        DataMap map = (DataMap) entry.get();
        assertTrue(map.getEntries().size() == 3);
        SpongeMatcher<Long> entry1 = ((DataValue<Long>) map.getEntries().get("entry1")).getMatcher();
        assertThat(entry1, matches(-6L));
        assertThat(entry1, not(matches(-4L)));
        SpongeMatcher<String> entry2 = ((DataValue<String>) map.getEntries().get("entry2")).getMatcher();
        assertThat(entry2, matches("test, string"));
        assertThat(entry2, not(matches("test")));
        SpongeMatcher<Long> entry3 = ((DataValue<Long>) map.getEntries().get("entry3")).getMatcher();
        assertThat(entry3, matches(3L));
        assertThat(entry3, matches(4L));
        assertThat(entry3, not(matches(5L)));
    }
}
