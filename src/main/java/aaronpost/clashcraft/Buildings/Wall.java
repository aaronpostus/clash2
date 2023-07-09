package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.BuildingMenus.DefaultBuildingMenu;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Schematics.Schematic;
import aaronpost.clashcraft.Singletons.Schematics;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class Wall extends Building {
    public Wall(Arena arena) {
        super(arena);
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
