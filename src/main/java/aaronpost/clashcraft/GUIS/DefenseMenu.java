package aaronpost.clashcraft.GUIS;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.Cannon;
import aaronpost.clashcraft.Buildings.GoldMine;
import aaronpost.clashcraft.Buildings.Wall;
import aaronpost.clashcraft.GUIS.Manager.InventoryButton;
import aaronpost.clashcraft.GUIS.Manager.InventoryGUI;
import aaronpost.clashcraft.Globals.BuildingGlobals;
import aaronpost.clashcraft.Globals.GUIHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class DefenseMenu extends InventoryGUI {
    private Player p;
    private Arena arena;
    public DefenseMenu(Arena arena) {
        super(ChatColor.GOLD + "+ Shop +");
        this.arena = arena;
        this.p = arena.getPlayer();
    }
    @Override
    protected Inventory createInventory(String name) {
        return Bukkit.createInventory(null, 27, name);
    }
    @Override
    public void decorate(Player p) {
        this.getInventory().setItem(10, BuildingGlobals.WALL_ITEM_STACK.clone());
        this.getInventory().setItem(12, Cannon.getShopItem());
        this.addButton(10,purchaseBuilding("wall"));
        this.addButton(12,purchaseBuilding("cannon"));
        GUIHelper.fillEmptyGUISpots(getInventory(),27);
    }
    private InventoryButton purchaseBuilding(String key) {
        return new InventoryButton().consumer(event -> {
            Player player = (Player) event.getWhoClicked();
            switch (key) {
                case "wall" -> {
                    arena.purchaseNewBuilding(new Wall(arena));
                    p.closeInventory();
                }
                case "cannon" -> {
                    arena.purchaseNewBuilding(new Cannon(arena));
                    p.closeInventory();
                }
            }
        });
    }
}
