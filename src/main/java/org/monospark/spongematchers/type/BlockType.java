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

    protected BlockType() {
        super("block");
    }

    @Override
    public boolean canMatch(Object o) {
        return o instanceof BlockSnapshot;
    }

    @Override
    protected boolean canParse(StringElement element, boolean deep) {
        return TYPE.canParseMatcher(element, deep);
    }

    @Override
    protected SpongeMatcher<BlockSnapshot> parse(StringElement element) throws SpongeMatcherParseException {
        return BlockMatcher.create(TYPE.parseMatcher(element));
    }
}
