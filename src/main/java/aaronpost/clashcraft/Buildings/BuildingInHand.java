package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Interfaces.IUpdatable;
import aaronpost.clashcraft.Islands.Island;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private Building building;
    private transient Arena arena;
    private transient Island island;
    private transient Player player;
    private transient int x = -1, z = -1;
    private transient List<Block> blockSilhoutte = new ArrayList<>();

    //Player p = new Player

    /**
     * @requires Player to already be assigned to an arena.
     * @param building
     */
    public BuildingInHand(Building building, Arena arena) {
        this.building = building;
        this.arena = arena;
        this.island = arena.getIsland();
    }
    public Building getBuilding() { return building; }
    public void setArena(Arena arena) {
        this.arena = arena;
    }
    public void place() {
        //island.addBuilding();
        if(building.isNewBuilding()) {
            building.paste(arena);
        }
    }

    // Will check if schematic needs an update.
    private boolean targetLocIsDifferent(Location location) {
        int x = (int) Math.ceil(location.getX());
        int z = (int) Math.ceil(location.getZ());
        return (x != this.x) && (z != this.z);
    }

    private void removeOldSilhouette() {
        for(Block block: blockSilhoutte) {
            block.setType(Material.GRASS_BLOCK);
        }
        blockSilhoutte = new ArrayList<>();
    }
    private void addNewSilhouette(Location loc) {
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
                block.setType(Material.TARGET);
                blockSilhoutte.add(block);
            }
        }
    }
    private boolean playerHoldingBuilding() {
        ItemStack item = player.getInventory().getItemInMainHand();
        if(item == null) {
            return false;
        }
        if(!item.hasItemMeta()) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            return false;
        }
        return meta.getPersistentDataContainer().has(BuildingGlobals.NAMESPACED_KEY_IDENTIFIER, PersistentDataType.STRING);
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
        // idling at the same location so do nothing
        /**if(!targetLocIsDifferent(loc)) {
            return;
        }**/
        // player is not looking at a valid grid location or building cannot fit there
        if(!arena.isValidGridLocation(loc) || !island.canAddBuilding(building, loc)) {
            this.x = (int) Math.ceil(loc.getX());
            this.z = (int) Math.ceil(loc.getZ());
            removeOldSilhouette();
            return;
        }
        removeOldSilhouette();
        addNewSilhouette(loc);
        this.x = (int) Math.ceil(loc.getX());
        this.z = (int) Math.ceil(loc.getZ());
    }

    @Override
    public void catchUp(float hours) {

    }

    @Override
    public void startUpdates() {
        this.island = arena.getIsland();
        this.player = arena.getPlayer();
        building.stopUpdates();
        blockSilhoutte = new ArrayList<>();
    }
    @Override
    public void stopUpdates() {
        removeOldSilhouette();
        building.stopUpdates();
        System.out.println("stopping updates");
    }
}
