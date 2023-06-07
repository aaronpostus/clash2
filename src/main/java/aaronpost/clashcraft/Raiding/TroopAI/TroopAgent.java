package aaronpost.clashcraft.Raiding.TroopAI;

import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Pair;

import java.util.List;

public class TroopAgent {
    private Nodes nodes;
    private Pair<Integer,Integer> agentPos;
    public TroopAgent(Nodes nodes) {
        this.nodes = nodes;
    }
    public boolean canReach(Building building) {
        for(Pair<Integer,Integer> gridLoc : nodes.getNeighbors(building)) {
            //if()
        }
        //if(canReach())
        return true;
    }
    public boolean canReach(int x, int z) {
        return true;
    }
    public List<Building> shortestPath(Building building) {
       // if()
        return null;
    }
}
