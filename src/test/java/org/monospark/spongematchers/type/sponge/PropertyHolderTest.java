package org.monospark.spongematchers.type.sponge;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.monospark.spongematchers.parser.element.StringElementParser;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.data.Property;
import org.spongepowered.api.data.property.PropertyHolder;
import org.spongepowered.api.data.property.block.MatterProperty;

import com.google.common.collect.ImmutableSet;

public class PropertyHolderTest {

    @Test
    public void canMatch_NonLocationObject_ReturnsFalse() throws SpongeMatcherParseException {
        Object o = 5;

        boolean canMatch = MatcherType.PROPERTY_HOLDER.canMatch(o);

        assertThat(canMatch, is(false));
    }

    @Test
    public void canMatch_PropertyHolderObject_ReturnsTrue() throws Exception {
        Object o = mock(PropertyHolder.class);

        boolean canMatch = MatcherType.PROPERTY_HOLDER.canMatch(o);

        assertThat(canMatch, is(true));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void parseMatcher_ValidMapElement_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement(
                "{'test1':false,'test2':1,'test3':2f," + "'test4':'liquid'}");

        SpongeMatcher<PropertyHolder> matcher = MatcherType.PROPERTY_HOLDER.parseMatcher(element);

        Property<String, Boolean> p1 = mock(Property.class);
        when(p1.getKey()).thenReturn("Test1Property");
        when(p1.getValue()).thenReturn(false);
        Property<String, Integer> p2 = mock(Property.class);
        when(p2.getKey()).thenReturn("Test2Property");
        when(p2.getValue()).thenReturn(1);
        Property<String, Double> p3 = mock(Property.class);
        when(p3.getKey()).thenReturn("Test3Property");
        when(p3.getValue()).thenReturn(2.0);
        Property<String, MatterProperty.Matter> p4 = mock(Property.class);
        when(p4.getKey()).thenReturn("Test4Property");
        when(p4.getValue()).thenReturn(MatterProperty.Matter.LIQUID);
        PropertyHolder holder = mock(PropertyHolder.class);
        when(holder.getApplicableProperties()).thenReturn(ImmutableSet.of(p1, p2, p3, p4));
        assertThat(matcher, matches(holder));
    }
}
