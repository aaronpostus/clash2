package aaronpost.clashcraft.Islands;
import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Arenas.Arenas;
import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Buildings.BuildingInHand;
import aaronpost.clashcraft.Buildings.Wall;
import aaronpost.clashcraft.Interfaces.IFixedUpdatable;
import aaronpost.clashcraft.Interfaces.IUpdatable;
import aaronpost.clashcraft.Pair;
import aaronpost.clashcraft.PersistentData.IslandWrapper;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.*;

public class Island implements Serializable, IFixedUpdatable, IUpdatable {
    private BuildingInHand buildingInHand;
    private transient Player player;
    private transient Arena arena;
    // Grid of UUIDs used for pathfinding and building interaction. Generated on load.
    private transient UUID[][] nodes;
    // Map of UUIDs used to retrieve buildings by UUID quickly. Generated on load.
    private transient Map<UUID, Building> buildings;
    private List<Building> buildingsToSave;

    public enum Interactions {
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
    public void saveBuildings() {
        this.buildingsToSave = new ArrayList<>();
        buildingsToSave.addAll(buildings.values());
    }
    public void initializeUUIDGrid() {
        nodes = new UUID[Arenas.GRID_X_LENGTH][Arenas.GRID_Z_LENGTH];
        for (int i = 0; i < Arenas.GRID_X_LENGTH; i++) {
            for (int j = 0; j < Arenas.GRID_Z_LENGTH; j++) {
                nodes[i][j] = null;
            }
        }
        buildings = new HashMap<>();
        for(Building building: buildingsToSave) {
            this.buildings.put(building.getUUID(),building);
        }
        for(Building building: buildings.values()) {
            Pair<Integer,Integer> gridLoc = building.getGridLoc();
            for(int i = gridLoc.first; i < gridLoc.first + building.getGridLengthX(); i++) {
                for(int j = gridLoc.second; j < gridLoc.second + building.getGridLengthZ(); j++) {
                    nodes[i][j]=building.getUUID();
                }
            }
        }
    }

    public Building getBuildingInBuildingInHand() {
        if (!hasBuildingInHand()) {
            return null;
        }
        return buildingInHand.getBuilding();
    }

    public BuildingInHand getBuildingInHand() {
        return buildingInHand;
    }

    public void placeBuildingInHand() {
        buildingInHand.place();
    }

    public void removeBuildingInHand() {
        buildingInHand = null;
    }

    public boolean hasBuildingInHand() {
        return buildingInHand != null;
    }

    public boolean hasBuilding(int x, int z) {
        return nodes[x][z] != null;
    }

    public Building getBuilding(int x, int z) {
        return buildings.get(nodes[x][z]);
    }

    public List<Building> getBuildings() {
        return new ArrayList<>(this.buildings.values());
    }

    public void addBuilding(Building building, int x, int z) {
        if (canPlaceBuilding(building, x, z)) {
            addBuildingHelper(building, x, z);
        }
    }

    private void addBuildingHelper(Building building, int x, int z) {
        UUID buildingUUID = building.getUUID();
        //remove old building locations from grid
        for (int i = 0; i < Arenas.GRID_X_LENGTH; i++) {
            for (int j = 0; j < Arenas.GRID_Z_LENGTH; j++) {
                if (nodes[i][j] != null) {
                    if (nodes[i][j].equals(buildingUUID)) {
                        nodes[i][j] = null;
                    }
                }
            }
        }
        // add new building locations to grid
        for (int i = x; i < x + building.getGridLengthX(); i++) {
            for (int j = z; j < z + building.getGridLengthZ(); j++) {
                nodes[i][j] = buildingUUID;
            }
        }
        if (!buildings.containsKey(buildingUUID)) {
            buildings.put(buildingUUID, building);
        }
    }

    // Needs to account for fitting within the 40x40 grid, not colliding with other buildings, but can "collide" with itself.
    public boolean canPlaceBuilding(Building building, org.bukkit.Location loc) {
        if (!arena.isValidGridLocation(loc)) {
            return false;
        }
        Pair<Integer, Integer> gridPos = arena.getGridLocFromAbsLoc(loc);
        return canAddBuildingHelper(building, gridPos);
    }

    public List<Pair<Integer, Integer>> getBuildingGridLocs(Building building) {
        List<Pair<Integer, Integer>> gridLocs = new ArrayList<>();
        for (int i = 0; i < Arenas.GRID_X_LENGTH; i++) {
            for (int j = 0; j< Arenas.GRID_Z_LENGTH; j++) {
                if(this.nodes[i][j].equals(building)) {
                    gridLocs.add(new Pair<>(i,j));
                }
            }
        }
        return gridLocs;
    }
    public Arena getArena() { return arena; }
    public boolean canPlaceBuilding(Building building, int x, int z) {
        return canAddBuildingHelper(building,new Pair<Integer,Integer>(x,z));
    }
    private boolean canAddBuildingHelper(Building building, Pair<Integer,Integer> gridPos) {
        int x = gridPos.first;
        int z = gridPos.second;
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
            Pair<Integer,Integer> gridLoc = arena.getGridLocFromAbsLoc(targetBlock);
            return getBuilding(gridLoc.first,gridLoc.second);
        }
        return null;
    }
    public void putBuildingInHand(Building building) {
        this.buildingInHand = new BuildingInHand(building, arena);
    }
    public Pair<Integer,Integer> getRandomWalkableLoc(Building building, Random random) {
        List<Pair<Integer, Integer>> ring = getOuterRing(building);
        return ring.get(random.nextInt(ring.size()));
    }
    public Location getCenterBuildingLoc(Building building, int heightAboveGround) {
        Pair<Integer,Integer> gridLoc = building.getGridLoc();
        Location loc = arena.getAbsLocationFromGridLoc(gridLoc.first, gridLoc.second, heightAboveGround);
        loc.setX(loc.getX() + (building.getGridLengthX() / 2f));
        loc.setZ(loc.getZ() + (building.getGridLengthZ() / 2f));
        return loc;
    }
    // Get a list of locations in the outer ring for a building
    public List<Pair<Integer, Integer>> getOuterRing(Building building) {
        List<Pair<Integer,Integer>> outerRing = new ArrayList<>();
        Pair<Integer,Integer> buildingLoc = building.getGridLoc();
        for(int x = buildingLoc.first; x < buildingLoc.first + building.getGridLengthX(); x++) {
            outerRing.add(new Pair<>(x, buildingLoc.second));
        }
        for(int z = buildingLoc.second + 1; z < buildingLoc.second + building.getGridLengthZ(); z++) {
            outerRing.add(new Pair<>(buildingLoc.first + building.getGridLengthX() -1, z));
        }
        for(int x = buildingLoc.first + building.getGridLengthX() - 2; x >= buildingLoc.first; x--) {
            outerRing.add(new Pair<>(x, buildingLoc.second + building.getGridLengthZ() - 1));
        }
        for(int z = buildingLoc.second + building.getGridLengthZ() - 2; z >= buildingLoc.second; z--) {
            outerRing.add(new Pair<>(buildingLoc.first, z));
        }
        return outerRing;
    }
    public Pair<Integer,Integer> getAdjacentWall(int x, int z) {
        if(hasWall(x+1,z)) {
            return new Pair<>(x+1,z);
        }
        if(hasWall(x-1,z)) {
            return new Pair<>(x-1,z);
        }
        if(hasWall(x,z+1)) {
            return new Pair<>(x,z+1);
        }
        if(hasWall(x,z-1)) {
            return new Pair<>(x,z-1);
        }
        return null;
    }
    private boolean hasWall(int x, int z) {
        if(!arena.isValidGridLocation(x,z)) {
            return false;
        }
        if(!hasBuilding(x,z)) {
            return false;
        }
        return getBuilding(x, z) instanceof Wall;
    }
    // Get a list of locations in the inner locs for a building
    public List<Pair<Integer, Integer>> getInnerLocs(Building building) {
        List<Pair<Integer,Integer>> innerLocs = new ArrayList<>();
        Pair<Integer,Integer> buildingLoc = building.getGridLoc();
        for(int i = buildingLoc.first + 1; i < buildingLoc.first + building.getGridLengthX() - 1; i++) {
            for(int j = buildingLoc.second + 1; j < buildingLoc.second + building.getGridLengthZ() - 1; j++) {
                innerLocs.add(new Pair<>(i,j));
            }
        }
        return innerLocs;
    }
    public List<Pair<Integer, Integer>> getLocs(Building building) {
        List<Pair<Integer,Integer>> locs = new ArrayList<>();
        Pair<Integer,Integer> buildingLoc = building.getGridLoc();
        for(int i = buildingLoc.first; i < buildingLoc.first + building.getGridLengthX(); i++) {
            for(int j = buildingLoc.second; j < buildingLoc.second + building.getGridLengthZ(); j++) {
                locs.add(new Pair<>(i,j));
            }
        }
        return locs;
    }
    @Override
    public void update() {
        if(buildingInHand != null) {
            buildingInHand.update();
        }
    }
    @Override
    public void fixedUpdateRequest() {
        for(Building building : getBuildings()) {
            building.fixedUpdateRequest();
        }
    }

    @Override
    public void catchUpRequest(float hours) {
        for(Building building : getBuildings()) {
            building.catchUpRequest(hours);
        }
    }
    public void refreshReferences(Arena arena) {
        this.arena = arena;
        this.player = arena.getPlayer();
        for(Building building : getBuildings()) {
            building.refreshReferences(arena);
        }
        if(buildingInHand != null) {
            this.buildingInHand.setArena(arena);
        }
        Location location = arena.getLoc();
    }
    public void loadBuildings() {
        for(Building building: getBuildings()) {
            building.paste();
        }
    }
    @Override
    public void startUpdates() {
        loadBuildings();
        if(buildingInHand != null) {
            player.sendMessage(ChatColor.GRAY + "You have a building in your hand: " +
                    buildingInHand.getBuilding().getPlainDisplayName());
            buildingInHand.startUpdates();
        }
        for(Building building : getBuildings()) {
            building.startUpdates();
        }
    }

    @Override
    public void stopUpdates() {
        for(Building building: getBuildings()) {
            building.resetToGrass();
        }
        Building buildingInHand = getBuildingInBuildingInHand();
        if(buildingInHand != null) {
            this.buildingInHand.stopUpdates();
            if(!buildingInHand.isNewBuilding()) {
                removeBuildingInHand();
            }
        }
        for(Building building : getBuildings()) {
            building.stopUpdates();
        }
        this.arena = null;
    }
}
