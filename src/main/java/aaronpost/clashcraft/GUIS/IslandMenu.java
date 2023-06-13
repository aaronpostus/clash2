package aaronpost.clashcraft.GUIS;

import aaronpost.clashcraft.ClashCraft;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class IslandMenu implements Listener {
    private final Player p;

    public IslandMenu(Player p) {
        this.p = p;
        Inventory categorySelect = p.getServer().createInventory(null, 27, ChatColor.GOLD + "+ Shop +");
        ItemStack stack = new ItemStack(Material.BOW);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Army");
        stack.setItemMeta(meta);
        categorySelect.setItem(10, stack);

        stack = new ItemStack(Material.CHEST);
        meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Resources");
        stack.setItemMeta(meta);
        categorySelect.setItem(12, stack);

        stack = new ItemStack(Material.SHIELD);
        meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "Defenses");
        stack.setItemMeta(meta);
        categorySelect.setItem(14, stack);

        stack = new ItemStack(Material.TNT);
        meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_GRAY + "Traps");
        stack.setItemMeta(meta);
        categorySelect.setItem(16, stack);

        p.openInventory(categorySelect);
    }

    @EventHandler
    public void onInvenClose(InventoryCloseEvent e) {
        if(e.getPlayer().equals(p)) {
            HandlerList.unregisterAll(this);
        }
    }

    @EventHandler
    public void onItemClick(InventoryClickEvent e) {
        if(e.getWhoClicked().equals(p)) {
            e.setCancelled(true);
            if(e.getCurrentItem() != null) {
                switch (e.getCurrentItem().getType()) {
                    case CHEST:
                        ClashCraft.plugin.getServer().getPluginManager().registerEvents
                                (new ResourcesMenu((Player) e.getWhoClicked()), ClashCraft.plugin);
                        HandlerList.unregisterAll(this);
                        break;
                    case BOW:
                        ClashCraft.plugin.getServer().getPluginManager().registerEvents
                                (new ArmyBuildings((Player) e.getWhoClicked()), ClashCraft.plugin);
                        HandlerList.unregisterAll(this);
                        break;
                    case SHIELD:
                        ClashCraft.plugin.getServer().getPluginManager().registerEvents
                                (new DefenseBuildings((Player) e.getWhoClicked()), ClashCraft.plugin);
                        HandlerList.unregisterAll(this);
                        break;
                }
            }
        }
    }
}
