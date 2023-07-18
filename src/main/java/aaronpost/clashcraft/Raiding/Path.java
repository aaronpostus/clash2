package aaronpost.clashcraft.Raiding;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Buildings.Wall;
import net.citizensnpcs.api.ai.goals.MoveToGoal;
import org.bukkit.Location;
import pathfinding.grid.GridCell;
import java.util.ArrayList;
import java.util.List;

public class Path {
    public Building currentTarget;
    public Building nextTarget;
    public List<GridCell> currentPath;
    public List<GridCell> nextPath;
    private Arena arena;
    public Path(Raid raid,List<GridCell> path, Building firstTarget, Building nextTarget) {
        this.currentTarget = firstTarget;
        this.nextTarget = nextTarget;
        this.arena = raid.getArena();
        initializeCurrentPathAndNextPath(path);
        translateToWaypoints();
        System.out.println(firstTarget.toString());
    }

    private void initializeCurrentPathAndNextPath(List<GridCell> path) {
        // make current path only up to the first building and then the second path the remaining stuffs
        this.currentPath = copyGridCellList(path);
        if(this.nextTarget == null) {
            System.out.println("dshua");
            int i;
            for(i=0;i<currentPath.size();i++) {
                if(path.get(i).building != null) {
                    if(path.get(i).building.equals(this.nextTarget)) {
                        System.out.println("fas");
                        break;
                    }
                }
            }
            this.nextPath = this.currentPath.subList(i,currentPath.size());
            this.currentPath = this.currentPath.subList(0,i);
        }
    }

    public static List<GridCell> copyGridCellList(List<GridCell> path) {
        List<GridCell> copy = new ArrayList<>();
        for(GridCell cell: path) {
            GridCell cellCopy = new GridCell(cell.x,cell.y,cell.isWalkable());
            cellCopy.building = cell.building;
            copy.add(cellCopy);
        }
        return copy;
    }
    public List<Location> getWaypoints() {
        List<GridCell> waypoints = getPathToTarget();
        List<Location> locWaypoints = new ArrayList<>();
        for(GridCell cell: waypoints) {
            locWaypoints.add(arena.getAbsLocationFromNavGridLoc(cell.x,cell.y,1));
        }
        return locWaypoints;
    }
    private List<GridCell> getPathToTarget() {
        Building target = currentTarget;
        int pathLength;
        for(pathLength = 0; pathLength < currentPath.size(); pathLength++) {
            if(currentPath.get(pathLength).building == target) {
                break;
            }
        }
        if(!(target instanceof Wall)) {
            pathLength++;
        }
        List<GridCell> pathToTarget = new ArrayList<>();
        for(int i = 0; i < pathLength; i++) {
            pathToTarget.add(currentPath.get(i));
        }
        return pathToTarget;
    }
    public void skipCurrentTarget() {
        if(nextTarget == null) {
            //recalculate
        }
        currentTarget = nextTarget;
        currentPath = nextPath;
        nextTarget = null;
        nextPath = null;
    }
    public boolean hasTarget() {
        return currentTarget != null;
    }
    public Building getTarget() {
        return currentTarget;
    }
    private void translateToWaypoints() {
        List<GridCell> pointsToKeep = new ArrayList<>();
        for(int i = 0; i < currentPath.size(); i++) {
            // remove if not a building
            Building building = currentPath.get(i).building;
            if(((i % 8 == 0) && currentPath.get(i).isWalkable()) || building == currentTarget) {
                pointsToKeep.add(currentPath.get(i));
            }
            else if(i == currentPath.size() - 1) {
                pointsToKeep.add(currentPath.get(i));
            }
            else if(i < currentPath.size() - 1 && currentPath.get(i+1).building == currentTarget) {
                pointsToKeep.add(currentPath.get(i));
            }
        }
        this.currentPath = pointsToKeep;
    }
    public List<GridCell> getCells() {
        return currentPath;
    }
}
