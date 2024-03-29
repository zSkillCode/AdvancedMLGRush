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

package com.skillplugins.advancedmlgrush.game.queue;

import com.google.inject.Inject;
import com.skillplugins.advancedmlgrush.annotations.PostConstruct;
import com.skillplugins.advancedmlgrush.config.configs.MainConfig;
import com.skillplugins.advancedmlgrush.config.configs.MessageConfig;
import com.skillplugins.advancedmlgrush.config.configs.SoundConfig;
import com.skillplugins.advancedmlgrush.game.map.MapInstanceManager;
import com.skillplugins.advancedmlgrush.game.map.MapManager;
import com.skillplugins.advancedmlgrush.game.map.MapTemplate;
import com.skillplugins.advancedmlgrush.game.map.MapType;
import com.skillplugins.advancedmlgrush.game.scoreboard.ScoreboardManager;
import com.skillplugins.advancedmlgrush.item.items.LobbyItems;
import com.skillplugins.advancedmlgrush.miscellaneous.registrable.Registrable;
import com.skillplugins.advancedmlgrush.sound.SoundUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Queue implements Registrable {

    private static final List<Queue> instances = new CopyOnWriteArrayList<>();

    private final List<Player> queue = new CopyOnWriteArrayList<>();

    @Inject
    private LobbyItems lobbyItems;
    @Inject
    private MessageConfig messageConfig;
    @Inject
    private SoundUtil soundUtil;
    @Inject
    private MainConfig mainConfig;
    @Inject
    private MapManager mapManager;
    @Inject
    private JavaPlugin javaPlugin;
    @Inject
    private ScoreboardManager scoreboardManager;
    @Inject
    private MapInstanceManager mapInstanceManager;

    @PostConstruct
    public void init() {
        instances.add(this);
    }

    /**
     * @return the number of players that can play on this map
     */
    public int getSize() {
        return queue.size();
    }


    public void register(final @NotNull Player player) {
        final Optional<Player> optionalPlayer = Optional.of(player);
        for (final Queue queue : instances) {
            if (queue.queue.contains(player)) {
                queue.removeFromQueue(player);
                return;
            }
        }

        if (mapInstanceManager.isIngame(player)) {
            player.sendMessage(messageConfig.getWithPrefix(optionalPlayer, MessageConfig.CANNOT_ENTER_QUEUE));
            return;
        }

        if (queue.contains(player)
                || queue.size() >= mapType().getPlayers()) {
            lobbyItems.setLobbyItems(player);
            player.sendMessage(messageConfig.getWithPrefix(optionalPlayer, MessageConfig.ERROR));
        } else {
            player.getInventory().clear();
            lobbyItems.setQueueItems(player);
            queue.add(player);
            scoreboardManager.updateScoreboard();
            player.sendMessage(messageConfig.getWithPrefix(optionalPlayer, MessageConfig.QUEUE_JOIN));

            if (queue.size() == mapType().getPlayers()) {
                queue.forEach(player1 -> player1.getInventory().clear());
                startGame();
                queue.clear();
            }
        }
    }

    @Override
    public void unregister(final @NotNull Player player) {
        queue.remove(player);
    }

    public void removeFromQueue(final @NotNull Player player) {
        if (queue.contains(player)) {
            soundUtil.playSound(player, SoundConfig.QUEUE_LEAVE);
            queue.remove(player);
            player.getInventory().clear();
            player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.QUEUE_LEAVE));
            Bukkit.getScheduler().scheduleSyncDelayedTask(javaPlugin, () -> lobbyItems.setLobbyItems(player), 5);
        }
    }

    abstract MapType mapType();

    private void startGame() {
        final Optional<MapTemplate> mapTemplate = mapManager.getRandomMapTemplate(mapType());
        if (!mapTemplate.isPresent()) {
            queue.forEach(player -> {
                player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.ERROR));
                lobbyItems.setLobbyItems(player);
            });
        } else {
            final int rounds = mainConfig.getInt(MainConfig.DEFAULT_ROUNDS);
            mapTemplate.get().createInstance(new ArrayList<>(queue), rounds);
        }
    }
}
