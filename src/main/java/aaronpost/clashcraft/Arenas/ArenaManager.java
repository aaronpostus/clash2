package aaronpost.clashcraft.Arenas;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ArenaManager implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        Arena arena = Arenas.a.findPlayerArena(p);
        if(arena != null) {
            arena.unassign();
        }
        p.setAllowFlight(false);
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.getPlayer().setAllowFlight(false);
        Arenas.a.sendToSpawn(e.getPlayer());
        e.getPlayer().setGameMode(GameMode.ADVENTURE);
    }
}
