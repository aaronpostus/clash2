package aaronpost.clashcraft.Buildings.BuildingStates;

import aaronpost.clashcraft.Buildings.Building;

// Author: Aaron Post
// Note: This should be an interface, but Gson is picky with interfaces
public abstract class IBuildingState {
    public abstract void update();
    public abstract void refreshReferences(Building building);
    public abstract void click();
    public abstract void openMenu();
    public abstract void catchUp(float hoursToCatchUp);
    public abstract void visualUpdate();
    public abstract void place(int x, int z);
    public abstract void pickup();
}
