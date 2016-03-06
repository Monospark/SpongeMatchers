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
import org.spongepowered.api.world.Dimension;

public class DimensionTypeTest {

    @Test
    public void canMatch_NonDimensionObject_ReturnsFalse() throws SpongeMatcherParseException {
        Object o = 5;

        boolean canMatch = MatcherType.DIMENSION.canMatch(o);

        assertThat(canMatch, is(false));
    }

    @Test
    public void canMatch_DimensionObject_ReturnsTrue() throws Exception {
        Object o = mock(Dimension.class);

        boolean canMatch = MatcherType.DIMENSION.canMatch(o);

        assertThat(canMatch, is(true));
    }

    public static final Dimension TEST_DIMENSION = createTestDimension();

    private static Dimension createTestDimension() {
        Dimension dimension = mock(Dimension.class);
        org.spongepowered.api.world.DimensionType type = mock(org.spongepowered.api.world.DimensionType.class);
        when(type.getId()).thenReturn("dimensionType");
        when(dimension.getType()).thenReturn(type);
        when(dimension.allowsPlayerRespawns()).thenReturn(true);
        when(dimension.doesWaterEvaporate()).thenReturn(false);
        when(dimension.hasSky()).thenReturn(true);
        when(dimension.getHeight()).thenReturn(200);
        when(dimension.getBuildHeight()).thenReturn(100);
        return dimension;
    }

    public static final String TEST_DIMENSION_MATCHER = "{'name': 'dimensionType', 'respawnAllowed': true,"
            + "'waterEvaporating': false, 'sky': true, 'height': 200, 'buildHeight': 100}";

    @Test
    public void parseMatcher_ValidMapElement_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement(TEST_DIMENSION_MATCHER);

        SpongeMatcher<Dimension> matcher = MatcherType.DIMENSION.parseMatcher(element);

        assertThat(matcher, matches(TEST_DIMENSION));
    }
}
