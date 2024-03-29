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

package com.skillplugins.advancedmlgrush.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public abstract class EventListener<E extends Event> {

    private final Class<? extends Event> eventClass;
    private final EventListenerPriority eventListenerPriority;

    protected abstract void onEvent(final @NotNull E event);

    public void callEvent(final @NotNull Event event) {
        if (event.getClass().equals(eventClass)) {
            onEvent((E) event);
        }
    }


}
