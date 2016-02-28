package org.monospark.spongematchers.type;

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
import org.spongepowered.api.entity.Entity;

import com.flowpowered.math.vector.Vector3d;

public class EntityTypeTest {

    @Test
    public void canMatch_NonEntityObject_ReturnsFalse() throws SpongeMatcherParseException {
        Object o = 5;

        boolean canMatch = MatcherType.ENTITY.canMatch(o);

        assertThat(canMatch, is(false));
    }

    @Test
    public void canMatch_EntityObject_ReturnsTrue() throws Exception {
        Object o = mock(Entity.class);

        boolean canMatch = MatcherType.ENTITY.canMatch(o);

        assertThat(canMatch, is(true));
    }



    private static final Entity OTHER_ENTITY = createOtherEntity();

    public static final Entity TEST_ENTITY = createTestEntity();

    private static Entity createTestEntity() {
        org.spongepowered.api.entity.EntityType type = mock(org.spongepowered.api.entity.EntityType.class);
        when(type.getId()).thenReturn("entity");
        Entity entity = mock(Entity.class);
        when(entity.getType()).thenReturn(type);
        when(entity.getLocation()).thenReturn(PositionLocationTypeTest.TEST_POSITION_LOCATION);
        when(entity.getRotation()).thenReturn(new Vector3d(1.5f, 2f, 1f));
        when(entity.getVehicle()).thenReturn(Optional.of(OTHER_ENTITY));
        when(entity.getPassenger()).thenReturn(Optional.of(OTHER_ENTITY));
        when(entity.getBaseVehicle()).thenReturn(OTHER_ENTITY);
        return entity;
    }

    private static Entity createOtherEntity() {
        org.spongepowered.api.entity.EntityType type = mock(org.spongepowered.api.entity.EntityType.class);
        when(type.getId()).thenReturn("other");
        Entity entity = mock(Entity.class);
        when(entity.getType()).thenReturn(type);
        when(entity.getLocation()).thenReturn(PositionLocationTypeTest.TEST_POSITION_LOCATION);
        when(entity.getRotation()).thenReturn(new Vector3d(0.0, 0.0, 0.0));
        when(entity.getVehicle()).thenReturn(Optional.empty());
        when(entity.getPassenger()).thenReturn(Optional.empty());
        when(entity.getBaseVehicle()).thenReturn(entity);
        return entity;
    }

    private static final String OTHER_ENTITY_MATCHER = "{'type': 'other', 'rotX': 0f}";

    public static final String TEST_ENTITY_MATCHER = "{'type': 'entity', 'location':"
            + PositionLocationTypeTest.TEST_POSITION_LOCATION_MATCHER + ",'rotX': >1f, 'rotY': 2f, 'rotZ': !2f,"
            + "'vehicle': " + OTHER_ENTITY_MATCHER + ", 'passenger': " + OTHER_ENTITY_MATCHER + ", 'baseVehicle': "
            + OTHER_ENTITY_MATCHER + "}";

    @Test
    public void parseMatcher_ValidMapElement_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement(TEST_ENTITY_MATCHER);

        SpongeMatcher<Entity> matcher = MatcherType.ENTITY.parseMatcher(element);

        assertThat(matcher, matches(TEST_ENTITY));
    }
}
