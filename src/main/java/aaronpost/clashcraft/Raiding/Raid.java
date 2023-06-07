package aaronpost.clashcraft.Raiding;

import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Islands.Island;

import java.util.List;

public class Raid {
    List<Building> buildingsExcludingWalls;
    public Raid(Island island) {
        List<Building> buildings = island.getBuildings();
        //remove walls from this

    }
}
