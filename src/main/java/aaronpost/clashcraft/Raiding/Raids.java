package aaronpost.clashcraft.Raiding;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Arenas.Arenas;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.PersistentData.Serializer;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Raids {
    public static Raids r = new Raids();
    private List<Raid> raids = new ArrayList<>();
    public Raids() {

    }
    public boolean tryRaid(Player raider, int x, int z) {
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
                raids.add(new Raid(arena, uuid, x, z));
                return true;
            }
        }
        return false;
    }

}
