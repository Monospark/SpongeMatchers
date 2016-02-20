package org.monospark.spongematchers.type;

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
import org.spongepowered.api.world.Dimension;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.storage.WorldProperties;

import com.google.common.collect.ImmutableMap;

public class WorldTypeTest {

    @Test
    public void canMatch_NonDimensionObject_ReturnsFalse() throws SpongeMatcherParseException {
        Object o = 5;

        boolean canMatch = MatcherType.WORLD.canMatch(o);

        assertThat(canMatch, is(false));
    }

    @Test
    public void canMatch_DimensionObject_ReturnsTrue() throws Exception {
        Object o = mock(World.class);

        boolean canMatch = MatcherType.WORLD.canMatch(o);

        assertThat(canMatch, is(true));
    }

    public static final World TEST_WORLD = createTestWorld();

    private static World createTestWorld() {
        World world = mock(World.class);
        when(world.getName()).thenReturn("world");
        Dimension dimension = DimensionTypeTest.TEST_DIMENSION;
        when(world.getDimension()).thenReturn(dimension);
        WorldProperties properties = mock(WorldProperties.class);
        when(properties.getSeed()).thenReturn(123L);
        when(world.getProperties()).thenReturn(properties);
        when(world.getGameRules()).thenReturn(ImmutableMap.of("rule1", "value1", "rule2", "value2"));
        return world;
    }

    public static final String TEST_WORLD_MATCHER = "{'name': 'world', 'dimension':"
            + DimensionTypeTest.TEST_DIMENSION_MATCHER + ", 'seed': 123, 'gameRules': {'rule1': 'value1',"
            + "'rule2': 'value2'}}";

    @Test
    public void parseMatcher_ValidMapElement_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement(TEST_WORLD_MATCHER);

        SpongeMatcher<World> matcher = MatcherType.WORLD.parseMatcher(element);

        assertThat(matcher, matches(TEST_WORLD));
    }
}
