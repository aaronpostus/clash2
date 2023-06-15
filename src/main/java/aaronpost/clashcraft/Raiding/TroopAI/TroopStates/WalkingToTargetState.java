package aaronpost.clashcraft.Raiding.TroopAI.TroopStates;

import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Raiding.Troop;

public class WalkingToTargetState implements ITroopState {
    Troop troop;
    public WalkingToTargetState(Troop troop) {
        this.troop = troop;
    }
    @Override
    public void update() {
        /**if(!troop.hasTarget()) {
            troop.target();
        }**/
    }

    @Override
    public void buildingDestroyed(Building building) {
        troop.skipTarget();
    }
}
