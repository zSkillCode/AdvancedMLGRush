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

package com.skillplugins.advancedmlgrush.item.items;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.config.configs.MainConfig;
import com.skillplugins.advancedmlgrush.game.gadgets.GadgetManager;
import com.skillplugins.advancedmlgrush.item.EnumItem;
import com.skillplugins.advancedmlgrush.item.ItemManager;
import com.skillplugins.advancedmlgrush.lib.xseries.XEnchantment;
import com.skillplugins.advancedmlgrush.sql.data.SQLDataCache;
import com.skillplugins.advancedmlgrush.util.Pair;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Singleton
public class IngameItems {

    @Inject
    private ItemManager itemManager;
    @Inject
    private SQLDataCache sqlDataCache;
    @Inject
    private GadgetManager gadgetManager;
    @Inject
    private MainConfig mainConfig;

    public Pair<ItemStack, Integer> getStick(final @NotNull Player player) {
        final ItemStack stick = gadgetManager.getStickAsBuilder(player).build();
        return new Pair<>(stick, sqlDataCache.getSQLData(player).getSettingsStickSlot());
    }

    public Pair<ItemStack, Integer> getBlock(final @NotNull Player player) {
        final ItemStack block = gadgetManager.getBlockAsBuilder(player).build();
        return new Pair<>(block, sqlDataCache.getSQLData(player).getSettingsBlockSlot());
    }

    public Pair<ItemStack, Integer> getPickaxe(final @NotNull Player player) {
        final ItemStack pickaxe = itemManager.getItem(Optional.of(player), EnumItem.PICKAXE);
        pickaxe.addUnsafeEnchantment(XEnchantment.DIG_SPEED.parseEnchantment(), mainConfig.getInt(MainConfig.PICKAXE_EFFICIENCY_LEVEL));
        return new Pair<>(pickaxe, sqlDataCache.getSQLData(player).getSettingsPickaxeSlot());
    }

    public void setIngameItems(final @NotNull Player player) {
        final Pair<ItemStack, Integer> stick = getStick(player);
        final Pair<ItemStack, Integer> block = getBlock(player);
        final Pair<ItemStack, Integer> pickaxe = getPickaxe(player);

        final ItemStack blocks = block.getKey();
        blocks.setAmount(mainConfig.getInt(MainConfig.BLOCK_AMOUNT));
        player.getInventory().setItem(stick.getValue(), stick.getKey());
        player.getInventory().setItem(block.getValue(), block.getKey());
        player.getInventory().setItem(pickaxe.getValue(), pickaxe.getKey());
    }

    public void setSpectatorItems(final @NotNull Player player) {
        itemManager.setItem(player.getInventory(), Optional.of(player), EnumItem.SPECTATE_LEAVE);
    }
}
