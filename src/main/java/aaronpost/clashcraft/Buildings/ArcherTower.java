package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Schematics.Schematic;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class ArcherTower extends Building {
    public ArcherTower(Arena arena) {
        super(arena);
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
        return 0;
    }

    @Override
    public int getGridLengthZ() {
        return 0;
    }

    @Override
    public long getTimeToBuild(int level) {
        return 0;
    }

    @Override
    public Schematic getSchematic(int level) {
        return null;
    }

    @Override
    public Schematic getBrokenSchematic() {
        return null;
    }
}
