package aaronpost.clashcraft.Input;

import aaronpost.clashcraft.Arenas.Arenas;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InputListener implements Listener {
    //List<InputActions>
    @EventHandler
    public void onClick(PlayerInteractEvent i) {
        Player p = i.getPlayer();
        if(Arenas.a.playerAtArena(p)) {

        }
    }
}
