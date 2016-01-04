package org.monospark.spongematchers;

import java.util.regex.Pattern;

import org.monospark.spongematchers.base.IntMatcher;
import org.monospark.spongematchers.base.IntMatcherParser;
import org.monospark.spongematchers.base.NameMatcher;
import org.monospark.spongematchers.base.NameMatcherParser;
import org.monospark.spongematchers.item.ItemStackMatcher;
import org.monospark.spongematchers.item.ItemStackMatcherParser;
import org.monospark.spongematchers.item.ItemTypeMatcher;
import org.monospark.spongematchers.item.ItemTypeMatcherParser;

public final class SpongeMatcherType<M extends SpongeMatcher<?>> {

    public static final SpongeMatcherType<IntMatcher> INT = new SpongeMatcherType<IntMatcher>(new IntMatcherParser());
    
    public static final SpongeMatcherType<NameMatcher> NAME = new SpongeMatcherType<NameMatcher>(new NameMatcherParser());
    
    public static final SpongeMatcherType<ItemTypeMatcher> ITEM_TYPE =
            new SpongeMatcherType<ItemTypeMatcher>(new ItemTypeMatcherParser());
    
    public static final SpongeMatcherType<ItemStackMatcher> ITEM_STACK =
            new SpongeMatcherType<ItemStackMatcher>(new ItemStackMatcherParser());

    private SpongeMatcherParser<M> parser;

    private SpongeMatcherType(SpongeMatcherParser<M> parser) {
        this.parser = parser;
    }

    public SpongeMatcherParser<M> getParser() {
        return parser;
    }
    
    public Pattern getAcceptanceRegex() {
        return parser.getAcceptanceRegex();
    }
}
