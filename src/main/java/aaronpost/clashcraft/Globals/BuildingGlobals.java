package aaronpost.clashcraft.Globals;

import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Pair;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class BuildingGlobals {
    public static final int ELIXIRCOLLECTOR_MAX_LEVEL = 5;
    public static final float[] ELIXIRCOLLECTOR_COLLECTION_RATE = new float[] {200,400,600,800,1000};
    public static final int[] ELIXIRCOLLECTOR_CAPACITY = new int[] {1000,2000,3000,5000,10000};
    public static final int ELIXIRCOLLECTOR_GRID_LENGTH = 6;
    public static final long[] ELIXIRCOLLECTOR_BUILD_TIME = new long[] {10L, 60L, 60L * 4, 60L * 10, 60L * 40};
    public static final int[] ELIXIRCOLLECTOR_COST = new int[] { 150, 300, 700, 1400, 3000 };
    public static final int[] ELIXIRCOLLECTOR_HITPOINTS = new int[] { 400, 440, 480, 520, 560 };
    public static final ItemStack ELIXIRCOLLECTOR_ITEM_STACK = new ItemStack(Material.STRIPPED_CRIMSON_HYPHAE);
    public static final String ELIXIRCOLLECTOR_DISPLAY_NAME = ChatColor.LIGHT_PURPLE + "Elixir Collector";
    public static final String[] ELIXIRCOLLECTOR_SCHEMATIC = new String[] { "ElixirCollector1", "ElixirCollector2",
            "ElixirCollector3", "ElixirCollector4", "ElixirCollector5"};
    public static NamespacedKey NAMESPACED_KEY_UUID = new NamespacedKey(ClashCraft.plugin, "buildingUUID");
    public static NamespacedKey NAMESPACED_KEY_IDENTIFIER = new NamespacedKey(ClashCraft.plugin, "buildingInHand");
    public enum BuildingStates { InHandNew, InHand, Upgrading, UpgradeComplete, IslandMode, DefenseMode }
    public enum ClickAction { PrimaryAction, CompleteBuildingAction }
    // Collectors
    public static float MILLISECONDS_TO_SECONDS = (1f/1000f);
    public static float SECONDS_TO_HOURS = (1f/60f/60f);
    public static ChatColor GOLDMINE_PRIMARY_COLOR = ChatColor.GOLD;
    public static String[] GOLDMINE_SCHEMATIC = new String[] { "GoldMine1", "GoldMine2", "GoldMine3", "GoldMine4",
                                                                "GoldMine5"};
    public static String[] GOLDMINE_BROKEN_SCHEMATIC = new String[] { "GoldMine1", "GoldMine2", "GoldMine3", "GoldMine4",
            "GoldMine5"};
    public static ItemStack GOLDMINE_ITEM_STACK = new ItemStack(Material.RAW_GOLD_BLOCK);
    public static String GOLDMINE_DISPLAY_NAME = GOLDMINE_PRIMARY_COLOR + "Gold Mine";
    static {
        ItemMeta meta = GOLDMINE_ITEM_STACK.getItemMeta();
        meta.setDisplayName(GOLDMINE_DISPLAY_NAME);
        GOLDMINE_ITEM_STACK.setItemMeta(meta);
    }
    public static float[] GOLDMINE_COLLECTION_RATE = new float[]{ 200, 400, 600, 800, 1000 };
    public static int[] GOLDMINE_CAPACITY = new int[]{ 1000, 2000, 3000, 5000, 10000 };
    public static long[] GOLDMINE_BUILD_TIME = new long[]{ 10, 60, 4 * 60, 10 * 60, 40 * 60 };
    public static int[] GOLDMINE_COST = new int[] { 150, 300, 700, 1400, 3000 };
    public static int[] GOLDMINE_HITPOINTS = new int[]{ 400, 440, 480, 520, 560 };
    public static int GOLDMINE_GRID_LENGTH = 6;

    // max level per townhall level
    public static int[] GOLDMINE_TOWN_HALL_LEVEL = new int[]{ 1, 1, 2, 2, 3};
    public static int GOLDMINE_MAX_LEVEL = 5;

    // Barracks
    public static ChatColor BARRACKS_COLOR = ChatColor.RED;
    public static String[] BARRACKS_SCHEMATICS = new String[] { "Barracks1"};
    public static String[] BROKEN_BARRACKS_SCHEMATICS = new String[] { "Barracks1_Broken"};
    public static final ItemStack BARRACKS_ITEM_STACK = new ItemStack(Material.IRON_SWORD);
    public static String BARRACKS_DISPLAY_NAME = BARRACKS_COLOR + "Barracks";
    static {
        ItemMeta meta = BARRACKS_ITEM_STACK.getItemMeta();
        meta.setDisplayName(BARRACKS_DISPLAY_NAME);
        BARRACKS_ITEM_STACK.setItemMeta(meta);
    }
    public static long[] BARRACKS_BUILD_TIME = new long[]{ 10 };
    public static float[] BARRACKS_COST = new float[] { 100 };
    public static int[] BARRACKS_HITPOINTS = new int[]{ 250 };
    public static int BARRACKS_GRID_LENGTH = 6;

    // max level per townhall level
    public static int[] BARRACKS_TOWN_HALL_LEVEL = new int[]{ 1 };
    public static int BARRACKS_MAX_LEVEL = 1;

    // Walls
    public static ChatColor WALL_COLOR = ChatColor.GOLD;
    public static String[] WALL_SCHEMATICS = new String[] { "Wall1" };
    public static String BROKEN_WALL_SCHEMATIC = "Wall_Broken";
    public static ItemStack WALL_ITEM_STACK = new ItemStack(Material.OAK_FENCE);
    public static final Material WALL_ITEM_MATERIAL = BARRACKS_ITEM_STACK.getType();
    public static String WALL_DISPLAY_NAME = WALL_COLOR + "Wall";
    static {
        ItemMeta meta = WALL_ITEM_STACK.getItemMeta();
        meta.setDisplayName(WALL_DISPLAY_NAME);
        WALL_ITEM_STACK.setItemMeta(meta);
    }
    public static float[] WALL_COST = new float[] { 50 };
    public static int[] WALL_HITPOINTS = new int[]{ 300 };
    public static int WALL_GRID_LENGTH = 2;

    public static int[] WALL_TOWN_HALL_LEVEL = new int[]{ 2 };
    public static int WALL_MAX_LEVEL = 1;

    //TOWNHALL
    public static ItemStack TOWN_HALL_ITEM_STACK = new ItemStack(Material.CHEST);
    public static String TOWN_HALL_DISPLAY_NAME = ChatColor.GOLD + "Town Hall";
    static {
        ItemMeta meta = TOWN_HALL_ITEM_STACK.getItemMeta();
        meta.setDisplayName(TOWN_HALL_DISPLAY_NAME);
        TOWN_HALL_ITEM_STACK.setItemMeta(meta);
    }
    public static String[] TOWN_HALL_SCHEMATICS = new String[] { "TownHall1", "TownHall2" };
    public static String[] BROKEN_TOWN_HALL_SCHEMATICS = new String[] { "TownHall1_Broken"};
    public static List<String> TOWN_HALL_STORAGE_CURRENCIES = List.of("gold","elixir");
    public static List<Map<String,Integer>> TOWN_HALL_STORAGE_CAPACITY = new ArrayList<>();
    static {
        Map<String,Integer> th1currency = new HashMap<>();
        th1currency.put("gold",1000);
        th1currency.put("elixir",1000);
        Map<String,Integer> th2currency = new HashMap<>();
        th2currency.put("gold",2500);
        th2currency.put("elixir",2500);
        TOWN_HALL_STORAGE_CAPACITY.add(th1currency);
        TOWN_HALL_STORAGE_CAPACITY.add(th2currency);
    }
    public static long[] TOWN_HALL_BUILD_TIME = new long[] { 0, 10L };

    //BUILDERHUT
    public static ItemStack BUILDER_HUT_ITEM_STACK = new ItemStack(Material.BRICKS);
    public static String BUILDER_HUT_DISPLAY_NAME = ChatColor.WHITE + "Builder Hut";
    static {
        ItemMeta meta = BUILDER_HUT_ITEM_STACK.getItemMeta();
        meta.setDisplayName(BUILDER_HUT_DISPLAY_NAME);
        BUILDER_HUT_ITEM_STACK.setItemMeta(meta);
    }

    //ARMY CAMP
    public static ItemStack ARMY_CAMP_ITEM_STACK = new ItemStack(Material.CAMPFIRE);
    public static String ARMY_CAMP_DISPLAY_NAME = ChatColor.YELLOW + "Army Camp";
    static {
        ItemMeta meta = ARMY_CAMP_ITEM_STACK.getItemMeta();
        meta.setDisplayName(ARMY_CAMP_DISPLAY_NAME);
        ARMY_CAMP_ITEM_STACK.setItemMeta(meta);
    }

    // GOLD STORAGE
    public static ItemStack GOLD_STORAGE_ITEM_STACK = new ItemStack(Material.YELLOW_SHULKER_BOX);
    public static String GOLD_STORAGE_DISPLAY_NAME = ChatColor.GOLD + "Gold Storage";
    static {
        GUIHelper.attachName(GOLD_STORAGE_ITEM_STACK,GOLD_STORAGE_DISPLAY_NAME);
    }
    public static String[] GOLD_STORAGE_SCHEMATICS = new String[] { "GoldStorage1", "GoldStorage2" };
    public static List<String> GOLD_STORAGE_CURRENCIES = List.of("gold");
    public static int[] GOLD_STORAGE_CAPACITY = new int[] { 1500, 3000 };
    public static long[] GOLD_STORAGE_BUILD_TIME = new long[] { 10L, 5 * 60L, };

    // ELIXIR STORAGE
    public static ItemStack ELIXIR_STORAGE_ITEM_STACK = new ItemStack(Material.MAGENTA_SHULKER_BOX);
    public static String ELIXIR_STORAGE_DISPLAY_NAME = ChatColor.LIGHT_PURPLE + "Elixir Storage";
    static {
        GUIHelper.attachName(ELIXIR_STORAGE_ITEM_STACK,ELIXIR_STORAGE_DISPLAY_NAME);
    }
    public static String[] ELIXIR_STORAGE_SCHEMATICS = new String[] { "ElixirStorage1", "ElixirStorage2" };
    public static List<String> ELIXIR_STORAGE_CURRENCIES = List.of("elixir");
    public static int[] ELIXIR_STORAGE_CAPACITY = new int[] { 1500, 3000 };
    public static long[] ELIXIR_STORAGE_BUILD_TIME = new long[] { 10L, 5 * 60L, };



    // todo
    public static final ItemStack DARK_BARRACKS_ITEM_STACK = new ItemStack(Material.NETHERITE_SWORD);
    public static String DARK_BARRACKS_DISPLAY_NAME = ChatColor.GRAY + "Dark Barracks";
    static {
        GUIHelper.attachName(DARK_BARRACKS_ITEM_STACK,DARK_BARRACKS_DISPLAY_NAME);
    }

    // Cannon
    // Walls
    public static String[] CANNON_SCHEMATICS = new String[] { "Cannon1" };
    public static ItemStack CANNON_ITEM_STACK = new ItemStack(Material.DISPENSER);
    public static String CANNON_DISPLAY_NAME = ChatColor.GRAY + "Cannon";
    static {
        GUIHelper.attachName(CANNON_ITEM_STACK, CANNON_DISPLAY_NAME);
    }
    public static long[] CANNON_BUILD_TIME = new long[] { 10L };
}
