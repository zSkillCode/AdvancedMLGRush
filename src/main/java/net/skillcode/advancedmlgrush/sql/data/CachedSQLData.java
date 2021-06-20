/*
 * Copyright (c) 2021 SkillCode
 *
 * This class is a part of the source code of the
 * AdvancedMLGRush plugin from SkillCode.
 *
 * This class may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: https://discord.skillplugins.com
 */

package net.skillcode.advancedmlgrush.sql.data;

import lombok.Data;

@Data
public class CachedSQLData {

    public static final CachedSQLData DEFAULT_SQL_DATA = new CachedSQLData();

    private boolean defaultData = true;

    private int settingsStickSlot = 0;
    private int settingsBlockSlot = 1;
    private int settingsPickaxeSlot = 8;
    private int settingsArena = -1;
    private int settingsRounds = 5;
    private int gadgetsStick = 0;
    private int gadgetsBlocks = 0;
    private int statsWins = 0;
    private int statsLoses = 0;
    private int statsBeds = 0;
    private int ranking = -1;

    public void increaseWins() {
        statsWins++;
    }

    public void increaseLoses() {
        statsLoses++;
    }

    public void increaseBeds() {
        statsBeds++;
    }


}