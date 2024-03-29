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

package com.skillplugins.advancedmlgrush.inventory;

import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.miscellaneous.registrable.Registrable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class InventoryManager implements Registrable {

    private final Map<Player, Class<? extends AbstractInventory>> inventoryMap = new ConcurrentHashMap<>();

    public void register(final @NotNull Player player, final @NotNull Class<? extends AbstractInventory> inventoryClass) {
        inventoryMap.put(player, inventoryClass);
    }

    @Override
    public void unregister(final @NotNull Player player) {
        inventoryMap.remove(player);
    }

    public Optional<Class<? extends AbstractInventory>> getOpenInventory(final @NotNull Player player) {
        return Optional.ofNullable(inventoryMap.getOrDefault(player, null));
    }
}
