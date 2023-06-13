package aaronpost.clashcraft.Raiding;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Arenas.Arenas;
import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Buildings.Wall;
import aaronpost.clashcraft.Islands.Island;
import aaronpost.clashcraft.Pair;
import org.bukkit.Material;
import pathfinding.grid.GridCell;
import pathfinding.grid.NavigationGrid;
import pathfinding.grid.finders.AStarGridFinder;
import pathfinding.grid.finders.GridFinderOptions;

import java.util.*;

public class IslandNavGraph {
    private final GridCell[][] cells;
    private final NavigationGrid<GridCell> navGrid;
    private final AStarGridFinder<GridCell> finder;
    public static final int NAV_OFFSET = 2;
    private final Island island;
    private Arena arena;
    private final List<Building> buildings;
    public Map<Building, List<Pair<Integer, Integer>>> outerRings = new HashMap<>();
    public Map<Building, Pair<Integer,Integer>> buildingCenters = new HashMap<>();
    public IslandNavGraph(Island island) {
        this.island = island;
        this.arena = island.getArena();
        this.buildings = island.getBuildings();
        this.cells = new GridCell[Arenas.NAV_GRID_X_LENGTH][Arenas.NAV_GRID_Z_LENGTH];
        for(int i = 0; i < Arenas.NAV_GRID_X_LENGTH; i++) {
            for(int j = 0; j < Arenas.NAV_GRID_Z_LENGTH; j++) {
                this.cells[i][j] = new GridCell(true);
            }
        }
        // all cells walkable by default
        this.navGrid = new NavigationGrid<>(cells, true);
        // only allow the cells in the outer ring of buildings to be walkable
        for(Building building: buildings) {
            for(Pair<Integer,Integer> loc: island.getLocs(building)) {
                this.cells[loc.first+NAV_OFFSET][loc.second+NAV_OFFSET].building = building;
            }
            if(building instanceof Wall) {
                for(Pair<Integer, Integer> outerLoc : island.getOuterRing(building)) {
                    this.navGrid.setWalkable(outerLoc.first + NAV_OFFSET, outerLoc.second + NAV_OFFSET, false);
                }
            }
            else {
                outerRings.put(building, island.getOuterRing(building));
                Pair<Integer,Integer> gridLoc = building.getGridLoc();
                gridLoc.first += (int) Math.ceil(building.getGridLengthX() / 2f);
                gridLoc.second += (int) Math.ceil(building.getGridLengthZ() / 2f);
                buildingCenters.put(building, gridLoc);
                for (Pair<Integer, Integer> innerLoc : island.getInnerLocs(building)) {
                    this.navGrid.setWalkable(innerLoc.first + NAV_OFFSET, innerLoc.second + NAV_OFFSET, false);
                }
            }
        }
        GridFinderOptions opt = new GridFinderOptions();
        opt.allowDiagonal = true;
        this.finder = new AStarGridFinder<>(GridCell.class, opt);
        for(int i = 0; i < Arenas.NAV_GRID_X_LENGTH; i++) {
            for(int j = 0; j < Arenas.NAV_GRID_Z_LENGTH; j++) {
                Material mat = Material.GREEN_STAINED_GLASS;
                if(!navGrid.isWalkable(i,j)) {
                    if(this.cells[i][j].building instanceof Wall) {
                        mat = Material.ORANGE_STAINED_GLASS;
                    }
                    else {
                        mat = Material.RED_STAINED_GLASS;
                    }
                }
                arena.getAbsLocationFromNavGridLoc(i,j,10).getBlock().setType(mat);
            }
        }
        refreshNavMesh();
    }
    public void refreshNavMesh() {
        /**
        // only refresh navmesh of inner ring because outside is always walkable
        for(Building building: buildings) {

        }
        for(int i = 0; i < Arenas.GRID_X_LENGTH; i++) {
            for (int j = 0; j < Arenas.GRID_Z_LENGTH; j++) {
                // no building so it is walkable
                Building building = island.getBuilding(i,j);
                if(building == null) {
                    cells[i+2][j+2].setWalkable(true);
                    cells[i+2][j+2].state = GridCell.GridCellState.WALKABLE;
                }
                else if(building instanceof Wall) {

                }
                else {
                    List<Pair<Integer,Integer>> outerRing = island.getOuterRing(building);
                    if()
                }
            }
        }
         */
        //this.navGrid.set
    }
    /**public List<Building> getClosestBuildings(int x, int z) {

    }
    public int getCostToAttack(int x, int y, Building building) {
        int cost = Integer.MAX_VALUE;
        List<GridCell> pathNoWalls;
        if()
    }**/
    public int getCost(List<GridCell> path) {
        int cost = 0;
        for(GridCell gridCell: path) {
            if(gridCell.building instanceof Wall) {
                cost += 5;
            }
            else {
                cost += 1;
            }
        }
        return cost;
    }
    public List<GridCell> path(int x1, int z1, int x2, int z2) {
        return this.finder.findPath(x1,z1,x2,z2,navGrid);
    }
    public List<GridCell> pathIfWallsDestroyed(int x1, int z1, int x2, int z2, List<Wall> buildingsDestroyed) {
        // copy cells and make them walkable
        GridCell[][] cells = this.cells.clone();
        for (Building building: buildingsDestroyed) {
            Pair<Integer,Integer> gridLoc = building.getGridLoc();
            int x = gridLoc.first + 2;
            int z = gridLoc.second + 2;
            for(int i = x; i < x + building.getGridLengthX(); i++) {
                for(int j = z; j < z + building.getGridLengthZ(); j++) {
                    cells[i][j].setWalkable(true);
                }
            }
        }
        return this.finder.findPath(x1,z1,x2,z2, new NavigationGrid<>(cells, true));
    }
    public List<GridCell> pathIfWallDestroyed(int x1, int z1, int x2, int z2, Wall buildingsDestroyed) {
        return pathIfWallsDestroyed(x1,z1,x2,z2, Collections.singletonList(buildingsDestroyed));
    }
    //private boolean pathIfBuildingDestroyed()
}
