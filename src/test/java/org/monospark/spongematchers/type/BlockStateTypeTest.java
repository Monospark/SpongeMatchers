package org.monospark.spongematchers.type;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.monospark.spongematchers.testutil.HamcrestSpongeMatchers.matches;

import java.util.Map;

import org.junit.Test;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.monospark.spongematchers.parser.element.StringElementParser;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.trait.BlockTrait;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.MemoryDataContainer;

import com.google.common.collect.Maps;

public class BlockStateTypeTest {

    @Test
    public void canMatch_NonBlockStateObject_ReturnsFalse() throws SpongeMatcherParseException {
        Object o = 5;

        boolean canMatch = MatcherType.BLOCK_STATE.canMatch(o);

        assertThat(canMatch, is(false));
    }

    @Test
    public void canMatch_BlockStateObject_ReturnsTrue() throws Exception {
        Object o = mock(BlockState.class);

        boolean canMatch = MatcherType.BLOCK_STATE.canMatch(o);

        assertThat(canMatch, is(true));
    }

    public static final BlockState TEST_BLOCK_STATE = createTestBlockState();

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static BlockState createTestBlockState() {
        BlockType type = BlockTypeTypeTest.TEST_BLOCK_TYPE;
        BlockState state = mock(BlockState.class);
        when(state.getType()).thenReturn(type);
        BlockTrait<Integer> trait1 = mock(BlockTrait.class);
        when(trait1.getId()).thenReturn("trait1");
        when(trait1.getValueClass()).thenReturn(Integer.class);
        BlockTrait<Boolean> trait2 = mock(BlockTrait.class);
        when(trait2.getId()).thenReturn("trait2");
        when(trait2.getValueClass()).thenReturn(Boolean.class);
        Map traits = Maps.newHashMap();
        traits.put(trait1, 1);
        traits.put(trait2, false);
        when(state.getTraitMap()).thenReturn(traits);
        DataContainer container = new MemoryDataContainer();
        container.set(DataQuery.of("UnsafeData", "boolean"), true);
        container.set(DataQuery.of("UnsafeData", "integer"), 1);
        when(state.toContainer()).thenReturn(container);
        return state;
    }

    public static final String TEST_BLOCK_STATE_MATCHER = "{'type':" + BlockTypeTypeTest.TEST_BLOCK_TYPE_MATCHER
            + ",'traits': {'trait1': 1, 'trait2': false}, 'data':{'boolean': true, 'integer': >0}}";

    @Test
    public void parseMatcher_ValidMapElement_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement(TEST_BLOCK_STATE_MATCHER);

        SpongeMatcher<BlockState> matcher = MatcherType.BLOCK_STATE.parseMatcher(element);

        assertThat(matcher, matches(TEST_BLOCK_STATE));
    }
}
