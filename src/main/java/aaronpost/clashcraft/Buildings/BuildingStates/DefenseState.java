package aaronpost.clashcraft.Buildings.BuildingStates;

import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Raiding.Raid;

public class DefenseState extends IBuildingState {
    private IBuildingState previousState;
    private Raid raid;
    private Building building;
    public DefenseState(Building building, Raid raid, IBuildingState previousState) {
        this.previousState = previousState;
        this.raid = raid;
    }
    public void restoreState() {
        building.state = previousState;
    }
    @Override
    public void update() {

    }

    @Override
    public void refreshReferences(Building building) {

    }

    @Override
    public void click() {

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
