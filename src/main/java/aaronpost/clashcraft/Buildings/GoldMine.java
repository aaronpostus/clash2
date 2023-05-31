package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Globals.BuildingGlobals;
import org.bukkit.inventory.ItemStack;
import java.time.Duration;

// Author: Aaron Post
public class GoldMine extends Collector {
    public GoldMine(int x, int z) {
        super(x,z);
        // super.currency = Player's gold currency;
    }

    @Override
    public boolean isMaxLevel() { return getLevel() == BuildingGlobals.GOLDMINE_MAX_LEVEL; }
    @Override
    public Duration getUpgradeTime() { return BuildingGlobals.GOLDMINE_BUILD_TIME[getLevel() - 1]; }

    @Override
    float getCollectionRate() {
        return BuildingGlobals.GOLDMINE_COLLECTION_RATE[getLevel() - 1];
    }

    @Override
    float getCapacity() {
        return BuildingGlobals.GOLDMINE_CAPACITY[getLevel() - 1];
    }

    @Override
    public ItemStack getItemStack() {
        return BuildingGlobals.GOLDMINE_ITEM_STACK.clone();
    }

    @Override
    public String getDisplayName() {
        return BuildingGlobals.GOLDMINE_DISPLAY_NAME;
    }
}
