package aaronpost.clashcraft.Islands;

import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Graph.GridGraph;

import java.util.ArrayList;

public class Island {
    public static enum Interactions {
        LEFT_CLICK, RIGHT_CLICK
    }
    private ArrayList<ArrayList<Building>> buildings;
    private GridGraph<Building> nodes;
    public Island() { buildings = new ArrayList<>(); nodes = new GridGraph<>(10,10); }

    // maybe pass through a list of ICommands instead
    public void interactionQuery(Island.Interactions interactionType, int x, int y) {
        //
        Building building;
        switch (interactionType) {
            case LEFT_CLICK:
        }
    }
}
