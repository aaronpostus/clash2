package aaronpost.clashcraft.Buildings.BuildingStates;

import aaronpost.clashcraft.Buildings.Building;

public class InHandState extends IBuildingState {
    private transient Building building;
    public InHandState(Building building) {
        this.building = building;
    }
    @Override
    public void update() {
        // allow updating while in hand, but don't do visual updates
        building.update();
    }

    @Override
    public void refreshReferences(Building building) {
        this.building = building;
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
        building.state = new IslandState(building);
        building.place(x,z);
    }

    @Override
    public void pickup() {

    }
}
