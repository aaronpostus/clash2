package aaronpost.clashcraft.Raiding.TroopAI.TroopStates;

import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Raiding.Troop;

public class AttackingTarget implements ITroopState {
    private Troop troop;
    private Building target;
    public AttackingTarget(Troop troop, Building target) {
        this.target = target;
        this.troop = troop;
        this.troop.lookAt(target);
    }
    @Override
    public void update() {
        troop.playAttackAnimation();
        target.damage(5);
    }
    @Override
    public void buildingDestroyed(Building building) {
        troop.state = new NoTargetState(troop);
    }

    @Override
    public void callback() {
        // no pathing
    }
}
