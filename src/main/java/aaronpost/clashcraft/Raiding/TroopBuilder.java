package aaronpost.clashcraft.Raiding;

import aaronpost.clashcraft.Buildings.Building;

public class TroopBuilder {
    private Troop troop;
    public TroopBuilder(IslandNavGraph navGraph) {
        this.troop = new Troop(navGraph);
    }
    public void onlyTarget(Building building) {

    }
    public Troop getTroop() { return troop; }
}
