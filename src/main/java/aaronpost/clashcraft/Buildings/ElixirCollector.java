package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Currency.Currency;
import aaronpost.clashcraft.Globals.BuildingGlobals;
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
public class ElixirCollector extends Collector {
    public ElixirCollector(Currency currency, int x, int z) {
        super(currency, x,z);
        super.setCurrencyName("elixir");
    }
    public ElixirCollector(Arena arena) {
        super(arena, Sessions.s.getSession(arena.getPlayer()).getCurrency("elixir"));
        super.setCurrencyName("elixir");
    }
    @Override
    public int getMaxLevel() { return BuildingGlobals.ELIXIRCOLLECTOR_MAX_LEVEL; }
    @Override
    float getCollectionRate() { return BuildingGlobals.ELIXIRCOLLECTOR_COLLECTION_RATE[getLevel() - 1]; }
    @Override
    float getCapacity() { return BuildingGlobals.ELIXIRCOLLECTOR_CAPACITY[getLevel() - 1]; }
    @Override
    public ChatColor getPrimaryColor() { return ChatColor.GRAY; }
    @Override
    public List<Material> getItemFountainMaterials() {
        return Arrays.asList(Material.MAGENTA_DYE, Material.MAGENTA_TERRACOTTA, Material.MAGENTA_CONCRETE);
    }
    @Override
    public int getGridLengthX() { return BuildingGlobals.ELIXIRCOLLECTOR_GRID_LENGTH; }
    @Override
    public int getGridLengthZ() { return BuildingGlobals.ELIXIRCOLLECTOR_GRID_LENGTH; }
    @Override
    public long getTimeToBuild(int level) { return BuildingGlobals.ELIXIRCOLLECTOR_BUILD_TIME[level-1]; }
    @Override
    public List<String> getUpgradeDescription() {
        List<String> lore = new ArrayList<>();
        int level = getLevel();
        String currency = " " + super.getCurrency().getDisplayName().toLowerCase() + " ";
        lore.add(ChatColor.GRAY + " + " + ChatColor.GREEN + (BuildingGlobals.ELIXIRCOLLECTOR_COST[level] -
                BuildingGlobals.ELIXIRCOLLECTOR_COST[level - 1] ) + currency
                + ChatColor.GRAY + "/ hour");
        lore.add(ChatColor.GRAY + " + " + ChatColor.GREEN + (BuildingGlobals.ELIXIRCOLLECTOR_CAPACITY[level] -
                BuildingGlobals.ELIXIRCOLLECTOR_CAPACITY[level - 1] ) + currency
                + ChatColor.GRAY + "storage capacity");
        lore.add(ChatColor.GRAY + " + " + ChatColor.GREEN + (BuildingGlobals.ELIXIRCOLLECTOR_HITPOINTS[level] -
                BuildingGlobals.ELIXIRCOLLECTOR_HITPOINTS[level - 1]) + ChatColor.GRAY + " hitpoints");
        lore.add(ChatColor.GRAY + "Cost: " + BuildingGlobals.ELIXIRCOLLECTOR_COST[level] + " " + Globals.GOLD_DISPLAY_NAME);
        lore.add(ChatColor.GRAY + "Upgrade Time: " + Globals.timeFromSeconds(BuildingGlobals.ELIXIRCOLLECTOR_BUILD_TIME[level]));
        return lore;
    }
    public static ItemStack getShopItem() {
        ItemStack stack = BuildingGlobals.ELIXIRCOLLECTOR_ITEM_STACK.clone();
        ItemMeta meta = stack.getItemMeta();
        meta.setLore(Arrays.asList(ChatColor.GRAY + " An elixir collector collects " + ChatColor.LIGHT_PURPLE + "elixir" + ChatColor.GRAY + " over",
                    ChatColor.GRAY + " time, even when you're offline!",
                    ChatColor.GRAY + "Cost: " + BuildingGlobals.ELIXIRCOLLECTOR_COST[0] + " " + Globals.GOLD_DISPLAY_NAME));
        meta.setDisplayName(BuildingGlobals.ELIXIRCOLLECTOR_DISPLAY_NAME);
        stack.setItemMeta(meta);
        return stack;
    }
    @Override
    public Schematic getSchematic() {
        return Schematics.s.getSchematic(BuildingGlobals.ELIXIRCOLLECTOR_SCHEMATIC[getLevel() - 1]);
    }
    @Override
    public Schematic getBrokenSchematic() {
        return null;
    }
    @Override
    public ItemStack getPlainItemStack() { return BuildingGlobals.ELIXIRCOLLECTOR_ITEM_STACK.clone(); }
    @Override
    public String getPlainDisplayName() { return BuildingGlobals.ELIXIRCOLLECTOR_DISPLAY_NAME; }
}
