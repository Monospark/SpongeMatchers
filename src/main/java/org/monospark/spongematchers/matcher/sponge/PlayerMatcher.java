package org.monospark.spongematchers.matcher.sponge;

import java.util.Map;

import org.monospark.spongematchers.matcher.SpongeMatcher;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.data.type.HandType;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.SubjectData;

import com.google.common.collect.Maps;

public final class PlayerMatcher extends LivingMatcher<Player> {

    public static SpongeMatcher<Player> createPlayerMatcher(SpongeMatcher<Map<String, Object>> matcher) {
        return new PlayerMatcher(matcher);
    }

    private PlayerMatcher(SpongeMatcher<Map<String, Object>> matcher) {
        super(MatcherType.PLAYER, matcher);
    }

    @Override
    protected void fillMap(Player o, Map<String, Object> map) {
        super.fillMap(o, map);
        map.put("name", o.getName());
        map.put("uuid", o.getUniqueId().toString());
        map.put("permissions", getPlayerPermissions(o));
        map.put("gamemode", o.gameMode().get().getId().toLowerCase());
        map.put("helmet", o.getHelmet());
        map.put("chestplate", o.getChestplate());
        map.put("leggings", o.getLeggings());
        map.put("boots", o.getBoots());
        map.put("itemInMainHand", o.getItemInHand(HandTypes.MAIN_HAND));
        map.put("itemInOffHand", o.getItemInHand(HandTypes.OFF_HAND));
    }

    private Map<String, Boolean> getPlayerPermissions(Player p) {
        Map<String, Boolean> permissions = Maps.newHashMap();
        addPermissions(p, permissions);
        return permissions;
    }

    private void addPermissions(Subject subject, Map<String, Boolean> permissions) {
        permissions.putAll(subject.getSubjectData().getPermissions(SubjectData.GLOBAL_CONTEXT));
        for (Subject parent : subject.getSubjectData().getParents(SubjectData.GLOBAL_CONTEXT)) {
            permissions.putAll(parent.getSubjectData().getPermissions(SubjectData.GLOBAL_CONTEXT));
            addPermissions(parent, permissions);
        }
    }
}
