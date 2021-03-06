package org.monospark.spongematchers.type.sponge;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.monospark.spongematchers.parser.element.StringElementParser;
import org.monospark.spongematchers.type.MatcherType;
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
    public void parseMatcher_ValidMapEntries_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement(
                "{'boolean':true,'integer':1,'fp':1.5f,'string':'test','list':[1,2],'dataview':{'nested':1}}");

        SpongeMatcher<DataView> matcher = MatcherType.DATA_VIEW.parseMatcher(element);

        DataView view1 = new MemoryDataContainer();
        view1.set(DataQuery.of("boolean"), true);
        view1.set(DataQuery.of("integer"), 1L);
        view1.set(DataQuery.of("fp"), 1.5D);
        view1.set(DataQuery.of("string"), "test");
        view1.set(DataQuery.of("list"), ImmutableList.of(1L, 2L));
        view1.set(DataQuery.of("dataview", "nested"), 1L);
        assertThat(matcher, matches(view1));
    }
}
