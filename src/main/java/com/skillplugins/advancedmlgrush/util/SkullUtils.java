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

package com.skillplugins.advancedmlgrush.util;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.skillplugins.advancedmlgrush.exception.ExceptionHandler;
import com.skillplugins.advancedmlgrush.item.builder.IBFactory;
import com.skillplugins.advancedmlgrush.item.builder.ItemBuilder;
import com.skillplugins.advancedmlgrush.item.builder.MetaType;
import com.skillplugins.advancedmlgrush.lib.xseries.XMaterial;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class SkullUtils {

    private final Map<String, ItemBuilder> cache = new ConcurrentHashMap<>();

    @Inject
    private IBFactory ibFactory;
    @Inject
    private NMSUtils nmsUtils;
    @Inject
    private ExceptionHandler exceptionHandler;


    public ItemBuilder getSkull(final @NotNull String value, final @NotNull String itemName) {
        if (cache.containsKey(value)) {
            return cache.get(value);
        }

        try {
            final GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "");
            gameProfile.getProperties().put("textures", new Property("textures", value));

            final ItemBuilder itemBuilder = ibFactory.create(MetaType.SKULL_META, 3).material(XMaterial.PLAYER_HEAD.parseMaterial());
            final SkullMeta skullMeta = (SkullMeta) itemBuilder.getItemMeta();
            skullMeta.setDisplayName(itemName);

            final Field profileField = nmsUtils.getOBCClass("inventory.CraftMetaSkull").getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, gameProfile);
            itemBuilder.meta(skullMeta);

            cache.put(value, itemBuilder);
            return itemBuilder;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            exceptionHandler.handleUnexpected(e);
        }
        return ibFactory.create(MetaType.ITEM_META, 0).material(XMaterial.ARROW.parseMaterial());
    }

}
