package aaronpost.clashcraft.Raiding.TroopAI.TroopStates;

import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Raiding.Troop;

public class AttackingTarget implements ITroopState {
    private Troop troop;
    public AttackingTarget(Troop troop) {
        this.troop = troop;
    }
    @Override
    public void update() {
        troop.playAttackAnimation();
    }

    @Override
    public void buildingDestroyed(Building building) {

    }
}
