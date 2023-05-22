package aaronpost.clashcraft;

import aaronpost.clashcraft.Buildings.GoldMine;
import aaronpost.clashcraft.Currency.Gold;
import aaronpost.clashcraft.Graph.GridGraph;
import aaronpost.clashcraft.Singletons.GameManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class ClashCraft extends JavaPlugin {
    static List<String> commands = Arrays.asList("test");
    public static ClashCraft plugin;
    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("running1");
        plugin = this;
        GameManager gm = GameManager.getInstance();
        gm.startUpdates();
        gm.addFixedUpdatable(new GoldMine());
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
