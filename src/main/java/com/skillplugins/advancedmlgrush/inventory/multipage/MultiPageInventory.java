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

package com.skillplugins.advancedmlgrush.inventory.multipage;

import com.google.inject.Inject;
import com.skillplugins.advancedmlgrush.annotations.PostConstruct;
import com.skillplugins.advancedmlgrush.config.configs.ItemNameConfig;
import com.skillplugins.advancedmlgrush.event.EventListener;
import com.skillplugins.advancedmlgrush.event.EventListenerPriority;
import com.skillplugins.advancedmlgrush.inventory.AbstractInventory;
import com.skillplugins.advancedmlgrush.item.EnumItem;
import com.skillplugins.advancedmlgrush.miscellaneous.Constants;
import com.skillplugins.advancedmlgrush.util.Pair;
import com.skillplugins.advancedmlgrush.util.SkullUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class MultiPageInventory extends AbstractInventory {

    private final List<Integer> elementSlots = new ArrayList<>();
    private final Map<Player, MultiPageHelper> multiPageHelperMap = new ConcurrentHashMap<>();

    @Inject
    protected SkullUtils skullUtils;
    @Inject
    protected ItemNameConfig itemNameConfig;
    @Inject
    private MPHFactory mphFactory;

    @PostConstruct
    public void initInventory() {
        super.init();
    }

    protected abstract String title();

    protected abstract LinkedHashMap<ItemStack, Object> onOpen(final @NotNull Player player);

    protected abstract void onElementClick(final Player player, final @NotNull Optional<Object> optional);

    //override
    protected Inventory modifyInventory(final @NotNull Inventory inventory) {
        return inventory;
    }

    @Override
    protected Pair<Inventory, String> onCreate() {
        final Inventory inventory = Bukkit.createInventory(null, 6 * 9, title());

        final String leftArrowName = itemNameConfig.getString(Optional.empty(), EnumItem.ARROW_LEFT);
        final String rightArrowName = itemNameConfig.getString(Optional.empty(), EnumItem.ARROW_RIGHT);

        inventoryUtils.frame(inventory);
        inventory.setItem(itemManager.getItemSlot(EnumItem.ARROW_LEFT), skullUtils
                .getSkull(Constants.ARROW_LEFT_VALUE, leftArrowName).build());
        inventory.setItem(itemManager.getItemSlot(EnumItem.ARROW_RIGHT), skullUtils
                .getSkull(Constants.ARROW_RIGHT_VALUE, rightArrowName).build());

        elementSlots.addAll(inventoryUtils.getEmptySlots(inventory));
        return new Pair<>(modifyInventory(inventory), title());
    }

    @Override
    protected Inventory onOpen(final @NotNull Inventory inventory, final @NotNull Player player) {
        multiPageHelperMap.put(player, mphFactory.create(inventory, elementSlots, onOpen(player)));
        multiPageHelperMap.get(player).loadFirstPage();
        return inventory;
    }

    @Override
    protected List<EventListener<?>> listeners(final @NotNull List<EventListener<?>> eventListeners) {
        final Class<? extends MultiPageInventory> clazz = this.getClass();
        eventListeners.add(new EventListener<InventoryCloseEvent>(InventoryCloseEvent.class, EventListenerPriority.MEDIUM) {
            @Override
            protected void onEvent(final @NotNull InventoryCloseEvent event) {
                final Player player = (Player) event.getPlayer();
                if (inventoryUtils.isOpenInventory(player, clazz)) {
                    multiPageHelperMap.remove(player);
                }
            }
        });

        eventListeners.add(new EventListener<InventoryClickEvent>(InventoryClickEvent.class, EventListenerPriority.MEDIUM) {
            @Override
            protected void onEvent(final @NotNull InventoryClickEvent event) {
                final Player player = (Player) event.getWhoClicked();
                if (inventoryUtils.isOpenInventory(player, clazz)) {

                    final MultiPageHelper multiPageHelper = multiPageHelperMap.getOrDefault(player, null);
                    final int slot = event.getSlot();

                    if (slot == itemManager.getItemSlot(EnumItem.ARROW_LEFT)) {
                        if (multiPageHelper.loadPreviousPage()) {
                            inventoryUtils.playClickSound(player);
                        }
                    } else if (slot == itemManager.getItemSlot(EnumItem.ARROW_RIGHT)) {
                        if (multiPageHelper.loadNextPage()) {
                            inventoryUtils.playClickSound(player);
                        }
                    } else if (elementSlots.contains(slot)
                            && itemUtils.isValidItem(event.getCurrentItem())) {
                        onElementClick(player, multiPageHelper.getElement(slot));
                    }
                }
            }
        });

        eventListeners.addAll(registerCustomListeners(eventListeners));
        return eventListeners;
    }

    protected List<EventListener<?>> registerCustomListeners(final @NotNull List<EventListener<?>> listeners) {
        return listeners;
    }

    @Override
    protected boolean cloneOnOpen() {
        return true;
    }
}
