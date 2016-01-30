package org.monospark.spongematchers.type;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.matcher.sponge.BlockTypeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.element.StringElement;
import org.spongepowered.api.block.BlockType;

public final class BlockTypeType extends MatcherType<BlockType> {

    private MatcherType<Map<String,Object>> type = MatcherType.definedMap()
            .addEntry("id", MatcherType.STRING)
            .addEntry("properties", MatcherType.PROPERTY_HOLDER)
            .build();
   
    BlockTypeType() {
        super("block type");
    }

    @Override
    public boolean canMatch(Object o) {
        return o instanceof BlockType;
    }

    @Override
    protected boolean canParse(StringElement element, boolean deep) {
        return type.canParseMatcher(element, deep);
    }

    @Override
    protected SpongeMatcher<BlockType> parse(StringElement element) throws SpongeMatcherParseException {
        return BlockTypeMatcher.create(type.parseMatcher(element));
    }
}
