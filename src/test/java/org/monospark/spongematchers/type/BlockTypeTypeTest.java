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
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.data.Property;

import com.google.common.collect.ImmutableSet;

public class BlockTypeTypeTest {

    @Test
    public void canMatch_NonBlockTypeObject_ReturnsFalse() throws SpongeMatcherParseException {
        Object o = 5;

        boolean canMatch = MatcherType.BLOCK_TYPE.canMatch(o);

        assertThat(canMatch, is(false));
    }

    @Test
    public void canMatch_BlockTypeObject_ReturnsTrue() throws Exception {
        Object o = mock(BlockType.class);

        boolean canMatch = MatcherType.BLOCK_TYPE.canMatch(o);

        assertThat(canMatch, is(true));
    }

    public static final BlockType TEST_BLOCK_TYPE = createTestBlockType();

    @SuppressWarnings("unchecked")
    private static BlockType createTestBlockType() {
        BlockType type = mock(BlockType.class);
        when(type.getId()).thenReturn("minecraft:apple");
        Property<String, Integer> p1 = mock(Property.class);
        when(p1.getKey()).thenReturn("test1");
        when(p1.getValue()).thenReturn(1);
        Property<String, Boolean> p2 = mock(Property.class);
        when(p2.getKey()).thenReturn("test2");
        when(p2.getValue()).thenReturn(false);
        when(type.getApplicableProperties()).thenReturn(ImmutableSet.of(p1, p2));
        return type;
    }

    public static final String TEST_BLOCK_TYPE_MATCHER =
            "{'id':r'minecraft:appl.','properties':{'test1':1,'test2':false}}";

    @Test
    public void parseMatcher_ValidMapElement_ReturnsCorrectSpongeMatcher() throws SpongeMatcherParseException {
        StringElement element = StringElementParser.parseStringElement(TEST_BLOCK_TYPE_MATCHER);

        SpongeMatcher<BlockType> matcher = MatcherType.BLOCK_TYPE.parseMatcher(element);

        assertThat(matcher, matches(TEST_BLOCK_TYPE));
    }
}
