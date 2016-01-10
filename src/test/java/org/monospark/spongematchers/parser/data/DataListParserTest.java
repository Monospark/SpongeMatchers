package org.monospark.spongematchers.parser.data;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.monospark.spongematchers.matcher.BaseMatchers;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.data.DataEntry;
import org.monospark.spongematchers.matcher.data.DataList;
import org.monospark.spongematchers.matcher.data.DataValue;

public class DataListParserTest {

    public void parseDataEntry_IntegerMatcherList_ReturnsCorrectDataList() {
        String input = "[<-5,2-5,7]";
        
        checkDataEntry(input);
    }
    
    public void parseDataEntry_IntegerMatcherListWithSpaces_ReturnsCorrectDataList() {
        String input = "[   <-5, 2-5  , 7 ]";
        
        checkDataEntry(input);
    }
    
    @SuppressWarnings("unchecked")
    private void checkDataEntry(String input) {
        Optional<? extends DataEntry> entry = DataEntryParser.parseDataEntry(input);
        
        assertTrue(entry.isPresent());
        assertTrue(entry.get() instanceof DataList);
        DataList list = (DataList) entry.get();
        assertTrue(list.getEntries().size() == 3);
        Set<SpongeMatcher<Long>> matchers = list.getEntries().stream()
                .<SpongeMatcher<Long>>map(e -> ((DataValue<Long>) e).getMatcher())
                .collect(Collectors.toSet());
        SpongeMatcher<Long> amount = BaseMatchers.amount(matchers);
        assertThat(amount, matches(-6L));
        assertThat(amount, matches(3L));
        assertThat(amount, matches(7L));
        assertThat(amount, not(matches(6L)));
    }
}
