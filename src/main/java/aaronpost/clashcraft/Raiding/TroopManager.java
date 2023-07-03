package aaronpost.clashcraft.Raiding;

import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Globals.Globals;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

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
    public boolean containsPlayer(Player player) {
        for(Troop troop: troops) {
            if(troop.getTroopPlayer().equals(player)) {
                return true;
            }
        }
        return false;
    }
    public Troop getTroopFromPlayer(Player player) {
        for(Troop troop: troops) {
            if(troop.getTroopPlayer().equals(player)) {
                return troop;
            }
        }
        return null;
    }
    public void removeTroop(Troop troop) {
        if (troops.contains(troop)) {
            troop.delete();
            troops.remove(troop);
        }
    }
    public void deleteAllTroops() {
        for (Troop troop: troops) {
            troop.delete();
        }
    }
    public void notifyTroopsOfDestroyedBuilding(Building building) {
        for(Troop troop: troops) {
            if(troop.hasTarget() && troop.getTarget() == building) {
                troop.notifyBuildingDestroyed(building);
            }
        }
    }
}
