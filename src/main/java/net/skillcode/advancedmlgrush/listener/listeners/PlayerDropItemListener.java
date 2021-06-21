/*
 * Copyright (c) 2021 SkillCode
 *
 * This file is a part of the source code of the
 * AdvancedMLGRush plugin by SkillCode.
 *
 * This class may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: https://discord.skillplugins.com
 */

package net.skillcode.advancedmlgrush.listener.listeners;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.event.EventManager;
import net.skillcode.advancedmlgrush.game.buildmode.BuildModeManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.jetbrains.annotations.NotNull;

@Singleton
public class PlayerDropItemListener implements Listener {

    @Inject
    private EventManager eventManager;
    @Inject
    private BuildModeManager buildModeManager;

    @EventHandler
    public void onClick(final @NotNull PlayerDropItemEvent event) {
        final Player player = event.getPlayer();
        if (!buildModeManager.isInBuildMode(player)) {
            event.setCancelled(true);
        }

        eventManager.callEvent(event);
    }

}