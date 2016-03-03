package org.monospark.spongematchers.type.sponge;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import java.util.Optional;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.monospark.spongematchers.parser.element.StringElementParser;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.data.value.mutable.MutableBoundedValue;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

public class LivingTypeTest {

    @Test
    public void canMatch_NonLivingObject_ReturnsFalse() throws SpongeMatcherParseException {
        Object o = 5;

        boolean canMatch = MatcherType.LIVING.canMatch(o);

        assertThat(canMatch, is(false));
    }

    @Test
    public void canMatch_LivingObject_ReturnsTrue() throws Exception {
        Object o = mock(Living.class);

        boolean canMatch = MatcherType.LIVING.canMatch(o);

        assertThat(canMatch, is(true));
    }



    @SuppressWarnings("unchecked")
    @Test
    public void parseMatcher_ValidMapElement_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("{'type': 'entity', 'location':"
                + PositionLocationTypeTest.TEST_POSITION_LOCATION_MATCHER + ", 'health': 10f, 'maxHealth': 20f}");

        SpongeMatcher<Living> matcher = MatcherType.LIVING.parseMatcher(element);

        org.spongepowered.api.entity.EntityType type = mock(org.spongepowered.api.entity.EntityType.class);
        when(type.getId()).thenReturn("entity");
        Living living = mock(Living.class);
        when(living.getType()).thenReturn(type);
        Location<World> location = PositionLocationTypeTest.TEST_POSITION_LOCATION;
        when(living.getLocation()).thenReturn(location);
        when(living.getRotation()).thenReturn(new Vector3d(1.5f, 2f, 1f));
        when(living.getVehicle()).thenReturn(Optional.empty());
        when(living.getPassenger()).thenReturn(Optional.empty());
        when(living.getBaseVehicle()).thenReturn(living);
        MutableBoundedValue<Double> health = mock(MutableBoundedValue.class);
        when(health.get()).thenReturn(10D);
        when(living.health()).thenReturn(health);
        MutableBoundedValue<Double> maxHealth = mock(MutableBoundedValue.class);
        when(maxHealth.get()).thenReturn(20D);
        when(living.maxHealth()).thenReturn(maxHealth);

        assertThat(matcher, matches(living));
    }
}
