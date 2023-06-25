package aaronpost.clashcraft.Buildings.BuildingStates;

import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Raiding.Raid;

public class DefenseState extends IBuildingState {
    private IBuildingState previousState;
    private transient Raid raid;
    private transient Building building;
    private transient float hp;
    private transient int maxhp;
    public DefenseState(Building building, Raid raid, IBuildingState previousState) {
        this.previousState = previousState;
        this.building = building;
        this.raid = raid;
        this.maxhp = building.getMaxHitpoints();
        this.hp = maxhp;
    }
    public void restoreState(Building building) {
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
        building.sendMessage("HP: " + (int) Math.ceil(hp) + "/" + maxhp);
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
