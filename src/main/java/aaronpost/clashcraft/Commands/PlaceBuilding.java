package aaronpost.clashcraft.Commands;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Buildings.BuildingInHand;
import aaronpost.clashcraft.Buildings.Wall;
import aaronpost.clashcraft.Interfaces.IArenaCommand;
import aaronpost.clashcraft.Islands.Island;
import aaronpost.clashcraft.Pair;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class PlaceBuilding implements IArenaCommand {
    @Override
    public void execute(Arena arena) {
        Island island = arena.getIsland();
        Player player = arena.getPlayer();
        BuildingInHand buildingInHand = island.getBuildingInHand();
        Block targetBlockExact = player.getTargetBlockExact(500);
        if(targetBlockExact == null) {
            return;
        }
        Location loc = targetBlockExact.getLocation();
        Pair<Integer,Integer> gridLoc = buildingInHand.getSelectedGridPos();
        Building building = buildingInHand.getBuilding();
        building.refreshReferences(arena);
        if(!island.canPlaceBuilding(building, gridLoc.first, gridLoc.second)) {
            building.sendMessage("Cannot place here.");
            return;
        }
        building.sendMessage("Placing.");
        island.placeBuildingInHand();
        PlayerInventory inventory = player.getInventory();
        inventory.removeItem(inventory.getItemInMainHand());
        if(building instanceof Wall) {
            arena.carveWallGaps();
        }
    }
}
