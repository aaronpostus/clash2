package aaronpost.clashcraft.Raiding;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Arenas.Arenas;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.PersistentData.Serializer;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class Raids {
    public static Raids r = new Raids();
    public final Map<Raid,UUID> raids = new HashMap<>();
    public Raids() {

    }
    public boolean tryRaid(Player raider) {
        // Needs to be modified to not server multiple players the same offline island at the same time
        // Also needs to prevent showing the player the same island multiple times

        File sessionFile = new File(Serializer.SESSIONS_PATH);
        File[] files = sessionFile.listFiles();
        Server server = ClashCraft.plugin.getServer();
        if(files == null) {
            return false;
        }
        for(File playerFile: files) {
            String fileName = playerFile.getName();
            UUID uuid = UUID.fromString(fileName.substring(0, fileName.lastIndexOf('.')));
            if(server.getPlayer(uuid) == null) {
                Arena arena = Arenas.a.findPlayerArena(raider);
                arena.unload();
                raids.put(new Raid(arena, uuid),uuid);
                return true;
            }
        }
        return false;
    }

}
