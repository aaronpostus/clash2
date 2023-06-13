package aaronpost.clashcraft.Raiding.TroopAI;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Arenas.Arenas;
import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Buildings.Wall;
import aaronpost.clashcraft.Islands.Island;
import aaronpost.clashcraft.Pair;
import aaronpost.clashcraft.Raiding.IslandNavGraph;
import org.bukkit.Material;
import pathfinding.grid.GridCell;

import java.util.ArrayList;
import java.util.List;

public class TroopAgent {
    private final Pair<Integer,Integer> agentPos;
    private final Island island;
    private final Arena arena;
    private final IslandNavGraph navGraph;

    public TroopAgent(Arena arena,IslandNavGraph navGraph, int x, int z) {
        this.arena = arena;
        this.island = arena.getIsland();
        this.navGraph = navGraph;
        this.agentPos = new Pair<>(x,z);
    }
    public void setAgentOptions(TroopAgentOptions options) {
    }

    public List<Building> pickBuildings() {
        List<Building> closestBuildings = new ArrayList<>();
        float[] shortestDistances = {Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE};
        Building[] selectedBuildings = {null, null, null};

        for (Building building : navGraph.buildingCenters.keySet()) {
            Pair<Integer, Integer> buildingCenter = navGraph.buildingCenters.get(building);
            float distance = (float) Math.sqrt(Math.pow(buildingCenter.first - agentPos.first, 2) + Math.pow(buildingCenter.second - agentPos.second, 2));
            // Update the closest buildings if a closer one is found
            for (int i = 0; i < 3; i++) {
                if (distance < shortestDistances[i]) {
                    // Shift the previous closest buildings down the list
                    for (int j = 2; j > i; j--) {
                        shortestDistances[j] = shortestDistances[j - 1];
                        selectedBuildings[j] = selectedBuildings[j - 1];
                    }
                    // Insert the new closest building
                    shortestDistances[i] = distance;
                    selectedBuildings[i] = building;
                    break;
                }
            }
        }
        // Add the selected buildings to the list
        for (Building building : selectedBuildings) {
            if (building != null) {
                closestBuildings.add(building);
            }
        }
        return closestBuildings;
    }
    public Building pickBuilding(List<Building> buildings) {
        for(int i=0; i < Arenas.NAV_GRID_X_LENGTH; i++){
            for(int j=0; j < Arenas.NAV_GRID_Z_LENGTH; j++) {
                arena.getAbsLocationFromNavGridLoc(i,j, 9).getBlock().setType(Material.AIR);
            }
        }
        int cheapestPathCost = Integer.MAX_VALUE;
        Building cheapestBuilding = null;
        for(Building building: buildings) {
            Pair<Integer,List<GridCell>> buildingPath = calculateCheapestPath(agentPos.first,agentPos.second, building);
            if(buildingPath.first < cheapestPathCost) {
                cheapestPathCost = buildingPath.first;
                cheapestBuilding = building;
            }
        }
        if(cheapestBuilding == null) {
            return null;
        }
        List<GridCell> cells = calculateCheapestPath(agentPos.first,agentPos.second, cheapestBuilding).second;
        if(cells == null) {
            return null;
        }
        for(GridCell cell: cells){
            arena.getAbsLocationFromNavGridLoc(cell.x, cell.y,9).getBlock().setType(Material.BLUE_STAINED_GLASS);
        }
        return cheapestBuilding;
    }
    public Pair<Integer,List<GridCell>> calculateCheapestPath(int x1, int z1, Building building) {
        int cheapestPathCost = Integer.MAX_VALUE;
        Pair<Integer,Integer> targetLoc = null;
        List<GridCell> cheapestPath = null;
        // find cheapest path without breaking any walls (if there is one)
        for(Pair<Integer,Integer> gridLoc: navGraph.outerRings.get(building)) {
            int x2 = gridLoc.first + IslandNavGraph.NAV_OFFSET;
            int z2 = gridLoc.second + IslandNavGraph.NAV_OFFSET;
            List<GridCell> path = navGraph.path(x1,z1,x2,z2);
            if(path!=null) {
                int pathCost = navGraph.getCost(path);
                if(pathCost<cheapestPathCost) {
                    cheapestPathCost = pathCost;
                    targetLoc = new Pair<>(x2,z2);
                }
            }
        }
        boolean breakwall = false;
        Wall targetWall = null;
        // find valid path by breaking a wall if there wasnt one (or see if there is a cheaper path by breaking a wall)
        for(Pair<Integer,Integer> gridLoc: navGraph.outerRings.get(building)) {
            Pair<Integer,Integer> wallToBreak = island.getAdjacentWall(gridLoc.first, gridLoc.second);
            int x2 = gridLoc.first + IslandNavGraph.NAV_OFFSET;
            int z2 = gridLoc.second + IslandNavGraph.NAV_OFFSET;
            if(wallToBreak != null) {
                Wall wall = (Wall) island.getBuilding(wallToBreak.first,wallToBreak.second);
                List<GridCell> path = navGraph.pathIfWallDestroyed(x1,z1,x2,z2,wall);
                if(path != null) {
                    int pathCost = navGraph.getCost(path);
                    if(pathCost<cheapestPathCost) {
                        breakwall = true;
                        cheapestPathCost = pathCost;
                        targetLoc = new Pair<>(x2,z2);
                        targetWall = wall;
                    }
                }
            }
        }
        if(targetLoc == null) {
            return null;
        }
        if(breakwall) {
            return new Pair<>(cheapestPathCost,navGraph.pathIfWallDestroyed(x1,z1,targetLoc.first,targetLoc.second,targetWall));
        }
        return new Pair<>(cheapestPathCost,navGraph.path(x1,z1,targetLoc.first,targetLoc.second));
    }
}
