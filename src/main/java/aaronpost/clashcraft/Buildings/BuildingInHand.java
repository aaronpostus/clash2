package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Interfaces.IUpdatable;
import aaronpost.clashcraft.Islands.Island;
import aaronpost.clashcraft.Pair;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BuildingInHand will follow the user's cursor on the island and paste a given building's schematic at that location,
 * and unpaste it as they move their cursor around. A special case MUST be considered for "new buildings" which won't
 * be created until they are placed. Buildings can be placed by left clicking, or "cancelled" by right clicking. Right
 * clicking for new buildings will prevent buying the building. Right clicking for old buildings will restore the building
 * to its original location. No actual data for existing buildings should be changed until it is actually placed back
 * down. This will be more efficient as well as redundant in case a player leaves before they place their building
 * back down. This listener should be deleted if the player leaves.
 */
public class BuildingInHand implements Serializable, IUpdatable {
    // Cant be saved
    private final Building building;
    private transient Arena arena;
    private transient Island island;
    private transient Player player;
    private transient int x = -1, z = -1;
    private transient int oldx, oldy;
    private transient Map<Block,Material> blockSilhoutte = new HashMap<>();

    //Player p = new Player

    /**
     * @requires Player to already be assigned to an arena.
     * @param building
     */
    public BuildingInHand(Building building, Arena arena) {
        this.building = building;
        this.building.refreshReferences(arena);
        this.arena = arena;
        this.island = arena.getIsland();
        Pair<Integer,Integer> gridLoc = building.getGridLoc();
        this.oldx = gridLoc.first;
        this.oldy = gridLoc.second;
    }
    public Building getBuilding() { return building; }
    public void setArena(Arena arena) {
        this.arena = arena;
        this.island = arena.getIsland();
        this.player = arena.getPlayer();
        this.x = -1;
        this.z = -1;
    }
    public void place() {
        building.refreshReferences(arena);
        building.placeRequest(x,z);
        island.addBuilding(building, x,z);
        island.removeBuildingInHand();
    }

    private void removeOldSilhouette() {
        for(Map.Entry<Block,Material> block: blockSilhoutte.entrySet()) {
            block.getKey().setType(block.getValue());
        }
        blockSilhoutte = new HashMap<>();
    }
    private void addSilhouette(Location loc) {
        int x = (int)Math.ceil(loc.getX());
        int z = (int)Math.ceil(loc.getZ());
        int y = (int)Math.floor(arena.getLoc().getY() - 1);
        for(int i = 0; i < building.getGridLengthX(); i++) {
            for (int j = 0; j < building.getGridLengthZ(); j++) {
                Location blockLoc = arena.getLoc().clone();
                blockLoc.setX(x + i);
                blockLoc.setY(y);
                blockLoc.setZ(z + j);
                Block block = blockLoc.getBlock();
                Material oldmat = block.getType();
                block.setType(Material.LIME_CONCRETE);
                blockSilhoutte.put(block,oldmat);
            }
        }
    }
    private void addCantPlaceSilhouette(Location loc) {
        Pair<Integer, Integer> gridLoc = arena.getGridLocFromAbsLoc(loc);
        int x = (int) Math.ceil(loc.getX());
        int z = (int) Math.ceil(loc.getZ());
        int y = (int) Math.floor(arena.getLoc().getY() - 1);
        for (int i = 0; i < building.getGridLengthX(); i++) {
            for (int j = 0; j < building.getGridLengthZ(); j++) {
                Location blockLoc = arena.getLoc().clone();
                blockLoc.setX(x + i);
                blockLoc.setY(y);
                blockLoc.setZ(z + j);
                int gridX = gridLoc.first + i;
                int gridZ = gridLoc.second + j;
                Block block = blockLoc.getBlock();
                if(shouldAddSilhoutte(gridX,gridZ, block)) {
                    Material mat = block.getType();
                    block.setType(Material.RED_CONCRETE);
                    blockSilhoutte.put(block,mat);
                }
            }
        }
    }
    private boolean shouldAddSilhoutte(int gridX, int gridZ, Block block) {
        if(!arena.isValidGridLocation(gridX, gridZ)) {
            return false;
        }
        if(!island.hasBuilding(gridX, gridZ))  {
            return true;
        }
        if(island.hasBuilding(gridX, gridZ)) {
            return island.getBuilding(gridX, gridZ).equals(building) || block.getType().equals(Material.MOSS_BLOCK);
        }
        return false;
    }
    private boolean playerHoldingBuilding() {
        if(this.player == null) {
            this.player = arena.getPlayer();
        }
        ItemStack item = player.getInventory().getItemInMainHand();
        if(!item.hasItemMeta()) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            return false;
        }
        return meta.getPersistentDataContainer().has(BuildingGlobals.NAMESPACED_KEY_IDENTIFIER, PersistentDataType.STRING);
    }
    public Pair<Integer,Integer> getSelectedGridPos() {
        return new Pair<>(x,z);
    }
    @Override
    public void update() {
        if(!playerHoldingBuilding()) {
            removeOldSilhouette();
            return;
        }
        Block targetBlock = player.getTargetBlockExact(500);
        // player is looking at the sky
        if(targetBlock == null) {
            this.x = -1;
            this.z = -1;
            removeOldSilhouette();
            return;
        }
        Location loc = targetBlock.getLocation();
        Location snappedLoc = getSnappedLocation(loc);
        // idling at the same location so do nothing
        /**if(!targetLocIsDifferent(loc)) {
            return;
        }**/
        // player is not looking at a valid grid location or building cannot fit there
        if(!arena.isValidGridLocation(snappedLoc)) {
            updateLoc(snappedLoc);
            removeOldSilhouette();
            return;
        }
        else if(!island.canPlaceBuilding(building, snappedLoc)) {
            removeOldSilhouette();
            addCantPlaceSilhouette(snappedLoc);
            updateLoc(snappedLoc);
            return;
        }

        removeOldSilhouette();
        addSilhouette(snappedLoc);
        updateLoc(snappedLoc);
    }
    private Location getSnappedLocation(Location loc) {
        Pair<Integer, Integer> gridLoc = arena.getGridLocFromAbsLoc(loc);
        if(gridLoc.first % 2 != 0) {
            loc.setX(loc.getX()-1);
        }
        if(gridLoc.second % 2 != 0) {
            loc.setZ(loc.getZ()-1);
        }
        return loc;
    }
    private void updateLoc(Location loc) {
        Pair<Integer, Integer> gridLoc = arena.getGridLocFromAbsLoc(loc);
        this.x = gridLoc.first;
        this.z = gridLoc.second;
    }

    @Override
    public void catchUpRequest(float hours) {

    }

    @Override
    public void startUpdates() {
        this.island = arena.getIsland();
        this.player = arena.getPlayer();
        building.stopUpdates();
        blockSilhoutte = new HashMap<>();
    }
    @Override
    public void stopUpdates() {
        removeOldSilhouette();
        System.out.println("1");
        this.building.stopUpdates();
    }
}
