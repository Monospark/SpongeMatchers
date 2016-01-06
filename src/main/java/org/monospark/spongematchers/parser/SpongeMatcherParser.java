package org.monospark.spongematchers.parser;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.monospark.spongematchers.matcher.BaseMatchers;
import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.item.Enchantment;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

import com.google.common.collect.Sets;

public abstract class SpongeMatcherParser<T> {

    public static final SpongeMatcherParser<Integer> INT = new IntMatcherParser();

    public static final SpongeMatcherParser<ItemType> ITEM_TYPE = new TypeMatcherParser<ItemType>();

    public static final SpongeMatcherParser<ItemStack> ITEM_STACK = new ItemStackMatcherParser();
    
    public static final SpongeMatcherParser<Optional<ItemStack>> ITEM_STACK_OR_HAND =
            new ItemStackOrHandMatcherParser();
    
    public static final SpongeMatcherParser<Enchantment> ENCHANTMENT = new TypeMatcherParser<Enchantment>();

    public static final SpongeMatcherParser<ItemEnchantment> ITEM_ENCHANTMENT = new ItemEnchantmentMatcherParser();
    
    private Pattern acceptanceRegex;
    
    protected SpongeMatcherParser() {
        acceptanceRegex = createAcceptanceRegex();
    }

    protected abstract Pattern createAcceptanceRegex();
    
    public final SpongeMatcher<T> parseMatcher(String string) throws SpongeMatcherFormatException {
        Objects.requireNonNull(string);

        return parseWithAmountCheck(string);
    }
    
    public final SpongeMatcher<T> parseMatcherUnsafe(String string) {
        Objects.requireNonNull(string);

        try {
            return parseWithAmountCheck(string);
        } catch (SpongeMatcherFormatException e) {
            throw new IllegalArgumentException("Invalid matcher string", e);
        }
    }
    
    private SpongeMatcher<T> parseWithAmountCheck(String string) throws SpongeMatcherFormatException {
        String[] split = string.split(";");
        if (split.length > 1) {
            Set<SpongeMatcher<T>> matcherAmount = Sets.newHashSetWithExpectedSize(split.length);;
            for (String part : split) {
                Matcher matcher = acceptanceRegex.matcher(part);
                matcherAmount.add(parse(matcher));
            }
            return BaseMatchers.amount(matcherAmount);
        } else {
            Matcher matcher = acceptanceRegex.matcher(string);
            return parse(matcher);
        }
    }
    
    protected abstract SpongeMatcher<T> parse(Matcher matcher) throws SpongeMatcherFormatException;

    public Pattern getAcceptanceRegex() {
        return acceptanceRegex;
    }
}
