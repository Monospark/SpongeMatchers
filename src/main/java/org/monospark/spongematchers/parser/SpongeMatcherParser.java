package org.monospark.spongematchers.parser;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.BaseMatchers;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.base.BooleanMatcherParser;
import org.monospark.spongematchers.parser.base.FloatingPointMatcherParser;
import org.monospark.spongematchers.parser.base.IntegerMatcherParser;
import org.monospark.spongematchers.parser.base.StringMatcherParser;
import org.monospark.spongematchers.parser.data.DataViewMatcherParser;
import org.monospark.spongematchers.parser.sponge.ItemEnchantmentMatcherParser;
import org.monospark.spongematchers.parser.sponge.ItemStackMatcherParser;
import org.monospark.spongematchers.parser.sponge.ItemStackOrHandMatcherParser;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.item.inventory.ItemStack;

import com.google.common.collect.Sets;

public abstract class SpongeMatcherParser<T> {

    public static final SpongeMatcherParser<Boolean> BOOLEAN = new BooleanMatcherParser();
    
    public static final SpongeMatcherParser<Long> INTEGER = new IntegerMatcherParser();
    
    public static final SpongeMatcherParser<Double> FLOATING_POINT = new FloatingPointMatcherParser();

    public static final SpongeMatcherParser<ItemStack> ITEM_STACK = new ItemStackMatcherParser();
    
    public static final SpongeMatcherParser<Optional<ItemStack>> ITEM_STACK_OR_HAND =
            new ItemStackOrHandMatcherParser();
    
    public static final SpongeMatcherParser<ItemEnchantment> ITEM_ENCHANTMENT = new ItemEnchantmentMatcherParser();
    
    public static final SpongeMatcherParser<DataView> DATA_VIEW = new DataViewMatcherParser();
    
    public static final SpongeMatcherParser<String> STRING = new StringMatcherParser();

    private String name;
    
    private Pattern acceptancePattern;

    protected SpongeMatcherParser(String name) {
        this.name = name;
        acceptancePattern = createAcceptancePattern();
    }

    protected abstract Pattern createAcceptancePattern();

    public final SpongeMatcher<T> parseMatcher(String string) throws SpongeMatcherParseException {
        Optional<SpongeMatcher<T>> matcher = parseMatcherOptional(string);
        if (!matcher.isPresent()) {
            throw new SpongeMatcherParseException("Invalid " + name + " matcher: " + string);
        } else {
            return matcher.get();
        }
    }
    
    public final Optional<SpongeMatcher<T>> parseMatcherOptional(String string) throws SpongeMatcherParseException {
        Objects.requireNonNull(string, "Input string must be not null");

        Optional<SpongeMatcher<T>> matcher = parseWithAmountCheck(string.trim());
        return matcher;
    }
    
    private Optional<SpongeMatcher<T>> parseWithAmountCheck(String string) throws SpongeMatcherParseException {
        Optional<List<SpongeMatcher<T>>> matchers = ParserHelper.<SpongeMatcher<T>>tokenize(string, '|', s -> {
            Matcher matcher = acceptancePattern.matcher(s.trim());
            return matcher.matches() ? Optional.of(parse(matcher)) : Optional.empty();
        });
        
        if (!matchers.isPresent()) {
            return Optional.empty();
        } else {
            return Optional.of(matchers.get().size() > 1 ? BaseMatchers.amount(Sets.newHashSet(matchers.get())) :
                    matchers.get().get(0));
        }
    }
    
    protected abstract SpongeMatcher<T> parse(Matcher matcher) throws SpongeMatcherParseException;

    public String getName() {
        return name;
    }
}
