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

package com.skillplugins.advancedmlgrush.game.map.setup.step;

import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.annotations.PostConstruct;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Singleton
public abstract class SetupSteps {

    final List<SetupStep> setupSteps = new CopyOnWriteArrayList<>();

    @PostConstruct
    public void init() {
        registerSteps();
    }

    public <E> Optional<SetupStep<E>> getSetupStep(final int index) {
        if (index < setupSteps.size()) {
            return Optional.of(setupSteps.get(index));
        }
        return Optional.empty();
    }

    abstract void registerSteps();

    protected void registerStep(final @NotNull SetupStep<?> setupStep) {
        setupSteps.add(setupStep);
    }

}
