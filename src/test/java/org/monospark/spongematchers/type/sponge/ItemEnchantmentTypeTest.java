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
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.item.Enchantment;

public class ItemEnchantmentTypeTest {

    @Test
    public void canMatch_NonItemEnchantmentObject_ReturnsFalse() throws SpongeMatcherParseException {
        Object o = 5;

        boolean canMatch = MatcherType.ITEM_ENCHANTMENT.canMatch(o);

        assertThat(canMatch, is(false));
    }

    @Test
    public void canMatch_ItemEnchantmentObject_ReturnsTrue() throws SpongeMatcherParseException {
        Object o = new ItemEnchantment(mock(Enchantment.class), 2);

        boolean canMatch = MatcherType.ITEM_ENCHANTMENT.canMatch(o);

        assertThat(canMatch, is(true));
    }

    @Test
    public void parseMatcher_ValidMapElement_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("{'id':r'minecraft:.+','level':2}");

        SpongeMatcher<ItemEnchantment> matcher = MatcherType.ITEM_ENCHANTMENT.parseMatcher(element);

        Enchantment enchantment = mock(Enchantment.class);
        when(enchantment.getId()).thenReturn("minecraft:test");
        ItemEnchantment e = new ItemEnchantment(enchantment, 2);
        assertThat(matcher, matches(e));
    }
}
