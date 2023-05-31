package aaronpost.clashcraft.Islands;
import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Arenas.Arenas;
import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Buildings.BuildingInHand;
import aaronpost.clashcraft.Interfaces.IFixedUpdatable;
import aaronpost.clashcraft.Interfaces.IUpdatable;
import aaronpost.clashcraft.Pair;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import javax.xml.stream.Location;
import java.io.Serializable;
import java.util.*;

public class Island implements Serializable, IFixedUpdatable, IUpdatable {
    private BuildingInHand buildingInHand;
    private transient Location location;
    private transient Player player;
    private transient Arena arena;
    private UUID[][] nodes;
    private Map<UUID, Building> buildings;
    public static enum Interactions {
        LEFT_CLICK, RIGHT_CLICK
    }
    public Island() {
        buildings = new HashMap<>();
        nodes = new UUID[Arenas.GRID_X_LENGTH][Arenas.GRID_Z_LENGTH];
        for (int i = 0; i < Arenas.GRID_X_LENGTH; i++) {
            for (int j = 0; j < Arenas.GRID_Z_LENGTH; j++) {
                nodes[i][j] = null;
            }
        }
    }
    public Building getBuildingInHand() {
        if(!hasBuildingInHand()) {
            return null;
        }
        Building building = buildingInHand.getBuilding();
        return building;
    }
    public void removeBuildingInHand() {
        buildingInHand = null;
    }
    public boolean hasBuildingInHand() { return buildingInHand != null; }
    public Building getBuilding(int x, int z) {
        return buildings.get(nodes[x][z]);
    }
    public List<Building> getBuildings() {
        return new ArrayList<>(this.buildings.values());
    }
    public void addBuilding(Building building, int x, int z) {
        if(canAddBuilding(building, x, z)) {
            addBuildingHelper(building, x, z);
        }
    }
    private void addBuildingHelper(Building building, int x, int z) {
        UUID buildingUUID = building.getUUID();
        //remove old building locations from grid
        for(int i = x; i < x + building.getGridLengthX(); i++) {
            for(int j = z; j < z + building.getGridLengthZ(); j++) {
                if(nodes[i][j] == buildingUUID) {
                    nodes[i][j] = null;
                }
            }
        }
        // add new building locations to grid
        for(int i = x; i < x + building.getGridLengthX(); i++) {
            for(int j = z; j < z + building.getGridLengthZ(); j++) {
                nodes[i][j] = buildingUUID;
            }
        }
        buildings.put(buildingUUID, building);
    }
    // Needs to account for fitting within the 40x40 grid, not colliding with other buildings, but can "collide" with itself.
    public boolean canAddBuilding(Building building, org.bukkit.Location loc) {
        if(!arena.isValidGridLocation(loc)) {
            return false;
        }
        Pair<Double,Double> gridPos = arena.getGridLocFromAbsLoc(loc);
        return canAddBuildingHelper(building, gridPos);
    }
    public boolean canAddBuilding(Building building, int x, int z) {
        return canAddBuildingHelper(building,new Pair<Double,Double>((double) x,(double)z));
    }
    private boolean canAddBuildingHelper(Building building, Pair<Double,Double> gridPos) {
        int x = gridPos.first.intValue();
        int z = gridPos.second.intValue();
        int xLength = building.getGridLengthX();
        int zLength = building.getGridLengthZ();
        if(!(x + xLength <= Arenas.GRID_X_LENGTH && z + zLength <= Arenas.GRID_Z_LENGTH && x >= 0 && z >= 0)) {
            return false;
        }
        for (int i = x; i < (x + xLength); i++) {
            for (int j = z; j < (z + zLength); j++) {
                Building building2 = getBuilding(i, j);
                if(building2 != null && !(building.equals(building2))) {
                    return false;
                }
            }
        }
        return true;
    }

    // Returns the building at a given location. If there is no building (or it's not a valid location) we return null
    public Building findBuildingAtLocation(org.bukkit.Location targetBlock) {
        if(arena.isValidGridLocation(targetBlock)) {
            Pair<Double,Double> gridLoc = arena.getGridLocFromAbsLoc(targetBlock);
            return getBuilding(gridLoc.first.intValue(),gridLoc.second.intValue());
        }
        return null;
    }
    public void putBuildingInHand(Building building) {
        this.buildingInHand = new BuildingInHand(building);
    }

    // maybe pass through a list of ICommands instead
    public void interactionQuery(Island.Interactions interactionType, int x, int y) {
        //
        Building building;
        switch (interactionType) {
            case LEFT_CLICK:
        }
    }
    @Override
    public void update() {
        if(buildingInHand != null) {
            buildingInHand.update();
        }
    }
    @Override
    public void fixedUpdate() {
        for(Building building : getBuildings()) {
            building.fixedUpdate();
        }
    }

    @Override
    public void catchUp(float hours) {
        for(Building building : getBuildings()) {
            building.catchUp(hours);
        }
    }
    public void setArena(Arena arena) {
        this.arena = arena;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    private void loadBuildings() {
        for(Building building: getBuildings()) {
            player.sendMessage(ChatColor.GRAY + "Loaded " + building.getPlainDisplayName() + ChatColor.GRAY
                    + " Level " + building.getLevel());
            building.paste(arena);
        }
        if(buildingInHand != null) {
            player.sendMessage(ChatColor.GRAY + "You have a building in your hand: " +
                    buildingInHand.getBuilding().getPlainDisplayName());
        }
    }
    @Override
    public void startUpdates() {
        loadBuildings();
        if(buildingInHand != null) {
            buildingInHand.startUpdates();
        }
        for(Building building : getBuildings()) {
            building.startUpdates();
        }
    }

    @Override
    public void stopUpdates() {
        for(Building building: getBuildings()) {
            building.resetToGrass(arena);
        }
        Building buildingInHand = getBuildingInHand();
        if(buildingInHand != null) {
            if(!buildingInHand.isNewBuilding()) {
                removeBuildingInHand();
            }
        }

        if(buildingInHand != null) {
            buildingInHand.stopUpdates();
        }
        for(Building building : getBuildings()) {
            building.stopUpdates();
        }
        this.arena = null;
    }
}
