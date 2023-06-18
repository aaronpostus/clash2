package aaronpost.clashcraft.GUIS;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.Barracks;
import aaronpost.clashcraft.Buildings.GoldMine;
import aaronpost.clashcraft.GUIS.Manager.InventoryButton;
import aaronpost.clashcraft.GUIS.Manager.InventoryGUI;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ArmyMenu extends InventoryGUI {
    private Player p;
    private Arena arena;
    public ArmyMenu(Arena arena) {
        this.arena = arena;
        this.p = arena.getPlayer();
    }
    @Override
    protected Inventory createInventory() {
        return Bukkit.createInventory(null, 27, ChatColor.GOLD + "+ Shop +");
    }
    @Override
    public void decorate(Player p) {
        this.getInventory().setItem(10, BuildingGlobals.BARRACKS_ITEM_STACK.clone());
        this.addButton(10,purchaseBuilding("barracks"));
    }
    private InventoryButton purchaseBuilding(String key) {
        return new InventoryButton().consumer(event -> {
            Player player = (Player) event.getWhoClicked();
            switch(key) {
                case "barracks":
                    arena.purchaseNewBuilding(new Barracks(arena));
                    p.closeInventory();
                    //ClashCraft.guiManager.openGUI(new IslandMenuRefactor(),arena.getPlayer());
                    break;
            }
        });
    }
}
