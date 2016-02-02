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
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

public class ItemStackTypeTest {

    @Test
    public void canMatch_NonItemStackObject_ReturnsFalse() throws SpongeMatcherParseException {
        Object o = 5;
        
        boolean canMatch = MatcherType.ITEM_STACK.canMatch(o);
        
        assertThat(canMatch, is(false));
    }
    
    @Test
    public void canMatch_ItemStackObject_ReturnsTrue() throws Exception {
        Object o = mock(ItemStack.class);

        boolean canMatch = MatcherType.ITEM_STACK.canMatch(o);
        
        assertThat(canMatch, is(true));
    }

    @Test
    public void parseMatcher_ValidMapElement_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement(
                "{'type':'minecraft:appl.+','durability':0,'quantity':1,'data':{'test':1}}");

        SpongeMatcher<ItemStack> matcher = MatcherType.ITEM_STACK.parseMatcher(element);

        ItemType type = mock(ItemType.class);
        when(type.getId()).thenReturn("minecraft:apple");
        ItemStack s1 = mock(ItemStack.class);
        when(s1.getItem()).thenReturn(type);
        when(s1.getQuantity()).thenReturn(1);
        DataContainer container1 = new MemoryDataContainer();
        container1.set(DataQuery.of("UnsafeDamage"), 0);
        container1.set(DataQuery.of("UnsafeData", "test"), 1);
        when(s1.toContainer()).thenReturn(container1);
        assertThat(matcher, matches(s1));
        ItemStack s2 = mock(ItemStack.class);
        when(s2.getItem()).thenReturn(type);
        when(s2.getQuantity()).thenReturn(2);
        DataContainer container2 = new MemoryDataContainer();
        container2.set(DataQuery.of("UnsafeDamage"), 1);
        when(s2.toContainer()).thenReturn(container2);
        assertThat(matcher, not(matches(s2)));
    }
}
