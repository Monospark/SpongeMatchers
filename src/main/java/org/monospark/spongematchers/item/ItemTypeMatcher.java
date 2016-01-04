package org.monospark.spongematchers.item;

import java.util.Objects;

import org.monospark.spongematchers.SpongeMatcher;
import org.monospark.spongematchers.base.NameMatcher;
import org.spongepowered.api.item.ItemType;

public final class ItemTypeMatcher implements SpongeMatcher<ItemType> {

    private NameMatcher modName;
    
    private NameMatcher typeName;
    
    public ItemTypeMatcher(NameMatcher modName, NameMatcher typeName) {
        this.modName = Objects.requireNonNull(modName);
        this.typeName = Objects.requireNonNull(typeName);
    }

    @Override
    public boolean matches(ItemType o) {
        String[] split = o.getName().split(":");
        String modName = split[0];
        String typeName = split[1];
        return this.modName.matches(modName) && this.typeName.matches(typeName);
    }
}
