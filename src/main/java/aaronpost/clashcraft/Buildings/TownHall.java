package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Buildings.BuildingMenus.DefaultBuildingMenu;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Schematics.Schematic;
import aaronpost.clashcraft.Singletons.Schematics;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class TownHall extends Building {
    public TownHall(int x, int z) {
        super(x,z);
    }
    @Override
    public void click() {

    }

    @Override
    public void openMenu() {

    }

    @Override
    public void catchUp(float hoursToCatchUp) {

    }

    @Override
    public void update() {

    }

    @Override
    public void visualUpdate() {

    }

    @Override
    public ItemStack getPlainItemStack() {
        return null;
    }

    @Override
    public String getPlainDisplayName() {
        return null;
    }

    @Override
    public ChatColor getPrimaryColor() {
        return null;
    }

    @Override
    public int getGridLengthX() {
        return 10;
    }

    @Override
    public int getGridLengthZ() {
        return 10;
    }

    @Override
    public long getTimeToBuild(int level) {
        return 0;
    }

    @Override
    public Schematic getSchematic() {
        return Schematics.s.getSchematic(BuildingGlobals.TOWN_HALL_SCHEMATICS[getLevel()-1]);
    }

    @Override
    public Schematic getBrokenSchematic() {
        return Schematics.s.getSchematic(BuildingGlobals.BROKEN_TOWN_HALL_SCHEMATICS[getLevel()-1]);
    }

    @Override
    public int getMaxLevel() { return 1; }

    @Override
    public void startUpdates() {

    }

    @Override
    public void stopUpdates() {

    }
}
