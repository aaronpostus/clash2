package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Globals.BuildingGlobals.BuildingStates;
import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Interfaces.IDisplayable;
import aaronpost.clashcraft.Interfaces.IFixedUpdatable;
import aaronpost.clashcraft.Schematics.Schematic;
import aaronpost.clashcraft.Singletons.Schematics;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.Serializable;
import java.time.Duration;
import java.util.UUID;

// Author: Aaron Post
public abstract class Building implements IDisplayable, IFixedUpdatable, Serializable {
    private BuildingStates state;
    private final UUID uuid;
    private int x, z;
    private transient Arena arena;
    private transient Location absoluteLocation;
    private int level = 1;
    public Building() {
        this(-1,-1);
        this.state = BuildingStates.InHandNew;
    }
    public Building(int x, int z) {
        this.uuid = UUID.randomUUID();
        this.x = x;
        this.z = z;
        this.state = BuildingStates.InHand;
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
    public boolean place(int x, int z) {
        setRelativeLocation(x,z);
        state = BuildingStates.IslandMode;
        startUpdates();
        paste(arena);
        return false;
    }
    public void pickup() {
        state = BuildingStates.InHand;
        arena.getIsland().putBuildingInHand(this);
    }
    public boolean upgrade() {
        // try to upgrade, if i cant i return false
        state = BuildingStates.Upgrading;
        return false;
    }
    public void click() {
        arena.getPlayer().sendMessage(getDisplayName() + " click");
    }
    public UUID getUUID() {
        return uuid;
    }
    public ItemStack getItemStack() {
        ItemStack itemStack = getPlainItemStack();
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(getDisplayName());
        PersistentDataContainer buildingData = meta.getPersistentDataContainer();
        buildingData.set(BuildingGlobals.NAMESPACED_KEY_UUID, PersistentDataType.STRING, getUUID().toString());
        buildingData.set(BuildingGlobals.NAMESPACED_KEY_IDENTIFIER, PersistentDataType.STRING, "building");
        itemStack.setItemMeta(meta);
        return itemStack;
    }
    public String getDisplayName() {
        return getPlainDisplayName() + ChatColor.GRAY + " Level " + getLevel();
    }
    public abstract ItemStack getPlainItemStack();
    public abstract String getPlainDisplayName();
    public abstract ChatColor getPrimaryColor();
    public abstract int getGridLengthX();
    public abstract int getGridLengthZ();
    public abstract Schematic getSchematic();
    public void paste(Arena a) {
        updateAbsoluteLocation();
        System.out.println(absoluteLocation);
        getSchematic().pasteSchematic(absoluteLocation);
        ClashCraft.plugin.getServer().getLogger().info("Pasted " + getPlainDisplayName() + " at " + absoluteLocation.toString());
    }
    public void resetToGrass(Arena a) {
        getSchematic().resetToGrassLand(absoluteLocation.clone());
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
        return state == BuildingStates.InHandNew;
    }
}
