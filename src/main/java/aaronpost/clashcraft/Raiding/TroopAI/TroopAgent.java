package aaronpost.clashcraft.Raiding.TroopAI;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Buildings.Wall;
import aaronpost.clashcraft.Islands.Island;
import aaronpost.clashcraft.Pair;
import aaronpost.clashcraft.Raiding.IslandNavGraph;
import aaronpost.clashcraft.Raiding.Path;
import aaronpost.clashcraft.Raiding.Raid;
import org.bukkit.Location;
import pathfinding.grid.GridCell;

import java.util.ArrayList;
import java.util.List;

public class TroopAgent {
    private Pair<Integer,Integer> agentPos;
    private final Island island;
    private final Arena arena;
    private Raid raid;
    private final IslandNavGraph navGraph;
    private TroopAgentOptions troopAgentOptions;

    public TroopAgent(Raid raid, IslandNavGraph navGraph, int x, int z) {
        this.raid = raid;
        this.arena = raid.getArena();
        this.island = arena.getIsland();
        this.navGraph = navGraph;
        this.agentPos = new Pair<>(x,z);
        this.troopAgentOptions = new TroopAgentOptions();
    }
    public void setAgentPos(int x, int z) {
        this.agentPos.first = x;
        this.agentPos.second = z;
    }
    public Pair<Integer,Integer> getAgentPos() {
        return agentPos;
    }
    public void setAgentOptions(TroopAgentOptions options) {
        this.troopAgentOptions = options;
    }
    public Location getLocationToLookAt(Building building) {
        if(building instanceof Wall) {
            Pair<Integer,Integer> loc = building.getGridLoc();
            return arena.getAbsLocationFromGridLoc(loc.first,loc.second,0.5f);
        }
        Pair<Integer,Integer> loc = navGraph.buildingCenters.get(building);
        return arena.getAbsLocationFromNavGridLoc(loc.first, loc.second, 0.5f);
    }
    private List<Building> pickBuildings() {
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
    public Path target() {
        List<Building> buildings = pickBuildings();
        int cheapestPathCost = Integer.MAX_VALUE;
        Building cheapestBuilding = null;
        Pair<Integer,List<GridCell>> path = null;
        for(Building building: buildings) {
            Pair<Integer,List<GridCell>> buildingPath = cheapestPathAroundWalls(agentPos.first,agentPos.second, building);
            if(buildingPath != null) {
                if(buildingPath.first < cheapestPathCost) {
                    path = new Pair<>(buildingPath.first,Path.copyGridCellList(buildingPath.second));
                    cheapestPathCost = buildingPath.first;
                    cheapestBuilding = building;
                }
            }
        }
        Building wall = null;
        for(Building building: buildings) {
            Pair<Pair<Integer,List<GridCell>>,Building> buildingPathWithWall = cheapestPathThroughWalls(agentPos.first,agentPos.second, building);
            if(buildingPathWithWall != null) {
                Pair<Integer, List<GridCell>> buildingPath = buildingPathWithWall.first;
                if (buildingPath != null) {
                    if (buildingPath.first < cheapestPathCost) {
                        System.out.println(1427890);
                        path = new Pair<>(buildingPath.first,Path.copyGridCellList(buildingPath.second));
                        wall = buildingPathWithWall.second;
                        cheapestPathCost = buildingPath.first;
                        cheapestBuilding = building;
                    }
                }
            }
        }
        if(path == null) {
            return null;
        }
        if(wall != null) {
            return new Path(this.raid,path.second,wall,cheapestBuilding);
        }
        return new Path(this.raid,path.second,cheapestBuilding,null);
    }
    private Pair<Integer,List<GridCell>> cheapestPathAroundWalls(int x1, int z1, Building building) {
        int cheapestPathCost = Integer.MAX_VALUE;
        Pair<Integer,Integer> targetLoc = null;
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
        if(targetLoc == null) {
            return null;
        }
        return new Pair<>(cheapestPathCost,navGraph.path(x1,z1,targetLoc.first,targetLoc.second));
    }
    private Pair<Pair<Integer,List<GridCell>>,Building> cheapestPathThroughWalls (int x1, int z1, Building building) {
        int cheapestPathCost = Integer.MAX_VALUE;
        Pair<Integer,Integer> targetLoc = null;
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
        Pair<Integer,List<GridCell>> pair = new Pair<>(cheapestPathCost,
                navGraph.pathIfWallDestroyed(x1,z1,targetLoc.first,targetLoc.second,targetWall));
        return new Pair<>(pair,targetWall);
    }

    public Pair<Integer,Integer> findClosestWalkableLoc(Pair<Integer,Integer> gridLoc) {
        return navGraph.findClosestWalkableLoc(gridLoc);
    }
}
