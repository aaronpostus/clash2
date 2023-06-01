package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Schematics.Schematic;
import aaronpost.clashcraft.Singletons.Schematics;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import java.time.Duration;

// Author: Aaron Post
public class GoldMine extends Collector {
    public GoldMine(int x, int z) {
        super(x,z);
        // super.currency = Player's gold currency;
    }
    public GoldMine() {
        super();
    }
    @Override
    public boolean isMaxLevel() { return getLevel() == BuildingGlobals.GOLDMINE_MAX_LEVEL; }
    @Override
    public Duration getUpgradeTime() { return BuildingGlobals.GOLDMINE_BUILD_TIME[getLevel() - 1]; }
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
    public Schematic getSchematic() {
        return Schematics.s.getSchematic(BuildingGlobals.GOLDMINE_SCHEMATIC[getLevel() - 1]);
    }

    @Override
    public ItemStack getPlainItemStack() { return BuildingGlobals.GOLDMINE_ITEM_STACK.clone(); }
    @Override
    public String getPlainDisplayName() { return BuildingGlobals.GOLDMINE_DISPLAY_NAME; }
}
