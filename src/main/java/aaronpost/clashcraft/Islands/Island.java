package aaronpost.clashcraft.Islands;
import aaronpost.clashcraft.Buildings.Building;
import java.util.ArrayList;

public class Island {
    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public static enum Interactions {
        LEFT_CLICK, RIGHT_CLICK
    }
    private ArrayList<Building> buildings;
    private Building[][] nodes;
    public Island() { buildings = new ArrayList<>(); nodes = new Building[10][10]; }

    // maybe pass through a list of ICommands instead
    public void interactionQuery(Island.Interactions interactionType, int x, int y) {
        //
        Building building;
        switch (interactionType) {
            case LEFT_CLICK:
        }
    }
}
