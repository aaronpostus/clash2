package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Currency.Currency;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Schematics.Schematic;
import aaronpost.clashcraft.Singletons.Schematics;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

// Author: Aaron Post
public class GoldMine extends Collector {
    public GoldMine(Currency currency, int x, int z) {
        super(currency, x,z);
        // super.currency = Player's gold currency;
    }
    public GoldMine(Arena arena, Currency currency) {
        super(arena, currency);
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
    public Schematic getSchematic() {
        return Schematics.s.getSchematic(BuildingGlobals.GOLDMINE_SCHEMATIC[getLevel() - 1]);
    }
    @Override
    public Schematic getBrokenSchematic() {
        return Schematics.s.getSchematic(BuildingGlobals.GOLDMINE_BROKEN_SCHEMATIC[getLevel() - 1]);
    }

    @Override
    public void visualUpdate() {

    }
    @Override
    public void openMenu() {

    }
    @Override
    public ItemStack getPlainItemStack() { return BuildingGlobals.GOLDMINE_ITEM_STACK.clone(); }
    @Override
    public String getPlainDisplayName() { return BuildingGlobals.GOLDMINE_DISPLAY_NAME; }
}
