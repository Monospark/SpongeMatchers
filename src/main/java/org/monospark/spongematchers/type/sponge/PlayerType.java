package org.monospark.spongematchers.type.sponge;

import org.monospark.spongematchers.matcher.sponge.PlayerMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.monospark.spongematchers.type.advanced.FixedMapType.Builder;
import org.spongepowered.api.entity.living.player.Player;

public final class PlayerType extends LivingType<Player> {

    public PlayerType() {
        super("player", Player.class, m -> PlayerMatcher.createPlayerMatcher(m));
    }

    @Override
    protected void addAdditionalEntries(Builder builder) {
        super.addAdditionalEntries(builder);
        builder
                .addEntry("name", MatcherType.STRING)
                .addEntry("uuid", MatcherType.STRING)
                .addEntry("permissions", MatcherType.variableMap(MatcherType.BOOLEAN))
                .addEntry("gamemode", MatcherType.STRING)
                .addEntry("helmet", MatcherType.optional(MatcherType.ITEM_STACK))
                .addEntry("chestplate", MatcherType.optional(MatcherType.ITEM_STACK))
                .addEntry("leggings", MatcherType.optional(MatcherType.ITEM_STACK))
                .addEntry("boots", MatcherType.optional(MatcherType.ITEM_STACK))
                .addEntry("itemInMainHand", MatcherType.optional(MatcherType.ITEM_STACK))
                .addEntry("itemInOffHand", MatcherType.optional(MatcherType.ITEM_STACK));
    }
}
