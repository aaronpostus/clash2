package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.BuildingMenus.DefaultBuildingMenu;
import aaronpost.clashcraft.Buildings.BuildingStates.*;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Commands.UpdateStorageCapacity;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Globals.GUIHelper;
import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Interfaces.IDisplayable;
import aaronpost.clashcraft.Interfaces.IFixedUpdatable;
import aaronpost.clashcraft.Interfaces.IInstantBuild;
import aaronpost.clashcraft.Pair;
import aaronpost.clashcraft.Schematics.Schematic;
import aaronpost.clashcraft.Session;
import aaronpost.clashcraft.Singletons.Schematics;
import aaronpost.clashcraft.Singletons.Sessions;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import java.io.Serializable;
import java.util.*;

// Author: Aaron Post
public abstract class Building implements IDisplayable, IFixedUpdatable, Serializable {
    public IBuildingState state;
    private final UUID uuid;
    private int x;
    private int z;
    private int nextLevel = 1;
    private transient Arena arena;
    private transient Session session;
    private transient Location absoluteLocation;
    private long buildTime;
    private transient int layersBuilt = 0;
    private int level = 1;
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
    public void click() { openMenu(); }
    public void openMenu() { ClashCraft.guiManager.openGUI(new DefaultBuildingMenu(this), getArena().getPlayer()); }
    public void catchUp(float hoursToCatchUp) { }
    public void update() { }
    public void startUpdates() { }
    public void stopUpdates() {
        state.stopUpdates();
    }
    public boolean storesCurrency() { return false; }
    public int getStorageCapacity(String currencyType) { return 0; }
    public List<String> storageCurrencies() { return new ArrayList<>(); }
    public void visualUpdate() { }
    public abstract ItemStack getPlainItemStack();
    public abstract String getPlainDisplayName();
    public abstract ChatColor getPrimaryColor();
    public abstract int getGridLengthX();
    public abstract int getGridLengthZ();
    public abstract long getTimeToBuild(int level);
    public abstract Schematic getSchematic(int level);
    public Schematic getSchematic() { return getSchematic(level); }
    public Schematic getNextSchematic() { return getSchematic(nextLevel); }
    public abstract Schematic getBrokenSchematic();
    public int getMaxLevel() { return 1; }
    public List<String> getUpgradeDescription() { return Arrays.asList("No upgrade description yet", "Implement this");}
    public ItemStack getUpgradeItem() {
        if(getLevel() == getMaxLevel()) {
            return Globals.MAXED_OUT_ITEM;
        }
        String name = ChatColor.GRAY + "Upgrade to Level " + (getLevel() + 1);
        return GUIHelper.attachNameAndLore(new ItemStack(Material.ARROW), name, getUpgradeDescription());
    }
    public int getMaxHitpoints() {
        return 100;
    }
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
    public long getRemainingUpdateTime() { return getTimeToBuild(nextLevel) - buildTime; }
    public int getLayersBuilt() { return layersBuilt; }
    public void setLayersBuilt(int layers) { this.layersBuilt = layers; }
    public float getPercentageBuilt() { return (buildTime / (float) getTimeToBuild(nextLevel)); }
    public int getLevel() {
        return level;
    }
    public boolean isNewBuilding() { return state instanceof InHandNewState; }
    public ItemStack getItemStack() {
        ItemStack itemStack = GUIHelper.attachNameAndData(getPlainItemStack(), getDisplayName(),
                BuildingGlobals.NAMESPACED_KEY_UUID, getUUID().toString());
        return GUIHelper.attachData(itemStack,BuildingGlobals.NAMESPACED_KEY_IDENTIFIER,"building");
    }
    public ItemStack getStatsItem() { return new ItemStack(Material.OAK_SIGN); }
    public void sendMessage(String message) {
        arena.sendActionBar(getPlainDisplayName() + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + message);
    }
    public void damage(int amountToDamage) {
        state.damage(amountToDamage);
    }
    public Pair<Integer, Integer> getGridLoc() {
        return new Pair<>(x,z);
    }
    public void place(int x, int z) {
        setRelativeLocation(x,z);
        startUpdates();
        paste();
    }
    public void pickup() {
        getArena().playSound(Sound.BLOCK_WOOD_BREAK,1f,1f);
        arena.getIsland().putBuildingInHand(this);
        resetToGrass();
        if(this instanceof Wall) {
            arena.carveWallGaps();
        }
    }
    public void buildStep() {
        this.buildTime += 1;
    }
    public void buildCatchup(float hours) {
        if(this.buildTime + (hours * 60 * 60) > getTimeToBuild(getNextLevel())) {
            this.buildTime = getTimeToBuild(getNextLevel());
            return;
        }
        this.buildTime += Math.floor(hours * 60 * 60);
    }
    public void finishBuilding() {
        new UpdateStorageCapacity().execute(arena);
    }
    public boolean upgrade() {
        if(state instanceof InHandNewState) {
            nextLevel = 1;
        }
        buildTime = 0;
        BuildingState newState = new BuildingState(this);
        state = newState;
        if(this instanceof IInstantBuild) {
            newState.finishBuilding();
        }
        paste();
        if(this instanceof Wall) {
            arena.carveWallGaps();
        }
        return false;
    }
    public void paste() {
        updateAbsoluteLocation();
        Schematic schematic;
        if(state instanceof BuildingState) {
            schematic = getNextSchematic();
            if(layersBuilt == -1) {
                switch(getGridLengthX()) {
                    case 4:
                        schematic = Schematics.s.getSchematic("2x2Giftbox");
                    case 6:
                        schematic = Schematics.s.getSchematic("3x3Giftbox");
                        break;
                    case 8:
                        schematic = Schematics.s.getSchematic("4x4Giftbox");
                        break;
                    default:
                        return;
                }
                schematic.pasteSchematic(absoluteLocation);
                return;
            }
            schematic.pasteSchematicConstruction(absoluteLocation, schematic.layersToBuild(getPercentageBuilt()));
            return;
        }
        getSchematic().pasteSchematic(absoluteLocation);
    }
    public void completeUpgrade() {
        this.level = this.nextLevel;
        this.nextLevel++;
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
