package aaronpost.clashcraft;

import aaronpost.clashcraft.Buildings.GoldMine;
import aaronpost.clashcraft.Singletons.GameManager;
import aaronpost.clashcraft.Singletons.Sessions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import us.aaronpost.clash.Schematics.Schematic;
import us.aaronpost.clash.Schematics.Schematics;
import us.aaronpost.clash.PersistentData.Serializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClashCraft extends JavaPlugin {
    static List<String> commands = Arrays.asList("test");
    public static ClashCraft plugin;
    private Serializer s;
    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("running1");
        plugin = this;
        GameManager gm = GameManager.getInstance();
        gm.startUpdates();
        gm.addFixedUpdatable(new GoldMine());

        s = new Serializer();
        Player[] list = new Player[getServer().getOnlinePlayers().size()];
        getServer().getOnlinePlayers().toArray(list);
        ArrayList<Schematic> schematics = new ArrayList<>();
        try {
            schematics = (ArrayList<Schematic>) s.deserializeSchematics();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Schematics.s.addSchematic(schematics);

        for(Player p: list) {
            try {
                Session session = s.deserializeSession(p);
                if(session != null) {
                    session.setD(System.currentTimeMillis());
                    ClashCraft.plugin.getLogger().info(p.getName() + "'s session has been loaded!");
                    Sessions.s.getSessions().add(session);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!commands.contains(label)) {
            return false;
        }
        else if (label.equals("test")) {
            sender.sendMessage("hi");
            GridGraph<Integer> gridGraph = new GridGraph<Integer>(3,3);
            return true;
        }
        return  false;
    }
}
