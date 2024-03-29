package aaronpost.clashcraft.Arenas;

import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Singletons.Sessions;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Arenas {

    private final ArrayList<Arena> arenas = new ArrayList<>();
    private final Map<Player,Arena> playerArenaMap = new HashMap<>();
    public static Arenas a = new Arenas();
    public static int GRID_X_LENGTH = 90;
    public static int GRID_Z_LENGTH = 90;
    // we allow troops to walk on the outside ring
    public static int NAV_GRID_X_LENGTH = GRID_X_LENGTH + 4;
    public static int NAV_GRID_Z_LENGTH = GRID_X_LENGTH + 4;
    public static Location spawn = new Location(Globals.world, 54, 68, 11);

    public Arenas() {
        addArena(40, 84, 71);
    }

    private void addArena(int x, int y, int z) {
        arenas.add(new Arena(new Location(Globals.world, x, y, z)));
    }

    public void sendToSpawn(Player p) {
        p.teleport(spawn);
    }
    public boolean playerAtArena(Player p) {
        for(Arena arena: arenas) {
            if(arena.getPlayer() != null) {
                if (arena.getPlayer().equals(p)) {
                    return true;
                }
            }
        }
        return false;
    }
    public void assignPlayer(Player player, Arena arena) {
        Sessions.s.playerStates.put(player, Sessions.PlayerState.ISLAND);
        playerArenaMap.put(player,arena);
        arena.assignPlayer(player);
    }
    public void unassignPlayer(Player player, Arena arena) {
        Sessions.s.playerStates.put(player, Sessions.PlayerState.DEFAULT);
        playerArenaMap.remove(player);
        arena.unassign();
    }

    public Arena findPlayerArena(Player p) {
        return playerArenaMap.get(p);
    }

    public boolean hasAvailableArena() {
        for(Arena arena: arenas) {
            if (arena.getPlayer() == null) {
                return true;
            }
        }
        return false;
    }
    public Arena findAvailableArena() {
        for(Arena arena: arenas) {
            if (arena.getPlayer() == null) {
                return arena;
            }
        }
        return null;
    }
}
