package org.monospark.spongematchers.type.sponge;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.monospark.spongematchers.parser.element.StringElementParser;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.data.value.mutable.MutableBoundedValue;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class PlayerTypeTest {

    @Test
    public void canMatch_NonPlayerObject_ReturnsFalse() throws SpongeMatcherParseException {
        Object o = 5;

        boolean canMatch = MatcherType.PLAYER.canMatch(o);

        assertThat(canMatch, is(false));
    }

    @Test
    public void canMatch_PlayerObject_ReturnsTrue() throws Exception {
        Object o = mock(Player.class);

        boolean canMatch = MatcherType.PLAYER.canMatch(o);

        assertThat(canMatch, is(true));
    }



    @SuppressWarnings("unchecked")
    @Test
    public void parseMatcher_ValidMapElement_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        UUID uuid = UUID.randomUUID();
        StringElement element = StringElementParser.parseStringElement("{"
                + " 'type': 'entity'"
                + ",'location':" + PositionLocationTypeTest.TEST_POSITION_LOCATION_MATCHER
                + ",'name': 'Test'"
                + ",'uuid': '" + uuid + "'"
                + ",'permissions': {'permission1': true, 'permission2': false, 'permission3': absent}"
                + ",'gamemode': 'survival'"
                + ",'helmet':" + ItemStackTypeTest.TEST_ITEM_STACK_MATCHER
                + ",'chestplate':" + ItemStackTypeTest.TEST_ITEM_STACK_MATCHER
                + ",'leggings':" + ItemStackTypeTest.TEST_ITEM_STACK_MATCHER
                + ",'boots':" + ItemStackTypeTest.TEST_ITEM_STACK_MATCHER
                + ",'itemInHand':" + ItemStackTypeTest.TEST_ITEM_STACK_MATCHER
                + "}");

        SpongeMatcher<Player> matcher = MatcherType.PLAYER.parseMatcher(element);

        org.spongepowered.api.entity.EntityType type = mock(org.spongepowered.api.entity.EntityType.class);
        when(type.getId()).thenReturn("entity");
        Player player = mock(Player.class);
        when(player.getType()).thenReturn(type);
        Location<World> location = PositionLocationTypeTest.TEST_POSITION_LOCATION;
        when(player.getLocation()).thenReturn(location);
        when(player.getRotation()).thenReturn(new Vector3d(1.5f, 2f, 1f));
        when(player.getVehicle()).thenReturn(Optional.empty());
        when(player.getPassenger()).thenReturn(Optional.empty());
        when(player.getBaseVehicle()).thenReturn(player);

        MutableBoundedValue<Double> health = mock(MutableBoundedValue.class);
        when(health.get()).thenReturn(10D);
        when(player.health()).thenReturn(health);
        MutableBoundedValue<Double> maxHealth = mock(MutableBoundedValue.class);
        when(maxHealth.get()).thenReturn(20D);
        when(player.maxHealth()).thenReturn(maxHealth);

        when(player.getName()).thenReturn("Test");
        when(player.getUniqueId()).thenReturn(uuid);

        Subject parent = mock(Subject.class);
        SubjectData parentData = mock(SubjectData.class);
        when(parentData.getPermissions(SubjectData.GLOBAL_CONTEXT)).thenReturn(ImmutableMap.of("permission1", true));
        when(parentData.getParents(SubjectData.GLOBAL_CONTEXT)).thenReturn(Collections.emptyList());
        when(parent.getSubjectData()).thenReturn(parentData);
        SubjectData playerData = mock(SubjectData.class);
        when(playerData.getPermissions(SubjectData.GLOBAL_CONTEXT)).thenReturn(ImmutableMap.of("permission2", false));
        when(playerData.getParents(SubjectData.GLOBAL_CONTEXT)).thenReturn(ImmutableList.of(parent));
        when(player.getSubjectData()).thenReturn(playerData);

        Value<GameMode> gameMode = mock(Value.class);
        GameMode mode = mock(GameMode.class);
        when(mode.getId()).thenReturn("survival");
        when(gameMode.get()).thenReturn(mode);
        when(player.gameMode()).thenReturn(gameMode);
        ItemStack testStack = ItemStackTypeTest.TEST_ITEM_STACK;
        when(player.getHelmet()).thenReturn(Optional.of(testStack));
        when(player.getChestplate()).thenReturn(Optional.of(testStack));
        when(player.getLeggings()).thenReturn(Optional.of(testStack));
        when(player.getBoots()).thenReturn(Optional.of(testStack));
        when(player.getItemInHand()).thenReturn(Optional.of(testStack));

        assertThat(matcher, matches(player));
    }
}
