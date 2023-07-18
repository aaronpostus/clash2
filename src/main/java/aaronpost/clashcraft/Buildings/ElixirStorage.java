package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.BuildingMenus.DefaultBuildingMenu;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Commands.UpdateStorageCapacity;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Globals.GUIHelper;
import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Schematics.Schematic;
import aaronpost.clashcraft.Singletons.Schematics;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ElixirStorage extends Building {
    public ElixirStorage(Arena arena) {
        super(arena);
        new UpdateStorageCapacity().execute(arena);
    }
    public static ItemStack getShopItem() {
        return GUIHelper.attachLore(BuildingGlobals.ELIXIR_STORAGE_ITEM_STACK.clone(), Arrays.asList(
                ChatColor.GRAY + "  An elixir storage allows you to",
                ChatColor.GRAY + "  stow reserves of " + ChatColor.LIGHT_PURPLE + "elixir" + ChatColor.GRAY + ".",
                ChatColor.GRAY + "Cost: " + BuildingGlobals.ELIXIR_STORAGE_COST[0] + " " + Globals.GOLD_DISPLAY_NAME,
                ChatColor.GRAY + "Build Time: " + Globals.timeFromSeconds(BuildingGlobals.ELIXIR_STORAGE_BUILD_TIME[0])));
    }
    @Override
    public ItemStack getStatsItem() {
        ItemStack stack = new ItemStack(Material.OAK_SIGN);
        String currency = " " + Globals.ELIXIR_DISPLAY_NAME.toLowerCase() + " ";
        return GUIHelper.attachNameAndLore(stack,getDisplayName() + " Stats", Arrays.asList(
                ChatColor.GRAY + " " + (int) getStorageCapacity("elixir") + currency + ChatColor.GRAY + "storage capacity",
                ChatColor.GRAY + " " + ChatColor.GRAY + getMaxHitpoints() + " hitpoints"
        ));
    }
    @Override
    public List<String> getUpgradeDescription() {
        List<String> lore = new ArrayList<>();
        int level = getLevel();
        String currency = " " + Globals.ELIXIR_DISPLAY_NAME.toLowerCase() + " ";
        lore.add(ChatColor.GRAY + " + " + ChatColor.GREEN + (BuildingGlobals.ELIXIR_STORAGE_CAPACITY[level] -
                BuildingGlobals.ELIXIR_STORAGE_CAPACITY[level - 1] ) + currency
                + ChatColor.GRAY + "storage capacity");
        lore.add(ChatColor.GRAY + " + " + ChatColor.GREEN + (BuildingGlobals.ELIXIR_STORAGE_HITPOINTS[level] -
                BuildingGlobals.ELIXIR_STORAGE_HITPOINTS[level - 1]) + ChatColor.GRAY + " hitpoints");
        lore.add(ChatColor.GRAY + "Cost: " + BuildingGlobals.GOLDMINE_COST[level] + " " + Globals.GOLD_DISPLAY_NAME);
        lore.add(ChatColor.GRAY + "Upgrade Time: " + Globals.timeFromSeconds(BuildingGlobals.ELIXIR_STORAGE_BUILD_TIME[level]));
        return lore;
    }
    @Override
    public boolean storesCurrency() { return true; }
    @Override
    public int getStorageCapacity(String currencyType) {
        if(isNewBuilding()) {
            return 0;
        }
        return BuildingGlobals.ELIXIR_STORAGE_CAPACITY[getLevel() - 1];
    }
    @Override
    public List<String> storageCurrencies() { return BuildingGlobals.ELIXIR_STORAGE_CURRENCIES; }
    @Override
    public ItemStack getPlainItemStack() {
        return BuildingGlobals.ELIXIR_STORAGE_ITEM_STACK.clone();
    }
    @Override
    public String getPlainDisplayName() {
        return BuildingGlobals.ELIXIR_STORAGE_DISPLAY_NAME;
    }
    @Override
    public ChatColor getPrimaryColor() {
        return ChatColor.LIGHT_PURPLE;
    }
    @Override
    public int getGridLengthX() {
        return 6;
    }
    @Override
    public int getGridLengthZ() {
        return 6;
    }
    @Override
    public long getTimeToBuild(int level) {
        return BuildingGlobals.ELIXIR_STORAGE_BUILD_TIME[level - 1];
    }
    @Override
    public Schematic getSchematic(int level) {
        return Schematics.s.getSchematic(BuildingGlobals.ELIXIR_STORAGE_SCHEMATICS[level - 1]);
    }
    @Override
    public Schematic getBrokenSchematic() {
        return null;
    }
    @Override
    public int getMaxLevel() {
        return 3;
    }
}
