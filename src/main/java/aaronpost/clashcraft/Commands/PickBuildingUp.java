package aaronpost.clashcraft.Commands;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Buildings.Wall;
import aaronpost.clashcraft.Interfaces.IArenaCommand;
import aaronpost.clashcraft.Islands.Island;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class PickBuildingUp implements IArenaCommand {
    private Building building = null;
    public PickBuildingUp() {

    }
    public PickBuildingUp(Building building) {
        this.building = building;
    }
    @Override
    public void execute(Arena arena) {
        Player player = arena.getPlayer();
        Island island = arena.getIsland();
        if(island.hasBuildingInHand()) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                    TextComponent.fromLegacyText(ChatColor.GRAY + " You can only have " +
                            ChatColor.RED + "one " + ChatColor.GRAY +
                            "building picked up at a time."));
            return;
        }
        Building tempBuilding = this.building;
        if(tempBuilding == null) {
            Block targetBlockExact = player.getTargetBlockExact(500);
            if(targetBlockExact == null) {
                return;
            }
            tempBuilding = island.findBuildingAtLocation(targetBlockExact.getLocation());
            if(tempBuilding == null) {
                return;
            }
        }
        tempBuilding.pickupRequest();
        island.getBuildings().remove(tempBuilding);
        tempBuilding.resetToGrass();
        player.getInventory().addItem(tempBuilding.getItemStack());
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                TextComponent.fromLegacyText(ChatColor.GRAY + "Picked up "
                        + tempBuilding.getPlainDisplayName()));
    }
}
