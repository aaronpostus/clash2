package aaronpost.clashcraft.PersistentData;

import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Currency.Currency;
import aaronpost.clashcraft.Buildings.BuildingStates.IBuildingState;
import aaronpost.clashcraft.Islands.Island;
import aaronpost.clashcraft.Schematics.Schematic;
import aaronpost.clashcraft.Singletons.Schematics;
import aaronpost.clashcraft.Session;
import aaronpost.clashcraft.Singletons.Sessions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class Serializer {
    private final Logger logger = ClashCraft.plugin.getLogger();
    public static final String SESSIONS_PATH = ClashCraft.plugin.getDataFolder().getAbsolutePath() + File.separator + "Sessions";
    public static final String SCHEMATICS_PATH = ClashCraft.plugin.getDataFolder().getAbsolutePath() + File.separator + "Schematics";
    private final Gson sessionGson;

    public Serializer() {
        GsonBuilder builder = new GsonBuilder();
        String path = "aaronpost.clashcraft.";
        builder.registerTypeAdapter(Building.class, new DeserializerAdapter<Building>(path + "Buildings."));
        builder.registerTypeAdapter(Currency.class, new DeserializerAdapter<Currency>(path + "Currency."));
        builder.registerTypeAdapter(IBuildingState.class, new DeserializerAdapter<IBuildingState>(path +
                "Buildings.BuildingStates."));
        builder.registerTypeAdapter(Island.class, new IslandAdapter());
        builder.setPrettyPrinting();
        this.sessionGson = builder.create();
    }
    public void logoffPlayer(Player p) {
        Session c = Sessions.s.getSession(p);
        if(c!=null) {
            c.getIsland().saveBuildings();
            try {
                serializeSession(p, c);
                logger.info(p.getName() + "'s session has been saved!");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Sessions.s.removeSession(p);
        }
    }
    public void logonPlayer(Player p) {
        // Load their files up!
        Player player = p.getPlayer();
        File file = new File(SESSIONS_PATH + File.separator + player.getUniqueId() + ".json");
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
            file = new File(SCHEMATICS_PATH + File.separator + schematics.get(i).getName() + ".json");
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
        File[] schematicsFilePath = new File(SCHEMATICS_PATH + File.separator).listFiles();
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
        File file = new File(SESSIONS_PATH + File.separator + p.getUniqueId() + ".json");
        System.out.println(file.getAbsolutePath());
        file.createNewFile();
        Writer w = new FileWriter(file, false);
        sessionGson.toJson(s, w);
        w.flush();
        w.close();
    }
    public Session deserializeSession(Player p) throws IOException {
        return deserializeSession(p.getUniqueId());
    }
    public Session deserializeSession(UUID uuid) throws IOException {
        File file = new File(SESSIONS_PATH + File.separator + uuid + ".json");
        if (file.exists()){
            Reader reader = new FileReader(file);
            Session s = sessionGson.fromJson(reader, Session.class);
            reader.close();
            return s;
        }
        return null;
    }

}
