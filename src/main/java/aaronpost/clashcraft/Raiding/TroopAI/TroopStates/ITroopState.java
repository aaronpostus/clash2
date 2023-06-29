package aaronpost.clashcraft.Raiding.TroopAI.TroopStates;

import aaronpost.clashcraft.Buildings.Building;

public interface ITroopState {
    void update();
    void buildingDestroyed(Building building);
    void callback();
}
