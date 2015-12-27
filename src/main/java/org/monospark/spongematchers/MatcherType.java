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

public final class MatcherType<M extends Matcher<?>> {

    public static final MatcherType<IntMatcher> INT = new MatcherType<IntMatcher>(new IntMatcherParser());
    
    public static final MatcherType<NameMatcher> NAME = new MatcherType<NameMatcher>(new NameMatcherParser());
    
    public static final MatcherType<ItemTypeMatcher> ITEM_TYPE =
            new MatcherType<ItemTypeMatcher>(new ItemTypeMatcherParser());
    
    public static final MatcherType<ItemStackMatcher> ITEM_STACK =
            new MatcherType<ItemStackMatcher>(new ItemStackMatcherParser());

    private MatcherParser<M> parser;

    private MatcherType(MatcherParser<M> parser) {
        this.parser = parser;
    }

    public MatcherParser<M> getParser() {
        return parser;
    }
    
    public Pattern getAcceptanceRegex() {
        return parser.getAcceptanceRegex();
    }
}
