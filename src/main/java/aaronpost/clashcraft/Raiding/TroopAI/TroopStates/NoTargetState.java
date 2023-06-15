package aaronpost.clashcraft.Raiding.TroopAI.TroopStates;

import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Raiding.Troop;

public class NoTargetState implements ITroopState {
    private Troop troop;
    public NoTargetState(Troop troop) {
        this.troop = troop;
    }
    @Override
    public void update() {
        troop.target();
        troop.state = new WalkingToTargetState(troop);
    }

    @Override
    public void buildingDestroyed(Building building) {

    }
}
