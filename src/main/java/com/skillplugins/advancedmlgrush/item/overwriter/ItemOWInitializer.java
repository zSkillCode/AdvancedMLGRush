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

package com.skillplugins.advancedmlgrush.item.overwriter;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.item.overwriter.overwriters.RankingOW;
import com.skillplugins.advancedmlgrush.item.overwriter.overwriters.StatsItemOW;
import com.skillplugins.advancedmlgrush.util.Initializer;
import org.jetbrains.annotations.NotNull;

@Singleton
public class ItemOWInitializer implements Initializer {

    private final ItemOWManager itemOWManager;

    @Inject
    public ItemOWInitializer(final @NotNull ItemOWManager itemOWManager) {
        this.itemOWManager = itemOWManager;
    }


    @Override
    public void init(final @NotNull Injector injector) {
        itemOWManager.registerItemOW(injector.getInstance(StatsItemOW.class));
        itemOWManager.registerItemOW(injector.getInstance(RankingOW.class));
    }
}
