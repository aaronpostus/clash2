package aaronpost.clashcraft.Islands;

import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Graph.GridGraph;

import java.util.ArrayList;

public class Island {
    private ArrayList<Building> buildings;
    private GridGraph<Building> nodes;
    public Island() { buildings = new ArrayList<>(); nodes = new GridGraph<>(10,10); }

}
