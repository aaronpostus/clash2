package aaronpost.clashcraft;

import aaronpost.clashcraft.Arenas.ArenaManager;
import aaronpost.clashcraft.Arenas.Arenas;
import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.PersistentData.Serializer;
import aaronpost.clashcraft.Schematics.Controller;
import aaronpost.clashcraft.Schematics.Schematic;
import aaronpost.clashcraft.Singletons.Schematics;
import aaronpost.clashcraft.Singletons.GameManager;
import aaronpost.clashcraft.Singletons.Sessions;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ClashCraft extends JavaPlugin {
    static List<String> commands = Arrays.asList("test","debugtools","island", "createschematic", "savecoordinates");
    public static ClashCraft plugin;
    private Serializer s;
    @Override
    public void onEnable() {
        plugin = this;
        plugin.getDataFolder();
        GameManager gm = GameManager.getInstance();
        gm.startUpdates();

        s = new Serializer();
        getServer().getPluginManager().registerEvents(s,this);
        getServer().getPluginManager().registerEvents(new Controller(), this);
        getServer().getPluginManager().registerEvents(new ArenaManager(), this);
        getServer().getPluginManager().registerEvents(new Interaction(), this);

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
                    ClashCraft.plugin.getLogger().info(p.getName() + "'s session has been loaded!");
                    Sessions.s.addSession(p, session);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onDisable() {
        Player[] list = new Player[Bukkit.getOnlinePlayers().size()];
        Bukkit.getOnlinePlayers().toArray(list);

        try {
            s.serializeSchematics();
        } catch(IOException e) {
            e.printStackTrace();
        }
        for(Player p: list) {
            try {
                Session session = Sessions.s.getSession(p);
                if(session != null) {
                    s.serializeSession(p, session);
                }
                ClashCraft.plugin.getLogger().info(p.getName() + "'s session has been saved!");
                if(Arenas.a.playerAtArena(p)) {
                    Arenas.a.findPlayerArena(p).unassign();
                    ClashCraft.plugin.getLogger().info(p.getName() + "'s arena has been unassigned.");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        getLogger().info("Successfully disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = getServer().getPlayer(sender.getName());
        if(!commands.contains(label)) {
            return false;
        }
        else if (label.equals("test")) {
            sender.sendMessage("hi");
            //GridGraph<Integer> gridGraph = new GridGraph<Integer>(3,3);
            return true;
        }
        else if (label.equals("debugtools")) {
            ItemStack stack = new ItemStack(Material.RED_CONCRETE);
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName("Barracks");
            stack.setItemMeta(meta);
            player.getInventory().addItem(stack);

            stack = new ItemStack(Material.CAMPFIRE);
            meta = stack.getItemMeta();
            meta.setDisplayName("Army Camp");
            stack.setItemMeta(meta);
            player.getInventory().addItem(stack);

            stack = new ItemStack(Material.OAK_FENCE);
            meta = stack.getItemMeta();
            meta.setDisplayName("Wall");
            stack.setItemMeta(meta);
            player.getInventory().addItem(stack);

            stack = new ItemStack(Material.BLAZE_ROD);
            meta = stack.getItemMeta();
            meta.setDisplayName(ChatColor.BLUE + "Schematic Wand");
            ArrayList<String> lore = new ArrayList<>(Arrays.asList(
                    ChatColor.YELLOW + "x: ",
                    ChatColor.YELLOW + "y: ",
                    ChatColor.YELLOW + "z: ",
                    ChatColor.YELLOW + "x: ",
                    ChatColor.YELLOW + "y: ",
                    ChatColor.YELLOW + "z: ")
            );
            meta.setLore(lore);
            stack.setItemMeta(meta);
            player.getInventory().addItem(stack);

            stack = new ItemStack(Material.SHEARS);
            meta = stack.getItemMeta();
            meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Save Coordinates Wand");
            meta.setLore(lore);
            stack.setItemMeta(meta);
            player.getInventory().addItem(stack);

            return true;
        }
        else if (label.equals("island")) {
            if(!Arenas.a.playerAtArena(player)) {
                if (Arenas.a.hasAvailableArena()) {
                    player.sendMessage(Globals.prefix + ChatColor.GRAY + " Sent you to your island.");
                    Arenas.a.findAvailableArena().assignPlayer(player);
                } else {
                    player.sendMessage(Globals.prefix + ChatColor.GRAY + " No arenas are available. Please wait for someone to unload their island.");
                    Arenas.a.sendToSpawn(player);
                }
            } else {
                player.sendMessage(Globals.prefix + ChatColor.GRAY + " Sent you to spawn.");
                Arenas.a.findPlayerArena(player).unassign();
            }
            return true;
        }
        System.out.println(0);
        if(label.equals("createschematic")) {
            ItemStack item = player.getInventory().getItemInMainHand();
            System.out.println(1);
            if(item.hasItemMeta()) {
                System.out.println(2);
                ItemMeta meta = item.getItemMeta();
                if(meta.hasDisplayName()) {
                    System.out.println(3);
                    if(meta.getDisplayName().equals(ChatColor.BLUE + "Schematic Wand")) {
                        System.out.println(4);
                        ArrayList<String> lore = new ArrayList<>(Objects.requireNonNull(meta.getLore()));
                        if (Controller.checkForCompleteLore(lore, 2)) {
                            System.out.println(5);
                            int x = Controller.getCoordFromString(lore.get(0));
                            int y = Controller.getCoordFromString(lore.get(1));
                            int z = Controller.getCoordFromString(lore.get(2));

                            Location l = new Location(player.getWorld(), x, y, z);
                            Block b1 = l.getBlock();
                            player.sendMessage(ChatColor.GOLD + "Created Schematic \"" + args[0] + "\".");
                            player.sendMessage(ChatColor.GRAY + "1: " + x + ", " + y + ", " + z);

                            x = Controller.getCoordFromString(lore.get(3));
                            y = Controller.getCoordFromString(lore.get(4));
                            z = Controller.getCoordFromString(lore.get(5));

                            l = new Location(player.getWorld(), x, y, z);
                            Block b2 = l.getBlock();

                            player.sendMessage(ChatColor.GRAY + "2: " + x + ", " + y + ", " + z);

                            Schematics.s.addSchematic(new Schematic(b1, b2, args[0]));
                        } else {
                            player.sendMessage(ChatColor.GOLD + "Lore is incomplete.");
                        }
                        return true;
                    }
                }
            }
        }
        else if(label.equals("savecoordinates")) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if(item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                if(meta.getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Save Coordinates Wand")) {
                    ArrayList<String> lore = new ArrayList<>(Objects.requireNonNull(meta.getLore()));
                    if (Controller.checkForCompleteLore(lore, 1)) {
                        int x = Controller.getCoordFromString(lore.get(0));
                        int y = Controller.getCoordFromString(lore.get(1));
                        int z = Controller.getCoordFromString(lore.get(2));

                        Location l = new Location(player.getWorld(), x, y, z);
                        Block b1 = l.getBlock();

                        x = Controller.getCoordFromString(lore.get(3));
                        y = Controller.getCoordFromString(lore.get(4));
                        z = Controller.getCoordFromString(lore.get(5));

                        l = new Location(player.getWorld(), x, y, z);
                        Block b2 = l.getBlock();

                        String text = args[0];
                        player.sendMessage(ChatColor.GOLD + "Created \"" + text + "\".");
                        player.sendMessage(ChatColor.GRAY + "Coordinate: " + x + ", " + y + ", " + z);

                        Schematics.s.addSchematic(new Schematic(b1, b2, text));
                    } else {
                        player.sendMessage(ChatColor.GOLD + "Lore is incomplete.");
                    }
                    return true;
                }
            }
        }
        return  false;
    }
}
