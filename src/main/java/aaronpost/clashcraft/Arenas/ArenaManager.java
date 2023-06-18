package aaronpost.clashcraft.Arenas;

import aaronpost.clashcraft.Singletons.Sessions;
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
            Arenas.a.unassignPlayer(p,arena);
        }
        p.setAllowFlight(false);
        Sessions.s.playerStates.remove(p);
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.setAllowFlight(false);
        Arenas.a.sendToSpawn(p);
        p.setGameMode(GameMode.ADVENTURE);
        Sessions.s.playerStates.put(p, Sessions.PlayerState.DEFAULT);
    }
}
