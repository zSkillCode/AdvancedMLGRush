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

package com.skillplugins.advancedmlgrush.placeholder.placeholders.stats.ranking;

import com.google.inject.Inject;
import com.skillplugins.advancedmlgrush.game.stats.Ranking;
import com.skillplugins.advancedmlgrush.placeholder.Placeholder;
import com.skillplugins.advancedmlgrush.sql.data.SQLDataCache;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class RankingPlaceholder extends Placeholder {

    private final SQLDataCache sqlDataCache;
    private final Ranking ranking;

    @Inject
    public RankingPlaceholder(final @NotNull SQLDataCache sqlDataCache, final @NotNull Ranking ranking) {
        this.sqlDataCache = sqlDataCache;
        this.ranking = ranking;
    }

    @Override
    public String identifier() {
        return "%stats_ranking%";
    }

    @Override
    public String onRequest(final @NotNull Optional<Player> optionalPlayer) {
        if (!optionalPlayer.isPresent()) {
            return getNullValue();
        }
        final Player player = optionalPlayer.get();

        if (!sqlDataCache.isLoaded(player)) {
            return getLoadingValue();
        }
        return String.valueOf(ranking.getRankingByName(player));
    }

}
