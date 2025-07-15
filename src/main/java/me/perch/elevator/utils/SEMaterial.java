package me.perch.elevator.utils;

import com.google.common.base.Enums;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import me.perch.elevator.Variables;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.DaylightDetector;

public enum SEMaterial {
   AIR,
   CAVE_AIR,
   VOID_AIR,
   ACACIA_LEAVES,
   ACACIA_LOG,
   ACACIA_PLANKS,
   ACACIA_SLAB,
   ACACIA_STAIRS,
   ACACIA_WOOD,
   ANCIENT_DEBRIS,
   ANDESITE,
   ANDESITE_SLAB,
   ANDESITE_STAIRS,
   ANVIL,
   BARREL,
   BASALT,
   BEACON,
   BEDROCK,
   BEEHIVE,
   BEE_NEST,
   BIRCH_LEAVES,
   BIRCH_LOG,
   BIRCH_PLANKS,
   BIRCH_SLAB,
   BIRCH_STAIRS,
   BIRCH_WOOD,
   BLACKSTONE,
   BLACKSTONE_SLAB,
   BLACKSTONE_STAIRS,
   BLACK_BED,
   BLACK_CARPET,
   BLACK_CONCRETE,
   BLACK_CONCRETE_POWDER,
   BLACK_GLAZED_TERRACOTTA,
   BLACK_SHULKER_BOX,
   BLACK_STAINED_GLASS,
   BLACK_STAINED_GLASS_PANE,
   BLACK_TERRACOTTA,
   BLACK_WOOL,
   BLAST_FURNACE,
   BLUE_BED,
   BLUE_CARPET,
   BLUE_CONCRETE,
   BLUE_CONCRETE_POWDER,
   BLUE_GLAZED_TERRACOTTA,
   BLUE_ICE,
   BLUE_SHULKER_BOX,
   BLUE_STAINED_GLASS,
   BLUE_STAINED_GLASS_PANE,
   BLUE_TERRACOTTA,
   BLUE_WOOL,
   BONE_BLOCK,
   BOOKSHELF,
   BRAIN_CORAL,
   BRAIN_CORAL_BLOCK,
   BRICKS,
   BRICK_SLAB,
   BRICK_STAIRS,
   BROWN_BED,
   BROWN_CARPET,
   BROWN_CONCRETE,
   BROWN_CONCRETE_POWDER,
   BROWN_GLAZED_TERRACOTTA,
   BROWN_MUSHROOM_BLOCK,
   BROWN_SHULKER_BOX,
   BROWN_STAINED_GLASS,
   BROWN_STAINED_GLASS_PANE,
   BROWN_TERRACOTTA,
   BROWN_WOOL,
   BUBBLE_COLUMN,
   BUBBLE_CORAL,
   BUBBLE_CORAL_BLOCK,
   CARTOGRAPHY_TABLE,
   CARVED_PUMPKIN,
   CAULDRON,
   CHAIN,
   CHAIN_COMMAND_BLOCK,
   CHEST,
   CHIPPED_ANVIL,
   CHISELED_NETHER_BRICKS,
   CHISELED_POLISHED_BLACKSTONE,
   CHISELED_QUARTZ_BLOCK,
   CHISELED_RED_SANDSTONE,
   CHISELED_SANDSTONE,
   CHISELED_STONE_BRICKS,
   CHORUS_FLOWER,
   CHORUS_PLANT,
   CLAY,
   COAL_BLOCK,
   COAL_ORE,
   COARSE_DIRT,
   COBBLESTONE,
   COBBLESTONE_SLAB,
   COBBLESTONE_STAIRS,
   COBWEB,
   COCOA,
   COMMAND_BLOCK,
   COMPARATOR,
   COMPOSTER,
   CONDUIT,
   CORNFLOWER,
   CRACKED_NETHER_BRICKS,
   CRACKED_POLISHED_BLACKSTONE_BRICKS,
   CRACKED_STONE_BRICKS,
   CRAFTING_TABLE,
   CRIMSON_FUNGUS,
   CRIMSON_HYPHAE,
   CRIMSON_NYLIUM,
   CRIMSON_PLANKS,
   CRIMSON_ROOTS,
   CRIMSON_SLAB,
   CRIMSON_STAIRS,
   CRIMSON_STEM,
   CRYING_OBSIDIAN,
   CUT_RED_SANDSTONE,
   CUT_RED_SANDSTONE_SLAB,
   CUT_SANDSTONE,
   CUT_SANDSTONE_SLAB,
   CYAN_BED,
   CYAN_CARPET,
   CYAN_CONCRETE,
   CYAN_CONCRETE_POWDER,
   CYAN_GLAZED_TERRACOTTA,
   CYAN_SHULKER_BOX,
   CYAN_STAINED_GLASS,
   CYAN_STAINED_GLASS_PANE,
   CYAN_TERRACOTTA,
   CYAN_WOOL,
   DAMAGED_ANVIL,
   DANDELION,
   DARK_OAK_LEAVES,
   DARK_OAK_LOG,
   DARK_OAK_PLANKS,
   DARK_OAK_SLAB,
   DARK_OAK_STAIRS,
   DARK_OAK_WOOD,
   DARK_PRISMARINE,
   DARK_PRISMARINE_SLAB,
   DARK_PRISMARINE_STAIRS,
   DEAD_BRAIN_CORAL_BLOCK,
   DEAD_BUBBLE_CORAL_BLOCK,
   DEAD_FIRE_CORAL_BLOCK,
   DEAD_HORN_CORAL_BLOCK,
   DEAD_TUBE_CORAL_BLOCK,
   DIAMOND_BLOCK,
   DIAMOND_ORE,
   DIORITE,
   DIORITE_SLAB,
   DIORITE_STAIRS,
   DIRT,
   DISPENSER,
   DRAGON_EGG,
   DRIED_KELP_BLOCK,
   DROPPER,
   END_STONE,
   END_STONE_BRICKS,
   END_STONE_BRICK_SLAB,
   END_STONE_BRICK_STAIRS,
   FIRE_CORAL_BLOCK,
   FLETCHING_TABLE,
   FROSTED_ICE,
   FURNACE,
   GILDED_BLACKSTONE,
   GLASS,
   GLOWSTONE,
   GOLD_BLOCK,
   GOLD_ORE,
   GRANITE,
   GRANITE_SLAB,
   GRANITE_STAIRS,
   GRASS_BLOCK,
   GRASS_PATH,
   GRAVEL,
   GRAY_BED,
   GRAY_CARPET,
   GRAY_CONCRETE,
   GRAY_CONCRETE_POWDER,
   GRAY_GLAZED_TERRACOTTA,
   GRAY_SHULKER_BOX,
   GRAY_STAINED_GLASS,
   GRAY_TERRACOTTA,
   GRAY_WOOL,
   GREEN_BED,
   GREEN_CARPET,
   GREEN_CONCRETE,
   GREEN_CONCRETE_POWDER,
   GREEN_GLAZED_TERRACOTTA,
   GREEN_SHULKER_BOX,
   GREEN_STAINED_GLASS,
   GREEN_TERRACOTTA,
   GREEN_WOOL,
   HAY_BLOCK,
   HONEYCOMB_BLOCK,
   HONEY_BLOCK,
   HORN_CORAL_BLOCK,
   ICE,
   INFESTED_CHISELED_STONE_BRICKS,
   INFESTED_COBBLESTONE,
   INFESTED_CRACKED_STONE_BRICKS,
   INFESTED_MOSSY_STONE_BRICKS,
   INFESTED_STONE,
   INFESTED_STONE_BRICKS,
   IRON_BLOCK,
   IRON_ORE,
   JACK_O_LANTERN,
   JUKEBOX,
   JUNGLE_LEAVES,
   JUNGLE_LOG,
   JUNGLE_PLANKS,
   JUNGLE_SLAB,
   JUNGLE_STAIRS,
   JUNGLE_WOOD,
   LAPIS_BLOCK,
   LAPIS_ORE,
   LIGHT_BLUE_BED,
   LIGHT_BLUE_CARPET,
   LIGHT_BLUE_CONCRETE,
   LIGHT_BLUE_CONCRETE_POWDER,
   LIGHT_BLUE_GLAZED_TERRACOTTA,
   LIGHT_BLUE_SHULKER_BOX,
   LIGHT_BLUE_STAINED_GLASS,
   LIGHT_BLUE_TERRACOTTA,
   LIGHT_BLUE_WOOL,
   LIGHT_GRAY_BED,
   LIGHT_GRAY_CARPET,
   LIGHT_GRAY_CONCRETE,
   LIGHT_GRAY_CONCRETE_POWDER,
   LIGHT_GRAY_GLAZED_TERRACOTTA,
   LIGHT_GRAY_SHULKER_BOX,
   LIGHT_GRAY_STAINED_GLASS,
   LIGHT_GRAY_TERRACOTTA,
   LIGHT_GRAY_WOOL,
   LIME_BED,
   LIME_CARPET,
   LIME_CONCRETE,
   LIME_CONCRETE_POWDER,
   LIME_GLAZED_TERRACOTTA,
   LIME_SHULKER_BOX,
   LIME_STAINED_GLASS,
   LIME_TERRACOTTA,
   LIME_WOOL,
   LODESTONE,
   LOOM,
   MAGENTA_BED,
   MAGENTA_CARPET,
   MAGENTA_CONCRETE,
   MAGENTA_CONCRETE_POWDER,
   MAGENTA_GLAZED_TERRACOTTA,
   MAGENTA_SHULKER_BOX,
   MAGENTA_STAINED_GLASS,
   MAGENTA_TERRACOTTA,
   MAGENTA_WOOL,
   MAGMA_BLOCK,
   MELON,
   MOSSY_COBBLESTONE,
   MOSSY_COBBLESTONE_SLAB,
   MOSSY_COBBLESTONE_STAIRS,
   MOSSY_STONE_BRICKS,
   MOSSY_STONE_BRICK_SLAB,
   MOSSY_STONE_BRICK_STAIRS,
   MUSHROOM_STEM,
   MYCELIUM,
   NETHERITE_BLOCK,
   NETHERRACK,
   NETHER_BRICKS,
   NETHER_BRICK_SLAB,
   NETHER_BRICK_STAIRS,
   NETHER_GOLD_ORE,
   NETHER_PORTAL,
   NETHER_QUARTZ_ORE,
   NETHER_WART_BLOCK,
   NOTE_BLOCK,
   OAK_LEAVES,
   OAK_LOG,
   OAK_PLANKS,
   OAK_SLAB,
   OAK_STAIRS,
   OAK_WOOD,
   PALE_OAK_LEAVES,
   PALE_OAK_LOG,
   PALE_OAK_PLANKS,
   PALE_OAK_SLAB,
   PALE_OAK_STAIRS,
   PALE_OAK_WOOD,
   OBSERVER,
   OBSIDIAN,
   ORANGE_BED,
   ORANGE_CARPET,
   ORANGE_CONCRETE,
   ORANGE_CONCRETE_POWDER,
   ORANGE_GLAZED_TERRACOTTA,
   ORANGE_SHULKER_BOX,
   ORANGE_STAINED_GLASS,
   ORANGE_TERRACOTTA,
   ORANGE_TULIP,
   ORANGE_WOOL,
   PACKED_ICE,
   PINK_BED,
   PINK_CARPET,
   PINK_CONCRETE,
   PINK_CONCRETE_POWDER,
   PINK_GLAZED_TERRACOTTA,
   PINK_SHULKER_BOX,
   PINK_STAINED_GLASS,
   PINK_TERRACOTTA,
   PINK_WOOL,
   PISTON,
   PODZOL,
   POLISHED_ANDESITE,
   POLISHED_ANDESITE_SLAB,
   POLISHED_ANDESITE_STAIRS,
   POLISHED_DIORITE,
   POLISHED_DIORITE_SLAB,
   POLISHED_DIORITE_STAIRS,
   POLISHED_GRANITE,
   POLISHED_GRANITE_SLAB,
   POLISHED_GRANITE_STAIRS,
   POLISHED_BASALT,
   POLISHED_BLACKSTONE,
   POLISHED_BLACKSTONE_BRICKS,
   POLISHED_BLACKSTONE_BRICK_SLAB,
   POLISHED_BLACKSTONE_BRICK_STAIRS,
   POLISHED_BLACKSTONE_SLAB,
   POLISHED_BLACKSTONE_STAIRS,
   PRISMARINE,
   PRISMARINE_BRICKS,
   PRISMARINE_BRICK_SLAB,
   PRISMARINE_BRICK_STAIRS,
   PRISMARINE_SLAB,
   PRISMARINE_STAIRS,
   PURPLE_CARPET,
   PURPLE_CONCRETE,
   PURPLE_CONCRETE_POWDER,
   PURPLE_GLAZED_TERRACOTTA,
   PURPLE_SHULKER_BOX,
   PURPLE_STAINED_GLASS,
   PURPLE_TERRACOTTA,
   PURPLE_WOOL,
   PURPUR_BLOCK,
   PURPUR_PILLAR,
   PURPUR_SLAB,
   PURPUR_STAIRS,
   QUARTZ_BLOCK,
   QUARTZ_BRICKS,
   QUARTZ_PILLAR,
   QUARTZ_SLAB,
   QUARTZ_STAIRS,
   REDSTONE_BLOCK,
   RED_BED,
   RED_CARPET,
   RED_CONCRETE,
   RED_CONCRETE_POWDER,
   RED_GLAZED_TERRACOTTA,
   RED_MUSHROOM,
   RED_MUSHROOM_BLOCK,
   RED_NETHER_BRICKS,
   RED_NETHER_BRICK_SLAB,
   RED_NETHER_BRICK_STAIRS,
   RED_SAND,
   RED_SANDSTONE,
   RED_SANDSTONE_SLAB,
   RED_SANDSTONE_STAIRS,
   RED_SHULKER_BOX,
   RED_STAINED_GLASS,
   RED_TERRACOTTA,
   RED_WOOL,
   RESPAWN_ANCHOR,
   SAND,
   SANDSTONE,
   SANDSTONE_SLAB,
   SANDSTONE_STAIRS,
   SCAFFOLDING,
   SHROOMLIGHT,
   SHULKER_BOX,
   SLIME_BLOCK,
   SMITHING_TABLE,
   SMOKER,
   SMOOTH_QUARTZ,
   SMOOTH_QUARTZ_SLAB,
   SMOOTH_QUARTZ_STAIRS,
   SMOOTH_RED_SANDSTONE,
   SMOOTH_RED_SANDSTONE_SLAB,
   SMOOTH_RED_SANDSTONE_STAIRS,
   SMOOTH_SANDSTONE,
   SMOOTH_SANDSTONE_SLAB,
   SMOOTH_SANDSTONE_STAIRS,
   SMOOTH_STONE,
   SMOOTH_STONE_SLAB,
   SNOW,
   SNOW_BLOCK,
   SOUL_SAND,
   SOUL_SOIL,
   SPONGE,
   SPRUCE_LEAVES,
   SPRUCE_LOG,
   SPRUCE_PLANKS,
   SPRUCE_SLAB,
   SPRUCE_STAIRS,
   SPRUCE_WOOD,
   STICKY_PISTON,
   STONE,
   STONE_BRICKS,
   STONE_BRICK_SLAB,
   STONE_BRICK_STAIRS,
   STONE_SLAB,
   STONE_STAIRS,
   STRIPPED_ACACIA_LOG,
   STRIPPED_ACACIA_WOOD,
   STRIPPED_BIRCH_LOG,
   STRIPPED_BIRCH_WOOD,
   STRIPPED_CRIMSON_HYPHAE,
   STRIPPED_CRIMSON_STEM,
   STRIPPED_DARK_OAK_LOG,
   STRIPPED_DARK_OAK_WOOD,
   STRIPPED_JUNGLE_LOG,
   STRIPPED_JUNGLE_WOOD,
   STRIPPED_OAK_LOG,
   STRIPPED_OAK_WOOD,
   STRIPPED_SPRUCE_LOG,
   STRIPPED_SPRUCE_WOOD,
   STRIPPED_WARPED_HYPHAE,
   STRIPPED_WARPED_STEM,
   STRIPPED_PALE_OAK_LOG,
   STRIPPED_PALE_OAK_WOOD,
   TARGET,
   TERRACOTTA,
   TNT,
   TUBE_CORAL_BLOCK,
   WARPED_HYPHAE,
   WARPED_NYLIUM,
   WARPED_PLANKS,
   WARPED_SLAB,
   WARPED_STAIRS,
   WARPED_STEM,
   WARPED_WART_BLOCK,
   WET_SPONGE,
   WHITE_BED,
   WHITE_CARPET,
   WHITE_CONCRETE,
   WHITE_CONCRETE_POWDER,
   WHITE_GLAZED_TERRACOTTA,
   WHITE_SHULKER_BOX,
   WHITE_STAINED_GLASS,
   WHITE_TERRACOTTA,
   WHITE_WOOL,
   YELLOW_BED,
   YELLOW_CARPET,
   YELLOW_CONCRETE,
   YELLOW_CONCRETE_POWDER,
   YELLOW_GLAZED_TERRACOTTA,
   YELLOW_SHULKER_BOX,
   YELLOW_STAINED_GLASS,
   YELLOW_TERRACOTTA,
   YELLOW_WOOL,
   AMETHYST_BLOCK,
   AZALEA,
   AZALEA_LEAVES,
   BUDDING_AMETHYST,
   CALCITE,
   CHISELED_DEEPSLATE,
   COBBLED_DEEPSLATE,
   COBBLED_DEEPSLATE_SLAB,
   COBBLED_DEEPSLATE_STAIRS,
   COPPER_BLOCK,
   COPPER_ORE,
   CRACKED_DEEPSLATE_BRICKS,
   CRACKED_DEEPSLATE_TILES,
   CUT_COPPER,
   CUT_COPPER_SLAB,
   CUT_COPPER_STAIRS,
   DEEPSLATE,
   DEEPSLATE_BRICKS,
   DEEPSLATE_BRICK_SLAB,
   DEEPSLATE_BRICK_STAIRS,
   DEEPSLATE_COAL_ORE,
   DEEPSLATE_COPPER_ORE,
   DEEPSLATE_DIAMOND_ORE,
   DEEPSLATE_EMERALD_ORE,
   DEEPSLATE_GOLD_ORE,
   DEEPSLATE_IRON_ORE,
   DEEPSLATE_LAPIS_ORE,
   DEEPSLATE_REDSTONE_ORE,
   DEEPSLATE_TILES,
   DEEPSLATE_TILE_SLAB,
   DEEPSLATE_TILE_STAIRS,
   DIRT_PATH,
   DRIPSTONE_BLOCK,
   EXPOSED_COPPER,
   EXPOSED_CUT_COPPER,
   EXPOSED_CUT_COPPER_SLAB,
   EXPOSED_CUT_COPPER_STAIRS,
   FLOWERING_AZALEA,
   FLOWERING_AZALEA_LEAVES,
   INFESTED_DEEPSLATE,
   MOSS_BLOCK,
   MOSS_CARPET,
   OXIDIZED_COPPER,
   OXIDIZED_CUT_COPPER,
   OXIDIZED_CUT_COPPER_SLAB,
   OXIDIZED_CUT_COPPER_STAIRS,
   POLISHED_DEEPSLATE,
   POLISHED_DEEPSLATE_SLAB,
   POLISHED_DEEPSLATE_STAIRS,
   RAW_COPPER_BLOCK,
   RAW_GOLD_BLOCK,
   RAW_IRON_BLOCK,
   ROOTED_DIRT,
   SMOOTH_BASALT,
   TINTED_GLASS,
   TUFF,
   WAXED_COPPER_BLOCK,
   WAXED_CUT_COPPER,
   WAXED_CUT_COPPER_SLAB,
   WAXED_CUT_COPPER_STAIRS,
   WAXED_EXPOSED_COPPER,
   WAXED_EXPOSED_CUT_COPPER,
   WAXED_EXPOSED_CUT_COPPER_SLAB,
   WAXED_EXPOSED_CUT_COPPER_STAIRS,
   WAXED_OXIDIZED_COPPER,
   WAXED_OXIDIZED_CUT_COPPER,
   WAXED_OXIDIZED_CUT_COPPER_SLAB,
   WAXED_OXIDIZED_CUT_COPPER_STAIRS,
   WAXED_WEATHERED_COPPER,
   WAXED_WEATHERED_CUT_COPPER,
   WAXED_WEATHERED_CUT_COPPER_SLAB,
   WAXED_WEATHERED_CUT_COPPER_STAIRS,
   WEATHERED_COPPER,
   WEATHERED_CUT_COPPER,
   WEATHERED_CUT_COPPER_SLAB,
   WEATHERED_CUT_COPPER_STAIRS,
   MUD,
   MUD_BRICKS,
   MUD_BRICK_SLAB,
   MUD_BRICK_STAIRS,
   MANGROVE_LOG,
   MANGROVE_WOOD,
   STRIPPED_MANGROVE_LOG,
   STRIPPED_MANGROVE_WOOD,
   MANGROVE_PLANKS,
   MANGROVE_LEAVES,
   MANGROVE_ROOTS,
   MUDDY_MANGROVE_ROOTS,
   MANGROVE_SLAB,
   MANGROVE_STAIRS,
   SCULK,
   BAMBOO_BLOCK,
   BAMBOO_PLANKS,
   BAMBOO_MOSAIC,
   BAMBOO_SLAB,
   BAMBOO_STAIRS,
   BAMBOO_MOSAIC_SLAB,
   BAMBOO_MOSAIC_STAIRS,
   CHERRY_LOG,
   CHERRY_WOOD,
   STRIPPED_CHERRY_LOG,
   STRIPPED_CHERRY_WOOD,
   CHERRY_PLANKS,
   CHERRY_LEAVES,
   CHERRY_SLAB,
   CHERRY_STAIRS,
   SUSPICIOUS_SAND,
   SUSPICIOUS_GRAVEL,
   CRAFTER,
   TRIAL_SPAWNER,
   TUFF_SLAB,
   TUFF_STAIRS,
   POLISHED_TUFF,
   POLISHED_TUFF_SLAB,
   POLISHED_TUFF_STAIRS,
   TUFF_BRICKS,
   TUFF_BRICK_SLAB,
   TUFF_BRICK_STAIRS,
   CHISELED_TUFF,
   CHISELED_TUFF_BRICKS,
   COPPER_GRATE,
   COPPER_BULB,
   EXPOSED_COPPER_GRATE,
   EXPOSED_COPPER_BULB,
   WEATHERED_COPPER_GRATE,
   WEATHERED_COPPER_BULB,
   OXIDIZED_COPPER_GRATE,
   OXIDIZED_COPPER_BULB,
   WAXED_COPPER_GRATE,
   WAXED_COPPER_BULB,
   WAXED_EXPOSED_COPPER_GRATE,
   WAXED_EXPOSED_COPPER_BULB,
   WAXED_WEATHERED_COPPER_GRATE,
   WAXED_WEATHERED_COPPER_BULB,
   WAXED_OXIDIZED_COPPER_GRATE,
   WAXED_OXIDIZED_COPPER_BULB,
   END_GATEWAY,
   TORCH,
   WALL_TORCH,
   REDSTONE_TORCH,
   REDSTONE_WALL_TORCH,
   SOUL_TORCH,
   SOUL_WALL_TORCH,
   STRING,
   REDSTONE_WIRE,
   TRIPWIRE,
   POPPY,
   BLUE_ORCHID,
   ALLIUM,
   AZURE_BLUET,
   RED_TULIP,
   WHITE_TULIP,
   PINK_TULIP,
   OXEYE_DAISY,
   LILY_OF_THE_VALLEY,
   WITHER_ROSE,
   GRASS,
   FERN,
   DEAD_BUSH,
   TALL_GRASS,
   LARGE_FERN,
   SUNFLOWER,
   LILAC,
   ROSE_BUSH,
   PEONY,
   BAMBOO_SAPLING,
   OAK_SAPLING,
   SPRUCE_SAPLING,
   BIRCH_SAPLING,
   JUNGLE_SAPLING,
   ACACIA_SAPLING,
   DARK_OAK_SAPLING,
   NETHER_SPROUTS,
   GLOW_LICHEN,
   VINE,
   CAVE_VINES,
   WEEPING_VINES,
   WEEPING_VINES_PLANT,
   TWISTING_VINES,
   TWISTING_VINES_PLANT,
   HANGING_ROOTS,
   SMALL_DRIPLEAF,
   SPORE_BLOSSOM,
   SUGAR_CANE,
   CORAL_FAN,
   DEAD_CORAL_FAN,
   TUBE_CORAL_FAN,
   BRAIN_CORAL_FAN,
   BUBBLE_CORAL_FAN,
   FIRE_CORAL_FAN,
   HORN_CORAL_FAN,
   DEAD_TUBE_CORAL_FAN,
   DEAD_BRAIN_CORAL_FAN,
   DEAD_BUBBLE_CORAL_FAN,
   DEAD_FIRE_CORAL_FAN,
   DEAD_HORN_CORAL_FAN,
   OAK_PRESSURE_PLATE,
   SPRUCE_PRESSURE_PLATE,
   BIRCH_PRESSURE_PLATE,
   JUNGLE_PRESSURE_PLATE,
   ACACIA_PRESSURE_PLATE,
   DARK_OAK_PRESSURE_PLATE,
   MANGROVE_PRESSURE_PLATE,
   CHERRY_PRESSURE_PLATE,
   BAMBOO_PRESSURE_PLATE,
   CRIMSON_PRESSURE_PLATE,
   WARPED_PRESSURE_PLATE,
   STONE_PRESSURE_PLATE,
   POLISHED_BLACKSTONE_PRESSURE_PLATE,
   LIGHT_WEIGHTED_PRESSURE_PLATE,
   HEAVY_WEIGHTED_PRESSURE_PLATE,
   PALE_MOSS_CARPET,
   TRIPWIRE_HOOK,
   LEVER,
   OAK_BUTTON,
   STONE_BUTTON,
   SPRUCE_BUTTON,
   BIRCH_BUTTON,
   JUNGLE_BUTTON,
   ACACIA_BUTTON,
   DARK_OAK_BUTTON,
   MANGROVE_BUTTON,
   CHERRY_BUTTON,
   PALE_OAK_BUTTON,
   BAMBOO_BUTTON,
   WARPED_BUTTON,
   CRIMSON_BUTTON,
   POLISHED_BLACKSTONE_BUTTON,
   EMERALD_BLOCK,
   OCHRE_FROGLIGHT,
   VERDANT_FROGLIGHT,
   PEARLESCENT_FROGLIGHT,
   CHISELED_COPPER;

   public static final EnumSet<SEMaterial> VALUES = EnumSet.allOf(SEMaterial.class);
   private static final Cache<String, SEMaterial> NAME_CACHE = CacheBuilder.newBuilder().softValues().expireAfterAccess(15L, TimeUnit.MINUTES).build();
   private static final Cache<SEMaterial, Optional<Material>> PARSED_CACHE = CacheBuilder.newBuilder().softValues().expireAfterAccess(10L, TimeUnit.MINUTES).concurrencyLevel(Runtime.getRuntime().availableProcessors()).build();
   private static final Pattern FORMAT_PATTERN = Pattern.compile("\\W+");
   private static final int VERSION = Integer.parseInt(getMajorVersion(Bukkit.getVersion()).substring(2));
   private static final boolean ISFLAT = supports(13);
   private final byte data;
   private final String[] legacy;

   private SEMaterial(int data, String... legacy) {
      this.data = (byte)data;
      this.legacy = legacy;
   }

   private SEMaterial() {
      this(0);
   }

   private SEMaterial(String... legacy) {
      this(0, legacy);
   }

   public static SEMaterial match(Block block, boolean ignoreData) {
      if (block == null) {
         return null;
      } else {
         return !Variables.PRE_1_13 && block.getBlockData() instanceof DaylightDetector
                 ? match(block.getType().name() + (((DaylightDetector)block.getBlockData()).isInverted() ? "_INVERTED" : ""))
                 : match(block.getType().name() + (Variables.PRE_1_13 && !ignoreData ? ":" + block.getData() : ""));
      }
   }

   public static SEMaterial match(String material) {
      String[] split = material.split(":");
      byte data = -1;
      material = split[0];
      if (split.length != 1 && Stream.of("DAYLIGHT_DETECTOR").noneMatch((s) -> split[0].toLowerCase().contains(s.toLowerCase()))) {
         data = Byte.parseByte(split[1]);
      }
      return matchDefinedSEMaterial(format(material), data);
   }

   private static SEMaterial matchDefinedSEMaterial(String name, byte data) {
      if (data <= 0 || ISFLAT) {
         Optional<SEMaterial> xMat = (Optional) Enums.getIfPresent(SEMaterial.class, name).transform(Optional::of).or(Optional.empty());
         if (xMat.isPresent()) {
            return xMat.get();
         }
      }
      return requestOldSEMaterial(name, data);
   }

   private static SEMaterial requestOldSEMaterial(String name, byte data) {
      String holder = name + data;
      SEMaterial material = NAME_CACHE.getIfPresent(holder);
      if (material != null) {
         return material;
      } else {
         Iterator<SEMaterial> var4 = VALUES.iterator();
         SEMaterial materials;
         do {
            do {
               if (!var4.hasNext()) {
                  return null;
               }
               materials = var4.next();
            } while (data != -1 && data != materials.data);
         } while (!materials.anyMatchLegacy(name));
         NAME_CACHE.put(holder, materials);
         return materials;
      }
   }

   private static String format(String name) {
      return FORMAT_PATTERN.matcher(name.trim().replace('-', '_').replace(' ', '_')).replaceAll("").toUpperCase(Locale.ENGLISH);
   }

   private static boolean supports(int version) {
      return VERSION >= version;
   }

   private static String getMajorVersion(String version) {
      int index = version.lastIndexOf("MC:");
      if (index != -1) {
         version = version.substring(index + 4, version.length() - 1);
      } else if (version.endsWith("SNAPSHOT")) {
         index = version.indexOf(45);
         version = version.substring(0, index);
      }
      int lastDot = version.lastIndexOf(46);
      if (version.indexOf(46) != lastDot) {
         version = version.substring(0, lastDot);
      }
      return version;
   }

   public Material getMaterial() {
      Optional<Material> cache = PARSED_CACHE.getIfPresent(this);
      if (cache != null) {
         return cache.orElse(null);
      } else {
         Material mat = Material.getMaterial(this.name());
         if (mat == null) {
            mat = this.requestOldMaterial();
         }
         if (mat != null) {
            PARSED_CACHE.put(this, Optional.of(mat));
         }
         return mat;
      }
   }

   private Material requestOldMaterial() {
      for (int i = this.legacy.length - 1; i >= 0; --i) {
         String legacy = this.legacy[i];
         if (i == 0 && legacy.charAt(1) == '.') {
            return null;
         }
         if (legacy.isEmpty()) {
            break;
         }
         Material material = Material.getMaterial(legacy);
         if (material != null) {
            return material;
         }
      }
      return null;
   }

   private boolean anyMatchLegacy(String name) {
      for (String legacy : this.legacy) {
         if (name.equals(legacy)) {
            return true;
         }
      }
      return false;
   }

   public byte getData() {
      return this.data;
   }

   public boolean isSpecialType(SEMaterial.SpecialType... specialTypes) {
      return Stream.of(specialTypes).anyMatch((specialType) -> this.name().contains(specialType.getContainedString()));
   }

   public static enum SpecialType {
      TRAPDOOR("TRAPDOOR"),
      PRESSURE_PLATE("_PLATE"),
      CARPET("CARPET"),
      SLAB("SLAB");

      private final String containedString;

      private SpecialType(String containedString) {
         this.containedString = containedString;
      }

      public static SEMaterial.SpecialType of(SEMaterial seMaterial) {
         if (seMaterial == null) return null; // <-- Add this line
         return Arrays.stream(values())
                 .filter(specialType -> seMaterial.name().contains(specialType.getContainedString()))
                 .findFirst()
                 .orElse(null);
      }

      public String getContainedString() {
         return this.containedString;
      }
   }
}
