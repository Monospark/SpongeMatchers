package org.monospark.spongematchers.parser;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.BaseMatchers;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.data.DataViewMatcherParser;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.item.Enchantment;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

import com.google.common.collect.Sets;

public abstract class SpongeMatcherParser<T> {

    public static final SpongeMatcherParser<Long> INTEGER = new IntegerMatcherParser();

    public static final SpongeMatcherParser<ItemType> ITEM_TYPE = new TypeMatcherParser<ItemType>();

    public static final SpongeMatcherParser<ItemStack> ITEM_STACK = new ItemStackMatcherParser();
    
    public static final SpongeMatcherParser<Optional<ItemStack>> ITEM_STACK_OR_HAND =
            new ItemStackOrHandMatcherParser();
    
    public static final SpongeMatcherParser<Enchantment> ENCHANTMENT = new TypeMatcherParser<Enchantment>();

    public static final SpongeMatcherParser<ItemEnchantment> ITEM_ENCHANTMENT = new ItemEnchantmentMatcherParser();
    
    public static final SpongeMatcherParser<DataView> DATA_VIEW = new DataViewMatcherParser();
    
    private Pattern acceptanceRegex;
    
    protected SpongeMatcherParser() {
        acceptanceRegex = createAcceptanceRegex();
    }

    protected abstract Pattern createAcceptanceRegex();
    
    public final Optional<SpongeMatcher<T>> parseMatcher(String string) {
        Objects.requireNonNull(string);

        return parseWithAmountCheck(string);
    }
    
    private Optional<SpongeMatcher<T>> parseWithAmountCheck(String string) {
        Optional<List<SpongeMatcher<T>>> matchers = ParserHelper.<SpongeMatcher<T>>tokenize(string, ",", s -> {
            Matcher matcher = acceptanceRegex.matcher(s);
            return matcher.matches() ? parse(matcher) : Optional.empty();
        });
        
        if (!matchers.isPresent()) {
            return Optional.empty();
        } else {
            return Optional.of(BaseMatchers.amount(Sets.newHashSet(matchers.get())));
        }
    }
    
    protected abstract Optional<SpongeMatcher<T>> parse(Matcher matcher);

    public Pattern getAcceptanceRegex() {
        return acceptanceRegex;
    }
}
