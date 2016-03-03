package org.monospark.spongematchers.type;

import java.util.List;
import java.util.Map;
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
import org.monospark.spongematchers.type.advanced.FixedMapType;
import org.monospark.spongematchers.type.advanced.ListType;
import org.monospark.spongematchers.type.advanced.MultiType;
import org.monospark.spongematchers.type.advanced.OptionalType;
import org.monospark.spongematchers.type.advanced.VariableMapType;
import org.monospark.spongematchers.type.base.BaseType;
import org.monospark.spongematchers.type.sponge.BiomeLocationType;
import org.monospark.spongematchers.type.sponge.BlockLocationType;
import org.monospark.spongematchers.type.sponge.BlockStateType;
import org.monospark.spongematchers.type.sponge.BlockType;
import org.monospark.spongematchers.type.sponge.BlockTypeType;
import org.monospark.spongematchers.type.sponge.DataViewType;
import org.monospark.spongematchers.type.sponge.DimensionType;
import org.monospark.spongematchers.type.sponge.EntityType;
import org.monospark.spongematchers.type.sponge.ItemEnchantmentType;
import org.monospark.spongematchers.type.sponge.ItemStackType;
import org.monospark.spongematchers.type.sponge.LivingType;
import org.monospark.spongematchers.type.sponge.PlayerType;
import org.monospark.spongematchers.type.sponge.PositionLocationType;
import org.monospark.spongematchers.type.sponge.PropertyHolderType;
import org.monospark.spongematchers.type.sponge.WorldType;
import org.monospark.spongematchers.util.GenericsHelper;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.data.property.PropertyHolder;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Dimension;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.google.common.collect.Sets;

public abstract class MatcherType<T> {

    public static final MatcherType<Boolean> BOOLEAN = new BaseType<>(BaseMatcherParser.BOOLEAN);

    public static final MatcherType<Long> INTEGER = new BaseType<Long>(BaseMatcherParser.INTEGER);

    public static final MatcherType<Double> FLOATING_POINT = new BaseType<>(BaseMatcherParser.FLOATING_POINT);

    public static final MatcherType<String> STRING = new BaseType<>(BaseMatcherParser.STRING);


    public static final MatcherType<DataView> DATA_VIEW = new DataViewType();

    public static final MatcherType<PropertyHolder> PROPERTY_HOLDER = new PropertyHolderType();


    public static final MatcherType<ItemStack> ITEM_STACK = new ItemStackType();

    public static final MatcherType<ItemEnchantment> ITEM_ENCHANTMENT = new ItemEnchantmentType();


    public static final MatcherType<Dimension> DIMENSION = new DimensionType();

    public static final MatcherType<World> WORLD = new WorldType();


    public static final MatcherType<Location<World>> BIOME_LOCATION = new BiomeLocationType();

    public static final MatcherType<Location<World>> BLOCK_LOCATION = new BlockLocationType();

    public static final MatcherType<Location<World>> POSITION_LOCATION = new PositionLocationType();


    public static final MatcherType<org.spongepowered.api.block.BlockType> BLOCK_TYPE = new BlockTypeType();

    public static final MatcherType<BlockState> BLOCK_STATE = new BlockStateType();

    public static final MatcherType<BlockSnapshot> BLOCK = new BlockType();


    public static final MatcherType<Entity> ENTITY = new EntityType<Entity>();

    public static final MatcherType<Living> LIVING = new LivingType<Living>();

    public static final MatcherType<Player> PLAYER = new PlayerType();

    public static final MatcherType<Entity> ANY_ENTITY = GenericsHelper.genericWrapper(multi()
            .addType(ENTITY)
            .addType(LIVING)
            .addType(PLAYER)
            .build());

    public static final MatcherType<Entity> ANY_LIVING = GenericsHelper.genericWrapper(multi()
            .addType(LIVING)
            .addType(PLAYER)
            .build());

    public static <T> MatcherType<List<T>> list(MatcherType<T> type) {
        return new ListType<T>(type);
    }

    public static FixedMapType.Builder fixedMap() {
        return FixedMapType.builder();
    }

    public static MatcherType<Map<String, Object>> variableMap(MatcherType<?> type) {
        return new VariableMapType(type);
    }

    public static <T> MatcherType<Optional<T>> optional(MatcherType<T> type) {
        return new OptionalType<T>(type);
    }

    public static <T> MultiType.Builder multi() {
        return MultiType.builder();
    }

    protected MatcherType() {}

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
                return SpongeMatcher.wildcard(this);
            }
        }

        return parse(element);
    }

    public final boolean canParseMatcher(StringElement element) {
        if (element instanceof ConnectedElement) {
            ConnectedElement con = (ConnectedElement) element;
            for (StringElement e : con.getElements()) {
                if (!canParseMatcher(e)) {
                    return false;
                }
            }
            return true;
        } else if (element instanceof PatternElement) {
            PatternElement p = (PatternElement) element;
            if (p.getType() == Type.NOT || p.getType() == Type.PARANTHESES) {
                return canParseMatcher(p.getElement());
            }
        } else if (element instanceof LiteralElement) {
            LiteralElement l = (LiteralElement) element;
            if (l.getType() == LiteralElement.Type.WILDCARD) {
                return true;
            }
        }

        return canParse(element);
    }

    protected abstract boolean canParse(StringElement element);

    protected abstract SpongeMatcher<T> parse(StringElement element) throws SpongeMatcherParseException;
}
