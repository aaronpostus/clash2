package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.BuildingMenus.DefaultBuildingMenu;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Globals.GUIHelper;
import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Interfaces.IInstantBuild;
import aaronpost.clashcraft.Schematics.Schematic;
import aaronpost.clashcraft.Singletons.Schematics;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Wall extends Building implements IInstantBuild {
    public Wall(Arena arena) {
        super(arena);
    }
    public static ItemStack getShopItem() {
        return GUIHelper.attachLore(BuildingGlobals.ELIXIR_STORAGE_ITEM_STACK.clone(), Arrays.asList(
                ChatColor.GRAY + "  Walls are used to defend your",
                ChatColor.GRAY + "  most valuable buildings.",
                ChatColor.GRAY + "Cost: " + BuildingGlobals.WALL_COST[0] + " " + Globals.GOLD_DISPLAY_NAME,
                ChatColor.GRAY + "Build Time: " + ChatColor.GOLD + "Instant")
        );
    }
    @Override
    public ItemStack getStatsItem() {
        return GUIHelper.attachNameAndLore(new ItemStack(Material.OAK_SIGN),getDisplayName() + " Stats",
                Arrays.asList(ChatColor.GRAY + " " + ChatColor.GRAY + getMaxHitpoints() + " hitpoints"
        ));
    }
    @Override
    public List<String> getUpgradeDescription() {
        List<String> lore = new ArrayList<>();
        int level = getLevel();
        String currency = " " + Globals.ELIXIR_DISPLAY_NAME.toLowerCase() + " ";
        lore.add(ChatColor.GRAY + " + " + ChatColor.GREEN + (BuildingGlobals.WALL_HITPOINTS[level] -
                BuildingGlobals.WALL_HITPOINTS[level - 1]) + ChatColor.GRAY + " hitpoints");
        lore.add(ChatColor.GRAY + "Cost: " + BuildingGlobals.WALL_COST[level] + " " + Globals.GOLD_DISPLAY_NAME);
        lore.add(ChatColor.GRAY + "Upgrade Time: " + ChatColor.GOLD + "Instant");
        return lore;
    }
    public Schematic getBrokenSchematic() {
        return Schematics.s.getSchematic(BuildingGlobals.BROKEN_WALL_SCHEMATIC);
    }
    @Override
    public ItemStack getPlainItemStack() {
        return BuildingGlobals.WALL_ITEM_STACK.clone();
    }
    @Override
    public String getPlainDisplayName() {
        return BuildingGlobals.WALL_DISPLAY_NAME;
    }
    @Override
    public ChatColor getPrimaryColor() {
        return BuildingGlobals.WALL_COLOR;
    }
    @Override
    public int getGridLengthX() {
        return BuildingGlobals.WALL_GRID_LENGTH;
    }
    @Override
    public int getGridLengthZ() {
        return BuildingGlobals.WALL_GRID_LENGTH;
    }
    @Override
    public long getTimeToBuild(int level) {
        return 0;
    }
    @Override
    public Schematic getSchematic(int level) {
        return Schematics.s.getSchematic(BuildingGlobals.WALL_SCHEMATICS[level-1]);
    }
    @Override
    public int getMaxLevel() {
        return BuildingGlobals.WALL_MAX_LEVEL;
    }
}
