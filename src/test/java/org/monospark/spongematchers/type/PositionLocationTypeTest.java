package org.monospark.spongematchers.type;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.monospark.spongematchers.parser.element.StringElementParser;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.extent.Extent;

import com.flowpowered.math.vector.Vector3d;

public class PositionLocationTypeTest {

    @Test
    public void canMatch_NonLocationObject_ReturnsFalse() throws SpongeMatcherParseException {
        Object o = 5;

        boolean canMatch = MatcherType.POSITION_LOCATION.canMatch(o);

        assertThat(canMatch, is(false));
    }

    @Test
    public void canMatch_LocationObject_ReturnsTrue() throws Exception {
        Extent e = mock(Extent.class);
        Object o = new Location<Extent>(e, new Vector3d(1.0, 1.0, 1.0));

        boolean canMatch = MatcherType.POSITION_LOCATION.canMatch(o);

        assertThat(canMatch, is(true));
    }

    @Test
    public void parseMatcher_ValidMapElement_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("{'x':1.0f,'y':1.0f,'z':1.0f}");

        SpongeMatcher<Location<?>> matcher = MatcherType.POSITION_LOCATION.parseMatcher(element);

        Extent e = mock(Extent.class);
        assertThat(matcher, matches(new Location<Extent>(e, new Vector3d(1.0, 1.0, 1.0))));
    }
}
