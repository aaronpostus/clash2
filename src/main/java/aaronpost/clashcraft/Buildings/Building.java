package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.BuildingStates.*;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Buildings.BuildingStates.IBuildingState;
import aaronpost.clashcraft.Interfaces.IDisplayable;
import aaronpost.clashcraft.Interfaces.IFixedUpdatable;
import aaronpost.clashcraft.Schematics.Schematic;
import aaronpost.clashcraft.Session;
import aaronpost.clashcraft.Singletons.Sessions;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import java.io.Serializable;
import java.util.UUID;

// Author: Aaron Post
public abstract class Building implements IDisplayable, IFixedUpdatable, Serializable {
    public IBuildingState state;
    private final UUID uuid;
    private int x, z, level = 1, nextLevel = 1;
    private transient Arena arena;
    private transient Session session;
    private transient Location absoluteLocation;
    private long buildTime;
    private transient int layersBuilt = 0;
    // New building in hand
    public Building(Arena arena) {
        this.uuid = UUID.randomUUID();
        this.x=-1;
        this.z=-1;
        this.arena = arena;
        this.state = new InHandNewState(this);
    }
    // Add level 1 building that doesn't have to build (townhall, clan castle, etc.)
    public Building(int x, int z) {
        this.uuid = UUID.randomUUID();
        this.x = x;
        this.z = z;
        this.state = new IslandState(this);
    }
    public void refreshReferences(Arena arena) {
        this.arena = arena;
        this.session = Sessions.s.getSession(arena.getPlayer());
        this.state.refreshReferences(this);
        updateAbsoluteLocation();
    }
    private void updateAbsoluteLocation() {
        this.absoluteLocation = this.arena.getLoc().clone();
        this.absoluteLocation.setX(x + absoluteLocation.getX());
        this.absoluteLocation.setZ(z + absoluteLocation.getZ());
    }
    public void setRelativeLocation(int x, int z) {
        this.x = x;
        this.z = z;
        updateAbsoluteLocation();
    }
    public void clickRequest() {
        state.click();
    }
    public void catchUpRequest(float hoursToCatchUp) {
        state.catchUp(hoursToCatchUp);
    }
    public void openMenuRequest() {
        state.openMenu();
    }
    @Override
    public void fixedUpdateRequest() {
        state.update();
    }
    public void pickupRequest() {
        state.pickup();
    }
    public void placeRequest(int x, int z) {
        state.place(x,z);
    }
    public void visualUpdateRequest() {
        state.visualUpdate();
    }
    // abstract methods -- you probably meant to access a "request" method instead.
    public abstract void click();
    public abstract void openMenu();
    public abstract void catchUp(float hoursToCatchUp);
    public abstract void update();
    public abstract void visualUpdate();
    public abstract ItemStack getPlainItemStack();
    public abstract String getPlainDisplayName();
    public abstract ChatColor getPrimaryColor();
    public abstract int getGridLengthX();
    public abstract int getGridLengthZ();
    public abstract long getTimeToBuild(int level);
    public abstract Schematic getSchematic();
    public abstract int getMaxLevel();
    public boolean isMaxLevel() {
        return getLevel() == getMaxLevel();
    }
    // getters
    public Arena getArena() {
        return arena;
    }
    public Session getSession() { return session; }
    public UUID getUUID() { return uuid; }
    public int getNextLevel() { return nextLevel; }
    public String getDisplayName() { return getPlainDisplayName() + ChatColor.GRAY + " Level " + getLevel(); }
    public long getRemainingUpdateTime() { return getTimeToBuild(nextLevel); }
    public int getLayersBuilt() { return layersBuilt; }
    public void setLayersBuilt(int layers) { this.layersBuilt = layers; }
    public float getPercentageBuilt() { return (buildTime / (float) getTimeToBuild(nextLevel)); }
    public int getLevel() { return level; }

    public boolean isNewBuilding() { return state instanceof InHandNewState; }
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
    public void sendMessage(String message) {
        arena.sendActionBar(getPlainDisplayName() + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + message);
    }
    public void place(int x, int z) {
        setRelativeLocation(x,z);
        startUpdates();
        paste();
    }
    public void pickup() {
        arena.getIsland().putBuildingInHand(this);
        resetToGrass();
    }
    public void buildStep() {
        this.buildTime += 1;
    }
    public boolean upgrade() {
        if(state instanceof InHandNewState) {
            nextLevel = 1;
        }
        state = new BuildingState(this);
        paste();
        return false;
    }
    public void paste() {
        updateAbsoluteLocation();
        Schematic schematic = getSchematic();
        if(state instanceof BuildingState) {
            schematic.pasteSchematicConstruction(absoluteLocation, schematic.layersToBuild(getPercentageBuilt()));
            return;
        }
        getSchematic().pasteSchematic(absoluteLocation);
    }
    public void resetToGrass() {
        getSchematic().resetToGrassLand(absoluteLocation.clone());
        for(Entity entity: Globals.world.getNearbyEntities(absoluteLocation, 7, 7,7)) {
            if(entity.getType().equals(EntityType.DROPPED_ITEM)) {
                entity.remove();
            }
        }
    }
}
