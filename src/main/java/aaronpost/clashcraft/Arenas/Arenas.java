package aaronpost.clashcraft.Arenas;

import aaronpost.clashcraft.Globals;
import aaronpost.clashcraft.Islands.Island;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Arenas {

    private ArrayList<Arena> arenas = new ArrayList<>();
    public static Arenas a = new Arenas();
    public static int GRID_X_LENGTH = 55;
    public static int GRID_Z_LENGTH = 55;
    public static Location spawn = new Location(Globals.world, -421, 124, 265);

    public Arenas() {
        addArena(-224, 52, -94);
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
    public Arena findArenaFromIsland(Island island) {
        for(Arena arena: arenas) {
            if(island.equals(arena.getIsland())) {
                return arena;
            }
        }
        return null;
    }
    public Arena findPlayerArena(Player p) {
        for(Arena arena: arenas) {
            if(arena.getPlayer() != null) {
                if (arena.getPlayer().equals(p)) {
                    return arena;
                }
            }
        }
        return null;
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
