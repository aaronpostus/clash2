package aaronpost.clashcraft.Arenas;

import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Raiding.Raids;
import aaronpost.clashcraft.Singletons.Sessions;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ArenaManager implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if(Sessions.s.getSession(p) == null) {
            e.setQuitMessage(null);
            return;
        }
        e.setQuitMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "-" + ChatColor.DARK_GRAY + "] " +
                ChatColor.GRAY + e.getPlayer().getName());
        Arena arena = Arenas.a.findPlayerArena(p);
        if(arena != null) {
            Arenas.a.unassignPlayer(p,arena);
        }
        p.setAllowFlight(false);
        Sessions.s.playerStates.remove(p);
        ClashCraft.serializer.logoffPlayer(p);
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(Raids.r.raids.containsValue(p.getUniqueId())) {
            e.setJoinMessage(null);
            p.kickPlayer("You are being raided (or a player is visiting your island).. try joining again soon.");
            return;
        }
        e.setJoinMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "+" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY
                + e.getPlayer().getName());
        p.setAllowFlight(false);
        Arenas.a.sendToSpawn(p);
        ClashCraft.serializer.logonPlayer(p);
        p.setGameMode(GameMode.ADVENTURE);
        Sessions.s.playerStates.put(p, Sessions.PlayerState.DEFAULT);
    }
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        String message = ChatColor.GRAY + e.getPlayer().getName() + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY
                + e.getMessage();
        for(Player player: ClashCraft.plugin.getServer().getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }
}
