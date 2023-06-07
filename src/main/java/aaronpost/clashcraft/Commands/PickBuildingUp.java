package aaronpost.clashcraft.Commands;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Interfaces.IArenaCommand;
import aaronpost.clashcraft.Islands.Island;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class PickBuildingUp implements IArenaCommand {
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
        if(island.hasBuildingInHand()) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                    TextComponent.fromLegacyText(ChatColor.GRAY + " You can only have " +
                            ChatColor.RED + "one " + ChatColor.GRAY +
                            "building picked up at a time."));
            return;
        }
        building.pickupRequest();
        island.getBuildings().remove(building);
        building.resetToGrass();
        player.getInventory().addItem(building.getItemStack());
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                TextComponent.fromLegacyText(ChatColor.GRAY + "Picked up "
                        + building.getPlainDisplayName()));
    }
}
