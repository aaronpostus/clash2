package aaronpost.clashcraft.Arenas;

import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Islands.Island;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Arenas {

    private ArrayList<Arena> arenas = new ArrayList<>();
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
