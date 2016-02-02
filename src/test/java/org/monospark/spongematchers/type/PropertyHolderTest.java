package org.monospark.spongematchers.type;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.monospark.spongematchers.parser.element.StringElementParser;
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
        StringElement element = StringElementParser.parseStringElement("{'test1':false,'test2':1,'test3':2f,"
                + "'test4':'LIQUID'}");

        SpongeMatcher<PropertyHolder> matcher = MatcherType.PROPERTY_HOLDER.parseMatcher(element);

        Property<String,Boolean> p1 = mock(Property.class);
        when(p1.getKey()).thenReturn("test1");
        when(p1.getValue()).thenReturn(false);
        Property<String, Integer> p2 = mock(Property.class);
        when(p2.getKey()).thenReturn("test2");
        when(p2.getValue()).thenReturn(1);
        Property<String, Double> p3 = mock(Property.class);
        when(p3.getKey()).thenReturn("test3");
        when(p3.getValue()).thenReturn(2.0);
        Property<String,MatterProperty.Matter> p4 = mock(Property.class);
        when(p4.getKey()).thenReturn("test4");
        when(p4.getValue()).thenReturn(MatterProperty.Matter.LIQUID);
        PropertyHolder holder1 = mock(PropertyHolder.class);
        when(holder1.getApplicableProperties()).thenReturn(ImmutableSet.of(p1, p2, p3, p4));
        assertThat(matcher, matches(holder1));
        
        Property<String,Boolean> p5 = mock(Property.class);
        when(p5.getKey()).thenReturn("test1");
        when(p5.getValue()).thenReturn(true);
        Property<String, Integer> p6 = mock(Property.class);
        when(p6.getKey()).thenReturn("test2");
        when(p6.getValue()).thenReturn(2);
        PropertyHolder holder2 = mock(PropertyHolder.class);
        when(holder2.getApplicableProperties()).thenReturn(ImmutableSet.of(p5, p6));
        assertThat(matcher, not(matches(holder2)));
    }
}
