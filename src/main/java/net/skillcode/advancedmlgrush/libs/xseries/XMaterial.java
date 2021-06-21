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

package net.skillcode.advancedmlgrush.libs.xseries;

import com.google.common.base.Enums;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * <b>XMaterial</b> - Data Values/Pre-flattening<br>
 * 1.13 and above as priority.
 * <p>
 * This class is mainly designed to support {@link ItemStack}. If you want to use it on blocks, you'll have to use
 * <a href="https://github.com/CryptoMorin/XSeries/blob/master/src/main/java/com/cryptomorin/xseries/XBlock.java">XBlock</a>
 * <p>
 * Pre-flattening: https://minecraft.gamepedia.com/Java_Edition_data_values/Pre-flattening
 * Materials: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html
 * Materials (1.12): https://helpch.at/docs/1.12.2/index.html?org/bukkit/Material.html
 * Material IDs: https://minecraft-ids.grahamedgecombe.com/
 * Material Source Code: https://hub.spigotmc.org/stash/projects/SPIGOT/repos/bukkit/browse/src/main/java/org/bukkit/Material.java
 * XMaterial v1: https://www.spigotmc.org/threads/329630/
 * <p>
 * This class will throw a "unsupported material" error if someone tries to use an item with an invalid data value which can only happen in 1.12 servers and below or when the
 * utility is missing a new material in that specific version.
 * To get an invalid item, (aka <a href="https://minecraft.fandom.com/wiki/Missing_Texture_Block">Missing Texture Block</a>) you can use the command
 * <b>/give @p minecraft:dirt 1 10</b> where 1 is the item amount, and 10 is the data value. The material {@link #DIRT} with a data value of {@code 10} doesn't exist.
 *
 * @author Crypto Morin
 * @version 10.0.0
 * @see Material
 * @see ItemStack
 */
public enum XMaterial {
    ACACIA_BOAT("BOAT_ACACIA"),
    ACACIA_BUTTON("WOOD_BUTTON"),
    ACACIA_DOOR("ACACIA_DOOR", "ACACIA_DOOR_ITEM"),
    ACACIA_FENCE,
    ACACIA_FENCE_GATE,
    ACACIA_LEAVES("LEAVES_2"),
    ACACIA_LOG("LOG_2"),
    ACACIA_PLANKS(4, "WOOD"),
    ACACIA_PRESSURE_PLATE("WOOD_PLATE"),
    ACACIA_SAPLING(4, "SAPLING"),
    ACACIA_SIGN("SIGN_POST", "SIGN"),
    ACACIA_SLAB(4, "WOOD_DOUBLE_STEP", "WOOD_STEP", "WOODEN_SLAB"),
    ACACIA_STAIRS,
    ACACIA_TRAPDOOR("TRAP_DOOR"),
    ACACIA_WALL_SIGN("WALL_SIGN"),
    ACACIA_WOOD("LOG_2"),
    ACTIVATOR_RAIL,
    /**
     * https://minecraft.gamepedia.com/Air
     *
     * @see #VOID_AIR
     * @see #CAVE_AIR
     */
    AIR,
    ALLIUM(2, "RED_ROSE"),
    ANCIENT_DEBRIS(16),
    ANDESITE(5, "STONE"),
    ANDESITE_SLAB,
    ANDESITE_STAIRS,
    ANDESITE_WALL,
    ANVIL,
    APPLE,
    ARMOR_STAND,
    ARROW,
    ATTACHED_MELON_STEM(7, "MELON_STEM"),
    ATTACHED_PUMPKIN_STEM(7, "PUMPKIN_STEM"),
    AZURE_BLUET(3, "RED_ROSE"),
    BAKED_POTATO,
    BAMBOO(0, 14),
    BAMBOO_SAPLING(14),
    BANNER(12, "STANDING_BANNER", "BANNER"),
    BARREL(0, 14),
    BARRIER,
    BASALT(16),
    BAT_SPAWN_EGG(65, "MONSTER_EGG"),
    BEACON,
    BEDROCK,
    BEEF("RAW_BEEF"),
    BEEHIVE(15),
    /**
     * Beetroot is a known material in pre-1.13
     */
    BEETROOT("BEETROOT_BLOCK"),
    BEETROOTS("BEETROOT"),
    BEETROOT_SEEDS,
    BEETROOT_SOUP,
    BEE_NEST(15),
    BEE_SPAWN_EGG(15),
    BELL(14),
    BIRCH_BOAT("BOAT_BIRCH"),
    BIRCH_BUTTON("WOOD_BUTTON"),
    BIRCH_DOOR("BIRCH_DOOR", "BIRCH_DOOR_ITEM"),
    BIRCH_FENCE,
    BIRCH_FENCE_GATE,
    BIRCH_LEAVES(2, "LEAVES"),
    BIRCH_LOG(2, "LOG"),
    BIRCH_PLANKS(2, "WOOD"),
    BIRCH_PRESSURE_PLATE("WOOD_PLATE"),
    BIRCH_SAPLING(2, "SAPLING"),
    BIRCH_SIGN("SIGN_POST", "SIGN"),
    BIRCH_SLAB(2, "WOOD_DOUBLE_STEP", "WOOD_STEP", "WOODEN_SLAB"),
    BIRCH_STAIRS("BIRCH_WOOD_STAIRS"),
    BIRCH_TRAPDOOR("TRAP_DOOR"),
    BIRCH_WALL_SIGN("WALL_SIGN"),
    BIRCH_WOOD(2, "LOG"),
    BLACKSTONE(16),
    BLACKSTONE_SLAB(16),
    BLACKSTONE_STAIRS(16),
    BLACKSTONE_WALL(16),
    BLACK_BED(15, "BED_BLOCK", "BED"),
    BLACK_CARPET(15, "CARPET"),
    BLACK_CONCRETE(15, "CONCRETE"),
    BLACK_CONCRETE_POWDER(15, "CONCRETE_POWDER"),
    BLACK_GLAZED_TERRACOTTA(15, 12),
    BLACK_SHULKER_BOX,
    BLACK_TERRACOTTA(15, "STAINED_CLAY"),
    BLAST_FURNACE(0, 14),
    BLAZE_POWDER,
    BLAZE_ROD,
    BLAZE_SPAWN_EGG(61, "MONSTER_EGG"),
    BLUE_BED(11, "BED_BLOCK", "BED"),
    BLUE_CARPET(11, "CARPET"),
    BLUE_CONCRETE(11, "CONCRETE"),
    BLUE_CONCRETE_POWDER(11, "CONCRETE_POWDER"),
    BLUE_GLAZED_TERRACOTTA(11, 12),
    BLUE_ICE(0, 13),
    BLUE_ORCHID(1, "RED_ROSE"),
    BLUE_SHULKER_BOX,
    BLUE_TERRACOTTA(11, "STAINED_CLAY"),
    BONE,
    BONE_BLOCK,
    BONE_MEAL(15, "INK_SACK"),
    BOOK,
    BOOKSHELF,
    BOW,
    BOWL,
    BRAIN_CORAL(13),
    BRAIN_CORAL_BLOCK(13),
    BRAIN_CORAL_FAN(13),
    BRAIN_CORAL_WALL_FAN,
    BREAD,
    BREWING_STAND("BREWING_STAND", "BREWING_STAND_ITEM"),
    BRICK("CLAY_BRICK"),
    BRICKS("BRICKS", "BRICK"),
    BRICK_SLAB(4, "STEP"),
    BRICK_STAIRS,
    BRICK_WALL,
    BROWN_BED(12, "BED_BLOCK", "BED"),
    BROWN_CARPET(12, "CARPET"),
    BROWN_CONCRETE(12, "CONCRETE"),
    BROWN_CONCRETE_POWDER(12, "CONCRETE_POWDER"),
    BROWN_GLAZED_TERRACOTTA(12, 12),
    BROWN_MUSHROOM,
    BROWN_MUSHROOM_BLOCK("BROWN_MUSHROOM", "HUGE_MUSHROOM_1"),
    BROWN_SHULKER_BOX,
    BROWN_TERRACOTTA(12, "STAINED_CLAY"),
    BUBBLE_COLUMN(13),
    BUBBLE_CORAL(13),
    BUBBLE_CORAL_BLOCK(13),
    BUBBLE_CORAL_FAN(13),
    BUBBLE_CORAL_WALL_FAN,
    BUCKET,
    CACTUS,
    CAKE("CAKE_BLOCK"),
    CAMPFIRE(14),
    CARROT("CARROT_ITEM"),
    CARROTS("CARROT"),
    CARROT_ON_A_STICK("CARROT_STICK"),
    CARTOGRAPHY_TABLE(0, 14),
    CARVED_PUMPKIN(1, 13),
    CAT_SPAWN_EGG,
    CAULDRON("CAULDRON", "CAULDRON_ITEM"),
    /**
     * 1.13 tag is not added because it's the same thing as {@link #AIR}
     *
     * @see #VOID_AIR
     */
    CAVE_AIR("AIR"),
    CAVE_SPIDER_SPAWN_EGG(59, "MONSTER_EGG"),
    CHAIN(16),
    CHAINMAIL_BOOTS,
    CHAINMAIL_CHESTPLATE,
    CHAINMAIL_HELMET,
    CHAINMAIL_LEGGINGS,
    CHAIN_COMMAND_BLOCK("COMMAND", "COMMAND_CHAIN"),
    CHARCOAL(1, "COAL"),
    CHEST("LOCKED_CHEST"),
    CHEST_MINECART("STORAGE_MINECART"),
    CHICKEN("RAW_CHICKEN"),
    CHICKEN_SPAWN_EGG(93, "MONSTER_EGG"),
    CHIPPED_ANVIL(1, "ANVIL"),
    CHISELED_NETHER_BRICKS(1, "NETHER_BRICKS"),
    CHISELED_POLISHED_BLACKSTONE(0, 16, "POLISHED_BLACKSTONE"),
    CHISELED_QUARTZ_BLOCK(1, "QUARTZ_BLOCK"),
    CHISELED_RED_SANDSTONE(1, "RED_SANDSTONE"),
    CHISELED_SANDSTONE(1, "SANDSTONE"),
    CHISELED_STONE_BRICKS(3, "SMOOTH_BRICK"),
    CHORUS_FLOWER(0, 9),
    CHORUS_FRUIT(0, 9),
    CHORUS_PLANT(0, 9),
    CLAY("HARD_CLAY"),
    CLAY_BALL,
    CLOCK("WATCH"),
    COAL,
    COAL_BLOCK,
    COAL_ORE,
    COARSE_DIRT(1, "DIRT"),
    COBBLESTONE,
    COBBLESTONE_SLAB(3, "STEP"),
    COBBLESTONE_STAIRS,
    COBBLESTONE_WALL("COBBLE_WALL"),
    COBWEB("WEB"),
    COCOA(15),
    COCOA_BEANS(3, "INK_SACK"),
    COD("RAW_FISH"),
    COD_BUCKET(0, 13),
    COD_SPAWN_EGG(0, 13),
    COMMAND_BLOCK("COMMAND"),
    COMMAND_BLOCK_MINECART("COMMAND_MINECART"),
    /**
     * Unlike redstone torch and redstone lamp... neither REDTONE_COMPARATOR_OFF nor REDSTONE_COMPARATOR_ON
     * are items. REDSTONE_COMPARATOR is.
     *
     * @see #REDSTONE_TORCH
     * @see #REDSTONE_LAMP
     */
    COMPARATOR("REDSTONE_COMPARATOR_OFF", "REDSTONE_COMPARATOR_ON", "REDSTONE_COMPARATOR"),
    COMPASS,
    COMPOSTER(0, 14),
    CONDUIT(0, 13, "BEACON"),
    COOKED_BEEF,
    COOKED_CHICKEN,
    COOKED_COD("COOKED_FISH"),
    COOKED_MUTTON,
    COOKED_PORKCHOP("PORK", "GRILLED_PORK"),
    COOKED_RABBIT,
    COOKED_SALMON(1, "COOKED_FISH"),
    COOKIE,
    CORNFLOWER(4, 14),
    COW_SPAWN_EGG(92, "MONSTER_EGG"),
    CRACKED_NETHER_BRICKS(2, "NETHER_BRICKS"),
    CRACKED_POLISHED_BLACKSTONE_BRICKS(0, 16, "POLISHED_BLACKSTONE_BRICKS"),
    CRACKED_STONE_BRICKS(2, "SMOOTH_BRICK"),
    CRAFTING_TABLE("WORKBENCH"),
    CREEPER_BANNER_PATTERN,
    CREEPER_HEAD(4, "SKULL", "SKULL_ITEM"),
    CREEPER_SPAWN_EGG(50, "MONSTER_EGG"),
    CREEPER_WALL_HEAD(4, "SKULL", "SKULL_ITEM"),
    CRIMSON_BUTTON(16),
    CRIMSON_DOOR(16),
    CRIMSON_FENCE(16),
    CRIMSON_FENCE_GATE(16),
    CRIMSON_FUNGUS(16),
    CRIMSON_HYPHAE(16),
    CRIMSON_NYLIUM(16),
    CRIMSON_PLANKS(16),
    CRIMSON_PRESSURE_PLATE(16),
    CRIMSON_ROOTS(16),
    CRIMSON_SIGN(0, 16, "SIGN_POST"),
    CRIMSON_SLAB(16),
    CRIMSON_STAIRS(16),
    CRIMSON_STEM(16),
    CRIMSON_TRAPDOOR(16),
    CRIMSON_WALL_SIGN(0, 16, "WALL_SIGN"),
    CROSSBOW,
    CRYING_OBSIDIAN(16),
    CUT_RED_SANDSTONE(13),
    CUT_RED_SANDSTONE_SLAB("STONE_SLAB2"),
    CUT_SANDSTONE(13),
    CUT_SANDSTONE_SLAB("STEP"),
    CYAN_BED(9, "BED_BLOCK", "BED"),
    CYAN_CARPET(9, "CARPET"),
    CYAN_CONCRETE(9, "CONCRETE"),
    CYAN_CONCRETE_POWDER(9, "CONCRETE_POWDER"),
    CYAN_GLAZED_TERRACOTTA(9, 12),
    CYAN_SHULKER_BOX,
    CYAN_TERRACOTTA(9, "STAINED_CLAY"),
    DAMAGED_ANVIL(2, "ANVIL"),
    DANDELION("YELLOW_FLOWER"),
    DARK_OAK_BOAT("BOAT_DARK_OAK"),
    DARK_OAK_BUTTON("WOOD_BUTTON"),
    DARK_OAK_DOOR("DARK_OAK_DOOR", "DARK_OAK_DOOR_ITEM"),
    DARK_OAK_FENCE,
    DARK_OAK_FENCE_GATE,
    DARK_OAK_LEAVES(4, "LEAVES", "LEAVES_2"),
    DARK_OAK_LOG(1, "LOG_2"),
    DARK_OAK_PLANKS(5, "WOOD"),
    DARK_OAK_PRESSURE_PLATE("WOOD_PLATE"),
    DARK_OAK_SAPLING(5, "SAPLING"),
    DARK_OAK_SIGN("SIGN_POST", "SIGN"),
    DARK_OAK_SLAB(5, "WOOD_DOUBLE_STEP", "WOOD_STEP", "WOODEN_SLAB"),
    DARK_OAK_STAIRS,
    DARK_OAK_TRAPDOOR("TRAP_DOOR"),
    DARK_OAK_WALL_SIGN("WALL_SIGN"),
    DARK_OAK_WOOD(1, "LOG_2"),
    DARK_PRISMARINE(1, "PRISMARINE"),
    DARK_PRISMARINE_SLAB(13),
    DARK_PRISMARINE_STAIRS(13),
    DAYLIGHT_DETECTOR("DAYLIGHT_DETECTOR_INVERTED"),
    DEAD_BRAIN_CORAL(13),
    DEAD_BRAIN_CORAL_BLOCK(13),
    DEAD_BRAIN_CORAL_FAN(13),
    DEAD_BRAIN_CORAL_WALL_FAN(13),
    DEAD_BUBBLE_CORAL(13),
    DEAD_BUBBLE_CORAL_BLOCK(13),
    DEAD_BUBBLE_CORAL_FAN(13),
    DEAD_BUBBLE_CORAL_WALL_FAN(13),
    DEAD_BUSH,
    DEAD_FIRE_CORAL(13),
    DEAD_FIRE_CORAL_BLOCK(13),
    DEAD_FIRE_CORAL_FAN(13),
    DEAD_FIRE_CORAL_WALL_FAN(13),
    DEAD_HORN_CORAL(13),
    DEAD_HORN_CORAL_BLOCK(13),
    DEAD_HORN_CORAL_FAN(13),
    DEAD_HORN_CORAL_WALL_FAN(13),
    DEAD_TUBE_CORAL(13),
    DEAD_TUBE_CORAL_BLOCK(13),
    DEAD_TUBE_CORAL_FAN(13),
    DEAD_TUBE_CORAL_WALL_FAN(13),
    DEBUG_STICK(0, 13),
    DETECTOR_RAIL,
    DIAMOND,
    DIAMOND_AXE,
    DIAMOND_BLOCK,
    DIAMOND_BOOTS,
    DIAMOND_CHESTPLATE,
    DIAMOND_HELMET,
    DIAMOND_HOE,
    DIAMOND_HORSE_ARMOR("DIAMOND_BARDING"),
    DIAMOND_LEGGINGS,
    DIAMOND_ORE,
    DIAMOND_PICKAXE,
    DIAMOND_SHOVEL("DIAMOND_SPADE"),
    DIAMOND_SWORD,
    DIORITE(3, "STONE"),
    DIORITE_SLAB,
    DIORITE_STAIRS,
    DIORITE_WALL,
    DIRT,
    DISPENSER,
    DOLPHIN_SPAWN_EGG(0, 13),
    DONKEY_SPAWN_EGG(32, "MONSTER_EGG"),
    DRAGON_BREATH("DRAGONS_BREATH"),
    DRAGON_EGG,
    DRAGON_HEAD(5, 9, "SKULL", "SKULL_ITEM"),
    DRAGON_WALL_HEAD(5, "SKULL", "SKULL_ITEM"),
    DRIED_KELP(13),
    DRIED_KELP_BLOCK(13),
    DROPPER,
    DROWNED_SPAWN_EGG(0, 13),
    DYE(0, 14, "INK_SACK", "BONE_MEAL"),
    EGG,
    ELDER_GUARDIAN_SPAWN_EGG(4, "MONSTER_EGG"),
    ELYTRA,
    EMERALD,
    EMERALD_BLOCK,
    EMERALD_ORE,
    ENCHANTED_BOOK,
    ENCHANTED_GOLDEN_APPLE(1, "GOLDEN_APPLE"),
    ENCHANTING_TABLE("ENCHANTMENT_TABLE"),
    ENDERMAN_SPAWN_EGG(58, "MONSTER_EGG"),
    ENDERMITE_SPAWN_EGG(67, "MONSTER_EGG"),
    ENDER_CHEST,
    ENDER_EYE("EYE_OF_ENDER"),
    ENDER_PEARL,
    END_CRYSTAL,
    END_GATEWAY(0, 9),
    END_PORTAL("ENDER_PORTAL"),
    END_PORTAL_FRAME("ENDER_PORTAL_FRAME"),
    END_ROD(0, 9),
    END_STONE("ENDER_STONE"),
    END_STONE_BRICKS("END_BRICKS"),
    END_STONE_BRICK_SLAB(6, "STEP"),
    END_STONE_BRICK_STAIRS("SMOOTH_STAIRS"),
    END_STONE_BRICK_WALL,
    EVOKER_SPAWN_EGG(34, "MONSTER_EGG"),
    EXPERIENCE_BOTTLE("EXP_BOTTLE"),
    FARMLAND("SOIL"),
    FEATHER,
    FERMENTED_SPIDER_EYE,
    FERN(2, "LONG_GRASS"),
    /**
     * For some reasons filled map items are really special.
     * Their data value starts from 0 and every time a player
     * creates a new map that maps data value increases.
     * https://github.com/CryptoMorin/XSeries/issues/91
     */
    FILLED_MAP("MAP"),
    FIRE,
    FIREWORK_ROCKET("FIREWORK"),
    FIREWORK_STAR("FIREWORK_CHARGE"),
    FIRE_CHARGE("FIREBALL"),
    FIRE_CORAL(13),
    FIRE_CORAL_BLOCK(13),
    FIRE_CORAL_FAN(13),
    FIRE_CORAL_WALL_FAN,
    FISHING_ROD,
    FLETCHING_TABLE(0, 14),
    FLINT,
    FLINT_AND_STEEL,
    FLOWER_BANNER_PATTERN,
    FLOWER_POT("FLOWER_POT", "FLOWER_POT_ITEM"),
    FOX_SPAWN_EGG(14),
    /**
     * This special material cannot be obtained as an item.
     */
    FROSTED_ICE(0, 9),
    FURNACE("BURNING_FURNACE"),
    FURNACE_MINECART("POWERED_MINECART"),
    GHAST_SPAWN_EGG(56, "MONSTER_EGG"),
    GHAST_TEAR,
    GILDED_BLACKSTONE(16),
    GLASS,
    GLASS_BOTTLE,
    GLASS_PANE("THIN_GLASS"),
    GLISTERING_MELON_SLICE("SPECKLED_MELON"),
    GLOBE_BANNER_PATTERN,
    GLOWSTONE,
    GLOWSTONE_DUST,
    GOLDEN_APPLE,
    GOLDEN_AXE("GOLD_AXE"),
    GOLDEN_BOOTS("GOLD_BOOTS"),
    GOLDEN_CARROT,
    GOLDEN_CHESTPLATE("GOLD_CHESTPLATE"),
    GOLDEN_HELMET("GOLD_HELMET"),
    GOLDEN_HOE("GOLD_HOE"),
    GOLDEN_HORSE_ARMOR("GOLD_BARDING"),
    GOLDEN_LEGGINGS("GOLD_LEGGINGS"),
    GOLDEN_PICKAXE("GOLD_PICKAXE"),
    GOLDEN_SHOVEL("GOLD_SPADE"),
    GOLDEN_SWORD("GOLD_SWORD"),
    GOLD_BLOCK,
    GOLD_INGOT,
    GOLD_NUGGET,
    GOLD_ORE,
    GRANITE(1, "STONE"),
    GRANITE_SLAB,
    GRANITE_STAIRS,
    GRANITE_WALL,
    GRASS(1, "LONG_GRASS"),
    GRASS_BLOCK("GRASS"),
    GRASS_PATH,
    GRAVEL,
    GRAY_BED(7, "BED_BLOCK", "BED"),
    GRAY_CARPET(7, "CARPET"),
    GRAY_CONCRETE(7, "CONCRETE"),
    GRAY_CONCRETE_POWDER(7, "CONCRETE_POWDER"),
    GRAY_GLAZED_TERRACOTTA(7, 12),
    GRAY_SHULKER_BOX,
    GRAY_TERRACOTTA(7, "STAINED_CLAY"),
    GREEN_BED(13, "BED_BLOCK", "BED"),
    GREEN_CARPET(13, "CARPET"),
    GREEN_CONCRETE(13, "CONCRETE"),
    GREEN_CONCRETE_POWDER(13, "CONCRETE_POWDER"),
    GREEN_GLAZED_TERRACOTTA(13, 12),
    GREEN_SHULKER_BOX,
    GREEN_TERRACOTTA(13, "STAINED_CLAY"),
    GRINDSTONE(0, 14),
    GUARDIAN_SPAWN_EGG(68, "MONSTER_EGG"),
    GUNPOWDER("SULPHUR"),
    HAY_BLOCK,
    HEART_OF_THE_SEA(13),
    HEAVY_WEIGHTED_PRESSURE_PLATE("IRON_PLATE"),
    HOGLIN_SPAWN_EGG(0, 16, "MONSTER_EGG"),
    HONEYCOMB(15),
    HONEYCOMB_BLOCK(15),
    HONEY_BLOCK(0, 15),
    HONEY_BOTTLE(0, 15),
    HOPPER,
    HOPPER_MINECART,
    HORN_CORAL(13),
    HORN_CORAL_BLOCK(13),
    HORN_CORAL_FAN(13),
    HORN_CORAL_WALL_FAN,
    HORSE_SPAWN_EGG(100, "MONSTER_EGG"),
    HUSK_SPAWN_EGG(23, "MONSTER_EGG"),
    ICE,
    INFESTED_CHISELED_STONE_BRICKS(5, "MONSTER_EGGS"),
    INFESTED_COBBLESTONE(1, "MONSTER_EGGS"),
    INFESTED_CRACKED_STONE_BRICKS(4, "MONSTER_EGGS"),
    INFESTED_MOSSY_STONE_BRICKS(3, "MONSTER_EGGS"),
    INFESTED_STONE("MONSTER_EGGS"),
    INFESTED_STONE_BRICKS(2, "MONSTER_EGGS"),
    /**
     * We will only add "INK_SAC" for {@link #BLACK_DYE} since it's
     * the only material (linked with this material) that is added
     * after 1.13, which means it can use both INK_SACK and INK_SAC.
     */
    INK_SAC("INK_SACK"),
    IRON_AXE,
    IRON_BARS("IRON_FENCE"),
    IRON_BLOCK,
    IRON_BOOTS,
    IRON_CHESTPLATE,
    IRON_DOOR("IRON_DOOR_BLOCK"),
    IRON_HELMET,
    IRON_HOE,
    IRON_HORSE_ARMOR("IRON_BARDING"),
    IRON_INGOT,
    IRON_LEGGINGS,
    IRON_NUGGET,
    IRON_ORE,
    IRON_PICKAXE,
    IRON_SHOVEL("IRON_SPADE"),
    IRON_SWORD,
    IRON_TRAPDOOR,
    ITEM_FRAME,
    JACK_O_LANTERN,
    JIGSAW(0, 14),
    JUKEBOX,
    JUNGLE_BOAT("BOAT_JUNGLE"),
    JUNGLE_BUTTON("WOOD_BUTTON"),
    JUNGLE_DOOR("JUNGLE_DOOR", "JUNGLE_DOOR_ITEM"),
    JUNGLE_FENCE,
    JUNGLE_FENCE_GATE,
    JUNGLE_LEAVES(3, "LEAVES"),
    JUNGLE_LOG(3, "LOG"),
    JUNGLE_PLANKS(3, "WOOD"),
    JUNGLE_PRESSURE_PLATE("WOOD_PLATE"),
    JUNGLE_SAPLING(3, "SAPLING"),
    JUNGLE_SIGN("SIGN_POST", "SIGN"),
    JUNGLE_SLAB(3, "WOOD_DOUBLE_STEP", "WOOD_STEP", "WOODEN_SLAB"),
    JUNGLE_STAIRS("JUNGLE_WOOD_STAIRS"),
    JUNGLE_TRAPDOOR("TRAP_DOOR"),
    JUNGLE_WALL_SIGN("WALL_SIGN"),
    JUNGLE_WOOD(3, "LOG"),
    KELP(13),
    KELP_PLANT(13),
    KNOWLEDGE_BOOK(0, 12, "BOOK"),
    LADDER,
    LANTERN(0, 14),
    LAPIS_BLOCK,
    LAPIS_LAZULI(4, "INK_SACK"),
    LAPIS_ORE,
    LARGE_FERN(3, "DOUBLE_PLANT"),
    LAVA("STATIONARY_LAVA"),
    LAVA_BUCKET,
    LEAD("LEASH"),
    LEATHER,
    LEATHER_BOOTS,
    LEATHER_CHESTPLATE,
    LEATHER_HELMET,
    LEATHER_HORSE_ARMOR(0, 14, "IRON_HORSE_ARMOR"),
    LEATHER_LEGGINGS,
    LECTERN(0, 14),
    LEVER,
    LIGHT_BLUE_BED(3, "BED_BLOCK", "BED"),
    LIGHT_BLUE_CARPET(3, "CARPET"),
    LIGHT_BLUE_CONCRETE(3, "CONCRETE"),
    LIGHT_BLUE_CONCRETE_POWDER(3, "CONCRETE_POWDER"),
    LIGHT_BLUE_GLAZED_TERRACOTTA(3, 12),
    LIGHT_BLUE_SHULKER_BOX,
    LIGHT_BLUE_TERRACOTTA(3, "STAINED_CLAY"),
    LIGHT_GRAY_BED(8, "BED_BLOCK", "BED"),
    LIGHT_GRAY_CARPET(8, "CARPET"),
    LIGHT_GRAY_CONCRETE(8, "CONCRETE"),
    LIGHT_GRAY_CONCRETE_POWDER(8, "CONCRETE_POWDER"),
    /**
     * Renamed to SILVER_GLAZED_TERRACOTTA in 1.12
     * Renamed to LIGHT_GRAY_GLAZED_TERRACOTTA in 1.14
     */
    LIGHT_GRAY_GLAZED_TERRACOTTA(0, 12, "STAINED_CLAY", "LIGHT_GRAY_TERRACOTTA", "SILVER_GLAZED_TERRACOTTA"),
    LIGHT_GRAY_SHULKER_BOX("SILVER_SHULKER_BOX"),
    LIGHT_GRAY_TERRACOTTA(8, "STAINED_CLAY"),
    LIGHT_WEIGHTED_PRESSURE_PLATE("GOLD_PLATE"),
    LILAC(1, "DOUBLE_PLANT"),
    LILY_OF_THE_VALLEY(15, 14),
    LILY_PAD("WATER_LILY"),
    LIME_BED(5, "BED_BLOCK", "BED"),
    LIME_CARPET(5, "CARPET"),
    LIME_CONCRETE(5, "CONCRETE"),
    LIME_CONCRETE_POWDER(5, "CONCRETE_POWDER"),
    LIME_GLAZED_TERRACOTTA(5, 12),
    LIME_SHULKER_BOX,
    LIME_TERRACOTTA(5, "STAINED_CLAY"),
    LINGERING_POTION,
    LLAMA_SPAWN_EGG(103, "MONSTER_EGG"),
    LODESTONE(16),
    LOOM(14),
    MAGENTA_BED(2, "BED_BLOCK", "BED"),
    MAGENTA_CARPET(2, "CARPET"),
    MAGENTA_CONCRETE(2, "CONCRETE"),
    MAGENTA_CONCRETE_POWDER(2, "CONCRETE_POWDER"),
    MAGENTA_GLAZED_TERRACOTTA(2, 12),
    MAGENTA_SHULKER_BOX,
    MAGENTA_TERRACOTTA(2, "STAINED_CLAY"),
    MAGMA_BLOCK(0, 10, "MAGMA"),
    MAGMA_CREAM,
    MAGMA_CUBE_SPAWN_EGG(62, "MONSTER_EGG"),
    /**
     * Adding this to the duplicated list will give you a filled map
     * for 1.13+ versions and removing it from duplicated list will
     * still give you a filled map in -1.12 versions.
     * Since higher versions are our priority I'll keep 1.13+ support
     * until I can come up with something to fix it.
     */
    MAP("EMPTY_MAP"),
    MELON("MELON_BLOCK"),
    MELON_SEEDS,
    MELON_SLICE("MELON"),
    MELON_STEM,
    MILK_BUCKET,
    MINECART,
    MOJANG_BANNER_PATTERN,
    MOOSHROOM_SPAWN_EGG(96, "MONSTER_EGG"),
    MOSSY_COBBLESTONE,
    MOSSY_COBBLESTONE_SLAB(3, "STEP"),
    MOSSY_COBBLESTONE_STAIRS,
    MOSSY_COBBLESTONE_WALL(1, "COBBLE_WALL", "COBBLESTONE_WALL"),
    MOSSY_STONE_BRICKS(1, "SMOOTH_BRICK"),
    MOSSY_STONE_BRICK_SLAB(5, "STEP"),
    MOSSY_STONE_BRICK_STAIRS("SMOOTH_STAIRS"),
    MOSSY_STONE_BRICK_WALL,
    MOVING_PISTON("PISTON_MOVING_PIECE"),
    MULE_SPAWN_EGG(32, "MONSTER_EGG"),
    MUSHROOM_STEM("BROWN_MUSHROOM"),
    MUSHROOM_STEW("MUSHROOM_SOUP"),
    MUSIC_DISC_11("GOLD_RECORD"),
    MUSIC_DISC_13("GREEN_RECORD"),
    MUSIC_DISC_BLOCKS("RECORD_3"),
    MUSIC_DISC_CAT("RECORD_4"),
    MUSIC_DISC_CHIRP("RECORD_5"),
    MUSIC_DISC_FAR("RECORD_6"),
    MUSIC_DISC_MALL("RECORD_7"),
    MUSIC_DISC_MELLOHI("RECORD_8"),
    MUSIC_DISC_PIGSTEP(16),
    MUSIC_DISC_STAL("RECORD_9"),
    MUSIC_DISC_STRAD("RECORD_10"),
    MUSIC_DISC_WAIT("RECORD_11"),
    MUSIC_DISC_WARD("RECORD_12"),
    MUTTON,
    MYCELIUM("MYCEL"),
    NAME_TAG,
    NAUTILUS_SHELL(13),
    NETHERITE_AXE(16),
    NETHERITE_BLOCK(16),
    NETHERITE_BOOTS(16),
    NETHERITE_CHESTPLATE(16),
    NETHERITE_HELMET(16),
    NETHERITE_HOE(16),
    NETHERITE_INGOT(16),
    NETHERITE_LEGGINGS(16),
    NETHERITE_PICKAXE(16),
    NETHERITE_SCRAP(16),
    NETHERITE_SHOVEL(16),
    NETHERITE_SWORD(16),
    NETHERRACK,
    NETHER_BRICK("NETHER_BRICK_ITEM"),
    NETHER_BRICKS("NETHER_BRICK"),
    NETHER_BRICK_FENCE("NETHER_FENCE"),
    NETHER_BRICK_SLAB(6, "STEP"),
    NETHER_BRICK_STAIRS,
    NETHER_BRICK_WALL,
    NETHER_GOLD_ORE(16),
    NETHER_PORTAL("PORTAL"),
    NETHER_QUARTZ_ORE("QUARTZ_ORE"),
    NETHER_SPROUTS(16),
    NETHER_STAR,
    /**
     * Just like mentioned in https://minecraft.gamepedia.com/Nether_Wart
     * Nether wart is also known as nether stalk in the code.
     * NETHER_STALK is the planted state of nether warts.
     */
    NETHER_WART("NETHER_WARTS", "NETHER_STALK"),
    NETHER_WART_BLOCK,
    NOTE_BLOCK,
    OAK_BOAT("BOAT"),
    OAK_BUTTON("WOOD_BUTTON"),
    OAK_DOOR("WOODEN_DOOR", "WOOD_DOOR"),
    OAK_FENCE("FENCE"),
    OAK_FENCE_GATE("FENCE_GATE"),
    OAK_LEAVES("LEAVES"),
    OAK_LOG("LOG"),
    OAK_PLANKS("WOOD"),
    OAK_PRESSURE_PLATE("WOOD_PLATE"),
    OAK_SAPLING("SAPLING"),
    OAK_SIGN("SIGN_POST", "SIGN"),
    OAK_SLAB("WOOD_DOUBLE_STEP", "WOOD_STEP", "WOODEN_SLAB"),
    OAK_STAIRS("WOOD_STAIRS"),
    OAK_TRAPDOOR("TRAP_DOOR"),
    OAK_WALL_SIGN("WALL_SIGN"),
    OAK_WOOD("LOG"),
    OBSERVER,
    OBSIDIAN,
    OCELOT_SPAWN_EGG(98, "MONSTER_EGG"),
    ORANGE_BED(1, "BED_BLOCK", "BED"),
    ORANGE_CARPET(1, "CARPET"),
    ORANGE_CONCRETE(1, "CONCRETE"),
    ORANGE_CONCRETE_POWDER(1, "CONCRETE_POWDER"),
    ORANGE_GLAZED_TERRACOTTA(1, 12),
    ORANGE_SHULKER_BOX,
    ORANGE_TERRACOTTA(1, "STAINED_CLAY"),
    ORANGE_TULIP(5, "RED_ROSE"),
    OXEYE_DAISY(8, "RED_ROSE"),
    PACKED_ICE,
    PAINTING,
    PANDA_SPAWN_EGG(14),
    PAPER,
    PARROT_SPAWN_EGG(105, "MONSTER_EGG"),
    PEONY(5, "DOUBLE_PLANT"),
    PETRIFIED_OAK_SLAB("WOOD_STEP"),
    PHANTOM_MEMBRANE(13),
    PHANTOM_SPAWN_EGG(0, 13),
    PIGLIN_BANNER_PATTERN(16),
    PIGLIN_BRUTE_SPAWN_EGG(16),
    PIGLIN_SPAWN_EGG(57, "MONSTER_EGG"),
    PIG_SPAWN_EGG(90, "MONSTER_EGG"),
    PILLAGER_SPAWN_EGG(14),
    PINK_BED(6, "BED_BLOCK", "BED"),
    PINK_CARPET(6, "CARPET"),
    PINK_CONCRETE(6, "CONCRETE"),
    PINK_CONCRETE_POWDER(6, "CONCRETE_POWDER"),
    PINK_GLAZED_TERRACOTTA(6, 12),
    PINK_SHULKER_BOX,
    PINK_TERRACOTTA(6, "STAINED_CLAY"),
    PINK_TULIP(7, "RED_ROSE"),
    PISTON("PISTON_BASE"),
    PISTON_HEAD("PISTON_EXTENSION"),
    PLAYER_HEAD(3, "SKULL", "SKULL_ITEM"),
    PLAYER_WALL_HEAD(3, "SKULL", "SKULL_ITEM"),
    PODZOL(2, "DIRT"),
    POISONOUS_POTATO,
    POLAR_BEAR_SPAWN_EGG(102, "MONSTER_EGG"),
    POLISHED_ANDESITE(6, "STONE"),
    POLISHED_ANDESITE_SLAB,
    POLISHED_ANDESITE_STAIRS,
    POLISHED_BASALT(16),
    POLISHED_BLACKSTONE(16),
    POLISHED_BLACKSTONE_BRICKS(16),
    POLISHED_BLACKSTONE_BRICK_SLAB(16),
    POLISHED_BLACKSTONE_BRICK_STAIRS(16),
    POLISHED_BLACKSTONE_BRICK_WALL(16),
    POLISHED_BLACKSTONE_BUTTON(16),
    POLISHED_BLACKSTONE_PRESSURE_PLATE(16),
    POLISHED_BLACKSTONE_SLAB(16),
    POLISHED_BLACKSTONE_STAIRS(16),
    POLISHED_BLACKSTONE_WALL(16),
    POLISHED_DIORITE(4, "STONE"),
    POLISHED_DIORITE_SLAB,
    POLISHED_DIORITE_STAIRS,
    POLISHED_GRANITE(2, "STONE"),
    POLISHED_GRANITE_SLAB,
    POLISHED_GRANITE_STAIRS,
    POPPED_CHORUS_FRUIT("CHORUS_FRUIT_POPPED"),
    POPPY("RED_ROSE"),
    PORKCHOP("PORK"),
    POTATO("POTATO_ITEM"),
    POTATOES("POTATO"),
    POTION,
    POTTED_ACACIA_SAPLING(4, "SAPLING", "FLOWER_POT"),
    POTTED_ALLIUM(2, "RED_ROSE", "FLOWER_POT"),
    POTTED_AZURE_BLUET(3, "RED_ROSE", "FLOWER_POT"),
    POTTED_BAMBOO,
    POTTED_BIRCH_SAPLING(2, "SAPLING", "FLOWER_POT"),
    POTTED_BLUE_ORCHID(1, "RED_ROSE", "FLOWER_POT"),
    POTTED_BROWN_MUSHROOM("FLOWER_POT"),
    POTTED_CACTUS("FLOWER_POT"),
    POTTED_CORNFLOWER,
    POTTED_CRIMSON_FUNGUS(16),
    POTTED_CRIMSON_ROOTS(16),
    POTTED_DANDELION("YELLOW_FLOWER", "FLOWER_POT"),
    POTTED_DARK_OAK_SAPLING(5, "SAPLING", "FLOWER_POT"),
    POTTED_DEAD_BUSH("FLOWER_POT"),
    POTTED_FERN(2, "LONG_GRASS", "FLOWER_POT"),
    POTTED_JUNGLE_SAPLING(3, "SAPLING", "FLOWER_POT"),
    POTTED_LILY_OF_THE_VALLEY,
    POTTED_OAK_SAPLING("SAPLING", "FLOWER_POT"),
    POTTED_ORANGE_TULIP(5, "RED_ROSE", "FLOWER_POT"),
    POTTED_OXEYE_DAISY(8, "RED_ROSE", "FLOWER_POT"),
    POTTED_PINK_TULIP(7, "RED_ROSE", "FLOWER_POT"),
    POTTED_POPPY("RED_ROSE", "FLOWER_POT"),
    POTTED_RED_MUSHROOM("FLOWER_POT"),
    POTTED_RED_TULIP(4, "RED_ROSE", "FLOWER_POT"),
    POTTED_SPRUCE_SAPLING(1, "SAPLING", "FLOWER_POT"),
    POTTED_WARPED_FUNGUS(16),
    POTTED_WARPED_ROOTS(16),
    POTTED_WHITE_TULIP(6, "RED_ROSE", "FLOWER_POT"),
    POTTED_WITHER_ROSE,
    POWERED_RAIL,
    PRISMARINE,
    PRISMARINE_BRICKS(2, "PRISMARINE"),
    PRISMARINE_BRICK_SLAB(4, "STEP"),
    PRISMARINE_BRICK_STAIRS(13),
    PRISMARINE_CRYSTALS,
    PRISMARINE_SHARD,
    PRISMARINE_SLAB(13),
    PRISMARINE_STAIRS(13),
    PRISMARINE_WALL,
    PUFFERFISH(3, "RAW_FISH"),
    PUFFERFISH_BUCKET(0, 13),
    PUFFERFISH_SPAWN_EGG(0, 13),
    PUMPKIN,
    PUMPKIN_PIE,
    PUMPKIN_SEEDS,
    PUMPKIN_STEM,
    PURPLE_BED(10, "BED_BLOCK", "BED"),
    PURPLE_CARPET(10, "CARPET"),
    PURPLE_CONCRETE(10, "CONCRETE"),
    PURPLE_CONCRETE_POWDER(10, "CONCRETE_POWDER"),
    PURPLE_GLAZED_TERRACOTTA(10, 12),
    PURPLE_SHULKER_BOX,
    PURPLE_TERRACOTTA(10, "STAINED_CLAY"),
    PURPUR_BLOCK,
    PURPUR_PILLAR,
    PURPUR_SLAB("PURPUR_DOUBLE_SLAB"),
    PURPUR_STAIRS,
    QUARTZ,
    QUARTZ_BLOCK,
    QUARTZ_BRICKS(16),
    QUARTZ_PILLAR(2, "QUARTZ_BLOCK"),
    QUARTZ_SLAB(7, "STEP"),
    QUARTZ_STAIRS,
    RABBIT,
    RABBIT_FOOT,
    RABBIT_HIDE,
    RABBIT_SPAWN_EGG(101, "MONSTER_EGG"),
    RABBIT_STEW,
    RAIL("RAILS"),
    RAVAGER_SPAWN_EGG(14),
    REDSTONE,
    REDSTONE_BLOCK,
    /**
     * Unlike redstone torch, REDSTONE_LAMP_ON isn't an item.
     * The name is just here on the list for matching.
     *
     * @see #REDSTONE_TORCH
     */
    REDSTONE_LAMP("REDSTONE_LAMP_ON", "REDSTONE_LAMP_OFF"),
    REDSTONE_ORE("GLOWING_REDSTONE_ORE"),
    /**
     * REDSTONE_TORCH_OFF isn't an item, but a block.
     * But REDSTONE_TORCH_ON is the item.
     * The name is just here on the list for matching.
     */
    REDSTONE_TORCH("REDSTONE_TORCH_OFF", "REDSTONE_TORCH_ON"),
    REDSTONE_WALL_TORCH,
    REDSTONE_WIRE,
    /**
     * Data value 14 or 0
     */
    RED_BED(14, "BED_BLOCK", "BED"),
    RED_CARPET(14, "CARPET"),
    RED_CONCRETE(14, "CONCRETE"),
    RED_CONCRETE_POWDER(14, "CONCRETE_POWDER"),
    RED_GLAZED_TERRACOTTA(14, 12),
    RED_MUSHROOM,
    RED_MUSHROOM_BLOCK("RED_MUSHROOM", "HUGE_MUSHROOM_2"),
    RED_NETHER_BRICKS("RED_NETHER_BRICK"),
    RED_NETHER_BRICK_SLAB(4, "STEP"),
    RED_NETHER_BRICK_STAIRS,
    RED_NETHER_BRICK_WALL,
    RED_SAND(1, "SAND"),
    RED_SANDSTONE,
    RED_SANDSTONE_SLAB("DOUBLE_STONE_SLAB2", "STONE_SLAB2"),
    RED_SANDSTONE_STAIRS,
    RED_SANDSTONE_WALL,
    RED_SHULKER_BOX,
    RED_TERRACOTTA(14, "STAINED_CLAY"),
    RED_TULIP(4, "RED_ROSE"),
    REPEATER("DIODE_BLOCK_ON", "DIODE_BLOCK_OFF", "DIODE"),
    REPEATING_COMMAND_BLOCK("COMMAND", "COMMAND_REPEATING"),
    RESPAWN_ANCHOR(16),
    ROSE_BUSH(4, "DOUBLE_PLANT"),
    ROTTEN_FLESH,
    SADDLE,
    SALMON(1, "RAW_FISH"),
    SALMON_BUCKET(0, 13),
    SALMON_SPAWN_EGG(0, 13),
    SAND,
    SANDSTONE,
    SANDSTONE_SLAB(1, "DOUBLE_STEP", "STEP", "STONE_SLAB"),
    SANDSTONE_STAIRS,
    SANDSTONE_WALL,
    SCAFFOLDING(0, 14),
    SCUTE(13),
    SEAGRASS(0, 13),
    SEA_LANTERN,
    SEA_PICKLE(13),
    SHEARS,
    SHEEP_SPAWN_EGG(91, "MONSTER_EGG"),
    SHIELD,
    SHROOMLIGHT(16),
    SHULKER_BOX("PURPLE_SHULKER_BOX"),
    SHULKER_SHELL,
    SHULKER_SPAWN_EGG(69, "MONSTER_EGG"),
    SILVERFISH_SPAWN_EGG(60, "MONSTER_EGG"),
    SKELETON_HORSE_SPAWN_EGG(28, "MONSTER_EGG"),
    SKELETON_SKULL("SKULL", "SKULL_ITEM"),
    SKELETON_SPAWN_EGG(51, "MONSTER_EGG"),
    SKELETON_WALL_SKULL("SKULL", "SKULL_ITEM"),
    SKULL_BANNER_PATTERN,
    SLIME_BALL,
    SLIME_BLOCK,
    SLIME_SPAWN_EGG(55, "MONSTER_EGG"),
    SMITHING_TABLE,
    SMOKER(0, 14),
    SMOOTH_QUARTZ(0, 13),
    SMOOTH_QUARTZ_SLAB(7, "STEP"),
    SMOOTH_QUARTZ_STAIRS,
    SMOOTH_RED_SANDSTONE(2, "RED_SANDSTONE"),
    SMOOTH_RED_SANDSTONE_SLAB("STONE_SLAB2"),
    SMOOTH_RED_SANDSTONE_STAIRS,
    SMOOTH_SANDSTONE(2, "SANDSTONE"),
    SMOOTH_SANDSTONE_SLAB("STEP"),
    SMOOTH_SANDSTONE_STAIRS,
    SMOOTH_STONE("STEP"),
    SMOOTH_STONE_SLAB("STEP"),
    SNOW,
    SNOWBALL("SNOW_BALL"),
    SNOW_BLOCK,
    SOUL_CAMPFIRE(16),
    SOUL_FIRE(16),
    SOUL_LANTERN(16),
    SOUL_SAND,
    SOUL_SOIL(16),
    SOUL_TORCH(16),
    SOUL_WALL_TORCH(16),
    SPAWNER("MOB_SPAWNER"),
    SPECTRAL_ARROW(0, 9),
    SPIDER_EYE,
    SPIDER_SPAWN_EGG(52, "MONSTER_EGG"),
    SPLASH_POTION,
    SPONGE,
    SPRUCE_BOAT("BOAT_SPRUCE"),
    SPRUCE_BUTTON("WOOD_BUTTON"),
    SPRUCE_DOOR("SPRUCE_DOOR", "SPRUCE_DOOR_ITEM"),
    SPRUCE_FENCE,
    SPRUCE_FENCE_GATE,
    SPRUCE_LEAVES(1, "LEAVES", "LEAVES_2"),
    SPRUCE_LOG(1, "LOG"),
    SPRUCE_PLANKS(1, "WOOD"),
    SPRUCE_PRESSURE_PLATE("WOOD_PLATE"),
    SPRUCE_SAPLING(1, "SAPLING"),
    SPRUCE_SIGN("SIGN_POST", "SIGN"),
    SPRUCE_SLAB(1, "WOOD_DOUBLE_STEP", "WOOD_STEP", "WOODEN_SLAB"),
    SPRUCE_STAIRS("SPRUCE_WOOD_STAIRS"),
    SPRUCE_TRAPDOOR("TRAP_DOOR"),
    SPRUCE_WALL_SIGN("WALL_SIGN"),
    SPRUCE_WOOD(1, "LOG"),
    SQUID_SPAWN_EGG(94, "MONSTER_EGG"),
    STAINED_GLASS("STAINED_GLASS"),
    STAINED_GLASS_PANE("THIN_GLASS", "STAINED_GLASS_PANE"),
    STICK,
    STICKY_PISTON("PISTON_BASE", "PISTON_STICKY_BASE"),
    STONE,
    STONECUTTER(14),
    STONE_AXE,
    STONE_BRICKS("SMOOTH_BRICK"),
    STONE_BRICK_SLAB(4, "DOUBLE_STEP", "STEP", "STONE_SLAB"),
    STONE_BRICK_STAIRS("SMOOTH_STAIRS"),
    STONE_BRICK_WALL,
    STONE_BUTTON,
    STONE_HOE,
    STONE_PICKAXE,
    STONE_PRESSURE_PLATE("STONE_PLATE"),
    STONE_SHOVEL("STONE_SPADE"),
    STONE_SLAB("DOUBLE_STEP", "STEP"),
    STONE_STAIRS,
    STONE_SWORD,
    STRAY_SPAWN_EGG(6, "MONSTER_EGG"),
    STRIDER_SPAWN_EGG(16),
    STRING,
    STRIPPED_ACACIA_LOG("LOG_2"),
    STRIPPED_ACACIA_WOOD("LOG_2"),
    STRIPPED_BIRCH_LOG(2, "LOG"),
    STRIPPED_BIRCH_WOOD(2, "LOG"),
    STRIPPED_CRIMSON_HYPHAE(16),
    STRIPPED_CRIMSON_STEM(16),
    STRIPPED_DARK_OAK_LOG("LOG"),
    STRIPPED_DARK_OAK_WOOD("LOG"),
    STRIPPED_JUNGLE_LOG(3, "LOG"),
    STRIPPED_JUNGLE_WOOD(3, "LOG"),
    STRIPPED_OAK_LOG("LOG"),
    STRIPPED_OAK_WOOD("LOG"),
    STRIPPED_SPRUCE_LOG(1, "LOG"),
    STRIPPED_SPRUCE_WOOD(1, "LOG"),
    STRIPPED_WARPED_HYPHAE(16),
    STRIPPED_WARPED_STEM(16),
    STRUCTURE_BLOCK,
    /**
     * Originally developers used barrier blocks for its purpose.
     * So technically this isn't really considered as a suggested material.
     */
    STRUCTURE_VOID(10, "BARRIER"),
    SUGAR,
    /**
     * Sugar Cane is a known material in pre-1.13
     */
    SUGAR_CANE("SUGAR_CANE_BLOCK"),
    SUNFLOWER("DOUBLE_PLANT"),
    SUSPICIOUS_STEW(0, 14),
    SWEET_BERRIES(14),
    SWEET_BERRY_BUSH(0, 14),
    TALL_GRASS(2, "DOUBLE_PLANT"),
    TALL_SEAGRASS(2, 13),
    TARGET(16),
    TERRACOTTA("STAINED_CLAY"),
    TIPPED_ARROW(0, 9),
    TNT,
    TNT_MINECART("EXPLOSIVE_MINECART"),
    TORCH,
    TOTEM_OF_UNDYING("TOTEM"),
    TRADER_LLAMA_SPAWN_EGG(103, 14),
    TRAPPED_CHEST,
    TRIDENT(13),
    TRIPWIRE,
    TRIPWIRE_HOOK,
    TROPICAL_FISH(2, "RAW_FISH"),
    TROPICAL_FISH_BUCKET(0, 13, "BUCKET", "WATER_BUCKET"),
    TROPICAL_FISH_SPAWN_EGG(0, 13, "MONSTER_EGG"),
    TUBE_CORAL(13),
    TUBE_CORAL_BLOCK(13),
    TUBE_CORAL_FAN(13),
    TUBE_CORAL_WALL_FAN,
    TURTLE_EGG(0, 13),
    TURTLE_HELMET(0, 13),
    TURTLE_SPAWN_EGG(0, 13),
    TWISTING_VINES(16),
    TWISTING_VINES_PLANT(16),
    VEX_SPAWN_EGG(35, "MONSTER_EGG"),
    VILLAGER_SPAWN_EGG(120, "MONSTER_EGG"),
    VINDICATOR_SPAWN_EGG(36, "MONSTER_EGG"),
    VINE,
    /**
     * 1.13 tag is not added because it's the same thing as {@link #AIR}
     *
     * @see #CAVE_AIR
     */
    VOID_AIR("AIR"),
    WALL_BANNER("WALL_BANNER", "STANDING_BANNER", "BANNER"),
    WALL_TORCH("TORCH"),
    WANDERING_TRADER_SPAWN_EGG(0, 14),
    WARPED_BUTTON(16),
    WARPED_DOOR(16),
    WARPED_FENCE(16),
    WARPED_FENCE_GATE(16),
    WARPED_FUNGUS(16),
    WARPED_FUNGUS_ON_A_STICK(16),
    WARPED_HYPHAE(16),
    WARPED_NYLIUM(16),
    WARPED_PLANKS(16),
    WARPED_PRESSURE_PLATE(16),
    WARPED_ROOTS(16),
    WARPED_SIGN(0, 16, "SIGN_POST"),
    WARPED_SLAB(16),
    WARPED_STAIRS(16),
    WARPED_STEM(16),
    WARPED_TRAPDOOR(16),
    WARPED_WALL_SIGN(0, 16, "WALL_SIGN"),
    WARPED_WART_BLOCK(16),
    /**
     * This is used for blocks only.
     * In 1.13- WATER will turn into STATIONARY_WATER after it finished spreading.
     * After 1.13+ this uses
     * https://hub.spigotmc.org/javadocs/spigot/org/bukkit/block/data/Levelled.html water flowing system.
     */
    WATER("STATIONARY_WATER"),
    WATER_BUCKET,
    WEEPING_VINES(16),
    WEEPING_VINES_PLANT(16),
    WET_SPONGE(1, "SPONGE"),
    /**
     * Wheat is a known material in pre-1.13
     */
    WHEAT("CROPS"),
    WHEAT_SEEDS("SEEDS"),
    WHITE_BED("BED_BLOCK", "BED"),
    WHITE_CARPET("CARPET"),
    WHITE_CONCRETE("CONCRETE"),
    WHITE_CONCRETE_POWDER("CONCRETE_POWDER"),
    WHITE_GLAZED_TERRACOTTA(0, 12, "STAINED_CLAY"),
    WHITE_SHULKER_BOX,
    WHITE_TERRACOTTA("STAINED_CLAY", "TERRACOTTA"),
    WHITE_TULIP(6, "RED_ROSE"),
    WITCH_SPAWN_EGG(66, "MONSTER_EGG"),
    WITHER_ROSE(0, 14),
    WITHER_SKELETON_SKULL(1, "SKULL", "SKULL_ITEM"),
    WITHER_SKELETON_SPAWN_EGG(5, "MONSTER_EGG"),
    WITHER_SKELETON_WALL_SKULL(1, "SKULL", "SKULL_ITEM"),
    WOLF_SPAWN_EGG(95, "MONSTER_EGG"),
    WOODEN_AXE("WOOD_AXE"),
    WOODEN_HOE("WOOD_HOE"),
    WOODEN_PICKAXE("WOOD_PICKAXE"),
    WOODEN_SHOVEL("WOOD_SPADE"),
    WOODEN_SWORD("WOOD_SWORD"),
    WOOL("WOOL"),
    WRITABLE_BOOK("BOOK_AND_QUILL"),
    WRITTEN_BOOK,
    YELLOW_BED(4, "BED_BLOCK", "BED"),
    YELLOW_CARPET(4, "CARPET"),
    YELLOW_CONCRETE(4, "CONCRETE"),
    YELLOW_CONCRETE_POWDER(4, "CONCRETE_POWDER"),
    YELLOW_GLAZED_TERRACOTTA(4, 12, "STAINED_CLAY", "YELLOW_TERRACOTTA"),
    YELLOW_SHULKER_BOX,
    YELLOW_TERRACOTTA(4, "STAINED_CLAY"),
    ZOGLIN_SPAWN_EGG(16),
    ZOMBIE_HEAD(2, "SKULL", "SKULL_ITEM"),
    ZOMBIE_HORSE_SPAWN_EGG(29, "MONSTER_EGG"),
    ZOMBIE_SPAWN_EGG(54, "MONSTER_EGG"),
    ZOMBIE_VILLAGER_SPAWN_EGG(27, "MONSTER_EGG"),
    ZOMBIE_WALL_HEAD(2, "SKULL", "SKULL_ITEM"),
    ZOMBIFIED_PIGLIN_SPAWN_EGG(57, "MONSTER_EGG", "ZOMBIE_PIGMAN_SPAWN_EGG");


    /**
     * Cached array of {@link XMaterial#values()} to avoid allocating memory for
     * calling the method every time.
     *
     * @since 2.0.0
     */
    public static final XMaterial[] VALUES = values();

    /**
     * We don't want to use {@link Enums#getIfPresent(Class, String)} to avoid a few checks.
     *
     * @since 5.1.0
     */
    private static final Map<String, XMaterial> NAMES = new HashMap<>();

    /**
     * Guava (Google Core Libraries for Java)'s cache for performance and timed caches.
     * For strings that match a certain XMaterial. Mostly cached for configs.
     *
     * @since 1.0.0
     */
    private static final Cache<String, XMaterial> NAME_CACHE = CacheBuilder.newBuilder()
            .expireAfterAccess(1, TimeUnit.HOURS)
            .build();

    /**
     * This is used for {@link #isOneOf(Collection)}
     *
     * @since 3.4.0
     */
    private static final LoadingCache<String, Pattern> CACHED_REGEX = CacheBuilder.newBuilder()
            .expireAfterAccess(3, TimeUnit.HOURS)
            .build(new CacheLoader<String, Pattern>() {
                @Override
                public Pattern load(@Nonnull String str) {
                    try {
                        return Pattern.compile(str);
                    } catch (PatternSyntaxException ex) {
                        ex.printStackTrace();
                        return null;
                    }
                }
            });
    /**
     * The maximum data value in the pre-flattening update which belongs to {@link #VILLAGER_SPAWN_EGG}<br>
     * https://minecraftitemids.com/types/spawn-egg
     *
     * @see #matchXMaterialWithData(String)
     * @since 8.0.0
     */
    private static final byte MAX_DATA_VALUE = 120;
    /**
     * Used to tell the system that the passed object's (name or material) data value
     * is not provided or is invalid.
     *
     * @since 8.0.0
     */
    private static final byte UNKNOWN_DATA_VALUE = -1;
    /**
     * The maximum material ID before the pre-flattening update which belongs to {@link #MUSIC_DISC_WAIT}
     *
     * @since 8.1.0
     */
    private static final short MAX_ID = 2267;
    /**
     * <b>XMaterial Paradox (Duplication Check)</b>
     * <p>
     * A set of duplicated material names in 1.13 and 1.12 that will conflict with the legacy names.
     * Values are the new material names. This map also contains illegal elements. Check the static initializer for more info.
     * <p>
     * Duplications are not useful at all in versions above the flattening update {@link Data#ISFLAT}
     * This set is only used for matching materials, for parsing refer to {@link #isDuplicated()}
     *
     * @since 3.0.0
     */
    private static final Set<String> DUPLICATED;

    static {
        for (XMaterial material : VALUES) NAMES.put(material.name(), material);
    }

    static {
        if (Data.ISFLAT) {
            // It's not needed at all if it's the newer version. We can save some memory.
            DUPLICATED = null;
        } else {
            // MELON_SLICE, CARROTS, POTATOES, BEETROOTS, GRASS_BLOCK, BRICKS, NETHER_BRICKS, BROWN_MUSHROOM
            // Using the constructor to add elements will decide to allocate more size which we don't need.
            DUPLICATED = new HashSet<>(4);
            DUPLICATED.add(GRASS.name());
            DUPLICATED.add(MELON.name());
            DUPLICATED.add(BRICK.name());
            DUPLICATED.add(NETHER_BRICK.name());
        }
    }

    /**
     * The data value of this material https://minecraft.gamepedia.com/Java_Edition_data_values/Pre-flattening
     * It's never a negative number.
     *
     * @see #getData()
     */
    private final byte data;
    /**
     * The version that this material was added in, otherwise 0 if the version is not recorded.
     *
     * @see #getMaterialVersion()
     * @since 7.0.0
     */
    private final byte version;
    /**
     * A list of material names that was being used for older verions.
     *
     * @see #getLegacy()
     */
    @Nonnull
    private final String[] legacy;
    /**
     * The cached Bukkit parsed material.
     *
     * @see #parseMaterial()
     * @since 9.0.0
     */
    @Nullable
    private final Material material;

    XMaterial(int data, int version, @Nonnull String... legacy) {
        this.data = (byte) data;
        this.version = (byte) version;
        this.legacy = legacy;

        Material mat = null;
        if ((!Data.ISFLAT && this.isDuplicated()) || (mat = Material.getMaterial(this.name())) == null) {
            for (int i = legacy.length - 1; i >= 0; i--) {
                mat = Material.getMaterial(legacy[i]);
                if (mat != null) break;
            }
        }
        this.material = mat;
    }

    XMaterial(int data, @Nonnull String... legacy) {
        this(data, 0, legacy);
    }

    XMaterial(int version) {
        this(0, version);
    }

    XMaterial() {
        this(0, 0);
    }

    XMaterial(String... legacy) {
        this(0, 0, legacy);
    }

    /**
     * Checks if the version is 1.13 Aquatic Update or higher.
     * An invocation of this method yields the cached result from the expression:
     * <p>
     * <blockquote>
     * {@link #supports(int) 13}}
     * </blockquote>
     *
     * @return true if 1.13 or higher.
     * @see #getVersion()
     * @see #supports(int)
     * @since 1.0.0
     */
    public static boolean isNewVersion() {
        return Data.ISFLAT;
    }

    /**
     * This is just an extra method that can be used for many cases.
     * It can be used in {@link org.bukkit.event.player.PlayerInteractEvent}
     * or when accessing,
     * or other compatibility related methods.
     * <p>
     * An invocation of this method yields exactly the same result as the expression:
     * <p>
     * <blockquote>
     * !{@link #supports(int)} 9
     * </blockquote>
     *
     * @since 2.0.0
     */
    public static boolean isOneEight() {
        return !supports(9);
    }

    /**
     * Gets the XMaterial with this name similar to {@link #valueOf(String)}
     * without throwing an exception.
     *
     * @param name the name of the material.
     *
     * @return an optional that can be empty.
     * @since 5.1.0
     */
    @Nonnull
    private static Optional<XMaterial> getIfPresent(@Nonnull String name) {
        return Optional.ofNullable(NAMES.get(name));
    }

    /**
     * The current version of the server.
     *
     * @return the current server version minor number.
     * @see #isNewVersion()
     * @since 2.0.0
     */
    public static int getVersion() {
        return Data.VERSION;
    }

    /**
     * When using newer versions of Minecraft ({@link #isNewVersion()}), helps
     * to find the old material name with its data value using a cached search for optimization.
     *
     * @see #matchDefinedXMaterial(String, byte)
     * @since 1.0.0
     */
    @Nullable
    private static XMaterial requestOldXMaterial(@Nonnull String name, byte data) {
        String holder = name + data;
        XMaterial cache = NAME_CACHE.getIfPresent(holder);
        if (cache != null) return cache;

        for (XMaterial material : VALUES) {
            // Not using material.name().equals(name) check is intended.
            if ((data == UNKNOWN_DATA_VALUE || data == material.data) && material.anyMatchLegacy(name)) {
                NAME_CACHE.put(holder, material);
                return material;
            }
        }

        return null;
    }

    /**
     * Parses the given material name as an XMaterial with a given data
     * value in the string if attached. Check {@link #matchXMaterialWithData(String)} for more info.
     *
     * @see #matchXMaterialWithData(String)
     * @see #matchDefinedXMaterial(String, byte)
     * @since 2.0.0
     */
    @Nonnull
    public static Optional<XMaterial> matchXMaterial(@Nonnull String name) {
        Validate.notEmpty(name, "Cannot match a material with null or empty material name");
        Optional<XMaterial> oldMatch = matchXMaterialWithData(name);
        return oldMatch.isPresent() ? oldMatch : matchDefinedXMaterial(format(name), UNKNOWN_DATA_VALUE);
    }

    /**
     * Parses material name and data value from the specified string.
     * The separator for the material name and its data value is {@code :}
     * Spaces are allowed. Mostly used when getting materials from config for old school minecrafters.
     * <p>
     * <b>Examples</b>
     * <p><pre>
     *     {@code INK_SACK:1 -> RED_DYE}
     *     {@code WOOL: 14  -> RED_WOOL}
     * </pre>
     *
     * @param name the material string that consists of the material name, data and separator character.
     *
     * @return the parsed XMaterial.
     * @see #matchXMaterial(String)
     * @since 3.0.0
     */
    @Nonnull
    private static Optional<XMaterial> matchXMaterialWithData(@Nonnull String name) {
        int index = name.indexOf(':');
        if (index != -1) {
            String mat = format(name.substring(0, index));
            try {
                // We don't use Byte.parseByte because we have our own range check.
                byte data = (byte) Integer.parseInt(StringUtils.deleteWhitespace(name.substring(index + 1)));
                return data >= 0 && data < MAX_DATA_VALUE ? matchDefinedXMaterial(mat, data) : matchDefinedXMaterial(mat, UNKNOWN_DATA_VALUE);
            } catch (NumberFormatException ignored) {
                return matchDefinedXMaterial(mat, UNKNOWN_DATA_VALUE);
            }
        }

        return Optional.empty();
    }

    /**
     * Parses the given material as an XMaterial.
     *
     * @throws IllegalArgumentException may be thrown as an unexpected exception.
     * @see #matchDefinedXMaterial(String, byte)
     * @see #matchXMaterial(ItemStack)
     * @since 2.0.0
     */
    @Nonnull
    public static XMaterial matchXMaterial(@Nonnull Material material) {
        Objects.requireNonNull(material, "Cannot match null material");
        return matchDefinedXMaterial(material.name(), UNKNOWN_DATA_VALUE)
                .orElseThrow(() -> new IllegalArgumentException("Unsupported material with no data value: " + material.name()));
    }

    /**
     * Parses the given item as an XMaterial using its material and data value (durability)
     * if not a damageable item {@link ItemStack#getDurability()}.
     *
     * @param item the ItemStack to match.
     *
     * @return an XMaterial if matched any.
     * @throws IllegalArgumentException may be thrown as an unexpected exception.
     * @see #matchXMaterial(Material)
     * @since 2.0.0
     */
    @Nonnull
    @SuppressWarnings("deprecation")
    public static XMaterial matchXMaterial(@Nonnull ItemStack item) {
        Objects.requireNonNull(item, "Cannot match null ItemStack");
        String material = item.getType().name();
        byte data = (byte) (Data.ISFLAT || item.getType().getMaxDurability() > 0 ? 0 : item.getDurability());

        // Check FILLED_MAP enum for more info.
        //if (!Data.ISFLAT && item.hasItemMeta() && item.getItemMeta() instanceof org.bukkit.inventory.meta.MapMeta) return FILLED_MAP;

        return matchDefinedXMaterial(material, data)
                .orElseThrow(() -> new IllegalArgumentException("Unsupported material from item: " + material + " (" + data + ')'));
    }

    /**
     * The main method that parses the given material name and data value as an XMaterial.
     * All the values passed to this method will not be null or empty and are formatted correctly.
     *
     * @param name the formatted name of the material.
     * @param data the data value of the material. Is always 0 or {@link #UNKNOWN_DATA_VALUE} when {@link Data#ISFLAT}
     *
     * @return an XMaterial (with the same data value if specified)
     * @see #matchXMaterial(Material)
     * @see #matchXMaterial(int, byte)
     * @see #matchXMaterial(ItemStack)
     * @since 3.0.0
     */
    @Nonnull
    protected static Optional<XMaterial> matchDefinedXMaterial(@Nonnull String name, byte data) {
        // if (!Boolean.valueOf(Boolean.getBoolean(Boolean.TRUE.toString())).equals(Boolean.FALSE.booleanValue())) return null;
        Boolean duplicated = null;
        boolean isAMap = name.equalsIgnoreCase("MAP");

        // Do basic number and boolean checks before accessing more complex enum stuff.
        if (Data.ISFLAT || (!isAMap && data <= 0 && !(duplicated = isDuplicated(name)))) {
            Optional<XMaterial> xMaterial = getIfPresent(name);
            if (xMaterial.isPresent()) return xMaterial;
        }
        // Usually flat versions wouldn't pass this point, but some special materials do.

        XMaterial oldXMaterial = requestOldXMaterial(name, data);
        if (oldXMaterial == null) {
            // Special case. Refer to FILLED_MAP for more info.
            return (data >= 0 && isAMap) ? Optional.of(FILLED_MAP) : Optional.empty();
        }

        if (!Data.ISFLAT && oldXMaterial.isPlural() && (duplicated == null ? isDuplicated(name) : duplicated)) return getIfPresent(name);
        return Optional.of(oldXMaterial);
    }

    /**
     * <b>XMaterial Paradox (Duplication Check)</b>
     * Checks if the material has any duplicates.
     * <p>
     * <b>Example:</b>
     * <p>{@code MELON, CARROT, POTATO, BEETROOT -> true}
     *
     * @param name the name of the material to check.
     *
     * @return true if there's a duplicated material for this material, otherwise false.
     * @since 2.0.0
     */
    private static boolean isDuplicated(@Nonnull String name) {
        // Don't use matchXMaterial() since this method is being called from matchXMaterial() itself and will cause a StackOverflowError.
        return DUPLICATED.contains(name);
    }

    /**
     * Gets the XMaterial based on the material's ID (Magic Value) and data value.<br>
     * You should avoid using this for performance issues.
     *
     * @param id   the ID (Magic value) of the material.
     * @param data the data value of the material.
     *
     * @return a parsed XMaterial with the same ID and data value.
     * @see #matchXMaterial(ItemStack)
     * @since 2.0.0
     * @deprecated this method loops through all the available materials and matches their ID using {@link #getId()}
     * which takes a really long time. Plugins should no longer support IDs. If you want, you can make a {@link Map} cache yourself.
     * This method obviously doesn't work for 1.13+ and will not be supported. This is only here for debugging purposes.
     */
    @Nonnull
    @Deprecated
    public static Optional<XMaterial> matchXMaterial(int id, byte data) {
        if (id < 0 || id > MAX_ID || data < 0) return Optional.empty();
        for (XMaterial materials : VALUES) {
            if (materials.data == data && materials.getId() == id) return Optional.of(materials);
        }
        return Optional.empty();
    }

    /**
     * Attempts to build the string like an enum name.
     * Removes all the spaces, and extra non-English characters. Also removes some config/in-game based strings.
     * While this method is hard to maintain, it's extremely efficient. It's approximately more than x5 times faster than
     * the normal RegEx + String Methods approach for both formatted and unformatted material names.
     *
     * @param name the material name to modify.
     *
     * @return an enum name.
     * @since 2.0.0
     */
    @Nonnull
    protected static String format(@Nonnull String name) {
        int len = name.length();
        char[] chs = new char[len];
        int count = 0;
        boolean appendUnderline = false;

        for (int i = 0; i < len; i++) {
            char ch = name.charAt(i);

            if (!appendUnderline && count != 0 && (ch == '-' || ch == ' ' || ch == '_') && chs[count] != '_')
                appendUnderline = true;
            else {
                boolean number = false;
                // Old materials have numbers in them.
                if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (number = (ch >= '0' && ch <= '9'))) {
                    if (appendUnderline) {
                        chs[count++] = '_';
                        appendUnderline = false;
                    }

                    if (number) chs[count++] = ch;
                    else chs[count++] = (char) (ch & 0x5f);
                }
            }
        }

        return new String(chs, 0, count);
    }

    /**
     * Checks if the specified version is the same version or higher than the current server version.
     *
     * @param version the major version to be checked. "1." is ignored. E.g. 1.12 = 12 | 1.9 = 9
     *
     * @return true of the version is equal or higher than the current version.
     * @since 2.0.0
     */
    public static boolean supports(int version) {
        return Data.VERSION >= version;
    }

    /**
     * Gets the exact major version (..., 1.9, 1.10, ..., 1.14)
     * In most cases, you shouldn't be using this method.
     *
     * @param version Supports {@link Bukkit#getVersion()}, {@link Bukkit#getBukkitVersion()} and normal formats such as "1.14"
     *
     * @return the exact major version.
     * @see #supports(int)
     * @see #getVersion()
     * @see #getMaterialVersion()
     * @since 2.0.0
     */
    @Nonnull
    public static String getMajorVersion(@Nonnull String version) {
        Validate.notEmpty(version, "Cannot get major Minecraft version from null or empty string");

        // getVersion()
        int index = version.lastIndexOf("MC:");
        if (index != -1) {
            version = version.substring(index + 4, version.length() - 1);
        } else if (version.endsWith("SNAPSHOT")) {
            // getBukkitVersion()
            index = version.indexOf('-');
            version = version.substring(0, index);
        }

        // 1.13.2, 1.14.4, etc...
        int lastDot = version.lastIndexOf('.');
        if (version.indexOf('.') != lastDot) version = version.substring(0, lastDot);

        return version;
    }

    public String[] getLegacy() {
        return this.legacy;
    }

    /**
     * XMaterial Paradox (Duplication Check)
     * I've concluded that this is just an infinite loop that keeps
     * going around the Singular Form and the Plural Form materials. A waste of brain cells and a waste of time.
     * This solution works just fine anyway.
     * <p>
     * A solution for XMaterial Paradox.
     * Manually parses the duplicated materials to find the exact material based on the server version.
     * If the name ends with "S" -> Plural Form Material.
     * Plural methods are only plural if they're also {@link #DUPLICATED}
     * <p>
     * The only special exceptions are {@link #BRICKS} and {@link #NETHER_BRICKS}
     *
     * @return true if this material is a plural form material, otherwise false.
     * @since 8.0.0
     */
    private boolean isPlural() {
        // this.name().charAt(this.name().length() - 1) == 'S'
        return this == CARROTS || this == POTATOES;
    }

    /**
     * Checks if the list of given material names matches the given base material.
     * Mostly used for configs.
     * <p>
     * Supports {@link String#contains} {@code CONTAINS:NAME} and Regular Expression {@code REGEX:PATTERN} formats.
     * <p>
     * <b>Example:</b>
     * <blockquote><pre>
     *     XMaterial material = {@link #matchXMaterial(ItemStack)};
     *     if (material.isOneOf(plugin.getConfig().getStringList("disabled-items")) return;
     * </pre></blockquote>
     * <br>
     * <b>{@code CONTAINS} Examples:</b>
     * <pre>
     *     {@code "CONTAINS:CHEST" -> CHEST, ENDERCHEST, TRAPPED_CHEST -> true}
     *     {@code "cOnTaINS:dYe" -> GREEN_DYE, YELLOW_DYE, BLUE_DYE, INK_SACK -> true}
     * </pre>
     * <p>
     * <b>{@code REGEX} Examples</b>
     * <pre>
     *     {@code "REGEX:^.+_.+_.+$" -> Every Material with 3 underlines or more: SHULKER_SPAWN_EGG, SILVERFISH_SPAWN_EGG, SKELETON_HORSE_SPAWN_EGG}
     *     {@code "REGEX:^.{1,3}$" -> Material names that have 3 letters only: BED, MAP, AIR}
     * </pre>
     * <p>
     * The reason that there are tags for {@code CONTAINS} and {@code REGEX} is for the performance.
     * Although RegEx patterns are cached in this method,
     * please avoid using the {@code REGEX} tag if you can use the {@code CONTAINS} tag instead.
     * It'll have a huge impact on performance.
     * Please avoid using {@code (capturing groups)} there's no use for them in this case.
     * If you want to use groups, use {@code (?: non-capturing groups)}. It's faster.
     * <p>
     * Want to learn RegEx? You can mess around in <a href="https://regexr.com/">RegExr</a> website.
     *
     * @param materials the material names to check base material on.
     *
     * @return true if one of the given material names is similar to the base material.
     * @since 3.1.1
     */
    public boolean isOneOf(@Nullable Collection<String> materials) {
        if (materials == null || materials.isEmpty()) return false;
        String name = this.name();

        for (String comp : materials) {
            String checker = comp.toUpperCase(Locale.ENGLISH);
            if (checker.startsWith("CONTAINS:")) {
                comp = format(checker.substring(9));
                if (name.contains(comp)) return true;
                continue;
            }
            if (checker.startsWith("REGEX:")) {
                comp = comp.substring(6);
                Pattern pattern = CACHED_REGEX.getUnchecked(comp);
                if (pattern != null && pattern.matcher(name).matches()) return true;
                continue;
            }

            // Direct Object Equals
            Optional<XMaterial> xMat = matchXMaterial(comp);
            if (xMat.isPresent() && xMat.get() == this) return true;
        }
        return false;
    }

    /**
     * Sets the {@link Material} (and data value on older versions) of an item.
     * Damageable materials will not have their durability changed.
     * <p>
     * Use {@link #parseItem()} instead when creating new ItemStacks.
     *
     * @param item the item to change its type.
     *
     * @see #parseItem()
     * @since 3.0.0
     */
    @Nonnull
    @SuppressWarnings("deprecation")
    public ItemStack setType(@Nonnull ItemStack item) {
        Objects.requireNonNull(item, "Cannot set material for null ItemStack");
        Material material = this.parseMaterial();
        Objects.requireNonNull(material, () -> "Unsupported material: " + this.name());

        item.setType(material);
        if (!Data.ISFLAT && material.getMaxDurability() <= 0) item.setDurability(this.data);
        return item;
    }

    /**
     * Checks if the given material name matches any of this xmaterial's legacy material names.
     * All the values passed to this method will not be null or empty and are formatted correctly.
     *
     * @param name the material name to check.
     *
     * @return true if it's one of the legacy names, otherwise false.
     * @since 2.0.0
     */
    private boolean anyMatchLegacy(@Nonnull String name) {
        for (int i = this.legacy.length - 1; i >= 0; i--) {
            if (name.equals(this.legacy[i])) return true;
        }
        return false;
    }

    /**
     * Parses an enum name to a user-friendly name.
     * These names will have underlines removed and with each word capitalized.
     * <p>
     * <b>Examples:</b>
     * <pre>
     *     {@literal EMERALD                 -> Emerald}
     *     {@literal EMERALD_BLOCK           -> Emerald Block}
     *     {@literal ENCHANTED_GOLDEN_APPLE  -> Enchanted Golden Apple}
     * </pre>
     *
     * @return a more user-friendly enum name.
     * @since 3.0.0
     */
    @Override
    @Nonnull
    public String toString() {
        return WordUtils.capitalize(this.name().replace('_', ' ').toLowerCase(Locale.ENGLISH));
    }

    /**
     * Gets the ID (Magic value) of the material.
     * https://www.minecraftinfo.com/idlist.htm
     *
     * @return the ID of the material or <b>-1</b> if it's not a legacy material or the server doesn't support the material.
     * @see #matchXMaterial(int, byte)
     * @since 2.2.0
     */
    @SuppressWarnings("deprecation")
    public int getId() {
        if (this.data != 0 || (this.legacy.length != 0 && Integer.parseInt(this.legacy[0].substring(2)) >= 13)) {
            return -1;
        }
        return this.parseMaterial().getId();
    }

    /**
     * The data value of this material <a href="https://minecraft.gamepedia.com/Java_Edition_data_values/Pre-flattening">pre-flattening</a>.
     * <p>
     * Can be accessed with {@link ItemStack#getData()} then {@code MaterialData#getData()}
     * or {@link ItemStack#getDurability()} if not damageable.
     *
     * @return data of this material, or 0 if none.
     * @since 1.0.0
     */
    @SuppressWarnings("deprecation")
    public byte getData() {
        return data;
    }

    /**
     * Parses an item from this XMaterial.
     * Uses data values on older versions.
     *
     * @return an ItemStack with the same material (and data value if in older versions.)
     * @see #setType(ItemStack)
     * @since 2.0.0
     */
    @Nullable
    @SuppressWarnings("deprecation")
    public ItemStack parseItem() {
        Material material = this.parseMaterial();
        if (material == null) return null;
        return Data.ISFLAT ? new ItemStack(material) : new ItemStack(material, 1, this.data);
    }

    /**
     * Parses the material of this XMaterial.
     *
     * @return the material related to this XMaterial based on the server version.
     * @since 1.0.0
     */
    @Nullable
    public Material parseMaterial() {
        return this.material;
    }

    /**
     * Checks if an item has the same material (and data value on older versions).
     *
     * @param item item to check.
     *
     * @return true if the material is the same as the item's material (and data value if on older versions), otherwise false.
     * @since 1.0.0
     */
    @SuppressWarnings("deprecation")
    public boolean isSimilar(@Nonnull ItemStack item) {
        Objects.requireNonNull(item, "Cannot compare with null ItemStack");
        if (item.getType() != this.parseMaterial()) return false;
        return Data.ISFLAT || item.getDurability() == this.data || item.getType().getMaxDurability() <= 0;
    }

    /**
     * Checks if this material is supported in the current version.
     * Suggested materials will be ignored.
     * <p>
     * Note that you should use {@link #parseMaterial()} or {@link #parseItem()} and check if it's null
     * if you're going to parse and use the material/item later.
     *
     * @return true if the material exists in {@link Material} list.
     * @since 2.0.0
     */
    public boolean isSupported() {
        return this.material != null;
    }

    /**
     * The version this material was added in. This will return the minor number in the version scheme.
     * For example, if it was added in 1.16, it'll return 16
     *
     * @return the version number of 0 if the version was not recorded.
     * @since 7.0.0
     */
    public byte getMaterialVersion() {
        return version;
    }

    /**
     * This method is needed due to Java enum initialization limitations.
     * It's really inefficient yes, but it's only used for initialization.
     * <p>
     * Yes there are many other ways like comparing the hardcoded ordinal or using a boolean in the enum constructor,
     * but it's not really a big deal.
     * <p>
     * This method should not be called if the version is after the flattening update {@link Data#ISFLAT}
     * and is only used for parsing materials, not matching, for matching check {@link #DUPLICATED}
     */
    private boolean isDuplicated() {
        switch (this.name()) {
            case "MELON":
            case "CARROT":
            case "POTATO":
            case "GRASS":
            case "BRICK":
            case "NETHER_BRICK":

                // Illegal Elements
                // Since both 1.12 and 1.13 have <type>_DOOR XMaterial will use it
                // for 1.12 to parse the material, but it needs <type>_DOOR_ITEM.
                // We'll trick XMaterial into thinking this needs to be parsed
                // using the old methods.
                // Some of these materials have their enum name added to the legacy list as well.
            case "DARK_OAK_DOOR":
            case "ACACIA_DOOR":
            case "BIRCH_DOOR":
            case "JUNGLE_DOOR":
            case "SPRUCE_DOOR":
            case "MAP":
            case "CAULDRON":
            case "BREWING_STAND":
            case "FLOWER_POT":
                return true;
            default:
                return false;
        }
    }

    /**
     * Used for datas that need to be accessed during enum initilization.
     *
     * @since 9.0.0
     */
    private static final class Data {
        /**
         * The current version of the server in the a form of a major version.
         * If the static initialization for this fails, you know something's wrong with the server software.
         *
         * @since 1.0.0
         */
        private static final int VERSION = Integer.parseInt(getMajorVersion(Bukkit.getVersion()).substring(2));
        /**
         * Cached result if the server version is after the v1.13 flattening update.
         *
         * @since 3.0.0
         */
        private static final boolean ISFLAT = supports(13);
    }
}
