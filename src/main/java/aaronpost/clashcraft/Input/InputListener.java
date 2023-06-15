package aaronpost.clashcraft.Input;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Arenas.Arenas;
import aaronpost.clashcraft.Commands.PlaceTroop;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InputListener implements Listener {
    //List<InputActions>
    @EventHandler
    public void onClick(PlayerInteractEvent i) {
        Player p = i.getPlayer();
        if(Arenas.a.playerAtArena(p)) {
            Arena a = Arenas.a.findPlayerArena(p);
            if(a.getCurrentRaid() != null) {
                ItemStack item = p.getInventory().getItemInMainHand();
                if(item.getType() == Material.GOLD_INGOT) {
                   p.getInventory().remove(item);
                    PlaceTroop pt = new PlaceTroop();
                    pt.execute(a);
                }

            }
        }
    }
}
