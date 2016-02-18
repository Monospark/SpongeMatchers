package org.monospark.spongematchers.type;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.parser.SpongeMatcherParseException;
import org.monospark.spongematchers.parser.base.BaseMatcherParser;
import org.monospark.spongematchers.parser.element.ConnectedElement;
import org.monospark.spongematchers.parser.element.ConnectedElement.Operator;
import org.monospark.spongematchers.parser.element.LiteralElement;
import org.monospark.spongematchers.parser.element.PatternElement;
import org.monospark.spongematchers.parser.element.PatternElement.Type;
import org.monospark.spongematchers.parser.element.StringElement;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.data.property.PropertyHolder;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;

import com.google.common.collect.Sets;

public abstract class MatcherType<T> {

    public static final MatcherType<Boolean> BOOLEAN = new BaseType<>(BaseMatcherParser.BOOLEAN);

    public static final MatcherType<Long> INTEGER = new BaseType<>(BaseMatcherParser.INTEGER);

    public static final MatcherType<Double> FLOATING_POINT = new BaseType<>(BaseMatcherParser.FLOATING_POINT);

    public static final MatcherType<String> STRING = new BaseType<>(BaseMatcherParser.STRING);

    public static final MatcherType<DataView> DATA_VIEW = new DataViewType();

    public static final MatcherType<PropertyHolder> PROPERTY_HOLDER = new PropertyHolderType();

    public static final MatcherType<ItemStack> ITEM_STACK = new ItemStackType();

    public static final MatcherType<ItemEnchantment> ITEM_ENCHANTMENT = new ItemEnchantmentType();

    public static final MatcherType<org.spongepowered.api.block.BlockType> BLOCK_TYPE = new BlockTypeType();

    public static final MatcherType<BlockState> BLOCK_STATE = new BlockStateType();

    public static final MatcherType<Location<?>> BIOME_LOCATION = new BiomeLocationType();

    public static final MatcherType<Location<?>> BLOCK_LOCATION = new BlockLocationType();

    public static final MatcherType<Location<?>> POSITION_LOCATION = new PositionLocationType();

    public static final MatcherType<BlockSnapshot> BLOCK = new BlockType();

    public static <T> MatcherType<List<T>> list(MatcherType<T> type) {
        return new ListType<T>(type);
    }

    public static DefinedMapType.Builder definedMap() {
        return DefinedMapType.builder();
    }

    public static UndefinedMapType.Builder undefinedMap() {
        return UndefinedMapType.builder();
    }

    public static <T> MatcherType<Optional<T>> optional(MatcherType<T> type) {
        return new OptionalType<T>(type);
    }

    private String name;

    protected MatcherType(String name) {
        this.name = name;
    }

    public abstract boolean canMatch(Object o);

    public final SpongeMatcher<T> parseMatcher(StringElement element) throws SpongeMatcherParseException {
        if (element instanceof ConnectedElement) {
            ConnectedElement con = (ConnectedElement) element;
            Set<SpongeMatcher<T>> matchers = Sets.newHashSet();
            for (StringElement e : con.getElements()) {
                matchers.add(parseMatcher(e));
            }
            return con.getOperator() == Operator.AND ? SpongeMatcher.and(matchers) : SpongeMatcher.or(matchers);
        } else if (element instanceof PatternElement) {
            PatternElement p = (PatternElement) element;
            if (p.getType() == Type.NOT) {
                return SpongeMatcher.not(parseMatcher(p.getElement()));
            } else if (p.getType() == Type.PARANTHESES) {
                return parseMatcher(p.getElement());
            }
        } else if (element instanceof LiteralElement) {
            LiteralElement l = (LiteralElement) element;
            if (l.getType() == LiteralElement.Type.WILDCARD) {
                return SpongeMatcher.wildcard();
            }
        }

        if (!canParse(element, false)) {
            throw new SpongeMatcherParseException("Invalid " + name + " matcher: " + element.getString());
        }

        return parse(element);
    }

    public final boolean canParseMatcher(StringElement element, boolean deep) {
        if (element instanceof ConnectedElement) {
            ConnectedElement con = (ConnectedElement) element;
            for (StringElement e : con.getElements()) {
                if (!canParseMatcher(e, deep)) {
                    return false;
                }
            }
            return true;
        } else if (element instanceof PatternElement) {
            PatternElement p = (PatternElement) element;
            if (p.getType() == Type.NOT || p.getType() == Type.PARANTHESES) {
                return canParseMatcher(p.getElement(), deep);
            }
        } else if (element instanceof LiteralElement) {
            LiteralElement l = (LiteralElement) element;
            return l.getType() == LiteralElement.Type.WILDCARD;
        }

        return canParse(element, deep);
    }

    protected abstract boolean canParse(StringElement element, boolean deep);

    protected abstract SpongeMatcher<T> parse(StringElement element) throws SpongeMatcherParseException;

    public final String getName() {
        return name;
    }
}
