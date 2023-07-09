package aaronpost.clashcraft.Buildings.BuildingStates;

import aaronpost.clashcraft.Buildings.Building;

public class InHandBuildingState extends IBuildingState {
    private transient Building building;
    public InHandBuildingState(Building building) {
        this.building = building;
    }

    @Override
    public void update() {
        float percentageBuilt = building.getPercentageBuilt();
        if(percentageBuilt < 1f) {
            building.buildStep();
        }
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
        building.setRelativeLocation(x,z);
        building.state = new BuildingState(building);
        building.place(x,z);
    }

    @Override
    public void pickup() {

    }
    @Override
    public void stopUpdates() {
        building.getArena().getIsland().removeBuildingInHand();
        building.state = new BuildingState(building);
    }
}
