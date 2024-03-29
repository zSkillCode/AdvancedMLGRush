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

@Getter
public enum EventListenerPriority {
    LOW(1),
    MEDIUM(2),
    HIGH(3);

    private final int priority;

    EventListenerPriority(final int priority) {
        this.priority = priority;
    }
}
