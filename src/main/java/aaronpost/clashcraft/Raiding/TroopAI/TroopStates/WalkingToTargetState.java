package aaronpost.clashcraft.Raiding.TroopAI.TroopStates;

import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Raiding.Troop;

public class WalkingToTargetState implements ITroopState {
    Troop troop;
    Building target;
    public WalkingToTargetState(Troop troop, Building target) {
        this.troop = troop;
        this.target = target;
        System.out.println("walkstate to " + target.getDisplayName());
    }
    @Override
    public void update() {
        /**if(!troop.hasTarget()) {
            troop.target();
        }**/
    }

    @Override
    public void buildingDestroyed(Building building) {
        if(troop.getTarget().equals(building)) {
            troop.skipTarget();
        }
    }
    public void callback() {
        //reached target
        System.out.println("callback");
        troop.state = new AttackingTarget(troop,target);
    }
}
