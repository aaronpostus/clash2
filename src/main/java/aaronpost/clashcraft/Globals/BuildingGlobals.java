package aaronpost.clashcraft.Globals;

import aaronpost.clashcraft.ClashCraft;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.Duration;

public class BuildingGlobals {
    public static NamespacedKey NAMESPACED_KEY_UUID = new NamespacedKey(ClashCraft.plugin, "buildingUUID");
    public static NamespacedKey NAMESPACED_KEY_IDENTIFIER = new NamespacedKey(ClashCraft.plugin, "buildingInHand");
    public static enum BuildingStates { InHandNew, InHand, Upgrading, UpgradeComplete, IslandMode, DefenseMode }
    // Collectors
    public static float MILLISECONDS_TO_SECONDS = (1f/1000f);
    public static float SECONDS_TO_HOURS = (1f/60f/60f);
    public static String[] GOLDMINE_SCHEMATIC = new String[] { "GoldMine1", "GoldMine2", "GoldMine3", "GoldMine4",
                                                                "GoldMine5"};
    public static ItemStack GOLDMINE_ITEM_STACK = new ItemStack(Material.CHEST);
    public static String GOLDMINE_DISPLAY_NAME = ChatColor.YELLOW + "Gold Mine";
    static {
        ItemMeta meta = GOLDMINE_ITEM_STACK.getItemMeta();
        meta.setDisplayName(GOLDMINE_DISPLAY_NAME);
        GOLDMINE_ITEM_STACK.setItemMeta(meta);
    }
    public static float[] GOLDMINE_COLLECTION_RATE = new float[]{ 200, 400, 600, 800, 1000 };
    public static float[] GOLDMINE_CAPACITY = new float[]{ 1000, 2000, 3000, 5000, 10000 };
    public static Duration[] GOLDMINE_BUILD_TIME = new Duration[]{ Duration.ofSeconds(10), Duration.ofMinutes(1),
            Duration.ofMinutes(4), Duration.ofMinutes(10), Duration.ofMinutes(40) };
    public static float[] GOLDMINE_COST = new float[] { 150, 300, 700, 1400, 3000 };
    public static int[] GOLDMINE_HITPOINTS = new int[]{ 400, 440, 480, 520, 560 };
    public static int GOLDMINE_GRID_LENGTH = 5;
    public static ChatColor GOLDMINE_PRIMARY_COLOR = ChatColor.GOLD;
    // max level per townhall level
    public static int[] GOLDMINE_TOWN_HALL_LEVEL = new int[]{ 1, 1, 2, 2, 3};
    public static int GOLDMINE_MAX_LEVEL = 5;

}
