package aaronpost.clashcraft.Commands;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Buildings.BuildingInHand;
import aaronpost.clashcraft.Interfaces.IArenaCommand;
import aaronpost.clashcraft.Islands.Island;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class LeftClickBuilding implements IArenaCommand {
    @Override
    public void execute(Arena arena) {
        Player player = arena.getPlayer();
        Island island = arena.getIsland();
        Block targetBlockExact = player.getTargetBlockExact(500);
        if(targetBlockExact == null) {
            return;
        }
        Building building = island.findBuildingAtLocation(targetBlockExact.getLocation());
        if(building == null) {
            return;
        }
        building.click();
    }
}
