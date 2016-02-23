package org.monospark.spongematchers.type;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.sponge.BlockTypeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.spongepowered.api.block.BlockType;

public final class BlockTypeType extends MatcherType<BlockType> {

    private static final MatcherType<Map<String, Object>> TYPE = MatcherType.definedMap()
            .addEntry("id", MatcherType.STRING)
            .addEntry("properties", MatcherType.PROPERTY_HOLDER)
            .build();

    BlockTypeType() {}

    @Override
    public boolean canMatch(Object o) {
        return o instanceof BlockType;
    }

    @Override
    protected boolean checkElement(StringElement element) {
        return TYPE.acceptsElement(element);
    }

    @Override
    protected SpongeMatcher<BlockType> parse(StringElement element) throws SpongeMatcherParseException {
        try {
            return BlockTypeMatcher.create(TYPE.parseMatcher(element));
        } catch (SpongeMatcherParseException e) {
            throw new SpongeMatcherParseException("Couldn't parse block type matcher: " + element.getString(), e);
        }
    }
}
