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

package com.skillplugins.advancedmlgrush.command.commands;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.config.configs.MainConfig;
import com.skillplugins.advancedmlgrush.config.configs.MessageConfig;
import com.skillplugins.advancedmlgrush.game.map.setup.MapSetup2x1;
import com.skillplugins.advancedmlgrush.game.map.setup.MapSetup4x1;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

@Singleton
public class SetupMapCommand implements CommandExecutor {

    @Inject
    private MapSetup2x1 mapSetup2X1;
    @Inject
    private MapSetup4x1 mapSetup4X1;
    @Inject
    private MessageConfig messageConfig;
    @Inject
    private MainConfig mainConfig;

    @Override
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] args) {
        if (!(commandSender instanceof Player)) return false;

        final Player player = (Player) commandSender;
        final Optional<Player> optionalPlayer = Optional.of(player);

        if (!player.hasPermission(mainConfig.getString(MainConfig.ADMIN_PERMISSION))) {
            player.sendMessage(messageConfig.getWithPrefix(optionalPlayer, MessageConfig.NO_PERMISSION));
            return false;
        }

        if (mapSetup2X1.isSettingUp(player)
                || mapSetup4X1.isSettingUp(player)) {
            player.sendMessage(messageConfig.getWithPrefix(optionalPlayer, MessageConfig.ALREADY_SETTING_UP_MAP));
            return false;
        }

        if (args.length < 2) {
            player.sendMessage(messageConfig.getWithPrefix(optionalPlayer, MessageConfig.SETUP_MAP_COMMAND_SYNTAX));
            return false;
        }

        final String arg = args[0];
        final StringBuilder stringBuilder = new StringBuilder();

        for (int i = 1; i < args.length; i++) {
            stringBuilder.append(args[i]).append(" ");
        }

        final String name = stringBuilder.substring(0, stringBuilder.toString().length() - 1);

        if (arg.equals("2x1")) {
            mapSetup2X1.startSetup(player, name);
        } else if (arg.equals("4x1")) {
            mapSetup4X1.startSetup(player, name);
        } else {
            player.sendMessage(messageConfig.getWithPrefix(optionalPlayer, MessageConfig.SETUP_MAP_COMMAND_SYNTAX));

        }
        return false;
    }
}
