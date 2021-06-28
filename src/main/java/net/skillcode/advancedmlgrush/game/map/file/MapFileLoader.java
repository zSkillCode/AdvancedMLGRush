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

package net.skillcode.advancedmlgrush.game.map.file;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import net.skillcode.advancedmlgrush.util.json.utils.JsonUtils;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

@Singleton
public class MapFileLoader {

    private final JavaPlugin javaPlugin;

    @Getter
    private final List<MapFile> mapFiles = new CopyOnWriteArrayList<>();

    @Inject
    public MapFileLoader(final @NotNull JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }


    public void loadFilesIfExists() {
        mapFiles.clear();
        final File directory = new File(Constants.MAP_PATH);
        if (directory.exists()) {
            if (directory.exists()) {
                for (final File file : directory.listFiles()) {
                    final MapFile mapFile = JsonUtils.getFromFile(MapFile.class, file);
                    if (mapFile != null) {
                        mapFiles.add(mapFile);
                    }
                }
            }
        }
    }

    public void createMapFile(final @NotNull MapFile mapFile) {
        final String filePath = Constants.MAP_PATH + mapFile.getName().toLowerCase(Locale.ROOT).replace(" ", "_");
        final File file = new File(filePath);

        if (!file.exists()) {
            JsonUtils.toFile(mapFile, filePath, true);
        }
    }
}
