package aaronpost.clashcraft.GUIS;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Arenas.Arenas;
import aaronpost.clashcraft.Buildings.Barracks;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Globals.Globals;
import aaronpost.clashcraft.Islands.Island;
import aaronpost.clashcraft.Session;
import aaronpost.clashcraft.Singletons.Sessions;
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

public class ArmyBuildings implements Listener {
    private final Player p;

    public ArmyBuildings(Player p) {
        this.p = p;
        Inventory categorySelect = p.getServer().createInventory(null, 27, ChatColor.GOLD + "+ Resources +");
        ItemStack stack = BuildingGlobals.BARRACKS_ITEM_STACK.clone();
        categorySelect.setItem(10, stack);

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
                // Kills the listener for this class, closes inventory
                HandlerList.unregisterAll(this);
                e.getWhoClicked().closeInventory();
                Session session = Sessions.s.getSession(p);
                Island island = session.getIsland();
                Arena arena = Arenas.a.findPlayerArena(p);
                if(island.getBuildingInBuildingInHand() == null) {
                    ItemStack stack = new ItemStack(Material.AIR);
                    switch (e.getCurrentItem().getType()) {
                        case TARGET: {
                            arena.purchaseNewBuilding(new Barracks(arena));
                            break;
                        }
                    }
                } else {
                    p.sendMessage(Globals.prefix + ChatColor.GRAY + " You may only have " + ChatColor.RED + "one" + ChatColor.GRAY + " building picked up at a time.");

                }
            }
        }
    }
}
