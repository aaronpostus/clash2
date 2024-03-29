package aaronpost.clashcraft.Raiding.TroopAI;

import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Islands.Island;
import aaronpost.clashcraft.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Nodes {
    private final Map<Building, List<Pair<Integer, Integer>>> neighbors = new HashMap<>();
    public Nodes(Island island) {
    }

    public List<Pair<Integer,Integer>> getNeighbors(Building building) { return neighbors.get(building); }
}
