package aaronpost.clashcraft.Raiding;

import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.PersistentData.Serializer;
import org.bukkit.Server;

import java.io.File;
import java.util.UUID;

public class Raids {
    public static Raids r = new Raids();
    public Raids() {

    }
    public boolean searchForRaid() {
        File sessionFile = new File(Serializer.SESSIONS_PATH);
        File[] files = sessionFile.listFiles();
        Server server = ClashCraft.plugin.getServer();
        for(File playerFile: files) {
            String fileName = playerFile.getName();
            UUID uuid = UUID.fromString(fileName.substring(0, fileName.lastIndexOf('.')));
            if(server.getPlayer(uuid) == null) {
                return true;
            }
        }
        return false;
    }

}
