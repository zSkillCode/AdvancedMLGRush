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

package com.skillplugins.advancedmlgrush.sql;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DataSaverParams {

    //config values
    private final boolean mySQL;
    private final String host;
    private final String port;
    private final String database;
    private final String user;
    private final String password;
    //other values
    private final String table;
    private final String dataFilePath;

}
