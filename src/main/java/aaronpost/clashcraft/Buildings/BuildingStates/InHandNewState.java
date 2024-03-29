package aaronpost.clashcraft.Buildings.BuildingStates;

import aaronpost.clashcraft.Buildings.BuilderHut;
import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Buildings.Wall;
import aaronpost.clashcraft.Interfaces.IInstantBuild;
import org.bukkit.Sound;

public class InHandNewState extends IBuildingState {
    private transient Building building;
    public InHandNewState(Building building) {
        this.building = building;
    }

    @Override
    public void update() {
        // do nothing there is nothing to update
    }

    @Override
    public void refreshReferences(Building building) {
        this.building = building;
    }

    @Override
    public void click() {
        // do nothing there is no building on the island to click on
    }

    @Override
    public void openMenu() {
        // do nothing there is no menu to open
    }

    @Override
    public void catchUp(float hoursToCatchUp) {
        // do nothing there is nothing to catch up
    }

    @Override
    public void visualUpdate() {
        // in hand nothing to visually update
    }
    @Override
    public void place(int x, int z) {
        building.setRelativeLocation(x,z);
        BuildingState buildingState = new BuildingState(building);
        building.state = buildingState;
        building.getArena().playSound(Sound.BLOCK_ANVIL_USE,0.35f,1f);
        building.place(x,z);
        if(building instanceof IInstantBuild) {
            buildingState.finishBuilding();
        }
    }

    @Override
    public void pickup() {
        // never called
    }
    @Override
    public void stopUpdates() {

    }
}
