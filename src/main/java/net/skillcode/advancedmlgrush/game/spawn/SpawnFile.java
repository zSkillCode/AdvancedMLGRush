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

package net.skillcode.advancedmlgrush.game.spawn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.skillcode.advancedmlgrush.util.json.JsonConfig;
import net.skillcode.advancedmlgrush.util.json.JsonLocation;

@Getter
@AllArgsConstructor
public class SpawnFile implements JsonConfig {

    private JsonLocation jsonLocation;

}
