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
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;

public class BiomeLocationTypeTest {

    @Test
    public void canMatch_NonLocationObject_ReturnsFalse() throws SpongeMatcherParseException {
        Object o = 5;

        boolean canMatch = MatcherType.BIOME_LOCATION.canMatch(o);

        assertThat(canMatch, is(false));
    }

    @Test
    public void canMatch_LocationObject_ReturnsTrue() throws Exception {
        Object o = new Location<World>(WorldTypeTest.TEST_WORLD, new Vector3i(1, 0, 1));

        boolean canMatch = MatcherType.BIOME_LOCATION.canMatch(o);

        assertThat(canMatch, is(true));
    }



    @Test
    public void parseMatcher_ValidMapElement_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("{'x': 1, 'y': 1, 'world':"
                + WorldTypeTest.TEST_WORLD_MATCHER + "}");

        SpongeMatcher<Location<World>> matcher = MatcherType.BIOME_LOCATION.parseMatcher(element);

        assertThat(matcher, matches(new Location<World>(WorldTypeTest.TEST_WORLD, new Vector3i(1, 0, 1))));
    }
}
