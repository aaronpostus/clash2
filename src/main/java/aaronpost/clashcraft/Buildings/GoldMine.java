package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.BuildingMenus.CollectorMenu;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Currency.Currency;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Globals.GUIHelper;
import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Schematics.Schematic;
import aaronpost.clashcraft.Singletons.Schematics;
import aaronpost.clashcraft.Singletons.Sessions;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Author: Aaron Post
public class GoldMine extends Collector {
    public GoldMine(Currency currency, int x, int z) {
        super(currency, x,z);
        super.setCurrencyName("gold");
    }
    public GoldMine(Arena arena) {
        super(arena, Sessions.s.getSession(arena.getPlayer()).getCurrency("gold"));
        super.setCurrencyName("gold");
    }
    @Override
    public int getMaxLevel() { return BuildingGlobals.GOLDMINE_MAX_LEVEL; }
    @Override
    float getCollectionRate() { return BuildingGlobals.GOLDMINE_COLLECTION_RATE[getLevel() - 1]; }
    @Override
    float getCapacity() { return BuildingGlobals.GOLDMINE_CAPACITY[getLevel() - 1]; }
    @Override
    public ChatColor getPrimaryColor() { return ChatColor.GRAY; }
    @Override
    public int getGridLengthX() { return BuildingGlobals.GOLDMINE_GRID_LENGTH; }
    @Override
    public int getGridLengthZ() { return BuildingGlobals.GOLDMINE_GRID_LENGTH; }
    @Override
    public long getTimeToBuild(int level) { return BuildingGlobals.GOLDMINE_BUILD_TIME[level-1]; }
    @Override
    public List<String> getUpgradeDescription() {
        List<String> lore = new ArrayList<>();
        int level = getLevel();
        String currency = " " + super.getCurrency().getDisplayName().toLowerCase() + " ";
        lore.add(ChatColor.GRAY + " + " + ChatColor.GREEN + (BuildingGlobals.GOLDMINE_COST[level] -
                BuildingGlobals.GOLDMINE_COST[level - 1] ) + currency
                + ChatColor.GRAY + "/ hour");
        lore.add(ChatColor.GRAY + " + " + ChatColor.GREEN + (BuildingGlobals.GOLDMINE_CAPACITY[level] -
                BuildingGlobals.GOLDMINE_CAPACITY[level - 1] ) + currency
                + ChatColor.GRAY + "storage capacity");
        lore.add(ChatColor.GRAY + " + " + ChatColor.GREEN + (BuildingGlobals.GOLDMINE_HITPOINTS[level] -
                BuildingGlobals.GOLDMINE_HITPOINTS[level - 1]) + ChatColor.GRAY + " hitpoints");
        lore.add(ChatColor.GRAY + "Cost: " + BuildingGlobals.GOLDMINE_COST[level] + " " + Globals.ELIXIR_DISPLAY_NAME);
        lore.add(ChatColor.GRAY + "Upgrade Time: " + Globals.timeFromSeconds(BuildingGlobals.GOLDMINE_BUILD_TIME[level]));
        return lore;
    }
    public static ItemStack getShopItem() {
        return GUIHelper.attachLore(BuildingGlobals.GOLDMINE_ITEM_STACK.clone(), Arrays.asList(
                ChatColor.GRAY + " A gold mine collects " + ChatColor.GOLD + "gold" + ChatColor.GRAY + " over",
                ChatColor.GRAY + " time, even when you're offline!",
                ChatColor.GRAY + "Cost: " + BuildingGlobals.GOLDMINE_COST[0] + " " + Globals.ELIXIR_DISPLAY_NAME,
                ChatColor.GRAY + "Build Time: " + Globals.timeFromSeconds(BuildingGlobals.GOLDMINE_BUILD_TIME[0])));
    }
    @Override
    public Schematic getSchematic(int level) {
        return Schematics.s.getSchematic(BuildingGlobals.GOLDMINE_SCHEMATIC[level - 1]);
    }
    @Override
    public Schematic getBrokenSchematic() {
        return Schematics.s.getSchematic("GoldMine1_Broken");
    }
    @Override
    public ItemStack getPlainItemStack() { return BuildingGlobals.GOLDMINE_ITEM_STACK.clone(); }
    @Override
    public String getPlainDisplayName() { return BuildingGlobals.GOLDMINE_DISPLAY_NAME; }
}
