package aaronpost.clashcraft.Buildings;

import aaronpost.clashcraft.Interfaces.IUpdatable;
import org.bukkit.Location;
import java.io.Serializable;
import java.time.Duration;

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
    private boolean isNewBuilding;
    // Cant be saved
    private Building building;

    //Player p = new Player

    /**
     * @requires Player to already be assigned to an arena.
     * @param building
     */
    public BuildingInHand(Building building) {
        this.building = building;
        this.isNewBuilding = building.isNewBuilding();
    }

    // Will check if schematic needs an update.
    public boolean checkIfSchematicNeedsUpdate(Location location) {
        // 500 is definitely overkill. This could be lowered to about 100 and probably work perfectly.
        //Location location = player.getTargetBlockExact(500).getLocation();
        int x = location.getBlockX();
        int z = location.getBlockZ();
        //if(x != this.x || z != this.z) {
            // Needs to check if building can fit in target location.
            //if(Arenas.a.findPlayerArena(player).fitSquareAtGridLoc(building)) {
                //return true;
            //}
        //}
        return false;
    }
    public Building getBuilding() {
        return building;
    }

    @Override
    public void update() {

    }

    @Override
    public void catchUp(Duration duration) {

    }

    @Override
    public void startUpdates() {

    }

    @Override
    public void stopUpdates() {

    }
}
