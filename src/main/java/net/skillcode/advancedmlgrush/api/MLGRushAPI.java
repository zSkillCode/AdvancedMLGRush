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

package net.skillcode.advancedmlgrush.api;

import net.skillcode.advancedmlgrush.event.events.PlayerDataLoadEvent;
import net.skillcode.advancedmlgrush.item.overwriter.ItemOW;
import net.skillcode.advancedmlgrush.placeholder.Placeholder;
import net.skillcode.advancedmlgrush.sql.data.CachedSQLData;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public interface MLGRushAPI {

    /**
     * Registers an ItemOverwriter.
     * <p>
     * ItemOverwriters allow you to overwrite items.
     *
     * @param itemOW the overwriter you want to register.
     */
    void registerItemOW(final @NotNull ItemOW itemOW);

    /**
     * Allows you to register a custom placeholder.
     *
     * @param placeholder the placeholder.
     */
    void registerPlaceholder(final @NotNull Placeholder placeholder);

    /**
     * Returns the SQL data of a player
     * <p>
     * Remember to check with {@link #isLoaded(Player)} if the player has been loaded.
     * If this is not the case, default SQL data will be returned.
     * <p>
     * Use the {@link  PlayerDataLoadEvent} instead of the {@link PlayerJoinEvent}.
     *
     * @param player The player you want to get the data from.
     * @return The SQL data of the player
     */
    CachedSQLData getSQLData(final @NotNull Player player);

    /**
     * @param player the player.
     * @return whether the SQL data of the player has been loaded.
     */
    boolean isLoaded(final @NotNull Player player);


}