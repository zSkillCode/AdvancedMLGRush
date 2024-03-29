/*
 * Copyright (c) 2021 SkillCode
 *
 * This file is a part of the source code of the
 * AdvancedMLGRush plugin by SkillCode.
 *
 * This file may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: http://discord.skillplugins.com
 */

package com.skillplugins.advancedmlgrush.item;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.config.configs.ItemMaterialConfig;
import com.skillplugins.advancedmlgrush.config.configs.ItemNameConfig;
import com.skillplugins.advancedmlgrush.config.configs.SlotsConfig;
import com.skillplugins.advancedmlgrush.item.builder.IBFactory;
import com.skillplugins.advancedmlgrush.item.builder.MetaType;
import com.skillplugins.advancedmlgrush.item.overwriter.ItemOWManager;
import com.skillplugins.advancedmlgrush.util.Pair;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Singleton
public class ItemManager {

    @Inject
    private ItemNameConfig itemNameConfig;
    @Inject
    private ItemMaterialConfig itemMaterialConfig;
    @Inject
    private ItemOWManager itemOWManager;
    @Inject
    private SlotsConfig slotsConfig;
    @Inject
    private IBFactory ibFactory;


    public ItemStack getItem(final @NotNull Optional<Player> optionalPlayer, final @NotNull EnumItem enumItem) {

        final Optional<ItemStack> optional = itemOWManager.getItem(optionalPlayer, enumItem);
        final Pair<Material, Integer> pair = getConfigMaterial(enumItem);

        return optional.orElseGet(() -> ibFactory.create(MetaType.ITEM_META, pair.getValue())
                .name(itemNameConfig.getString(optionalPlayer, enumItem.getConfigPath()))
                .material(pair.getKey())
                .unbreakable()
                .hideAttributes()
                .hideEnchants()
                .hideUnbreakable()
                .build());
    }

    public String getItemName(final @NotNull Optional<Player> optionalPlayer, final @NotNull EnumItem enumItem) {
        return itemNameConfig.getString(optionalPlayer, enumItem);
    }

    public void setItem(final @NotNull Inventory inventory, final @NotNull Optional<Player> optionalPlayer,
                        final @NotNull EnumItem enumItem) {
        final int slot = getItemSlot(enumItem);
        if (slot >= 0
                && slot < inventory.getSize()) {
            inventory.setItem(slot, getItem(optionalPlayer, enumItem));
        }
    }

    public int getItemSlot(final @NotNull EnumItem enumItem) {
        return slotsConfig.getInt(enumItem.getConfigPath());
    }

    private Pair<Material, Integer> getConfigMaterial(final @NotNull EnumItem enumItem) {
        return itemMaterialConfig.getMaterial(enumItem);
    }

}
