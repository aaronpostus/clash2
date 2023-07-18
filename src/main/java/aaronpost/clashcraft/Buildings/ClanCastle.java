package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Interfaces.IInstantBuild;
import aaronpost.clashcraft.Schematics.Schematic;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

/**
 * Note: level 1 is ruin. level 2 is actually level 1
 */
public class ClanCastle extends Building implements IInstantBuild {
    public ClanCastle(int x, int z) {
        super(x,z);
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
        return 6;
    }

    @Override
    public int getGridLengthZ() {
        return 6;
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
