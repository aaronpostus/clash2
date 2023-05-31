package aaronpost.clashcraft.PersistentData;

import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Currency.Currency;
import aaronpost.clashcraft.Schematics.Schematic;
import aaronpost.clashcraft.Schematics.Schematics;
import aaronpost.clashcraft.Session;
import aaronpost.clashcraft.Singletons.Sessions;
import aaronpost.clashcraft.Troop;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Serializer implements Listener {
    private final Logger logger = ClashCraft.plugin.getLogger();
    private final String sessionsPath = ClashCraft.plugin.getDataFolder().getAbsolutePath() + File.separator + "Sessions";
    private final String schematicsPath = ClashCraft.plugin.getDataFolder().getAbsolutePath() + File.separator + "Schematics";
    private final Gson sessionGson;
    private final String path = "aaronpost.clashcraft.";
    public Serializer() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Building.class, new DeserializerAdapter<Building>(path + "Buildings."));
        builder.registerTypeAdapter(Currency.class, new DeserializerAdapter<Currency>(path + "Currency."));
        //builder.setPrettyPrinting();
        this.sessionGson = builder.create();
    }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent p) {
        Session c = Sessions.s.getSession(p.getPlayer());
        if(c!=null) {
            try {
                serializeSession(p.getPlayer(), c);
                logger.info(p.getPlayer().getName() + "'s session has been saved!");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Sessions.s.removeSession(p.getPlayer());
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent p) {
        // Load their files up!
        Player player = p.getPlayer();
        File file = new File(sessionsPath + File.separator + player.getUniqueId() + ".json");
        // Player already has data, deserialize
        if(file.exists()) {
            try {
                Sessions.s.addSession(player, deserializeSession(player));
                logger.info(player.getName() + "'s session has been loaded!");
            } catch (IOException e) {
                // This shouldn't happen because we already have a check for it.
                e.printStackTrace();
            }
        }
        // Player does NOT already have data, so create.
        else {
            logger.info(player.getName() + " is new! Their session data has been instantiated.");
            Sessions.s.addSession(player,new Session(player));

        }
    }
    public void serializeSchematics() throws IOException {
        List<Schematic> schematics = Schematics.s.getSchematics();
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson g = builder.create();
        File file;
        for(int i = 0; i < schematics.size(); i++) {
            file = new File(schematicsPath + File.separator + schematics.get(i).getName() + ".json");
            if(!file.exists()) {
                try {
                    file.createNewFile();
                    Writer w = new FileWriter(file, false);
                    g.toJson(schematics.get(i), w);
                    w.flush();
                    w.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public List<Schematic> deserializeSchematics() throws IOException {
        File[] schematicsFilePath = new File(schematicsPath + File.separator).listFiles();
        List<Schematic> schematics = new ArrayList<>();
        int numberOfLoadedSchematics = 0;
        if(schematicsFilePath != null) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            for(File file: schematicsFilePath) {
                if (file.exists()) {
                    Reader reader = new FileReader(file);
                    Schematic s = gson.fromJson(reader, Schematic.class);
                    schematics.add(s);
                    reader.close();
                    numberOfLoadedSchematics++;
                }
                else {
                    logger.info("Something is horribly wrong with the file system. Fix this.");
                    return null;
                }
            }
            logger.info("Loaded " + numberOfLoadedSchematics +" schematics.");
            return schematics;
        }
        return new ArrayList<Schematic>();
    }
    public void serializeSession(Player p, Session c) throws IOException {
        Session s = Sessions.s.getSession(p);
        File file = new File(sessionsPath + File.separator + p.getUniqueId() + ".json");
        System.out.println(file.getAbsolutePath());
        file.createNewFile();
        Writer w = new FileWriter(file, false);
        sessionGson.toJson(s, w);
        w.flush();
        w.close();
    }
    public Session deserializeSession(Player p) throws IOException {
        File file = new File(sessionsPath + File.separator + p.getUniqueId() + ".json");
        if (file.exists()){
            Reader reader = new FileReader(file);
            Session s = sessionGson.fromJson(reader, Session.class);
            reader.close();
            return s;
        }
        return null;
    }
}
