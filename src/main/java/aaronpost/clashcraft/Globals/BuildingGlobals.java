package aaronpost.clashcraft.Globals;

import aaronpost.clashcraft.ClashCraft;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BuildingGlobals {
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
    public static ItemStack GOLDMINE_ITEM_STACK = new ItemStack(Material.CHEST);
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
    public static ItemStack BARRACKS_ITEM_STACK = new ItemStack(Material.TARGET);
    public static final Material BARRACKS_ITEM_MATERIAL = BARRACKS_ITEM_STACK.getType();
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

    // max level per townhall level
    public static int[] WALL_TOWN_HALL_LEVEL = new int[]{ 2 };
    public static int WALL_MAX_LEVEL = 1;

    //TOWNHALL
    public static String[] TOWN_HALL_SCHEMATICS = new String[] { "TownHall1"};
    public static String[] BROKEN_TOWN_HALL_SCHEMATICS = new String[] { "TownHall1_Broken"};

    //BUILDERHUT
    public static ItemStack BUILDER_HUT_ITEM_STACK = new ItemStack(Material.BRICKS);
    public static String BUILDER_HUT_DISPLAY_NAME = ChatColor.WHITE + "Builder Hut";
    static {
        ItemMeta meta = BUILDER_HUT_ITEM_STACK.getItemMeta();
        meta.setDisplayName(BUILDER_HUT_DISPLAY_NAME);
        BUILDER_HUT_ITEM_STACK.setItemMeta(meta);
    }
}
