package org.monospark.spongematchers.type;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.monospark.spongematchers.parser.element.StringElementParser;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.MemoryDataContainer;

import com.google.common.collect.ImmutableList;

public class DataViewTypeTest {

    @Test
    public void canMatch_NonDataViewObject_ReturnsFalse() throws SpongeMatcherParseException {
        Object o = 5;
        
        boolean canMatch = MatcherType.DATA_VIEW.canMatch(o);
        
        assertThat(canMatch, is(false));
    }
    
    @Test
    public void canMatch_DataViewObject_ReturnsTrue() throws SpongeMatcherParseException {
        DataView view = new MemoryDataContainer();
        view.set(DataQuery.of("boolean"), true);
        
        boolean canMatch = MatcherType.DATA_VIEW.canMatch(view);
        
        assertThat(canMatch, is(true));
    }
    
    @Test
    public void canMatch_SameClasses_ReturnsTrue() throws SpongeMatcherParseException {
        Object o = true;
        
        boolean canMatch = MatcherType.BOOLEAN.canMatch(o);
        
        assertThat(canMatch, is(true));
    }
    
    @Test
    public void canParse_NonMapElement_ReturnsFalse() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("empty");
        
        boolean canParse = MatcherType.DATA_VIEW.canParse(element, false);
        
        assertThat(canParse, is(false));
    }

    @Test
    public void canParse_DeepAndUnparseableMapValue_ReturnsFalse() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("{test:any:any:1}");
        
        boolean canParse = MatcherType.DATA_VIEW.canParse(element, true);
        
        assertThat(canParse, is(false));
    }
    
    @Test
    public void parseMatcher_ValidMapEntries_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement(
                "{boolean:true,integer:1,fp:1.5f,string:'test',list:[1,2]}");
        
        SpongeMatcher<DataView> matcher = MatcherType.DATA_VIEW.parseMatcher(element);
        
        DataView view1 = new MemoryDataContainer();
        view1.set(DataQuery.of("boolean"), true);
        view1.set(DataQuery.of("integer"), 1L);
        view1.set(DataQuery.of("fp"), 1.5D);
        view1.set(DataQuery.of("string"), "test");
        view1.set(DataQuery.of("list"), ImmutableList.of(1L, 2L));
        assertThat(matcher, matches(view1));
        
        DataView view2 = new MemoryDataContainer();
        view2.set(DataQuery.of("boolean"), 1L);
        view2.set(DataQuery.of("integer"), false);
        view2.set(DataQuery.of("fp"), 1.5D);
        view2.set(DataQuery.of("string"), "test");
        view2.set(DataQuery.of("list"), ImmutableList.of(1D, 2D, 3D));
        assertThat(matcher, not(matches(view2)));
    }
}
