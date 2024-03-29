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

package com.skillplugins.advancedmlgrush.placeholder;

import com.google.inject.Inject;
import com.skillplugins.advancedmlgrush.config.configs.MainConfig;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public abstract class Placeholder {

    @Inject
    protected MainConfig mainConfig;

    public abstract String identifier();

    public abstract String onRequest(final @NotNull Optional<Player> optionalPlayer);

    protected String getLoadingValue() {
        return mainConfig.getString(MainConfig.PLACEHOLDER_LOADING_VALUE);
    }

    protected String getNullValue() {
        return mainConfig.getString(MainConfig.PLACEHOLDER_NULL_VALUE);
    }

    public String getRandomValue() {
        return mainConfig.getString(MainConfig.PLACEHOLDER_RANDOM_VALUE);
    }

}
