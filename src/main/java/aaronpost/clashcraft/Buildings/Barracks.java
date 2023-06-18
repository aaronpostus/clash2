package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.GUIS.BarracksMenu;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Schematics.Schematic;
import aaronpost.clashcraft.Singletons.Schematics;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Barracks extends Building {

    public Barracks(Arena arena) {
        super(arena);
    }

    @Override
    public void click() {
        openMenu();
    }

    @Override
    public void openMenu() {
        Player player = getArena().getPlayer();
        ClashCraft.guiManager.openGUI(new BarracksMenu(this,player), player);
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
        return BuildingGlobals.BARRACKS_ITEM_STACK;
    }

    @Override
    public String getPlainDisplayName() {
        return BuildingGlobals.BARRACKS_DISPLAY_NAME;
    }

    @Override
    public ChatColor getPrimaryColor() {
        return BuildingGlobals.BARRACKS_COLOR;
    }

    @Override
    public int getGridLengthX() {
        return BuildingGlobals.BARRACKS_GRID_LENGTH;
    }

    @Override
    public int getGridLengthZ() {
        return BuildingGlobals.BARRACKS_GRID_LENGTH;
    }

    @Override
    public long getTimeToBuild(int level) {
        return BuildingGlobals.BARRACKS_BUILD_TIME[level - 1];
    }

    @Override
    public Schematic getSchematic() {
        return Schematics.s.getSchematic(BuildingGlobals.BARRACKS_SCHEMATICS[getLevel() - 1]);
    }
    @Override
    public Schematic getBrokenSchematic() {
        return Schematics.s.getSchematic(BuildingGlobals.BROKEN_BARRACKS_SCHEMATICS[getLevel() - 1]);
    }

    @Override
    public int getMaxLevel() {
        return BuildingGlobals.BARRACKS_MAX_LEVEL;
    }

    @Override
    public void startUpdates() {

    }

    @Override
    public void stopUpdates() {

    }
}
