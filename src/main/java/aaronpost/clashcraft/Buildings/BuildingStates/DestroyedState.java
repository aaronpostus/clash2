package aaronpost.clashcraft.Buildings.BuildingStates;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.Building;

public class DestroyedState extends IBuildingState {
    private IBuildingState previousState;
    private transient Building building;
    public DestroyedState(Building building, IBuildingState previousState) {
        this.building = building;
        this.previousState = previousState;
        this.building.resetToGrass();
    }
    @Override
    public void update() {

    }

    @Override
    public void refreshReferences(Building building) {
        this.building = building;
    }

    @Override
    public void click() {
        if(building.getArena().currentState == Arena.ArenaState.RAID_STATE) {
            building.sendMessage("Destroyed.");
            return;
        }
        building.state = previousState;
        building.sendMessage("Repaired.");
        building.paste();
    }

    @Override
    public void openMenu() {

    }

    @Override
    public void catchUp(float hoursToCatchUp) {

    }

    @Override
    public void visualUpdate() {

    }

    @Override
    public void place(int x, int z) {

    }

    @Override
    public void pickup() {

    }
}
