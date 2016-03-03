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
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.Property;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

import com.google.common.collect.ImmutableSet;

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

    public static final ItemStack TEST_ITEM_STACK = createTestItemStack();

    @SuppressWarnings({"unchecked"})
    private static ItemStack createTestItemStack() {
        ItemType type = mock(ItemType.class);
        when(type.getId()).thenReturn("minecraft:apple");
        ItemStack stack = mock(ItemStack.class);
        when(stack.getItem()).thenReturn(type);
        when(stack.getQuantity()).thenReturn(1);
        Property<String, Boolean> p = mock(Property.class);
        when(p.getKey()).thenReturn("test");
        when(p.getValue()).thenReturn(false);
        when(stack.getApplicableProperties()).thenReturn(ImmutableSet.of(p));
        DataContainer container1 = new MemoryDataContainer();
        container1.set(DataQuery.of("UnsafeDamage"), 0);
        container1.set(DataQuery.of("UnsafeData", "test"), 1);
        when(stack.toContainer()).thenReturn(container1);
        return stack;
    }

    public static final String TEST_ITEM_STACK_MATCHER = "{'type':r'minecraft:appl.+','durability':0,'quantity':1,"
            + "'properties':{'test':false},'data':{'test':1}}";

    @Test
    public void parseMatcher_ValidMapElement_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement(TEST_ITEM_STACK_MATCHER);

        SpongeMatcher<ItemStack> matcher = MatcherType.ITEM_STACK.parseMatcher(element);

        assertThat(matcher, matches(TEST_ITEM_STACK));
    }
}
