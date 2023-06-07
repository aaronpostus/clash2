package aaronpost.clashcraft.Buildings.BuildingStates;

import aaronpost.clashcraft.Buildings.Building;

public class IslandState extends IBuildingState {
    private transient Building building;
    public IslandState(Building building) {
        this.building = building;
    }
    @Override
    public void update() {
        building.update();
    }

    @Override
    public void refreshReferences(Building building) {
        this.building = building;
    }

    @Override
    public void click() {
        this.building.click();
    }

    @Override
    public void openMenu() {
        this.building.openMenu();
    }

    @Override
    public void catchUp(float hoursToCatchUp) {
        this.building.catchUp(hoursToCatchUp);
    }

    @Override
    public void visualUpdate() {

    }

    @Override
    public void place(int x, int z) {

    }

    @Override
    public void pickup() {
        building.pickup();
        building.state = new InHandState(building);
    }
}
