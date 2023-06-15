package aaronpost.clashcraft.Raiding;

import java.util.ArrayList;
import java.util.List;

public class TroopManager {
    private final List<Troop> troops;
    public TroopManager() {
        this.troops = new ArrayList<>();
    }
    public void addTroop(Troop troop) {
        this.troops.add(troop);
    }
    public void removeTroop(Troop troop) {
        if (troops.contains(troop)) {
            troops.remove(troop);
        }
    }
    public void updateTroops() {
        for (Troop troop: troops) {
            troop.update();
        }
    }
    public void deleteAllTroops() {
        for (Troop troop: troops) {
            troop.delete();
        }
    }

}
