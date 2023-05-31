package aaronpost.clashcraft.Schematics;

import aaronpost.clashcraft.ClashCraft;
import aaronpost.clashcraft.Singletons.Schematics;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * Controller handles creating schematics and saving coordinates.
 */
public class Controller implements Listener {
    @EventHandler
    public void onClick(PlayerInteractEvent i) {
        if(i.hasItem()) {
            ItemStack item = i.getItem();
            if (item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                if (meta.getDisplayName().equals(ChatColor.BLUE + "Schematic Wand")) {
                    if (i.hasBlock()) {
                        EquipmentSlot slot = i.getHand(); //Get the hand of the event and set it to 'e'.
                        if (slot.equals(EquipmentSlot.HAND)) { // checks for main hand to prevent from running twice

                            Location l = i.getClickedBlock().getLocation();
                            ArrayList<String> lore = new ArrayList<>(meta.getLore());

                            int x = (int) Math.floor(l.getX());
                            int y = (int) Math.floor(l.getY());
                            int z = (int) Math.floor(l.getZ());

                            if (i.getAction() == Action.LEFT_CLICK_BLOCK) {
                                i.getPlayer().sendMessage(ChatColor.GOLD + "Position 1: " + ChatColor.GRAY + x + ", " + y + ", " + z);
                                lore.set(0, ChatColor.YELLOW + "x: " + x);
                                lore.set(1, ChatColor.YELLOW + "y: " + y);
                                lore.set(2, ChatColor.YELLOW + "z: " + z);
                                i.setCancelled(true);
                            } else if (i.getAction() == Action.RIGHT_CLICK_BLOCK) {
                                lore.set(3, ChatColor.YELLOW + "x: " + x);
                                lore.set(4, ChatColor.YELLOW + "y: " + y);
                                lore.set(5, ChatColor.YELLOW + "z: " + z);
                                i.getPlayer().sendMessage(ChatColor.GOLD + "Position 2: " + ChatColor.GRAY + x + ", " + y + ", " + z);
                                i.setCancelled(true);
                            }
                            meta.setLore(lore);
                            item.setItemMeta(meta);
                        }
                    }
                } else if(meta.getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Save Coordinates Wand")) {
                    if (i.hasBlock()) {
                        EquipmentSlot slot = i.getHand(); //Get the hand of the event and set it to 'e'.
                        if (slot.equals(EquipmentSlot.HAND)) { // checks for main hand to prevent from running twice
                            Location l = i.getClickedBlock().getLocation();
                            ArrayList<String> lore = new ArrayList<>(meta.getLore());
                            int x = (int) Math.floor(l.getX());
                            int y = (int) Math.floor(l.getY());
                            int z = (int) Math.floor(l.getZ());
                            lore.set(0, ChatColor.YELLOW + "x: " + x);
                            lore.set(1, ChatColor.YELLOW + "y: " + y);
                            lore.set(2, ChatColor.YELLOW + "z: " + z);
                            i.getPlayer().sendMessage(ChatColor.GOLD + "Coordinate: " + ChatColor.GRAY + x + ", " + y + ", " + z);
                            meta.setLore(lore);
                            item.setItemMeta(meta);
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void onCrouch(PlayerToggleSneakEvent e) {
        if(e.isSneaking() && e.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) {
            if(e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.BLAZE_ROD)) {
                if(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Schematic Wand")) {
                    if(Schematics.s.getSchematics().size() > 0) {
                        //Not a perfect way of doing this, but it will work.
                        ClashCraft.plugin.getServer().getPluginManager().registerEvents(new AdminSchematicMenu(e.getPlayer()), ClashCraft.plugin);
                    }
                }
            }
        }
    }

    public static boolean checkForCompleteLore(ArrayList<String> lore, int numOfCoord) {
        for(int i = 0; i < 3 * numOfCoord; i++) {
            if(ChatColor.stripColor(lore.get(i)).length() < 4) {
                return false;
            }
        }
        return true;
    }
    public static int getCoordFromString(String str) {
        return Integer.parseInt(ChatColor.stripColor(str.substring(5)));
    }
}
