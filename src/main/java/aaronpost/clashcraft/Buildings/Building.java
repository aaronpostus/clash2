package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Interfaces.IDisplayable;
import aaronpost.clashcraft.Interfaces.IFixedUpdatable;
import aaronpost.clashcraft.Schematics.Schematic;
import aaronpost.clashcraft.Schematics.Schematics;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serializable;
import java.time.Duration;
import java.util.UUID;

// Author: Aaron Post
public abstract class Building implements IDisplayable, IFixedUpdatable, Serializable {
    private final UUID uuid;
    private int x, z;
    private Schematic schematic;
    private transient Arena arena;
    private transient Location absoluteLocation;
    private int level = 1;
    private boolean isNewBuilding;
    public Building(int x, int z) {
        this.uuid = UUID.randomUUID();
        this.x = x;
        this.z = z;
        this.isNewBuilding = true;
    }
    public void setArena(Arena arena) {
        this.arena = arena;
        updateAbsoluteLocation();
    }
    private void updateAbsoluteLocation() {
        this.absoluteLocation = arena.getLoc().clone();
        this.absoluteLocation.setX(x + absoluteLocation.getX());
        this.absoluteLocation.setZ(z + absoluteLocation.getZ());
    }
    public void setRelativeLocation(int x, int z) {
        this.x = x;
        this.z = z;
        updateAbsoluteLocation();
    }
    boolean place() {
        // try to place, if i can't i return false

        return false;
    }
    boolean pickup() {
        // try to pickup, if i cant i return false

        return false;
    }
    boolean upgrade() {
        // try to upgrade, if i cant i return false
        return false;
    }
    void click() {

    }
    public UUID getUUID() {
        return uuid;
    }

    // setters and getters
    public void setSchematic(String schematicName) {
        this.schematic = Schematics.s.getSchematic(schematicName);
    }
    public ItemStack getItemStack() {
        ItemStack itemStack = getPlainItemStack();
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(getDisplayName());
        itemStack.setItemMeta(meta);
        return itemStack;
    }
    public String getDisplayName() {
        return getPlainDisplayName() + ChatColor.GRAY + " Level " + getLevel();
    }
    public abstract ItemStack getPlainItemStack();
    public abstract String getPlainDisplayName();
    public abstract ChatColor getPrimaryColor();
    public int getGridLengthX() {
        return x;
    }
    public int getGridLengthZ() {
        return z;
    }
    public void paste(Arena a) {
        schematic.pasteSchematic(absoluteLocation, 0);
        ClashCraft.plugin.getServer().getLogger().info("Pasted " + getPlainDisplayName() + " at " + absoluteLocation.toString());
    }
    public void resetToGrass(Arena a) {
        schematic.resetToGrassLand(absoluteLocation.clone());
        for(Entity entity: Globals.world.getNearbyEntities(absoluteLocation, 7, 7,7)) {
            if(entity.getType().equals(EntityType.DROPPED_ITEM)) {
                entity.remove();
            }
        }
    }
    public abstract boolean isMaxLevel();
    public abstract Duration getUpgradeTime();
    public int getLevel() {
        return level;
    }

    public boolean isNewBuilding() {
        return isNewBuilding;
    }
}
