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
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class BlockTypeTest {

    @Test
    public void canMatch_NonBlockSnapshotObject_ReturnsFalse() throws SpongeMatcherParseException {
        Object o = 5;

        boolean canMatch = MatcherType.BLOCK.canMatch(o);

        assertThat(canMatch, is(false));
    }

    @Test
    public void canMatch_BlockStateObject_ReturnsTrue() throws Exception {
        Object o = mock(BlockSnapshot.class);

        boolean canMatch = MatcherType.BLOCK.canMatch(o);

        assertThat(canMatch, is(true));
    }

    @Test
    public void parseMatcher_ValidMapElement_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement("{'state':"
                + BlockStateTypeTest.TEST_BLOCK_STATE_MATCHER + ",'location':"
                + BlockLocationTypeTest.TEST_BLOCK_LOCATION_MATCHER + "}");

        SpongeMatcher<BlockSnapshot> matcher = MatcherType.BLOCK.parseMatcher(element);

        BlockSnapshot snapshot = mock(BlockSnapshot.class);
        when(snapshot.getExtendedState()).thenReturn(BlockStateTypeTest.TEST_BLOCK_STATE);
        Location<World> location = BlockLocationTypeTest.TEST_BLOCK_LOCATION;
        when(snapshot.getLocation()).thenReturn(Optional.of(location));

        assertThat(matcher, matches(snapshot));
    }
}
