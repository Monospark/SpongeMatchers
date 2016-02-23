package org.monospark.spongematchers.type;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.sponge.BlockMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.spongepowered.api.block.BlockSnapshot;

public final class BlockType extends MatcherType<BlockSnapshot> {

    private static final MatcherType<Map<String, Object>> TYPE = MatcherType.definedMap()
            .addEntry("state", MatcherType.BLOCK_STATE)
            .addEntry("location", MatcherType.BLOCK_LOCATION)
            .build();

    BlockType() {}

    @Override
    public boolean canMatch(Object o) {
        return o instanceof BlockSnapshot;
    }

    @Override
    protected boolean checkElement(StringElement element) {
        return TYPE.acceptsElement(element);
    }

    @Override
    protected SpongeMatcher<BlockSnapshot> parse(StringElement element) throws SpongeMatcherParseException {
        try {
            return BlockMatcher.create(TYPE.parseMatcher(element));
        } catch (SpongeMatcherParseException e) {
            throw new SpongeMatcherParseException("Couldn't parse block matcher: " + element.getString(), e);
        }
    }
}
