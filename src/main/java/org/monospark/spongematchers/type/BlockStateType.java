package org.monospark.spongematchers.type;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.sponge.BlockStateMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.spongepowered.api.block.BlockState;

public final class BlockStateType extends MatcherType<BlockState> {

    public static final MatcherType<Map<String, Object>> TRAIT_TYPE = MatcherType.undefinedMap()
            .addType(MatcherType.BOOLEAN)
            .addType(MatcherType.INTEGER)
            .addType(MatcherType.STRING)
            .build();

    private static final MatcherType<Map<String, Object>> TYPE = MatcherType.definedMap()
            .addEntry("type", MatcherType.BLOCK_TYPE)
            .addEntry("traits", TRAIT_TYPE)
            .addEntry("data", MatcherType.DATA_VIEW)
            .build();

    BlockStateType() {}

    @Override
    public boolean canMatch(Object o) {
        return o instanceof BlockState;
    }

    @Override
    protected boolean checkElement(StringElement element) {
        return TYPE.acceptsElement(element);
    }

    @Override
    protected SpongeMatcher<BlockState> parse(StringElement element) throws SpongeMatcherParseException {
        try {
            return BlockStateMatcher.create(TYPE.parseMatcher(element));
        } catch (SpongeMatcherParseException e) {
            throw new SpongeMatcherParseException("Couldn't parse block state matcher: " + element.getString(), e);
        }
    }
}
