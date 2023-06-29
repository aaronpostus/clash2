package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Buildings.BuildingMenus.DefaultBuildingMenu;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Schematics.Schematic;
import aaronpost.clashcraft.Singletons.Schematics;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class TownHall extends Building {
    public TownHall(int x, int z) {
        super(x,z);
    }
    @Override
    public boolean storesCurrency() { return true; }
    @Override
    public int getStorageCapacity(String currencyType) {
        return BuildingGlobals.TOWN_HALL_STORAGE_CAPACITY.get(getLevel()-1).get(currencyType);
    }
    @Override
    public List<String> storageCurrencies() { return BuildingGlobals.TOWN_HALL_STORAGE_CURRENCIES; }
    @Override
    public void click() {
        openMenu();
    }

    @Override
    public void openMenu() {
        ClashCraft.guiManager.openGUI(new DefaultBuildingMenu(this),getArena().getPlayer());
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
        return BuildingGlobals.TOWN_HALL_ITEM_STACK.clone();
    }

    @Override
    public String getPlainDisplayName() {
        return BuildingGlobals.TOWN_HALL_DISPLAY_NAME;
    }

    @Override
    public ChatColor getPrimaryColor() {
        return ChatColor.GOLD;
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
        return BuildingGlobals.TOWN_HALL_BUILD_TIME[level-1];
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
    public int getMaxLevel() { return 2; }

    @Override
    public void startUpdates() {

    }

    @Override
    public void stopUpdates() {

    }
}
