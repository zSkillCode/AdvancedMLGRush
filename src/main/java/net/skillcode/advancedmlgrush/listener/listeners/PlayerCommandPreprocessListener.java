/*
 * Copyright (c) 2021 SkillCode
 *
 * This file is a part of the source code of the
 * AdvancedMLGRush plugin by SkillCode.
 *
 * This file may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: https://discord.skillplugins.com
 */

package net.skillcode.advancedmlgrush.listener.listeners;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.config.configs.MessageConfig;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import net.skillcode.advancedmlgrush.placeholder.Placeholders;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Optional;

@Singleton
public class PlayerCommandPreprocessListener implements Listener {

    @Inject
    private MessageConfig messageConfig;
    @Inject
    private JavaPlugin javaPlugin;
    @Inject
    private Placeholders placeholders;

    @Inject
    public PlayerCommandPreprocessListener(final @NotNull MessageConfig messageConfig) {
        this.messageConfig = messageConfig;
    }

    @EventHandler
    public void onCommand(final @NotNull PlayerCommandPreprocessEvent event) {
        final String message = event.getMessage().toLowerCase(Locale.ROOT);
        if (message.startsWith("/mlgrush")
                || message.startsWith("/advancedmlgrush")) {
            final Player player = event.getPlayer();
            player.sendMessage(placeholders.replace(Optional.of(player),
                    String.format(messageConfig.getString(MessageConfig.PREFIX) + Constants.MLGRUSH_COMMAND_MESSAGE,
                            javaPlugin.getDescription().getVersion())));
            event.setCancelled(true);
        }
    }

}