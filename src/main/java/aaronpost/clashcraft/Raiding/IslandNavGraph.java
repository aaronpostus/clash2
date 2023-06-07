package aaronpost.clashcraft.Raiding;

import aaronpost.clashcraft.Arenas.Arenas;
import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Islands.Island;
import aaronpost.clashcraft.Pair;
import pathfinding.grid.GridCell;
import pathfinding.grid.NavigationGrid;
import pathfinding.grid.finders.AStarGridFinder;
import pathfinding.grid.finders.GridFinderOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IslandNavGraph {
    private final GridCell[][] cells;
    private final NavigationGrid<GridCell> navGrid;
    private final AStarGridFinder<GridCell> finder;
    private final Map<Building, List<Pair<Integer,Integer>>> buildingGridLocs;
    private final Island island;
    public IslandNavGraph(Island island) {
        this.island = island;
        this.buildingGridLocs = initializeBuildingGridLocs(island);
        this.cells = new GridCell[Arenas.GRID_X_LENGTH + 2][Arenas.GRID_Z_LENGTH+2];
        for(int i = 0; i < Arenas.GRID_X_LENGTH + 2; i++) {
            for (int j = 0; j < Arenas.GRID_Z_LENGTH + 2; j++) {
                // if there is a building at a cell, set it to not be walkable
                cells[i][j].setWalkable(!island.hasBuilding(i,j));
            }
        }
        this.navGrid = new NavigationGrid<>(cells, true);
        GridFinderOptions opt = new GridFinderOptions();
        opt.allowDiagonal = false;
        this.finder = new AStarGridFinder<>(GridCell.class, opt);
    }
    private Map<Building, List<Pair<Integer,Integer>>> initializeBuildingGridLocs(Island island) {
        Map<Building, List<Pair<Integer,Integer>>> map = new HashMap<>();
        for(Building building : island.getBuildings()) {
            map.put(building, island.getBuildingGridLocs(building));
        }
        return map;
    }
    public void setBuildingWalkable(Building building, boolean canWalk) {
        setBuildingWalkable(building, canWalk, this.cells);
    }
    private void setBuildingWalkable(Building building, boolean canWalk, GridCell[][] cells) {
        for(Pair<Integer,Integer> loc: buildingGridLocs.get(building)) {
            cells[loc.first][loc.second].setWalkable(canWalk);
        }
    }
    /**public List<Building> getClosestBuildings(int x, int z) {

    }
    public int getCostToAttack(int x, int y, Building building) {
        int cost = Integer.MAX_VALUE;
        List<GridCell> pathNoWalls;
        if()
    }**/
    public boolean hasPath(int x1, int z1, int x2, int z2) {
        return hasPath(x1,z1,x2,z2,navGrid);
    }
    private boolean hasPath(int x1, int z1, int x2, int z2, NavigationGrid<GridCell> navGrid) {
        return this.finder.findPath(x1,z1,x2,z2,navGrid) != null;
    }
    public List<GridCell> path(int x1, int z1, int x2, int z2) {
        return this.finder.findPath(x1,z1,x2,z2,navGrid);
    }
    public boolean pathIfBuildingsDestroyed(int x1, int z1, int x2, int z2, List<Building> buildingsDestroyed) {
        // copy cells and make them walkable
        GridCell[][] cells = this.cells.clone();
        for (Building building: buildingsDestroyed) {
            setBuildingWalkable(building, true, cells);
        }
        return hasPath(x1,z1,x2,z2, new NavigationGrid<>(cells, true));
    }
    //private boolean pathIfBuildingDestroyed()
}
