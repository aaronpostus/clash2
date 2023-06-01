package aaronpost.clashcraft.Commands;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Interfaces.IArenaCommand;
import aaronpost.clashcraft.Islands.Island;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class PlaceBuilding implements IArenaCommand {
    @Override
    public void execute(Arena arena) {
        Island island = arena.getIsland();
        Player player = arena.getPlayer();
        Building building = island.getBuildingInHand();
        Block targetBlockExact = player.getTargetBlockExact(500);
        if(targetBlockExact == null) {
            return;
        }
        Location loc = targetBlockExact.getLocation();
        if(!island.canPlaceBuilding(building, loc)) {
            return;
        }
//        Pair<Double, Double> gridLoc = arena.getGridLocFromAbsLoc(loc);
//        int x = gridLoc.first.intValue();
//        int z = gridLoc.second.intValue();
        island.placeBuildingInHand();
        //building.place(x, z);
        PlayerInventory inventory = player.getInventory();
        inventory.removeItem(inventory.getItemInMainHand());
//        island.addBuilding(building, x, z);
    }
}
